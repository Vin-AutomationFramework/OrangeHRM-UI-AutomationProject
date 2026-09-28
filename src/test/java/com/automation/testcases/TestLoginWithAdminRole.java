package com.automation.testcases;

import java.util.Iterator;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.automation.actions.LoginPageActions;
import com.automation.assertions.HomePageAssertions;
import com.automation.assertions.LoginPageAssertions;
import com.automation.utils.CommonUtils;
import com.automation.utils.ExcelReader;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class TestLoginWithAdminRole extends BaseTest{
	
	// ThreadLocal to hold input file path per thread context
    private static final ThreadLocal<String> inputDataPath = new ThreadLocal<>();
    private static final ThreadLocal<String> inputSheetName = new ThreadLocal<>();

    @BeforeClass
    @Parameters({"excelFilePath", "sheetName"})
    public void setUp(String excelFilePath, String sheetName) {
        inputDataPath.set(excelFilePath);
        inputSheetName.set(sheetName);
    }

    @Test(dataProvider = "loginTestdataProvider", description = "Login and Dashboard Validation Flow")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Story Name: User Authentication and Role Dashboard Validation")
    @Description("Test Case Description: Verify multi-user role login handling positive and negative credential scenarios.")
    public void testUserLoginFlow(Map<String, String> excelDataMap) {
        if (inputDataPath.get() == null) {
            throw new IllegalStateException("inputData path is not set in @BeforeClass!");
        }
        CommonUtils.setAllureReportTestCaseName(excelDataMap.get("testCaseName"));

        LoginPageActions loginActions = new LoginPageActions();
        HomePageAssertions homePageAssertions = new HomePageAssertions();
        LoginPageAssertions loginPageAssertions = new LoginPageAssertions();
        SoftAssert softAssert = new SoftAssert();

        String testCaseType = excelDataMap.get("testCaseType");
        String username = excelDataMap.get("userName");
        String password = excelDataMap.get("password");
        String expectedHeader = excelDataMap.get("tabText");
        String expectedError = excelDataMap.get("errorMsg");

        // Action execution
        loginActions.login(username, password);
        if(testCaseType.equalsIgnoreCase("Positive")) 
        {
        homePageAssertions.verifyDashboardTabOnHomePageAfterSuccesfullLogIn(expectedHeader);
        }else {
        	loginPageAssertions.verifyErrorMessageForInvalidUserName(expectedError);
        }
        

        softAssert.assertAll();
    }

    @AfterClass
    public void tearDownClass() {
        inputDataPath.remove();
        inputSheetName.remove();
    }
    
    @DataProvider(name = "loginTestdataProvider")
    public Iterator<Object[]> readDataProvider() {
        return ExcelReader.getTestDataAsIterator(inputDataPath.get(), inputSheetName.get());
    }

	
	
}
