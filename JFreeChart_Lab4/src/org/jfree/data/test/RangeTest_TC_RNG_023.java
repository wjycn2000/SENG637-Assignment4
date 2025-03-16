package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_023 {

    @Test
    public void testHashCode_WithExtremeValues() {
        // Creating Range objects with extreme values
        Range range1 = new Range(Double.MIN_VALUE, Double.MAX_VALUE);
        Range range2 = new Range(-Double.MAX_VALUE, Double.MAX_VALUE);

        // Generating hash codes
        int hash1 = range1.hashCode();
        int hash2 = range2.hashCode();

        // Print hash codes for debugging
        System.out.println("HashCode for Range(Double.MIN_VALUE, Double.MAX_VALUE): " + hash1);
        System.out.println("HashCode for Range(-Double.MAX_VALUE, Double.MAX_VALUE): " + hash2);

        // Ensure that the method does not fail and returns valid hash codes
        assertNotNull("Hash code for extreme range should not be null", hash1);
        assertNotNull("Hash code for extreme range should not be null", hash2);

        // Verify that hash codes are generated and ideally should be distinct
        assertTrue("Hash codes for extreme ranges should be different", hash1 != hash2);
    }
}
