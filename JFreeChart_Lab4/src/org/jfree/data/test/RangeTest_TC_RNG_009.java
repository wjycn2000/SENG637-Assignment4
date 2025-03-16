package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_009 {

    private Range originalRange;

    @Before
    public void setUp() {
        // Initialize the original range
        originalRange = new Range(5.0, 10.0);
    }

    @Test
    public void testExpandToInclude_WithInRangeValue() {
        // Expanding to include 7 (already within the range, so should remain the same)
        Range newRange = Range.expandToInclude(originalRange, 7.0);

        // The range should remain unchanged
        assertEquals("Lower bound should remain 5.0", 5.0, newRange.getLowerBound(), 0.000000001d);
        assertEquals("Upper bound should remain 10.0", 10.0, newRange.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testExpandToInclude_WithOutOfRangeValue() {
        // Expanding to include 12 (outside the upper bound, should extend range)
        Range expandedRange = Range.expandToInclude(originalRange, 12.0);

        // Expected bounds: Lower bound remains the same (5.0), Upper bound extends to 12.0
        assertEquals("Lower bound should remain 5.0", 5.0, expandedRange.getLowerBound(), 0.000000001d);
        assertEquals("Upper bound should be extended to 12.0", 12.0, expandedRange.getUpperBound(), 0.000000001d);
    }
}
