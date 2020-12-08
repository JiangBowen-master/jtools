package com.bowen.jtools.utils;

/**
 * Copyright (c) 2019 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 19-1-22
 */
public class BWTimeDecayUtils {

    /**
     * 时间衰减 Fnc
     *
     * @param beginValue 初始值
     * @param endValue 结束值
     * @param totalDecayTime 总衰减时长
     * @param curDecayTime 当前时间
     * @return 衰减权重
     */
    private static double getTimeDecayWeight(double beginValue, double endValue, int totalDecayTime, double curDecayTime) {
        double alpha = Math.log(beginValue / endValue) / totalDecayTime;
        double l = - Math.log(beginValue) / alpha;
        return Math.exp(-alpha * (curDecayTime + l));
    }

    public static void main(String args[]) {
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 0.0));
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 0.5));
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 2));
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 4));
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 8));
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 10));
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 12));
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 14));
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 24));
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 30));
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 40));
        System.out.println(getTimeDecayWeight(1.0, 0.1, 48, 48));
    }

}
