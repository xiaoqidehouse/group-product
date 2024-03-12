package com.ruoyi.config;


import com.ruoyi.domain.SysUser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;

public class ExcelConfig {
    public static void exportToExcel(List<SysUser> dataList) {
        int rowIndex = 0;
        int sheetIndex = 1;
        // 创建工作簿
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        // 获取第一个sheet页
        Sheet sheet = workbook.createSheet("Sheet"+sheetIndex);


        // 写入表头
        Row headerRow = sheet.createRow(0);

        headerRow.createCell(1).setCellValue("列2");
        headerRow.createCell(2).setCellValue("列3");
        // 写入数据

        for (SysUser data : dataList) {
            if (rowIndex >= 1048575) {
                sheetIndex++;
                sheet = workbook.createSheet("Sheet" + sheetIndex);
                rowIndex = 0;

            }
                Row row = sheet.createRow(rowIndex++);
                row.createCell(1).setCellValue(data.getUserName());
                row.createCell(2).setCellValue(data.getNickName());

        }

        // 将数据写入文件
        try (FileOutputStream outputStream = new FileOutputStream("output.xlsx")) {
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 关闭工作簿
        workbook.dispose();
    }
}
