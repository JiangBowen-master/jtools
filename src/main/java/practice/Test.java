package practice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/10/23
 */
public class Test {

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

    public static void main(String[] args) {
        double[] arrs = {1,2,3,4,5,6,7,8,9,100};
        List<Double> doubleList = new ArrayList<>();
        for (double currArr : arrs) {
            doubleList.add(currArr);
        }
        System.out.println(getPercentile(doubleList, 0.9));
    }

}
