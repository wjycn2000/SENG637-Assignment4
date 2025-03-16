package org.jfree.data.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Running JUnit Tests...\n");

        // Run tests from a specific test class
        Result result = JUnitCore.runClasses(RangeTest.class, DataUtilitiesTest.class, DataUtilitiesTest_TC_DU_001.class, DataUtilitiesTest_TC_DU_002.class, DataUtilitiesTest_TC_DU_003.class, DataUtilitiesTest_TC_DU_004.class, DataUtilitiesTest_TC_DU_005.class, DataUtilitiesTest_TC_DU_006.class, DataUtilitiesTest_TC_DU_007.class, DataUtilitiesTest_TC_DU_008.class, RangeTest_TC_RNG_001.class, RangeTest_TC_RNG_002.class, RangeTest_TC_RNG_003.class, RangeTest_TC_RNG_004.class, RangeTest_TC_RNG_005.class, RangeTest_TC_RNG_006.class, RangeTest_TC_RNG_007.class, RangeTest_TC_RNG_008.class, RangeTest_TC_RNG_009.class, RangeTest_TC_RNG_010.class, RangeTest_TC_RNG_011.class, RangeTest_TC_RNG_012.class, RangeTest_TC_RNG_013.class, RangeTest_TC_RNG_014.class, RangeTest_TC_RNG_015.class, RangeTest_TC_RNG_016.class, RangeTest_TC_RNG_017.class, RangeTest_TC_RNG_018.class, RangeTest_TC_RNG_019.class, RangeTest_TC_RNG_020.class, RangeTest_TC_RNG_021.class, RangeTest_TC_RNG_022.class, RangeTest_TC_RNG_023.class, RangeTest_TC_RNG_024.class, RangeTest_TC_RNG_025.class, RangeTest_TC_RNG_026.class, RangeTest_TC_RNG_027.class, RangeTest_TC_RNG_028.class, RangeTest_TC_RNG_029.class, RangeTest_TC_RNG_030.class, RangeTest_TC_RNG_031.class, RangeTest_TC_RNG_032.class);

        // Print failures
        System.out.println("Number of failed tests: " + result.getFailureCount());
        for (Failure failure : result.getFailures()) {
            System.out.println("Test failed: " + failure.toString());
        }

        // Print successes
        int totalTests = result.getRunCount();
        int successCount = totalTests - result.getFailureCount();
        System.out.println("\nTotal tests executed: " + totalTests);
        System.out.println("Number of successful tests: " + successCount);
        System.out.println("Number of failed tests: " + result.getFailureCount());
        System.out.println("\nAll tests passed: " + result.wasSuccessful());
    }
}