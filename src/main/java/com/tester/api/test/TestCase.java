package com.tester.api.test;


import com.alibaba.fastjson.JSONObject;
import com.tester.api.beans.ResponseBean;
import com.tester.api.http.HttpUtil;
import com.tester.api.listeners.ReportListener;
import com.tester.api.utlis.DataProviders;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static com.tester.api.utlis.Asserts.asserts;

public class TestCase {

    public static String access_token;
    public static String user_token;


    @Test(dataProvider = "testData", dataProviderClass = DataProviders.class)
    public void autoApi(HashMap<String, String> data) throws IOException, URISyntaxException {
        HashMap<String, String> params = ParseData.getMap(data.get("params"));
        ResponseBean response =  new HttpUtil().getResponse(data.get("url"), data.get("method"), params, data.get("token"), data.get("formData"));
        JSONObject obj = JSONObject.parseObject(response.getResponse());
        saveToken(obj);

        ReportListener.create(data.get("description"),data.get("method"),data.get("url"), params,response.getStatusCode(),response.getResponse());

        asserts(response.getResponse(),data.get("expected"),response.getStatusCode());
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
