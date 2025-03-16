package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.junit.*;

public class DataUtilitiesTest_TC_DU_004 {

    private Values2D largeDataset;
    private static final int LARGE_DATASET_SIZE = 100000;
    private static final int COLUMN_INDEX = 3;
    private double expectedSum;

    @Before
    public void setUp() {
        // Initialize a stub implementation of Values2D
        largeDataset = new Values2D() {
            @Override
            public int getRowCount() {
                return LARGE_DATASET_SIZE;
            }

            @Override
            public int getColumnCount() {
                return 5; // Assume 5 columns exist
            }

            @Override
            public Number getValue(int row, int column) {
                return (column == COLUMN_INDEX) ? row + 1.0 : 0.0; // Column 3 has values 1 to 100000
            }
        };

        // Compute expected sum: Sum of first 100,000 numbers
        expectedSum = (LARGE_DATASET_SIZE * (LARGE_DATASET_SIZE + 1)) / 2.0;
    }

    @Test(timeout = 5000) // Ensure execution is within 5 seconds
    public void testCalculateColumnTotal_LargeDataset() {
        long startTime = System.nanoTime();

        // Call the method under test
        double result = DataUtilities.calculateColumnTotal(largeDataset, COLUMN_INDEX);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convert to milliseconds

        // Verify the sum
        assertEquals("The sum of column 3 should match the expected sum", expectedSum, result, 0.000000001d);

        // Print execution time
        System.out.println("Execution time for large dataset: " + duration + " ms");

        // Ensure execution completes within 5 seconds
        assertTrue("Execution time should be within acceptable limits", duration < 5000);
    }
}
