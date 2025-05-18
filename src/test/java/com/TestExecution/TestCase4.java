package com.TestExecution;


import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCase4 {

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