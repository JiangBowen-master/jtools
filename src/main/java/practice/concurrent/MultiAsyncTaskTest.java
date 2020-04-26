package practice.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/4/22
 */
public class MultiAsyncTaskTest {

    public static void test1(List<Shop> shops) {
        long start = System.nanoTime();
        shops.stream()
                .map(shop -> shop.getPrice("iphone"))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("test1: " + duration + " msecs");
    }

    public static void test2(List<Shop> shops) {
        Executor executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread t = new Thread(runnable);
                t.setDaemon(true);
                return t;
            }
        });
        long start = System.nanoTime();
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice("iphone"), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
                .collect(Collectors.toList());
        List<String> priceResultList = priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("test2: " + duration + " msecs");
    }

    public static void main(String[] args) {
        List<Shop> shops = Arrays.asList(
                new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"));

        test1(shops);
        test2(shops);

    }

    public static Random random = new Random();

    // 对商店返回字符串的解析操作
    public static class Quote {
        private final String shopName;
        private final double price;
        private final Discount.Code discountCode;
        public Quote(String shopName, double price, Discount.Code code) {
            this.shopName = shopName;
            this.price = price;
            this.discountCode = code;
        }
        public static Quote parse(String s) {
            String[] split = s.split(":");
            String shopName = split[0];
            double price = Double.parseDouble(split[1]);
            Discount.Code discountCode = Discount.Code.valueOf(split[2]);
            return new Quote(shopName, price, discountCode);
        }
        public String getShopName() {
            return shopName;
        }
        public double getPrice() {
            return price;
        }
        public Discount.Code getDiscountCode() {
            return discountCode;
        }
    }

    public static void delay() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 集中式的折扣服务
    public static class Discount {
        // 该折扣服务提供了五个不同的折扣代码，每个折扣代码对应不同的折扣率
        public enum Code {
            NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
            private final int percentage;
            Code(int percentage) {
                this.percentage = percentage;
            }
        }
        // 接收一个Quote对象，返回一个字符串，表示生成该Quote的shop中的折扣价格
        public static String applyDiscount(Quote quote) {
            return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
        }
        private static double apply(double price, Code code) {
            System.out.println("calculating discounted price...");
            delay();
            double discountedPrice = price * (100 - code.percentage) / 100;
            System.out.println("discounted price is: " +  discountedPrice);
            return discountedPrice;
        }
    }

    public static class Shop {
        public String name;
        public Shop(String name) {
            this.name = name;
        }
        // 以Shop-Name:price:DiscountCode的格式返回一个String类型的值
        public String getPrice(String product) {
            double price = calculatePrice(product);
            Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
            String retPrice = String.format("%s:%.2f:%s", name, price, code);
            System.out.println("retPrice is: " + retPrice);
            return retPrice;
        }
        // 返回随机价格
        private double calculatePrice(String product) {
            System.out.println("calculating price...");
            delay();
            return random.nextDouble() * product.charAt(0) + product.charAt(1);
        }
    }

}
