package akhokarasik;


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
    }

    public void accept(Visitor visitor) {
        root.accept(visitor);
    }

    public interface Visitor {
        void visit(Node node);
        void visit(char symbol, Node node);
    }

}
