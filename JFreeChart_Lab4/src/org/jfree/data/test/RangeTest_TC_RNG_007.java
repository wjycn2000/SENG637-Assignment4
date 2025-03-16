package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_007 {

    private Range validRange;

    @Before
    public void setUp() {
        // Initialize a valid range
        validRange = new Range(5.0, 10.0);
    }

    @Test
    public void testExpand_WithNullRange() {
        // Verify that expanding a null range throws an exception
        try {
            Range.expand(null, 0.2, 0.3);
            fail("Expanding a null range should throw an IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            System.out.println("Handled null range expansion: " + e.getMessage());
        }
    }

    @Test
    public void testExpand_WithExtremeMargins() {
        // Expanding with extremely large margins
        Range expandedRange = Range.expand(validRange, 100.0, 100.0);

        // Expected lower bound: 5 - (5 * 100) = -495
        // Expected upper bound: 10 + (5 * 100) = 510
        assertEquals("Expanded lower bound should be -495", -495.0, expandedRange.getLowerBound(), 0.000000001d);
        assertEquals("Expanded upper bound should be 510", 510.0, expandedRange.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testExpand_WithExtremeNegativeMargins() {
        // Expanding with extremely negative margins (may collapse to a single point or throw exception)
        try {
            Range contractedRange = Range.expand(validRange, -10.0, -10.0);

            // If no exception is thrown, verify the expected range
            double expectedLower = (validRange.getLowerBound() + validRange.getUpperBound()) / 2;
            double expectedUpper = expectedLower;

            assertEquals("Contracted lower bound should match expected midpoint", expectedLower, contractedRange.getLowerBound(), 0.000000001d);
            assertEquals("Contracted upper bound should match expected midpoint", expectedUpper, contractedRange.getUpperBound(), 0.000000001d);
        } catch (IllegalArgumentException e) {
            System.out.println("Handled extreme negative margin contraction: " + e.getMessage());
        }
    }
}
