package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_015 {

    @Test
    public void testGetLowerBound_WithNegativeRange() {
        // Creating a range (-10, -1)
        Range range = new Range(-10.0, -1.0);

        // Expected lower bound is -10.0
        assertEquals("Lower bound of Range(-10,-1) should be -10.0", 
                     -10.0, range.getLowerBound(), 0.000000001d);
    }
}
