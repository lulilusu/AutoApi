package com.tester.api.listeners;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.qameta.allure.Attachment;

import java.util.HashMap;

public class ReportListener {

    public static void create(String description, String method, String url, HashMap<String, String> requestBody, int statusCode, String Respond) {
        requestBody(description, method, url, requestBody);
        respondBody(statusCode,Respond);
    }

    @Attachment("用例详情")
    public static String requestBody(String description, String method, String url, HashMap<String, String> requestBody) {
        String body = JSON.toJSONString(requestBody, true);
        return "用例描述: " + description + "\n"
                + "请求方式: " + method + "\n"
                + "URL: " + url + "\n"
                + "请求参数: " + "\n" + body;
    }

    @Attachment("响应详情")
    public static String respondBody(int statusCode,String responed) {
        JSONObject obj = JSONObject.parseObject(responed);
        String resp = JSON.toJSONString(obj, true);
        return "状态码: " + statusCode + "\n"
                + "响应正文: " + "\n" +resp;
    }

    @Attachment("断言结果")
    public static StringBuffer assertResult(StringBuffer assertResult) {
        return assertResult;
    }
}
