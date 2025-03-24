package org.jfree.data.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Running JUnit Tests...\n");

        // Run tests from a specific test class
        Result result = JUnitCore.runClasses(RangeTest.class, DataUtilitiesTest.class);

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