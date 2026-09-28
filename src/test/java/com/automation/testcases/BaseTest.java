package com.automation.testcases;

import com.automation.factory.BrowserFactory;
import com.automation.utils.ConfigReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
	
	@BeforeMethod
    @Parameters({"browser", "headless", "baseUrl"})
    public void setUp(@Optional("") String browser,
                      @Optional("") String headless,
                      @Optional("") String baseUrl) {
        
        // Priority: Maven CLI System Property -> XML Parameter -> config.properties
        String finalBrowser = System.getProperty("browser", 
                browser.isEmpty() ? ConfigReader.getProperty("browser") : browser);

        String headlessStr = System.getProperty("headless", 
                headless.isEmpty() ? ConfigReader.getProperty("headless") : headless);
        boolean finalHeadless = Boolean.parseBoolean(headlessStr);

        String finalUrl = System.getProperty("baseUrl", 
                baseUrl.isEmpty() ? ConfigReader.getProperty("baseUrl") : baseUrl);

        BrowserFactory.startBrowser(finalBrowser, finalHeadless);
        BrowserFactory.getDriver().get(finalUrl);
    }

    @AfterMethod
    public void tearDown() {
        BrowserFactory.quitDriver();
    }
}
