package com.example.demo.helper;

import com.example.demo.entity.Faculty;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class FacultyExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = {"№", "Faculty Name"};
    static String SHEET = "Faculties";

    public static ByteArrayInputStream facultiesToExcel(List<Faculty> faculties) {
        try {
            Workbook workbook = new HSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Sheet sheet = workbook.createSheet(SHEET);
            //HEADER
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }
            int rowIdx = 1;
            for (Faculty faculty : faculties) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(rowIdx-1);
                row.createCell(1).setCellValue(faculty.getName());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
