package queues;

import modules.Node;
import modules.State;
import operators.Cost;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class Queue {
    protected HashMap<State, Cost> cost;
    protected Node root;

    public abstract boolean isEmpty();

    public abstract void add(List<Node> nodes);

    public abstract Node removeFront();

    public final void makeQ(Node node) {
        root = node;
        cost = new HashMap<>();
        add(Collections.singletonList(node));
    }
}