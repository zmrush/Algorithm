package com.mz;

import java.io.FileInputStream;
import java.net.URL;
import java.security.Timestamp;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regrex {
    public static void main(String[] args) throws Exception{
//        while(true){
//            Thread.sleep(1);
//        }
        CyclicBarrier cyclicBarrier=new CyclicBarrier(2);
        Semaphore semaphore=new Semaphore(2);
//        Thread thread=new Thread(new Runnable() {
//            public void run() {
//                System.out.println("park ");
//                LockSupport.park();
//                System.out.println(Thread.interrupted());
//            }
//        });
//        thread.start();
//        Thread.sleep(2000);
//        thread.interrupt();
//        String s=(String)null;
//        java.sql.Timestamp timestamp=new java.sql.Timestamp(1511057814138L);
//        URL url=new URL(new URL("http://news.xinhuanet.com/fortune/2017-11/18/c_129744086.htm"),"/robots.txt");
//        Pattern pattern= Pattern.compile("^http://tech.sina.com.cn/[a-z]+/[0-9\\-]+/[^/]+$");
//        Boolean find=pattern.matcher("http://tech.sina.com.cn/it/2017-11-17/doc-ifynwxum2949160.shtml").find();
//        System.out.println(find);
    }
} 