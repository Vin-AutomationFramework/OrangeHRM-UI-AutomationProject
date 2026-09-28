package com.automation.assertions;

import org.testng.Assert;

import com.automation.factory.BrowserFactory;
import com.automation.factory.PageFactory;
import com.automation.locators.LoginPageLocators;
import com.automation.utils.CommonUtils;

import io.qameta.allure.Step;

public class LoginPageAssertions {
	
	private final PageFactory pFactory;
	
	public LoginPageAssertions() {
		this.pFactory = new PageFactory();
	}
	
	@Step("VerifyErrorMessageForInvalidUserName")
	public void verifyErrorMessageForInvalidUserName(String expectedErrorMsg) {
		String actualErrorMesg = this.pFactory.getText(LoginPageLocators.INVALIDERROR_MSG);
		CommonUtils.captureScreenshotToAllure(BrowserFactory.getDriver(), 
	            "Error Message Verification Screenshot");
		Assert.assertEquals(actualErrorMesg, expectedErrorMsg, "Invalid Credential Error Message Shown as expected");
		
	}

}
