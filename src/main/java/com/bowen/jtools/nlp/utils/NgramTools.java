package com.bowen.jtools.nlp.utils;

import com.bowen.jtools.utils.StringUtil;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (c) 2019 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 19-5-23
 */
public class NgramTools {

    /**
     * 根据 Title 生成 NGram List
     *
     * @param title
     * @param gramNum
     * @return
     */
    public static List<String> getNGramListByTitleNoSegment(String title, int gramNum) {

        List<String> nGramList = new ArrayList<>();

        char titleCharArr[] = StringUtil.filterSpecialCharacter(title).toCharArray();
        int titleLength = titleCharArr.length;

        for (int gramWindow = gramNum; gramWindow > 0; gramWindow--) {

            StringBuilder sb = new StringBuilder();
            for (int beginIndex = 0; beginIndex < (titleLength - gramWindow + 1); beginIndex++) {
                for (int endIndex = beginIndex; endIndex < (beginIndex + gramWindow); endIndex++) {
                    sb.append(titleCharArr[endIndex]);
                }
                sb.append(" ");
            }

            if (!sb.toString().trim().isEmpty())    nGramList.add(sb.toString() + "\n");
        }

        return nGramList;
    }

    /**
     * 根据单字符串生成NGram词数组
     *
     * @param str 生成NGram的源字符串
     * @return NGram 词组
     */
    public static List<String> getNGramListByStr(String str){
        ArrayList<String> words = new ArrayList<>();
        HanLP.segment(str).forEach(term -> words.add(term.word));
        return getNGramListByWordsList(words);
    }

    /**
     * 根据一个关键词数组生成NGram词数组
     *
     * @param words 分词后的词数组
     * @return NGram 词组
     */
    public static List<String> getNGramListByWordsList(List<String> words) {

        List<String> nGramList = new ArrayList<>();
        int wordsSize = words.size();

        if (wordsSize <= 2) {
            return nGramList;
        }

        StringBuilder sbInitial = new StringBuilder();
        words.forEach(word -> {
            if (!StringUtil.filterSpecialCharacter(word).equals(""))
                sbInitial.append(word + " ");
        });
        nGramList.add(sbInitial.toString());

        // 窗口从最大值开始，递减 (窗口长度 1/2 ~ 2/3 + 全量)
        for (int windowSize = wordsSize * 2 / 3; windowSize >= wordsSize / 2; windowSize--) {
            for (int i = 0; i < wordsSize - windowSize + 1; i++) {
                StringBuilder sb = new StringBuilder();

                for (int j = i; j < i + windowSize; j++) {
                    String curWord = words.get(j);
                    // 过滤掉特殊字符后训练模型
                    if (StringUtil.filterSpecialCharacter(curWord).equals(""))
                        continue;
                    sb.append(words.get(j) + " ");
                }

                nGramList.add(sb.toString());
            }
        }

        return nGramList;
    }

    public static void main(String args[]){

        System.out.println(getNGramListByTitleNoSegment("琅琊榜穆青带周老先生进京梅长苏也是放下心来!", 4));
        System.out.println(getNGramListByTitleNoSegment("琅琊榜穆青带周老先生进京梅长苏也是放下心来!", 5));
        System.out.println(getNGramListByTitleNoSegment("琅琊榜好看的很", 4));
        System.out.println(getNGramListByTitleNoSegment("琅琊榜", 4));

        // testt OK
        System.out.println(getNGramListByWordsList(Arrays.asList("我", "很好", "，", "那么", "你呢", "？", "我", "tm", "也", "很好", "！")));
        System.out.println(getNGramListByWordsList(Arrays.asList("我", "很好", "那么")));

        List<Term> terms = HanLP.segment("琅琊榜穆青带周老先生进京梅长苏也是放下心来!");
        List<String> words = new ArrayList<>();
        terms.forEach(term -> words.add(term.word));

        System.out.println(getNGramListByWordsList(words));
    }


}
