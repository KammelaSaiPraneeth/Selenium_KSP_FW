package org.completeUtilities;

import org.BaseTestLayer.BaseTestClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;

public class utilitiesClass extends BaseTestClass {
    public static String screenShotFolderName="";
    private static WebDriver driver;

    public utilitiesClass()
    {
        this.driver=super.driver;
    }

    public static void captureScreenShot(String fileName)
    {
        System.out.println(" Working is successful for failed screen shots");
        if(screenShotFolderName==null) {
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            String screenShotFolderName = myDateObj.format(myFormatObj);
        }
        TakesScreenshot takesScreenshot= (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("./Screenshots/"+screenShotFolderName+fileName);
        try
        {
            FileUtils.copyFile(sourceFile, destFile);
        } catch (IOException e) {
            {
                e.printStackTrace();
            }
        }
        System.out.println("Screenshot saved successfully");
    }
}
