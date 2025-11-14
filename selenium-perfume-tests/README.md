# Perfume E-Commerce Selenium Test Automation Framework

A comprehensive test automation framework for the Perfume E-Commerce website using Selenium WebDriver, Cucumber BDD, and TestNG.

## Table of Contents
- [Framework Overview](#framework-overview)
- [Technologies and Tools](#technologies-and-tools)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation and Setup](#installation-and-setup)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Reports](#test-reports)
- [Writing New Tests](#writing-new-tests)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)

## Framework Overview

This framework implements industry best practices for test automation:

- **Page Object Model (POM)**: Separation of page elements and test logic
- **Behavior-Driven Development (BDD)**: Cucumber with Gherkin syntax
- **Cross-Browser Testing**: Support for Chrome, Firefox, and Edge
- **Parallel Execution**: TestNG parallel test execution
- **Data-Driven Testing**: CSV and Excel file support
- **Custom Wait Strategies**: JavaScript event-based synchronization
- **Comprehensive Reporting**: HTML, JSON, XML, and ExtentReports

## Technologies and Tools

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 11+ | Programming language |
| Selenium WebDriver | 4.16.1 | Browser automation |
| Cucumber | 7.15.0 | BDD framework |
| TestNG | 7.8.0 | Test execution framework |
| Maven | 3.6+ | Build and dependency management |
| WebDriverManager | 5.6.3 | Automatic driver management |
| Apache POI | 5.2.5 | Excel file handling |
| OpenCSV | 5.9 | CSV file handling |
| ExtentReports | 5.1.1 | Advanced reporting |

## Project Structure

```
selenium-perfume-tests/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/perfume/automation/
│   │           ├── components/
│   │           │   └── Header.java              # Reusable header component
│   │           ├── pages/
│   │           │   ├── BasePage.java           # Base page with common methods
│   │           │   ├── HomePage.java           # Home page POM
│   │           │   ├── ShopPage.java           # Shop page POM
│   │           │   ├── ProductDetailPage.java  # Product detail POM
│   │           │   ├── CartPage.java           # Cart page POM
│   │           │   └── ContactPage.java        # Contact page POM
│   │           └── utils/
│   │               ├── BrowserFactory.java      # Cross-browser setup
│   │               ├── ConfigReader.java        # Configuration reader
│   │               ├── WaitHelper.java          # Custom wait utilities
│   │               └── TestDataReader.java      # CSV/Excel data reader
│   ├── test/
│   │   ├── java/
│   │   │   └── com/perfume/automation/
│   │   │       ├── runners/
│   │   │       │   └── TestRunner.java         # Cucumber-TestNG runner
│   │   │       └── stepdefinitions/
│   │   │           ├── Hooks.java              # Before/After hooks
│   │   │           ├── CommonSteps.java        # Shared step definitions
│   │   │           ├── HomeSteps.java          # Home page steps
│   │   │           ├── ShopSteps.java          # Shop page steps
│   │   │           ├── CartSteps.java          # Cart steps
│   │   │           ├── ContactSteps.java       # Contact form steps
│   │   │           └── ProductDetailSteps.java # Product detail steps
│   │   └── resources/
│   │       ├── features/
│   │       │   ├── home.feature                # Home page scenarios (8 scenarios)
│   │       │   ├── shop.feature                # Shop page scenarios (10 scenarios)
│   │       │   ├── cart.feature                # Cart scenarios (12 scenarios)
│   │       │   ├── contact.feature             # Contact form scenarios (12 scenarios)
│   │       │   └── product-detail.feature      # Product detail scenarios (12 scenarios)
│   │       ├── testdata/
│   │       │   ├── contact-form-data.csv       # Contact form test data
│   │       │   └── search-data.csv             # Search test data
│   │       └── config.properties               # Test configuration
├── pom.xml                                      # Maven dependencies
├── testng.xml                                   # TestNG suite configuration
└── README.md                                    # This file
```

## Prerequisites

Before setting up the framework, ensure you have:

1. **Java Development Kit (JDK) 11 or higher**
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use OpenJDK
   - Verify installation: `java -version`

2. **Apache Maven 3.6 or higher**
   - Download from [Maven website](https://maven.apache.org/download.cgi)
   - Verify installation: `mvn -version`

3. **IDE (Optional but recommended)**
   - IntelliJ IDEA, Eclipse, or VS Code with Java extensions

4. **Web Browsers**
   - Chrome, Firefox, or Edge (drivers are auto-downloaded by WebDriverManager)

## Installation and Setup

### 1. Clone or Download the Project

```bash
cd /path/to/your/workspace
# If using Git:
git clone <repository-url>
cd selenium-perfume-tests
```

### 2. Install Dependencies

```bash
mvn clean install
```

This command will:
- Download all required Maven dependencies
- Compile the source code
- Run the build lifecycle

### 3. Verify Setup

```bash
mvn clean compile
```

If successful, you should see `BUILD SUCCESS`.

## Configuration

### config.properties

Located at `src/test/resources/config.properties`:

```properties
# Base URL of the application under test
base.url=file:///C:/Users/Elias/Desktop/QA/WebSWER353/index.html

# Browser: chrome, firefox, edge
browser=chrome

# Headless mode: true or false
headless=false

# Wait timeouts (in seconds)
implicit.wait=10
explicit.wait=15
page.load.timeout=30

# Parallel execution
parallel.thread.count=3
```

**Important**: Update `base.url` to match your local file path.

### testng.xml

Located at project root. Configure parallel execution:

```xml
<suite name="Perfume E-Commerce Test Suite" parallel="methods" thread-count="3">
```

Options for `parallel`:
- `methods`: Run test methods in parallel
- `classes`: Run test classes in parallel
- `tests`: Run test tags in parallel

## Running Tests

### 1. Run All Tests

```bash
mvn clean test
```

### 2. Run Specific Feature File

```bash
mvn test -Dcucumber.features="src/test/resources/features/home.feature"
```

### 3. Run Tests by Tags

Add tags to your scenarios in `.feature` files:

```gherkin
@smoke
Scenario: Verify home page loads successfully
```

Then run:

```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

### 4. Run Tests in Specific Browser

```bash
mvn test -Dbrowser=firefox
```

### 5. Run Tests in Headless Mode

```bash
mvn test -Dheadless=true
```

### 6. Run with TestNG XML

```bash
mvn test -DsuiteXmlFile=testng.xml
```

### 7. Run Tests in Parallel

Parallel execution is configured in `testng.xml`. To change thread count:

```bash
mvn test -DthreadCount=5
```

## Test Reports

After test execution, reports are generated in:

### 1. Cucumber HTML Report
- Location: `target/cucumber-reports/cucumber-html-report.html`
- Open in browser for detailed scenario results

### 2. Cucumber JSON Report
- Location: `target/cucumber-reports/cucumber.json`
- Used for CI/CD integration and custom reporting

### 3. Cucumber XML Report
- Location: `target/cucumber-reports/cucumber.xml`
- JUnit format for Jenkins/CI tools

### 4. ExtentReports
- Location: `test-output/extent-reports/`
- Advanced HTML report with charts and statistics

### 5. Console Output
- Real-time test execution logs in terminal

### Viewing Reports

```bash
# Open HTML report (Windows)
start target/cucumber-reports/cucumber-html-report.html

# Open HTML report (Mac/Linux)
open target/cucumber-reports/cucumber-html-report.html
```

## Writing New Tests

### 1. Create a Feature File

Create `.feature` file in `src/test/resources/features/`:

```gherkin
Feature: New Feature

  Scenario: Test scenario name
    Given I am on the home page
    When I perform some action
    Then I should see expected result
```

### 2. Implement Step Definitions

Create step definition class in `src/test/java/com/perfume/automation/stepdefinitions/`:

```java
@When("I perform some action")
public void iPerformSomeAction() {
    // Implementation
}

@Then("I should see expected result")
public void iShouldSeeExpectedResult() {
    Assert.assertTrue(condition, "Error message");
}
```

### 3. Create Page Object (if needed)

Add methods to existing page classes or create new ones in `src/main/java/com/perfume/automation/pages/`:

```java
public class NewPage extends BasePage {
    private By element = By.cssSelector("[data-testid='element-id']");

    public void clickElement() {
        click(element);
    }
}
```

## Best Practices

### 1. Use data-testid Selectors
```java
By element = By.cssSelector("[data-testid='product-name']");
```

### 2. Use Custom Wait Strategies
```java
waitHelper.waitForProductsLoaded();  // Wait for custom event
```

### 3. Use Page Object Pattern
```java
HomePage homePage = new HomePage(driver);
homePage.clickShopNowButton();
```

### 4. Use Descriptive Scenario Names
```gherkin
Scenario: User should be able to add product to cart from home page
```

### 5. Keep Step Definitions Reusable
```java
@When("I click the {string} button")
public void iClickTheButton(String buttonName) {
    // Generic button click
}
```

### 6. Use Background for Common Steps
```gherkin
Background:
  Given I am on the home page
  And I wait for products to load
```

### 7. Use Scenario Outline for Data-Driven Tests
```gherkin
Scenario Outline: Search for products
  When I search for "<keyword>"
  Then I should see results

  Examples:
    | keyword |
    | Bloom   |
    | Essence |
```

## Troubleshooting

### Issue: Tests fail with "Element not found"
**Solution**:
- Check if data-testid attributes exist on elements
- Increase wait timeouts in config.properties
- Use explicit waits: `waitHelper.waitForElementVisible(locator)`

### Issue: Browser doesn't launch
**Solution**:
- Ensure browser is installed
- Check WebDriverManager is downloading correct driver
- Try running: `mvn clean install -U`

### Issue: Tests pass locally but fail in CI/CD
**Solution**:
- Use headless mode in CI: `headless=true`
- Increase timeouts for slower CI environments
- Check browser versions compatibility

### Issue: Parallel execution causes failures
**Solution**:
- Ensure thread-safe driver management (ThreadLocal is used)
- Reduce thread count in testng.xml
- Check for shared test data conflicts

### Issue: Screenshots not captured
**Solution**:
- Verify Hooks.java is properly configured
- Check driver instance is not null
- Ensure target directory has write permissions

### Issue: Cannot find feature files
**Solution**:
- Verify features path in TestRunner: `features = "src/test/resources/features"`
- Ensure .feature files are in correct directory
- Rebuild project: `mvn clean compile`

## Advanced Usage

### Running Specific Scenarios

```bash
# Run scenarios by name pattern
mvn test -Dcucumber.features="src/test/resources/features" -Dcucumber.filter.name=".*cart.*"
```

### Custom Configuration

Create multiple config files for different environments:

```bash
mvn test -Dconfig.file=config-staging.properties
```

### Data-Driven Tests

Use TestDataReader to read CSV/Excel:

```java
List<Map<String, String>> testData = TestDataReader.readCSV("testdata/contact-form-data.csv");
```

## Contributing

When adding new tests:
1. Follow existing naming conventions
2. Add JavaDoc comments to public methods
3. Update this README if adding new features
4. Ensure all tests pass before committing

## Support

For issues or questions:
- Check the Troubleshooting section
- Review console logs and reports
- Verify configuration in config.properties

---

**Framework Version**: 1.0.0
**Last Updated**: 2024
**Author**: Perfume E-Commerce QA Team
