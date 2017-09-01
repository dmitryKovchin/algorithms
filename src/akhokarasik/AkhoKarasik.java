package akhokarasik;

/**
 * Created by dimka on 8/31/2017.
 */
public class AkhoKarasik {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.addWord("abba");
        trie.addWord("alibaba");
        trie.addWord("ab");
        trie.addWord("abcaba");

        System.out.println(trie.getSubstringPositions("xxabbalibaba"));
    }
}
