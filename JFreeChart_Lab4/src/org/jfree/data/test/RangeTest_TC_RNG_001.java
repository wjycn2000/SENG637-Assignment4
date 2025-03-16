package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_001 {

    private Range range;

    @Before
    public void setUp() {
        // Initialize a Range object
        range = new Range(5.0, 10.0);
    }

    @Test
    public void testEquals_WithDifferentObjectTypes() {
        // Comparing with a String object
        String stringObject = "Not a Range";
        assertFalse("Comparing Range with a String should return false", range.equals(stringObject));

        // Comparing with an Integer object
        Integer integerObject = 100;
        assertFalse("Comparing Range with an Integer should return false", range.equals(integerObject));
    }
}
