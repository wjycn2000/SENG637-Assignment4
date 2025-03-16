package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_010 {

    private Range originalRange;

    @Before
    public void setUp() {
        // Initialize the original range
        originalRange = new Range(5.0, 10.0);
    }

    @Test
    public void testExpandToInclude_AtLowerBound() {
        // Expanding to include the lower bound (5), which should not change the range
        Range newRange = Range.expandToInclude(originalRange, 5.0);

        // Verify that the range remains unchanged
        assertEquals("Lower bound should remain 5.0", 5.0, newRange.getLowerBound(), 0.000000001d);
        assertEquals("Upper bound should remain 10.0", 10.0, newRange.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testExpandToInclude_AtUpperBound() {
        // Expanding to include the upper bound (10), which should not change the range
        Range newRange = Range.expandToInclude(originalRange, 10.0);

        // Verify that the range remains unchanged
        assertEquals("Lower bound should remain 5.0", 5.0, newRange.getLowerBound(), 0.000000001d);
        assertEquals("Upper bound should remain 10.0", 10.0, newRange.getUpperBound(), 0.000000001d);
    }
}
