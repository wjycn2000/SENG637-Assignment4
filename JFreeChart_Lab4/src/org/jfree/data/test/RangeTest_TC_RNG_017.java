package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_017 {

    @Test
    public void testGetUpperBound_WithPositiveRange() {
        // Creating a range (5,10)
        Range range = new Range(5.0, 10.0);

        // Expected upper bound is 10.0
        assertEquals("Upper bound of Range(5,10) should be 10.0", 
                     10.0, range.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testGetUpperBound_WithNegativeRange() {
        // Creating a range (-5,5)
        Range range = new Range(-5.0, 5.0);

        // Expected upper bound is 5.0
        assertEquals("Upper bound of Range(-5,5) should be 5.0", 
                     5.0, range.getUpperBound(), 0.000000001d);
    }
}
