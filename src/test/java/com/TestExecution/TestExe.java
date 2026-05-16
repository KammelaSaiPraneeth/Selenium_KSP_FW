package com.TestExecution;

import org.BaseTestLayer.BaseTestClass;
import org.testng.annotations.Test;


public class TestExe extends BaseTestClass {
    @Test
    public void testcase() throws InterruptedException
    {
        System.out.println("Test case has been launched");
    }
}
