package com.zxx.Semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
/*
* 一个计数信号量。 在概念上，信号量维持一组许可证。
* 如果有必要，每个acquire()都会阻塞，直到许可证可用，然后才能使用它。 每个release()添加许可证，潜在地释放阻塞获取方。
* 但是，没有使用实际的许可证对象; Semaphore只保留可用数量的计数，并相应地执行。
信号量通常用于限制线程数，而不是访问某些（物理或逻辑）资源。
* //作用：多个共享资源互斥的使用!并发限流
* */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到了车位");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + "离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();//释放
                }
            }, String.valueOf(i + 1)).start();
        }
    }
}
