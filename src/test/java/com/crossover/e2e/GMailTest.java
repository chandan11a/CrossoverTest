package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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
    	
    	String url=properties.getProperty("url");
        driver.get(url);
        driver.manage().window().maximize() ;
        WebDriverWait wait=new WebDriverWait(driver, 20); //Webdriverwait for Explicit Wait
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
        
        webElement(properties.getProperty("Send")).click();
        
       
        Thread.sleep(10000);
         
        
        
        
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
