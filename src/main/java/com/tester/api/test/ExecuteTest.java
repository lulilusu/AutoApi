package com.tester.api.test;


import com.alibaba.fastjson.JSONObject;
import com.tester.api.beans.CaseBean;
import com.tester.api.beans.ResponseBean;
import com.tester.api.http.HttpUtil;
import com.tester.api.listeners.ReportListener;
import com.tester.api.utlis.DataProviders;
import com.tester.api.utlis.ParseParam;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static com.tester.api.utlis.Asserts.asserts;

public class ExecuteTest {

    public static String access_token;
    public static String user_token;


//    @Test(dataProvider = "Data", dataProviderClass = DataProviders.class)
//    public void autoExcel(HashMap<String, String> data) throws IOException, URISyntaxException {
//        HashMap<String, String> params = ParseParam.getExcelMap(data.get("params"));
//        ResponseBean response =  new HttpUtil().getResponse(data.get("url"), data.get("method"), params, data.get("token"), data.get("formData"));
//        JSONObject obj = JSONObject.parseObject(response.getResponse());
//        saveToken(obj);
//
//        ReportListener.create(data.get("description"),data.get("method"),data.get("url"), params,response.getStatusCode(),response.getResponse());
//
//        asserts(response.getResponse(),data.get("expected"),response.getStatusCode());
//        System.out.println(response.getResponse());
//
//    }


    @Test(dataProvider = "testData", dataProviderClass = DataProviders.class)
    public void autoApi(CaseBean cb) throws IOException, URISyntaxException {
        // 请求参数转为map
        HashMap<String, String> params = ParseParam.getDBMap(cb.getParams());
        // 发送请求
        ResponseBean response = new HttpUtil().getResponse(cb.getUrl(), cb.getMethod(), params, cb.getToken(), cb.getFormData());
        // 保存token
        JSONObject obj = JSONObject.parseObject(response.getResponse());
        saveToken(obj);

        // 测试报告
        ReportListener.create(cb.getDesc(),cb.getMethod(),cb.getUrl(),params,response.getStatusCode(),response.getResponse(),response.getAllHeaders());
        // 断言
        asserts(response.getResponse(),cb.getExpected(),response.getStatusCode());
        System.out.println(response.getResponse());
    }

    public void saveToken(JSONObject obj){
        if (!obj.isEmpty()) {
            if (obj.containsKey("access_token")){
                access_token = obj.getString("access_token");
            }else if (obj.containsKey("user_token")){
                user_token = obj.getString("user_token");
            }
        }
    }
}
