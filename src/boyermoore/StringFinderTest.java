package boyermoore;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringFinderTest {

    private StringFinder stringFinder;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getSafeShift_sameLetters() throws Exception {
        stringFinder = new StringFinder("aaa");

        assertArrayEquals(new int[]{1, 1, 1}, stringFinder.getSafeShift());
    }

    @Test
    public void getSafeShift_simple() throws Exception {
        stringFinder = new StringFinder("aba");

        assertArrayEquals(new int[]{2, 2, 1}, stringFinder.getSafeShift());
    }

    @Test
    public void getSafeShift_simpleRepeatable() throws Exception {
        stringFinder = new StringFinder("baabaa");

        assertArrayEquals(new int[]{3, 3, 3, 3, 1, 1}, stringFinder.getSafeShift());
    }

    @Test
    public void getSafeShift() throws Exception {
        stringFinder = new StringFinder("abacabax");

        assertArrayEquals(new int[]{8, 8, 8, 8, 8, 8, 8, 1}, stringFinder.getSafeShift());
    }

    @Test
    public void getSafeShift_repeatable() throws Exception {
        stringFinder = new StringFinder("aacaabaabaa");

        assertArrayEquals(new int[]{9, 9, 9, 9, 9, 3, 9, 9, 6, 1, 1}, stringFinder.getSafeShift());
    }

    @Test
    public void getAllOccurrencesInText_repeatable() throws Exception {
        stringFinder = new StringFinder("baabaa");
        String text = "axabbaabaabaabaabaaxa";

        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(4);
        expectedList.add(7);
        expectedList.add(10);
        expectedList.add(13);
        List<Integer> list = stringFinder.getAllOccurrencesInText(text);
        assertEquals(expectedList, list);
    }

    @Test
    public void getAllOccurrencesInText_largest() throws Exception {
        int m = 10000;
        int n = 100000;
        StringBuilder patternBuilder = new StringBuilder();
        for (int i = 0; i < m; i++) {
            patternBuilder.append("a");
        }
        String pattern = patternBuilder.toString();
        StringBuilder textBuilder = new StringBuilder();
        for (int i = 0; i < n / m; i++) {
            textBuilder.append(pattern);
        }
        stringFinder = new StringFinder(pattern);
        String text = textBuilder.toString();

        List<Integer> expectedList = new ArrayList<>();
        for (int i = 0; i <= n - m; i++) {
            expectedList.add(i);
        }

        long start = System.currentTimeMillis();
        List<Integer> list = stringFinder.getAllOccurrencesInText(text);
        assertEquals(expectedList, list);
        long end = System.currentTimeMillis();
        assertTrue((end - start) < 1000);
    }

    @Test
    public void getAllOccurrencesInText_largest2() throws Exception {
        int m = 20;
        int n = 100000;
        StringBuilder patternBuilder = new StringBuilder();
        for (int i = 0; i < m; i++) {
            patternBuilder.append("ab");
        }
        String pattern = patternBuilder.toString();
        StringBuilder textBuilder = new StringBuilder();
        for (int i = 0; i < n / m; i++) {
            textBuilder.append(pattern);
        }
        stringFinder = new StringFinder(pattern);
        String text = textBuilder.toString();

        List<Integer> expectedList = new ArrayList<>();
        for (int i = 0; i <= n - m; i++) {
            expectedList.add(2 * i);
        }

        long start = System.currentTimeMillis();
        List<Integer> list = stringFinder.getAllOccurrencesInText(text);
        assertEquals(expectedList, list);
        long end = System.currentTimeMillis();
        assertTrue((end - start) < 1000);
    }

    @Test
    public void getAllOccurrencesInText_largest2Dumb() throws Exception {
        // This pattern length when dumb works same time as boyer-moore
        int m = 20;

        int n = 100000;
        StringBuilder patternBuilder = new StringBuilder();
        for (int i = 0; i < m; i++) {
            patternBuilder.append("ab");
        }
        String pattern = patternBuilder.toString();
        StringBuilder textBuilder = new StringBuilder();
        for (int i = 0; i < n / m; i++) {
            textBuilder.append(pattern);
        }

        String text = textBuilder.toString();

        List<Integer> expectedList = new ArrayList<>();
        for (int i = 0; i <= n - m; i++) {
            expectedList.add(2 * i);
        }

        long start = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();

        int index = text.indexOf(pattern);
        while (index >= 0) {
            list.add(index);
            index = text.indexOf(pattern, index + 1);
        }

        assertEquals(expectedList, list);
        long end = System.currentTimeMillis();
        assertTrue((end - start) < 1000);
    }

}