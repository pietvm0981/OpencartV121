package testCases;

/*
1) Data is marked as valid | result: valid login ==> Test passed | Logout
2) Data is marked as valid | result: invalid login ==> Test failed

3) Data is marked as invalid | result: invalid login ==> Test passed
4) Data is marked as invalid | result: valid login ==> Test failed | Logout
 */

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

	@Test(priority=1, dataProvider="loginData", dataProviderClass=DataProviders.class, groups="DataDriven", testName="TC003_LoginDDT") //getting data provider from different class
	public void verifyLoginDDT(String email, String password, String expectedResult) {
		
		logger.info("***** Starting TC003_LoginDDT *****");
		
		try {
			//HomePage
			HomePage homePage = new HomePage(driver);
			logger.info("Click on My Account link");
			homePage.clickMyAccount();
			logger.info("Click on Login link");
			homePage.clickLogin();
				
			//LoginPage
			LoginPage loginPage = new LoginPage(driver);
			logger.info("Fill in email and password");
			loginPage.setEmail(email);
			loginPage.setPassword(password);
			logger.info("Click on Login button");
			loginPage.clickLogin();
			
			//MyAccountPage
			MyAccountPage myAccountPage = new MyAccountPage(driver);
			logger.info("Verify user is on the My Account page");
			boolean targetPage = myAccountPage.MyAccountPageExists();
			
			if(expectedResult.equalsIgnoreCase("Valid")) {
				
				if(targetPage == true) {
					logger.info("Email: " + email +"| Password: " + password + " is flagged as " + expectedResult.toLowerCase() 
						+ " and is valid");
					myAccountPage.clickLogout();
					Assert.assertTrue(true);
				}
				else {
					logger.info("Email: " + email +"| Password: " + password + " is flagged as " + expectedResult.toLowerCase() 
						+ " but is invalid");
					Assert.assertTrue(false);
				}
			}
			
			if(expectedResult.equalsIgnoreCase("Invalid")) {
				if(targetPage == true) {
					logger.info("Email: " + email +"| Password: " + password + " is flagged as " + expectedResult.toLowerCase() 
							+ " but is valid");
					myAccountPage.clickLogout();
					Assert.assertTrue(false);

				}
				else {
					logger.info("Email: " + email +"| Password: " + password + " is flagged as " + expectedResult.toLowerCase() 
						+ " and is invalid");
					Assert.assertTrue(true);
				}
			}
		}
		catch(Exception e) {
			Assert.fail();
		}
		

		
	
		logger.info("***** Finished TC003_LoginDDT *****");
			
	}
	
	
}
