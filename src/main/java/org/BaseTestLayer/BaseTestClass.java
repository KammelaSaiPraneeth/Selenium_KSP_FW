package org.BaseTestLayer;

import com.aventstack.extentreports.ExtentTest;
import org.TestNGUtilities.ExtentReportManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;


public class BaseTestClass {

    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    protected static final Logger logger= LogManager.getLogger(BaseTestClass.class);

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context)
    {
        String suiteName=context.getSuite().getName();
        String[] groups=context.getIncludedGroups();
        logger.info("Execution for the suited started :"+suiteName);
        ExtentReportManager.setupExtentReport();
        ExtentReportManager.getReport().setSystemInfo("suiteName",suiteName);
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext context)
    {
        String testName=context.getName();
        logger.info("Started execution of the test"+testName);
        ExtentReportManager.getReport().setSystemInfo("TestName ",testName);
    }

    int totaltests;
    int passedTests;
    int failedTests;
    int skippedTests;
    @AfterMethod
            public void afterMethod(ITestResult result)
    {
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
    }
   @AfterSuite
    public void afterSuite()
   {
       int percentage=(totaltests/passedTests)*100;

       ExtentReportManager.createCustomTable(String.valueOf(totaltests),
               String.valueOf(passedTests),
               String.valueOf(failedTests),
               String.valueOf(skippedTests),
               String.valueOf(percentage));

       ExtentReportManager.flushReport();
   }
}