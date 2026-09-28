package com.automation.factory;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageFactory {
	
	private WebDriverWait wait;

    public PageFactory() {
        this.wait = new WebDriverWait(BrowserFactory.getDriver(), Duration.ofSeconds(10));
    }

    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void fill(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

}
