package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_031 {

    @Test
    public void testShift_WithNullRange() {
        try {
            // Attempt to shift a null range by 5
            Range shiftedRange = Range.shift(null, 5.0);
            fail("Shifting a null Range should throw a NullPointerException.");
        } catch (IllegalArgumentException e) {
            System.out.println("Handled null range shift: " + e.getMessage());
            assertNotNull("NullPointerException should be thrown for null input", e);
        }
    }

    @Test
    public void testShift_WithNegativeRange() {
        // Creating a negative range (-10,-1)
        Range negativeRange = new Range(-10.0, -1.0);

        // Shifting it by 3
        Range shiftedRange = Range.shift(negativeRange, 3.0);

        // Expected range: (-7, 2)
        assertEquals("Shifted lower bound should be -7.0", -7.0, shiftedRange.getLowerBound(), 0.000000001d);
        assertEquals("Shifted upper bound should be 2.0", 2.0, shiftedRange.getUpperBound(), 0.000000001d);
    }
}
