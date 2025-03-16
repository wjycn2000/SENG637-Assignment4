package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_018 {

    @Test
    public void testGetUpperBound_WithMaxValue() {
        // Creating a range with Double.MAX_VALUE as the upper bound
        Range range = new Range(5.0, Double.MAX_VALUE);

        // Expected upper bound is Double.MAX_VALUE
        assertEquals("Upper bound of Range(5, Double.MAX_VALUE) should be Double.MAX_VALUE", 
                     Double.MAX_VALUE, range.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testGetUpperBound_WithMinValue() {
        // Creating a range with Double.MIN_VALUE as the lower bound
        Range range = new Range(Double.MIN_VALUE, 10.0);

        // Expected upper bound is 10.0
        assertEquals("Upper bound of Range(Double.MIN_VALUE, 10) should be 10.0", 
                     10.0, range.getUpperBound(), 0.000000001d);
    }
}
