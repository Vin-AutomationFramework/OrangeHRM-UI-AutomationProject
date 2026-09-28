package com.automation.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserFactory {
	
	private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void startBrowser(String browser, boolean headless) {
        WebDriver driver;
        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless) options.addArguments("-headless");
            driver = new FirefoxDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            if (headless) options.addArguments("--headless=new");
            driver = new ChromeDriver(options);
        }
        driver.manage().window().maximize();
        driverThreadLocal.set(driver);
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }

}
