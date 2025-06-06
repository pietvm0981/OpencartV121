package testCases;

import testBase.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegistrationPage;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	@Test (priority=1, groups={"Regression", "Master"}, testName="TC001_AccountRegistrationTest")
	public void verifyAccountRegistration() {
		
		logger.info("***** Starting TC001_AccountRegistrationTest *****");
		
		try {
			HomePage homePage = new HomePage(driver);
			homePage.clickMyAccount();
			logger.info("Clicked on MyAccount Link");
			
			homePage.clickRegister();
			homePage.clickMyAccount();
			logger.info("Clicked on Register Link");
			
			logger.info("Providing customer details");
			RegistrationPage registrationPage = new RegistrationPage(driver);
			registrationPage.setFirstName(randomfirstName().toUpperCase());
			registrationPage.setLastName(randomLastName().toUpperCase());
			registrationPage.setEmail(randomString() + "@yopmail.com");
			registrationPage.setTelephone(randomNumber());
			
			String password = randomAlphaNumeric();
			registrationPage.setPassword(password);
			registrationPage.setConfirmPassword(password);
			
			logger.info("Accepting Privacy Policy");
			registrationPage.setPrivacyPolicy();
			logger.info("Clicked on Continue button");
			registrationPage.clickContinue();
			
			
			logger.info("Validating expected message");
			String confirmationMessage = registrationPage.getConfirmationMessage();
			
			if(confirmationMessage.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			}
			else {
				logger.error("Test Failed");
				logger.debug("Debug logs...");
				Assert.fail();
			}
			
			//Assert.assertEquals(confirmationMessage, "Your Account Has Been Created!");
		}
		catch(Exception e) {
			logger.error("Test Failed: " + e.getMessage());
			logger.debug("Debug logs...");
			Assert.fail();
		}
		logger.info("***** Finished TC001_AccountRegistrationTest *****");
	}
	

	
	
	
	
	
}
