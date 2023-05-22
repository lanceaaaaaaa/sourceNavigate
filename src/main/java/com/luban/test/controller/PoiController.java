package com.luban.test.controller;

import com.alibaba.excel.EasyExcel;
import com.luban.test.model.UserInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoiController {



    private static List<UserInfo> getList() {

        List<UserInfo> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0; i<65535; i++){
            UserInfo userInfo = new UserInfo();
            userInfo.setId(i+1);
            userInfo.setNumber(i+10);
            userInfo.setName("name"+i);
            userInfo.setCreate_time(format.format(new Date()));
            userInfo.setUpdate_time(format.format(new Date()));
            list.add(userInfo);
        }
        return list;
    }
    /*
    HSSFWorkbook导出
     */
    @Test
    public void hssWorkbook() throws IOException {

        long before = System.currentTimeMillis();
        List<UserInfo> list = getList();

        String fileName  = "D:/h-用户.xls";
        Workbook workbook=new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("报表");

        for(int i=0; i<list.size(); i++){
            UserInfo userInfo = list.get(i);
            Row row = sheet.createRow(i+1);
            row.createCell(1).setCellValue(userInfo.getId());
            row.createCell(2).setCellValue(userInfo.getNumber());
            row.createCell(3).setCellValue(userInfo.getName());
            row.createCell(4).setCellValue(userInfo.getCreate_time());
            row.createCell(5).setCellValue(userInfo.getUpdate_time());
        }
        FileOutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        //关闭流
        outputStream.close();
        long later = System.currentTimeMillis();
        System.out.println(later - before);
    }

    /*
    XssfWorkbook导出
     */
    @Test
    public void xssfWorkbooktest() throws Exception {
        long before = System.currentTimeMillis();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("sheet名称");
        String fileName  = "D:/x-用户.xlsx";

        List<UserInfo> list = getList();
        for(int i=0; i<list.size(); i++){
            UserInfo userInfo = list.get(i);
            XSSFRow xssfRow = xssfSheet.createRow(i+1);
            xssfRow.createCell(1).setCellValue(userInfo.getId());
            xssfRow.createCell(2).setCellValue(userInfo.getNumber());
            xssfRow.createCell(3).setCellValue(userInfo.getName());
            xssfRow.createCell(4).setCellValue(userInfo.getCreate_time());
            xssfRow.createCell(5).setCellValue(userInfo.getUpdate_time());
        }


        FileOutputStream outputStream = new FileOutputStream(fileName);

        xssfWorkbook.write(outputStream);
        long later = System.currentTimeMillis();
        System.out.println(later - before);
    }

    /*
    SXSSFWorkbook导出
     */
    @Test
    public void sXSSFWorkbookTest() throws Exception {
        long before = System.currentTimeMillis();
        List<UserInfo> list = getList();
        String fileName  = "D:/s-用户.xlsx";
        //内存中保留 100 条数据，以免内存溢出，其余写入硬盘
        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        workbook.setCompressTempFiles(true);
        //获得该工作区的第一个sheet
        Sheet sheet = workbook.createSheet("Sheet0");
        FileOutputStream outStream = new FileOutputStream(fileName);
        try {
            for(int i=0; i<list.size(); i++){
                Row row = sheet.createRow(i+1);
                UserInfo userInfo = list.get(i);
                row.createCell(1).setCellValue(userInfo.getId());
                row.createCell(2).setCellValue(userInfo.getNumber());
                row.createCell(3).setCellValue(userInfo.getName());
                row.createCell(4).setCellValue(userInfo.getCreate_time());
                row.createCell(5).setCellValue(userInfo.getUpdate_time());
            }
            workbook.write(outStream);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            workbook.dispose();
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long later = System.currentTimeMillis();
        System.out.println(later - before);
    }

    /*
    EasyExcel导出
     */
    @Test
    public void easyExcelTest(){
        long before = System.currentTimeMillis();
        File file = new File("D:/用户表.xlsx");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        EasyExcel.write(file, UserInfo.class).sheet("用户").doWrite(getList());
        long later = System.currentTimeMillis();
        System.out.println(later - before);
    }


}
