package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RangeTest_TC_RNG_024 {

    private static final int LARGE_DATASET_SIZE = 100000;

    @Test(timeout = 10000) // Ensure execution completes within 10 seconds
    public void testHashCode_WithLargeDataset() {
        long startTime = System.nanoTime();

        Set<Integer> hashCodes = new HashSet<>();
        Map<Integer, Integer> collisionCount = new HashMap<>();
        boolean hasCollision = false;

        for (int i = 0; i < LARGE_DATASET_SIZE; i++) {
            Range range = new Range(i, i + 10.0);
            int hash = range.hashCode();

            // Track collisions
            if (!hashCodes.add(hash)) {
                hasCollision = true;
                collisionCount.put(hash, collisionCount.getOrDefault(hash, 0) + 1);
            }
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convert to milliseconds

        // Print execution time for performance evaluation
        System.out.println("Execution time for generating 100,000 hash codes: " + duration + " ms");

        // Print collision details
        if (hasCollision) {
            System.out.println("Total Collisions: " + collisionCount.size());
            System.out.println("Hash codes with multiple occurrences: " + collisionCount);
        }

        // Assertions
        assertFalse("Hash codes should be unique across large datasets", hasCollision);
        assertTrue("Execution time should be within acceptable limits", duration < 10000);
    }
}
