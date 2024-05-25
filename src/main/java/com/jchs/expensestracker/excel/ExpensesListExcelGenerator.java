package com.jchs.expensestracker.excel;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jchs.expensestracker.model.Expense;

public class ExpensesListExcelGenerator {

    public Workbook generate(List<Expense> expenses) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setFont(boldFont);
        
        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat((short)4);
        
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy"));
        
        Sheet sheet = workbook.createSheet();
        
        Row row = sheet.createRow(2);
        
        Cell cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("DATE");

        cell = row.createCell(1);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("TIN ID");

        cell = row.createCell(2);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("SUPPLIER");

        cell = row.createCell(3);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("ADDRESS");

        cell = row.createCell(4);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("AMOUNT");

        cell = row.createCell(5);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("EXPENSE TITLE");
        
        int rowNum = 4;
        
        for (Expense expense : expenses) {
        	row = sheet.createRow(rowNum);
        	
            cell = row.createCell(0);
            cell.setCellStyle(dateStyle);
            cell.setCellValue(expense.getDateOfTransaction());

            cell = row.createCell(1);
            cell.setCellValue(expense.getTin());

            cell = row.createCell(2);
            cell.setCellValue(expense.getSupplier());

            cell = row.createCell(3);
            cell.setCellValue(expense.getAddress());

            cell = row.createCell(4);
            cell.setCellStyle(numberStyle);
            cell.setCellValue(expense.getAmount().doubleValue());

            cell = row.createCell(5);
            cell.setCellValue(expense.getCategory2().getDescription());
        	
        	rowNum++;
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        
        return workbook;
    }
    
}
