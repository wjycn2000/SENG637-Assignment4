package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_021 {

    @Test
    public void testHashCode_ForDifferentRanges() {
        // Creating different Range objects
        Range range1 = new Range(5.0, 10.0);
        Range range2 = new Range(-5.0, 5.0);
        Range range3 = new Range(0.0, 0.0);

        // Generating hash codes
        int hash1 = range1.hashCode();
        int hash2 = range2.hashCode();
        int hash3 = range3.hashCode();

        // Print hash codes for debugging
        System.out.println("HashCode for Range(5,10): " + hash1);
        System.out.println("HashCode for Range(-5,5): " + hash2);
        System.out.println("HashCode for Range(0,0): " + hash3);

        // Verify that each unique range produces a distinct hash code
        assertTrue("Hash codes for different ranges should be unique", hash1 != hash2);
        assertTrue("Hash codes for different ranges should be unique", hash1 != hash3);
        assertTrue("Hash codes for different ranges should be unique", hash2 != hash3);
    }
}
