package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager; //Log4j
import org.apache.logging.log4j.Logger; //Log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
public static WebDriver driver;
public Logger logger; //Log4j
public Properties prop;

	@SuppressWarnings("deprecation")
	@BeforeClass(groups= {"Sanity","Master","DataDriven"})
	@Parameters({"os","browser"})
	public void setup(String os, String browser) throws IOException, InterruptedException{
		
		//Loading config.properties file
		FileReader file = new FileReader(".//src//test//resources//config.properties");
		prop = new Properties();
		prop.load(file);
		
		logger = LogManager.getLogger(this.getClass()); //Log4j
		
		if(prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
			
			//Remote execution
			String huburl = "http://localhost:4444/wd/hub";
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN10);
			}
			else if(os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}
			else if(os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}
			else {
				System.out.println("No matching OS");
				return;
			}
			
			//browser
			switch(browser.toLowerCase()) {
				case "chrome" : capabilities.setBrowserName("chrome");
					break;
				case "edge": capabilities.setBrowserName("MicrosoftEdge");
					break;
				case "firefox": capabilities.setBrowserName("firefox");
					break;
				default: System.out.println("No matching browser");
					return;
			}
			
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
			
			
		}
		
		if(prop.getProperty("execution_env").equalsIgnoreCase("local")) {
			//Local execution
			switch(browser.toLowerCase()) {
			case "chrome": driver = new ChromeDriver();
				break;
			case "edge": driver = new EdgeDriver();
				break;
			default : System.out.println("Invalid browser name..."); 
				return;
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(prop.getProperty("appURL")); //reading url from properties file
		driver.manage().window().maximize();
		
		
	}
	
	@AfterClass(groups= {"Sanity","Master","DataDriven"})
	public void tearDown() {
		driver.quit();
	}
	
	
	@SuppressWarnings("deprecation")
	public String randomString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	@SuppressWarnings("deprecation")
	public String randomNumber() {
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}
	
	public String randomAlphaNumeric() {
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		String generatedNumber = RandomStringUtils.randomNumeric(3);
		return (generatedString + "@" + generatedNumber);
	}
	
	public String randomfirstName() {
		
		String[] firstNames = {"Peter","John","Devon","Marc","Pete","Stephan","Neil","Martin","Bryan"};
		Random random = new Random();
		int randomNumber = random.nextInt(firstNames.length - 1);
		return firstNames[randomNumber];
	}
	
	public String randomLastName() {
		
		String[] lastNames = {"Jones","Ellis","Peters","Willis","Stratton","White","Durrance","Black"};
		Random random = new Random();
		int randomNumber = random.nextInt(lastNames.length - 1);
		return lastNames[randomNumber];
	}
	
	public String getStandardUserEmail() {
		return prop.getProperty("email");
	}
	
	public String getStandardUserPassword() {
		return prop.getProperty("password");
	}
	
	public String captureScreen(String testMethodName) throws IOException{
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\" + testMethodName + "_" + timeStamp;
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
		
	}
	
	
	
}
