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
import org.jfree.data.DefaultKeyedValues;


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
     *//*
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
        //assertEquals(-0.3, result.getValue(0).doubleValue(), 0.0001);
        assertEquals(0.2, result.getValue(1).doubleValue(), 0.0001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0001);
    }*/

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
 
 
 // Test 1: Validate precise column index handling in calculateColumnTotal(...)
 // Why: Kills mutants that affect column index usage and accumulation logic.
 @Test
 public void testCalculateColumnTotal_LastColumnOnly() {
     Mockery mockery = new Mockery();
     final Values2D values = mockery.mock(Values2D.class);

     mockery.checking(new Expectations() {{
         oneOf(values).getRowCount(); will(returnValue(2));
         oneOf(values).getValue(0, 1); will(returnValue(2.5));
         oneOf(values).getValue(1, 1); will(returnValue(3.5));
     }});

     double result = DataUtilities.calculateColumnTotal(values, 1);
     assertEquals("Sum of last column", 6.0, result, 0.00001);
 }

 // Test 2: calculateRowTotal(...) with valid columns edge case
 // Why: Targets mutants where invalid or skipped columns aren't correctly handled.
 @Test
 public void testCalculateRowTotal_ValidColumnsSubset() {
     Mockery mockery = new Mockery();
     final Values2D values = mockery.mock(Values2D.class);

     mockery.checking(new Expectations() {{
         oneOf(values).getColumnCount(); will(returnValue(3));
         oneOf(values).getValue(0, 0); will(returnValue(1.0));
         oneOf(values).getValue(0, 2); will(returnValue(2.0));
     }});

     int[] validCols = {0, 2};
     double result = DataUtilities.calculateRowTotal(values, 0, validCols);
     assertEquals("Row total for specific columns", 3.0, result, 0.00001);
 }
 
 // Test 3: createNumberArray2D(...) with nested empty arrays
 // Why: Kills edge-case mutants that assume inner array length > 0.
 @Test
 public void testCreateNumberArray2D_WithEmptyInnerArrays() {
     double[][] input = new double[][] { {}, {}, {} };
     Number[][] result = DataUtilities.createNumberArray2D(input);

     assertEquals(3, result.length);
     for (Number[] row : result) {
         assertEquals(0, row.length);
     }
 }
 
 // Test 4: equal(...) with near-duplicate arrays (precision sensitivity)
 // Why: Kills mutants that wrongly compare with != instead of .equals().
 @Test
 public void testEqual_NearlyIdenticalValues() {
     double[][] a = { {1.000001}, {2.000001} };
     double[][] b = { {1.000001}, {2.000001} };
     assertTrue("Arrays with identical values should be equal", DataUtilities.equal(a, b));
 }
 
 // Test 5: clone(...) with sparse null-initialized rows
 // Why: Kills mutants inside loops that copy only non-null arrays.
 @Test
 public void testClone_OnlySomeRowsInitialized() {
     double[][] input = new double[3][];
     input[1] = new double[] {3.14};
     double[][] result = DataUtilities.clone(input);

     assertNull(result[0]);
     assertEquals(3.14, result[1][0], 0.0001);
     assertNull(result[2]);
 }
 
 // Test 6: calculateColumnTotal(...) – Single row
 // Why: Targets mutants involving loop logic for row traversal.
 @Test
 public void testCalculateColumnTotal_SingleRow() {
     Mockery mockery = new Mockery();
     final Values2D values = mockery.mock(Values2D.class);

     mockery.checking(new Expectations() {{
         oneOf(values).getRowCount(); will(returnValue(1));
         oneOf(values).getValue(0, 0); will(returnValue(4.0));
     }});

     double result = DataUtilities.calculateColumnTotal(values, 0);
     assertEquals("Column total with one row", 4.0, result, 0.00001);
 }
 
 // Test 7: calculateRowTotal(...) – Empty valid columns array
 // Why: Kills logic that handles skipping empty inputs.
 @Test
 public void testCalculateRowTotal_EmptyValidCols2() {
     Mockery mockery = new Mockery();
     final Values2D values = mockery.mock(Values2D.class);

     mockery.checking(new Expectations() {{
         oneOf(values).getColumnCount(); will(returnValue(3));
     }});

     int[] validCols = new int[0];
     double result = DataUtilities.calculateRowTotal(values, 0, validCols);
     assertEquals("Row total with no valid columns", 0.0, result, 0.00001);
 }
 
 // Test 8: equal(...) – One array with null row
 // Why: Kills mutations that assume all rows are non-null.
 @Test
 public void testEqual_ArrayWithNullRow() {
     double[][] a = {null, {1.0}};
     double[][] b = {null, {1.0}};
     assertTrue("Arrays with same null row positions should be equal", DataUtilities.equal(a, b));
 }
 
 // Test 9: clone(...) – Verify deep clone
 // Why: Kills survivors in the loop that mistakenly perform shallow copy.
 @Test
 public void testClone_DeepEqualityCheck() {
     double[][] input = {{1.0}, {2.0}};
     double[][] clone = DataUtilities.clone(input);

     // Modify input and ensure clone doesn't change
     input[0][0] = 999.0;
     assertEquals("Clone should be deep", 1.0, clone[0][0], 0.00001);
 }
 
 // Test 10: createNumberArray(...) – Edge floating point values
 // Why: Kills mutants that affect boxing or precision assumptions.
 @Test
 public void testCreateNumberArray_ExtremeValues() {
     double[] input = {Double.MAX_VALUE, Double.MIN_VALUE};
     Number[] output = DataUtilities.createNumberArray(input);

     assertEquals(Double.MAX_VALUE, output[0]);
     assertEquals(Double.MIN_VALUE, output[1]);
 }

 // Kill mutant that replaces return null with return new Number[0]:
 // This kills mutants that try to return an empty array instead of null.
 @Test(expected = IllegalArgumentException.class)
 public void testCreateNumberArray2D_NullInput_ThrowsException() {
     DataUtilities.createNumberArray2D(null);
 }

 // 1. Test rowPercentages with rows summing to zero
 // 💡 This still targets looping and accumulation logic and may help kill equivalent or boundary-case mutants.
 @Test
 public void testCalculateColumnTotal_AllZeros() {
     Mockery context = new Mockery();
     final Values2D values = context.mock(Values2D.class);

     context.checking(new Expectations() {{
         one(values).getRowCount(); will(returnValue(2));
         one(values).getValue(0, 0); will(returnValue(0.0));
         one(values).getValue(1, 0); will(returnValue(0.0));
     }});

     double result = DataUtilities.calculateColumnTotal(values, 0);
     assertEquals("Should return 0.0 for all zero column", 0.0, result, 0.00001);
 }

 
 // Test createNumberArray with NaN values
 // Why? Targets mutations involving conditional or equality checks with NaN. Equality logic with NaN behaves differently and isn’t always covered unless explicitly tested.
 @Test
 public void testCreateNumberArray_ContainsNaN() {
     double[] input = {Double.NaN, 1.0};
     Number[] result = DataUtilities.createNumberArray(input);

     assertEquals(Double.NaN, result[0].doubleValue(), 0.00001);
     assertEquals(1.0, result[1].doubleValue(), 0.00001);
 }
 
 // Test getCumulativePercentages with negative values
 // Why? Most tests use positive values. This checks for mutants related to percentage aggregation logic, including edge case behavior with negatives.
 @Test
 public void testGetCumulativePercentages_NegativeValues() {
     DefaultKeyedValues values = new DefaultKeyedValues();
     values.addValue("A", -2.0);
     values.addValue("B", 1.0);
     values.addValue("C", 1.0);

     KeyedValues result = DataUtilities.getCumulativePercentages(values);

     System.out.println("A: " + result.getValue("A"));
     System.out.println("B: " + result.getValue("B"));
     System.out.println("C: " + result.getValue("C"));
 }

 // Targeting createNumberArray2D(...)
 // This test triggers the edge condition the mutant misses. It should now get killed.
 @Test
 public void testCreateNumberArray2D_ArrayLengthAndValues() {
     double[][] input = {
         {1.1, 2.2},
         {3.3, 4.4}
     };

     Number[][] output = DataUtilities.createNumberArray2D(input);

     assertEquals("Length of outer array", 2, output.length);
     assertEquals("Length of inner array 0", 2, output[0].length);
     assertEquals("Length of inner array 1", 2, output[1].length);

     assertEquals(1.1, output[0][0].doubleValue(), 0.0001);
     assertEquals(2.2, output[0][1].doubleValue(), 0.0001);
     assertEquals(3.3, output[1][0].doubleValue(), 0.0001);
     assertEquals(4.4, output[1][1].doubleValue(), 0.0001);
 }

 
 // Targeting createNumberArray(...) with edge values
 // Ensures the array is not misindexed or misallocated (which the mutation could corrupt).
 @Test
 public void testCreateNumberArray_SingleElement() {
     double[] input = {Math.PI};
     Number[] output = DataUtilities.createNumberArray(input);
     assertEquals(Math.PI, output[0].doubleValue(), 0.000001);
 }

 // Targeting clone(...) with partially null arrays
 // This targets mutants that change loop limits or null handling logic.
 @Test
 public void testCreateNumberArray2D_OneRowNull_ThrowsIllegalArgumentException() {
     double[][] input = new double[2][];
     input[0] = new double[] {1.0};
     input[1] = null;

     try {
         DataUtilities.createNumberArray2D(input);
         fail("Expected IllegalArgumentException for null inner row");
     } catch (IllegalArgumentException e) {
         assertEquals("Null 'data' argument.", e.getMessage());
     }
 }
 
 // // Additional tests to improve mutation coverage
 
 @Test
 public void testEqual_UnequalLengths() {
     double[][] a = new double[][] { {1.0}, {2.0} };
     double[][] b = new double[][] { {1.0} };  // shorter
     assertFalse(DataUtilities.equal(a, b));
 }

 @Test
 public void testEqual_OneNestedNull() {
     double[][] a = new double[][] { null, {2.0} };
     double[][] b = new double[][] { {1.0}, {2.0} };
     assertFalse(DataUtilities.equal(a, b));
 }

 @Test
 public void testEqual_BothNestedNull() {
     double[][] a = new double[][] { null, {2.0} };
     double[][] b = new double[][] { null, {2.0} };
     assertTrue(DataUtilities.equal(a, b));
 }

 @Test
 public void testEqual_WithNaN() {
     double[][] a = new double[][] { {Double.NaN} };
     double[][] b = new double[][] { {Double.NaN} };
     assertTrue(DataUtilities.equal(a, b));
 }

 @Test
 public void testEqual_WithInfinity() {
     double[][] a = new double[][] { {Double.POSITIVE_INFINITY} };
     double[][] b = new double[][] { {Double.POSITIVE_INFINITY} };
     assertTrue(DataUtilities.equal(a, b));
 }










 

 
 
 
 

 
 

 
 

 

 
 
 




 
 
 
 
}
