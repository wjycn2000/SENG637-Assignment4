package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_013 {

    @Test
    public void testGetLowerBound_WithPositiveRange() {
        // Creating a range (5,10)
        Range range = new Range(5.0, 10.0);

        // Expected lower bound is 5.0
        assertEquals("Lower bound of Range(5,10) should be 5.0", 5.0, range.getLowerBound(), 0.000000001d);
    }

    @Test
    public void testGetLowerBound_WithNegativeRange() {
        // Creating a range (-5,5)
        Range range = new Range(-5.0, 5.0);

        // Expected lower bound is -5.0
        assertEquals("Lower bound of Range(-5,5) should be -5.0", -5.0, range.getLowerBound(), 0.000000001d);
    }
}
