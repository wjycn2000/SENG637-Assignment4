package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_008 {

    private Range largeRange;

    @Before
    public void setUp() {
        // Initialize a very large range
        largeRange = new Range(-1e9, 1e9);
    }

    @Test(timeout = 5000) // Ensure execution completes within 5 seconds
    public void testExpand_WithLargeRangeValues() {
        long startTime = System.nanoTime();

        // Expanding with lowerMargin = 0.5, upperMargin = 0.5
        Range expandedRange = Range.expand(largeRange, 0.5, 0.5);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convert to milliseconds

        // Expected lower bound: -1e9 - (2e9 * 0.5) = -2e9
        // Expected upper bound: 1e9 + (2e9 * 0.5) = 2e9
        assertEquals("Expanded lower bound should be -2e9", -2e9, expandedRange.getLowerBound(), 0.000000001d);
        assertEquals("Expanded upper bound should be 2e9", 2e9, expandedRange.getUpperBound(), 0.000000001d);

        // Print execution time for performance evaluation
        System.out.println("Execution time for large range expansion: " + duration + " ms");

        // Ensure execution completes within acceptable limits
        assertTrue("Execution time should be within acceptable limits", duration < 5000);
    }
}
