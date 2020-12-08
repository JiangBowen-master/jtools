package practice;

import com.bowen.jtools.utils.BWFileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/10/23
 */
public class ContestA {



    private static double getPercentile(List<Double> dataList, double p) {
        int n = dataList.size();
        dataList.sort(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                if(o1 == null || o2== null){
                    return 0;
                }
                return o1.compareTo(o2);
            }
        });
        double px =  p*(n-1);
        int i = (int)java.lang.Math.floor(px);
        double g = px - i;
        if(g==0){
            return dataList.get(i);
        }else{
            return (1-g)*dataList.get(i)+g*dataList.get(i+1);
        }
    }

    private static void getAllMaxPercent90() throws IOException {

//        List<String> lines = BWFileUtils.readAllLines("/home/vague/下载/315532803txt");

        List<String> lines = Files.readAllLines(Paths.get("/home/vague/下载/315532803txt"), Charset.defaultCharset());
//        System.out.println(lines);

        int startIdx = 0;
        assert lines != null;
        int finalIdx = lines.size() - 1;
        double maxPercentile90 = 0;

        for (startIdx = 0; startIdx <= finalIdx - 3600 + 1; startIdx++) {
            int endIdx = startIdx + 3600 - 1;
            List<String> strings = lines.subList(startIdx, endIdx + 1);
            List<Double> doubleList = new ArrayList<>();
            for (String curStr : strings) {
                doubleList.add(Double.parseDouble(curStr));
            }
            maxPercentile90 = Math.max(getPercentile(doubleList, 0.9), maxPercentile90);
            System.out.println("current maxPercentile90 is: " + maxPercentile90);
        }

        System.out.println("答案: " + maxPercentile90);
    }

    public static void main(String[] args) throws IOException {


        getAllMaxPercent90();

//        double[] arrs = {2,4,5,1,3,3,4,1,2,5};
//        List<Double> doubleList = new ArrayList<>();
//        for (double currArr : arrs) {
//            doubleList.add(currArr);
//        }
//        System.out.println(doubleList.subList(0, 2));
//
//        System.out.println(getPercentile(doubleList, 0.5));

    }



}
