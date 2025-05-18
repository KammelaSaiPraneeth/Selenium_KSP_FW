package org.TestNGUtilities;

import com.aventstack.extentreports.Status;
import org.BaseTestLayer.BaseTestClass;
import org.testng.IRetryAnalyzer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.completeUtilities.utilitiesClass;
import java.util.HashSet;
import java.util.Set;


public class listenerClassUtility extends BaseTestClass implements ITestListener,IRetryAnalyzer
{
 public static ExtentSparkReporter sparkReporter;
 public static ExtentReports extReports;
 public ExtentTest extTest;
 private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

 private static Set<String> executedClasses = new HashSet<>();
 @Override
 public void onTestStart(ITestResult result) {
  String className=result.getTestClass().getName().substring(result.getTestClass().getName().lastIndexOf(".")+1);
  String methodName=result.getMethod().getMethodName();
  logger.info("Test has been started"+className+"==> Method Name"+methodName);
  String[] groupName=result.getTestContext().getIncludedGroups();
  String xmlName=result.getTestContext().getName();
  ExtentReportManager.createTest(className+"."+methodName,className,"Sai Praneeth");
  ExtentReportManager.getTest().log(Status.INFO,"Test case is started:"+className+"  ==>  "+methodName);
 }

 public void onTestSuccess(ITestResult result)
 {
  String className=result.getTestClass().getName().substring(result.getTestClass().getName().lastIndexOf(".")+1);
  String methodName=result.getMethod().getMethodName();
  logger.info("Test case has Passed Successfully:" +className+ " => " +methodName);
  ExtentReportManager.getTest().log(Status.PASS,"Test is Successful:"+className+"  ==>  "+methodName);

 }

 public void onTestFailure(ITestResult result) {
  String className = result.getTestClass().getName().substring(result.getTestClass().getName().lastIndexOf(".") + 1);
  String methodName = result.getMethod().getMethodName();
  logger.info("Test case has failed:" +className+ " => " +methodName);
  ExtentReportManager.getTest().log(Status.FAIL,"Test is Failed:"+className+"  ==>  "+methodName);
  ExtentReportManager.getTest().log(Status.FAIL,result.getThrowable());
  utilitiesClass.captureScreenShot(result.getTestContext().getName()+".jpg");
 }

 public void onTestSkipped( ITestResult result)
 {
  String className = result.getTestClass().getName().substring(result.getTestClass().getName().lastIndexOf(".") + 1);
  String methodName = result.getMethod().getMethodName();
  logger.info("Test case has skipped:" +className+ " => " +methodName);
  ExtentReportManager.getTest().log(Status.SKIP,"Test is Failed:"+className+"  ==>  "+methodName);
  ExtentReportManager.getTest().log(Status.SKIP,result.getThrowable());
 }
 int count=0;
 int retryLimit=2;
 @Override
 public boolean retry(ITestResult iTestResult)
 {
  if(count<retryLimit)
  {
   count++;
   return  true;
  }
  return false;
 }



}