package com.perfume.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * BrowserFactory for creating WebDriver instances
 * Supports Chrome, Firefox, and Edge browsers
 * Thread-safe implementation for parallel testing
 */
public class BrowserFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ConfigReader config = ConfigReader.getInstance();

    /**
     * Initialize WebDriver based on config properties
     */
    public static WebDriver initializeDriver() {
        return initializeDriver(config.getBrowser());
    }


    public static WebDriver initializeDriver(String browserName) {
        WebDriver webDriver = null;

        switch (browserName.toLowerCase()) {
            case "chrome":
                webDriver = createChromeDriver();
                break;
            case "firefox":
                webDriver = createFirefoxDriver();
                break;
            case "edge":
                webDriver = createEdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        // Set timeouts
        webDriver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        webDriver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));

        // Maximize window
        webDriver.manage().window().maximize();

        // Store in ThreadLocal
        driver.set(webDriver);

        return webDriver;
    }

    /**
     * Create Chrome WebDriver instance
     */
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        // Allow JavaScript to fetch local files from file:// protocol
        options.addArguments("--allow-file-access-from-files");

        return new ChromeDriver(options);
    }

    /**
     * Create Firefox WebDriver instance
     */
    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless");
        }

        options.addPreference("dom.webnotifications.enabled", false);

        return new FirefoxDriver(options);
    }

    /**
     * Create Edge WebDriver instance
     */
    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless");
        }

        options.addArguments("--disable-notifications");

        return new EdgeDriver(options);
    }

    /**
     * Get current thread's WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            throw new RuntimeException("WebDriver not initialized for this thread!");
        }
        return driver.get();
    }

    /**
     * Quit and clean up WebDriver
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
