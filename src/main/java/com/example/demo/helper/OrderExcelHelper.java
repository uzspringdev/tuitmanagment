package com.example.demo.helper;

import com.example.demo.entity.Order;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class OrderExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = {"№", "Order number", "Order Name", "Type of Order", "Order Date"};
    static String SHEET = "Orders";

    public static ByteArrayInputStream ordersToExcel(List<Order> orders) {
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
            for (Order order : orders) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(rowIdx-1);
                row.createCell(1).setCellValue(order.getOrderNumber());
                row.createCell(2).setCellValue(order.getName());
                row.createCell(3).setCellValue(order.getTypeOfOrder());
                row.createCell(4).setCellValue(order.getOrderDate().toString());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
