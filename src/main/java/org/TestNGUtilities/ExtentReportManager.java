package org.TestNGUtilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentReports extReports;
    public static ExtentTest extTest;


    public static void setupExtentReport()
    {
        ExtentSparkReporter  extSparkReporter= new ExtentSparkReporter("./reports/extent.html");
        extReports = new ExtentReports();
        extSparkReporter.config().setDocumentTitle("This is a referral Test Automation Report");
        extSparkReporter.config().setReportName("Automation suite test");
        extSparkReporter.config().setTheme(Theme.DARK);
        extReports.setSystemInfo("Browser","Chrome");
        extReports.setSystemInfo("OS Name","Windows");
        extReports.attachReporter(extSparkReporter);
    }

    public static void createTest(String testName,String className, String author)
    {
        extTest= extReports
                .createTest("<b>" +testName+ "</b>")
                .assignCategory(className)
                .assignAuthor(author)
                .assignDevice("Chrome");
        test.set(extTest);
    }

    public static ExtentTest getTest()
    {
        return test.get();
    }

    public static ExtentReports  getReport()
    {

        return extReports;
    }
    public static void flushReport()
    {
        extReports.flush();
    }
    public static void createCustomTable(String totalTests, String passedTests, String failedTests, String skippedTests, String passPercentage) {
        String customTable = "<table style='width:100%; border: 2px solid Black; border-collapse: collapse;'>"
                + "<tr>"
                + "<th>Total Tests</th>"
                + "<th>Passed</th>"
                + "<th>Failed</th>"
                + "<th>Skipped</th>"
                + "<th>Passed%</th>"
                + "</tr>"
                + "<tr>"
                + "<td>" + totalTests + "</td>"
                + "<td>" + passedTests + "</td>"
                + "<td>" + failedTests + "</td>"
                + "<td>" + skippedTests + "</td>"
                + "<td>" + passPercentage + "</td>"
                + "</tr>"
                + "</table>";
        extReports.setSystemInfo("Test Summary", customTable);
    }
}
