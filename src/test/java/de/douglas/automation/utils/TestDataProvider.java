package de.douglas.automation.utils;

import org.testng.annotations.DataProvider;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TestDataProvider {

    private static Workbook getWorkbook() throws IOException {
        try (FileInputStream fis = new FileInputStream("src/test/resources/filter-data.xlsx")) {
            return WorkbookFactory.create(fis);
        }
    }

    @DataProvider(name = "filterData")
    public static Object[][] getFilterData() throws IOException {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][colCount];

        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                if (row.getCell(j) != null) {
                    data[i - 1][j] = row.getCell(j).toString();
                } else {
                    data[i - 1][j] = "";
                }
            }
        }
        return data;
    }

    public static String[] getCriteria() throws IOException {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);

        Row headerRow = sheet.getRow(0);
        String[] criteria = new String[headerRow.getLastCellNum()];

        for (int j = 0; j < criteria.length; j++) {
            criteria[j] = headerRow.getCell(j).toString();
        }
        return criteria;
    }

    public static Map<String, String> getExpectedUrlSegments() throws IOException {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(1);

        Map<String, String> urlSegments = new HashMap<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                String criteria = row.getCell(0).toString();
                String expectedUrlSegment = row.getCell(1).toString();
                urlSegments.put(criteria, expectedUrlSegment);
            }
        }
        return urlSegments;
    }


    //Other data provider formats..

    @DataProvider(name = "filterDataFromHighlight")
    public static Object[][] getFilterDataForHighlight() throws IOException {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getLastRowNum();
        Object[][] data = new Object[rowCount][1];

        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            data[i - 1][0] = row.getCell(0).toString();
        }
        return data;
    }

    public static String[] getFilterOptions() throws IOException {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();

        String[] filterOptions = new String[rowCount];
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            filterOptions[i - 1] = row.getCell(0).toString();
        }
        return filterOptions;
    }
}



