package com.zxx.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
 *
 * */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> System.out.println("龙珠召唤成功"));
        for (int i = 0; i < 7; i++) {
            final int finalI = i;
            new Thread(() -> {
                System.out.println("已集齐第" + (finalI + 1) + "颗龙珠");
                try {
                    cyclicBarrier.await();//等待所有parties已经在这个障碍上调用了await 。
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
