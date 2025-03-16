package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_027 {

    private Range validRange;

    @Before
    public void setUp() {
        // Initialize a valid range
        validRange = new Range(5.0, 10.0);
    }

    @Test
    public void testIntersects_WithNullRange() {
        try {
            // Attempt to call intersects on a null range
            Range nullRange = null;
            boolean result = nullRange.intersects(5.0, 10.0);
            fail("Calling intersects() on a null Range should throw a NullPointerException.");
        } catch (NullPointerException e) {
            System.out.println("Handled null range intersection: " + e.getMessage());
        }
    }

    @Test
    public void testIntersects_WithExtremeRange() {
        // Checking if Range(5,10) intersects with an extremely large range
        assertTrue("Range(5,10) should intersect with Range(Double.MIN_VALUE, Double.MAX_VALUE)",
                   validRange.intersects(Double.MIN_VALUE, Double.MAX_VALUE));
    }
}
