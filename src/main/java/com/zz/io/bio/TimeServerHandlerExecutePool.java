package com.zz.io.bio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author zhangzhen
 * @create 2023/2/5 20:02
 */
public class TimeServerHandlerExecutePool {

    private ExecutorService executor;

    public TimeServerHandlerExecutePool(int coreSize, int maxSize, int queueSize) {
        this.executor = new ThreadPoolExecutor(coreSize, maxSize, 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueSize));
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }

}
