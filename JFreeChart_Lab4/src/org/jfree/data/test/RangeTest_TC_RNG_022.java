package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest_TC_RNG_022 {

    @Test
    public void testHashCode_ForSimilarRanges() {
        // Creating two similar but slightly varied Range objects
        Range range1 = new Range(5.0, 10.0);
        Range range2 = new Range(5.0001, 10.0001);

        // Generating hash codes
        int hash1 = range1.hashCode();
        int hash2 = range2.hashCode();

        // Print hash codes for debugging
        System.out.println("HashCode for Range(5,10): " + hash1);
        System.out.println("HashCode for Range(5.0001,10.0001): " + hash2);

        // Verify that slightly different ranges produce different hash codes
        assertTrue("Hash codes for similar but distinct ranges should be different", hash1 != hash2);
    }
}
