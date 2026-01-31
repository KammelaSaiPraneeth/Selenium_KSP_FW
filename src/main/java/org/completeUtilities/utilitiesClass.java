package org.completeUtilities;

import org.BaseTestLayer.BaseTestClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;

public class utilitiesClass extends BaseTestClass {

    public static String screenShotFolderName="";
    public static String captureScreenShot(String fileName)
    {
        if(screenShotFolderName==null) {
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            screenShotFolderName = myDateObj.format(myFormatObj);
            System.out.println(" screenShotFolderName " +screenShotFolderName);
        }
        TakesScreenshot takesScreenshot= (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("/Screenshots/"+screenShotFolderName+fileName);
        System.out.println(" dest file " +destFile);
        try
        {
            FileUtils.copyFile(sourceFile, destFile);
        } catch (IOException e) {

            {
                e.printStackTrace();
            }
        }

        return destFile.getAbsolutePath();
    }
}
