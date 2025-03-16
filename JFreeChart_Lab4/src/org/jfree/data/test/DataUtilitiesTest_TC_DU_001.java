package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.junit.*;

public class DataUtilitiesTest_TC_DU_001 {

    private Mockery mockingContext;
    private Values2D values;

    @Before
    public void setUp() {
        // Initialize mocking context
        mockingContext = new Mockery();
        values = mockingContext.mock(Values2D.class);

        // Define expectations for the mock object
        mockingContext.checking(new Expectations() {{
            oneOf(values).getRowCount();
            will(returnValue(3));

            oneOf(values).getValue(0, 1);
            will(returnValue(5.0));

            oneOf(values).getValue(1, 1);
            will(returnValue(10.0));

            oneOf(values).getValue(2, 1);
            will(returnValue(15.0));
        }});
    }

    @Test
    public void testCalculateColumnTotal_ValidData() {
        // Expected sum of column 1: 5.0 + 10.0 + 15.0 = 30.0
        double expectedSum = 30.0;
        double result = DataUtilities.calculateColumnTotal(values, 1);

        // Verify the result
        assertEquals("The sum of column 1 should be 30.0", expectedSum, result, 0.000000001d);
    }
}
