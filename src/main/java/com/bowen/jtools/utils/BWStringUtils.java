package com.bowen.jtools.utils;

import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;


import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2019 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 19-5-23
 */
public class BWStringUtils {

    private static DocVectorModel docVectorModel;

    static{
        try {
            long startTime = System.currentTimeMillis();
            // 文档向量
            String modelPath = System.getProperty("user.dir") + "/data/model/title_words.vector";
            docVectorModel = new DocVectorModel(new WordVectorModel(modelPath));
            long endTime = System.currentTimeMillis();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String xmlEncode(String src) {
        if ((null == src) || (src.equals(""))) {
            return "";
        }
        String dst = src;
        dst = dst.replaceAll("&", "&amp;");
        dst = dst.replaceAll("<", "&lt;");
        dst = dst.replaceAll(">", "&gt;");
        dst = dst.replaceAll("\"", "&quot;");
        dst = dst.replaceAll("'", "&acute;");
        return dst;
    }

    // 过滤特殊字符
    public static String filterSpecialCharacter(String str){
        // 只允许字母和数字、中文
        String regEx = "[`~☆★!@#$%^&*()+=|{}':;.,\\[\\]《》·<>/?！￥…（）【】‘；：\"_～”“’。，、？——-]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim().replace(" ", "").replace("\\", "");
    }

    // 返回相似度（利用编辑距离）
    public static double getSimilarity(String orign, String dest){
        int distance = editDistance(orign, dest);
        int length = orign.length() > dest.length() ? orign.length() : dest.length();
        double ret = 1 - distance * 1.0 / length;
        return ret;
    }

    // 返回相似度（利用模型）
    public static double getSimilarityUsingModel(String var1, String var2){
        return docVectorModel.similarity(var1, var2);
    }

    // 编辑距离
    private static int editDistance(String a, String b) {
        if (a == null || b == null) {
            return -1;
        }
        int[][] matrix = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i < a.length() + 1; i++) {
            for (int j = 0; j < b.length() + 1; j++) {
                if (i == 0) {
                    matrix[i][j] = j;
                } else if (j == 0) {
                    matrix[i][j] = i;
                } else {
                    if (a.charAt(i - 1) == b.charAt(j - 1)) {
                        matrix[i][j] = matrix[i - 1][j - 1];
                    } else {
                        matrix[i][j] = 1 + Math.min(Math.min(matrix[i - 1][j], matrix[i][j - 1]), matrix[i - 1][j - 1]);
                    }
                }
            }
        }
        return matrix[a.length()][b.length()];
    }

    public static void main(String args[]){

        String relativelyPath=System.getProperty("user.dir");

        System.out.println(relativelyPath);

        System.out.println("\\N".isEmpty());
        String str1 = "马保国被业余选手4秒KO，却击败过格斗冠军，李连杰的评价很客观";
//        String str2 = "太极掌门马保国4秒被50岁大叔KO";
//        String str2 = "特朗普发出威胁：将永久冻结世卫组织经费";
        String str2 = "翼装飞行失联女生身亡 张家界天门山景区：降落伞未打开";
        System.out.println(getSimilarityUsingModel(str1, str2));

        while(true) {
            long startTime = System.currentTimeMillis();
            // refresh crawledTitles n 小时内抓取的, 重新load
            try {
                for(int i = 0; i < 20000; i++) {
                    getSimilarityUsingModel("正式成为世界非遗,中国藏医药浴法申遗经么?" + i, "正式成为世界遗,中医药浴法申遗经历么?" + i + 2);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println((endTime - startTime));
        }
        //        System.out.println(filterSpecialCharacter("中 美贸\"易磋商8日\"持续到深,.-——_~～，。夜 特朗普：非常顺利"));
        //        System.out.println(filterSpecialCharacter("中 美贸\"易  abv da磋商8日\"持续到深,.-——_~～，。夜 特朗普：非常顺利"));
        //        System.out.println(filterSpecialCharacter("12《》月CPI将公布"));
    }

}
