package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.*;
import testBase.BaseClass;



public class TC002_LoginTest extends BaseClass{
	
	@Test(priority=1, groups={"Sanity","Master"}, testName="TC002_LoginTest")
	public void verifyLogin() {
		logger.info("***** Starting TC002_LoginTest *****");
		
		try {
			HomePage homePage = new HomePage(driver);
			logger.info("Click on My Account link");
			homePage.clickMyAccount();
			logger.info("Click on Login link");
			homePage.clickLogin();
			
			
			LoginPage loginPage = new LoginPage(driver);
			logger.info("Fill in email and password");
			loginPage.setEmail(getStandardUserEmail());
			loginPage.setPassword(getStandardUserPassword());
			logger.info("Click on Login button");
			loginPage.clickLogin();
			
			MyAccountPage myAccountPage = new MyAccountPage(driver);
			logger.info("Verify user is on the My Account page");
			Assert.assertEquals(myAccountPage.MyAccountPageExists(), true,"Login failed");
		}
		catch(Exception e) {
			Assert.fail();
		}
		
		logger.info("***** Finished TC002_LoginTest *****");
			
	}
}
