package com.my.concurrent;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 速率控制,semaphore控制的是总量，RateLimiter控制的是速率
 * Created by liangpw on 2016/8/29.
 */
public class RateController {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        RateLimiter rateLimiter = RateLimiter.create(10);
        long begin=System.currentTimeMillis();
        for(int i=1;i<=100;i++){
            rateLimiter.acquire();
            executorService.submit(new Task(i));
        }
        System.out.println("end!took time-----"+ (System.currentTimeMillis()-begin));

    }
    static class Task implements Runnable{
        private int num;
        Task(int num){
            this.num=num;
        }
        @Override
        public void run() {
            System.out.println("task------"+num);
        }
    }
}
