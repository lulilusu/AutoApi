package com.tester.api.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class ParseData {


    /**
     * 用例参数转为Map
     * @param params
     * @return
     */
    public static HashMap<String, String> getMap(String params) {
        HashMap<String, String> map = new HashMap<>();
        String[] buf = params.split("\n");
        for (String line : buf) {
            if (line.contains("=")) {
                map.put(line.split("=")[0], line.split("=")[1]);
            }
        }
        return map;
    }

    /**
     * 获取json值 写法： data[0]/name ;  message
     * @param responseJson
     * @param jpath
     * @return
     */
    public static String getValueByJPath(JSONObject responseJson, String jpath){
        Object obj = responseJson;
        for(String s : jpath.split("/")) {
            if(!s.isEmpty()) {
                if(!(s.contains("[") || s.contains("]"))) {
                    obj = ((JSONObject) obj).get(s);
                }else if(s.contains("[") || s.contains("]")) {
                    obj =((JSONArray)((JSONObject)obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replaceAll("]", "")));
                }
            }
        }
        return obj.toString();
    }
//
//    public static void main(String[] args) {
//        String str ="{\"data\":[{\"id\":\"4028815d6dc81154016dc8e597a10000\",\"createTime\":\"2019-10-22 11:21:34\",\"modifyTime\":\"2019-10-29 14:59:09\",\"valid\":1,\"regionId\":\"100000\",\"regionName\":\"北海分站\",\"name\":\"测试\",\"phone\":1888888888,\"loginAccount\":\"beihai\",\"middleUrl\":\"http://work.360humi.com/login.html\",\"receptionUrl\":\"https://www.360humi.com/\"},{\"id\":\"4028815d6dc81154016dc8e597a10001\",\"createTime\":\"2019-10-23 11:21:34\",\"modifyTime\":\"2019-10-29 14:52:32\",\"valid\":1,\"regionId\":\"100001\",\"regionName\":\"重庆分站\",\"name\":\"测试\",\"phone\":1888888888,\"loginAccount\":\"chongqing\",\"middleUrl\":null,\"receptionUrl\":null}],\"message\":\"交易成功\",\"status\":\"0000\",\"error\":false,\"hasErrors\":false}";
//        JSONObject object = JSONObject.parseObject(str);
//        String valueByJPath = getValueByJPath(object, "data[1]/regionName");
//        System.out.println(valueByJPath);
//    }
}
