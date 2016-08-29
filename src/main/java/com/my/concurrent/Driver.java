package com.my.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by liangpw on 2016/8/29.
 */
public class Driver {
    public static void main(String[] args) {
        CountDownLatch start=new CountDownLatch(1);
        CountDownLatch done=new CountDownLatch(5);
        for (int i=0;i<5;i++){
            new Thread(new Worker(start,done)).start();
        }
        long begin=System.currentTimeMillis();
        //driver 初始化
        driver_init();
        System.out.println(System.currentTimeMillis()-begin);
        //让worker开始干活
        start.countDown();
        //等待所有worker完成才完成
        try {
            done.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-begin);


    }

    private static void driver_init(){
        System.out.println(Thread.currentThread().getName()+"  do init....");
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
        }

    }


    static class Worker implements Runnable {
        private final CountDownLatch start;
        private final CountDownLatch done;

        Worker(CountDownLatch start, CountDownLatch done) {
            this.start = start;
            this.done = done;
        }

        @Override
        public void run() {
            try {
                //等待driver准备完成才开始
                start.await();
                doWork();
                //一个worker完成，计数减一
                done.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        private void doWork(){
            System.out.println(Thread.currentThread().getName()+"  do work....");
            try {
                Thread.sleep(10000l);
            } catch (InterruptedException e) {
            }
        }
    }
}
