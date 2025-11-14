package com.perfume.automation.stepdefinitions;

import com.perfume.automation.utils.BrowserFactory;
import com.perfume.automation.utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber Hooks for setup and teardown
 * Manages WebDriver lifecycle and test environment
 */
public class Hooks {

    private WebDriver driver;

    /**
     * Executed before each scenario
     * Initializes WebDriver and test environment
     */
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("========================================");
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("========================================");

        // Get browser type from config
        String browser = ConfigReader.getInstance().getBrowser();

        // Initialize WebDriver
        driver = BrowserFactory.initializeDriver(browser);

        // Maximize browser window
        driver.manage().window().maximize();

        System.out.println("Browser launched: " + browser);
        System.out.println("Base URL: " + ConfigReader.getInstance().getBaseUrl());
    }

    /**
     * Executed after each scenario
     * Takes screenshot if scenario fails and quits WebDriver
     */
    @After
    public void tearDown(Scenario scenario) {
        System.out.println("========================================");
        System.out.println("Scenario Status: " + scenario.getStatus());
        System.out.println("========================================");

        // Take screenshot if scenario failed
        if (scenario.isFailed()) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshotBytes, "image/png", "Failed Scenario Screenshot");
                System.out.println("Screenshot captured for failed scenario: " + scenario.getName());
            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        }

        // Close browser
        if (driver != null) {
            BrowserFactory.quitDriver();
            System.out.println("Browser closed successfully");
        }

        System.out.println("========================================");
        System.out.println("Finished Scenario: " + scenario.getName());
        System.out.println("========================================\n");
    }

    /**
     * Optional: Tag-specific hooks
     * Executed only for scenarios with @smoke tag
     */
    @Before("@smoke")
    public void beforeSmokeTest(Scenario scenario) {
        System.out.println("Executing SMOKE test: " + scenario.getName());
    }

    /**
     * Optional: Tag-specific hooks
     * Executed only for scenarios with @regression tag
     */
    @Before("@regression")
    public void beforeRegressionTest(Scenario scenario) {
        System.out.println("Executing REGRESSION test: " + scenario.getName());
    }

    /**
     * Optional: Cleanup hook for database or API cleanup
     * Executed after scenarios with @cleanup tag
     */
    @After("@cleanup")
    public void cleanupData(Scenario scenario) {
        System.out.println("Performing cleanup for: " + scenario.getName());
        // Add cleanup logic here (e.g., clear localStorage, reset test data)
        if (driver != null) {
            driver.manage().deleteAllCookies();
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("localStorage.clear();");
            System.out.println("Cookies and localStorage cleared");
        }
    }
}
