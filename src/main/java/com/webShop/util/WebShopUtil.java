package com.webShop.util;

import com.task1.subtask2.ArrayList;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WebShopUtil {
    private static final String AND = "&";
    private static final String EQUAL = "=";

    public static boolean contains(String[] array, String value) {
        if (array == null) {
            return false;
        }
        return Arrays.asList(array).contains(value);
    }

    public static int getLastPage(int countProducts, int pageSize) {
        return (int) Math.ceil((double) countProducts / pageSize);
    }

    public static String updatePage(String params, int page) {
        Map<String, List<String>> paramsMap = extractParams(params);
        paramsMap.put(Parameters.PAGE, Arrays.asList(String.valueOf(page)));
        return paramsToString(paramsMap);
    }

    public static Map<String, List<String>> extractParams(String url) {
        Map<String, List<String>> map = new LinkedHashMap<>();
        if (url == null || url.length() == 0) {
            return map;
        }
        String[] params = url.split(AND);

        for (String param : params) {
            String name = param.split(EQUAL)[0];
            String value = param.split(EQUAL)[1];
            List<String> values;
            if (map.containsKey(name)) {
                values = map.get(name);
            } else {
                values = new ArrayList<>();
            }
            values.add(value);
            map.put(name, values);
        }
        return map;
    }

    public static String paramsToString(Map<String, List<String>> params) {
        StringBuilder paramsString = new StringBuilder();
        String and = "";
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            for (String value : entry.getValue()) {
                paramsString.append(and).append(entry.getKey()).append(EQUAL).append(value);
            }
            and = AND;
        }
        return paramsString.toString();
    }
}
