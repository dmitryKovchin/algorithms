package akhokarasik;


import java.util.ArrayList;
import java.util.List;

public class Trie {

    private final Node root;

    public Trie() {
      root = new Node();
    }

    public void addWord(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            Node child = node.getChild(word.charAt(i));
            if (child == null) {
                child = node.createChild(word.charAt(i));
            }
            node = child;
        }
        node.setTerminated(true);
    }

    public List<TextPosition> getSubstringPositions(String text) {
        List<TextPosition> list = new ArrayList<>();
        Node node = root;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (node.getChild(ch) != null) {
                node = node.getChild(ch);
            } else {
                while (node != root && node.getChild(ch) == null) {
                    node = node.getPrefSuffReference();
                }
                if (node != root) {
                    node = node.getChild(ch);
                }
            }
           if (node.isTerminated()) {
               list.add(new TextPosition(i + 1 - node.getDepth(), node.getDepth()));
           }
        }
        return list;
    }

    public void accept(Visitor visitor) {
        root.accept(visitor);
    }

    public interface Visitor {
        void visit(Node node);
        void visit(char symbol, Node node);
    }

}
