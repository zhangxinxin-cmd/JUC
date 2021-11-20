package com.zxx.bq;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/*
 * 同步队列synchronousQueue不能存储元素，一旦添加，就必须先删除，再执行下面的操作
 * */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                synchronousQueue.put("a");
                synchronousQueue.put("b");
                synchronousQueue.put("c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                synchronousQueue.put("e");
                TimeUnit.SECONDS.sleep(2);
                synchronousQueue.put("f");
                synchronousQueue.put("g");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                System.out.println(synchronousQueue.take());
                System.out.println(synchronousQueue.take());
                System.out.println(synchronousQueue.take());
                System.out.println(synchronousQueue.take());
                System.out.println(synchronousQueue.take());
                System.out.println(synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
