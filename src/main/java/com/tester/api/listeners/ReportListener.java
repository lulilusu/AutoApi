package com.tester.api.listeners;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.qameta.allure.Attachment;
import org.apache.http.Header;

import java.util.HashMap;

public class ReportListener {

    public static void create(String description, String method, String url, HashMap<String, String> requestBody, int statusCode, String Respond,Header[] allHeaders) {
        requestBody(description, method, url, requestBody);
        respondBody(statusCode,Respond,allHeaders);
    }

    @Attachment("用例详情")
    public static String requestBody(String description, String method, String url, HashMap<String, String> requestBody) {
        String body = JSON.toJSONString(requestBody, true);

        return "Desc: " + description + "\n"
                + "Metod: " + method + "\n"
                + "URL: " + url + "\n"
                + "Params: " + "\n" + body;
    }

    @Attachment("响应详情")
    public static String respondBody(int statusCode, String responed, Header[] allHeaders) {
        JSONObject obj = JSONObject.parseObject(responed);
        String resp = JSON.toJSONString(obj, true);

        StringBuffer sb = new StringBuffer();
        for (Header header :allHeaders){
            sb.append(header.getName()).append(":").append(header.getValue()).append("\n");
        }

        return  sb.toString()
                +"status: " + statusCode + "\n"
                + "body: " + "\n" +resp;
    }

    @Attachment("响应断言")
    public static StringBuffer assertResult(StringBuffer assertResult) {
        return assertResult;
    }

    @Attachment("数据库校验")
    public static StringBuffer dbAssert(StringBuffer dbAssert){
        return dbAssert;
    }
}
