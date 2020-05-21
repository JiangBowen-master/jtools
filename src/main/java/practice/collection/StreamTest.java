package practice.collection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/5/18
 */
public class StreamTest {

    private static Gson gson = new Gson();

    public static void main(String[] args) {

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
