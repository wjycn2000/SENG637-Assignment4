package org.jfree.data.test;

import static org.junit.Assert.*; import org.jfree.data.Range; import org.junit.*;

import java.lang.reflect.Method;

/**
 * Helper method to invoke private static methods.
 */

public class RangeTest {

	private static Object invokePrivateStaticMethod(String methodName, Class<?>[] paramTypes, Object... params) throws Exception {
	    Method method = Range.class.getDeclaredMethod(methodName, paramTypes);
	    method.setAccessible(true); // Allow access to private method
	    return method.invoke(null, params); // Invoke method without an instance
	}

	
	/*
	 * Test combine()
	 */
	
    /**
     * Test Case: Both input ranges are null.
     * Test Strategy: ECP - Null case
     * Expected Behavior: The method should return null.
     */
    @Test
    public void testCombineBothNullReturnsNull() {
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(null, null);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertNull("Combining two null ranges should return null.", combinedRange);
    }

    /**
     * Test Case: First input range is null.
     * Test Strategy: ECP - Null handling
     * Expected Behavior: The method should return the second range.
     */
    @Test
    public void testCombineFirstNullReturnsSecondRange() {
        Range range2 = new Range(2, 5);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(null, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Combining null with a valid range should return the valid range.", range2, combinedRange);
    }

    /**
     * Test Case: Second input range is null.
     * Test Strategy: ECP - Null handling
     * Expected Behavior: The method should return the first range.
     */
    @Test
    public void testCombineSecondNullReturnsFirstRange() {
        Range range1 = new Range(3, 8);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, null);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Combining a valid range with null should return the valid range.", range1, combinedRange);
    }

    /**
     * Test Case: Two non-overlapping ranges.
     * Test Strategy: ECP - Non-overlapping ranges
     * Expected Behavior: The method should return a new range covering both inputs.
     */
    @Test
    public void testCombineNonOverlappingRangesMergesCorrectly() {
        Range range1 = new Range(1, 5);
        Range range2 = new Range(10, 15);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Non-overlapping ranges should be merged into (1, 15).", new Range(1, 15), combinedRange);
    }

    /**
     * Test Case: Two touching ranges.
     * Test Strategy: BVA - Touching boundaries
     * Expected Behavior: The method should merge them into a single continuous range.
     */
    @Test
    public void testCombineTouchingRangesMerges() {
        Range range1 = new Range(1, 5);
        Range range2 = new Range(5, 10);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Touching ranges should merge into (1,10).", new Range(1, 10), combinedRange);
    }

    /**
     * Test Case: Two overlapping ranges.
     * Test Strategy: ECP - Overlapping ranges
     * Expected Behavior: The method should merge the overlapping portions into a single range.
     */
    @Test
    public void testCombineOverlappingRangesMergesCorrectly() {
        Range range1 = new Range(1, 6);
        Range range2 = new Range(4, 10);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Overlapping ranges should merge into (1,10).", new Range(1, 10), combinedRange);
    }

    /**
     * Test Case: Identical ranges.
     * Test Strategy: ECP - Identical ranges
     * Expected Behavior: The method should return the same range.
     */
    @Test
    public void testCombineIdenticalRangesReturnsSameRange() {
        Range range1 = new Range(1, 5);
        Range range2 = new Range(1, 5);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Identical ranges should return the same range.", range1, combinedRange);
    }

    /**
     * Test Case: One range is fully inside another.
     * Test Strategy: ECP - Subset range
     * Expected Behavior: The method should return the larger range.
     */
    @Test
    public void testCombineOneRangeInsideAnotherReturnsLargerRange() {
        Range range1 = new Range(1, 10);
        Range range2 = new Range(3, 7);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Range inside another should return the larger range.", range1, combinedRange);
    }

    /**
     * Test Case: Merging when one range has Integer.MIN_VALUE.
     * Test Strategy: BVA - Lower boundary
     * Expected Behavior: The method should correctly handle the extreme lower bound.
     */
    @Test
    public void testCombineLowerBoundaryValuesMergeCorrectly() {
        Range range1 = new Range(Integer.MIN_VALUE, -1);
        Range range2 = new Range(0, 5);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Lower boundary values should merge into (Integer.MIN_VALUE, 5).", new Range(Integer.MIN_VALUE, 5), combinedRange);
    }

    /**
     * Test Case: Merging when one range has Integer.MAX_VALUE.
     * Test Strategy: BVA - Upper boundary
     * Expected Behavior: The method should correctly handle the extreme upper bound.
     */
    @Test
    public void testCombineUpperBoundaryValuesMergeCorrectly() {
        Range range1 = new Range(10, Integer.MAX_VALUE);
        Range range2 = new Range(5, 9);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Upper boundary values should merge into (5, Integer.MAX_VALUE).", new Range(5, Integer.MAX_VALUE), combinedRange);
    }

    /**
     * Test Case: Merging two ranges at the zero boundary.
     * Test Strategy: BVA - Zero boundary
     * Expected Behavior: The method should correctly handle zero-based merging.
     */
    @Test
    public void testCombineZeroBoundaryValuesMergeCorrectly() {
        Range range1 = new Range(-5, 0);
        Range range2 = new Range(0, 5);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Zero boundary values should merge into (-5,5).", new Range(-5, 5), combinedRange);
    }
    
    /**
     * Test Case: Identical single-point ranges.
     * Test Strategy: ECP - Single-point range
     * Expected Behavior: The method should return the same single-point range.
     */
    @Test
    public void testCombineSinglePointRangesIdenticalReturnsSamePoint() {
        Range range1 = new Range(3, 3);
        Range range2 = new Range(3, 3);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Identical single-point ranges (3,3) should return (3,3).",
            new Range(3, 3), combinedRange);
    }

    /**
     * Test Case: Merging two adjacent single-point ranges.
     * Test Strategy: BVA - Adjacent single points
     * Expected Behavior: The method should merge them into a larger range.
     */
    @Test
    public void testCombineSinglePointRangesAdjacentMerges() {
        Range range1 = new Range(3, 3);
        Range range2 = new Range(4, 4);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Adjacent single-point ranges should merge into (3,4).", new Range(3, 4), combinedRange);
    }
    
    /*
     * Test constrain()
     */
    
    /**
     * Test Case: A normal value within the valid range.
     * Test Strategy: Inside the range
     * Expected Behavior: The method should return the input value as is.
     */
    @Test
    public void testConstrainWithinRangeReturnsSameValue() {
        Range range = new Range(2, 8);
        assertEquals("A value inside the range should return itself.",
            5, range.constrain(5), 0.000000001d);
    }

    /**
     * Test Case: The lower boundary value of the range.
     * Test Strategy: Lower boundary inside the range
     * Expected Behavior: The method should return the lower boundary itself.
     */
    @Test
    public void testConstrainLowerBoundaryReturnsSameValue() {
        Range range = new Range(2, 8);
        assertEquals("The lower boundary should return itself.",
            2, range.constrain(2), 0.000000001d);
    }

    /**
     * Test Case: The upper boundary value of the range.
     * Test Strategy: Upper boundary inside the range
     * Expected Behavior: The method should return the upper boundary itself.
     */
    @Test
    public void testConstrainUpperBoundaryReturnsSameValue() {
        Range range = new Range(2, 8);
        assertEquals("The upper boundary should return itself.",
            8, range.constrain(8), 0.000000001d);
    }

    /**
     * Test Case: A value below the lower boundary.
     * Test Strategy: Below the range
     * Expected Behavior: The method should return the lower boundary.
     */
    @Test
    public void testConstrainBelowRangeReturnsLowerBound() {
        Range range = new Range(2, 8);
        assertEquals("A value below the range should return the lower bound.",
            2, range.constrain(0), 0.000000001d);
    }

    /**
     * Test Case: A significantly lower value, far outside the range.
     * Test Strategy: Far below the range
     * Expected Behavior: The method should return the lower boundary.
     */
    @Test
    public void testConstrainFarBelowRangeReturnsLowerBound() {
        Range range = new Range(2, 8);
        assertEquals("A value far below the range should return the lower bound.",
            2, range.constrain(-5), 0.000000001d);
    }

    /**
     * Test Case: A value above the upper boundary.
     * Test Strategy: Above the range
     * Expected Behavior: The method should return the upper boundary.
     */
    @Test
    public void testConstrainAboveRangeReturnsUpperBound() {
        Range range = new Range(2, 8);
        assertEquals("A value above the range should return the upper bound.",
            8, range.constrain(10), 0.000000001d);
    }

    /**
     * Test Case: A significantly higher value, far outside the range.
     * Test Strategy: Far above the range
     * Expected Behavior: The method should return the upper boundary.
     */
    @Test
    public void testConstrainFarAboveRangeReturnsUpperBound() {
        Range range = new Range(2, 8);
        assertEquals("A value far above the range should return the upper bound.",
            8, range.constrain(100), 0.000000001d);
    }

    /**
     * Test Case: A value just below the lower boundary.
     * Test Strategy: Just below the lower boundary
     * Expected Behavior: The method should return the lower boundary.
     */
    @Test
    public void testConstrainJustBelowLowerBoundaryReturnsLowerBound() {
        Range range = new Range(2, 8);
        assertEquals("A value just below the lower boundary should return the lower bound.",
            2, range.constrain(1.9999), 0.000000001d);
    }

    /**
     * Test Case: A value slightly lower than the range.
     * Test Strategy: Lower boundary outside range
     * Expected Behavior: The method should return the lower boundary.
     */
    @Test
    public void testConstrainLowerBoundaryOutsideReturnsLowerBound() {
        Range range = new Range(2, 8);
        assertEquals("A value just outside the lower boundary should return the lower bound.",
            2, range.constrain(1), 0.000000001d);
    }

    /**
     * Test Case: A value just above the upper boundary.
     * Test Strategy: Just above the upper boundary
     * Expected Behavior: The method should return the upper boundary.
     */
    @Test
    public void testConstrainJustAboveUpperBoundaryReturnsUpperBound() {
        Range range = new Range(2, 8);
        assertEquals("A value just above the upper boundary should return the upper bound.",
            8, range.constrain(8.0001), 0.000000001d);
    }

    /**
     * Test Case: A value slightly higher than the range.
     * Test Strategy: Upper boundary outside range
     * Expected Behavior: The method should return the upper boundary.
     */
    @Test
    public void testConstrainUpperBoundaryOutsideReturnsUpperBound() {
        Range range = new Range(2, 8);
        assertEquals("A value just outside the upper boundary should return the upper bound.",
            8, range.constrain(9), 0.000000001d);
    }
	
    // Testing method Range.contains()
    // TC_RNG_033: Value exactly at the lower bound
    @Test
    public void testContains_AtLowerBound() {
        Range range = new Range(5.0, 10.0);
        assertTrue("Range(5,10) should contain its lower bound 5.0", range.contains(5.0));
    }

    // TC_RNG_034: Value exactly at the upper bound
    @Test
    public void testContains_AtUpperBound() {
        Range range = new Range(5.0, 10.0);
        assertTrue("Range(5,10) should contain its upper bound 10.0", range.contains(10.0));
    }

    // TC_RNG_035: Value within the range
    @Test
    public void testContains_ValueInsideRange() {
        Range range = new Range(5.0, 10.0);
        assertTrue("Range(5,10) should contain 7.5", range.contains(7.5));
    }

    // TC_RNG_036: Value outside the range (lower than lower bound)
    @Test
    public void testContains_ValueBelowRange() {
        Range range = new Range(5.0, 10.0);
        assertFalse("Range(5,10) should not contain 3.0", range.contains(3.0));
    }

    // TC_RNG_037: Value outside the range (greater than upper bound)
    @Test
    public void testContains_ValueAboveRange() {
        Range range = new Range(5.0, 10.0);
        assertFalse("Range(5,10) should not contain 12.0", range.contains(12.0));
    }

    // TC_RNG_038: Value in a negative range
    @Test
    public void testContains_ValueInNegativeRange() {
        Range range = new Range(-10.0, -5.0);
        assertTrue("Range(-10,-5) should contain -7.0", range.contains(-7.0));
    }

    // TC_RNG_039: Value outside a negative range
    @Test
    public void testContains_ValueOutsideNegativeRange() {
        Range range = new Range(-10.0, -5.0);
        assertFalse("Range(-10,-5) should not contain -11.0", range.contains(-11.0));
    }

    // TC_RNG_040: Value at zero in a range that includes zero
    @Test
    public void testContains_ValueAtZero() {
        Range range = new Range(-5.0, 5.0);
        assertTrue("Range(-5,5) should contain 0.0", range.contains(0.0));
    }

    // TC_RNG_041: Value in a very large range
    @Test
    public void testContains_ValueInLargeRange() {
        Range range = new Range(-1e9, 1e9);
        assertTrue("Range(-1e9,1e9) should contain 0", range.contains(0.0));
    }

    // TC_RNG_042: Value outside a very large range
    @Test
    public void testContains_ValueOutsideLargeRange() {
        Range range = new Range(-1e9, 1e9);
        assertFalse("Range(-1e9,1e9) should not contain 1e12", range.contains(1e12));
    }

    // TC_RNG_043: Single-point range containing its own value
    @Test
    public void testContains_SinglePointRangeContainsItsValue() {
        Range range = new Range(3.0, 3.0);
        assertTrue("Single-point range (3,3) should contain 3.0", range.contains(3.0));
    }

    // TC_RNG_044: Single-point range should not contain other values
    @Test
    public void testContains_SinglePointRangeDoesNotContainOtherValues() {
        Range range = new Range(3.0, 3.0);
        assertFalse("Single-point range (3,3) should not contain 4.0", range.contains(4.0));
    }

    // TC_RNG_045: Extreme values - Checking Double.MIN_VALUE
    @Test
    public void testContains_WithDoubleMinValue() {
        Range range = new Range(Double.MIN_VALUE, 10.0);
        assertTrue("Range(Double.MIN_VALUE, 10) should contain Double.MIN_VALUE", range.contains(Double.MIN_VALUE));
    }

    // TC_RNG_046: Extreme values - Checking Double.MAX_VALUE
    @Test
    public void testContains_WithDoubleMaxValue() {
        Range range = new Range(-10.0, Double.MAX_VALUE);
        assertTrue("Range(-10, Double.MAX_VALUE) should contain Double.MAX_VALUE", range.contains(Double.MAX_VALUE));
    }

    // TC_RNG_047: Handling NaN values
    @Test
    public void testContains_WithNaN() {
        Range range = new Range(5.0, 10.0);
        assertFalse("Range(5,10) should not contain NaN", range.contains(Double.NaN));
    }
    
    // Testing method Range.getCentralValue()
    // TC_RNG_048: Central value of a standard positive range
    @Test
    public void testGetCentralValue_PositiveRange() {
        Range range = new Range(5.0, 10.0);
        assertEquals("Central value of Range(5,10) should be 7.5", 
                     7.5, range.getCentralValue(), 0.000000001d);
    }

    // TC_RNG_049: Central value of a negative range
    @Test
    public void testGetCentralValue_NegativeRange() {
        Range range = new Range(-10.0, -5.0);
        assertEquals("Central value of Range(-10,-5) should be -7.5", 
                     -7.5, range.getCentralValue(), 0.000000001d);
    }

    // TC_RNG_050: Central value when range includes zero
    @Test
    public void testGetCentralValue_IncludingZero() {
        Range range = new Range(-5.0, 5.0);
        assertEquals("Central value of Range(-5,5) should be 0.0", 
                     0.0, range.getCentralValue(), 0.000000001d);
    }

    // TC_RNG_051: Central value when lower bound is zero
    @Test
    public void testGetCentralValue_ZeroLowerBound() {
        Range range = new Range(0.0, 10.0);
        assertEquals("Central value of Range(0,10) should be 5.0", 
                     5.0, range.getCentralValue(), 0.000000001d);
    }

    // TC_RNG_052: Central value of a single-point range
    @Test
    public void testGetCentralValue_SinglePointRange() {
        Range range = new Range(3.0, 3.0);
        assertEquals("Central value of Range(3,3) should be 3.0", 
                     3.0, range.getCentralValue(), 0.000000001d);
    }

    // TC_RNG_053: Central value of a large range
    @Test
    public void testGetCentralValue_LargeRange() {
        Range range = new Range(-1e9, 1e9);
        assertEquals("Central value of Range(-1e9,1e9) should be 0.0", 
                     0.0, range.getCentralValue(), 0.000000001d);
    }

    // TC_RNG_054: Central value of an extreme range near Double.MIN_VALUE
    @Test
    public void testGetCentralValue_WithDoubleMinValue() {
        Range range = new Range(Double.MIN_VALUE, 10.0);
        assertEquals("Central value of Range(Double.MIN_VALUE, 10) should be (Double.MIN_VALUE + 10) / 2", 
                     (Double.MIN_VALUE + 10.0) / 2, range.getCentralValue(), 0.000000001d);
    }

    // TC_RNG_055: Central value of an extreme range near Double.MAX_VALUE
    @Test
    public void testGetCentralValue_WithDoubleMaxValue() {
        Range range = new Range(-10.0, Double.MAX_VALUE);
        assertEquals("Central value of Range(-10, Double.MAX_VALUE) should be (-10 + Double.MAX_VALUE) / 2", 
                     (-10.0 + Double.MAX_VALUE) / 2, range.getCentralValue(), 0.000000001d);
    }

    // TC_RNG_056: Central value of a large negative range
    @Test
    public void testGetCentralValue_LargeNegativeRange() {
        Range range = new Range(-1e12, -1e6);
        assertEquals("Central value of Range(-1e12,-1e6) should be (-1e12 + -1e6) / 2", 
                     (-1e12 + -1e6) / 2, range.getCentralValue(), 0.000000001d);
    }

    // TC_RNG_057: Central value of a small decimal range
    @Test
    public void testGetCentralValue_SmallDecimalRange() {
        Range range = new Range(0.0001, 0.0005);
        assertEquals("Central value of Range(0.0001,0.0005) should be 0.0003", 
                     0.0003, range.getCentralValue(), 0.000000001d);
    }
    
    // Testing method Range.getLength()
    // TC_RNG_058: Length of a standard positive range
    @Test
    public void testGetLength_PositiveRange() {
        Range range = new Range(5.0, 10.0);
        assertEquals("Length of Range(5,10) should be 5.0", 
                     5.0, range.getLength(), 0.000000001d);
    }

    // TC_RNG_059: Length of a negative range
    @Test
    public void testGetLength_NegativeRange() {
        Range range = new Range(-10.0, -5.0);
        assertEquals("Length of Range(-10,-5) should be 5.0", 
                     5.0, range.getLength(), 0.000000001d);
    }

    // TC_RNG_060: Length of a range including zero
    @Test
    public void testGetLength_IncludingZero() {
        Range range = new Range(-5.0, 5.0);
        assertEquals("Length of Range(-5,5) should be 10.0", 
                     10.0, range.getLength(), 0.000000001d);
    }

    // TC_RNG_061: Length of a zero-width range (both bounds are the same)
    @Test
    public void testGetLength_ZeroWidthRange() {
        Range range = new Range(3.0, 3.0);
        assertEquals("Length of Range(3,3) should be 0.0", 
                     0.0, range.getLength(), 0.000000001d);
    }

    // TC_RNG_062: Length of a very large range
    @Test
    public void testGetLength_LargeRange() {
        Range range = new Range(-1e9, 1e9);
        assertEquals("Length of Range(-1e9,1e9) should be 2e9", 
                     2e9, range.getLength(), 0.000000001d);
    }

    // TC_RNG_063: Length of an extreme range near Double.MIN_VALUE
    @Test
    public void testGetLength_WithDoubleMinValue() {
        Range range = new Range(Double.MIN_VALUE, 10.0);
        assertEquals("Length of Range(Double.MIN_VALUE, 10) should be 10 - Double.MIN_VALUE", 
                     10.0 - Double.MIN_VALUE, range.getLength(), 0.000000001d);
    }

    // TC_RNG_064: Length of an extreme range near Double.MAX_VALUE
    @Test
    public void testGetLength_WithDoubleMaxValue() {
        Range range = new Range(-10.0, Double.MAX_VALUE);
        assertEquals("Length of Range(-10, Double.MAX_VALUE) should be Double.MAX_VALUE + 10", 
                     Double.MAX_VALUE + 10.0, range.getLength(), 0.000000001d);
    }

    // TC_RNG_065: Length of a large negative range
    @Test
    public void testGetLength_LargeNegativeRange() {
        Range range = new Range(-1e12, -1e6);
        assertEquals("Length of Range(-1e12,-1e6) should be (-1e6 + 1e12)", 
                     (-1e6 + 1e12), range.getLength(), 0.000000001d);
    }

    // TC_RNG_066: Length of a small decimal range
    @Test
    public void testGetLength_SmallDecimalRange() {
        Range range = new Range(0.0001, 0.0005);
        assertEquals("Length of Range(0.0001,0.0005) should be 0.0004", 
                     0.0004, range.getLength(), 0.000000001d);
    }
    
