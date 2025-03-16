package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_020 {

    private Range largeRange;

    @Before
    public void setUp() {
        // Initialize a large range
        largeRange = new Range(-1e9, 1e9);
    }

    @Test(timeout = 5000) // Ensure execution completes within 5 seconds
    public void testGetUpperBound_WithLargeRange() {
        long startTime = System.nanoTime();

        // Retrieve the upper bound
        double upperBound = largeRange.getUpperBound();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convert to milliseconds

        // Expected upper bound is 1e9
        assertEquals("Upper bound of Range(-1e9,1e9) should be 1e9", 
                     1e9, upperBound, 0.000000001d);

        // Print execution time for performance evaluation
        System.out.println("Execution time for retrieving upper bound: " + duration + " ms");

        // Ensure execution completes within acceptable limits
        assertTrue("Execution time should be within acceptable limits", duration < 5000);
    }
}
