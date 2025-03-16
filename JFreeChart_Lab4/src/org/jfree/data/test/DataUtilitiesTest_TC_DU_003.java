package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.junit.*;

public class DataUtilitiesTest_TC_DU_003 {

    private Mockery mockingContext;
    private Values2D values;

    @Before
    public void setUp() {
        // Initialize mocking context
        mockingContext = new Mockery();
        values = mockingContext.mock(Values2D.class);

        // Define expectations for a dataset containing null values
        mockingContext.checking(new Expectations() {{
            oneOf(values).getRowCount();
            will(returnValue(4)); // Dataset has 4 rows

            oneOf(values).getValue(0, 2);
            will(returnValue(10.0)); // Valid number

            oneOf(values).getValue(1, 2);
            will(returnValue(null)); // Null value (should be skipped)

            oneOf(values).getValue(2, 2);
            will(returnValue(5.5)); // Valid number

            oneOf(values).getValue(3, 2);
            will(returnValue(null)); // Another null value (should be skipped)
        }});
    }

    @Test
    public void testCalculateColumnTotal_WithNullValues() {
        // Expected sum: 10.0 + 5.5 = 15.5 (nulls are ignored)
        double expectedSum = 15.5;
        double result = DataUtilities.calculateColumnTotal(values, 2);

        // Verify the result
        assertEquals("The sum of column 2 should exclude null values and be 15.5", expectedSum, result, 0.000000001d);
    }
}
