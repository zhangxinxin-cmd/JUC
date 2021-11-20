package com.zxx.pc;
/*
 * A执行完调用B，B执行完调用C，C执行完调用C
 * */

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class C {
    public static void main(String[] args) {
        Data2 data2=new Data2("A");
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data2.printA();
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data2.printB();
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data2.printC();
            }
        }).start();
    }
}

class Data2 {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();
    String light;

    public Data2(String light) {
        this.light = light;
    }

    public void printA() {
        lock.lock();
        try {
            while (!Objects.equals(light, "A")) {
                conditionA.await();
            }
            System.out.println(Thread.currentThread().getName() + "A");
            light="B";
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (!Objects.equals(light, "B")) {
                conditionB.await();
            }
            System.out.println(Thread.currentThread().getName() + "B");
            light="C";
            conditionC.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            while (!Objects.equals(light, "C")) {
                conditionC.await();
            }
            System.out.println(Thread.currentThread().getName() + "C");
            light="A";
            conditionA.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}