//////////////////////////////////////  

    @Test
    public void testGetLowerBound() {
        Range r = new Range(5, 10);
        assertEquals(5, r.getLowerBound(), 0.0001);
    }

    @Test
    public void testGetUpperBound() {
        Range r = new Range(5, 10);
        assertEquals(10, r.getUpperBound(), 0.0001);
    }

    @Test
    public void testGetLength() {
        Range r = new Range(3, 8);
        assertEquals(5, r.getLength(), 0.0001);
    }

    @Test
    public void testContains_ValueWithinRange_ShouldReturnTrue() {
        Range r = new Range(1, 5);
        assertTrue(r.contains(3));
    }

    @Test
    public void testContains_ValueBelowRange_ShouldReturnFalse() {
        Range r = new Range(1, 5);
        assertFalse(r.contains(0));
    }

    @Test
    public void testContains_ValueAboveRange_ShouldReturnFalse() {
        Range r = new Range(1, 5);
        assertFalse(r.contains(6));
    }

    @Test
    public void testShift_PositiveDelta_ShouldMoveRange() {
        Range r = new Range(2, 6);
        Range shifted = Range.shift(r, 3);
        assertEquals(new Range(5, 9), shifted);
    }

    /*@Test
    public void testShift_NegativeDelta_ShouldMoveRangeLeft() {
        Range r = new Range(2, 6);
        Range shifted = Range.shift(r, -3);
        assertEquals(new Range(-1, 3), shifted);
    }*/
    
    @Test
    public void testShift_ZeroCrossingAllowed() {
        Range r = new Range(-3, 5);
        Range shifted = Range.shift(r, 4, true);
        assertEquals(new Range(1, 9), shifted);
    }

    @Test
    public void testShift_ZeroCrossingDisallowed_ShouldNotGoNegative() {
        Range r = new Range(-3, 5);
        Range shifted = Range.shift(r, 4, false);
        assertEquals(new Range(0, 9), shifted);
    }

    @Test
    public void testScale_PositiveFactor_ShouldStretchRange() {
        Range r = new Range(2, 6);
        Range scaled = Range.scale(r, 2);
        assertEquals(new Range(4, 12), scaled);
    }

    /*@Test
    public void testScale_ZeroFactor_ShouldThrowException() {
        Range r = new Range(2, 6);
        try {
            Range.scale(r, 0);
            fail("Expected exception when scaling by zero");
        } catch (IllegalArgumentException e) {
            // Expected behavior
        }
    }*/

    @Test
    public void testExpandToInclude_ValueWithinRange_ShouldRemainSame() {
        Range r = new Range(5, 15);
        Range expanded = Range.expandToInclude(r, 10);
        assertEquals(new Range(5, 15), expanded);
    }

    @Test
    public void testExpandToInclude_ValueAboveRange_ShouldExpandUpperBound() {
        Range r = new Range(5, 15);
        Range expanded = Range.expandToInclude(r, 20);
        assertEquals(new Range(5, 20), expanded);
    }

    @Test
    public void testExpandToInclude_ValueBelowRange_ShouldExpandLowerBound() {
        Range r = new Range(5, 15);
        Range expanded = Range.expandToInclude(r, 2);
        assertEquals(new Range(2, 15), expanded);
    }

    @Test
    public void testHashCode_TwoIdenticalRanges_ShouldHaveSameHashCode() {
        Range r1 = new Range(3, 7);
        Range r2 = new Range(3, 7);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    public void testEquals_SameBounds_ShouldReturnTrue() {
        Range r1 = new Range(3, 7);
        Range r2 = new Range(3, 7);
        assertTrue(r1.equals(r2));
    }

    @Test
    public void testEquals_DifferentBounds_ShouldReturnFalse() {
        Range r1 = new Range(3, 7);
        Range r2 = new Range(4, 7);
        assertFalse(r1.equals(r2));
    }
    
    @Test
    public void testCombineBothNullReturnsNull1() {
        assertNull("Combining two null ranges should return null.", Range.combine(null, null));
    }

    @Test
    public void testCombineFirstNullReturnsSecondRange1() {
        Range range2 = new Range(2, 5);
        assertEquals("Combining null with a valid range should return the valid range.", range2, Range.combine(null, range2));
    }

    @Test
    public void testCombineSecondNullReturnsFirstRange1() {
        Range range1 = new Range(3, 8);
        assertEquals("Combining a valid range with null should return the valid range.", range1, Range.combine(range1, null));
    }
    

    @Test
    public void testExpand_ShouldExpandBothSides() {
        Range r = new Range(5, 15);
        Range expanded = Range.expand(r, 0.5, 0.5);
        assertEquals(new Range(0, 20), expanded);
    }
    
    @Test
    public void testExpand_ShouldExpandLowerOnly() {
        Range r = new Range(5, 15);
        Range expanded = Range.expand(r, 0.5, 0);
        assertEquals(new Range(0, 15), expanded);
    }
    
    @Test
    public void testExpand_ShouldExpandUpperOnly() {
        Range r = new Range(5, 15);
        Range expanded = Range.expand(r, 0, 0.5);
        assertEquals(new Range(5, 20), expanded);
    }
    
    @Test
    public void testIntersects_OverlappingRanges_ShouldReturnTrue() {
        Range r = new Range(2, 6);
        assertTrue(r.intersects(4, 8));
    }
    
    @Test
    public void testIntersects_NonOverlappingRanges_ShouldReturnFalse() {
        Range r = new Range(2, 6);
        assertFalse(r.intersects(7, 9));
    }
    
    /*@Test
    public void testIntersects_TouchingEdges_ShouldReturnTrue() {
        Range r = new Range(2, 6);
        assertTrue(r.intersects(6, 9));
    }*/
    
    @Test
    public void testCombine_TwoValidRanges_ShouldMerge() {
        Range r1 = new Range(2, 5);
        Range r2 = new Range(4, 8);
        Range result = Range.combine(r1, r2);
        assertEquals(new Range(2, 8), result);
    }
    
    @Test
    public void testCombine_FirstRangeNull_ShouldReturnSecond() {
        Range r2 = new Range(4, 8);
        assertEquals(r2, Range.combine(null, r2));
    }
    
    @Test
    public void testCombine_SecondRangeNull_ShouldReturnFirst() {
        Range r1 = new Range(4, 8);
        assertEquals(r1, Range.combine(r1, null));
    }
    
    @Test
    public void testCombine_BothRangesNull_ShouldReturnNull() {
        assertNull(Range.combine(null, null));
    }
    
    @Test
    public void testIntersects_OneInsideAnother_ShouldReturnTrue() {
        Range r = new Range(2, 10);
        assertTrue("A range inside another should return true", r.intersects(4, 8));
    }

    @Test
    public void testIntersects_BoundaryMatches_ShouldReturnTrue() {
        Range r = new Range(3, 7);
        assertTrue("A range matching the boundary should return true", r.intersects(3, 7));
    }
    
    @Test
    public void testShift_BelowZeroWithZeroCrossingAllowed() {
        Range r = new Range(-2, 5);
        Range shifted = Range.shift(r, -3, true);
        assertEquals(new Range(-5, 2), shifted);
    }

    /*@Test
    public void testShift_BelowZeroWithZeroCrossingDisallowed() {
        Range r = new Range(-2, 5);
        Range shifted = Range.shift(r, -3, false);
        assertEquals(new Range(0, 5), shifted);
    }*/

    @Test
    public void testExpandToInclude_BothSidesExpansion() {
        Range r = new Range(3, 8);
        Range expanded = Range.expandToInclude(r, 10);
        expanded = Range.expandToInclude(expanded, 2);
        assertEquals(new Range(2, 10), expanded);
    }

    @Test
    public void testExpandToInclude_NoChange() {
        Range r = new Range(3, 8);
        Range expanded = Range.expandToInclude(r, 5);
        assertEquals("Value inside range should not expand it", new Range(3, 8), expanded);
    }

    /*@Test(expected = IllegalArgumentException.class)
    public void testScale_NegativeFactor_ShouldThrowException() {
        Range r = new Range(2, 6);
        Range.scale(r, -1);
    }*/

    @Test
    public void testIntersects_InputRangeBefore_ShouldReturnFalse() {
        Range r = new Range(5, 10);
        assertFalse("Range completely before should return false", r.intersects(1, 4));
    }

    @Test
    public void testIntersects_InputRangeAfter_ShouldReturnFalse() {
        Range r = new Range(5, 10);
        assertFalse("Range completely after should return false", r.intersects(11, 15));
    }

    @Test
    public void testCombine_IdenticalRanges_ShouldReturnSameRange() {
        Range r1 = new Range(3, 8);
        Range r2 = new Range(3, 8);
        assertEquals("Combining identical ranges should return the same range", r1, Range.combine(r1, r2));
    }

    @Test
    public void testCombine_OneInsideAnother_ShouldReturnOuterRange() {
        Range outer = new Range(2, 10);
        Range inner = new Range(4, 8);
        assertEquals("Combining an inner range should return the outer range", outer, Range.combine(outer, inner));
    }

    @Test
    public void testShift_AlignsWithZero() {
        Range r = new Range(-5, 5);
        Range shifted = Range.shift(r, 5, false);
        assertEquals("Range should start at 0 after shifting", new Range(0, 10), shifted);
    }

    /*@Test
    public void testShift_LargePositiveDelta() {
        Range r = new Range(-5, 5);
        Range shifted = Range.shift(r, 20);
        assertEquals("Range should move completely into positive territory", new Range(15, 25), shifted);
    }*/

    @Test
    public void testScale_ByOne_ShouldRemainSame() {
        Range r = new Range(3, 7);
        Range scaled = Range.scale(r, 1.0);
        assertEquals("Scaling by 1 should keep the range unchanged", new Range(3, 7), scaled);
    }

    @Test
    public void testScale_ByLargeFactor_ShouldExpand() {
        Range r = new Range(2, 6);
        Range scaled = Range.scale(r, 10);
        assertEquals("Scaling by 10 should expand the range", new Range(20, 60), scaled);
    }

    /*@Test
    public void testIntersects_ExactBoundaryMatch_ShouldReturnTrue() {
        Range r = new Range(5, 10);
        assertTrue("Intersection at exact boundary should return true", r.intersects(10, 15));
    }*/

    @Test
    public void testIntersects_TouchingButNoOverlap_ShouldReturnFalse() {
        Range r = new Range(5, 10);
        assertFalse("Ranges touching at boundary but not overlapping should return false", r.intersects(10, 10));
    }

    @Test
    public void testShift_ZeroDelta_ShouldRemainSame() {
        Range r = new Range(3, 8);
        Range shifted = Range.shift(r, 0);
        assertEquals("Shifting by zero should keep the same range", new Range(3, 8), shifted);
    }

    @Test
    public void testShift_PositiveShiftWithZeroCrossingDisabled() {
        Range r = new Range(-4, 4);
        Range shifted = Range.shift(r, 6, false);
        assertEquals("Shifting by 6 should stop at zero for lower bound", new Range(0, 10), shifted);
    }

    /*
    @Test
    public void testExpand_OnlyLower_ShouldExpandDownward() {
        Range r = new Range(5, 15);
        Range expanded = Range.expand(r, 0.5, 0.0);
        
        // Debugging: Print actual values
        System.out.println("Expected: Range[-2.0, 12.0], Actual: " + expanded);
        
        assertEquals("Expanding only lower should extend downward", -2.0, expanded.getLowerBound(), 0.0001);
        assertEquals("Upper bound should remain unchanged", 12.0, expanded.getUpperBound(), 0.0001);
    }*/


    /*
    @Test
    public void testExpand_OnlyUpper_ShouldExpandUpward() {
        Range r = new Range(4, 12);
        Range expanded = Range.expand(r, 0.0, 0.5);
        assertEquals("Expanding only upper should extend upward", new Range(4, 18), expanded);
    }*/

    @Test
    public void testCombine_OneRangeNull_ShouldReturnOtherRange() {
        Range r = new Range(2, 10);
        assertEquals("Combining with null should return the non-null range", r, Range.combine(r, null));
    }

    /**
     * Test Case: Lower bound is greater than upper bound.
     * Test Strategy: Exception Handling
     * Expected Behavior: The constructor should throw an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_LowerGreaterThanUpper_ShouldThrowException() {
        new Range(10, 5); // Invalid range, should throw an exception
    }
    
    /**
     * Test Case: getLowerBound() should throw an exception if lower > upper.
     * Test Strategy: Exception Handling
     * Expected Behavior: The method should throw an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetLowerBound_LowerGreaterThanUpper_ShouldThrowException() {
        Range invalidRange = new Range(10, 5); // Creating an invalid range
        invalidRange.getLowerBound(); // This should trigger the exception
    }

    /**
     * Test Case: getUpperBound() should return the correct upper bound.
     * Test Strategy: Normal Execution
     * Expected Behavior: The method should return the upper bound correctly.
     */
    @Test
    public void testGetUpperBound_ValidRange_ShouldReturnUpper() {
        Range validRange = new Range(2, 8);
        assertEquals("Upper bound should be 8", 8, validRange.getUpperBound(), 0.0001);
    }

    /**
     * Test Case: getUpperBound() should throw an exception if lower > upper.
     * Test Strategy: Exception Handling
     * Expected Behavior: The method should throw an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetUpperBound_LowerGreaterThanUpper_ShouldThrowException() {
        Range invalidRange = new Range(10, 5); // Creating an invalid range
        invalidRange.getUpperBound(); // This should trigger the exception
    }

    /**
     * Test Case: Forcefully set lower > upper and call getLowerBound().
     * Expected: IllegalArgumentException should be thrown.
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     *//*
    @Test(expected = IllegalArgumentException.class)
    public void testGetLowerBound_ForcedInvalidState_ShouldThrowException() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Range r = new Range(2, 8); // Start with a valid range
        // Use Reflection to forcefully change the state (simulate corruption)
        java.lang.reflect.Field field = Range.class.getDeclaredField("lower");
        field.setAccessible(true);
        field.set(r, 10); // Forcefully change lower to be greater than upper
        r.getLowerBound(); // This should now trigger the exception
    }*/

    /**
     * Test Case: Forcefully set lower > upper and call getUpperBound().
     * Expected: IllegalArgumentException should be thrown.
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     *//*
    @Test(expected = IllegalArgumentException.class)
    public void testGetUpperBound_ForcedInvalidState_ShouldThrowException() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Range r = new Range(2, 8); // Start with a valid range
        // Use Reflection to forcefully change the state (simulate corruption)
        java.lang.reflect.Field field = Range.class.getDeclaredField("lower");
        field.setAccessible(true);
        field.set(r, 10); // Forcefully change lower to be greater than upper
        r.getUpperBound(); // This should now trigger the exception
    }*/

    /**
     * Test Case: getLength() should return the correct range length.
     * Test Strategy: Normal Execution
     * Expected Behavior: The method should return upper - lower.
     */
    @Test
    public void testGetLength_ValidRange_ShouldReturnDifference() {
        Range r = new Range(3, 8);
        assertEquals("Length should be 5", 5, r.getLength(), 0.0001);
    }

    /**
     * Test Case: getLength() should return 0 when lower == upper.
     * Test Strategy: Boundary Condition
     * Expected Behavior: The length of a zero-width range should be 0.
     */
    @Test
    public void testGetLength_ZeroWidthRange_ShouldReturnZero() {
        Range r = new Range(5, 5);
        assertEquals("Zero-width range should have length 0", 0, r.getLength(), 0.0001);
    }

    /**
     * Test Case: getLength() should return the correct difference for negative values.
     * Test Strategy: Edge Case
     * Expected Behavior: The method should correctly compute the length with negative bounds.
     */
    @Test
    public void testGetLength_NegativeRange_ShouldReturnCorrectLength() {
        Range r = new Range(-10, -5);
        assertEquals("Negative range should have correct length", 5, r.getLength(), 0.0001);
    }

    /**
     * Test Case: Forcefully set lower > upper and call getLength().
     * Expected: IllegalArgumentException should be thrown.
     *//*
    @Test(expected = IllegalArgumentException.class)
    public void testGetLength_ForcedInvalidState_ShouldThrowException() throws Exception {
        Range r = new Range(2, 8); // Create a valid range
        java.lang.reflect.Field field = Range.class.getDeclaredField("lower");
        field.setAccessible(true);
        field.set(r, 10); // Forcefully set lower > upper
        r.getLength(); // Should trigger IllegalArgumentException
    }*/
    /**
     * Test Case: Two ranges that overlap should return true.
     * Test Strategy: Normal Execution
     * Expected Behavior: intersects() should return true.
     */
    @Test
    public void testIntersects_OverlappingRanges_ShouldReturnTrue1() {
        Range r1 = new Range(3, 10);
        Range r2 = new Range(7, 15);
        assertTrue("Overlapping ranges should return true", r1.intersects(r2));
    }

    /**
     * Test Case: Two touching but not overlapping ranges.
     * Test Strategy: Edge Case
     * Expected Behavior: intersects() should return false.
     */
    @Test
    public void testIntersects_TouchingRanges_ShouldReturnFalse() {
        Range r1 = new Range(3, 7);
        Range r2 = new Range(7, 10);
        assertFalse("Touching but not overlapping ranges should return false", r1.intersects(r2));
    }

    /**
     * Test Case: Identical ranges should return true.
     * Test Strategy: Edge Case
     * Expected Behavior: intersects() should return true.
     */
    @Test
    public void testIntersects_IdenticalRanges_ShouldReturnTrue() {
        Range r = new Range(4, 9);
        assertTrue("Identical ranges should return true", r.intersects(r));
    }

    /**
     * Test Case: Two non-overlapping ranges should return false.
     * Test Strategy: Edge Case
     * Expected Behavior: intersects() should return false.
     */
    @Test
    public void testIntersects_NonOverlappingRanges_ShouldReturnFalse1() {
        Range r1 = new Range(1, 5);
        Range r2 = new Range(6, 10);
        assertFalse("Non-overlapping ranges should return false", r1.intersects(r2));
    }

    /**
     * Test Case: intersects() should throw an exception when range is null.
     * Test Strategy: Exception Handling
     * Expected Behavior: The method should throw a NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void testIntersects_NullRange_ShouldThrowException() {
        Range r = new Range(2, 8);
        r.intersects(null); // Should trigger NullPointerException
    }

    /**
     * Test Case: Combining two valid ranges should return a merged range.
     * Test Strategy: Normal Execution
     * Expected Behavior: The method should return a new range spanning both.
     */
    @Test
    public void testCombineIgnoringNaN_TwoValidRanges_ShouldReturnMergedRange() {
        Range r1 = new Range(2, 6);
        Range r2 = new Range(4, 10);
        Range result = Range.combineIgnoringNaN(r1, r2);
        assertEquals("Lower bound should be 2", 2, result.getLowerBound(), 0.0001);
        assertEquals("Upper bound should be 10", 10, result.getUpperBound(), 0.0001);
    }

    /**
     * Test Case: If range1 is null, it should return range2.
     * Test Strategy: Edge Case (Handling null values)
     * Expected Behavior: The method should return range2.
     */
    @Test
    public void testCombineIgnoringNaN_FirstRangeNull_ShouldReturnSecondRange() {
        Range r2 = new Range(3, 8);
        Range result = Range.combineIgnoringNaN(null, r2);
        assertEquals("Should return the second range", r2, result);
    }

    /**
     * Test Case: If range2 is null, it should return range1.
     * Test Strategy: Edge Case (Handling null values)
     * Expected Behavior: The method should return range1.
     */
    @Test
    public void testCombineIgnoringNaN_SecondRangeNull_ShouldReturnFirstRange() {
        Range r1 = new Range(1, 5);
        Range result = Range.combineIgnoringNaN(r1, null);
        assertEquals("Should return the first range", r1, result);
    }

    /**
     * Test Case: If range1 is null and range2 is NaN, should return null.
     * Test Strategy: Edge Case (NaN handling)
     * Expected Behavior: The method should return null.
     */
    @Test
    public void testCombineIgnoringNaN_FirstRangeNullAndSecondIsNaN_ShouldReturnNull() {
        Range nanRange = new Range(Double.NaN, Double.NaN);
        Range result = Range.combineIgnoringNaN(null, nanRange);
        assertNull("Should return null when combining with NaN range", result);
    }

    /**
     * Test Case: If range2 is null and range1 is NaN, should return null.
     * Test Strategy: Edge Case (NaN handling)
     * Expected Behavior: The method should return null.
     */
    @Test
    public void testCombineIgnoringNaN_SecondRangeNullAndFirstIsNaN_ShouldReturnNull() {
        Range nanRange = new Range(Double.NaN, Double.NaN);
        Range result = Range.combineIgnoringNaN(nanRange, null);
        assertNull("Should return null when combining with NaN range", result);
    }

    /**
     * Test Case: If both ranges are NaN, should return null.
     * Test Strategy: Edge Case (NaN handling)
     * Expected Behavior: The method should return null.
     */
    @Test
    public void testCombineIgnoringNaN_BothRangesNaN_ShouldReturnNull() {
        Range nanRange1 = new Range(Double.NaN, Double.NaN);
        Range nanRange2 = new Range(Double.NaN, Double.NaN);
        Range result = Range.combineIgnoringNaN(nanRange1, nanRange2);
        assertNull("Should return null when both ranges are NaN", result);
    }

    /**
     * Test Case: If one range contains NaN but is not entirely NaN, should be ignored.
     * Test Strategy: Edge Case (Partial NaN handling)
     * Expected Behavior: The method should still return a valid range.
     */
    @Test
    public void testCombineIgnoringNaN_OneRangeWithNaNBound_ShouldIgnoreNaN() {
        Range validRange = new Range(2, 6);
        Range nanBoundRange = new Range(Double.NaN, 8);
        Range result = Range.combineIgnoringNaN(validRange, nanBoundRange);
        assertEquals("Lower bound should be 2", 2, result.getLowerBound(), 0.0001);
        assertEquals("Upper bound should be 8", 8, result.getUpperBound(), 0.0001);
    }

/////////////// 
    
    /**
     * Test Case: `min()` should return the smaller of two numbers.
     * Test Strategy: Normal Execution
     * Expected Behavior: The method should return the minimum value.
     */
    @Test
    public void testMin_ValidNumbers_ShouldReturnMinimum() throws Exception {
        assertEquals("min(3, 5) should return 3", 3.0, 
            (double) invokePrivateStaticMethod("min", new Class[]{double.class, double.class}, 3.0, 5.0), 0.0001);
    }

    /**
     * Test Case: `min()` should return the non-NaN value if one input is NaN.
     * Test Strategy: Edge Case (NaN handling)
     * Expected Behavior: The method should return the non-NaN number.
     */
    @Test
    public void testMin_OneNaN_ShouldReturnOtherNumber() throws Exception {
        assertEquals("min(NaN, 3) should return 3", 3.0, 
            (double) invokePrivateStaticMethod("min", new Class[]{double.class, double.class}, Double.NaN, 3.0), 0.0001);
    }

    /**
     * Test Case: `min()` should return NaN if both inputs are NaN.
     * Test Strategy: Edge Case (NaN handling)
     * Expected Behavior: The method should return NaN.
     */
    @Test
    public void testMin_BothNaN_ShouldReturnNaN() throws Exception {
        assertTrue("min(NaN, NaN) should return NaN", 
            Double.isNaN((double) invokePrivateStaticMethod("min", new Class[]{double.class, double.class}, Double.NaN, Double.NaN)));
    }

    /**
     * Test Case: `max()` should return the larger of two numbers.
     * Test Strategy: Normal Execution
     * Expected Behavior: The method should return the maximum value.
     */
    @Test
    public void testMax_ValidNumbers_ShouldReturnMaximum() throws Exception {
        assertEquals("max(3, 5) should return 5", 5.0, 
            (double) invokePrivateStaticMethod("max", new Class[]{double.class, double.class}, 3.0, 5.0), 0.0001);
    }

    /**
     * Test Case: `max()` should return the non-NaN value if one input is NaN.
     * Test Strategy: Edge Case (NaN handling)
     * Expected Behavior: The method should return the non-NaN number.
     */
    @Test
    public void testMax_OneNaN_ShouldReturnOtherNumber() throws Exception {
        assertEquals("max(NaN, 5) should return 5", 5.0, 
            (double) invokePrivateStaticMethod("max", new Class[]{double.class, double.class}, Double.NaN, 5.0), 0.0001);
    }

    /**
     * Test Case: `max()` should return NaN if both inputs are NaN.
     * Test Strategy: Edge Case (NaN handling)
     * Expected Behavior: The method should return NaN.
     */
    @Test
    public void testMax_BothNaN_ShouldReturnNaN() throws Exception {
        assertTrue("max(NaN, NaN) should return NaN", 
            Double.isNaN((double) invokePrivateStaticMethod("max", new Class[]{double.class, double.class}, Double.NaN, Double.NaN)));
    }

    /**
     * Test Case: If `range` is null, it should create a new range where lower == upper == value.
     * Test Strategy: Edge Case (Null handling)
     * Expected Behavior: The method should return a new range (value, value).
     */
    @Test
    public void testExpandToInclude_NullRange_ShouldCreateSingleValueRange() {
        Range result = Range.expandToInclude(null, 5);
        assertEquals("Lower bound should be 5", 5, result.getLowerBound(), 0.0001);
        assertEquals("Upper bound should be 5", 5, result.getUpperBound(), 0.0001);
    }

    /**
     * Test Case: If `value` is smaller than the current lower bound, the lower bound should expand.
     * Test Strategy: Edge Case (Expanding Lower)
     * Expected Behavior: The method should create a range with the new lower bound.
     */
    @Test
    public void testExpandToInclude_ValueBelowLower_ShouldExpandLowerBound() {
        Range r = new Range(3, 10);
        Range result = Range.expandToInclude(r, 1);
        assertEquals("Lower bound should be 1", 1, result.getLowerBound(), 0.0001);
        assertEquals("Upper bound should remain 10", 10, result.getUpperBound(), 0.0001);
    }

    /**
     * Test Case: If `value` is greater than the current upper bound, the upper bound should expand.
     * Test Strategy: Edge Case (Expanding Upper)
     * Expected Behavior: The method should create a range with the new upper bound.
     */
    @Test
    public void testExpandToInclude_ValueAboveUpper_ShouldExpandUpperBound() {
        Range r = new Range(3, 10);
        Range result = Range.expandToInclude(r, 15);
        assertEquals("Lower bound should remain 3", 3, result.getLowerBound(), 0.0001);
        assertEquals("Upper bound should be 15", 15, result.getUpperBound(), 0.0001);
    }

    /**
     * Test Case: If `value` is within the current range, it should return the same range.
     * Test Strategy: Edge Case (No Change)
     * Expected Behavior: The method should return the original range.
     */
    @Test
    public void testExpandToInclude_ValueInsideRange_ShouldReturnSameRange() {
        Range r = new Range(3, 10);
        Range result = Range.expandToInclude(r, 5);
        assertEquals("Range should remain unchanged", r, result);
    }

    /**
     * Test Case: If `value` is equal to the lower bound, the range should remain unchanged.
     * Test Strategy: Boundary Condition
     * Expected Behavior: The method should return the original range.
     */
    @Test
    public void testExpandToInclude_ValueEqualsLower_ShouldReturnSameRange() {
        Range r = new Range(3, 10);
        Range result = Range.expandToInclude(r, 3);
        assertEquals("Range should remain unchanged", r, result);
    }

    /**
     * Test Case: If `value` is equal to the upper bound, the range should remain unchanged.
     * Test Strategy: Boundary Condition
     * Expected Behavior: The method should return the original range.
     */
    @Test
    public void testExpandToInclude_ValueEqualsUpper_ShouldReturnSameRange() {
        Range r = new Range(3, 10);
        Range result = Range.expandToInclude(r, 10);
        assertEquals("Range should remain unchanged", r, result);
    }

    /**
     * Test Case: expand() should correctly expand the range.
     * Test Strategy: Normal Execution
     * Expected Behavior: The method should return a range expanded by the given margins.
     */
    @Test
    public void testExpand_NormalExpansion_ShouldReturnExpandedRange() {
        Range r = new Range(10, 20);
        Range result = Range.expand(r, 0.5, 0.5);
        assertEquals("Lower bound should be 5", 5, result.getLowerBound(), 0.0001);
        assertEquals("Upper bound should be 25", 25, result.getUpperBound(), 0.0001);
    }

    /**
     * Test Case: If margins are zero, the range should remain unchanged.
     * Test Strategy: Edge Case (Zero margins)
     * Expected Behavior: The method should return the same range.
     */
    @Test
    public void testExpand_ZeroMargins_ShouldReturnSameRange() {
        Range r = new Range(10, 20);
        Range result = Range.expand(r, 0, 0);
        assertEquals("Range should remain unchanged", r, result);
    }

    /**
     * Test Case: If lower bound becomes greater than upper due to margins, should adjust bounds.
     * Test Strategy: Edge Case (Invalid range correction)
     * Expected Behavior: The method should set lower and upper to the midpoint.
     */
    @Test
    public void testExpand_LowerGreaterThanUpper_ShouldAdjustToMidpoint() {
        Range r = new Range(10, 20);
        Range result = Range.expand(r, -1.5, -1.5); // Forces lower > upper
        double midpoint = (10 + 20) / 2.0; // Expected midpoint = 15
        assertEquals("Lower and Upper should be equal to midpoint", midpoint, result.getLowerBound(), 0.0001);
        assertEquals("Lower and Upper should be equal to midpoint", midpoint, result.getUpperBound(), 0.0001);
    }


    /**
     * Test Case: Large margins should expand the range significantly.
     * Test Strategy: Edge Case (Extreme expansion)
     * Expected Behavior: The method should return a highly expanded range.
     */
    @Test
    public void testExpand_LargeMargins_ShouldExpandCorrectly() {
        Range r = new Range(10, 20);
        Range result = Range.expand(r, 5, 5);
        assertEquals("Lower bound should be -40", -40, result.getLowerBound(), 0.0001);
        assertEquals("Upper bound should be 70", 70, result.getUpperBound(), 0.0001);
    }

    /**
     * Test Case: Negative margins should shrink the range.
     * Test Strategy: Edge Case (Shrinking)
     * Expected Behavior: The method should contract the range.
     */
    @Test
    public void testExpand_NegativeMargins_ShouldContractRange() {
        Range r = new Range(10, 20);
        Range result = Range.expand(r, -0.25, -0.25);
        assertEquals("Lower bound should be 12.5", 12.5, result.getLowerBound(), 0.0001);
        assertEquals("Upper bound should be 17.5", 17.5, result.getUpperBound(), 0.0001);
    }

    /**
     * Test Case: Passing a null range should throw an IllegalArgumentException.
     * Test Strategy: Exception Handling
     * Expected Behavior: The method should throw an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExpand_NullRange_ShouldThrowIllegalArgumentException() {
        Range.expand(null, 0.5, 0.5);
    }

    /**
     * Test Case: Positive value shifted should not go below zero.
     * Test Strategy: Normal Execution
     * Expected Behavior: The method should return max(value + delta, 0.0).
     */
    @Test
    public void testShiftWithNoZeroCrossing_PositiveValue_ShouldNotCrossZero() throws Exception {
        assertEquals("Shifting 5 by -10 should stop at 0", 0.0, 
            (double) invokePrivateStaticMethod("shiftWithNoZeroCrossing", 
            new Class[]{double.class, double.class}, 5.0, -10.0), 0.0001);
    }

    /**
     * Test Case: Negative value shifted should not go above zero.
     * Test Strategy: Normal Execution
     * Expected Behavior: The method should return min(value + delta, 0.0).
     */
    @Test
    public void testShiftWithNoZeroCrossing_NegativeValue_ShouldNotCrossZero() throws Exception {
        assertEquals("Shifting -5 by 10 should stop at 0", 0.0, 
            (double) invokePrivateStaticMethod("shiftWithNoZeroCrossing", 
            new Class[]{double.class, double.class}, -5.0, 10.0), 0.0001);
    }


    /**
     * Test Case: Zero value should correctly shift by delta.
     * Test Strategy: Edge Case (Zero value)
     * Expected Behavior: The method should return value + delta.
     */
    @Test
    public void testShiftWithNoZeroCrossing_ZeroValue_ShouldShiftByDelta() throws Exception {
        assertEquals("Shifting 0 by 5 should return 5", 5.0, 
            (double) invokePrivateStaticMethod("shiftWithNoZeroCrossing", 
            new Class[]{double.class, double.class}, 0.0, 5.0), 0.0001);
    }

    /**
     * Test Case: Small shift should not trigger zero-crossing.
     * Test Strategy: Edge Case (Small shifts)
     * Expected Behavior: The method should return value + delta.
     */
    @Test
    public void testShiftWithNoZeroCrossing_SmallShift_ShouldWorkNormally() throws Exception {
        assertEquals("Shifting 2 by -1 should return 1", 1.0, 
            (double) invokePrivateStaticMethod("shiftWithNoZeroCrossing", 
            new Class[]{double.class, double.class}, 2.0, -1.0), 0.0001);
    }

    /**
     * Test Case: Negative value shifted by a small negative delta.
     * Test Strategy: Edge Case
     * Expected Behavior: The method should return value + delta.
     */
    @Test
    public void testShiftWithNoZeroCrossing_NegativeToNegative_ShouldShiftNormally() throws Exception {
        assertEquals("Shifting -5 by -2 should return -7", -7.0, 
            (double) invokePrivateStaticMethod("shiftWithNoZeroCrossing", 
            new Class[]{double.class, double.class}, -5.0, -2.0), 0.0001);
    }

    /**
     * Test Case: Two `Range` objects with identical lower and upper bounds should be equal.
     * Test Strategy: Normal Execution
     * Expected Behavior: The method should return `true`.
     */
    @Test
    public void testEquals_IdenticalRanges_ShouldReturnTrue() {
        Range r1 = new Range(5, 10);
        Range r2 = new Range(5, 10);
        assertTrue("Identical ranges should be equal", r1.equals(r2));
    }

    /**
     * Test Case: Comparing `Range` with `null` should return false.
     * Test Strategy: Edge Case (Null handling)
     * Expected Behavior: The method should return `false`.
     */
    @Test
    public void testEquals_NullComparison_ShouldReturnFalse() {
        Range r = new Range(5, 10);
        assertFalse("Comparing to null should return false", r.equals(null));
    }

    /**
     * Test Case: Comparing `Range` with an object of a different class should return false.
     * Test Strategy: Edge Case (Type mismatch)
     * Expected Behavior: The method should return `false`.
     */
    @Test
    public void testEquals_DifferentClass_ShouldReturnFalse() {
        Range r = new Range(5, 10);
        String differentObject = "Not a Range";
        assertFalse("Comparing to different type should return false", r.equals(differentObject));
    }

    /**
     * Test Case: Two `Range` objects with different lower bounds should return false.
     * Test Strategy: Edge Case (Lower bound difference)
     * Expected Behavior: The method should return `false`.
     */
    @Test
    public void testEquals_DifferentLowerBound_ShouldReturnFalse() {
        Range r1 = new Range(5, 10);
        Range r2 = new Range(6, 10);
        assertFalse("Different lower bounds should return false", r1.equals(r2));
    }

    /**
     * Test Case: Two `Range` objects with different upper bounds should return false.
     * Test Strategy: Edge Case (Upper bound difference)
     * Expected Behavior: The method should return `false`.
     */
    @Test
    public void testEquals_DifferentUpperBound_ShouldReturnFalse() {
        Range r1 = new Range(5, 10);
        Range r2 = new Range(5, 12);
        assertFalse("Different upper bounds should return false", r1.equals(r2));
    }

    /**
     * Test Case: If both bounds are NaN, `isNaNRange()` should return `true`.
     * Test Strategy: Normal Execution
     * Expected Behavior: The method should return `true`.
     */
    @Test
    public void testIsNaNRange_BothNaN_ShouldReturnTrue() {
        Range r = new Range(Double.NaN, Double.NaN);
        assertTrue("Range with NaN lower and upper should return true", r.isNaNRange());
    }

    /**
     * Test Case: If only lower bound is NaN, `isNaNRange()` should return `false`.
     * Test Strategy: Edge Case (Partial NaN)
     * Expected Behavior: The method should return `false`.
     */
    @Test
    public void testIsNaNRange_OnlyLowerNaN_ShouldReturnFalse() {
        Range r = new Range(Double.NaN, 10);
        assertFalse("Range with NaN lower but valid upper should return false", r.isNaNRange());
    }

    /**
     * Test Case: If only upper bound is NaN, `isNaNRange()` should return `false`.
     * Test Strategy: Edge Case (Partial NaN)
     * Expected Behavior: The method should return `false`.
     */
    @Test
    public void testIsNaNRange_OnlyUpperNaN_ShouldReturnFalse() {
        Range r = new Range(5, Double.NaN);
        assertFalse("Range with valid lower but NaN upper should return false", r.isNaNRange());
    }

    /**
     * Test Case: If neither bound is NaN, `isNaNRange()` should return `false`.
     * Test Strategy: Normal Execution
     * Expected Behavior: The method should return `false`.
     */
    @Test
    public void testIsNaNRange_NoNaN_ShouldReturnFalse() {
        Range r = new Range(5, 10);
        assertFalse("Range with valid bounds should return false", r.isNaNRange());
    }
    
 // ===== Additional PIT Mutation Tests =====
    
 // --------------------------
 // 🔁 Additional PIT Test Cases
 // --------------------------

 /**
  * Test to kill mutant: intersects() changed >= to >
  */
    @Test
    public void testIntersectsSlightOverlapAtUpperEdge() {
        Range range = new Range(5.0, 10.0);
        assertTrue("Should intersect when the incoming range overlaps the upper bound",
                   range.intersects(9.999, 10.001));
    }


    /**
     * This test verifies how Range.shift(...) behaves when crossZero is false.
     * It uses a base range (-3.0, 2.0) and applies a negative delta (-4.0),
     * which would normally move the upper bound to -2.0, but since crossZero = false,
     * the upper bound is clamped at 0.0 (cannot go negative from positive).
     */
    @Test
    public void testShiftWithNegativeBaseAndCrossZeroFalse() {
        Range range = new Range(-3.0, 2.0);

        // Apply shift of -4.0 with crossZero = false
        Range shifted = Range.shift(range, -4.0, false);

        // Expected behavior: Range cannot "cross" zero into the opposite sign.
        // -3.0 - 4.0 = -7.0 → stays negative
        //  2.0 - 4.0 = -2.0 → would cross zero → clamped to 0.0
        assertEquals("Lower bound can remain negative", -7.0, shifted.getLowerBound(), 0.0001);
        assertEquals("Upper bound should not go negative when crossZero is false", 0.0, shifted.getUpperBound(), 0.0001);
    }


 /**
  * Test to kill mutant: expand() with margin misuse
  */
 @Test
 public void testExpandWithPositiveMargins() {
     Range range = new Range(2.0, 4.0);
     Range expanded = Range.expand(range, 0.5, 0.25);
     assertEquals("Expanded lower bound", 1.0, expanded.getLowerBound(), 0.0001);
     assertEquals("Expanded upper bound", 4.5, expanded.getUpperBound(), 0.0001);
 }

 /**
  * Test to kill mutant: swapped lower/upper bounds in combine()
  */
 @Test
 public void testCombineProducesCorrectBounds() {
     Range r1 = new Range(2.0, 4.0);
     Range r2 = new Range(1.0, 3.0);
     Range combined = Range.combine(r1, r2);
     assertEquals("Combined lower bound", 1.0, combined.getLowerBound(), 0.0001);
     assertEquals("Combined upper bound", 4.0, combined.getUpperBound(), 0.0001);
 }

 /**
  * Test to kill mutant: constrain() changes < to <=
  */
 @Test
 public void testConstrainAtLowerBound() {
     Range range = new Range(1.0, 5.0);
     assertEquals("Constrain should return value at lower bound", 1.0, range.constrain(1.0), 0.0001);
 }

 /**
  * Test to kill mutant: constrain() changes > to >=
  */
 @Test
 public void testConstrainAtUpperBound() {
     Range range = new Range(1.0, 5.0);
     assertEquals("Constrain should return value at upper bound", 5.0, range.constrain(5.0), 0.0001);
 }

 @Test
 public void testShiftCrossZeroFalseFromNegativeToPositive() {
     Range range = new Range(-3.0, -1.0);
     Range shifted = Range.shift(range, 5.0, false);

     // Expect shift to cap at 0.0 instead of going positive
     assertEquals("Should not cross into positive", 0.0, shifted.getLowerBound(), 0.0001);
     assertEquals("Should not cross into positive", 0.0, shifted.getUpperBound(), 0.0001);
 }

 @Test(expected = IllegalArgumentException.class)
 public void testConstructorThrowsWhenLowerGreaterThanUpperWithEdgeValues() {
     new Range(Double.MAX_VALUE, Double.MIN_VALUE);
 }

 @Test
 public void testGetCentralValueWithNegativeRange() {
     Range r = new Range(-4.0, -2.0);
     assertEquals(-3.0, r.getCentralValue(), 0.0001);
 }

 @Test
 public void testContainsJustOutsideBounds() {
     Range r = new Range(1.0, 5.0);
     assertFalse(r.contains(5.000001));
     assertFalse(r.contains(0.999999));
 }

 @Test
 public void testIntersectsJustOverlappingLowerAndUpper() {
     Range r = new Range(2.0, 6.0);
     assertTrue(r.intersects(1.5, 2.5)); // overlaps lower
     assertTrue(r.intersects(5.5, 6.5)); // overlaps upper
 }

 @Test
 public void testIntersectsJustOutsideBounds() {
     Range r = new Range(2.0, 6.0);
     assertFalse(r.intersects(0.0, 2.0)); // no real overlap
     assertFalse(r.intersects(6.0, 7.0)); // just touches upper
 }

 @Test
 public void testConstrainSlightlyOutOfRange() {
     Range r = new Range(10.0, 20.0);
     assertEquals(10.0, r.constrain(9.999999), 0.000001);
     assertEquals(20.0, r.constrain(20.000001), 0.000001);
 }
 
 @Test
 public void testCombineIgnoringNaN_BothNullReturnsNull() {
     // Should return null if both inputs are null
     Range result = Range.combineIgnoringNaN(null, null);
     assertNull("Expected null when both ranges are null", result);
 }

 @Test
 public void testCombineIgnoringNaN_OneRangeHasNaN() {
     // One range contains only NaN values, which should be ignored
     Range r1 = new Range(Double.NaN, Double.NaN);
     Range result = Range.combineIgnoringNaN(r1, null);
     assertNull("Expected null because r1 is effectively invalid", result);
 }

 @Test
 public void testExpandToInclude_WithNaNValue() {
     Range r = new Range(1.0, 5.0);
     // Including NaN should return the original range unchanged
     Range result = Range.expandToInclude(r, Double.NaN);
     assertEquals("Lower bound should remain the same", 1.0, result.getLowerBound(), 0.00001);
     assertEquals("Upper bound should remain the same", 5.0, result.getUpperBound(), 0.00001);
 }

 @Test
 public void testGetLengthWithTinyRange() {
     // Very close upper and lower bounds, should still calculate length
     Range r = new Range(1.0000001, 1.0000002);
     assertEquals("Expected precise length calculation", 0.0000001, r.getLength(), 1e-10);
 }

 @Test
 public void testShiftAcrossZeroWithCrossZeroFalse() {
     Range r = new Range(-5.0, -1.0);
     Range shifted = Range.shift(r, 10.0, false); // would normally become (5.0, 9.0)

     // But crossZero=false prevents crossing from negative to positive
     assertEquals("Lower bound should be clamped at 0", 0.0, shifted.getLowerBound(), 0.0001);
     assertEquals("Upper bound should be clamped at 0", 0.0, shifted.getUpperBound(), 0.0001);
 }
 
//Test for getLength() where lower == upper (edge case: zero-length range)
@Test
public void testGetLengthZero() {
  Range r = new Range(3.0, 3.0);
  assertEquals("Length of zero-width range", 0.0, r.getLength(), 0.0000001);
}

//Test equals() where the ranges are the same object
@Test
public void testEqualsSameObject() {
  Range r = new Range(1.0, 2.0);
  assertTrue("Range should be equal to itself", r.equals(r));
}

//Test equals() with a null object
@Test
public void testEqualsNull() {
  Range r = new Range(1.0, 2.0);
  assertFalse("Range should not equal null", r.equals(null));
}

//Test equals() with an object of different type
@Test
public void testEqualsDifferentClass() {
  Range r = new Range(1.0, 2.0);
  assertFalse("Range should not equal object of different class", r.equals("not a range"));
}

//Test hashCode() consistency
@Test
public void testHashCodeConsistency() {
  Range r1 = new Range(1.0, 2.0);
  Range r2 = new Range(1.0, 2.0);
  assertEquals("Equal ranges should have the same hash code", r1.hashCode(), r2.hashCode());
}

//Test constrain() on lower bound
@Test
public void testConstrainAtLowerBound1() {
  Range r = new Range(1.0, 5.0);
  assertEquals("Constrain at lower bound", 1.0, r.constrain(1.0), 0.0000001);
}

//Test constrain() on upper bound
@Test
public void testConstrainAtUpperBound1() {
  Range r = new Range(1.0, 5.0);
  assertEquals("Constrain at upper bound", 5.0, r.constrain(5.0), 0.0000001);
}

//Test toString() output format
@Test
public void testToStringFormat() {
  Range r = new Range(1.0, 3.0);
  String expected = "Range[1.0,3.0]";
  assertEquals("String representation of Range", expected, r.toString());
}

//Test clamp() method when value equals lower bound
//Test intersects() with b1 == lower (exclusive edge)
@Test
public void testIntersectsTouchesLowerBoundOnly() {
 Range r = new Range(2.0, 6.0);
 // b0 = 1.0, b1 = 2.0 → b1 == lower → should be false
 assertFalse("Should not intersect at exact lower bound", r.intersects(1.0, 2.0));
}


//Test clamp() method when value equals upper bound
//Test intersects() with b0 == upper (exclusive edge)
@Test
public void testIntersectsTouchesUpperBoundOnly() {
 Range r = new Range(2.0, 6.0);
 // b0 = 6.0, b1 = 7.0 → b0 == upper → should be false
 assertFalse("Should not intersect at exact upper bound", r.intersects(6.0, 7.0));
}


//Test scale() method with a negative factor (should throw exception)
@Test(expected = IllegalArgumentException.class)
public void testScaleWithNegativeFactor() {
 Range range = new Range(2.0, 4.0);
 Range result = Range.scale(range, -1.5);
}

//Test expandToInclude() with value exactly at the upper bound
@Test
public void testExpandToIncludeValueAtUpperBound() {
 Range range = new Range(2.0, 5.0);
 Range expanded = Range.expandToInclude(range, 5.0);
 assertEquals("Should return the same range", range, expanded);
}

//Test intersects() with non-overlapping range (value just outside upper bound)
@Test
public void testIntersectsJustAboveUpperBound() {
 Range range = new Range(2.0, 5.0);
 assertFalse("Should not intersect", range.intersects(5.0001, 6.0));
}

//Test intersects() with non-overlapping range (value just below lower bound)
@Test
public void testIntersectsJustBelowLowerBound() {
 Range range = new Range(2.0, 5.0);
 assertFalse("Should not intersect", range.intersects(1.0, 1.9999));
}

//Test getCentralValue() for a large range
@Test
public void testGetCentralValueWithLargeRange() {
 Range range = new Range(-1000000.0, 1000000.0);
 assertEquals("Central value should be 0", 0.0, range.getCentralValue(), 0.00001);
}

///////////////////
///

@Test
public void testHashCode_ConsistentForEqualRanges() {
    Range r1 = new Range(1.0, 5.0);
    Range r2 = new Range(1.0, 5.0);
    assertEquals("Equal ranges must have same hash code", r1.hashCode(), r2.hashCode());
}


@Test
public void testHashCodeSymmetry() {
    Range a = new Range(1.0, 4.0);
    Range b = new Range(1.0, 4.0);
    assertEquals("Equal objects must have same hashCode", a.hashCode(), b.hashCode());
}

@Test
public void testHashCode_DifferentRangesLikelyDifferentHash() {
    Range r1 = new Range(1.0, 5.0);
    Range r2 = new Range(2.0, 6.0);
    assertNotEquals("Different ranges likely have different hash codes", r1.hashCode(), r2.hashCode());
}

@Test
public void testEqualsAndHashCodeContract() {
    Range a = new Range(0.0, 10.0);
    Range b = new Range(0.0, 10.0);
    assertTrue("Ranges should be equal", a.equals(b));
    assertEquals("Equal ranges must have same hash code", a.hashCode(), b.hashCode());
}

@Test
public void testHashCode_SelfConsistent() {
    Range r = new Range(-3.0, 7.0);
    int hash1 = r.hashCode();
    int hash2 = r.hashCode();
    assertEquals("hashCode should be consistent across calls", hash1, hash2);
}


@Test
public void testHashCodeDistinctForDifferentRanges() {
    Range r1 = new Range(1.0, 5.0);
    Range r2 = new Range(2.0, 6.0); // valid and different
    assertNotEquals("hashCode must differ for different ranges", r1.hashCode(), r2.hashCode());
}

@Test
public void testGetCentralValueWithPositiveAndNegative() {
    Range r = new Range(-5.0, 5.0);
    assertEquals(0.0, r.getCentralValue(), 0.0000001);
}

@Test
public void testIntersectsTouchingLowerBoundOnly() {
    Range r = new Range(3.0, 8.0);
    assertFalse(r.intersects(3.0, 3.0));  // Only touching, should be false
}

@Test
public void testIntersectsOverlapAtLowerBound() {
    Range r = new Range(3.0, 8.0);
    assertTrue(r.intersects(2.0, 4.0));  // b1 > lower
}

@Test
public void testCombineIgnoringNaN_NaNs() {
    Range r1 = null;
    Range r2 = new Range(Double.NaN, Double.NaN);
    assertNull(Range.combineIgnoringNaN(r1, r2));
}

@Test
public void testCombineIgnoringNaN_SecondRangeNaN_ReturnsFirst() {
    Range r1 = new Range(1.0, 4.0);
    Range r2 = new Range(Double.NaN, Double.NaN);
    Range result = Range.combineIgnoringNaN(r1, r2);

    assertEquals(1.0, result.getLowerBound(), 0.0001);
    assertEquals(4.0, result.getUpperBound(), 0.0001);
}


@Test
public void testCombineIgnoringNaN_FirstRangeNaN_ReturnsSecond() {
    Range r1 = new Range(Double.NaN, Double.NaN);
    Range r2 = new Range(2.0, 5.0);
    Range result = Range.combineIgnoringNaN(r1, r2);

    assertEquals(2.0, result.getLowerBound(), 0.0001);
    assertEquals(5.0, result.getUpperBound(), 0.0001);
}


@Test
public void testExpandToIncludeWithNaN() {
    Range r = new Range(0.0, 10.0);
    Range result = Range.expandToInclude(r, Double.NaN);

    // Should return the original range
    assertEquals(0.0, result.getLowerBound(), 0.0001);
    assertEquals(10.0, result.getUpperBound(), 0.0001);
}

@Test
public void testCombine_AllowsNaNs() {
    Range r1 = new Range(1.0, 5.0);
    Range r2 = new Range(Double.NaN, Double.NaN);
    Range result = Range.combine(r1, r2);
    assertTrue(Double.isNaN(result.getLowerBound()) || Double.isNaN(result.getUpperBound()));
}

@Test
public void testCombineIgnoringNaN_LeftNaN_RightValid() {
    Range left = new Range(Double.NaN, Double.NaN);
    Range right = new Range(1.0, 5.0);
    Range result = Range.combineIgnoringNaN(left, right);
    // Expect the right range to be returned
    assertEquals("Lower bound should match right", 1.0, result.getLowerBound(), 0.0001);
    assertEquals("Upper bound should match right", 5.0, result.getUpperBound(), 0.0001);
}

@Test
public void testCombineIgnoringNaN_OneNaNBoundEach() {
    Range r1 = new Range(Double.NaN, 10.0);
    Range r2 = new Range(2.0, Double.NaN);
    Range result = Range.combineIgnoringNaN(r1, r2);
    // Lower bound: 2.0 from r2, Upper bound: 10.0 from r1
    assertEquals(2.0, result.getLowerBound(), 0.0001);
    assertEquals(10.0, result.getUpperBound(), 0.0001);
}

@Test
public void testCombineIgnoringNaN_BothNaN() {
    Range r1 = new Range(Double.NaN, Double.NaN);
    Range r2 = new Range(Double.NaN, Double.NaN);
    Range result = Range.combineIgnoringNaN(r1, r2);
    assertNull("Expected null when both ranges are NaN", result);
}

@Test
public void testConstrainExactlyOnLowerBound() {
    Range r = new Range(1.0, 5.0);
    assertEquals(1.0, r.constrain(1.0), 0.0001);
}

@Test
public void testConstrainExactlyOnUpperBound() {
    Range r = new Range(1.0, 5.0);
    assertEquals(5.0, r.constrain(5.0), 0.0001);
}

@Test
public void testExpandToInclude_ExactUpper() {
    Range r = new Range(2.0, 5.0);
    Range result = Range.expandToInclude(r, 5.0);  // No change
    assertEquals(2.0, result.getLowerBound(), 0.0001);
    assertEquals(5.0, result.getUpperBound(), 0.0001);
}

@Test
public void testExpandToInclude_ExactLower() {
    Range r = new Range(2.0, 5.0);
    Range result = Range.expandToInclude(r, 2.0);  // No change
    assertEquals(2.0, result.getLowerBound(), 0.0001);
    assertEquals(5.0, result.getUpperBound(), 0.0001);
}

@Test
public void testMinMaxLogic_NaNHandling() {
    Range r1 = new Range(2.0, 3.0);
    Range r2 = new Range(Double.NaN, Double.NaN);
    Range result = Range.combineIgnoringNaN(r1, r2);
    assertEquals(2.0, result.getLowerBound(), 0.0001);
    assertEquals(3.0, result.getUpperBound(), 0.0001);
}

@Test
public void testCombine_SameRangeTwice() {
    Range r = new Range(1.0, 4.0);
    Range result = Range.combine(r, r);
    assertEquals(1.0, result.getLowerBound(), 0.0001);
    assertEquals(4.0, result.getUpperBound(), 0.0001);
}

@Test
public void testConstrain_ValueInsideRange_ShouldReturnSame() {
    Range r = new Range(2.0, 5.0);
    assertEquals(3.5, r.constrain(3.5), 0.0000001);
}

@Test
public void testCombine_Range1SmallerRange2Larger() {
    Range r1 = new Range(1.0, 3.0);
    Range r2 = new Range(2.0, 6.0);
    Range combined = Range.combine(r1, r2);
    assertEquals(1.0, combined.getLowerBound(), 0.00001);
    assertEquals(6.0, combined.getUpperBound(), 0.00001);
}

@Test
public void testCombineIgnoringNaN_OneRangeIsNaN() {
    Range r1 = new Range(2.0, 5.0);
    Range r2 = new Range(Double.NaN, Double.NaN);  // isNaNRange is true
    Range result = Range.combineIgnoringNaN(r1, r2);
    assertEquals(r1, result); // r2 is ignored
}

@Test
public void testCombineIgnoringNaN_BothRangesNaN() {
    Range r1 = new Range(Double.NaN, Double.NaN);
    Range r2 = new Range(Double.NaN, Double.NaN);
    assertNull(Range.combineIgnoringNaN(r1, r2));
}

@Test
public void testExpandToInclude_ExactlyAtLowerBound() {
    Range r = new Range(2.0, 5.0);
    Range expanded = Range.expandToInclude(r, 2.0);
    assertEquals(2.0, expanded.getLowerBound(), 0.00001);
    assertEquals(5.0, expanded.getUpperBound(), 0.00001);
}

@Test
public void testCombineIgnoringNaN_MaxLogicViaUpperBound() {
    // max(4.0, Double.NaN) should return 4.0 internally
    Range r1 = new Range(1.0, 4.0);
    Range r2 = new Range(Double.NaN, Double.NaN);
    Range result = Range.combineIgnoringNaN(r1, r2);

    assertEquals("Expected max logic to return 4.0 as upper bound", 4.0, result.getUpperBound(), 0.0001);
}


@Test
public void testCombineIgnoringNaN_MinLogicViaLowerBound() {
    // min(Double.NaN, 3.0) should return 3.0 internally
    Range r1 = new Range(Double.NaN, 4.0);
    Range r2 = new Range(3.0, 6.0);
    Range result = Range.combineIgnoringNaN(r1, r2);

    assertEquals("Expected min logic to return 3.0 as lower bound", 3.0, result.getLowerBound(), 0.0001);
}

@Test
public void testCombineIgnoringNaN_BothRangesWithNaNBounds() {
    Range r1 = new Range(Double.NaN, 3.0);
    Range r2 = new Range(1.0, Double.NaN);
    Range result = Range.combineIgnoringNaN(r1, r2);
    assertEquals("Should choose 1.0 as lower and 3.0 as upper", 1.0, result.getLowerBound(), 0.0001);
    assertEquals(3.0, result.getUpperBound(), 0.0001);
}

@Test
public void testCombineIgnoringNaN_FirstNull_SecondNaN() {
    Range result = Range.combineIgnoringNaN(null, new Range(Double.NaN, Double.NaN));
    assertNull("Should return null when second is NaN range and first is null", result);
}

@Test
public void testCombineIgnoringNaN_SecondNull_FirstNaN() {
    Range result = Range.combineIgnoringNaN(new Range(Double.NaN, Double.NaN), null);
    assertNull("Should return null when first is NaN range and second is null", result);
}

@Test
public void testCombineIgnoringNaN_SecondHasNaNLowerOnly() {
    Range r1 = new Range(1.0, 3.0);
    Range r2 = new Range(Double.NaN, 4.0);
    Range result = Range.combineIgnoringNaN(r1, r2);
    assertEquals("Lower bound should be 1.0", 1.0, result.getLowerBound(), 0.0001);
    assertEquals("Upper bound should be 4.0", 4.0, result.getUpperBound(), 0.0001);
}

@Test
public void testCombineIgnoringNaN_FirstHasNaNUpperOnly() {
    Range r1 = new Range(1.0, Double.NaN);
    Range r2 = new Range(2.0, 4.0);
    Range result = Range.combineIgnoringNaN(r1, r2);
    assertEquals("Lower bound should be 1.0", 1.0, result.getLowerBound(), 0.0001);
    assertEquals("Upper bound should be 4.0", 4.0, result.getUpperBound(), 0.0001);
}

@Test
public void testExpandToInclude_ExactUpperBound() {
    Range range = new Range(1.0, 5.0);
    Range result = Range.expandToInclude(range, 5.0);
    assertEquals(range, result);
}

@Test
public void testExpandToInclude_ExactLowerBound() {
    Range range = new Range(1.0, 5.0);
    Range result = Range.expandToInclude(range, 1.0);
    assertEquals(range, result);
}

@Test
public void testIntersectsSameRange() {
    Range r1 = new Range(2.0, 6.0);
    assertTrue("Range should intersect itself", r1.intersects(r1));
}

@Test
public void testIntersectsWithNaNValues() {
    Range range = new Range(1.0, 5.0);
    // NaN should not intersect with any valid range
    assertFalse("Range should not intersect with NaN start", range.intersects(Double.NaN, 4.0));
    assertFalse("Range should not intersect with NaN end", range.intersects(2.0, Double.NaN));
}

@Test
public void testShiftWithoutZeroCrossingWhenShiftGoesNegative() {
    Range range = new Range(1.0, 3.0);
    Range shifted = Range.shift(range, -5.0, false);
    // Should clamp to zero instead of going negative
    assertEquals("Lower bound should clamp to 0", 0.0, shifted.getLowerBound(), 0.0001);
    assertEquals("Upper bound should clamp to 0", 0.0, shifted.getUpperBound(), 0.0001);
}

@Test
public void testExpandToIncludeWhenValueEqualsBounds() {
    Range range = new Range(2.0, 4.0);
    Range sameLower = Range.expandToInclude(range, 2.0);
    Range sameUpper = Range.expandToInclude(range, 4.0);
    assertEquals("Should return same range", range, sameLower);
    assertEquals("Should return same range", range, sameUpper);
}

@Test
public void testEqualsWithNullAndDifferentType() {
    Range range = new Range(2.0, 5.0);
    assertNotEquals("Should not equal null", null, range);
    assertNotEquals("Should not equal different type", "some string", range);
}

@Test
public void testCombineIgnoringNaNWithAllNaN() {
    Range r1 = new Range(Double.NaN, Double.NaN);
    Range r2 = new Range(Double.NaN, Double.NaN);
    Range combined = Range.combineIgnoringNaN(r1, r2);
    assertNull("Combining all-NaN ranges should return null", combined);
}

@Test
public void testGetLengthForZeroLengthRange() {
    Range zero = new Range(3.0, 3.0);
    assertEquals("Length of zero range should be 0", 0.0, zero.getLength(), 0.0001);
}

@Test
public void testShiftWithNullBase() {
    try {
        Range.shift(null, 1.0);
        fail("Expected IllegalArgumentException for null base range.");
    } catch (IllegalArgumentException e) {
        // Expected behavior
    }
}

@Test
public void testExpandZeroExpansion() {
    Range base = new Range(5.0, 10.0);
    Range expanded = Range.expand(base, 0.0, 0.0);
    assertEquals("Expanded range should be same as original", base, expanded);
}

@Test
public void testExpandCollapseToSinglePoint() {
    Range base = new Range(3.0, 5.0);
    Range collapsed = Range.expand(base, -0.5, -0.5); // Will collapse to midpoint
    assertEquals("Collapsed range should be at midpoint", new Range(4.0, 4.0), collapsed);
}

@Test
public void testCombineBothNull() {
    assertNull("Combining two null ranges should return null", Range.combine(null, null));
}

//Tests for expandToInclude with NaN
@Test
public void testExpandToIncludeNaNValue() {
 Range base = new Range(1.0, 5.0);
 Range result = Range.expandToInclude(base, Double.NaN);
 assertEquals(base, result); // Should return the original range
}

//Tests for shift with extreme delta
@Test
public void testShiftWithLargeDelta() {
 Range base = new Range(1.0, 2.0);
 Range result = Range.shift(base, 1e6);
 assertEquals(1000001.0, result.getLowerBound(), 0.0001);
 assertEquals(1000002.0, result.getUpperBound(), 0.0001);
}

//Tests for combine with extreme ranges
@Test
public void testCombineExtremeMinAndMax() {
 Range r1 = new Range(-Double.MAX_VALUE, -1e100);
 Range r2 = new Range(1e100, Double.MAX_VALUE);
 Range combined = Range.combine(r1, r2);
 assertEquals(-Double.MAX_VALUE, combined.getLowerBound(), 0.0001);
 assertEquals(Double.MAX_VALUE, combined.getUpperBound(), 0.0001);
}

//Tests for equals with null
@Test
public void testEqualsWithNull1() {
 Range r1 = new Range(1.0, 2.0);
 assertFalse(r1.equals(null));
}

@Test
public void testScaleWithNaNFactor_ReturnsNaNBounds() {
    Range base = new Range(1.0, 5.0);
    Range result = Range.scale(base, Double.NaN);
    assertTrue(Double.isNaN(result.getLowerBound()));
    assertTrue(Double.isNaN(result.getUpperBound()));
}

@Test
public void testScaleWithInfiniteFactor() {
    Range base = new Range(1.0, 5.0);
    Range result = Range.scale(base, Double.POSITIVE_INFINITY);
    assertTrue(Double.isInfinite(result.getLowerBound()));
    assertTrue(Double.isInfinite(result.getUpperBound()));
}

@Test
public void testScaleWithSmallDecimalFactor_MatchActualBehavior() {
    Range base = new Range(1.0, 2.0);
    Range result = Range.scale(base, 0.001);

    assertEquals(0.001, result.getLowerBound(), 0.0001);
    assertEquals(0.002, result.getUpperBound(), 0.0001);
}




@Test
public void testExpandWithExtremeMargins_Corrected() {
    Range base = new Range(2.0, 3.0);
    Range result = Range.expand(base, 1000.0, 1000.0);
    assertEquals(-998.0, result.getLowerBound(), 0.0001);
    assertEquals(1003.0, result.getUpperBound(), 0.0001);
}

@Test
public void testScaleWithNegativeRangeAndPositiveFactor() {
    Range range = new Range(-2.0, -1.0);
    Range result = Range.scale(range, 2.0);
    // Expect scaling to expand the range from [-2.0, -1.0] to [-4.0, -2.0]
    assertEquals(-4.0, result.getLowerBound(), 0.0001);
    assertEquals(-2.0, result.getUpperBound(), 0.0001);
}

@Test
public void testEqualsDifferentPrecisionValues() {
    Range r1 = new Range(1.0000001, 2.0000001);
    Range r2 = new Range(1.0000002, 2.0000002);
    assertFalse("Ranges with minor difference should not be equal", r1.equals(r2));
}

@Test
public void testContainsValueVeryCloseToLowerBound() {
    Range range = new Range(5.0, 10.0);
    assertTrue(range.contains(5.00000000001));
}

@Test
public void testHashCodeWithNegativeBounds() {
    Range range = new Range(-3.0, -1.0);
    int hash = range.hashCode();
    assertNotEquals("Hash code should not be zero for valid range", 0, hash);
}

//Case: Shift range with delta of 0.0 and allowZeroCrossing = true
@Test
public void testShiftZeroDeltaAllowZeroCrossingTrue() {
 Range base = new Range(-1.0, 1.0);
 Range result = Range.shift(base, 0.0, true);
 assertEquals(-1.0, result.getLowerBound(), 0.0001);
 assertEquals(1.0, result.getUpperBound(), 0.0001);
 // Targets mutants that affect handling of zero shift with zero crossing allowed
}

//Case: Shift range with delta of 0.0 and allowZeroCrossing = false
@Test
public void testShiftZeroDeltaAllowZeroCrossingFalse() {
 Range base = new Range(0.0, 2.0);
 Range result = Range.shift(base, 0.0, false);
 assertEquals(0.0, result.getLowerBound(), 0.0001);
 assertEquals(2.0, result.getUpperBound(), 0.0001);
 // Targets mutant that might modify bounds when shift is 0
}

//Case: combineIgnoringNaN when both are valid ranges
@Test
public void testCombineIgnoringNaNWithTwoValidRanges() {
 Range r1 = new Range(1.0, 2.0);
 Range r2 = new Range(3.0, 4.0);
 Range result = Range.combineIgnoringNaN(r1, r2);
 assertEquals(1.0, result.getLowerBound(), 0.0001);
 assertEquals(4.0, result.getUpperBound(), 0.0001);
 // Targets mutants that mishandle combining logic
}

//Case: expand with lowerMargin and upperMargin = 0.0
@Test
public void testExpandWithZeroMargins() {
 Range base = new Range(2.0, 4.0);
 Range result = Range.expand(base, 0.0, 0.0);
 assertEquals(2.0, result.getLowerBound(), 0.0001);
 assertEquals(4.0, result.getUpperBound(), 0.0001);
 // Kills mutants that apply margins even when zero
}

//Case: scale a symmetric range with small decimal factor
@Test
public void testScaleSymmetricSmallDecimalFactor() {
 Range base = new Range(-1.0, 1.0);
 Range result = Range.scale(base, 0.001);
 assertEquals(-0.001, result.getLowerBound(), 0.0001);
 assertEquals(0.001, result.getUpperBound(), 0.0001);
 // Mutants that incorrectly round or flip bounds will be caught here
}

//Case: equals for a self-reference
@Test
public void testEqualsSelf() {
 Range r = new Range(5.0, 10.0);
 assertTrue(r.equals(r));
 // Mutants that mess with reference checks in equals()
}

//Case: equals with both bounds equal but different object
@Test
public void testEqualsDifferentInstanceSameBounds() {
 Range r1 = new Range(3.0, 7.0);
 Range r2 = new Range(3.0, 7.0);
 assertTrue(r1.equals(r2));
 // Targets mutants that compare by reference instead of value
}

//Case: equals with different lower bound
@Test
public void testEqualsDifferentLowerBound() {
 Range r1 = new Range(2.0, 6.0);
 Range r2 = new Range(3.0, 6.0);
 assertFalse(r1.equals(r2));
 // Catches mutations that skip lower bound comparison
}

//Case: equals with different upper bound
@Test
public void testEqualsDifferentUpperBound() {
 Range r1 = new Range(2.0, 6.0);
 Range r2 = new Range(2.0, 7.0);
 assertFalse(r1.equals(r2));
 // Catches mutants skipping upper bound comparison
}

//Case: equals with null
@Test
public void testEqualsNull1() {
 Range r = new Range(2.0, 5.0);
 assertFalse(r.equals(null));
 // Filters out mutations replacing null-check logic
}

@Test
public void testHashCode_SameRangeProducesSameHash() {
    Range r1 = new Range(1.0, 5.0);
    Range r2 = new Range(1.0, 5.0);
    assertEquals("Equal ranges must have same hashCode", r1.hashCode(), r2.hashCode());
}

@Test
public void testHashCode_DifferentLowerBound() {
    Range r1 = new Range(1.0, 5.0);
    Range r2 = new Range(2.0, 5.0);
    assertNotEquals("Ranges with different lower bounds must have different hashCode", r1.hashCode(), r2.hashCode());
}

@Test
public void testHashCode_DifferentUpperBound() {
    Range r1 = new Range(1.0, 5.0);
    Range r2 = new Range(1.0, 6.0);
    assertNotEquals("Ranges with different upper bounds must have different hashCode", r1.hashCode(), r2.hashCode());
}

@Test
public void testHashCode_DifferentRangeEntirely() {
    Range r1 = new Range(-100.0, -50.0);
    Range r2 = new Range(50.0, 100.0);
    assertNotEquals("Completely different ranges must have different hashCodes", r1.hashCode(), r2.hashCode());
}

@Test
public void testExpandWithNegativeMargins() {
    Range base = new Range(2.0, 4.0);
    Range result = Range.expand(base, -0.5, -0.5);

    // Range collapses to the midpoint: (2 + 4) / 2 = 3.0
    assertEquals(3.0, result.getLowerBound(), 0.00001);
    assertEquals(3.0, result.getUpperBound(), 0.00001);
}


@Test
public void testExpandToIncludeLowerThanRange() {
    Range original = new Range(2.0, 5.0);
    Range result = Range.expandToInclude(original, 1.0);
    assertEquals(1.0, result.getLowerBound(), 0.00001);
    assertEquals(5.0, result.getUpperBound(), 0.00001);
}

@Test
public void testExpandToIncludeGreaterThanRange() {
    Range original = new Range(2.0, 5.0);
    Range result = Range.expandToInclude(original, 6.0);
    assertEquals(2.0, result.getLowerBound(), 0.00001);
    assertEquals(6.0, result.getUpperBound(), 0.00001);
}

@Test
public void testEqualsDifferentLowerSameUpper() {
    Range a = new Range(2.0, 5.0);
    Range b = new Range(3.0, 5.0); // Different lower
    assertFalse("Should not be equal", a.equals(b));
}

@Test
public void testIntersectsOutsideRange() {
    Range r = new Range(2.0, 5.0);
    assertFalse("Should not intersect", r.intersects(6.0, 7.0));
}

@Test
public void testEqualsWhenUpperJustSlightlyGreater() {
    Range r1 = new Range(1.0, 5.0);
    Range r2 = new Range(1.0, 5.0000001);
    assertFalse("Ranges with slightly different upper bounds should not be equal", r1.equals(r2));
}

@Test
public void testEqualsWhenUpperIsIncrementedByOne() {
    Range r1 = new Range(1.0, 5.0);
    Range r2 = new Range(1.0, 6.0);
    assertFalse("Upper bounds differ by 1, should not be equal", r1.equals(r2));
}

@Test
public void testShiftAcrossZeroWithClampToZero() {
    Range base = new Range(-3.0, -2.0);
    Range result = Range.shift(base, 3.5, false);

    // In your implementation, both bounds clamp to 0.0
    assertEquals(0.0, result.getLowerBound(), 0.0001);
    assertEquals(0.0, result.getUpperBound(), 0.0001);
}



@Test
public void testCombineIgnoringNaN_OneValidOneNaN() {
    Range r1 = new Range(Double.NaN, 4.0);
    Range r2 = new Range(2.0, 3.0);
    Range result = Range.combineIgnoringNaN(r1, r2);
    assertEquals(2.0, result.getLowerBound(), 0.0001);
    assertEquals(4.0, result.getUpperBound(), 0.0001);
}

@Test
public void testExpandCollapseToMidpoint() {
    Range base = new Range(1.0, 2.0);
    Range result = Range.expand(base, -1.0, -1.0); // total collapse
    assertEquals(1.5, result.getLowerBound(), 0.0001);
    assertEquals(1.5, result.getUpperBound(), 0.0001);
}

@Test
public void testCombineIgnoringNaN_WithUpperNaN() {
    Range r1 = new Range(1.0, Double.NaN);
    Range r2 = new Range(0.0, 5.0);
    Range result = Range.combineIgnoringNaN(r1, r2);
    assertEquals(0.0, result.getLowerBound(), 0.0001);
    assertEquals(5.0, result.getUpperBound(), 0.0001);
}

@Test
public void testExpandToInclude_EqualToUpperBound() {
    Range base = new Range(2.0, 5.0);
    Range result = Range.expandToInclude(base, 5.0);
    assertEquals(2.0, result.getLowerBound(), 0.0001);
    assertEquals(5.0, result.getUpperBound(), 0.0001);
}

@Test
public void testEqualsSymmetry() {
    Range a = new Range(1.0, 4.0);
    Range b = new Range(1.0, 4.0);
    assertTrue(a.equals(b));
    assertTrue(b.equals(a));
}

@Test
public void testIntersectsJustOutsideUpper() {
    Range r = new Range(1.0, 5.0);
    assertFalse("Range should not intersect beyond upper", r.intersects(5.000001, 6.0));
}

@Test
public void testGetCentralValueHighPrecision() {
    Range r = new Range(1.0000001, 1.0000003);
    double center = r.getCentralValue();
    assertEquals(1.0000002, center, 0.0000001);
}

@Test
public void testExpandWithVerySmallMargins() {
    Range base = new Range(2.0, 5.0);
    Range expanded = Range.expand(base, 0.001, 0.001);
    // Expected lower = 2 - 3 * 0.001 = 1.997, upper = 5 + 3 * 0.001 = 5.003
    assertEquals(1.997, expanded.getLowerBound(), 0.0001);
    assertEquals(5.003, expanded.getUpperBound(), 0.0001);
}

@Test
public void testScaleWithTinyFactor_Fixed() {
    Range base = new Range(2.0, 4.0);
    Range result = Range.scale(base, 0.0001);

    assertEquals(0.0002, result.getLowerBound(), 0.0000001);
    assertEquals(0.0004, result.getUpperBound(), 0.0000001);
}


@Test
public void testCombineWithSameRanges() {
    Range base = new Range(3.0, 7.0);
    Range combined = Range.combine(base, base);
    assertEquals(base, combined);
}

@Test
public void testShiftCrossZeroFalsePositiveDelta_FixedAgain() {
    Range base = new Range(-2.0, 2.0);
    Range shifted = Range.shift(base, 1.0, false);

    // Expected: lower = -1.0, upper = 3.0
    assertEquals(-1.0, shifted.getLowerBound(), 0.0001);
    assertEquals(3.0, shifted.getUpperBound(), 0.0001);
}


@Test
public void testShiftCrossZeroFalseNegativeDelta_FixedAgain() {
    Range base = new Range(-1.0, 3.0);
    Range shifted = Range.shift(base, -2.0, false);

    // Expected: lower = -3.0, upper = 1.0
    assertEquals(-3.0, shifted.getLowerBound(), 0.0001);
    assertEquals(1.0, shifted.getUpperBound(), 0.0001);
}

@Test
public void testGetCentralValue_Precision() {
    Range r = new Range(0.3333333, 0.6666667);
    assertEquals(0.5, r.getCentralValue(), 0.0000001);
}

@Test
public void testConstrain_BoundaryEdgeCases() {
    Range r = new Range(1.0, 5.0);
    assertEquals(1.0, r.constrain(1.0), 0.0001); // lower bound
    assertEquals(5.0, r.constrain(5.0), 0.0001); // upper bound
}

@Test
public void testCombineIgnoringNaN_LeftNaNRightValid() {
    Range r1 = new Range(Double.NaN, Double.NaN);
    Range r2 = new Range(2.0, 4.0);
    Range result = Range.combineIgnoringNaN(r1, r2);
    assertEquals(2.0, result.getLowerBound(), 0.0001);
    assertEquals(4.0, result.getUpperBound(), 0.0001);
}

@Test
public void testCombineIgnoringNaN_BothValid() {
    Range r1 = new Range(1.0, 2.0);
    Range r2 = new Range(3.0, 5.0);
    Range result = Range.combineIgnoringNaN(r1, r2);
    assertEquals(1.0, result.getLowerBound(), 0.0001);
    assertEquals(5.0, result.getUpperBound(), 0.0001);
}

@Test
public void testExpand_NegativeMargins() {
    Range r = new Range(4.0, 6.0); // length = 2.0
    Range result = Range.expand(r, -0.25, -0.25);
    assertEquals(4.5, result.getLowerBound(), 0.0001);
    assertEquals(5.5, result.getUpperBound(), 0.0001);
}

@Test
public void testShift_WithZeroCrossingTrue() {
    Range r = new Range(-2.0, 2.0);
    Range result = Range.shift(r, 3.0, true);
    assertEquals(1.0, result.getLowerBound(), 0.0001);
    assertEquals(5.0, result.getUpperBound(), 0.0001);
}

@Test
public void testShift_WithZeroCrossingFalse_FinalConfirmed() {
    Range base = new Range(-2.0, 2.0);
    Range result = Range.shift(base, 3.0, false);

    // Lower bound is clamped because original was negative
    assertEquals(0.0, result.getLowerBound(), 0.0001);
    // Upper bound is shifted normally
    assertEquals(5.0, result.getUpperBound(), 0.0001);
}



@Test
public void testIntersects_TouchingBounds_Fixed() {
    Range r = new Range(1.0, 5.0);

    // 1.0 and 5.0 are exclusive in your implementation (intersects uses strict inequality)
    assertFalse("Range should not intersect when touching upper", r.intersects(5.0, 6.0));
    assertFalse("Range should not intersect when touching lower", r.intersects(0.0, 1.0));

    // Proper intersection
    assertTrue("Actual overlap should return true", r.intersects(2.0, 4.0));
}


@Test(expected = IllegalArgumentException.class)
public void testConstructor_InvalidRange() {
    new Range(10.0, 5.0); // Invalid: lower > upper
}

@Test
public void testContains_NearFloatingPointErrors() {
    Range r = new Range(1.0, 2.0);
    assertTrue(r.contains(1.0000000000000001));
    assertTrue(r.contains(1.9999999999999999));
}

@Test
public void testGetLengthWithNegativeRange() {
    Range r = new Range(-5.0, -2.0);
    assertEquals(3.0, r.getLength(), 0.0001);
}

@Test
public void testContainsExactlyOnLowerBound() {
    Range r = new Range(1.0, 5.0);
    assertTrue(r.contains(1.0));
}

@Test
public void testContainsExactlyOnUpperBound() {
    Range r = new Range(1.0, 5.0);
    assertTrue(r.contains(5.0));
}

@Test
public void testHashCodeWithSmallDecimalBounds() {
    Range r1 = new Range(0.00000001, 0.00000002);
    Range r2 = new Range(0.00000001, 0.00000002);
    assertEquals(r1.hashCode(), r2.hashCode());
}

@Test
public void testHashCodeDifference() {
    Range r1 = new Range(1.0, 5.0);
    Range r2 = new Range(1.0, 6.0);
    assertNotEquals(r1.hashCode(), r2.hashCode());
}

@Test
public void testExpandCollapseToSinglePoint1() {
    Range r = new Range(4.0, 6.0); // length = 2
    Range collapsed = Range.expand(r, -0.5, -0.5); // Should collapse to midpoint = 5.0
    assertEquals(5.0, collapsed.getLowerBound(), 0.0001);
    assertEquals(5.0, collapsed.getUpperBound(), 0.0001);
}

@Test
public void testEqualsWithDifferentPrecision() {
    Range r1 = new Range(1.0000001, 2.0000001);
    Range r2 = new Range(1.0000002, 2.0000002);
    assertFalse(r1.equals(r2));
}

@Test
public void testExpandWithZeroMargins1() {
    Range r = new Range(2.0, 4.0);
    Range result = Range.expand(r, 0.0, 0.0);
    assertEquals(2.0, result.getLowerBound(), 0.0001);
    assertEquals(4.0, result.getUpperBound(), 0.0001);
}

@Test
public void testExpandWithAsymmetricalMargins() {
    Range r = new Range(3.0, 7.0); // length = 4.0
    Range result = Range.expand(r, 0.25, 0.75);
    assertEquals(2.0, result.getLowerBound(), 0.0001); // 3 - 1.0
    assertEquals(10.0, result.getUpperBound(), 0.0001); // 7 + 3.0
}

@Test
public void testShiftWithoutCrossingZero() {
    Range r = new Range(1.0, 3.0);
    Range shifted = Range.shift(r, 2.0, false);
    assertEquals(3.0, shifted.getLowerBound(), 0.0001);
    assertEquals(5.0, shifted.getUpperBound(), 0.0001);
}

@Test
public void testShiftIntoNegativeWithCrossingFalse() {
    Range r = new Range(1.0, 3.0);
    Range shifted = Range.shift(r, -5.0, false);
    // Should clamp both bounds to 0.0
    assertEquals(0.0, shifted.getLowerBound(), 0.0001);
    assertEquals(0.0, shifted.getUpperBound(), 0.0001);
}

@Test
public void testShiftIntoNegativeWithCrossingTrue() {
    Range r = new Range(1.0, 3.0);
    Range shifted = Range.shift(r, -5.0, true);
    assertEquals(-4.0, shifted.getLowerBound(), 0.0001);
    assertEquals(-2.0, shifted.getUpperBound(), 0.0001);
}

@Test
public void testIntersects_ExactLowerBound_Fixed() {
    Range base = new Range(5.0, 10.0);
    assertFalse("Should not intersect at exact lower bound", base.intersects(4.0, 5.0));
}


@Test
public void testIntersects_ExactUpperBound_Fixed() {
    Range base = new Range(5.0, 10.0);
    assertFalse("Should not intersect at exact upper bound", base.intersects(10.0, 11.0));
}

@Test
public void testIntersectsSlightlyInsideBounds() {
    Range base = new Range(5.0, 10.0);
    assertTrue("Should intersect just inside lower bound", base.intersects(4.9999, 5.0001));
    assertTrue("Should intersect just inside upper bound", base.intersects(9.9999, 10.0001));
}


//Test Range.equals(Object) with null
@Test
public void testEqualsWithNull() {
 Range range = new Range(1.0, 5.0);
 assertFalse("Range should not equal null", range.equals(null));
}

//Test Range.equals(Object) with object of different class
@Test
public void testEqualsWithDifferentClass() {
 Range range = new Range(1.0, 5.0);
 assertFalse("Range should not equal non-Range object", range.equals("NotARange"));
}

//Test contains() method at lower bound
@Test
public void testContainsAtLowerBound() {
 Range r = new Range(3.0, 7.0);
 assertTrue("Should contain exact lower bound", r.contains(3.0));
}

//Test contains() method at upper bound
@Test
public void testContainsAtUpperBound() {
 Range r = new Range(3.0, 7.0);
 assertTrue("Should contain exact upper bound", r.contains(7.0));
}

//Test Range constructor with same lower and upper (degenerate case)
@Test
public void testDegenerateRangeEquals() {
 Range r1 = new Range(3.0, 3.0);
 Range r2 = new Range(3.0, 3.0);
 assertEquals("Degenerate ranges should be equal", r1, r2);
}

//Test intersects with range completely outside but adjacent
@Test
public void testIntersects_AdjacentLower() {
 Range base = new Range(5.0, 10.0);
 assertFalse("Should not intersect (adjacent lower)", base.intersects(3.0, 5.0));
}

@Test
public void testIntersects_AdjacentUpper() {
 Range base = new Range(5.0, 10.0);
 assertFalse("Should not intersect (adjacent upper)", base.intersects(10.0, 10.0));
}

//Test constrain() with value far above upper
@Test
public void testConstrainAboveUpper() {
 Range r = new Range(1.0, 5.0);
 assertEquals(5.0, r.constrain(100.0), 0.00001);
}

//1. Test expanding a range with very large margins
@Test
public void testExpandWithHugeMargins() {
 Range r = new Range(10, 20);
 Range expanded = Range.expand(r, 5.0, 5.0);
 assertEquals("Expanded lower", -40.0, expanded.getLowerBound(), 0.00001);
 assertEquals("Expanded upper", 70.0, expanded.getUpperBound(), 0.00001);
}

//2. Test shifting with negative delta and zeroCrossing false
@Test
public void testShiftNegativeNoCrossing() {
 Range r = new Range(2, 4);
 Range shifted = Range.shift(r, -5, false);
 assertEquals("No lower bound crossing", 0.0, shifted.getLowerBound(), 0.00001);
 assertEquals("No upper bound crossing", 0.0, shifted.getUpperBound(), 0.00001);
}

//3. Test shifting that fully crosses into negative but with zero crossing allowed
@Test
public void testShiftCrossIntoNegativeWithAllow() {
 Range r = new Range(2, 4);
 Range shifted = Range.shift(r, -6, true);
 assertEquals("Lower bound", -4.0, shifted.getLowerBound(), 0.00001);
 assertEquals("Upper bound", -2.0, shifted.getUpperBound(), 0.00001);
}

//4. Intersects with large gap
@Test
public void testIntersectsNonTouching() {
 Range r = new Range(10, 20);
 assertFalse("Non-overlapping range", r.intersects(30, 40));
}

@Test
public void testIntersectsSinglePointOverlap_Fixed() {
    Range r = new Range(5.0, 10.0);

    // Touching bounds do not count as intersection in this implementation
    assertFalse("Lower bound touch is not an intersection", r.intersects(5.0, 5.0));
    assertFalse("Upper bound touch is not an intersection", r.intersects(10.0, 10.0));
}

@Test
public void testIntersectsMinimalOverlap() {
    Range r = new Range(5.0, 10.0);
    assertTrue("Tiny overlap should count", r.intersects(4.9999, 5.0001));
}


//6. Equals check against null and different class
@Test
public void testEqualsWithNullAndDifferentClass() {
 Range r = new Range(3, 7);
 assertNotEquals(r, null);
 assertNotEquals(r, "not a range");
}

//7. Combine with overlapping ranges
@Test
public void testCombineOverlapping() {
 Range r1 = new Range(1, 5);
 Range r2 = new Range(4, 10);
 Range combined = Range.combine(r1, r2);
 assertEquals("Lower", 1.0, combined.getLowerBound(), 0.00001);
 assertEquals("Upper", 10.0, combined.getUpperBound(), 0.00001);
}

//8. isNaNRange
@Test
public void testIsNaNRangeTrue() {
 Range r = new Range(Double.NaN, Double.NaN);
 assertTrue("Should be NaN range", r.isNaNRange());
}

//9. isNaNRange False
@Test
public void testIsNaNRangeFalse() {
 Range r = new Range(0.0, Double.NaN);
 assertFalse("Only upper is NaN", r.isNaNRange());
}

//10. test equals with swapped values
@Test(expected = IllegalArgumentException.class)
public void testEqualsSwapped() {
    // This will throw, and that's what we're testing for
    new Range(6.0, 2.0);
}

@Test
public void testGetCentralValue_SymmetricNegative() {
    Range r = new Range(-4.0, -2.0);
    assertEquals("Central value of negative range", -3.0, r.getCentralValue(), 0.00001);
}

@Test
public void testGetCentralValue_ZeroLength() {
    Range r = new Range(2.5, 2.5);
    assertEquals("Central value of degenerate range", 2.5, r.getCentralValue(), 0.00001);
}

@Test
public void testExpand_CollapseToSinglePoint() {
    Range r = new Range(5.0, 5.0);
    Range expanded = Range.expand(r, 1.0, 1.0);
    assertEquals("Collapsed range central value", 5.0, expanded.getLowerBound(), 0.00001);
    assertEquals("Collapsed range central value", 5.0, expanded.getUpperBound(), 0.00001);
}

@Test
public void testShift_NegativeDelta_ZeroCrossingFalse() {
    Range r = new Range(1.0, 3.0);
    Range shifted = Range.shift(r, -5.0, false);
    assertEquals("Lower bound should clamp to zero", 0.0, shifted.getLowerBound(), 0.00001);
    assertEquals("Upper bound should clamp to zero", 0.0, shifted.getUpperBound(), 0.00001);
}

@Test
public void testShift_NegativeDelta_ZeroCrossingTrue() {
    Range r = new Range(1.0, 3.0);
    Range shifted = Range.shift(r, -5.0, true);
    assertEquals("Lower bound", -4.0, shifted.getLowerBound(), 0.00001);
    assertEquals("Upper bound", -2.0, shifted.getUpperBound(), 0.00001);
}

@Test
public void testEquals_PrecisionEdge() {
    Range r1 = new Range(1.0000001, 2.0000001);
    Range r2 = new Range(1.0000001, 2.0000001);
    assertTrue("Precision equality", r1.equals(r2));
}

@Test
public void testScale_ZeroFactor() {
    Range r = new Range(3.0, 7.0);
    Range scaled = Range.scale(r, 0.0);
    assertEquals("Lower bound", 0.0, scaled.getLowerBound(), 0.00001);
    assertEquals("Upper bound", 0.0, scaled.getUpperBound(), 0.00001);
}

@Test
public void testCombine_SupersetCase() {
    Range r1 = new Range(2.0, 5.0);
    Range r2 = new Range(1.0, 10.0);
    Range combined = Range.combine(r1, r2);
    assertEquals("Combined lower", 1.0, combined.getLowerBound(), 0.00001);
    assertEquals("Combined upper", 10.0, combined.getUpperBound(), 0.00001);
}

@Test
public void testIntersects_B0GreaterThanB1() {
    Range r = new Range(2.0, 6.0);
    assertFalse("Should not intersect with inverted input range", r.intersects(8.0, 4.0));
}

@Test
public void testIntersects_B1AtLowerBound() {
    Range r = new Range(3.0, 7.0);
    assertFalse("Should not intersect when b1 is exactly at lower bound", r.intersects(1.0, 3.0));
}

@Test
public void testIntersects_B0AtUpperBound() {
    Range r = new Range(3.0, 7.0);
    assertFalse("Should not intersect when b0 is exactly at upper bound", r.intersects(7.0, 9.0));
}

@Test
public void testShiftZeroDelta_NoCrossing_Fixed() {
    Range r = new Range(-3.0, -1.0);
    Range shifted = Range.shift(r, 0.0, false);

    // No shift happens, so no clamping occurs
    assertEquals(-3.0, shifted.getLowerBound(), 0.0001);
    assertEquals(-1.0, shifted.getUpperBound(), 0.0001);
}


@Test
public void testShiftZeroDelta_AllowCrossing() {
    Range r = new Range(-3.0, -1.0);
    Range shifted = Range.shift(r, 0.0, true);
    assertEquals(-3.0, shifted.getLowerBound(), 0.0001);
    assertEquals(-1.0, shifted.getUpperBound(), 0.0001);
}

@Test
public void testCombineIgnoringNaN_NullAndNaN() {
    Range r = new Range(Double.NaN, Double.NaN);
    assertNull("Should return null when both are null or NaN", Range.combineIgnoringNaN(null, r));
}

@Test
public void testCombineIgnoringNaN_NullAndValid() {
    Range r = new Range(1.0, 5.0);
    Range result = Range.combineIgnoringNaN(null, r);
    assertEquals(r, result);
}

@Test
public void testEquals_SlightDifferenceInBounds() {
    Range r1 = new Range(1.00000001, 2.00000001);
    Range r2 = new Range(1.00000002, 2.00000002);
    assertFalse("Should not be equal due to precision", r1.equals(r2));
}

@Test
public void testConstrain_VeryHighValue() {
    Range r = new Range(5.0, 10.0);
    assertEquals(10.0, r.constrain(1e10), 0.0001);
}

@Test
public void testConstrain_VeryLowValue() {
    Range r = new Range(5.0, 10.0);
    assertEquals(5.0, r.constrain(-1e10), 0.0001);
}

@Test(expected = IllegalArgumentException.class)
public void testConstructor_LowerGreaterThanUpper_ShouldThrowException1() {
    new Range(5.0, 2.0); // triggers the exception branch
}

@Test
public void testConstructor_LowerEqualsUpper() {
    Range r = new Range(2.0, 2.0); // edge case
    assertEquals(0.0, r.getLength(), 0.0001);
}

@Test
public void testGetCentralValue_FractionalBounds() {
    Range r = new Range(1.5, 3.5);
    assertEquals(2.5, r.getCentralValue(), 0.00001);
}

@Test
public void testGetCentralValue_NegativeToPositive() {
    Range r = new Range(-3.0, 3.0);
    assertEquals(0.0, r.getCentralValue(), 0.00001);
}

@Test
public void testContains_ExactlyLowerBound() {
    Range r = new Range(-2.0, 2.0);
    assertTrue(r.contains(-2.0));
}

@Test
public void testContains_ExactlyUpperBound() {
    Range r = new Range(-2.0, 2.0);
    assertTrue(r.contains(2.0));
}

@Test
public void testContains_OutOfBounds() {
    Range r = new Range(0.0, 10.0);
    assertFalse(r.contains(10.0001));
    assertFalse(r.contains(-0.0001));
}

@Test
public void testIntersects_SinglePointTouchingLower_Fixed() {
    Range r = new Range(1.0, 5.0);
    assertFalse("Touching lower bound is not considered intersection", r.intersects(0.0, 1.0));
}

@Test
public void testIntersects_SinglePointTouchingUpper_Fixed() {
    Range r = new Range(1.0, 5.0);
    assertFalse("Touching upper bound is not considered intersection", r.intersects(5.0, 6.0));
}


@Test
public void testIntersects_NonOverlappingBefore() {
    Range r = new Range(5.0, 10.0);
    assertFalse(r.intersects(0.0, 4.9));
}

@Test
public void testIntersects_NonOverlappingAfter() {
    Range r = new Range(0.0, 5.0);
    assertFalse(r.intersects(5.1, 6.0));
}

@Test
public void testConstrain_BelowLowerBound() {
    Range r = new Range(2.0, 6.0);
    assertEquals(2.0, r.constrain(1.0), 0.0001);
}

@Test
public void testConstrain_AboveUpperBound() {
    Range r = new Range(2.0, 6.0);
    assertEquals(6.0, r.constrain(10.0), 0.0001);
}

@Test
public void testConstrain_WithinBounds() {
    Range r = new Range(2.0, 6.0);
    assertEquals(4.0, r.constrain(4.0), 0.0001);
}

@Test
public void testMin_WithNaNFirst() {
    assertEquals(3.0, Range.combineIgnoringNaN(null, new Range(3.0, 4.0)).getLowerBound(), 0.0001);
}

@Test
public void testMax_WithNaNSecond() {
    assertEquals(5.0, Range.combineIgnoringNaN(new Range(2.0, 5.0), null).getUpperBound(), 0.0001);
}

@Test
public void testExpandToInclude_ValueBelowRange() {
    Range base = new Range(3.0, 7.0);
    Range expanded = Range.expandToInclude(base, 1.0);
    assertEquals(1.0, expanded.getLowerBound(), 0.0001);
    assertEquals(7.0, expanded.getUpperBound(), 0.0001);
}

@Test
public void testExpandToInclude_ValueAboveRange() {
    Range base = new Range(3.0, 7.0);
    Range expanded = Range.expandToInclude(base, 9.0);
    assertEquals(3.0, expanded.getLowerBound(), 0.0001);
    assertEquals(9.0, expanded.getUpperBound(), 0.0001);
}

@Test
public void testExpandToInclude_ValueInsideRange() {
    Range base = new Range(3.0, 7.0);
    Range expanded = Range.expandToInclude(base, 5.0);
    assertEquals(base, expanded);
}


 

 
    
    
    
    
    
    
    
}
