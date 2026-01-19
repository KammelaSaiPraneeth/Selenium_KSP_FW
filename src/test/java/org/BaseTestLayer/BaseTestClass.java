package org.BaseTestLayer;

import com.aventstack.extentreports.ExtentTest;
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
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.util.Properties;


public abstract class BaseTestClass {

    int totaltests;
    int passedTests;
    int failedTests;
    int skippedTests;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    protected static final Logger logger = LogManager.getLogger(BaseTestClass.class);
    public static WebDriver driver;
    //public static WebDriver driver = new ChromeDriver();


/*    @BeforeMethod
    public void init()
    {
        driver=initialization();
        try (FileInputStream fis = new FileInputStream("src/main/java/org/config/config.properties")) {
            prop.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public static void initialization() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/java/org/config/config.properties")) {
            prop.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String browser = prop.getProperty("browser").toLowerCase();

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.setCapability("browserVersion", "latest");
                driver = new ChromeDriver(chromeOptions);
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
        driver.get(prop.getProperty("baseUrl"));
     /*   return driver;*/
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {
        String suiteName = context.getSuite().getName();
        String[] groups = context.getIncludedGroups();
        logger.info("Execution for the suited started :" + suiteName);
        ExtentReportManager.setupExtentReport();
        ExtentReportManager.getReport().setSystemInfo("suiteName", suiteName);
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext context) {
        BaseTestClass.initialization();
        String testName = context.getName();
        logger.info("Started execution of the test" + testName);
        ExtentReportManager.getReport().setSystemInfo("TestName ", testName);
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
}
