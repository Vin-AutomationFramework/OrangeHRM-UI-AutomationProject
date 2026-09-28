package com.automation.locators;

import org.openqa.selenium.By;

public class HomePageLocators {
	
	private HomePageLocators() {
        throw new UnsupportedOperationException("This is a utility/locator class and cannot be instantiated");
    }
	
	public static final By DASHBOARD_TAB = By.xpath("//h6[text()='Dashboard']");
	
	

}
