package org.jfree.data.test;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals; 
import org.jmock.*;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jfree.data.KeyedValues;
import java.lang.IllegalArgumentException;
import java.util.Arrays;
import org.jfree.data.Values2D;

public class DataUtilitiesTest extends DataUtilities {
	private final Mockery context = new JUnit4Mockery();
	
	/**
     * Test Case: Standard case with positive values.
     * Test Strategy: Normal case (ECP)
     * Expected: Correct cumulative percentages calculated.
     */
    @Test
    public void testGetCumulativePercentagesNormalCase() {
        KeyedValues mockData = context.mock(KeyedValues.class);

        context.checking(new Expectations() {{
            allowing(mockData).getItemCount(); will(returnValue(3));

            allowing(mockData).getKey(0); will(returnValue(0));
            allowing(mockData).getKey(1); will(returnValue(1));
            allowing(mockData).getKey(2); will(returnValue(2));

            allowing(mockData).getValue(0); will(returnValue(5));
            allowing(mockData).getValue(1); will(returnValue(9));
            allowing(mockData).getValue(2); will(returnValue(2));

            allowing(mockData).getKeys(); will(returnValue(Arrays.asList(0, 1, 2)));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.3125, result.getValue(0).doubleValue(), 0.0001);
        assertEquals(0.875, result.getValue(1).doubleValue(), 0.0001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0001);
    }

    /**
     * Test Case: Single-value dataset.
     * Test Strategy: Minimum input size (BVA)
     * Expected: The only value should have a cumulative percentage of 1.0.
     */
    @Test
    public void testGetCumulativePercentagesSingleValue() {
        KeyedValues mockData = context.mock(KeyedValues.class);

        context.checking(new Expectations() {{
            allowing(mockData).getItemCount(); will(returnValue(1));

            allowing(mockData).getKey(0); will(returnValue(0));
            allowing(mockData).getValue(0); will(returnValue(10));

            allowing(mockData).getKeys(); will(returnValue(Arrays.asList(0)));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(1.0, result.getValue(0).doubleValue(), 0.0001);
    }

    /**
     * Test Case: Values including zero.
     * Test Strategy: Handling zero (ECP)
     * Expected: Zero should not affect cumulative percentage calculations.
     */
    @Test
    public void testGetCumulativePercentagesWithZeroValue() {
        KeyedValues mockData = context.mock(KeyedValues.class);

        context.checking(new Expectations() {{
            allowing(mockData).getItemCount(); will(returnValue(3));

            allowing(mockData).getKey(0); will(returnValue(0));
            allowing(mockData).getKey(1); will(returnValue(1));
            allowing(mockData).getKey(2); will(returnValue(2));

            allowing(mockData).getValue(0); will(returnValue(5));
            allowing(mockData).getValue(1); will(returnValue(0));
            allowing(mockData).getValue(2); will(returnValue(5));

            allowing(mockData).getKeys(); will(returnValue(Arrays.asList(0, 1, 2)));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.5, result.getValue(0).doubleValue(), 0.0001);
        assertEquals(0.5, result.getValue(1).doubleValue(), 0.0001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0001);
    }

    /**
     * Test Case: Negative values.
     * Test Strategy: Handling negative numbers (ECP)
     * Expected: If allowed, cumulative percentages should work normally.
     */
    @Test
    public void testGetCumulativePercentagesWithNegativeValues() {
        KeyedValues mockData = context.mock(KeyedValues.class);

        context.checking(new Expectations() {{
            allowing(mockData).getItemCount(); will(returnValue(3));

            allowing(mockData).getKey(0); will(returnValue(0));
            allowing(mockData).getKey(1); will(returnValue(1));
            allowing(mockData).getKey(2); will(returnValue(2));

            allowing(mockData).getValue(0); will(returnValue(-3));
            allowing(mockData).getValue(1); will(returnValue(5));
            allowing(mockData).getValue(2); will(returnValue(4));

            allowing(mockData).getKeys(); will(returnValue(Arrays.asList(0, 1, 2)));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(-0.3, result.getValue(0).doubleValue(), 0.0001);
        assertEquals(0.2, result.getValue(1).doubleValue(), 0.0001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0001);
    }

    /**
     * Test Case: Null input.
     * Test Strategy: Invalid input (ECP)
     * Expected: The method should throw an InvalidParameterException.
     */
    @Test
    public void testGetCumulativePercentagesNullInput() {
        try {
            DataUtilities.getCumulativePercentages(null);
            fail("Expected InvalidParameterException to be thrown");
        } catch (IllegalArgumentException e) {
            // Expected behavior, test passes
        }
    }

    /**
     * Test Case: Empty dataset.
     * Test Strategy: Edge case (ECP)
     * Expected: The method should return an empty KeyedValues instance.
     */
    @Test
    public void testGetCumulativePercentagesEmptyDataset() {
        KeyedValues mockData = context.mock(KeyedValues.class);

        context.checking(new Expectations() {{
            allowing(mockData).getItemCount(); will(returnValue(0));
            allowing(mockData).getKeys(); will(returnValue(Arrays.asList()));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0, result.getItemCount());
    }

 /**
  * Test Case: Convert a valid array of doubles into a Number[] array.
  * Test Strategy: Equivalence Class Partitioning (ECP) - Normal case.
  * Expected Behavior: The method should correctly convert a double[] to Number[].
  */
   
 @Test
 public void testCreateNumberArray_ValidInput() {
     double[] input = {1.5, 2.5, 3.5};
     Number[] expected = {1.5, 2.5, 3.5};
     
     Number[] result = DataUtilities.createNumberArray(input);
     
     assertArrayEquals("The method should return an array of Number objects matching the input doubles.",
                       expected, result);
 }

 /**
  * Test Case: Convert a valid 2D array of doubles into a Number[][] array.
  * Test Strategy: Equivalence Class Partitioning (ECP) - Normal case.
  * Expected Behavior: The method should correctly convert a double[][] to Number[][].
  */
     
 @Test
 public void testCreateNumberArray2D_ValidInput() {
     double[][] input = {
         {1.1, 2.2, 3.3},
         {4.4, 5.5, 6.6}
     };

     Number[][] expected = {
         {1.1, 2.2, 3.3},
         {4.4, 5.5, 6.6}
     };

     Number[][] result = DataUtilities.createNumberArray2D(input);

     assertArrayEquals("The method should return a 2D array of Number objects matching the input doubles.",
                       expected, result);
 }
 
 // New test cases from Assignment 3
 
 //test cases for equal()
 /**
  * Tests the case where both input arrays are null.
  * Expected behavior: The method should return true since two null objects are considered equal.
  */
 @Test
 public void testEqualBothNull() {
     assertTrue(DataUtilities.equal(null, null));
 }

 /**
  * Tests the case where the first array is null and the second array is not.
  * Expected behavior: The method should return false since a null array cannot be equal to a non-null array.
  */
 @Test
 public void testEqualFirstNullSecondNotNull() {
     double[][] b = { {1.0, 2.0}, {3.0, 4.0} };
     assertFalse(DataUtilities.equal(null, b));
 }

 /**
  * Tests the case where the first array is not null, but the second array is null.
  * Expected behavior: The method should return false since a non-null array cannot be equal to a null array.
  */
 @Test
 public void testEqualFirstNotNullSecondNull() {
     double[][] a = { {1.0, 2.0}, {3.0, 4.0} };
     assertFalse(DataUtilities.equal(a, null));
 }

 /**
  * Tests the case where both arrays are empty.
  * Expected behavior: The method should return true since both arrays have zero rows and are structurally identical.
  */
 @Test
 public void testEqualBothEmptyArrays() {
     double[][] a = new double[0][0];
     double[][] b = new double[0][0];
     assertTrue(DataUtilities.equal(a, b));
 }

 /**
  * Tests the case where the two arrays have different numbers of rows.
  * Expected behavior: The method should return false since the dimensions do not match.
  */
 @Test
 public void testEqualDifferentRowLengths() {
     double[][] a = { {1.0, 2.0}, {3.0, 4.0} };
     double[][] b = { {1.0, 2.0} };
     assertFalse(DataUtilities.equal(a, b));
 }

 /**
  * Tests the case where both arrays have the same dimensions but contain different values.
  * Expected behavior: The method should return false since at least one element differs.
  */
 @Test
 public void testEqualDifferentValues() {
     double[][] a = { {1.0, 2.0}, {3.0, 4.0} };
     double[][] b = { {1.0, 2.0}, {3.0, 5.0} };
     assertFalse(DataUtilities.equal(a, b));
 }

 /**
  * Tests the case where both arrays are identical in structure and values.
  * Expected behavior: The method should return true since both arrays are exactly the same.
  */
 @Test
 public void testEqualIdenticalArrays() {
     double[][] a = { {1.0, 2.0}, {3.0, 4.0} };
     double[][] b = { {1.0, 2.0}, {3.0, 4.0} };
     assertTrue(DataUtilities.equal(a, b));
 }
 
 // test cases for clone()
 /**
  * Tests that passing a null input throws an IllegalArgumentException.
  */
 @Test(expected = IllegalArgumentException.class)
 public void testCloneNullInput() {
     DataUtilities.clone(null);
 }

 /**
  * Tests cloning an empty 2D array.
  * Expected behavior: Returns another empty array.
  */
 @Test
 public void testCloneEmptyArray() {
     double[][] source = new double[0][0];
     double[][] result = DataUtilities.clone(source);
     assertArrayEquals(source, result);
 }

 /**
  * Tests cloning a 2D array with a single element.
  * Expected behavior: The clone should be identical to the original.
  */
 @Test
 public void testCloneSingleElementArray() {
     double[][] source = {{42.0}};
     double[][] result = DataUtilities.clone(source);
     assertArrayEquals(source, result);
 }

 /**
  * Tests cloning a multi-dimensional 2D array.
  * Expected behavior: The clone should be identical to the original.
  */
 @Test
 public void testCloneMultiDimensionalArray() {
     double[][] source = {{1.1, 2.2}, {3.3, 4.4}};
     double[][] result = DataUtilities.clone(source);
     assertArrayEquals(source, result);
 }

 /**
  * Tests that cloning creates a deep copy.
  * Expected behavior: Changing the clone should not affect the original array.
  */
 @Test
 public void testCloneEnsuresDeepCopy() {
     double[][] source = {{1.1, 2.2}, {3.3, 4.4}};
     double[][] result = DataUtilities.clone(source);

     // Modify cloned array
     result[0][0] = 9.9;

     // Ensure original remains unchanged
     assertEquals(1.1, source[0][0], 0.00001);
 }

 /**
  * Tests cloning an array that contains null rows.
  * Expected behavior: The cloned array should also contain null rows at the same indices.
  */
 @Test
 public void testClonePreservesNullRows() {
     double[][] source = {{1.0, 2.0}, null, {3.0, 4.0}};
     double[][] result = DataUtilities.clone(source);
     
     assertNull(result[1]); // Second row should remain null
     assertArrayEquals(source[0], result[0], 0.00001);
     assertArrayEquals(source[2], result[2], 0.00001);
 }

 /**
  * Tests cloning an array that contains special floating-point values (NaN, Infinity).
  * Expected behavior: Special values should be preserved in the cloned array.
  */
 @Test
 public void testCloneHandlesSpecialValues() {
     double[][] source = {
         {Double.NaN, Double.POSITIVE_INFINITY},
         {Double.NEGATIVE_INFINITY, 0.0}
     };
     double[][] result = DataUtilities.clone(source);

     assertArrayEquals(source[0], result[0], 0.00001);
     assertArrayEquals(source[1], result[1], 0.00001);
 }
 
 //test cases for calculateColumnTotal()
 /**
  * Tests that passing a null data input throws an IllegalArgumentException.
  */
 @Test(expected = IllegalArgumentException.class)
 public void testCalculateColumnTotal_NullData() {
     DataUtilities.calculateColumnTotal(null, 0, new int[]{0, 1});
 }

 /**
  * Tests an empty validRows array.
  * Expected behavior: The method should return 0.0 since no rows are selected.
  */
 @Test
 public void testCalculateColumnTotal_EmptyValidRows() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getRowCount();
         will(returnValue(3));
     }});

     double result = DataUtilities.calculateColumnTotal(mockData, 0, new int[]{});
     assertEquals(0.0, result, 0.00001);
 }

 /**
  * Tests selecting valid rows from a multi-row dataset.
  * Expected behavior: The method should sum only the selected valid row indices.
  */
 @Test
 public void testCalculateColumnTotal_ValidRows() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getRowCount();
         will(returnValue(3));

         allowing(mockData).getValue(0, 0);
         will(returnValue(2.5));

         allowing(mockData).getValue(1, 0);
         will(returnValue(3.5));

         allowing(mockData).getValue(2, 0);
         will(returnValue(4.0));
     }});

     double result = DataUtilities.calculateColumnTotal(mockData, 0, new int[]{0, 2});
     assertEquals(6.5, result, 0.00001);
 }

 /**
  * Tests handling of invalid row indices (out of range).
  * Expected behavior: Rows that exceed rowCount should be ignored.
  */
 @Test
 public void testCalculateColumnTotal_OutOfRangeRows() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getRowCount();
         will(returnValue(2));

         allowing(mockData).getValue(0, 0);
         will(returnValue(2.0));

         allowing(mockData).getValue(1, 0);
         will(returnValue(3.0));
     }});

     double result = DataUtilities.calculateColumnTotal(mockData, 0, new int[]{0, 1, 5, 10});
     assertEquals(5.0, result, 0.00001);
 }

