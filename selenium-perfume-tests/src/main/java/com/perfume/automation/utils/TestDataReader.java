package com.perfume.automation.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading test data from CSV and Excel files
 * Supports data-driven testing
 */
public class TestDataReader {

    /**
     * Read data from CSV file
     * Returns list of maps where each map represents a row
     * Map keys are column headers, values are cell values
     */
    public static List<Map<String, String>> readCSV(String filePath) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allData = csvReader.readAll();

            if (allData.isEmpty()) {
                System.err.println("CSV file is empty: " + filePath);
                return dataList;
            }

            // First row contains headers
            String[] headers = allData.get(0);

            // Process data rows
            for (int i = 1; i < allData.size(); i++) {
                String[] row = allData.get(i);
                Map<String, String> dataMap = new HashMap<>();

                for (int j = 0; j < headers.length; j++) {
                    if (j < row.length) {
                        dataMap.put(headers[j], row[j]);
                    } else {
                        dataMap.put(headers[j], "");
                    }
                }

                dataList.add(dataMap);
            }

        } catch (IOException | CsvException e) {
            System.err.println("Error reading CSV file: " + filePath);
            e.printStackTrace();
        }

        return dataList;
    }

    /**
     * Read data from Excel file
     * Returns list of maps where each map represents a row
     * Map keys are column headers, values are cell values
     */
    public static List<Map<String, String>> readExcel(String filePath, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                System.err.println("Sheet not found: " + sheetName);
                return dataList;
            }

            // Get header row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                System.err.println("Header row is empty in sheet: " + sheetName);
                return dataList;
            }

            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell));
            }

            // Process data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> dataMap = new HashMap<>();

                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = (cell != null) ? getCellValueAsString(cell) : "";
                    dataMap.put(headers.get(j), cellValue);
                }

                dataList.add(dataMap);
            }

        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + filePath);
            e.printStackTrace();
        }

        return dataList;
    }

    /**
     * Read data from Excel file (first sheet)
     */
    public static List<Map<String, String>> readExcel(String filePath) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            if (sheet == null) {
                System.err.println("No sheets found in Excel file: " + filePath);
                return dataList;
            }

            // Get header row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                System.err.println("Header row is empty");
                return dataList;
            }

            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell));
            }

            // Process data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> dataMap = new HashMap<>();

                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = (cell != null) ? getCellValueAsString(cell) : "";
                    dataMap.put(headers.get(j), cellValue);
                }

                dataList.add(dataMap);
            }

        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + filePath);
            e.printStackTrace();
        }

        return dataList;
    }

    /**
     * Convert cell value to string based on cell type
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Return as string without decimal for whole numbers
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            case BLANK:
                return "";

            default:
                return "";
        }
    }

    /**
     * Get specific column data from CSV
     */
    public static List<String> getColumnData(String filePath, String columnName) {
        List<String> columnData = new ArrayList<>();
        List<Map<String, String>> allData = readCSV(filePath);

        for (Map<String, String> row : allData) {
            if (row.containsKey(columnName)) {
                columnData.add(row.get(columnName));
            }
        }

        return columnData;
    }

    /**
     * Get specific row data from CSV by row index
     */
    public static Map<String, String> getRowData(String filePath, int rowIndex) {
        List<Map<String, String>> allData = readCSV(filePath);

        if (rowIndex >= 0 && rowIndex < allData.size()) {
            return allData.get(rowIndex);
        }

        return new HashMap<>();
    }

    /**
     * Get row count from CSV file
     */
    public static int getRowCount(String filePath) {
        return readCSV(filePath).size();
    }
}
