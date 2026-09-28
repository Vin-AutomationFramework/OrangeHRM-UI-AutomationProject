package com.automation.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelReader {
	
	public static Object[][] getTestData(String filePath, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in " + filePath);
            }
            Row headerRow = sheet.getRow(0);
            int totalRows = sheet.getPhysicalNumberOfRows();
            int totalCols = headerRow.getPhysicalNumberOfCells();

            for (int i = 1; i < totalRows; i++) {
                Row currentRow = sheet.getRow(i);
                if (currentRow == null) continue;
                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < totalCols; j++) {
                    String header = headerRow.getCell(j).getStringCellValue().trim();
                    Cell cell = currentRow.getCell(j);
                    String value = (cell == null) ? "" : cell.toString().trim();
                    rowData.put(header, value);
                }
                dataList.add(rowData);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel data from: " + filePath, e);
        }

        Object[][] data = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i);
        }
        return data;
    }

    // New helper method for Iterator<Object[]> data providers
    public static Iterator<Object[]> getTestDataAsIterator(String filePath, String sheetName) {
        Object[][] data = getTestData(filePath, sheetName);
        List<Object[]> list = new ArrayList<>();
        for (Object[] row : data) {
            list.add(row);
        }
        return list.iterator();
    }

}
