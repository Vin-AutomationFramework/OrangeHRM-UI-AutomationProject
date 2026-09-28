package com.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

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
    
    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] captureScreenshotToAllure(WebDriver driver, String attachName) {
        if (driver != null) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

}
