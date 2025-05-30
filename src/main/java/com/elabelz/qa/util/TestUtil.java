package com.elabelz.qa.util;

import java.io.File;
import java.io.File;
import java.io.IOException;
// Unused import: java.util.regex.Pattern;
// Unused import: java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;
// POI imports are removed
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Unused import: java.util.regex.Matcher;
// Unused import: java.util.regex.Pattern;

import com.elabelz.qa.base.TestBase;

public class TestUtil extends TestBase {

	// TESTDATA_SHEET_PATH and POI static variables removed
	static JavascriptExecutor js; // js seems unrelated to POI, keeping it for now.

	 // getTestData method removed
	 
	 public static void waitForAnElement(WebElement element)
	 {
		  WebDriverWait wait1 = new WebDriverWait(driver, 20);
		  wait1.until(ExpectedConditions.visibilityOf(element));
		  if(element.isEnabled()) {
			  element.click();
		  }
	 }
	
	
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}
	
	
}	
