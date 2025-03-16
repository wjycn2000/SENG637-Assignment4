package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_032 {

    private Range largeRange;

    @Before
    public void setUp() {
        // Initialize a large range (-1e9, 1e9)
        largeRange = new Range(-1e9, 1e9);
    }

    @Test(timeout = 5000) // Ensure execution completes within 5 seconds
    public void testShift_WithExtremePositiveShift() {
        long startTime = System.nanoTime();

        // Shifting Range(-1e9,1e9) by 1e12 with allowZeroCrossing=true
        Range shiftedRange = Range.shift(largeRange, 1e12, true);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        // Expected new bounds
        double expectedLower = -1e9 + 1e12;
        double expectedUpper = 1e9 + 1e12;

        // Validate shifted range
        assertEquals("Shifted lower bound should be -1e9 + 1e12", expectedLower, shiftedRange.getLowerBound(), 0.000000001d);
        assertEquals("Shifted upper bound should be 1e9 + 1e12", expectedUpper, shiftedRange.getUpperBound(), 0.000000001d);

        // Print execution time for performance evaluation
        System.out.println("Execution time for extreme range shift: " + duration + " ms");

        // Ensure execution completes within acceptable limits
        assertTrue("Execution time should be within acceptable limits", duration < 5000);
    }
}
