package com.zxx.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableImplements callableImplements=new CallableImplements();
        FutureTask<Integer> futureTask=new FutureTask<>(callableImplements);
        new Thread(futureTask).start();
        Integer i = futureTask.get();
        System.out.println(i);
    }
}

class CallableImplements implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(1<<10);
        return 1 << 10;
    }
}