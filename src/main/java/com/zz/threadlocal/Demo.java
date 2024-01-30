package com.zz.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author zhangzhen
 * @create 2023/10/29 11:44
 */
public class Demo {

//    public static void main(String[] args) {
//        ThreadLocal threadLocal = ThreadLocal.withInitial(() -> null);
//
//        int count = 2;
//        ExecutorService executorService = Executors.newFixedThreadPool(count);
//
//        for (int i = 0; i < count; i++) {
//            executorService.execute(() -> threadLocal.set(Thread.currentThread().getName()));
//        }
//
//        for (int i = 0; i < count; i++) {
//            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get()));
//        }
//
//        try {
//            Thread.sleep(1000);
//            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            executorService.shutdown();
//        }
//    }

//    public static void main(String[] args) {
//        ThreadLocal threadLocal = new InheritableThreadLocal();
//
//        String threadName = Thread.currentThread().getName();
//        threadLocal.set(threadName);
//
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(() -> System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get()));
//
//        try {
//            Thread.sleep(1000);
//            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//     }

    public static void main(String[] args) {
        ThreadLocal threadLocal = new TransmittableThreadLocal();

        threadLocal.set("value1-in-parent");

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Runnable runnable = () -> System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        TtlRunnable ttlRunnable = TtlRunnable.get(runnable);

        executor.submit(ttlRunnable);

        threadLocal.set("value2-in-parent");
        TtlRunnable ttlRunnable1 = TtlRunnable.get(runnable);
        executor.submit(ttlRunnable1);

        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

}
