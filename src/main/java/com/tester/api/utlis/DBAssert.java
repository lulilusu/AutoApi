package com.tester.api.utlis;


import com.alibaba.fastjson.JSONObject;
import com.tester.api.listeners.ReportListener;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class DBAssert {

    public static void verify(String sql, JSONObject json) throws SQLException {
       if (sql != null){
           Map<String, String> mapType = new HashMap<>();    // 参数类型
           Map<String, String> map = new HashMap<>();        // 参数键值
           StringBuffer sb = new StringBuffer();             // 保存断言结果


           ResultSet result = DBData.query(sql);
           ResultSetMetaData metaData = result.getMetaData();
           Boolean flag = false;
           String key = null;
           while (result.next()) {
               for (int i = 1; i <= metaData.getColumnCount(); i++) {
                   String[] split = metaData.getColumnName(i).split("_");
                   for (int j = 0; j < split.length; j ++){
                       String s = split[i-1].substring(1).toUpperCase();
                   }
                   key = StringUtils.join(split);
                   System.out.println(key);
                   mapType.put(key, metaData.getColumnTypeName(i)); // 保存数据库字段名和字段名数据类型

                   String columnName = metaData.getColumnName(i);     // 获取原始键
                   String vaule = result.getString(columnName);      // 通过键获取值
                   map.put(key, vaule);               //   保存sql返回值为map
               }
           }
           for (String dbKey : map.keySet()) {
               String str1="";
               String str2="";
               for (String jsonkey : json.keySet()) {
                   String jkey= jsonkey.toUpperCase(); // 将接口响应字段变为全小写
                   if (dbKey.equals(jkey)) {    //   当数据库字段与响应字段相等时进行 值判断
                       if (map.get(dbKey).equals(json.get(dbKey)) && map.get(dbKey) == json.get(dbKey)) {    // ==条件 会不明原因失败
                           str1= jsonkey +"【数据库：" + map.get(dbKey) + "," + "响应"+ json.get(dbKey) + "】pass! \n";
                           System.out.println("【数据库：" + map.get(dbKey) + "响应：" + json.get(dbKey) + "】pass");
                           flag = true;
                       } else {
                           str2= jsonkey +"【数据库：" + map.get(dbKey) + "," + "响应"+ json.get(dbKey) + "】fail! \n";
                           System.out.println("【数据库：" + map.get(dbKey) + "响应：" + json.get(dbKey) + "】fail");
                           flag = false;
                       }
                   }
               }
               sb.append(str1).append("\n").append(str2);  // 保存结果
               ReportListener.dbAssert(sb);                // 加入测试报告
           }

           Assert.assertTrue(flag,"断言失败");

       }else {
           System.out.println("该接口没有数据库校验");
       }
    }
}
