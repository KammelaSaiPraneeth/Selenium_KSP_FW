package org.commonUtilities;

import org.BaseTestLayer.BaseTestClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class utilitiesClass extends BaseTestClass {

    public static String screenShotFolderName="";


    public String readAndParseFile(String filePath,String fileName)
    {
        String file=filePath+"/"+fileName;
        String jsonContent;
        try {
            jsonContent = new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonContent;
    }

    public String readFileContent(String filePathName,String fileName){
        //Read File Contents
        String line, fileContent = "";
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePathName+"/"+fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                fileContent+=line;
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;

    }
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


    public List<Map<String, String>> columnDataExtract(String excelFilePath, String excelFileName) {
        //A Utility method to retrieve data from excel that has testcases and test data in the columns,Col1 has TestCaseName, Flag, Data etc
        List<Map<String, String>> dataList= new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(fis);
            // Get the first sheet,  we can get the excel with name of the sheet also using below syntax that is commented
            //Sheet sheet1 =  workbook.getSheet("Name of the excel Sheet");
            Sheet sheet = workbook.getSheetAt(0);
            //Here we are targetting to get the complete first row.
            Row firsrRowData= sheet.getRow(0);
            //Now we are iterating through that row, as we have test cases after first column,
            // we are considering the iteration from 1
            //getLastCellNum in the iteration gets us the las column number of the excel.
            for (int i = 1; i <firsrRowData.getLastCellNum();i++)
            {
                Map<String, String> mp=new HashMap<>();
                String KeyValue="";
                String KeyValueData="";
                //Here we are geting the total number of rows in that excel.
                //getPhysicalNumberOfRows this method will give us the physically present rows in this excel sheet
                for(int j=0; j<sheet.getPhysicalNumberOfRows();j++)
                {
                    Row roi= sheet.getRow(j);
                    Cell ccpoi= roi.getCell(i);
                    KeyValueData=ccpoi.getStringCellValue();
                    Row roman= sheet.getRow(j);
                    Cell cellman= roman.getCell(0);
                    KeyValue=cellman.getStringCellValue();
                    mp.put(KeyValue,KeyValueData);
                }
                dataList.add(mp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public List<Map<String, String>> rowDataExtract(String excelFilePath, String excelFileName)
    {
        //A Utility method to retrieve data from excel that has testcases and test data in the rows,Row1 has TestCaseName, Flag, Data etc
        List<Map<String, String>> dataList = new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            Row rowdata = sheet.getRow(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++)
            {
                Row valueRow=sheet.getRow(i);
                Map<String, String> dataMap = new HashMap<>();
                for (int j = 0; j < rowdata.getLastCellNum(); j++) {
                    Cell keyvalue=rowdata.getCell(j);
                    Cell valueCell=valueRow.getCell(j);
                    String key = keyvalue.getStringCellValue();
                    String value = valueCell.getStringCellValue();
                    dataMap.put(key, value);
                }
                dataList.add(dataMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

}
