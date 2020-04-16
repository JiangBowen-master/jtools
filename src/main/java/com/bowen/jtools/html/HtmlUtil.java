package com.bowen.jtools.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2019 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 19-5-23
 */
public class HtmlUtil {

    /**
     * 根据正则 reg 匹配文本
     *
     * @param str
     * @param regEx
     * @return
     */
    public static String matchStrByReg(String str, String regEx){
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(str);
        if (matcher.find()){
            return HtmlUtil.filterHTMLCharacter(matcher.group());
        }

        return "";
    }

    /**
     * 过滤 HTML 标签
     *
     * @param htmlStr
     * @return
     */
    public static String filterHTMLCharacter(String htmlStr){

        // 去除 script、style、以及所有html标签
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>";
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>";
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll("");

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll("");

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll("");

        return htmlStr.trim();
    }

}
