package akhokarasik;

/**
 * Created by dimka on 8/31/2017.
 */
public class TextPosition {

    public final int start;
    public final int length;

    public TextPosition(int start, int length) {
        this.start = start;
        this.length = length;
    }

    @Override
    public String toString() {
        return "TextPosition{" +
                "start=" + start +
                ", length=" + length +
                '}';
    }
}
