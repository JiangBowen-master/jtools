package com.bowen.jtools.crawler;

import com.bowen.jtools.crawler.utils.WebDriver;

/**
 * Copyright (c) 2019 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 19-6-4
 */
public class TouTiaoCrawler {

    public static String getHtmlByUrl(String url) {

        WebDriver webDriver = WebDriver.newDriver();

        while (true) {
            try {
                webDriver.get(url);
//                return webDriver.findElementByTagName("html").getAttribute("innerHTML");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    public static void main(String args[]) {
        String html = TouTiaoCrawler.getHtmlByUrl("https://www.toutiao.com/search/?keyword=机器之心");
        System.out.println(html);
    }

}
