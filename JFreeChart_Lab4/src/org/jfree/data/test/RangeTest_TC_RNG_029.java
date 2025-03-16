package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_029 {

    private Range originalRange;

    @Before
    public void setUp() {
        // Initialize the original range
        originalRange = new Range(5.0, 10.0);
    }

    @Test
    public void testShift_PositiveShift_AllowZeroCrossing() {
        // Shifting Range(5,10) by 2 with allowZeroCrossing=true
        Range shiftedRange = Range.shift(originalRange, 2.0, true);

        // Expected range: (7,12)
        assertEquals("Shifted lower bound should be 7.0", 7.0, shiftedRange.getLowerBound(), 0.000000001d);
        assertEquals("Shifted upper bound should be 12.0", 12.0, shiftedRange.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testShift_NegativeShift_AllowZeroCrossing() {
        // Shifting Range(5,10) by -2 with allowZeroCrossing=true
        Range shiftedRange = Range.shift(originalRange, -2.0, true);

        // Expected range: (3,8)
        assertEquals("Shifted lower bound should be 3.0", 3.0, shiftedRange.getLowerBound(), 0.000000001d);
        assertEquals("Shifted upper bound should be 8.0", 8.0, shiftedRange.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testShift_PositiveShift_NoZeroCrossing() {
        // Shifting Range(5,10) by 2 with allowZeroCrossing=false
        Range shiftedRange = Range.shift(originalRange, 2.0, false);

        // Expected range: (7,12) (same as allowZeroCrossing=true since it doesn't cross zero)
        assertEquals("Shifted lower bound should be 7.0", 7.0, shiftedRange.getLowerBound(), 0.000000001d);
        assertEquals("Shifted upper bound should be 12.0", 12.0, shiftedRange.getUpperBound(), 0.000000001d);
    }
}
