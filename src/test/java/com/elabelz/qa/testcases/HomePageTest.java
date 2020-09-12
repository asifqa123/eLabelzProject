package com.elabelz.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.elabelz.qa.base.TestBase;
import com.elabelz.qa.pages.HomePage;
import com.elabelz.qa.pages.LoginPage;
import com.elabelz.qa.util.TestUtil;

public class HomePageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	String actualTitle= "My account - My Store";
	String actualAlert = "Your order on My Store is complete.";
	public HomePageTest() {
		super();
	}

	//before test case -- launch the browser and login
	//@test -- execute test case
	//after each test case -- close the browser
	
	@BeforeClass
	public void setUp() {
		initialization();
		loginPage = new LoginPage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	@Test(priority=3)
	public void verifyHomePageTitleTest(){
		String homePageTitle = homePage.verifyHomePageTitle();
		Assert.assertEquals(homePageTitle, actualTitle , "Home page title not matched");
	}
	
	@Test(priority=4)
	public void addProductToCart() throws InterruptedException
	{
		homePage.orderProduct();
		Assert.assertEquals(homePage.alertFlag, true);
	}
	
	
	@AfterClass
	public void tearDown(){
		driver.quit();
	}
	
}
