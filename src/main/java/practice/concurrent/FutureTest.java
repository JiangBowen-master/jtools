package practice.concurrent;

import java.util.concurrent.*;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/4/20
 */
public class FutureTest {

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double> future = executorService.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Thread.sleep(5000);
                return 23.33;
            }
        });

        System.out.println("something else");

        try {
            // get 是阻塞操作
            Double result = future.get(2, TimeUnit.SECONDS);
            System.out.println("result: " + result);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
