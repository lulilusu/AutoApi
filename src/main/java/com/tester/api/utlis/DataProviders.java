package com.tester.api.utlis;

import com.tester.api.beans.CaseBean;
import jxl.read.biff.BiffException;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DataProviders {

    @DataProvider(name = "Data")
    public Object[][] getTestData() throws IOException, BiffException {
        ExcelData excelData = new ExcelData("testcase");
        return excelData.getExcelData();
    }

    @DataProvider(name = "testData")
    public Iterator<Object[]> data() throws SQLException {
        List<Object[]> dataProvider = new ArrayList<>();
        List<CaseBean> apicase = DBData.getData("apicase");
        for (CaseBean data : apicase) {
            if (data.isRun()){
                dataProvider.add(new Object[] { data });
            }
        }
        return dataProvider.iterator();
    }

}
