package com.elabelz.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.elabelz.qa.base.TestBase;
import com.elabelz.qa.pages.HomePage;
import com.elabelz.qa.pages.SearchResultsPage;
import com.elabelz.qa.util.JsonTestDataReader;

import java.util.List;
import java.util.Map;

public class SearchPageTest extends TestBase {

    HomePage homePage;
    SearchResultsPage searchResultsPage;

    @BeforeMethod
    public void setUp() {
        initialization(); // Initializes driver and opens URL from TestBase
        homePage = new HomePage();
    }

    @DataProvider(name = "productSearchData")
    public Object[][] getProductSearchDataFromJson() {
        JsonTestDataReader reader = new JsonTestDataReader();
        List<Map<String, String>> testDataList = reader.getTestData("searchData");

        // Assuming "searchTerm" and "expectedProduct" are the keys in testdata.json
        // And that expectedProduct is the partial text we expect in one of the results.
        Object[][] data = new Object[testDataList.size()][2];
        for (int i = 0; i < testDataList.size(); i++) {
            Map<String, String> dataSet = testDataList.get(i);
            data[i][0] = dataSet.get("searchTerm");
            data[i][1] = dataSet.get("expectedProduct"); // This is the expected partial text
        }
        return data;
    }

    @Test(dataProvider = "productSearchData")
    public void verifyProductSearchAndResults(String searchTerm, String expectedPartialText) {
        searchResultsPage = homePage.searchProduct(searchTerm);

        String actualQueryTerm = searchResultsPage.getSearchResultQueryTerm();
        Assert.assertTrue(actualQueryTerm.toLowerCase().contains(searchTerm.toLowerCase()),
                "Search query term displayed '" + actualQueryTerm + "' does not contain the search term '" + searchTerm + "'.");

        int resultsCount = searchResultsPage.getResultsCount();
        Assert.assertTrue(resultsCount > 0, "No products found for search term: " + searchTerm);

        List<String> productNames = searchResultsPage.getProductNames();
        boolean foundExpectedProduct = false;
        for (String name : productNames) {
            if (name.toLowerCase().contains(expectedPartialText.toLowerCase())) {
                foundExpectedProduct = true;
                break;
            }
        }
        Assert.assertTrue(foundExpectedProduct,
                "Expected product containing text '" + expectedPartialText + "' not found in search results for '" + searchTerm + "'. Product names found: " + productNames);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
