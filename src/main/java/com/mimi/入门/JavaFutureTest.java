package com.mimi.入门;

import java.util.concurrent.*;

/**
 * create by gary 2019/12/11
 * 技术交流请加QQ:498982703
 */
public class JavaFutureTest {

    static class  ThreadDemo implements Runnable{
        @Override
        public void run() {
            System.out.println("run...");
        }
    }


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw new NullPointerException();
            }
        });


        try {
            String result = future.get();
            System.out.println(result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("null exception");
        }


    }
}
