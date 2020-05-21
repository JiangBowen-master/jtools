package com.bowen.jtools.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
    }

    private static void test2() {
        String json = "{\"id\":\"netease_II01MXKG5HP9BLV\",\"title\":\"特朗普发出威胁：将永久冻结世卫组织经费\",\"cover\":\"https://xiaomi-browser-nos.yiyouliao.com/icovr-20200519-5c6293121c0d37590304e6d271f998b5.jpg?time\\u003d1589874987\\u0026signature\\u003dE5A283A94A30025DC9892C9D86E375EC\",\"url\":\"https://yiyouliao.com/api-server/rss/xiaomi/item/II01MXKG5HP9BLV.html?version\\u003d2\\u0026mibusinessId\\u003dxiangkan\\u0026env\\u003dtest\",\"publishTime\":\"2020-05-19 15:51:37\",\"lbsLocations\":[]}";
        NavigateArticleItem navigateArticleItem = gson.fromJson(json, NavigateArticleItem.class);
        System.out.println(navigateArticleItem.toString());
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

}
