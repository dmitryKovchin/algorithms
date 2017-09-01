package akhokarasik;

/**
 * Created by dimka on 8/31/2017.
 */
public class AkhoKarasik {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.addWord("abba");
        trie.addWord("alibaba");
        trie.addWord("abcaba");

        trie.accept(new Trie.Visitor() {
            @Override
            public void visit(Node node) {

            }

            @Override
            public void visit(char symbol, Node node) {
                System.out.print(symbol);
            }
        });
    }
}
