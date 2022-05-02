package com.thinking.my.excel;

import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2021/12/22 6:58 下午
 **/
public class ExcelUtil {

    /**
     * 根据流生成 excel 文件
     * @param inputStream
     * @return
     */
    public static XSSFWorkbook readExcelFromStream(InputStream inputStream){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            return workbook;
        } catch (IOException e) {
           e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取excel
     * @param workbook
     * @return
     */
    public static void getExcelSheetAndRowNum(XSSFWorkbook workbook){
        int sheetNum = workbook.getNumberOfSheets();
        int totalRows = 0;
        int totalCellNum = 0;
        for(int i = 0;i<sheetNum;i++){
            int rowNum = workbook.getSheetAt(i).getPhysicalNumberOfRows();
            totalRows = totalRows+rowNum;
        }
    }
    public static boolean addSheet(XSSFWorkbook wb, InputStream inputStream,int copyRow){
        XSSFWorkbook workbook = readExcelFromStream(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        addSheet(wb,sheet,copyRow);
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void addSheet(XSSFWorkbook wb,XSSFSheet sheet ,int copyRow){
        CellCopyPolicy cellCopyPolicy = new CellCopyPolicy();
        cellCopyPolicy.setCopyCellStyle(false);
        XSSFSheet toSheet = wb.createSheet("Sheet"+(wb.getNumberOfSheets()+1));
        copySheet(sheet,toSheet,copyRow);
        wb.setActiveSheet(wb.getSheetIndex(toSheet));
//        wb.createSheet().copyRows(getRows(sheet,copyRow),0,cellCopyPolicy);
    }

    public static List<? extends Row> getRows(XSSFSheet sheet, int copyRow){
        List<XSSFRow> xssfRows = new ArrayList<>();
        int i = 0;
        for (Iterator<Row> rowIt = sheet.rowIterator(); rowIt.hasNext();){
            xssfRows.add((XSSFRow)rowIt.next());
            i++;
            if(i==copyRow){
                break;
            }
        }
        return xssfRows;
    }

    /**
     * Sheet复制
     * @param fromSheet
     * @param toSheet
     */
    public static void copySheet( XSSFSheet fromSheet, XSSFSheet toSheet,int copyRow) {
//        mergeSheetAllRegion(fromSheet, toSheet);
        // 设置列宽
//        int length = fromSheet.getRow(fromSheet.getFirstRowNum()).getLastCellNum();
//        for (int i = 0; i <= length; i++) {
//            toSheet.setColumnWidth(i, fromSheet.getColumnWidth(i));
//        }
        CellCopyPolicy cellCopyPolicy = new CellCopyPolicy();
        //不同版本的 excel存在样式不兼容的问题
        cellCopyPolicy.setCopyCellStyle(false);
        int i = 0;
        XSSFRow oldRow;
        XSSFRow newRow;
        for (Iterator rowIt = fromSheet.rowIterator(); rowIt.hasNext();) {
            oldRow = (XSSFRow) rowIt.next();
            newRow = toSheet.createRow(oldRow.getRowNum());
//            newRow.copyRowFrom(oldRow,cellCopyPolicy);
            copyRow(oldRow,newRow,cellCopyPolicy);
            i++;
            if(i==copyRow){
                break;
            }
        }
    }

    /**
     * 合并单元格
     * @param fromSheet
     * @param toSheet
     */
    public static void mergeSheetAllRegion(XSSFSheet fromSheet, XSSFSheet toSheet) {
        int num = fromSheet.getNumMergedRegions();
        CellRangeAddress cellR = null;
        for (int i = 0; i < num; i++) {
            cellR = fromSheet.getMergedRegion(i);
            toSheet.addMergedRegion(cellR);
        }
    }

    /**
     * 行复制功能
     * @param oldRow
     * @param toRow
     */
    public static void copyRow( XSSFRow oldRow, XSSFRow toRow,CellCopyPolicy cellCopyPolicy) {
        toRow.setHeight(oldRow.getHeight());
        XSSFCell tmpCell;
        XSSFCell newCell;
        for (Iterator cellIt = oldRow.cellIterator(); cellIt.hasNext();) {
            tmpCell = (XSSFCell) cellIt.next();
            newCell = toRow.createCell(tmpCell.getColumnIndex());
            newCell.copyCellFrom(tmpCell,cellCopyPolicy);
        }
    }

}
