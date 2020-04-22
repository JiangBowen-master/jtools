package practice.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/4/20
 */
public class CompletableFutureTest {

    public static void delay() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 同步
    public static double getPrice() {
        System.out.println("calculating...");
        // sleep 3s
        delay();
        return new Random().nextDouble();
    }

    // 异步(base)
    public static Future<Double> getPriceAsyncBase() {
        // 创建CompletableFuture对象, 它会包含计算的结果
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 如果价格计算正常结束，完成Future操作并设置商品价格
                    double price = getPrice();
                    price = 1/0;
                    futurePrice.complete(price);
                } catch (Exception e) {
                    // 否则就抛出导致失败的异常，完成这次Future操作
                    futurePrice.completeExceptionally(e);
                }
            }
        }).start();

        return futurePrice;
    }

    // 异步（工厂方法）
    public static Future<Double> getPriceAsyncFactoryMethod() {
        Future<Double> futurePrice = CompletableFuture.supplyAsync(new Supplier<Double>() {
            @Override
            public Double get() {
                double price = getPrice();
                return price;
            }
        });

        return futurePrice;
    }

    // 商店类
    public static class Shop {
        public String name;
        public Shop(String name) {
            this.name = name;
        }
        public double getPrice() {
            System.out.println("calculating price for: " + name);
            delay();
            return new Random().nextDouble();
        }
    }

    // 测试多个Shop异步计算价格
    public static void testCompletableFuture() throws ExecutionException, Exception{
        List<Shop> shops = Arrays.asList(
                new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"));

        // 同步流
        long start = System.nanoTime();
        List<String> retPriceListViaStream = shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.name, shop.getPrice()))
                .collect(Collectors.toList());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("同步流 Done in " + duration + " msecs");

        // 异步流
        start = System.nanoTime();
        List<String> retPriceListViaParaStream = shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.name, shop.getPrice()))
                .collect(Collectors.toList());
        duration = (System.nanoTime() - start) / 1000000;
        System.out.println("异步流 Done in " + duration + " msecs");

        // 使用CompletableFuture(不同流水线)
        start = System.nanoTime();
        List<CompletableFuture<String>> completableFuturePriceList = shops
                .stream()
                .map(shop -> CompletableFuture.supplyAsync(new Supplier<String>() {
                    @Override
                    public String get() {
                        return String.format("%s price is %.2f", shop.name, shop.getPrice());
                    }
                }))
                .collect(Collectors.toList());
        List<String> retPriceListViaCompletableFuture = completableFuturePriceList
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        duration = (System.nanoTime() - start) / 1000000;
        // 上面用两个流，是因为如果你在单一流水线中处理流，发向不同商家的请求只能以同步、顺序执行的方式才会成功
        System.out.println("不同流水线处理流 CompletableFuture Done in " + duration + " msecs");

        // （终极解决方案）使用CompletableFuture(不同流水线) 使用线程池提交
        start = System.nanoTime();
        Executor executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread t = new Thread(runnable);
                t.setDaemon(true);
                return t;
            }
        });
        List<CompletableFuture<String>> completableFuturePriceListEXT = shops
                .stream()
                .map(shop -> CompletableFuture.supplyAsync(new Supplier<String>() {
                    @Override
                    public String get() {
                        return String.format("%s price is %.2f", shop.name, shop.getPrice());
                    }
                }, executor))
                .collect(Collectors.toList());
        List<String> retPriceListViaCompletableFutureEXT = completableFuturePriceList
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        duration = (System.nanoTime() - start) / 1000000;
        // 上面用两个流，是因为如果你在单一流水线中处理流，发向不同商家的请求只能以同步、顺序执行的方式才会成功
        System.out.println("不同流水线处理流-EXT CompletableFuture Done in " + duration + " msecs");

        // 使用CompletableFuture(单一流水线) (错误写法)
        // 新的CompletableFuture对象只有在前一个操作完全结束之后，才能创建。
        start = System.nanoTime();
        List<String> retPriceListViaCompletableFutureDiffStream = shops
                .stream()
                .map(shop -> CompletableFuture.supplyAsync(new Supplier<String>() {
                    @Override
                    public String get() {
                        return String.format("%s price is %.2f", shop.name, shop.getPrice());
                    }
                }))
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        duration = (System.nanoTime() - start) / 1000000;
        System.out.println("单一流水线处理流 CompletableFuture Done in " + duration + " msecs");

        System.out.println(retPriceListViaStream);
        System.out.println(retPriceListViaParaStream);
        System.out.println(retPriceListViaCompletableFuture);
        System.out.println(retPriceListViaCompletableFutureDiffStream);
        System.out.println(retPriceListViaCompletableFutureEXT);

    }

    // 异步
//    public static Future<Double> getPriceAsyncViaThreadPool() {
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
//        Future<Double> future = executorService.submit(new Callable<Double>() {
//            @Override
//            public Double call() throws Exception {
//                double price = getPrice();
//                futurePrice.complete(price);
//                return
//            }
//        });
//
//        return futurePrice;
//    }

    public static void main(String[] args) throws Exception {
        System.out.println("begin");
//        System.out.println(getPrice());
//        Future<Double> priceAsync = getPriceAsyncBase();
//        System.out.println(priceAsync);
//        System.out.println(priceAsync.get());

        // test getPriceAsyncFactoryMethod
//        Future<Double> priceAsyncFactoryMethod = getPriceAsyncFactoryMethod();
//        System.out.println(priceAsyncFactoryMethod.get());

        // testCompletableFuture
        testCompletableFuture();
    }

}
