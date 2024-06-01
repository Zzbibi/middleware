package com.zz.juc;

import org.junit.Test;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交替打印字符，一共打印100个
 *
 * @Author zhangzhen
 * @create 2023/6/30 08:02
 */
public class Demo {

    @Test
    public void test() {
        Printer printer = new Printer(1, 50);
        Thread thread1 = new Thread(() -> {
            printer.print(1, 2, "A");
        });
        Thread thread2 = new Thread(() -> {
            printer.print(2, 1, "B");
        });
        thread1.start();
        thread2.start();
    }

    public class Printer {
        private int flag;
        private int loopNum;
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public Printer (int flag, int loopNum) {
            this.flag = flag;
            this.loopNum = loopNum;
        }

//        public void print(int currFlag, int nextFlag, String printStr) {
//            for (int i = 0; i < loopNum; i++) {
//                synchronized (this) {
//                    while (flag != currFlag) {
//                        try {
//                            this.wait();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    System.out.println(printStr);
//                    flag = nextFlag;
//                    notifyAll();
//                }
//            }
//        }

        public void print(int currFlag, int nextFlag, String printStr) {
            for (int i = 0; i < loopNum; i++) {
                try {
                    lock.lock();
                    while (flag != currFlag) {
                        condition.await();
                    }
                    System.out.println(printStr + ":" + i);
                    flag = nextFlag;
                    condition.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        Printer1 printer1 = new Printer1(50, 1);

        new Thread(() -> printer1.print(1, 2), "A").start();
        new Thread(() -> printer1.print(2, 1), "B").start();
    }
    public static class Printer1 {

        private int num = 0;
        private final int loopNum;
        private int flag;

        public Printer1(int loopNum, int flag) {
            this.loopNum = loopNum;
            this.flag = flag;
        }

        public void print(int currFlag, int nextFlag) {
            for (int i = 0; i < loopNum; i++) {
                synchronized (this) {
                    while (currFlag != flag) {
                        try {
                            wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("Thread:" + Thread.currentThread().getName() + " num:" +  num++);
                    flag = nextFlag;
                    notify();
                }
            }
        }
    }



}
