package com.my.concurrent;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.*;

/**
 * 速率控制,semaphore控制的是总量，RateLimiter控制的是速率
 * Created by liangpw on 2016/8/29.
 */
public class RateController2 {
    public static void main(String[] args) {
        Haha haha=new Haha();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        RateLimiter rateLimiter = RateLimiter.create(10);
        long begin=System.currentTimeMillis();
        for(int i=1;i<=100;i++){
            rateLimiter.acquire();
            executorService.submit(new Task(i,haha));

        }
        System.out.println("end!took time-----"+ (System.currentTimeMillis()-begin));

    }
    static class Task implements Runnable{
        private int num;
        private Haha haha;
        Task(int num,Haha haha){
            this.num=num;
            this.haha=haha;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            haha.haha(num);
        }
    }
}
