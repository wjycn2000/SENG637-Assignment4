package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.junit.*;

public class DataUtilitiesTest_TC_DU_005 {

    private Mockery mockingContext;
    private Values2D values;

    @Before
    public void setUp() {
        // Initialize mocking context
        mockingContext = new Mockery();
        values = mockingContext.mock(Values2D.class);

        // Define expectations for the mock object
        mockingContext.checking(new Expectations() {{
            oneOf(values).getColumnCount();
            will(returnValue(3)); // Assume 3 columns in the dataset

            oneOf(values).getValue(1, 0);
            will(returnValue(4.5));

            oneOf(values).getValue(1, 1);
            will(returnValue(3.5));

            oneOf(values).getValue(1, 2);
            will(returnValue(2.0));
        }});
    }

    @Test
    public void testCalculateRowTotal_ValidData() {
        // Expected sum of row 1: 4.5 + 3.5 + 2.0 = 10.0
        double expectedSum = 10.0;
        double result = DataUtilities.calculateRowTotal(values, 1);

        // Verify the result
        assertEquals("The sum of row 1 should be 10.0", expectedSum, result, 0.000000001d);
    }
}
