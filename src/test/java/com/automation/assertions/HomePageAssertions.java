package com.automation.assertions;

import org.testng.Assert;

import com.automation.factory.PageFactory;
import com.automation.locators.HomePageLocators;

import io.qameta.allure.Step;

public class HomePageAssertions {
	
private final PageFactory pFactory;
	
	public HomePageAssertions() {
		this.pFactory = new PageFactory();
	}
	
	@Step("VerifyDashboardTabOnHomePageAfterSuccesfullLogIn")
	public void verifyDashboardTabOnHomePageAfterSuccesfullLogIn(String expectedHeading) {
		String actualHeading = this.pFactory.getText(HomePageLocators.DASHBOARD_TAB);
		Assert.assertEquals(actualHeading, expectedHeading, "Heading text mismatch!");
	}

}
