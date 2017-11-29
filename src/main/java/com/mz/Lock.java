package com.mz;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by mingzhu7 on 2016/12/7.
 */
public class Lock {
    public static synchronized void test(){
        try {
            System.out.println("enter:"+Thread.currentThread().getName());
            Lock.class.wait();
        }catch (Exception e){
            e.printStackTrace();

        }
    }
    public static void main(String[] args) throws Exception{

        int a=Integer.MAX_VALUE;
        System.out.println(a);
        Thread A=new Thread(new Runnable() {
            public void run() {
                test();
            }
        });
        A.start();
        Thread B=new Thread(new Runnable() {
            public void run() {
                test();
            }
        });
        B.start();
        Thread.sleep(1000);
        System.out.println(A.getName()+":"+A.getState());
        System.out.println(B.getName()+":"+B.getState());
        if(A.getState()==Thread.State.WAITING){
            //A.interrupt();
        }
        if(B.getState()==Thread.State.WAITING){
            //B.interrupt();
        }
        System.out.println(A.getName()+":"+A.getState());
        System.out.println(B.getName()+":"+B.getState());
        ReentrantLock putLock=new ReentrantLock();
        ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
        CopyOnWriteArraySet<String> lsls=null;
    }
}
