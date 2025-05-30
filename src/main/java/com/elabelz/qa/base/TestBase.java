/* 
 *  This is a base class where you can find the location of config.properties and drivers.
 */
package com.elabelz.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions; // Appears unused
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager; // Added
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterTest;

import com.elabelz.qa.util.WebEventListener;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/elabelz/qa/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void initialization(){
		String browserName = prop.getProperty("browser");
		
		if(browserName.equalsIgnoreCase("chrome")){ // Made comparison case-insensitive
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(); 
		}
		else if(browserName.equalsIgnoreCase("FF") || browserName.equalsIgnoreCase("firefox")){ // Made comparison case-insensitive and added "firefox"
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(); 
		}
		// Add other browsers here if needed, e.g., Edge
		// else if(browserName.equalsIgnoreCase("edge")){
		//	WebDriverManager.edgedriver().setup();
		//	driver = new EdgeDriver();
		// }
		else {
			System.out.println("Browser name not supported or not specified in config.properties, defaulting to Chrome.");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		driver.manage().window().maximize();

		// Read timeout values from properties file
		long implicitWait = Long.parseLong(prop.getProperty("implicitWait", "10")); // Default to 10 if not found
		long pageLoadTimeout = Long.parseLong(prop.getProperty("pageLoadTimeout", "60")); // Default to 60 if not found
		// long explicitWaitTimeout = Long.parseLong(prop.getProperty("explicitWaitTimeout", "20")); // Read for future use, default to 20

		driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
		
	}
	
	@AfterTest(alwaysRun= true)
	public void teardown()
	//==============================//
	{
		System.out.println("**************************************     END OF TEST    **************************************");
		driver.quit();
	}
}
