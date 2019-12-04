package pl.agh.edu.kis.sp2.sim.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelWriter {
    private List<Data> dataList;
    private int rowCount=0;
    public ExcelWriter()
    {
        this.dataList = new ArrayList<>();
    }
    public void writeExcel(List<Data> listData, String excelFilePath) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        int rowCount = 0;
        for (Data aData : listData) {
            Row row1 = sheet.createRow(0);
            Row row2 = sheet.createRow(++rowCount);
            writeHeader(row1);
            writeData(aData, row2);
        }

        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }
    private void writeHeader(Row row)
    {
        Cell cell = row.createCell(0);
        cell.setCellValue("Iteration");

        cell = row.createCell(1);
        cell.setCellValue("Number of Relations Data");

        cell = row.createCell(2);
        cell.setCellValue("Number of Activity Data");

        cell = row.createCell(3);
        cell.setCellValue("Number of Location Data");

        cell = row.createCell(4);
        cell.setCellValue("Number of Weather Data");

        cell = row.createCell(5);
        cell.setCellValue("Number of Time");
    }
    private void writeData(Data aData, Row row) {
        this.rowCount++;
        Cell cell = row.createCell(0);
        cell.setCellValue(rowCount);
        cell = row.createCell(1);
        cell.setCellValue(aData.getNumberofRelationsData());

        cell = row.createCell(2);
        cell.setCellValue(aData.getNumberofActivityData());

        cell = row.createCell(3);
        cell.setCellValue(aData.getNumberofLocationData());

        cell = row.createCell(4);
        cell.setCellValue(aData.getNumberofWeatherData());

        cell = row.createCell(5);
        cell.setCellValue(aData.getNumberofTime());


    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }
    public void addToDataList(Data data)
    {
        this.dataList.add(data);
    }
}
