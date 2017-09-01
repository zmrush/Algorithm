package com.mz;

import java.util.concurrent.*;

/**
 * Created by mingzhu7 on 2017/8/15.
 */
public class DefaultThreadPoolExecutor {
    public static void main(String[] args) throws Exception{
        TransferQueue<Object> transferQueue=null;
        final SynchronousQueue<Object> queue=new SynchronousQueue<Object>();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Object obj=queue.poll(10000, TimeUnit.MILLISECONDS);
                    System.out.println(obj);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Object obj=queue.poll(10000, TimeUnit.MILLISECONDS);
                    System.out.println(obj);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1000);
        queue.offer(new Object());
        queue.offer(new Object());
        Thread.sleep(5000);
        //
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(1,2,60, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        threadPoolExecutor.execute(new Runnable() {
            public void run(){
                System.out.println(Thread.currentThread().getId());
                System.out.println("test1");
                try {
                    Thread.currentThread().sleep(1000);
                }catch (Exception e){

                }
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            public void run(){
                System.out.println(Thread.currentThread().getId());
                System.out.println("test2");
                try{
                    Thread.currentThread().sleep(2);
                }catch (Exception e){

                }
            }
        });
        Thread.currentThread().sleep(20000);
        threadPoolExecutor.execute(new Runnable() {
            public void run(){
                System.out.println(Thread.currentThread().getId());
                System.out.println("test3");
                try{
                    Thread.currentThread().sleep(80000);
                }catch (Exception e){

                }
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            public void run(){
                System.out.println(Thread.currentThread().getId());
                System.out.println("test4");
                try{
                    Thread.currentThread().sleep(80000);
                }catch (Exception e){

                }
            }
        });
        Thread.currentThread().sleep(1000000);
    }
}
