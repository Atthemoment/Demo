package com.my.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by liangpw on 2016/8/29.
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Semaphore semaphore=new Semaphore(10);
        long begin=System.currentTimeMillis();
        for(int i=1;i<=100;i++){
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.submit(new Task(i,semaphore));
        }
        System.out.println("end!took time-----"+ (System.currentTimeMillis()-begin));
    }

    static class Task implements Runnable{
        private int num;
        private Semaphore semaphore;
        Task(int num,Semaphore semaphore){
            this.num=num;
            this.semaphore=semaphore;
        }
        @Override
        public void run() {
            System.out.println("task------"+num);
            semaphore.release();
        }
    }

}
