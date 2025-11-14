package com.perfume.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestRunner class for executing Cucumber tests with TestNG
 * Configured with Cucumber options for feature files, step definitions, and reporting
 */
@CucumberOptions(
    features = "src/test/resources/features",           // Path to feature files
    glue = "com.perfume.automation.stepdefinitions",    // Package containing step definitions
    plugin = {
        "pretty",                                         // Console output formatting
        "html:target/cucumber-reports/cucumber-html-report.html",  // HTML report
        "json:target/cucumber-reports/cucumber.json",     // JSON report
        "junit:target/cucumber-reports/cucumber.xml"      // XML report for CI/CD
    },
    monochrome = true,                                   // Readable console output
    dryRun = false,                                      // Set to true to check if all steps are defined
    tags = ""                                            // Run all scenarios, or use tags like "@smoke"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Enable parallel execution at scenario level
     * Parallel threads count is configured in testng.xml
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
