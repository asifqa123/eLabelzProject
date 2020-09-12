/*
 * In this test class we are launching an application
 * And we are testing the registration process
 */
package com.elabelz.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.elabelz.qa.base.TestBase;
import com.elabelz.qa.pages.HomePage;
import com.elabelz.qa.pages.RegistrationPage;
import com.elabelz.qa.util.TestUtil;

public class RegistrationPageTest extends TestBase 
{
	RegistrationPage regPage;
	HomePage homePage;
	String sheetName = "registrationData";
	String actualTitle= "My account - My Store";

	public RegistrationPageTest(){
		super();
		
	}
	
	@BeforeMethod
	public void setUp() throws InterruptedException {
		initialization();
		regPage= new RegistrationPage();
	}
	
	@DataProvider
	public Object[][] getRegistrationData(){
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}
	
	@Test(priority=1,dataProvider="getRegistrationData")
	public void CreatingAndRegisteringAccount(String email, String gen, String cusFirst, String cusLast, String passw, String comp, 
			 String adr1, String adr2, String yourCity, String postcd, String addTxt, String homeph, String mobph, String aliasAdd)
	{
		try {
			regPage.createNewContact(email, gen, cusFirst, cusLast, passw, comp, adr1, adr2, yourCity, postcd, addTxt, homeph, mobph, aliasAdd);
			
			if(regPage.altBox)
			{
				String title = regPage.validateLoginPageTitle();
				System.out.println(title);
				Assert.assertEquals(title, actualTitle);
				System.out.println("Test Case Is Pass"); 
			}
			else
			{
				Assert.fail("User already registered. Register with someother email address !!!");
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
