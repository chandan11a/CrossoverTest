package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.codehaus.plexus.util.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class GMailTest extends TestCase {
     WebDriver driver;
     String testCasename=this.getClass().getSimpleName();
		String extentReportFile = System.getProperty("user.dir")
				
				+ "\\Report\\"+testCasename+".html";
     ExtentReports extent = new ExtentReports(extentReportFile, false);

		// Start the test using the ExtentTest class object.
		ExtentTest extentTest = extent.startTest("Cross Over Automation",
				"GMAIL");
    private Properties properties = new Properties();

    public void setUp() throws Exception {
    	String path=System.getProperty("user.dir");
        properties.load(new FileReader(new File(path+"/src/test/resources/test.properties")));
        //Dont Change below line. Set this value in test.properties file incase you need to change it..
        System.setProperty("webdriver.chrome.driver",properties.getProperty("webdriver.chrome.driver") );
        driver = new ChromeDriver();
    }

    public void tearDown() throws Exception {
        driver.quit();
    }

    /*
     * Please focus on completing the task
     * 
     */
    @Test
    public void testSendEmail() throws Exception {
    	
    	try {
    		
			
			
    		 WebDriverWait wait=new WebDriverWait(driver, 20); //Webdriverwait for Explicit Wait
    		    
    	String url=properties.getProperty("url");
        driver.get(url);
        driver.manage().window().maximize() ;
         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);		//Implicit Wait		
         extentTest.log(LogStatus.INFO, "Browser Launched");
			
        WebElement user = webElement(properties.getProperty("userElement"));
        user.sendKeys(properties.getProperty("username"));
    	extentTest.log(LogStatus.INFO, "Entering Username");	
        WebElement pass = webElement(properties.getProperty("passElement"));
        pass.click();

      
        WebElement passwordElement = webElement(properties.getProperty("passwordelem"));
        passwordElement.sendKeys(properties.getProperty("password"));
        
        WebElement PassNext = webElement(properties.getProperty("PassNext"));
        extentTest.log(LogStatus.INFO, "Entering password");	
        PassNext.click();
        extentTest.log(LogStatus.INFO, "Click SignIn");	
        WebElement composeElement = webElement(properties.getProperty("Compose"));
        wait.until(ExpectedConditions.elementToBeClickable(composeElement));
        extentTest.log(LogStatus.PASS, "Login Successfull");		
        
     
        composeElement.click();
        extentTest.log(LogStatus.INFO, "Click Compose");	

        WebElement To = webElement(properties.getProperty("To"));
        
        To.clear();
        To.sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
        extentTest.log(LogStatus.INFO, "Enter To Email Address");	

     // emailSubject and emailbody to be used in this unit test.
        String emailSubject = properties.getProperty("email.subject");
        String emailBody = properties.getProperty("email.body");
        
        webElement(properties.getProperty("Subject")).sendKeys(emailSubject);
        extentTest.log(LogStatus.INFO, "Enter Subject");	
        webElement(properties.getProperty("Body")).sendKeys(emailBody);
        extentTest.log(LogStatus.INFO, "Enter Email Body");	
        
        WebElement MoreOptions = webElement(properties.getProperty("MoreOptions"));
        extentTest.log(LogStatus.INFO, "Click More Options");	
        
        WebElement Label = webElement(properties.getProperty("Label"));
       
        
        
        WebElement Social = webElement(properties.getProperty("SocialCheck"));
        
        
        
        Actions actions = new Actions(driver);
        Thread.sleep(2000);
        
         actions.moveToElement(MoreOptions).build().perform();
         Thread.sleep(2000);
         
         MoreOptions.click();
        
        javascriptExecutorClick(properties.getProperty("MoreOptions"));
     
      
        wait.until(ExpectedConditions.elementToBeClickable(Label));
        extentTest.log(LogStatus.INFO, "Click Label");	
      //  Label.click();
       
        Thread.sleep(2000);
         
        wait.until(ExpectedConditions.elementToBeClickable(Social));
        Social.click();
        extentTest.log(LogStatus.INFO, "Click Social");	
        Thread.sleep(2000);
       
        webElement(properties.getProperty("Send")).click();
        extentTest.log(LogStatus.PASS, "Email Sent");		
        
        Thread.sleep(2000);
          driver.navigate().refresh();// To Avoid Stale ref exception
        
          Thread.sleep(2000);
          WebElement Social1 = webElement(properties.getProperty("Social"));
          wait.until(ExpectedConditions.elementToBeClickable(Social1));
          Social1.click();
          extentTest.log(LogStatus.INFO, "Click Social Tab");	
        
        
        
      
              Thread.sleep(5000);
              try {
            	  WebElement sTAR = webElement(properties.getProperty("NonStar"));
                  wait.until(ExpectedConditions.elementToBeClickable(sTAR));
                  sTAR.click();
                  extentTest.log(LogStatus.PASS, "Mark email as starred");
            	}
            	catch(org.openqa.selenium.StaleElementReferenceException ex)
            	{
            		 WebElement sTAR = webElement(properties.getProperty("NonStar"));
            	        wait.until(ExpectedConditions.elementToBeClickable(sTAR));
            	        sTAR.click();
                        extentTest.log(LogStatus.PASS, "Star Clicked");

            	}
        
       try {
        
        WebElement Sub = webElement(properties.getProperty("SubjectToWait"));
        wait.until(ExpectedConditions.elementToBeClickable(Sub));
        Sub.click();//Always Clicks on the First Arrived Email
        extentTest.log(LogStatus.PASS, "Subject Verified");	

       }catch(org.openqa.selenium.StaleElementReferenceException ex)
   	{
    	   WebElement Sub = webElement(properties.getProperty("SubjectToWait"));
           wait.until(ExpectedConditions.elementToBeClickable(Sub));
           Sub.click();//Always Clicks on the First Arrived Email
           extentTest.log(LogStatus.PASS, "Subject Verified");	
  	}
        Thread.sleep(5000);
      
    	   WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@title,'with label Inbox')]")));  
        
           wait.until(ExpectedConditions.elementToBeClickable(label));
          String text=label.getText();
          if(text.contains("Inbox")) { 
        	  System.out.println("Pass");String
    		  extentReportImage11=captureScreenShot(testCasename);
    		  
    		  extentTest.log(LogStatus.PASS, "Inbox Label Found"); extentTest.log(
    		  LogStatus.INFO, "Snapshot : " +
    		  extentTest.addScreenCapture(extentReportImage11));
    		  
    		  } else {
    			  System.out.println("Fail");String extentReportImage11=captureScreenShot(testCasename);
    		  
				
				  extentTest.log(LogStatus.FAIL, "Social Label Not Found"); extentTest.log(
				 LogStatus.INFO, "Error Snapshot : " +
				 extentTest.addScreenCapture(extentReportImage11));
				
    		  
    		  }
        

       
           
        WebElement VerifySub = webElement(properties.getProperty("VerifySub"));
        wait.until(ExpectedConditions.elementToBeClickable(VerifySub));
        String subject=VerifySub.getText();
        Assert.assertEquals(subject, emailSubject);
        extentTest.log(LogStatus.PASS, "Email Body Verified");	
        System.out.println("run");
       
        WebElement SubBoy = webElement(properties.getProperty("SubBoy"));
        wait.until(ExpectedConditions.elementToBeClickable(SubBoy));
        String body=SubBoy.getText();
        Assert.assertEquals(body, emailBody);
        System.out.println("rr");
    	extentTest.log(LogStatus.INFO, "Browser closed");

		
    	// close report.
		extent.endTest(extentTest);

		// writing everything to document.
		extent.flush();
       
        Thread.sleep(10000);
    	}catch(Exception e)
    	{
    		// close report.
    		extent.endTest(extentTest);

    		// writing everything to document.
    		extent.flush();
    		e.printStackTrace();
    	}
        
        
        
    }
    public String captureScreenShot(String testCasename) {
		String screenShotFilePath=null;
		String x=null;
		Random rand = new Random(); 
		int rand_int1 = rand.nextInt(100000); 
		try {	
			
			File scrFile =null;
				scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
		

			String screenShotPah=System.getProperty("user.dir")
					
					+ "\\Report\\";

			File fi= new File(screenShotPah);
			if(!fi.exists()){
				fi.mkdirs();
			}
			FileUtils.copyFile(scrFile, new File(screenShotPah+ testCasename+rand_int1+ ".png"));
			screenShotFilePath="Screenshot:" + ""+testCasename+rand_int1+".png";

			 x=screenShotPah+ testCasename+rand_int1+ ".png";
			 System.out.println(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return x;
	}
	

    public void javascriptExecutorClick(String locator){
    	 WebDriverWait wait=new WebDriverWait(driver, 20); //Webdriverwait for Explicit Wait
    	    
    	 WebElement element=webElement(locator);
    	wait.until(ExpectedConditions.elementToBeClickable(element));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();",element );
	}
	public WebElement webElement(String sTarget) {

		
		if (sTarget.startsWith("css")) {
			sTarget = sTarget.substring(4);
			return driver.findElement(By.cssSelector(sTarget));
		}
		if(sTarget.startsWith("(/"))
		{
			sTarget = sTarget.substring(0);
			return driver.findElement(By.xpath(sTarget));
		}
		if (sTarget.startsWith("//") ) {
			return driver.findElement(By.xpath(sTarget));
		}
		if (sTarget.startsWith("xpath")) {
			sTarget = sTarget.substring(6);
			return driver.findElement(By.xpath(sTarget));
		}
		if (sTarget.startsWith("name")) {
			sTarget = sTarget.substring(5);
			return driver.findElement(By.name(sTarget));
		}
		if (sTarget.startsWith("id")) {
			sTarget = sTarget.substring(3);
			return driver.findElement(By.id(sTarget));
		}
		if (sTarget.startsWith("link")) {
			sTarget = sTarget.substring(5);
			return driver.findElement(By.linkText(sTarget));
		}
		return null;
	}
}
