package com.bowen.jtools.nlp;

import com.bowen.jtools.utils.BWFileUtils;

/**
 * Copyright (c) 2019 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 19-5-23
 */
public class Test {

    public static void testFileUtil() {
        System.out.println(BWFileUtils.getParentLevel("../path"));
        System.out.println(System.getProperty("user.dir"));
    }

    public static void main(String args[]) {
        testFileUtil();
    }

}
