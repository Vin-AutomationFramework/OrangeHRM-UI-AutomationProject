package com.automation.utils;

import io.qameta.allure.Allure;

public class CommonUtils {
	
	/**
     * Dynamically updates the test case title in the Allure report at runtime.
     * @param testCaseName Name read from the Excel row
     */
    public static void setAllureReportTestCaseName(String testCaseName) {
        if (testCaseName != null && !testCaseName.trim().isEmpty()) {
            Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(testCaseName));
        }
    }

}
