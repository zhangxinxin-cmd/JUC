package com.zxx.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class B {
    public static void main(String[] args) throws InterruptedException {
        Data data = new Data();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        }).start();
        Thread.sleep(1000);
        System.out.println(data.getIn());
        System.out.println(data.getCo());
    }
}

class Data {
    private int number;
    private int in = 0;
    private int co = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public Data() {
        this.number = 0;
    }

    public void increment() {
        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            number++;
            in++;
            System.out.println(Thread.currentThread().getName() + "--------->" + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void decrement() {
        lock.lock();
        try {
            while (number == 0) {
                condition.await();
            }
            number--;
            co++;
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + "--------->" + number);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public int getIn() {
        return in;
    }

    public int getCo() {
        return co;
    }
}