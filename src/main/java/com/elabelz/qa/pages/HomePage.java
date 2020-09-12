/*
 * In this class we are selecting, adding to a cart and purchasing a product and after successful purchase we are logging out from the application.
 */
package com.elabelz.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.elabelz.qa.base.TestBase;
import com.elabelz.qa.util.TestUtil;

public class HomePage extends TestBase {

	@FindBy(xpath = "//a[contains(@title,'Women')]")
	WebElement women;

	@FindBy(xpath = "//a[@class='subcategory-name'][contains(.,'Dresses')]")
	WebElement dresses;
	
	@FindBy(xpath = "//a[@class='subcategory-name'][contains(.,'Casual Dresses')]")
	WebElement casualDresses;
	
	@FindBy(xpath = "//img[@title='Printed Dress']")
	WebElement printedDress;
	
	@FindBy(xpath = "//span[contains(.,'Add to cart')]")
	WebElement addToCart;
	
	@FindBy(xpath = "//select[contains(@name,'1')]")
	WebElement selectSize;
	
	@FindBy(xpath = "//span[contains(.,'Proceed to checkout')]")
	WebElement checkOut;
	
	@FindBy(xpath = "/html/body/div[1]/div[2]/div/div[3]/div/p[2]/a[1]/span")
	WebElement proceedToCheckOut;
	
	@FindBy(xpath = "/html/body/div[1]/div[2]/div/div[3]/div/form/p/button/span")
	WebElement proceedToCheckOut1;
	
	@FindBy(xpath = "//input[@type='checkbox']")
	WebElement termsAndCondition;
	
	@FindBy(xpath = "/html/body/div[1]/div[2]/div/div[3]/div/div/form/p/button/span")
	WebElement proceedToCheckOut2;
	
	@FindBy(xpath = "//a[contains(@class,'cheque')]")
	WebElement checkPayment;
	
	@FindBy(xpath = "//span[contains(.,'I confirm my order')]")
	WebElement confirmOrder;
	
	@FindBy(xpath = "//p[contains(.,'Your order on My Store is complete.')]")
	WebElement alert;
	
	@FindBy(xpath = "//a[contains(.,'Back to orders')]")
	WebElement backToOrder;
	
	@FindBy(xpath = "//a[@class='logout']")
	WebElement logOut;
	
	public String alertMessage;
	public boolean alertFlag;
	
	// Initializing the Page Objects:
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public String verifyHomePageTitle(){
		return driver.getTitle();
	}
	
	public void orderProduct() throws InterruptedException
	{
		alertFlag= false;
		TestUtil.waitForAnElement(women);
		TestUtil.waitForAnElement(dresses);
		TestUtil.waitForAnElement(casualDresses);
		Select sortBy = new Select(driver.findElement(By.xpath("//select[@class='selectProductSort form-control']")));
		sortBy.selectByIndex(2);
		Actions action = new Actions(driver);
		action.moveToElement(printedDress).build().perform();;
		driver.findElement(By.xpath("//span[contains(.,'More')]")).click();
		Select sizeBy = new Select(selectSize);
		sizeBy.selectByIndex(2);
		TestUtil.waitForAnElement(addToCart);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[contains(.,'Proceed to checkout')]")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/p[2]/a[1]/span")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/form/p/button/span")).click();
		driver.findElement(By.xpath("//input[contains(@type,'checkbox')]")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div/form/p/button/span")).click();
		TestUtil.waitForAnElement(checkPayment);
		driver.findElement(By.xpath("//span[contains(.,'I confirm my order')]")).click();
		try
		{
			alertMessage = alert.getText();
			System.out.println(alertMessage);
			alertFlag = true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		TestUtil.waitForAnElement(backToOrder);
		TestUtil.waitForAnElement(logOut);
	}
}
