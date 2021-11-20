package com.zxx.rw;

import javax.lang.model.element.VariableElement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        //写入
        for (int i = 0; i < 5; i++) {
            int temp = i;
            new Thread(() -> {
                myCache.put(String.valueOf(temp), UUID.randomUUID().toString().substring(0, 5));
            },String.valueOf(i)).start();
        }
        //读取
        for (int i = 0; i < 5; i++) {
            int temp = i;
            new Thread(() -> {
                myCache.get(String.valueOf(temp));
            },String.valueOf(i)).start();
        }
    }
}

class MyCache {
    private volatile Map<String, String> map = new HashMap<>();
    //写入会混乱，一个线程的写入还没完毕，另一个线程就开始写入
    public void put(String key, String value) {
        System.out.println(Thread.currentThread().getName() + "写入" + key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "写入完毕");
    }
    //读取可以多个线程同时进行
    public void get(String key) {
        System.out.println(Thread.currentThread().getName() + "正在读取" + key);
        String value = map.get(key);
        System.out.println(Thread.currentThread().getName() + "读取完毕");
    }
}

class MyCacheLock {
    private volatile Map<String, String> map = new HashMap<>();
    //更加细粒度的控制
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock lock=new ReentrantLock();
    //单个线程写入
    public void put(String key, String value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入完毕");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
    //多个线程读取，但读写互斥
    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在读取" + key);
            String value = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取完毕");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}