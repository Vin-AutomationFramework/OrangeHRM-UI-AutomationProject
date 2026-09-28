package com.automation.assertions;

import org.testng.Assert;

import com.automation.factory.PageFactory;
import com.automation.locators.HomePageLocators;

import io.qameta.allure.Step;

public class LoginPageAssertions {
	
	private final PageFactory pFactory;
	
	public LoginPageAssertions() {
		this.pFactory = new PageFactory();
	}
	
	@Step("VerifyHeadingTextAfterSuccessfullLOgIN")
	public void verifyHeadingTextAfterSuccessfullLOgIN(String expectedText) {
		String actualHeading = this.pFactory.getText(HomePageLocators.DASHBOARD_TAB);
		Assert.assertEquals(actualHeading, expectedText, "Heading text mismatch!");
	}

}
