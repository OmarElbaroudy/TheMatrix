package operators;

import modules.Node;

import java.util.List;

public class HandleAgents extends Operator {

    public HandleAgents(Operation operation, Cost cost) {
        super(operation, cost);
    }

    @Override
    public List<Node> expand(Node node) {
        return null;
    }
}
