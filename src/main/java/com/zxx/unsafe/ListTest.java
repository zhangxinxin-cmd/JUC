package com.zxx.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class ListTest {
    public static void main(String[] args) throws InterruptedException {
        //CopyOnWrite写入时复制 计算机程序设计领域的一种优化策略
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
        TimeUnit.SECONDS.sleep(2);
        System.out.println("===============");
        System.out.println(list);
    }
}
