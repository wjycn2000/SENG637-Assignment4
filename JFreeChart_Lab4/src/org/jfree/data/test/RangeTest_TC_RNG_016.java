package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_016 {

    private Range largeRange;

    @Before
    public void setUp() {
        // Initialize a large range
        largeRange = new Range(-1e9, 1e9);
    }

    @Test(timeout = 5000) // Ensure execution completes within 5 seconds
    public void testGetLowerBound_WithLargeRange() {
        long startTime = System.nanoTime();

        // Retrieve the lower bound
        double lowerBound = largeRange.getLowerBound();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convert to milliseconds

        // Expected lower bound is -1e9
        assertEquals("Lower bound of Range(-1e9,1e9) should be -1e9", 
                     -1e9, lowerBound, 0.000000001d);

        // Print execution time for performance evaluation
        System.out.println("Execution time for retrieving lower bound: " + duration + " ms");

        // Ensure execution completes within acceptable limits
        assertTrue("Execution time should be within acceptable limits", duration < 5000);
    }
}
