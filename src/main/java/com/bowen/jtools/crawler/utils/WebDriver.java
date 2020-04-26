package com.bowen.jtools.crawler.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Copyright (c) 2019 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 19-6-4
 */
public class WebDriver extends ChromeDriver implements AutoCloseable {

    private static String binaryPath = null;

    public static void setWebDriverPath(String webDriverPath, String binaryPath) {
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        if (binaryPath != null) {
            WebDriver.binaryPath = binaryPath;
        }
    }

    public static WebDriver newDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setHeadless(true);
//        chromeOptions.addArguments("--no-sandbox");

        if (WebDriver.binaryPath != null) {
            chromeOptions.setBinary(binaryPath);
        }

        return new WebDriver(chromeOptions);
    }

    private WebDriver(ChromeOptions chromeOptions) {
        super(chromeOptions);
    }

    @Override
    public void close() {
        super.close();
        super.quit();
    }

    public static void main(String[] args) {
        setWebDriverPath("/usr/bin/chromedriver", null);

        try (WebDriver webDriver = WebDriver.newDriver()) {
            webDriver.get("https://www.toutiao.com/");

            System.out.println(webDriver.getTitle());
            System.out.println(webDriver.getCurrentUrl());
            System.out.println(webDriver.findElementByTagName("html").getAttribute("innerHTML"));
        }
    }

}
