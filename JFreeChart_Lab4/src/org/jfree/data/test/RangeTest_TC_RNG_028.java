package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_028 {

    private Range largeRange;

    @Before
    public void setUp() {
        // Initialize a large range
        largeRange = new Range(-1e9, 1e9);
    }

    @Test(timeout = 5000) // Ensure execution completes within 5 seconds
    public void testIntersects_WithLargeRangeSet() {
        long startTime = System.nanoTime();

        // Checking if Range(-1e9,1e9) intersects with Range(0,1e9+1)
        boolean result = largeRange.intersects(0.0, 1e9 + 1);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        // Expected result: true (since 0 to 1e9+1 overlaps with -1e9 to 1e9)
        assertTrue("Range(-1e9,1e9) should intersect with Range(0,1e9+1)", result);

        // Print execution time for performance evaluation
        System.out.println("Execution time for large range intersection check: " + duration + " ms");

        // Ensure execution completes within acceptable limits
        assertTrue("Execution time should be within acceptable limits", duration < 5000);
    }
}
