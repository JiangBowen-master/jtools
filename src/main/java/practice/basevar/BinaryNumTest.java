package practice.basevar;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/4/23
 */
public class BinaryNumTest {

    public static void main(String[] args) {
        int i = 7;
        System.out.println(Integer.toBinaryString(i));

        String s = "a,g";
        for (String str : s.split(",")) {
            System.out.println(str);
        }

    }

}
