package akhokarasik;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dimka on 8/31/2017.
 */
public class Node {

    private final Map<Character, Node> children;

    public Node() {
        children = new HashMap<>();
    }

    public void add(char symbol, Node child) {
        children.put(symbol, child);
    }

    public Node createChild(char symbol) {
        Node child = new Node();
        children.put(symbol, child);
        return child;
    }

    public Node getChild(char symbol) {
        return children.get(symbol);
    }

    public boolean isTerminate() {
        return children.isEmpty();
    }

    public void accept(Trie.Visitor visitor) {
        for (Map.Entry<Character, Node> entry : children.entrySet()) {
            visitor.visit(entry.getKey(), entry.getValue());
            entry.getValue().accept(visitor);
        }

    }

}
