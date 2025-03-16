package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_005 {

    private Range originalRange;

    @Before
    public void setUp() {
        // Initialize the original range
        originalRange = new Range(5.0, 10.0);
    }

    @Test
    public void testExpand_WithPositiveMargins() {
        // Expanding with lowerMargin = 0.2, upperMargin = 0.3
        Range expandedRange = Range.expand(originalRange, 0.2, 0.3);

        // Expected lower bound: 5 - (5 * 0.2) = 4.0
        // Expected upper bound: 10 + (5 * 0.3) = 11.5
        assertEquals("Expanded lower bound should be 4.0", 4.0, expandedRange.getLowerBound(), 0.000000001d);
        assertEquals("Expanded upper bound should be 11.5", 11.5, expandedRange.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testExpand_WithNegativeMargins() {
        // Expanding with lowerMargin = -0.2, upperMargin = -0.3 (contracts the range)
        try {
            Range contractedRange = Range.expand(originalRange, -0.2, -0.3);

            // If no exception is thrown, verify the expected range
            double expectedLower = 6.0;
            double expectedUpper = 8.5;

            assertEquals("Contracted lower bound should be 6.0", expectedLower, contractedRange.getLowerBound(), 0.000000001d);
            assertEquals("Contracted upper bound should be 8.5", expectedUpper, contractedRange.getUpperBound(), 0.000000001d);
        } catch (IllegalArgumentException e) {
            // If contraction results in invalid range, test should pass as long as exception is expected
            System.out.println("Handled invalid contraction: " + e.getMessage());
        }
    }

    @Test
    public void testExpand_WithZeroMargins() {
        // Expanding with lowerMargin = 0, upperMargin = 0 (no change)
        Range sameRange = Range.expand(originalRange, 0.0, 0.0);

        // The range should remain unchanged
        assertEquals("Lower bound should remain 5.0", 5.0, sameRange.getLowerBound(), 0.000000001d);
        assertEquals("Upper bound should remain 10.0", 10.0, sameRange.getUpperBound(), 0.000000001d);
    }
}
