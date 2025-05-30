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
import org.openqa.selenium.support.ui.WebDriverWait; // Added
import org.openqa.selenium.support.ui.ExpectedConditions; // Added

import com.elabelz.qa.base.TestBase;
import com.elabelz.qa.util.TestUtil;

public class HomePage extends TestBase {

	// Locators for elements not defined with @FindBy, used in orderProduct()
    private By sortByDropdownLocator = By.xpath("//select[@class='selectProductSort form-control']");
    private By moreButtonForPrintedDressLocator = By.xpath("//span[contains(.,'More')]");
    // Note: Some XPaths are very absolute and might be brittle.
    // Using the existing ones for now.
    private By proceedToCheckoutSummaryLocator = By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/p[2]/a[1]/span");
    private By proceedToCheckoutSignInLocator = By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/form/p/button/span");
    private By termsCheckboxLocator = By.xpath("//input[contains(@type,'checkbox')]"); // Same as @FindBy termsAndCondition
    private By proceedToCheckoutShippingLocator = By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div/form/p/button/span");


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

	// Search elements
    @FindBy(id = "search_query_top")
    WebElement searchBox;

    @FindBy(name = "submit_search")
    WebElement submitSearchButton;
	
	public String alertMessage;
	public boolean alertFlag;
	
	// Initializing the Page Objects:
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public SearchResultsPage searchProduct(String productName) {
		WebDriverWait wait = TestUtil.getWebDriverWait(driver);
		WebElement searchBoxEl = wait.until(ExpectedConditions.visibilityOf(searchBox));
		searchBoxEl.clear();
		searchBoxEl.sendKeys(productName);
		wait.until(ExpectedConditions.elementToBeClickable(submitSearchButton)).click();
		return new SearchResultsPage();
	}
	
	public String verifyHomePageTitle(){
		return driver.getTitle();
	}
	
	public void orderProduct() throws InterruptedException { // InterruptedException might not be needed after removing Thread.sleep
	    alertFlag = false;
	    WebDriverWait wait = TestUtil.getWebDriverWait(driver);

	    wait.until(ExpectedConditions.elementToBeClickable(women)).click();
	    wait.until(ExpectedConditions.elementToBeClickable(dresses)).click();
	    wait.until(ExpectedConditions.elementToBeClickable(casualDresses)).click();

	    WebElement sortByEl = TestUtil.waitForElementToBeVisible(driver, sortByDropdownLocator);
	    Select sortBy = new Select(sortByEl);
	    sortBy.selectByIndex(2);

	    Actions action = new Actions(driver);
	    WebElement printedDressEl = wait.until(ExpectedConditions.visibilityOf(printedDress));
	    action.moveToElement(printedDressEl).build().perform();

	    TestUtil.waitForElementToBeClickable(driver, moreButtonForPrintedDressLocator).click();

	    WebElement selectSizeEl = wait.until(ExpectedConditions.visibilityOf(selectSize));
	    Select sizeBy = new Select(selectSizeEl);
	    sizeBy.selectByIndex(2);

	    wait.until(ExpectedConditions.elementToBeClickable(addToCart)).click();

	    // Removed Thread.sleep(3000);
	    // The original @FindBy for checkOut seems to be the one inside the modal after adding to cart.
	    wait.until(ExpectedConditions.elementToBeClickable(checkOut)).click();

	    TestUtil.waitForElementToBeClickable(driver, proceedToCheckoutSummaryLocator).click();
	    TestUtil.waitForElementToBeClickable(driver, proceedToCheckoutSignInLocator).click();

	    // Using the @FindBy WebElement for termsAndCondition
	    wait.until(ExpectedConditions.elementToBeClickable(termsAndCondition)).click();
	    // Re-using proceedToCheckoutShippingLocator as it was used before, assuming it's correct for this step.
	    TestUtil.waitForElementToBeClickable(driver, proceedToCheckoutShippingLocator).click();

	    wait.until(ExpectedConditions.elementToBeClickable(checkPayment)).click();
	    wait.until(ExpectedConditions.elementToBeClickable(confirmOrder)).click();

	    try {
	        WebElement alertEl = wait.until(ExpectedConditions.visibilityOf(alert));
	        alertMessage = alertEl.getText();
	        System.out.println(alertMessage);
	        alertFlag = true;
	    } catch (Exception e) {
	        System.out.println("Alert message not found or other exception: " + e.getMessage());
	    }
	    wait.until(ExpectedConditions.elementToBeClickable(backToOrder)).click();
	    wait.until(ExpectedConditions.elementToBeClickable(logOut)).click();
	}
}
