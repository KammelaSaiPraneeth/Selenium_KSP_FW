package com.Docker;

import org.BaseTestLayer.BaseTestClass;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class Tests extends BaseTestClass {
    WebDriver driver= super.driver;

    @Test
    public void chromeTest1()
    {
        System.out.println("This is first chrome test");

    }

    @Test
    public void chromeTest2()
    {
        System.out.println("This is second chrome test");
    }

    @Test
    public void chromeTest3()
    {
        System.out.println("This is third chrome test");
    }

}
