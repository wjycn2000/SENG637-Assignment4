package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_026 {

    private Range range;

    @Before
    public void setUp() {
        // Initialize the base range (5,10)
        range = new Range(5.0, 10.0);
    }

    @Test
    public void testIntersects_AtExactUpperBoundary() {
        // Range(5,10) intersects with Range(10,15) (touching at boundary)
        assertTrue("Range(5,10) should intersect with Range(10,15)", 
                   range.intersects(10.0, 15.0));
    }

    @Test
    public void testIntersects_AtExactLowerBoundary() {
        // Range(5,10) intersects with Range(4.9999,5) (touching at boundary)
        assertTrue("Range(5,10) should intersect with Range(4.9999,5)", 
                   range.intersects(4.9999, 5.0));
    }
}
