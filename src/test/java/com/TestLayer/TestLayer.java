package com.TestLayer;

import org.PageLayer.loginPage;
import org.BaseTestLayer.BaseTestClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

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
        WebDriverWait ww = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert until = ww.until(ExpectedConditions.alertIsPresent());
        until.getText();
       WebElement ee= ww.until(ExpectedConditions.elementToBeClickable(By.xpath("")));
        Assert.assertEquals(actualTitle,"Products");
    }
}
