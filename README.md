# Selenium Test Automation Framework for Automation Practice

This project provides an automated testing framework using Selenium WebDriver, Java, TestNG, and Maven to test the functionalities of the [Automation Practice](http://automationpractice.com/index.php) website.

## Features
- Automated tests for user registration, login, and product search.
- Page Object Model (POM) design pattern for maintainable test code.
- Test data managed externally using JSON files.
- Automated browser driver management using WebDriverManager.
- Configurable test environment (browser, URL, timeouts) via a properties file.
- Test execution driven by TestNG and built with Maven.
- (Note: Extent Reports integration mentioned in original README needs verification after refactoring).

## Technologies Used
- **Java**: 1.8
- **Selenium WebDriver**: 3.14.0 (for browser automation)
- **TestNG**: 6.11 (as the testing framework)
- **Maven**: (for build automation and dependency management)
- **Jackson**: 2.13.0 (for parsing JSON test data)
- **WebDriverManager**: 5.6.3 (for automatic browser driver management)
- **Log4j**: 1.2.17 (for logging)

## Framework Architecture

The framework follows a standard Page Object Model design and is structured as follows:

- **`src/main/java/com/elabelz/qa/base/TestBase.java`**: Base class for all test scripts. Handles browser initialization, properties loading, WebDriver event listening, and basic teardown.
- **`src/main/java/com/elabelz/qa/pages/`**: Contains Page Object classes (e.g., `HomePage.java`, `LoginPage.java`, `RegistrationPage.java`, `SearchResultsPage.java`) that define WebElements and methods to interact with specific pages of the application.
- **`src/main/java/com/elabelz/qa/util/`**: Utility classes:
    - **`JsonTestDataReader.java`**: Reads test data from `testdata.json`.
    - **`TestUtil.java`**: Provides general test utility functions, including explicit wait helpers.
    - **`WebEventListener.java`**: Implements WebDriver event listening for logging.
- **`src/main/java/com/elabelz/qa/config/config.properties`**: Configuration file for application URL, browser type, user credentials (for test purposes), and wait timeouts.
- **`src/main/java/com/elabelz/qa/testdata/testdata.json`**: JSON file containing test data for various test cases (e.g., login credentials, registration details, search terms).
- **`src/test/java/com/elabelz/qa/testcases/`**: Contains TestNG test classes (e.g., `LoginPageTest.java`, `RegistrationPageTest.java`, `SearchPageTest.java`) that implement the actual test logic using page objects and test data.
- **`pom.xml`**: Maven Project Object Model file. Defines project dependencies, plugins, and build settings.
- **`testNG.xml`**: TestNG suite configuration file, defining the test suites and classes to be executed.

## Setup Instructions

1.  **Prerequisites**:
    *   Java Development Kit (JDK) 1.8 or higher installed.
    *   Apache Maven installed.
    *   An internet connection (for downloading dependencies and WebDriver binaries).

2.  **Clone the Repository**:
    ```bash
    git clone <repository_url>
    cd <repository_directory>
    ```

3.  **Driver Management**:
    This framework uses WebDriverManager, which automatically downloads and sets up the appropriate browser drivers (e.g., ChromeDriver, GeckoDriver) as needed. No manual driver installation is required.

4.  **Configuration**:
    Modify `src/main/java/com/elabelz/qa/config/config.properties` if you need to change:
    *   `url`: The base URL of the application.
    *   `browser`: The browser to use (e.g., `chrome`, `firefox`).
    *   `username`/`password`: Default credentials for tests that require login.
    *   `implicitWait`, `pageLoadTimeout`, `explicitWaitTimeout`: Default timeouts for WebDriver.

## How to Run Tests

Tests are executed using Maven and TestNG.

1.  **Open a terminal or command prompt.**
2.  **Navigate to the project's root directory.**
3.  **Run tests using Maven**:

    To run all tests defined in `testNG.xml`:
    ```bash
    mvn clean test
    ```
    If `testNG.xml` is not automatically picked up by the Surefire plugin configuration in `pom.xml`, you might need to specify it (though typically the default name `testng.xml` in the project root or `src/test/resources` is found):
    ```bash
    mvn clean test -DsuiteXmlFile=testNG.xml
    ```
    *(Note: The current `testNG.xml` is in the project root.)*

## Test Reports

-   **TestNG Reports**: After execution, TestNG generates HTML reports in the `test-output/` directory. Open `test-output/index.html` or `test-output/emailable-report.html` in a web browser to view the results.
-   **Extent Reports**: The original `README.md` mentioned Extent Reports. If this integration is still active and configured, reports might also be found in a directory specified by its configuration (commonly `ExtentReports/`). This needs verification.

## Original Scenarios Covered

The framework aims to automate the following scenarios on the [Automation Practice](http://automationpractice.com/index.php) website:

1.  **Register New User**: Automates the creation of a new user account.
    *(Note: Current test data for registration in `testdata.json` is minimal. The `RegistrationPage.java` and `RegistrationPageTest.java` might require more data or adjustments for full form submission.)*
2.  **Login with Existing User**: Automates the login process for an existing user.
3.  **Product Search and Verification**: (New test case) Automates searching for a product and verifying search results.
4.  **Buy One Product**: (Original scenario - `HomePage.orderProduct()` exists but no dedicated test class was found in the initial exploration for this specific end-to-end flow. This could be a future addition.)
