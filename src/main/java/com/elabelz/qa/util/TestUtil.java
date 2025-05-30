package com.elabelz.qa.util;

import java.io.File;
import java.io.FileInputStream; // Added
import java.io.IOException;
import java.util.Properties; // Added

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By; // Added
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver; // Added
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.elabelz.qa.base.TestBase;

public class TestUtil extends TestBase {

	static JavascriptExecutor js;

	// Old waitForAnElement method can be removed or kept if used elsewhere, removing for now
	/*
	 public static void waitForAnElement(WebElement element)
	 {
		  WebDriverWait wait1 = new WebDriverWait(driver, 20); // driver here is from TestBase
		  wait1.until(ExpectedConditions.visibilityOf(element));
		  if(element.isEnabled()) {
			  element.click();
		  }
	 }
	*/
	
	public static WebDriverWait getWebDriverWait(WebDriver webDriver) { // Changed parameter name for clarity
	    Properties prop = new Properties();
	    try (FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/elabelz/qa/config/config.properties")) {
	        prop.load(ip);
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Return a default wait if properties fail to load
	        return new WebDriverWait(webDriver, 10); // Default to 10s
	    }
	    long timeout = Long.parseLong(prop.getProperty("explicitWaitTimeout", "10")); // Default to 10s
	    return new WebDriverWait(webDriver, timeout);
	}

	public static WebElement waitForElementToBeClickable(WebDriver webDriver, By locator) {
	    WebDriverWait wait = getWebDriverWait(webDriver);
	    return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static WebElement waitForElementToBeVisible(WebDriver webDriver, By locator) {
	    WebDriverWait wait = getWebDriverWait(webDriver);
	    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}
	
	
}	
