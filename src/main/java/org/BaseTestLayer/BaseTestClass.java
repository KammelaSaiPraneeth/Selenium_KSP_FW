package org.BaseTestLayer;

import org.TestNGUtilities.ExtentReportManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.config.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;


public abstract class BaseTestClass {
    protected static final Logger logger = LogManager.getLogger(BaseTestClass.class);
    ConfigManager config = ConfigManager.getInstance();
    private final WebDriverManager driverManager = WebDriverManager.getInstance();
    public static WebDriver driver;
    int totaltests;
    int passedTests;
    int failedTests;
    int skippedTests;


    public WebDriver getDriver() {
        return driverManager.getDriver();
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {
        String suiteName = context.getSuite().getName();
        logger.info("Suite execution started: {}", suiteName);
        ExtentReportManager.setupExtentReport();
        ExtentReportManager.getReport().setSystemInfo("Suite Name", suiteName);
        ExtentReportManager.getReport().setSystemInfo("Environment", config.getEnvironment());
        ExtentReportManager.getReport().setSystemInfo("Browser", config.getBrowser());
    }

    @BeforeMethod(alwaysRun = true)
    public void init()
    {
        String  browser= config.getBrowser();
        logger.info("Initializing driver for browser: {}", browser);
        driverManager.initDriver(browser);
        getDriver().get(config.getBaseUrl());
        logger.info("Navigated to base URL: {}", config.getBaseUrl());
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext context) {
        String testName = context.getName();
        logger.info("Started execution of the test{}", testName);
        ExtentReportManager.getReport().setSystemInfo("TestName ", testName);
    }

    @AfterTest
    public void afterTest()
    {
        logger.info("Test block execution completed");
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
        driverManager.quitDriver();
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
}
