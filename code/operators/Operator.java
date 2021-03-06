package operators;

import modules.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Operator {
    protected Operation operation;
    protected Cost cost;

    protected Operator(Operation operation, Cost cost) {
        this.operation = operation;
        this.cost = cost;
    }

    public abstract List<Node> expand(Node node);

    protected State getNextState(State cur) {
        int n = cur.getGrid().getN();
        int m = cur.getGrid().getM();
        byte numOfAliveHostages = 0;

        Grid curGrid = cur.getGrid();
        Cell[][] newGrid = new Cell[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Host type = curGrid.getHostAtPos(i, j);

                if (type == Host.HOSTAGE) {
                    int damage = curGrid.getDamageAtPos(i, j);

                    if (damage + 2 < 100) {
                        numOfAliveHostages++;
                        newGrid[i][j] = new Cell(damage + 2);
                    } else {
                        newGrid[i][j] = new Cell(Host.MUTATED_AGENT);
                    }

                    continue;
                }

                if (type == Host.PAD) {
                    int toX = curGrid.getPadXAtPos(i, j);
                    int toY = curGrid.getPadYAtPos(i, j);
                    newGrid[i][j] = new Cell(toX, toY);
                    continue;
                }

                newGrid[i][j] = new Cell(type);
            }
        }

        List<Byte> newCarriedDamages =
                cur.getCarriedDamages().stream().
                        map(x -> (byte) Math.min(100, x + 2)).
                        collect(Collectors.toList());


        return new StateBuilder().
                Grid(new Grid(newGrid)).
                remCarry(cur.getRemCarry()).
                damage(cur.getDamage()).
                numOfAliveHostages(numOfAliveHostages).
                carriedDamages(newCarriedDamages).
                xPos(cur.getX()).
                yPos(cur.getY()).
                build();
    }

    protected List<Node> filterInvalidNodes(List<Node> nodes) {
        return nodes.stream().filter(
                node -> {
                    int x = node.getState().getX();
                    int y = node.getState().getY();
                    Host host = node.getState().getGrid().getHostAtPos(x, y);
                    return host != Host.MUTATED_AGENT && host != Host.AGENT;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public final String toString() {
        return operation.toString();
    }

    public Operation getOperation() {
        return operation;
    }

    public Cost getCost() {
        return cost;
    }
}
