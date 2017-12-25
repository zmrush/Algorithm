package com.mz;

import sun.java2d.SurfaceDataProxy;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by mingzhu7 on 2017/12/25.
 */
public class OrderGenerator {
    private long machineId=0x000;
    private static volatile long lastTime=System.currentTimeMillis();
    private static final long start=new Date(2014-1900,0,1).getTime();
    private static AtomicLong c=new AtomicLong(0);
    private static final int maxRetryTimes=3;
    private static final long baseCount=2;
    static private final long maxCounter=0x1<<(baseCount*4);
    private static final long idOffset=baseCount*4*2;
    public OrderGenerator(String machineIdt){
        machineId = Long.valueOf(machineIdt);
        if( machineId>=maxCounter || machineId<0 )
            throw new RuntimeException("OrderGenerator Initializing Error:machine id is too large or smaller than zero!");
        machineId = machineId << (baseCount*4);//详见下面的说明
    }
    public long generateID() {
        long id=machineId;
        int retryTimes=0;
        start:
        for(;;) {
            long now = System.currentTimeMillis();
            if (now == lastTime) {
                long t = c.incrementAndGet();
                if (t >= maxCounter) {
                    if(retryTimes>maxRetryTimes)
                        throw new RuntimeException("cannnot get a suitable client orderfix id");
                    retryTimes++;
                    LockSupport.parkNanos(1000000);//等待1ms
                    continue start;
                }
                id += t;
            } else {
                c.set(0);
                lastTime = now;
            }
            id += (now - start) << idOffset;//没有做位运算
            break;
        }
        return id;
    }
    public static void main(String[] args) throws Exception{
        long enddate=new Date(2054-1900,0,1).getTime();
        System.out.println(enddate);
        System.out.println(start);
        System.out.println(enddate-start);
        OrderGenerator orderGenerator=new OrderGenerator("1");
        final int size=1024;
        long[] ids=new long[size];
        final int baseSize=10;
        ExecutorService es= Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch=new CountDownLatch(baseSize);
        for(int i=0;i<baseSize;i++) {
            final int j=i;
            es.submit(new Runnable() {
                @Override
                public void run() {
                    ids[j]=orderGenerator.generateID();
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("end");
    }
}
