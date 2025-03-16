package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_011 {

    private Range validRange;

    @Before
    public void setUp() {
        // Initialize a valid range
        validRange = new Range(5.0, 10.0);
    }

    @Test
    public void testExpandToInclude_WithNullRange() {
        // Expanding a null range should return a new range with the single included value
        Range newRange = Range.expandToInclude(null, 8.0);

        // The expected range should be (8.0, 8.0)
        assertNotNull("Expanding a null range should return a new Range object", newRange);
        assertEquals("Lower bound should be 8.0", 8.0, newRange.getLowerBound(), 0.000000001d);
        assertEquals("Upper bound should be 8.0", 8.0, newRange.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testExpandToInclude_WithExtremeLargeValue() {
        // Expanding range to include a very large value (1e12)
        Range expandedRange = Range.expandToInclude(validRange, 1e12);

        // Expected bounds: Lower bound remains the same (5.0), Upper bound extends to 1e12
        assertEquals("Lower bound should remain 5.0", 5.0, expandedRange.getLowerBound(), 0.000000001d);
        assertEquals("Upper bound should be extended to 1e12", 1e12, expandedRange.getUpperBound(), 0.000000001d);
    }

    @Test
    public void testExpandToInclude_WithExtremeSmallValue() {
        // Expanding range to include a very small value (-1e12)
        Range expandedRange = Range.expandToInclude(validRange, -1e12);

        // Expected bounds: Lower bound extends to -1e12, Upper bound remains the same (10.0)
        assertEquals("Lower bound should be extended to -1e12", -1e12, expandedRange.getLowerBound(), 0.000000001d);
        assertEquals("Upper bound should remain 10.0", 10.0, expandedRange.getUpperBound(), 0.000000001d);
    }
}
