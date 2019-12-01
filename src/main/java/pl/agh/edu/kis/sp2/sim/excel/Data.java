package pl.agh.edu.kis.sp2.sim.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Data {
    private int NumberofRelationsData;
    private int NumberofActivityData;
    private int NumberofLocationData;
    private int NumberofWeatherData;
    private int NumberofTime;
    public Data() {

    }
    public Data(int NumberofRelationsData,int NumberofActivityData,int NumberofLocationData,int NumberofWeatherData,int NumberofTime)
    {
        this.NumberofRelationsData = NumberofRelationsData;
        this.NumberofActivityData = NumberofActivityData;
        this.NumberofLocationData = NumberofLocationData;
        this.NumberofWeatherData = NumberofWeatherData;
        this.NumberofTime = NumberofTime;
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
        cell.setCellValue("Number of Relations Data");

        cell = row.createCell(1);
        cell.setCellValue("Number of Activity Data");

        cell = row.createCell(2);
        cell.setCellValue("Number of Location Data");

        cell = row.createCell(3);
        cell.setCellValue("Number of Weather Data");

        cell = row.createCell(4);
        cell.setCellValue("Number of Time");
    }
    private void writeData(Data aData, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(this.NumberofRelationsData);

        cell = row.createCell(1);
        cell.setCellValue(this.NumberofActivityData);

        cell = row.createCell(2);
        cell.setCellValue(this.NumberofLocationData);

        cell = row.createCell(3);
        cell.setCellValue(this.NumberofWeatherData);

        cell = row.createCell(4);
        cell.setCellValue(this.NumberofTime);
    }
    public List<Data> getListData() {
        Data data = this;
        List<Data> listData = Arrays.asList(data);
        return listData;
    }
}
