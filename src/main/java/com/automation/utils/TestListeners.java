package com.automation.utils;

import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.automation.factory.BrowserFactory;

import io.qameta.allure.Attachment;

public class TestListeners implements ITestListener{
	
	@Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] saveScreenshot() {
        return ((TakesScreenshot) BrowserFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (BrowserFactory.getDriver() != null) {
            saveScreenshot();
        }
    }

}
