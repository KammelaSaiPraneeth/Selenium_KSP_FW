package com.TestExecution;
import org.BaseTestLayer.BaseTestClass;
import org.TestNGUtilities.ExtentReportManager;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCase1 extends BaseTestClass {


    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
                {"user1", "password1"},
                {"user2", "password2"},
                {"user3", "password3"}
        };
    }

    @Test(dataProvider = "loginData")
    public void testPassOne(String username, String password) {
        System.out.println("Testing login with: " + username + " / " + password);
        ExtentReportManager.logStep("This is step 1");
      //  System.out.println("This is step 2");
        ExtentReportManager.logStep("Test step 2");
       // System.out.println("This is step 3");
        ExtentReportManager.logStep("Test step 3");
        System.out.println("This is step 4");
        ExtentReportManager.logStep("Test step 4");
        System.out.println("This is step 5");
        ExtentReportManager.logStep("Test step 5");
        System.out.println("This is step 6");
        ExtentReportManager.logStep("Test step 6");
        if(username.contains("user2"))
        {
            Assert.fail();
        }
    }

    @Test
    public void testPassTwo() {
        int expected = 5;
        int actual = 2 + 3;
        Assert.assertEquals(actual, expected, "Addition result should match");
    }

    @Test
    public void testFailOne() {
        System.out.println("This is a failed test case that should run multiple times");

        Assert.assertTrue(false, "This test is intentionally failing");
    }

    @Test
    public void testFailTwo() {
        int expected = 10;
        int actual = 5 * 2;
        Assert.assertEquals(actual, 11, "This assertion will fail");
    }

    @Test
    public void testPassThree() {
        String str = "TestNG";
        Assert.assertNotNull(str, "String should not be null");
    }

    @Test
    public void testSkipped() {
        throw new SkipException("This test is intentionally skipped");
    }
}
