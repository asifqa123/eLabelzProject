package com.elabelz.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.elabelz.qa.base.TestBase;
import com.elabelz.qa.util.TestUtil;

public class SearchResultsPage extends TestBase {

    @FindBy(xpath = "//ul[contains(@class,'product_list')]/li")
    private List<WebElement> productList;

    // This XPath is relative to each item in productList.
    // It cannot be directly used with @FindBy for a single WebElement here
    // but will be used within the getProductNames() method.
    // private String productNameRelativeXpath = ".//a[@class='product-name']";

    @FindBy(xpath = "//span[@class='lighter']")
    private WebElement searchResultQueryTerm;

    @FindBy(xpath = "//h1[contains(@class,'page-heading')]/span[@class='heading-counter']")
    private WebElement searchResultCounter; // Element to get the count of products

    public SearchResultsPage() {
        super(); // Though TestBase constructor doesn't call super(), it's good practice if TestBase had a more complex hierarchy
        PageFactory.initElements(driver, this);
    }

    public int getResultsCount() {
        WebDriverWait wait = TestUtil.getWebDriverWait(driver);
        // Wait for the counter element to be visible
        WebElement counterElement = wait.until(ExpectedConditions.visibilityOf(searchResultCounter));
        String counterText = counterElement.getText(); // e.g., "7 results have been found."
        // Extract the number from the text
        try {
            return Integer.parseInt(counterText.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            System.out.println("Could not parse results count from text: " + counterText);
            return 0;
        }
    }

    public List<String> getProductNames() {
        WebDriverWait wait = TestUtil.getWebDriverWait(driver);
        // Wait for the product list to be present
        wait.until(ExpectedConditions.visibilityOfAllElements(productList));

        List<String> names = new ArrayList<>();
        if (productList != null) {
            for (WebElement productItem : productList) {
                // Find product name relative to the productItem
                // Using explicit wait for each product name element to ensure it's loaded
                try {
                    WebElement productNameEl = wait.until(ExpectedConditions.visibilityOf(productItem.findElement(By.xpath(".//a[@class='product-name']"))));
                    names.add(productNameEl.getText());
                } catch (Exception e) {
                    // Handle if a product name is not found or visible for some reason
                    System.out.println("Could not find product name for an item: " + e.getMessage());
                }
            }
        }
        return names;
    }

    public String getSearchResultQueryTerm() {
        WebDriverWait wait = TestUtil.getWebDriverWait(driver);
        return wait.until(ExpectedConditions.visibilityOf(searchResultQueryTerm)).getText().replace("\"", ""); // Remove quotes
    }
}
