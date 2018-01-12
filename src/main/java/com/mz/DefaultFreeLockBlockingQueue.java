package com.mz;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by mingzhu7 on 2018/1/12.
 */
public class DefaultFreeLockBlockingQueue {
    private class Node<T>{
        T value;
        Node<T> next;
        Node(T val){
            this.value=val;
        }
    }
    private static Unsafe unsafe;
    private static Long nextFieldOffset;
    static{
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe=(Unsafe)field.get(null);
            Field nextField=Node.class.getDeclaredField("next");
            nextFieldOffset=unsafe.objectFieldOffset(nextField);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public DefaultFreeLockBlockingQueue(long capacity) throws IllegalArgumentException{
        if(capacity<0)
            throw new IllegalArgumentException("capacity must be larger than zero");
        this.capacity=capacity;
    }
    private AtomicLong size=new AtomicLong(0);
    private Long capacity;
    private Node<Object> head=new Node(null);
    private Node<Object> tail=head;
    public void put(Object value){
        Node<Object> tmp=new Node(value);
        while(true){
            if(size.get()>capacity)
                throw new RuntimeException("queue is full");
            boolean right=unsafe.compareAndSwapObject(tail,nextFieldOffset,null,tmp);
            if(right) {
                tail = tmp;
                size.getAndIncrement();
                return;
            }
        }
    }
    public Object take(){
        while(true) {
            Node tmp=head.next;
            if (tmp != null) {
                boolean right = unsafe.compareAndSwapObject(head, nextFieldOffset, tmp, tmp.next);
                if (right) {
                    size.getAndDecrement();
                    tmp.next = tmp;//help gc
                    if(tmp==tail)
                        tail=head;//queue is empty and tail redirect to head
                    return tmp.value;
                }
            }else{
                throw new RuntimeException("empty queue");
            }
        }
    }
    public static void main(String[] args){
        DefaultFreeLockBlockingQueue queue=new DefaultFreeLockBlockingQueue(5);
        queue.put("2");
        queue.put("3");
        System.out.println(queue.take());
        System.out.println(queue.take());
    }
}
