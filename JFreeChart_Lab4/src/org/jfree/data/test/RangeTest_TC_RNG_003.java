package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_003 {

    private Range range;

    @Before
    public void setUp() {
        // Initialize a Range object
        range = new Range(5.0, 10.0);
    }

    @Test
    public void testEquals_WithNullObject() {
        // Verify that equals() returns false when compared with null
        assertFalse("Comparing Range with null should return false", range.equals(null));
    }
}
