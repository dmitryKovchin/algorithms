package boyermoore;

import java.util.ArrayList;
import java.util.List;

public class StringFinder {

    private final String s;
    private final int m;
    private final int[] safeShift;

    public StringFinder(String s) {
        this.s = s;
        m = s.length();
        safeShift = new int[m];
        ZFunction zFunction = new ZFunction(new StringBuilder(s).reverse().toString());
        int maxPrefix = 0;
        for (int i = 1; i < m; i++) {
            if (zFunction.get(i) == m - i && zFunction.get(i) > maxPrefix) {
                maxPrefix = zFunction.get(i);
            }
        }
        for (int i = 0; i < m - 1; i++) {
            safeShift[i] = m - maxPrefix;
        }
        safeShift[m - 1] = 1;
        for (int i = 0; i < m - 1; i++) {
            // i = 0 => rI = m -1
            // i = m - 2 => rI = 1
            int reverseIndex = m - 1 - i;
            int maxSuffix = zFunction.get(reverseIndex);
            if (safeShift[m - 1 - maxSuffix] > m - 1 - i) {
                safeShift[m - 1 - maxSuffix] = m - 1 - i;
            }
        }
    }

    public List<Integer> getAllOccurencesInText(String text) {
        List<Integer> list = new ArrayList<>();
        char[] sArray = s.toCharArray();
        char[] textArray = text.toCharArray();
        int n = textArray.length;
        int textStartIndex = 0;
        int startOldRangeExclusive = -1;
        int endOldRangeInclusive = -1;

        while (textStartIndex <= n - m) {
            int j = m - 1;
            while (j > endOldRangeInclusive && sArray[j] == textArray[textStartIndex + j]) {
                j--;
            }
            if (j == endOldRangeInclusive) {
                // [startOldRange; endOldRange] - already checked
                j = startOldRangeExclusive;
                while (j >= 0 && sArray[j] == textArray[textStartIndex + j]) {
                    j--;
                }
            }
            if (j < 0) {
                list.add(textStartIndex);
                textStartIndex += safeShift[0];
                startOldRangeExclusive = 0;
                endOldRangeInclusive = m - 1 - safeShift[0];
            } else {
                textStartIndex += safeShift[j];
                startOldRangeExclusive = j - safeShift[j];
                endOldRangeInclusive = m - 1 - safeShift[j];
            }
        }
        return list;
    }

    public int[] getSafeShift() {
        return safeShift;
    }
}
