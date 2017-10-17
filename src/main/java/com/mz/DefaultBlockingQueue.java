package com.mz;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by mingzhu7 on 2016/12/7.
 */
public class DefaultBlockingQueue {
    private class Node{
        private Object elem;
        private Node next;
        Node(Object elem){
            this.elem=elem;
        }
        Node(Object elem,Node next){
            this.elem=elem;
            this.next=next;
        }
    }
    private volatile AtomicInteger asize=new AtomicInteger(0);
    private int capacity;
    private Node tail=new Node(null);
    private Node head=new Node(null,tail);
    //private Node tail=head;
    private ReentrantLock putLock=new ReentrantLock();
    private Condition putCondition=putLock.newCondition();
    private ReentrantLock takeLock=new ReentrantLock();
    private Condition takeCondition=takeLock.newCondition();
    public DefaultBlockingQueue(int capacity){
        if(capacity<1)
            throw new RuntimeException("capacity must be larger than zero");
        this.capacity=capacity;
    }
    public void put(Object elem) throws Exception{
        putLock.lock();
        int lastsize=0;
        try {
            while (asize.get() >= capacity) {
                putCondition.await();
            }
            tail.elem = elem;
            tail = tail.next = new Node(null);
            lastsize=asize.getAndIncrement();
            if(lastsize+1<capacity){
                putCondition.signal();
            }
        }finally {
            putLock.unlock();
            if(lastsize==0) {
                takeLock.lock();
                try {
                    takeCondition.signal();
                } finally {
                    takeLock.unlock();
                }
            }
        }

    }
    public Object get() throws Exception{
        takeLock.lock();
        int lastsize=0;
        try{
            while(asize.get()<=0){
                takeCondition.await();
            }
            Node tmp=head.next;
            head.next=tmp.next;
            tmp.next=tmp;
            lastsize=asize.getAndDecrement();
            if(lastsize-1>0){
                takeCondition.signal();
            }
            return tmp.elem;
        }finally {
            takeLock.unlock();
            if(lastsize==capacity){
                try{
                    putLock.lock();
                    putCondition.signal();
                }finally {
                    putLock.unlock();
                }
            }
        }
    }






//    public void put(Object elem) throws Exception{
//        putLock.lock();
//        int lastsize=-1;
//        try {
//            while (asize.get() >= capacity)
//                putCondition.await();
//            Node node=new Node(elem);
//            tail.next=node;
//            tail=node;
//            lastsize=asize.getAndDecrement();
//            if(lastsize<capacity-1)
//                putCondition.signal();
//        }finally {
//            putLock.unlock();
//            if(lastsize<=0) {
//                try {
//                    takeLock.lock();
//                    takeCondition.signal();
//                }finally {
//                    takeLock.unlock();
//                }
//            }
//
//        }
//    }
//    public Object get() throws Exception{
//        takeLock.lock();
//        int lastsize=-1;
//        try {
//            while (asize.get() <= 0)
//                takeCondition.await();
//            Node tmp=head.next;
//            head.next=tmp.next;
//            lastsize=asize.getAndDecrement();
//            Object o=tmp.elem;
//            tmp.next=tmp;
//            tmp.elem=null;
//            if(lastsize>1)
//                takeCondition.signal();
//            return o;
//        }finally{
//            takeLock.unlock();
//            if(lastsize>=capacity){
//                try{
//                    putLock.lock();
//                    putCondition.signal();
//                }finally{
//                    putLock.unlock();
//                }
//            }
//        }
//    }
//    public void put(Object elem) throws Exception{
//        putLock.lock();
//        int size=0;
//        try{
//            while(asize.get()==capacity){
//                putCondition.await();
//            }
//            Node node=new Node(elem);
//            tail.next=node;
//            tail=node;
//            size=asize.getAndIncrement();
//            //插入太快，很多线程都hang住了，然后又突然地被消费了很多，因此有可能需要通知下一个生产者
//            if(size<(capacity-1))
//                putCondition.notify();
//        }finally {
//            putLock.unlock();
//            if(size==0){
//                takeLock.lock();
//                try{
//                    takeCondition.notify();
//                }finally{
//                    takeLock.unlock();
//                }
//            }
//        }
//    }
//    public Object get() throws Exception{
//        takeLock.lock();
//        int size=0;
//        try{
//            while(asize.get()==0)
//                takeCondition.await();
//            Node node=head;
//            head=head.next;
//            size=asize.getAndDecrement();
//            //消费太快，导致消费的消费都hang住，然后突然又快速插入很多个，因此需要通知下一个消费者
//            if(size>1)
//                takeCondition.notify();
//            return node.elem;
//        }finally {
//            takeLock.unlock();
//            if (size == capacity) {
//                putLock.lock();
//                try {
//                    putCondition.notify();
//                } finally {
//                    putLock.unlock();
//                }
//            }
//        }
//    }
    public static void main(String[] args){
        LinkedBlockingQueue<Object> objes=new LinkedBlockingQueue<Object>();   //put可以中断，offer不可以中断，因为它有对应的限时操作
        ArrayBlockingQueue<Object> objes2=new ArrayBlockingQueue<Object>(23);  //arrayblockingqueue 读写使用同一把锁，因为他们会操作同一个数组，如果使用不同的线程，可能会出现每个线程的数组不一样（非volatile，即所谓内存屏障的原因http://stackoverflow.com/questions/11015571/arrayblockingqueue-uses-a-single-lock-for-insertion-and-removal-but-linkedblocki）
    }
}
