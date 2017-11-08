package boyermoore;

public class ZFunction {

    private final int[] a;

    public ZFunction(String s) {
        a = new int[s.length()];
        int zMax = 0;
        int zMaxStart = 0;
        a[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            int j;
            if (zMax > i) {
                a[i] = Math.min(a[i - zMaxStart], zMax - i + 1);
                j = i + a[i];
            } else {
                j = i;
            }
            while (j < s.length() && s.charAt(j) == s.charAt(j - i)) {
                j++;
            }
            if (j > i && zMax < j - 1) {
                zMax = j - 1;
                zMaxStart = i;
            }
            a[i] = j - i;
        }
    }

    public int get(int i) {
        return a[i];
    }

    public int[] largePrefixLengths() {
        return a;
    }
}
