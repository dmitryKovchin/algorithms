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
            // i = 0 => reverseIndex = m -1
            // i = m - 2 => reverseIndex = 1
            int reverseIndex = m - 1 - i;
            int maxSuffix = zFunction.get(reverseIndex);
            if (safeShift[m - 1 - maxSuffix] > m - 1 - i) {
                safeShift[m - 1 - maxSuffix] = m - 1 - i;
            }
        }
    }

    public List<Integer> getAllOccurrencesInText(String text) {
        List<Integer> occurrences = new ArrayList<>();

        // Avoid String.charAt() - which is slow because of it checks StringIndexOutOfBoundsException
        char[] sArray = s.toCharArray();
        char[] textArray = text.toCharArray();
        int n = textArray.length;
        int textStartIndex = 0;
        int startCheckedRangeExclusive = -1;
        int endCheckedRangeInclusive = -1;

        while (textStartIndex <= n - m) {
            int j = m - 1;
            while (j > endCheckedRangeInclusive && sArray[j] == textArray[textStartIndex + j]) {
                j--;
            }
            if (j == endCheckedRangeInclusive) {
                // (startCheckedRangeExclusive; endCheckedRangeInclusive] - already checked
                j = startCheckedRangeExclusive;
                while (j >= 0 && sArray[j] == textArray[textStartIndex + j]) {
                    j--;
                }
            }
            int currentSafeShift;
            if (j < 0) {
                occurrences.add(textStartIndex);
                currentSafeShift = safeShift[0];
            } else {
                currentSafeShift = safeShift[j];
            }
            textStartIndex += currentSafeShift;
            startCheckedRangeExclusive = Math.max(0, j - currentSafeShift);
            endCheckedRangeInclusive = m - 1 - currentSafeShift;
        }
        return occurrences;
    }

    public int[] getSafeShift() {
        return safeShift;
    }
}
