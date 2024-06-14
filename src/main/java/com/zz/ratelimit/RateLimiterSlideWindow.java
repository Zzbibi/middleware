package com.zz.ratelimit;

import java.util.Arrays;

/**
 * @Author zhangzhen
 * @create 2024/6/14 10:02
 */
public class RateLimiterSlideWindow {

    private final int windowSize;
    private final int qps;
    private final int splitNum;
    private final int[] counters;
    private int index;
    private long startTime;

    public RateLimiterSlideWindow(int windowSize, int qps, int splitNum) {
        this.windowSize = windowSize;
        this.qps = qps;
        this.splitNum = splitNum;
        this.counters = new int[splitNum];
        this.index = 0;
        this.startTime = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire() {
        // 向前滑动的窗口数量
        long slideWindowNum = (Math.max(System.currentTimeMillis() - windowSize - startTime, 0)) / (windowSize / splitNum);
        slideWindow(slideWindowNum);

        int currSumCount = Arrays.stream(counters).sum();
        if (currSumCount >= qps) {
            return false;
        }
        counters[index]++;
        return true;
    }

    private void slideWindow(long slideWindowNum) {
        if (slideWindowNum == 0) {
            return;
        }

        long needSlideWindowNum = Math.min(slideWindowNum, splitNum);
        for (int i = 0; i < needSlideWindowNum; i++) {
            // 窗口向前滑动，同时将当前小窗口的计数器置为0
            index = (index + 1) % splitNum;
            counters[index] = 0;
        }

        startTime = startTime + slideWindowNum * (windowSize / splitNum);
    }


    public static void main(String[] args) {
        RateLimiterSlideWindow rateLimiter = new RateLimiterSlideWindow(1000, 2, 2);

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (rateLimiter.tryAcquire()) {
            System.out.println("限流通过");
        } else {
            System.out.println("被限流");
        }

        if (rateLimiter.tryAcquire()) {
            System.out.println("限流通过");
        } else {
            System.out.println("被限流");
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (rateLimiter.tryAcquire()) {
            System.out.println("限流通过");
        } else {
            System.out.println("被限流");
        }


    }

}
