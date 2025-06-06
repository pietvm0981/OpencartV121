package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;


public class ExtentReportManager implements ITestListener{
	public ExtentSparkReporter sparkReporter; //UI of the report
	public ExtentReports extent; //populate common info on the report
	public ExtentTest test; // creating test case entries in the report and update status of the test methods
	
	String reportName;
	
	
	  public void onStart(ITestContext testContext) {
		    
		  /*SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		  Date dt = new Date();
		  String currentDateTimestamp = df.format(dt);
		   */
		  
		  	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		  	reportName = "Test-Report-" + timeStamp +".html";
		    String reportFilePath = System.getProperty("user.dir") + "/reports/" + reportName;
		    sparkReporter = new ExtentSparkReporter(reportFilePath); //specify location of the report
		    
		    sparkReporter.config().setDocumentTitle("OpenCart Automation Report"); //Title of Report
		    sparkReporter.config().setReportName("OpenCart Functional Testing"); //Name of the report
		    sparkReporter.config().setTheme(Theme.DARK);
		 
		    
		    extent = new ExtentReports();
		    extent.attachReporter(sparkReporter);
		    
		    //populate common info
		    extent.setSystemInfo("Application", "OpenCart");
		    extent.setSystemInfo("Module", "Admin");
		    extent.setSystemInfo("Sub Module", "Customers");
		    extent.setSystemInfo("User Name", System.getProperty("user.name"));
		    extent.setSystemInfo("Environment", "QA");
		    
		    String os = testContext.getCurrentXmlTest().getParameter("os");
		    extent.setSystemInfo("Operating System", os);
		    
		    String browser = testContext.getCurrentXmlTest().getParameter("browser");
		    extent.setSystemInfo("Browser", browser);
		    
		    List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		    if(!includedGroups.isEmpty()) {
		    	extent.setSystemInfo("Groups", includedGroups.toString());
		    }   
	  }
	
	  public void onTestStart(ITestResult result) {

	  }
	  
	  public void onTestSuccess(ITestResult result) {
		  	
		    test = extent.createTest(result.getTestClass().getName()); //create a new entry in the report
		    test.assignCategory(result.getMethod().getGroups()); //to display groups in the report
		    test.log(Status.PASS, result.getName() + " got successfully executed"); //Update status P/F/S

	  }
	  
	  public void onTestFailure(ITestResult result) {
		  test = extent.createTest(result.getTestClass().getName());
		  test.assignCategory(result.getMethod().getGroups());
		  test.log(Status.FAIL, result.getName() + " got failed");
		  test.log(Status.INFO, "Test case FAILED cause is: " + result.getThrowable().getMessage());
		  
		  try {
			  String imgPath = new BaseClass().captureScreen(result.getName());
			  test.addScreenCaptureFromPath(imgPath);
		  }catch(IOException e1) {
			  e1.printStackTrace();
		  }
		  
		  
	  }
	  
	  public void onTestSkipped(ITestResult result) {
		  test = extent.createTest(result.getTestClass().getName());
		  test.assignCategory(result.getMethod().getGroups());
		  test.log(Status.SKIP, result.getName() + " got skipped");
		  test.log(Status.INFO,"Test case SKIPPED cause is: " + result.getThrowable().getMessage());
	  }
	  
	  
	  public void onFinish(ITestContext context) {
		  extent.flush(); // Write test information to output
		  
		  //Open report in browser
		  String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + reportName;
		  File extentReport = new File(pathOfExtentReport);
		  try {
			Desktop.getDesktop().browse(extentReport.toURI()); //open the report  
		  }
		  catch(IOException e) {
			  e.printStackTrace();
		  }
		  
		  //Email report
		  
//		  try {
//			  //app-password for Gmail to be configured at https://myaccount.google.com/apppasswords
//			  
//			  @SuppressWarnings("deprecation")
//			  URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + reportName);
//			  
//			  //Create the email message
//			  ImageHtmlEmail email = new ImageHtmlEmail();
//			  email.setDataSourceResolver(new DataSourceUrlResolver(url));
//			  email.setHostName("smtp.gmail.com");
//			  email.setSmtpPort(587);
//			  //email.setSslSmtpPort("587");
//			  email.setAuthenticator(new DefaultAuthenticator("email","app-password"));
//			  email.setSSLOnConnect(true);
//			  email.setFrom("from email"); //Sender
//			  email.setSubject("Test Results");
//			  email.setMsg("Please find attached report...");
//			  email.addTo("to email"); //Receiver
//			  email.attach(url, "extent report", "Please check report...");
//			  email.send(); //send the email
//		  }
//		  catch(Exception e){
//			  e.printStackTrace();
//		  }
		 	  
	  }
}

