package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.junit.*;

public class DataUtilitiesTest_TC_DU_008 {

    private Values2D largeDataset;
    private static final int LARGE_DATASET_SIZE = 100000;
    private static final int ROW_INDEX = 3;
    private double expectedSum;

    @Before
    public void setUp() {
        // Initialize a stub implementation of Values2D
        largeDataset = new Values2D() {
            @Override
            public int getRowCount() {
                return 5; // Assume 5 rows exist
            }

            @Override
            public int getColumnCount() {
                return LARGE_DATASET_SIZE;
            }

            @Override
            public Number getValue(int row, int column) {
                return (row == ROW_INDEX) ? column + 1.0 : 0.0; // Row 3 has values 1 to 100000
            }
        };

        // Compute expected sum: Sum of first 100,000 numbers
        expectedSum = (LARGE_DATASET_SIZE * (LARGE_DATASET_SIZE + 1)) / 2.0;
    }

    @Test(timeout = 5000) // Ensure execution is within 5 seconds
    public void testCalculateRowTotal_LargeDataset() {
        long startTime = System.nanoTime();

        // Call the method under test
        double result = DataUtilities.calculateRowTotal(largeDataset, ROW_INDEX);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convert to milliseconds

        // Verify the sum
        assertEquals("The sum of row 3 should match the expected sum", expectedSum, result, 0.000000001d);

        // Print execution time
        System.out.println("Execution time for large dataset: " + duration + " ms");

        // Ensure execution completes within 5 seconds
        assertTrue("Execution time should be within acceptable limits", duration < 5000);
    }
}
