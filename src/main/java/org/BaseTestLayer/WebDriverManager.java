package org.BaseTestLayer;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.apache.logging.log4j.Logger;
public final class WebDriverManager
{
    private static final Logger logger = LogManager.getLogger(WebDriverManager.class);
    private static volatile WebDriverManager instance; // Singleton instance
    public static ThreadLocal<WebDriver> tdriver= new ThreadLocal<>();

    private WebDriverManager()
    {
    }
    public static WebDriverManager getInstance()
    {
        if (instance == null) {
            synchronized (WebDriverManager.class)
            {
                if (instance == null)
                {
                    instance = new WebDriverManager();
                }
            }
        }
        return instance;
    }

    public void initDriver(String browser)
    {
        if (tdriver.get() != null) {
            logger.warn("Driver already initialized for thread: {}"
                    + Thread.currentThread().getName());
            return;
        }
        logger.info("Initializing '{}' browser for thread: {}"
                , browser, Thread.currentThread().getName());
        switch (browser.toLowerCase().trim()) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                tdriver.set(new ChromeDriver(options));
                break;
            }
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--width=1920");
                options.addArguments("--height=1080");
                tdriver.set(new FirefoxDriver(options));
                break;
            }
            case "edge": {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--start-maximized");
                tdriver.set(new EdgeDriver(options));
                break;
            }
            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: '" + browser + "'. Use chrome | firefox | edge");
        }
        logger.info("Browser '{}' initialized successfully", browser);
    }

    public WebDriver getDriver()
    {
        if (tdriver.get() == null) {
            throw new IllegalStateException("Driver not initialized. Call initDriver(browser) first.");
        }
        return tdriver.get();
    }

    public void quitDriver() {
        if (tdriver.get() != null) {
            logger.info("Quitting browser for thread: {}"
                    + Thread.currentThread().getName());
            tdriver.get().quit();
            tdriver.remove();
            logger.info("Browser quit and ThreadLocal cleared");
        } else {
            logger.warn("quitDriver called but no driver found for thread: {}"
                    + Thread.currentThread().getName());
        }
    }
}




