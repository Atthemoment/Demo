package com.my.zookeeper.election;

/**
 * Created by liangpw on 2016/8/25.
 */
public class TestWorker2 {
    public static void main(String[] args)throws Exception{
        Worker worker = new Worker("TestWorker2");
        worker.startUp();
        try {
            Object lock = new Object();
            synchronized (lock) {
                while (true) {
                    lock.wait();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
