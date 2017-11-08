package boyermoore;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ZFunctionTest {

    private ZFunction zFunction;

    @Before
    public void setUp() throws Exception {
        zFunction = new ZFunction("abacabax");
    }

    @Test
    public void largePrefixLengths() throws Exception {
        assertArrayEquals(new int[]{0, 0, 1, 0, 3, 0, 1, 0}, zFunction.largePrefixLengths());
    }

    @Test
    public void largePrefixLengths_sameLetters() throws Exception {
        zFunction = new ZFunction("aaaaaaaa");
        assertArrayEquals(new int[]{0, 7, 6, 5, 4, 3, 2, 1}, zFunction.largePrefixLengths());
    }

    @Test
    public void largePrefixLengths_repeatable() throws Exception {
        zFunction = new ZFunction("ababababab");
        assertArrayEquals(new int[]{0, 0, 8, 0, 6, 0, 4, 0, 2, 0},
                zFunction.largePrefixLengths());
    }

    @Test
    public void largePrefixLengths_repeatableWithC() throws Exception {
        zFunction = new ZFunction("ababababcababababc");
        assertArrayEquals(new int[]{0, 0, 6, 0, 4, 0, 2, 0, 0, 9, 0, 6, 0, 4, 0, 2, 0, 0},
                zFunction.largePrefixLengths());
    }

}