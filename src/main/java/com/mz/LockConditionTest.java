package com.mz;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by mingzhu7 on 2016/11/18.
 */
public class LockConditionTest {
    private static ReentrantLock putLock=new ReentrantLock();
    private static Condition notFull=putLock.newCondition();
    private static ReentrantLock takeLock=new ReentrantLock();
    private static Condition notEmpty=takeLock.newCondition();
    public static void main(String[] args) throws Exception{
//        new Thread(new Runnable(){
//            public void run(){
//                try {
//                    get();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        Thread.currentThread().sleep(5000);
//        new Thread(new Runnable(){
//            public void run(){
//                try{
//                    put();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        new Thread(new Runnable(){
            public void run(){
                try{
                    take();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.currentThread().sleep(1000);
        new Thread(new Runnable(){
            public void run(){
                try{
                    take2();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void  put() throws Exception{
        putLock.lock();
        try {
            notFull.signal();
        }finally{
            Thread.currentThread().sleep(5000);
            System.out.println("put end");
            Thread.currentThread().sleep(5000);
            putLock.unlock();
        }
    }
    private static void get() throws Exception{
        putLock.lock();
        try{
            notFull.await();
            System.out.println("get end");
        }finally{
            putLock.unlock();
        }

    }
    private static void take() throws Exception{
        takeLock.lock();
        System.out.println(Thread.currentThread().toString());
        try{
            for(int i=0;i<1000000000;i++){
                int sum=0;
                for(int j=0;j<1000000000;j++){
                    sum+=2;
                }
                System.out.println("finish one");
            }
        }finally{
            takeLock.unlock();
        }
    }
    private static void take2() throws Exception{
        takeLock.lock();
        try{
            for(int i=0;i<10000000;i++){
                int sum=0;
                for(int j=0;j<10000000;j++){
                    sum+=2;
                }
            }
        }finally{
            takeLock.unlock();
        }
    }
}
