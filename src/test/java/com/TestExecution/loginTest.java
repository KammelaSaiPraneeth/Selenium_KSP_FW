package com.TestExecution;

import org.BaseTestLayer.BaseTestClass;
import org.PageLayer.loginPage;
import org.testng.annotations.Test;


public class loginTest extends BaseTestClass {
    public  loginPage login;

    @Test
    public void loginValidation() throws InterruptedException {
        login = new loginPage();
        String  actualLoginTitle=  login.loginFunc("standard_user","password");

    }

}