package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class GMailTest extends TestCase {
     WebDriver driver;
     
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
       
        WebElement user = webElement(properties.getProperty("userElement"));
        user.sendKeys(properties.getProperty("username"));

        WebElement pass = webElement(properties.getProperty("passElement"));
        pass.click();

      
        WebElement passwordElement = webElement(properties.getProperty("passwordelem"));
        passwordElement.sendKeys(properties.getProperty("password"));
        
        WebElement PassNext = webElement(properties.getProperty("PassNext"));
        
        PassNext.click();

        WebElement composeElement = webElement(properties.getProperty("Compose"));
        wait.until(ExpectedConditions.elementToBeClickable(composeElement));
     
        composeElement.click();

        WebElement To = webElement(properties.getProperty("To"));
        
        To.clear();
        To.sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
        
     // emailSubject and emailbody to be used in this unit test.
        String emailSubject = properties.getProperty("email.subject");
        String emailBody = properties.getProperty("email.body");
        
        webElement(properties.getProperty("Subject")).sendKeys(emailSubject);
        webElement(properties.getProperty("Body")).sendKeys(emailBody);
        
        WebElement MoreOptions = webElement(properties.getProperty("MoreOptions"));
        
        WebElement Label = webElement(properties.getProperty("Label"));
        
        WebElement Social = webElement(properties.getProperty("SocialCheck"));
        Actions actions = new Actions(driver);
        Thread.sleep(2000);
        
         actions.moveToElement(MoreOptions).build().perform();
         Thread.sleep(2000);
         
         MoreOptions.click();
      // WebElement s=driver.findElement(By.xpath("//iframe[@name='oauth2relay860174152']"));
        
     //   driver.switchTo().frame(s);
        
        javascriptExecutorClick(properties.getProperty("MoreOptions"));
     
       // driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.elementToBeClickable(Label));
        Label.click();
        Thread.sleep(2000);
         
        wait.until(ExpectedConditions.elementToBeClickable(Social));
        Social.click();
        
        Thread.sleep(2000);
       
        webElement(properties.getProperty("Send")).click();
        
        Thread.sleep(2000);
          driver.navigate().refresh();
        
          Thread.sleep(2000);
          WebElement Social1 = webElement(properties.getProperty("Social"));
          wait.until(ExpectedConditions.elementToBeClickable(Social1));
          Social1.click();
          Thread.sleep(5000);
          List<WebElement> inboxEmails = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//*[@class='zA zE']"))));                   

          for(WebElement email : inboxEmails){                                                                                                                           
              if(email.isDisplayed() && email.getText().contains("email.subject")){                                                                                                                                   
                  email.click();                                                                                                                                         

                  WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@title,'with label Inbox')]")));                    
                  WebElement subject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Subject of this message')]")));          
                  WebElement body = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Single line body of the message')]")));   

              }                   
        
        
      
              Thread.sleep(10000);
              try {
            	  WebElement sTAR = webElement(properties.getProperty("NonStar"));
                  wait.until(ExpectedConditions.elementToBeClickable(sTAR));
                  sTAR.click();
            	}
            	catch(org.openqa.selenium.StaleElementReferenceException ex)
            	{
            		 WebElement sTAR = webElement(properties.getProperty("NonStar"));
            	        wait.until(ExpectedConditions.elementToBeClickable(sTAR));
            	        sTAR.click();
            	}
        
       try {
        
        WebElement Sub = webElement(properties.getProperty("SubjectToWait"));
        wait.until(ExpectedConditions.elementToBeClickable(Sub));
        Sub.click();//Always Clicks on the First Arrived Email
       }catch(org.openqa.selenium.StaleElementReferenceException ex)
   	{
    	   WebElement Sub = webElement(properties.getProperty("SubjectToWait"));
           wait.until(ExpectedConditions.elementToBeClickable(Sub));
           Sub.click();//Always Clicks on the First Arrived Email
  	}
        
        WebElement VerifySub = webElement(properties.getProperty("VerifySub"));
        wait.until(ExpectedConditions.elementToBeClickable(VerifySub));
        String subject=VerifySub.getText();
        Assert.assertEquals(subject, emailSubject);
       
        WebElement SubBoy = webElement(properties.getProperty("SubBoy"));
        wait.until(ExpectedConditions.elementToBeClickable(SubBoy));
        String body=SubBoy.getText();
        Assert.assertEquals(body, emailBody);
        
        
          }
       
        Thread.sleep(10000);
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
        
        
        
    }
    public void javascriptExecutorClick(String locator){
    	 WebDriverWait wait=new WebDriverWait(driver, 20); //Webdriverwait for Explicit Wait
    	    
    	 WebElement element=webElement(locator);
    	//wait.until(ExpectedConditions.elementToBeClickable(element));
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
