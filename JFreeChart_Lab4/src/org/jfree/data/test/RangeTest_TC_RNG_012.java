package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_012 {

    private Range largeRange;

    @Before
    public void setUp() {
        // Initialize a very large range
        largeRange = new Range(-1e9, 1e9);
    }

    @Test(timeout = 5000) // Ensure execution completes within 5 seconds
    public void testExpandToInclude_WithExtremeUpperBound() {
        long startTime = System.nanoTime();

        // Expanding to include 1e12
        Range expandedRange = Range.expandToInclude(largeRange, 1e12);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convert to milliseconds

        // Expected bounds: Lower bound remains the same (-1e9), Upper bound extends to 1e12
        assertEquals("Lower bound should remain -1e9", -1e9, expandedRange.getLowerBound(), 0.000000001d);
        assertEquals("Upper bound should be extended to 1e12", 1e12, expandedRange.getUpperBound(), 0.000000001d);

        // Print execution time for performance evaluation
        System.out.println("Execution time for large range expansion: " + duration + " ms");

        // Ensure execution completes within acceptable limits
        assertTrue("Execution time should be within acceptable limits", duration < 5000);
    }
}
