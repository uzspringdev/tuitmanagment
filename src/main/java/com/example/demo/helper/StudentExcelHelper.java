package com.example.demo.helper;

import com.example.demo.entity.Student;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class StudentExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = {"№", "Student", "Faculty", "Group", "Payment form"};
    static String SHEET = "Specialities";

    public static ByteArrayInputStream studentsToExcel(List<Student> students) {
        try {
            Workbook workbook = new HSSFWorkbook();
            Font font = workbook.createFont();
            font.setBold(true);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFont(font);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Sheet sheet = workbook.createSheet(SHEET);
            //COLUMN WIDTH
            sheet.setColumnWidth(0,6*255);
            sheet.setColumnWidth(1,30*255);
            sheet.setColumnWidth(2,25*255);
            sheet.setColumnWidth(3,10*255);
            sheet.setColumnWidth(4,15*255);
            //HEADER
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERS.length; col++) {

                Cell cell = headerRow.createCell(col);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(HEADERS[col]);
            }
            int rowIdx = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(rowIdx-1);
                row.createCell(1).setCellValue(student.getPassport().getLastName() + " " + student.getPassport().getFirstName() + " " + student.getPassport().getPatronymic());
                row.createCell(2).setCellValue(student.getFaculty().getName());
                row.createCell(3).setCellValue(student.getGroupName());
                row.createCell(4).setCellValue(student.getPaymentForm());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
