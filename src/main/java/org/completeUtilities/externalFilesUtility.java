package org.completeUtilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class externalFilesUtility {

    public void mapTrailCall() {
        //This is a utilitymethod that is used to fetch the data from the Excel sheet when the test cases and test
        // data is in colums.
        String excelFilePath = "C:\\Users\\kspraneeth\\Desktop\\Important\\main code\\src\\test\\resources\\DataT.xlsx";
        List<Map<String, String>> Headers= new ArrayList<>();
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
                Headers.add(mp);
            }
        } catch (IOException e) {
            e.printStackTrace();       }
        for (Map<String, String> DM : Headers) {
            System.out.println(DM);
        }
    }

    public void mapRowCol()
    {
        //This is a utilitymethod that is used to fetch the data from the Excel sheet when the test cases and test data is in rows
        String excelFilePath = "C:\\Users\\kspraneeth\\IdeaProjects\\TestTrials\\src\\main\\resources\\Data.xlsx";
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
        for (Map<String, String> DM : dataList) {
            System.out.println(DM);
        }
    }
    public  static  void main(String[] args)
    {
        externalFilesUtility ee= new externalFilesUtility();
        ee.mapTrailCall();
    }
}
