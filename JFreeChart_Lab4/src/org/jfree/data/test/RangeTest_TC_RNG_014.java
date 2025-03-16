package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_014 {

    @Test
    public void testGetLowerBound_WithMinValue() {
        // Creating a range with Double.MIN_VALUE as the lower bound
        Range range = new Range(Double.MIN_VALUE, 5.0);

        // Expected lower bound is Double.MIN_VALUE
        assertEquals("Lower bound of Range(Double.MIN_VALUE,5) should be Double.MIN_VALUE", 
                     Double.MIN_VALUE, range.getLowerBound(), 0.000000001d);
    }

    @Test
    public void testGetLowerBound_WithMaxValue() {
        // Creating a range with Double.MAX_VALUE as the upper bound
        Range range = new Range(-5.0, Double.MAX_VALUE);

        // Expected lower bound is -5.0
        assertEquals("Lower bound of Range(-5, Double.MAX_VALUE) should be -5.0", 
                     -5.0, range.getLowerBound(), 0.000000001d);
    }
}
