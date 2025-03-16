package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.junit.*;

public class DataUtilitiesTest_TC_DU_007 {

    private Mockery mockingContext;
    private Values2D values;

    @Before
    public void setUp() {
        // Initialize mocking context
        mockingContext = new Mockery();
        values = mockingContext.mock(Values2D.class);

        // Define expectations for a dataset containing null values
        mockingContext.checking(new Expectations() {{
            oneOf(values).getColumnCount();
            will(returnValue(4)); // Assume 4 columns in the dataset

            oneOf(values).getValue(2, 0);
            will(returnValue(8.0)); // Valid number

            oneOf(values).getValue(2, 1);
            will(returnValue(null)); // Null value (should be skipped)

            oneOf(values).getValue(2, 2);
            will(returnValue(5.0)); // Valid number

            oneOf(values).getValue(2, 3);
            will(returnValue(null)); // Another null value (should be skipped)
        }});
    }

    @Test
    public void testCalculateRowTotal_WithNullValues() {
        // Expected sum: 8.0 + 5.0 = 13.0 (nulls are ignored)
        double expectedSum = 13.0;
        double result = DataUtilities.calculateRowTotal(values, 2);

        // Verify the result
        assertEquals("The sum of row 2 should exclude null values and be 13.0", expectedSum, result, 0.000000001d);
    }
}
