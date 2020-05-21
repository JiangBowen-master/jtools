package com.bowen.jtools.utils;

import static java.lang.Math.sqrt;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/5/8
 */
public class CTRUtil {

    /**
     * p —— 概率，即点击的概率，也就是 CTR
     * n —— 样本总数，即曝光数
     * z —— 在正态分布里，均值 + z * 标准差会有一定的置信度。例如 z 取 1.96，就有 95% 的置信度。
     * @param click 点击
     * @param pv 曝光
     * @return
     */
    public static double getWalsonCtr(int click, int pv) {
        if (pv * click == 0 || pv < click) {
            return 0.f;
        }
        double score = 0.f;
        double z = 1.96f;
        int n = pv;
        double p = 1.0f * click / pv;
        score = (p + z * z / (2.f * n) - z * sqrt((p * (1.0f - p) + z * z / (4.f * n)) / n)) / (1.f
                + z * z / n);
        return score;
    }

    public static void main(String[] args) {
        System.out.println(getWalsonCtr(0, 0));
        System.out.println(getWalsonCtr(2, 0));
        System.out.println(getWalsonCtr(2, 1));

        System.out.println(getWalsonCtr(5, 10));
        System.out.println(getWalsonCtr(50, 100));
        System.out.println(getWalsonCtr(500, 1000));

        System.out.println(getWalsonCtr(32, 34));
    }

}
