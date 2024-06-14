package com.zz.ratelimit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author zhangzhen
 * @create 2024/6/14 09:41
 */
public class RateLimiterFixedWindow {

    private final long windowSize;
    private final long qps;
    private final AtomicInteger counter = new AtomicInteger(0);
    private long startTime;


    public RateLimiterFixedWindow(long windowSize, long qps) {
        this.windowSize = windowSize;
        this.qps = qps;
        startTime = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire() {
        if (System.currentTimeMillis() - startTime > windowSize) {
            startTime = System.currentTimeMillis();
            counter.set(0);
        }

        if (counter.get() >= qps) {
            return false;
        }

        counter.incrementAndGet();
        return true;
    }


    public static void main(String[] args) {
        RateLimiterFixedWindow rateLimiterFixedWindow = new RateLimiterFixedWindow(1000, 2);

        for (int i = 0; i < 10; i++) {
            if (rateLimiterFixedWindow.tryAcquire()) {
                System.out.println("限流通过");
            } else {
                System.out.println("被限流");
            }

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
