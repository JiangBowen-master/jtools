package com.bowen.jtools.utils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2019 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 19-5-23
 */
public class FileUtil {

    public static int getParentLevel(String relativePath) {

        String pathArr[] = relativePath.split("/");
        Matcher matcher = Pattern.compile("\\.").matcher(pathArr.length == 0 ? relativePath : pathArr[0]);
        int parentLevel = 0;
        while (matcher.find())  parentLevel++;

        return parentLevel;
    }

    /**
     * 根据路径获取该路径下所有File
     *
     * @param path
     * @return
     */
    public static ArrayList<File> getFiles(String path) {
        ArrayList<File> fileList = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileIndex : files) {
                // 递归搜索目录
                if (fileIndex.isDirectory()) {
                    getFiles(fileIndex.getPath());
                } else {
                    fileList.add(fileIndex);
                }
            }
        }
        return fileList;
    }

    /**
     * 根据路径获该路径下所有文件的全路径名
     *
     * @param path
     * @return
     */
    public static ArrayList<String> getDetailFilePathList(String path) {
        ArrayList<String> fileNameList = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileIndex : files) {
                // 递归搜索目录
                if (fileIndex.isDirectory()) {
                    getFiles(fileIndex.getPath());
                } else {
                    fileNameList.add(fileIndex.getAbsolutePath());
                }
            }
        }
        return fileNameList;
    }

    /**
     * 读取文件至字符串
     *
     * @param filePath
     * @return
     */
    public static String readToString(String filePath) {
        String encoding = "UTF-8";
        File file = new File(filePath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按行读取文件至 List<String>
     *
     * @param filePath
     * @return
     */
    public static List<String> readAllLines(String filePath) {
        String allText = readToString(filePath);
        if (allText != null) {
            return Arrays.asList(allText.split("\n"));
        }
        return null;
    }

    /**
     * 无重复读取
     *
     * @param filePath
     * @return
     */
    public static Set<String> readAllLinesWithoutRepeat(String filePath) {
        Set<String> resultSet = new HashSet<String>();
        List<String> tmpList = readAllLines(filePath);
        for (String str : tmpList) {
            resultSet.add(str);
        }
        return resultSet;
    }

    /**
     * 指定写入路径，将所有行写入文件
     *
     * @param filePath
     * @param lines
     * @param append
     */
    public static void writeAllLines(String filePath, List<String> lines, boolean append) {
        File file = new File(filePath);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(), append);
            bw = new BufferedWriter(fw);
            for (String line : lines) {
                bw.write(line.trim() + "\n");
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 默认覆盖写
    public static void writeAllLines(String filePath, List<String> lines) {
        writeAllLines(filePath, lines, false);
    }

    // 清空文件
    public static void emptyFile(String filePath) {
        writeAllLines(filePath, Arrays.asList(""), false);
    }


}
