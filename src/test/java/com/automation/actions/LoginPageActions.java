package com.automation.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.automation.factory.PageFactory;

import com.automation.locators.LoginPageLocators;

import io.qameta.allure.Step;

public class LoginPageActions {
	
	private static final Logger log = LogManager.getLogger(LoginPageActions.class);
    private final PageFactory pFactory;

    public LoginPageActions() {
        this.pFactory = new PageFactory();
    }

    @Step("Login with username: {0}")
    public void login(String username, String password) {
        log.info("Entering credentials for user: {}", username);
        this.pFactory.fill(LoginPageLocators.USERNAME_FIELD, username);
        this.pFactory.fill(LoginPageLocators.PASSWORD_FIELD, password);
        this.pFactory.click(LoginPageLocators.LOGIN_BUTTON);
      
    }
}
