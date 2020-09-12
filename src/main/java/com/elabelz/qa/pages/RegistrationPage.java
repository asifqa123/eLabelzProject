/*
 * In this class.
 * We are launching an application.
 * And registering a user with all the required details.
 * And in case if the user is already registered then we are failing it saying "user already exists".
 */
package com.elabelz.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.elabelz.qa.base.TestBase;
import com.elabelz.qa.util.TestUtil;

public class RegistrationPage extends TestBase{
	
	@FindBy(xpath = "//a[contains(@class,'login')]")
	WebElement signUP;
	
	@FindBy(xpath = "//input[@name='email_create']")
	WebElement enterEmail;
	
	@FindBy(xpath = "//span[contains(.,'Create an account')]")
	WebElement createAcc;
	
	@FindBy(xpath = "//div[@class='alert alert-danger']")
	WebElement dangerAlert;
	
	@FindBy(xpath = "//input[@type='radio'][contains(@id,'gender1')]")
	WebElement gender;
	
	@FindBy(xpath = "//input[@name='customer_firstname']")
	WebElement custFirstName;
	
	@FindBy(name="customer_lastname")
	WebElement custLastName;
	
	@FindBy(xpath = "//input[@type='password']")
	WebElement password;
	
	@FindBy(xpath = "//select[@name='days']")
	WebElement dobDays;
	
	@FindBy(xpath = "//select[@name='months']")
	WebElement dobMonths;

	@FindBy(xpath = "//select[@name='years']")
	WebElement dobYears;
	
	@FindBy(name="company")
	WebElement company;
	
	@FindBy(name="address1")
	WebElement address1;
	
	@FindBy(name="address2")
	WebElement address2;
	
	@FindBy(name="city")
	WebElement city;
	
	@FindBy(name="postcode")
	WebElement postcode;
	
	@FindBy(name="id_country")
	WebElement idCountry;
	
	@FindBy(xpath = "//textarea[@class='form-control']")
	WebElement additionalTxt;
	
	@FindBy(name="phone")
	WebElement homePhone;
	
	@FindBy(name="phone_mobile")
	WebElement mobile;
	
	@FindBy(xpath = "//input[@value='My address']")
	WebElement myAddressAlias;
	
	@FindBy(xpath = "//span[contains(.,'Register')]")
	WebElement registerButton;
	
	public boolean altBox=false;
	// Initializing the Page Objects:
	public RegistrationPage() {
		PageFactory.initElements(driver, this);
	}
	
	

	
	public void createNewContact(String email, String gen, String cusFirst, String cusLast, String passw, String comp, 
								 String adr1, String adr2, String yourCity, String postcd,  String addTxt, String homeph, String mobph, String aliasAdd) throws InterruptedException
	{
		TestUtil.waitForAnElement(signUP);
		enterEmail.sendKeys(email);
		TestUtil.waitForAnElement(createAcc);
		altBox= driver.findElements(By.xpath("//h1[contains(.,'Create an account')]")).size()!=0;
		
		if(!altBox){
			System.out.println("User account already exists. Hence, no need to create again !!!");
		}
		else{
			gender.sendKeys(gen);
			custFirstName.sendKeys(cusFirst);
			custLastName.sendKeys(cusLast);
			password.sendKeys(passw);
			
			Select BirthDay = new Select(driver.findElement(By.name("days")));
			BirthDay.selectByIndex(2);
			Select BirthMonth = new Select(driver.findElement(By.name("months")));
			BirthMonth.selectByIndex(2);
			Select BirthYear = new Select(driver.findElement(By.name("years")));
			BirthYear.selectByIndex(15);
			
			company.sendKeys(comp);
			address1.sendKeys(adr1);
			address2.sendKeys(adr2);
			city.sendKeys(yourCity);
			
			Select YourState = new Select(driver.findElement(By.name("id_state")));
			YourState.selectByIndex(5);
			postcode.sendKeys(postcd);
			Select YourCountry = new Select(driver.findElement(By.name("id_country")));
			YourCountry.selectByIndex(1);
			additionalTxt.sendKeys(addTxt);
			homePhone.sendKeys(homeph);
			mobile.sendKeys(mobph);
			myAddressAlias.clear();
			myAddressAlias.sendKeys(aliasAdd);
			TestUtil.waitForAnElement(registerButton);
			System.out.println("!!!!  Registered  !!!!");
			
			}
	} 
	
	public String validateLoginPageTitle(){
		return driver.getTitle();
	}
}
