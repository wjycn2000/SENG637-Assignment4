package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_002 {

    private Range range1;
    private Range range2;

    @Before
    public void setUp() {
        // Initialize two Range objects with slightly different values
        range1 = new Range(5.0, 10.0);
        range2 = new Range(5.0001, 10.0001);
    }

    @Test
    public void testEquals_WithSlightlyDifferentValues() {
        // Verify that the equals method correctly identifies them as different
        assertFalse("Comparing Range(5,10) with Range(5.0001, 10.0001) should return false", range1.equals(range2));
    }
}
