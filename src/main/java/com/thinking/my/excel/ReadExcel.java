package com.thinking.my.excel;

import sun.java2d.pipe.SpanIterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description
 * @Author liyong
 * @Date 2021/12/22 5:54 下午
 **/
public class ReadExcel {

    public static void main(String[] args) throws IOException {
        String inputFile = "/Users/liyong/Desktop/SQL模本查询.xlsx";
        String outputFile = "/Users/liyong/Desktop/ly_key.cvs";
        InputStream is = new FileInputStream(inputFile);

        XSSFWorkbook workbook = ExcelUtil.readExcelFromStream(is);

        int sheetsNum = workbook.getNumberOfSheets();
        XSSFSheet sheet =  workbook.getSheetAt(1);
       Row row =  sheet.getRow(0);

       int rowNum = row.getPhysicalNumberOfCells();
       for(int i=0;i<rowNum;i++){
           System.out.println(row.getCell(i).getStringCellValue());
       }
       Row row2 =  sheet.getRow(1);
       int row2Num = row2.getPhysicalNumberOfCells();
        for(int i=0;i<rowNum;i++){
            System.out.println(row2.getCell(i).getStringCellValue());
        }
        System.out.println(rowNum+"_"+row2Num);


    }
}
