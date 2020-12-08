package practice.basevar;

import java.util.HashMap;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/6/8
 */
public class StringTest {

    private static HashMap<String, Integer> categoryRecallQUeueCount = new HashMap<>();

    public static void main(String[] args) {
        String testStr = "fenghuang_ucms_7wycqagKCJN";
        int indexOfSlash = testStr.indexOf("_");
        System.out.println(testStr.indexOf("_"));
        System.out.println(testStr.substring(0, indexOfSlash));
        System.out.println(testStr.substring(indexOfSlash + 1));

        System.out.println(categoryRecallQUeueCount.get("key1"));

    }

}
