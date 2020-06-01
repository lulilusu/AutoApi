package com.tester.api.utlis;

import jxl.read.biff.BiffException;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.Iterator;

public class DataProviders {

    @DataProvider(name = "setUpData")
    public Object[][] setData() throws IOException, BiffException {
        ExcelData excelData = new ExcelData("data");
        return excelData.getExcelData();
    }

    @DataProvider(name = "testData")
    public Object[][] getTestData() throws IOException, BiffException {
        ExcelData excelData = new ExcelData("testcase");
        return excelData.getExcelData();
    }
//
//    @DataProvider(name = "data")
//    public Iterator<Object> dp(){
//
//    }
}
