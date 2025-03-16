package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_004 {

    private Range largeRange1;
    private Range largeRange2;

    @Before
    public void setUp() {
        // Initialize two identical Range objects with very large values
        largeRange1 = new Range(-1e9, 1e9);
        largeRange2 = new Range(-1e9, 1e9);
    }

    @Test
    public void testEquals_WithLargeRangeValues() {
        // Verify that equals() returns true for identical large ranges
        assertTrue("Comparing Range(-1e9, 1e9) with an identical range should return true", 
                   largeRange1.equals(largeRange2));
    }
}
