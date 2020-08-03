package com.bowen.jtools.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringEscapeUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/5/12
 */
public class BWJsonUtils {

    private static Gson gson = new Gson();

    public static void main(String[] args) {
        // test1();
         test2();
//        test3();
    }

    private static void test2() {
        String json = "{\"id\":\"netease_II01MXKG5HP9BLV\",\"title\":\"特朗普发出威胁：将永久冻结世卫组织经费\",\"cover\":\"https://xiaomi-browser-nos.yiyouliao.com/icovr-20200519-5c6293121c0d37590304e6d271f998b5.jpg?time\\u003d1589874987\\u0026signature\\u003dE5A283A94A30025DC9892C9D86E375EC\",\"url\":\"https://yiyouliao.com/api-server/rss/xiaomi/item/II01MXKG5HP9BLV.html?version\\u003d2\\u0026mibusinessId\\u003dxiangkan\\u0026env\\u003dtest\",\"publishTime\":\"2020-05-19 15:51:37\",\"lbsLocations\":[]}";
        NavigateArticleItem navigateArticleItem = gson.fromJson(json, NavigateArticleItem.class);
        System.out.println(navigateArticleItem.toString());

        // JSon串在被串行化后保存在文件中，读取字符串时，是不能直接拿来用JSON.parse()解析为JSON  对象的。因为它是一个字符串，不是一个合法的JSON对象格式。
        //
        System.out.println(json);
        json = "{\\\"id\\\":\\\"netease_II01MXKG5HP9BLV\\\",\\\"title\\\":\\\"特朗普发出威胁：将永久冻结世卫组织经费\\\"";
        System.out.println(json);
        System.out.println(StringEscapeUtils.unescapeJava(json));

    }

    private static void test1() {
        //        String testJson = "{\"info\":\"\",\"opt\":\"reset\"}";
        String testJson = "{\"imei\":\"00ada36e8b278616c0e7ecfb05928b62\",\"categorylist\":[\"财经\",\"社会\"]}";
        JsonObject jsonObject = gson.fromJson(testJson, JsonObject.class);
        System.out.println(jsonObject.getAsJsonArray("categorylist"));
        System.out.println(jsonObject.get("categorylist"));
        System.out.println(jsonObject.get("categorylist").getAsJsonArray());
        for (JsonElement el : jsonObject.getAsJsonArray("categorylist")) {
            System.out.println(el.getAsString());
        }
    }

    private static void test3() {
        String testJson = "{\"imei\":\"00ada36e8b278616c0e7ecfb05928b62\",\"categorylist\":[\"财经\",\"社会\"]}";
        JsonObject jsonObject = gson.fromJson(testJson, JsonObject.class);

        String testJson2 = "{\"imei\":\"12312231616c0e7ecfb05928b62\",\"categorylist\":[\"财经22\",\"社会22\"]}";
        JsonObject jsonObject2 = gson.fromJson(testJson2, JsonObject.class);

        String testJson3 = "{\"imei\":\"2343436e8b278616c0e7ecfb05928b62\",\"categorylist\":[\"财经33\",\"社会33\"]}";
        JsonObject jsonObject3 = gson.fromJson(testJson3, JsonObject.class);

        List<JsonObject> jsonObjectList = new ArrayList<>();
        jsonObjectList.add(jsonObject);
        jsonObjectList.add(jsonObject2);
        jsonObjectList.add(jsonObject3);

        System.out.println(jsonObjectList.stream().map(e -> e.getAsJsonArray("categorylist")).collect(Collectors.toList()));

        Type listType = new TypeToken<List<String>>() {}.getType();
        Object res = jsonObjectList.stream().map(e -> gson.fromJson(e.get("categorylist"), listType)).collect(Collectors.toList());
        System.out.println(res);
    }

}
