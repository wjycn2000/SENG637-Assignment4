package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_030 {

    private Range originalRange;

    @Before
    public void setUp() {
        // Initialize the original range
        originalRange = new Range(5.0, 10.0);
    }

    @Test
    public void testShift_ByZero() {
        // Shifting Range(5,10) by 0 should return the same range
        Range shiftedRange = Range.shift(originalRange, 0.0, true);

        // Expected range: (5,10) (no change)
        assertEquals("Shifted lower bound should remain 5.0", 5.0, shiftedRange.getLowerBound(), 0.000000001d);
        assertEquals("Shifted upper bound should remain 10.0", 10.0, shiftedRange.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testShift_ByMaxValue() {
        // Shifting Range(5,10) by Double.MAX_VALUE should result in a very large range
        Range shiftedRange = Range.shift(originalRange, Double.MAX_VALUE, true);

        // Expected lower and upper bounds
        double expectedLower = 5.0 + Double.MAX_VALUE;
        double expectedUpper = 10.0 + Double.MAX_VALUE;

        assertEquals("Shifted lower bound should be 5.0 + Double.MAX_VALUE", expectedLower, shiftedRange.getLowerBound(), 0.000000001d);
        assertEquals("Shifted upper bound should be 10.0 + Double.MAX_VALUE", expectedUpper, shiftedRange.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testShift_ByNegativeMaxValue() {
        // Shifting Range(5,10) by -Double.MAX_VALUE should result in a very small range
        Range shiftedRange = Range.shift(originalRange, -Double.MAX_VALUE, true);

        // Expected lower and upper bounds
        double expectedLower = 5.0 - Double.MAX_VALUE;
        double expectedUpper = 10.0 - Double.MAX_VALUE;

        assertEquals("Shifted lower bound should be 5.0 - Double.MAX_VALUE", expectedLower, shiftedRange.getLowerBound(), 0.000000001d);
        assertEquals("Shifted upper bound should be 10.0 - Double.MAX_VALUE", expectedUpper, shiftedRange.getUpperBound(), 0.000000001d);
    }
}
