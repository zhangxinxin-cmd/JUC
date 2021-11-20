package com.zxx.unsafe;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

public class SetTEST{

    public static void main(String[] args) throws InterruptedException {
        /*//会产生异常ConcurrentModificationException
        Set<String> hashSet=new HashSet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                hashSet.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(hashSet);
            },String.valueOf(i)).start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println(hashSet);*/
        Set<String> hashSet=new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                hashSet.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(hashSet);
            },String.valueOf(i)).start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("==================");
        System.out.println(hashSet);
    }
}
