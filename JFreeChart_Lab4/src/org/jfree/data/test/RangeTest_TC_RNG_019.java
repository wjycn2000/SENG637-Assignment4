package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_019 {

    @Test
    public void testGetUpperBound_WithNegativeRange() {
        // Creating a range (-10, -1)
        Range range = new Range(-10.0, -1.0);

        // Expected upper bound is -1.0
        assertEquals("Upper bound of Range(-10,-1) should be -1.0", 
                     -1.0, range.getUpperBound(), 0.000000001d);
    }
}
