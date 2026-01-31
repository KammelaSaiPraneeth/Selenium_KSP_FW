package com.TestLayer;

import PageLayer.loginPage;
import org.BaseTestLayer.BaseTestClass;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestLayer extends BaseTestClass {

    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
                {"standard_user", "secret_sauce"},
                {"visual_user", "secret_sauce"},
                {"abc","poi"}
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String userName, String password) throws InterruptedException {
        loginPage lp= new loginPage();
        String actualTitle=lp.loginFunc(userName,password);
        System.out.println(" This is the title of page "+actualTitle);
        Assert.assertEquals(actualTitle,"Products");
    }
}
