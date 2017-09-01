package akhokarasik;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dimka on 8/31/2017.
 */
public class Node {

    private final Map<Character, Node> children;
    private Node prefSuffReference;
    private boolean terminated;
    private int depth;

    public Node() {
        children = new HashMap<>();
    }

    void add(char symbol, Node child) {
        children.put(symbol, child);
    }

    void attachToParent(Node parent, char symbol) {
        depth = parent.depth + 1;
        parent.add(symbol, this);
        if (parent.prefSuffReference == null) {
            prefSuffReference = parent;
            return;
        }
        Node node  = parent.prefSuffReference;
        while (node.prefSuffReference != null) {
            Node child = node.getChild(symbol);
            if (child != null) {
                node = child;
                break;
            }
        }
        prefSuffReference = node;
    }

    public Node createChild(char symbol) {
        Node child = new Node();
        child.attachToParent(this, symbol);
        return child;
    }

    public Node getChild(char symbol) {
        return children.get(symbol);
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public Node getPrefSuffReference() {
        return prefSuffReference;
    }

    public int getDepth() {
        return depth;
    }

    public void accept(Trie.Visitor visitor) {
        for (Map.Entry<Character, Node> entry : children.entrySet()) {
            visitor.visit(entry.getKey(), entry.getValue());
            entry.getValue().accept(visitor);
        }

    }

}
