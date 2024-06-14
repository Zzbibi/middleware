package com.zz.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author zhangzhen
 * @create 2024/6/13 10:41
 */
public class Demo1 {

    public static void main(String[] args) {

        //SynchronizedPrinter printer = new SynchronizedPrinter(1, 50);
        LockPrinter printer = new LockPrinter(1, 50);
        new Thread(() -> printer.print(1, 2, "A")).start();
        new Thread(() -> printer.print(2, 1, "B")).start();

    }

    static class SynchronizedPrinter {
        private int num = 0;
        private int flag;
        private final int loopNum;
        private final Object object = new Object();

        public SynchronizedPrinter(int firstFlg, int loopNum) {
            this.flag = firstFlg;
            this.loopNum = loopNum;
        }

        public void print(int currFlag, int nextFlag) {
            for (int i = 0; i < loopNum; i++) {
                synchronized (object) {
                    while (currFlag != flag) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + num++);
                    flag = nextFlag;
                    object.notifyAll();
                }
            }
        }

    }

    static class LockPrinter {
        private int flag;
        private final int loopNum;
        private final Lock lock = new ReentrantLock();
        private final Condition condition = lock.newCondition();


        public LockPrinter (int firstFlag, int loopNum) {
            this.flag = firstFlag;
            this.loopNum = loopNum;
        }

        public void print(int currFlag, int nextFlag, String str) {
            for (int i = 0; i < loopNum; i++) {
                lock.lock();
                while (currFlag != flag) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println(Thread.currentThread().getName() + ": " + str);
                flag = nextFlag;
                condition.signal();
            }
        }

    }

}
