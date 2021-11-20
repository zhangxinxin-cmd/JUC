package com.zxx.unsafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class MapTest {
    public static void main(String[] args) throws InterruptedException {
//        Map<String, String> map = new HashMap<>();
        Map<String,String> map=new ConcurrentHashMap<>(50);
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
        TimeUnit.SECONDS.sleep(2);
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry);
        }
    }

}