 /**
  * Tests handling of null values in the dataset.
  * Expected behavior: Null values should be ignored in the sum calculation.
  */
 @Test
 public void testCalculateColumnTotal_NullValues() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getRowCount();
         will(returnValue(3));

         allowing(mockData).getValue(0, 0);
         will(returnValue(2.0));

         allowing(mockData).getValue(1, 0);
         will(returnValue(null));

         allowing(mockData).getValue(2, 0);
         will(returnValue(4.0));
     }});

     double result = DataUtilities.calculateColumnTotal(mockData, 0, new int[]{0, 1, 2});
     assertEquals(6.0, result, 0.00001);
 }

 /**
  * Tests handling of negative values.
  * Expected behavior: The method should correctly sum negative numbers.
  */
 @Test
 public void testCalculateColumnTotal_NegativeValues() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getRowCount();
         will(returnValue(3));

         allowing(mockData).getValue(0, 0);
         will(returnValue(-2.5));

         allowing(mockData).getValue(1, 0);
         will(returnValue(-3.5));

         allowing(mockData).getValue(2, 0);
         will(returnValue(4.0));
     }});

     double result = DataUtilities.calculateColumnTotal(mockData, 0, new int[]{0, 1, 2});
     assertEquals(-2.0, result, 0.00001);
 }

 /**
  * Tests handling of floating-point precision.
  * Expected behavior: The method should correctly handle floating-point arithmetic.
  */
 @Test
 public void testCalculateColumnTotal_FloatingPointPrecision() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getRowCount();
         will(returnValue(2));

         allowing(mockData).getValue(0, 0);
         will(returnValue(0.1));

         allowing(mockData).getValue(1, 0);
         will(returnValue(0.2));
     }});

     double result = DataUtilities.calculateColumnTotal(mockData, 0, new int[]{0, 1});
     assertEquals(0.3, result, 0.00001);
 }
 
 //test cases for calcualteRowTotal()
 /**
  * Tests that passing a null data input throws an IllegalArgumentException.
  */
 @Test(expected = IllegalArgumentException.class)
 public void testCalculateRowTotal_NullData() {
     DataUtilities.calculateRowTotal(null, 0, new int[]{0, 1});
 }

 /**
  * Tests an empty validCols array.
  * Expected behavior: The method should return 0.0 since no columns are selected.
  */
 @Test
 public void testCalculateRowTotal_EmptyValidCols() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getColumnCount();
         will(returnValue(3));
     }});

     double result = DataUtilities.calculateRowTotal(mockData, 0, new int[]{});
     assertEquals(0.0, result, 0.00001);
 }

 /**
  * Tests selecting valid columns from a multi-column dataset.
  * Expected behavior: The method should sum only the selected valid column indices.
  */
 @Test
 public void testCalculateRowTotal_ValidColumns() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getColumnCount();
         will(returnValue(3));

         allowing(mockData).getValue(0, 0);
         will(returnValue(2.5));

         allowing(mockData).getValue(0, 1);
         will(returnValue(3.5));

         allowing(mockData).getValue(0, 2);
         will(returnValue(4.0));
     }});

     double result = DataUtilities.calculateRowTotal(mockData, 0, new int[]{0, 2});
     assertEquals(6.5, result, 0.00001);
 }

 /**
  * Tests handling of invalid column indices (out of range).
  * Expected behavior: Columns that exceed colCount should be ignored.
  */
 @Test
 public void testCalculateRowTotal_OutOfRangeColumns() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getColumnCount();
         will(returnValue(2));

         allowing(mockData).getValue(0, 0);
         will(returnValue(2.0));

         allowing(mockData).getValue(0, 1);
         will(returnValue(3.0));
     }});

     double result = DataUtilities.calculateRowTotal(mockData, 0, new int[]{0, 1, 5, 10});
     assertEquals(5.0, result, 0.00001);
 }

 /**
  * Tests handling of null values in the dataset.
  * Expected behavior: Null values should be ignored in the sum calculation.
  */
 @Test
 public void testCalculateRowTotal_NullValues() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getColumnCount();
         will(returnValue(3));

         allowing(mockData).getValue(0, 0);
         will(returnValue(2.0));

         allowing(mockData).getValue(0, 1);
         will(returnValue(null));

         allowing(mockData).getValue(0, 2);
         will(returnValue(4.0));
     }});

     double result = DataUtilities.calculateRowTotal(mockData, 0, new int[]{0, 1, 2});
     assertEquals(6.0, result, 0.00001);
 }

 /**
  * Tests handling of negative values.
  * Expected behavior: The method should correctly sum negative numbers.
  */
 @Test
 public void testCalculateRowTotal_NegativeValues() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getColumnCount();
         will(returnValue(3));

         allowing(mockData).getValue(0, 0);
         will(returnValue(-2.5));

         allowing(mockData).getValue(0, 1);
         will(returnValue(-3.5));

         allowing(mockData).getValue(0, 2);
         will(returnValue(4.0));
     }});

     double result = DataUtilities.calculateRowTotal(mockData, 0, new int[]{0, 1, 2});
     assertEquals(-2.0, result, 0.00001);
 }

 /**
  * Tests handling of floating-point precision.
  * Expected behavior: The method should correctly handle floating-point arithmetic.
  */
 @Test
 public void testCalculateRowTotal_FloatingPointPrecision() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getColumnCount();
         will(returnValue(2));

         allowing(mockData).getValue(0, 0);
         will(returnValue(0.1));

         allowing(mockData).getValue(0, 1);
         will(returnValue(0.2));
     }});

     double result = DataUtilities.calculateRowTotal(mockData, 0, new int[]{0, 1});
     assertEquals(0.3, result, 0.00001);
 }

 /**
  * Tests case where column count is negative (invalid condition).
  * Expected behavior: The method should return 0.0.
  */
 @Test
 public void testCalculateRowTotal_NegativeColumnCount() {
     final Values2D mockData = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         allowing(mockData).getColumnCount();
         will(returnValue(-1));
     }});

     double result = DataUtilities.calculateRowTotal(mockData, 0, new int[]{0, 1});
     assertEquals(0.0, result, 0.00001);
 }
}
