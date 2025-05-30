package com.elabelz.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider; // Added
import org.testng.annotations.Test;

import com.elabelz.qa.base.TestBase;
import com.elabelz.qa.pages.HomePage;
import com.elabelz.qa.pages.LoginPage;
import com.elabelz.qa.util.JsonTestDataReader; // Added
import java.util.List; // Added
import java.util.Map; // Added

public class LoginPageTest extends TestBase{
	LoginPage loginPage;
	HomePage homePage;
	String actualTitle= "My account - My Store";
	
	public LoginPageTest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		initialization();
		loginPage = new LoginPage();	
	}
	
	@DataProvider
	public Object[][] getLoginDataFromJson() {
	    JsonTestDataReader reader = new JsonTestDataReader();
	    List<Map<String, String>> testDataList = reader.getTestData("loginData");
	    // Expecting 2 parameters: username, password from testdata.json
	    Object[][] data = new Object[testDataList.size()][2];
	    for (int i = 0; i < testDataList.size(); i++) {
	        Map<String, String> dataSet = testDataList.get(i);
	        data[i][0] = dataSet.get("username");
	        data[i][1] = dataSet.get("password");
	    }
	    return data;
	}
	
	@Test(priority=2, dataProvider="getLoginDataFromJson") // Added dataProvider
	public void loginTest(String username, String password){ // Updated method signature
		homePage = loginPage.login(username, password); // Use parameters from data provider
		String title = loginPage.validateLoginPageTitle();
		Assert.assertEquals(title, actualTitle, "Not able to login successfully. Hence, this test case is fail");
	}
	
	
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	
	
	
	

}
