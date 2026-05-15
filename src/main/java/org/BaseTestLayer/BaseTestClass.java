package org.BaseTestLayer;

import org.TestNGUtilities.ExtentReportManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


public abstract class BaseTestClass {
    protected static final Logger logger = LogManager.getLogger(BaseTestClass.class);
    public static WebDriver driver;
    int totaltests;
    int passedTests;
    int failedTests;
    int skippedTests;
    ConfigManager config = ConfigManager.getInstance();

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {
        String suiteName = context.getSuite().getName();
        String[] groups = context.getIncludedGroups();
        logger.info("Execution for the suited started :" + suiteName);
        ExtentReportManager.setupExtentReport();
        ExtentReportManager.getReport().setSystemInfo("suiteName", suiteName);
    }

    @BeforeMethod
    public void init() throws InterruptedException {
        driver= initialization();
        driver.get(config.getBaseUrl());
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext context) {
        String testName = context.getName();
        logger.info("Started execution of the test" + testName);
        ExtentReportManager.getReport().setSystemInfo("TestName ", testName);
    }

    @AfterTest
    public void afterTest()
    {
        System.out.println(" This is after test method");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        totaltests++;
        switch (result.getStatus()) {
            case ITestResult.FAILURE:
                failedTests++;
                ExtentReportManager.getTest().fail(result.getThrowable());
                break;
            case ITestResult.SKIP:
                skippedTests++;
                ExtentReportManager.getTest().skip(result.getThrowable());
                break;
            default:
                passedTests++;
                ExtentReportManager.getTest().pass("Test has been passed");
                break;
        }
        driver.quit();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        int totalTestsConsidered = totaltests - skippedTests;
        double percentage = (double) passedTests / totalTestsConsidered * 100;
        System.out.println("Percentage" + percentage);
        ExtentReportManager.createCustomTable(String.valueOf(totaltests),
                String.valueOf(passedTests),
                String.valueOf(failedTests),
                String.valueOf(skippedTests),
                String.format("%.2f", percentage));
        ExtentReportManager.flushReport();
    }


    //Method to init the driver

    public WebDriver initialization() {
        String browser = config.getBrowser().toLowerCase();
        switch (browser) {
            case "chrome":
                System.out.println(" This is "+browser + " Browser");
                ChromeOptions chromeOptions = new ChromeOptions();
                //chromeOptions.addArguments("--start-maximized");
               // chromeOptions.setCapability("browserVersion", "latest");
               // try {
                    driver = new ChromeDriver(chromeOptions);
              /*  } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }*/
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
                firefoxOptions.setCapability("browserVersion", "latest");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                driver = new EdgeDriver(edgeOptions);
                break;

            case "safari":
                SafariOptions safariOptions = new SafariOptions();
                driver = new SafariDriver(safariOptions);
                break;

            default:
                throw new RuntimeException("Invalid browser in config: " + browser);
        }
        return driver;
    }

}
