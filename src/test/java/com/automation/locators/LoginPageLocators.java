package com.automation.locators;

import org.openqa.selenium.By;

public class LoginPageLocators {
	
	private LoginPageLocators() {
        throw new UnsupportedOperationException("This is a utility/locator class and cannot be instantiated");
    }
	
	public static final By USERNAME_FIELD = By.name("username");
	public static final By PASSWORD_FIELD = By.name("password");
	public static final By LOGIN_BUTTON = By.xpath("//button[text()=' Login ']");
	

}
