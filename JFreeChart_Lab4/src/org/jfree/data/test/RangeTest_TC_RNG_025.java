package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_025 {

    private Range range;

    @Before
    public void setUp() {
        // Initialize the base range (5,10)
        range = new Range(5.0, 10.0);
    }

    @Test
    public void testIntersects_WithOverlappingRange() {
        // Range(5,10) intersects with Range(7,12) (overlapping case)
        assertTrue("Range(5,10) should intersect with Range(7,12)", 
                   range.intersects(7.0, 12.0));
    }

    @Test
    public void testIntersects_WithTouchingRange() {
        // Range(5,10) intersects with Range(10,15) (touching at boundary)
        assertTrue("Range(5,10) should intersect with Range(10,15)", 
                   range.intersects(10.0, 15.0));
    }

    @Test
    public void testIntersects_WithNonOverlappingRange() {
        // Range(5,10) does NOT intersect with Range(1,4) (non-overlapping case)
        assertFalse("Range(5,10) should NOT intersect with Range(1,4)", 
                    range.intersects(1.0, 4.0));
    }
}
