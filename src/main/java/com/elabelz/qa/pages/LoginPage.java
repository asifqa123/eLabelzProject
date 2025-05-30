/*
 * In this class we are login to an application with a valid credentials.
 */
package com.elabelz.qa.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.elabelz.qa.base.TestBase;
import com.elabelz.qa.util.TestUtil;
import org.openqa.selenium.support.ui.WebDriverWait; // Added
import org.openqa.selenium.support.ui.ExpectedConditions; // Added

public class LoginPage extends TestBase{
	
	@FindBy(xpath = "//a[contains(@class,'login')]")
	WebElement signInButton;
	
	@FindBy(xpath = "//input[@id='email']")
	WebElement username;
	
	@FindBy(xpath = "//input[@type='password']")
	WebElement password;
	
	@FindBy(xpath = "//span[contains(.,'Sign in')]")
	WebElement loginBtn;
	
	//Initializing the Page Objects:
	public LoginPage(){
		PageFactory.initElements(driver, this);
	}
	
	public String validateLoginPageTitle(){
		return driver.getTitle();
	}
	
	public HomePage login(String un, String pwd){
		WebDriverWait wait = TestUtil.getWebDriverWait(driver);

		wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();

		WebElement userField = wait.until(ExpectedConditions.visibilityOf(username));
		userField.sendKeys(un);

		WebElement passField = wait.until(ExpectedConditions.visibilityOf(password));
		passField.sendKeys(pwd);

		wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();

		return new HomePage();
	}
	
	
}
