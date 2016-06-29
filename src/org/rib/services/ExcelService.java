package org.rib.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.rib.domain.TaskDomain;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Andrey Ribachenko
 * @version 1.0
 * @since 29.06.2016
 */
public class ExcelService {

    public void writeToFile(File file, List<TaskDomain> taskList) {
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Task list");

        int rowNum = 0;
        Cell cell;
        for (TaskDomain task : taskList) {
            Row row = sheet.createRow(rowNum);
            if (rowNum  == 0) {
                cell = row.createCell(0);
                cell.setCellValue("Process PID");
                cell = row.createCell(1);
                cell.setCellValue("Process name");
                cell = row.createCell(2);
                cell.setCellValue("Memory usage");
            } else {
                cell = row.createCell(0);
                cell.setCellValue(task.getPid() == null ? "-" : task.getPid().toString());
                cell = row.createCell(1);
                cell.setCellValue(task.getName());
                cell = row.createCell(2);
                cell.setCellValue(task.getMemoryUsage());
            }
            rowNum++;
        }

        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            System.out.println("Excel file written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
