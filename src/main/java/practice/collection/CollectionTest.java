package practice.collection;

import java.util.*;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/5/18
 */
public class CollectionTest {

    static Map<String, List<String>> userArticleIdRecallMap = new HashMap<>();

    public static void main(String[] args) {
        userArticleIdRecallMap.put("a", new ArrayList<>(Arrays.asList("111", "222", "333")));
        userArticleIdRecallMap.put("b", new ArrayList<>(Arrays.asList("111111", "222222", "333333")));
        userArticleIdRecallMap.put("c", new ArrayList<>(Arrays.asList("111111111", "222222222", "333333333")));

        for (Map.Entry<String, List<String>> entry : userArticleIdRecallMap.entrySet()) {
            String userId = entry.getKey();
            List<String> value = entry.getValue();
            if (entry.getValue().size() < 4) {
                value.add("xxx");
                userArticleIdRecallMap.put(userId, value);
            }
        }

        System.out.println(userArticleIdRecallMap);
    }

}
