package com.tester.api.utlis;


import java.util.*;

public class Remove {

    /**
     * 方式一：运算次数较多
     *
     * @param paramMap
     * @return
     */
    public static Map<String, String> removeMapEmptyValue1(Map<String, String> paramMap) {
        Set<String> set = paramMap.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            String str = it.next();
            if (paramMap.get(str) == null || paramMap.get(str) == ""){
                paramMap.remove(str);
                set = paramMap.keySet();
                it = set.iterator();
            }
        }
        return paramMap;
    }

    /**
     * 方式二：
     *
     * @param paramMap
     * @return
     */
    public static Map<String, String> removeMapEmptyValue(Map<String, String> paramMap) {
        Set<String> set = paramMap.keySet();
        Iterator<String> it = set.iterator();
        List<String> listKey = new ArrayList<String>();
        while (it.hasNext()) {
            String str = it.next();
            if (paramMap.get(str) == null || "".equals(paramMap.get(str))) {
                listKey.add(str);
            }
        }
        for (String key : listKey) {
            paramMap.remove(key);
        }
        return paramMap;
    }
}
