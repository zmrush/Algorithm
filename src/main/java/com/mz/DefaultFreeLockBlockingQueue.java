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
    private static Long headFieldOffset;
    private static Long tailFieldOffset;
    static{
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe=(Unsafe)field.get(null);
            Field nextField=Node.class.getDeclaredField("next");
            nextFieldOffset=unsafe.objectFieldOffset(nextField);
            Field headField=DefaultFreeLockBlockingQueue.class.getDeclaredField("head");
            headFieldOffset=unsafe.objectFieldOffset(headField);
            Field tailField=DefaultFreeLockBlockingQueue.class.getDeclaredField("tail");
            tailFieldOffset=unsafe.objectFieldOffset(tailField);
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
    private Node<Object> tail=new Node(null);
    private Node<Object> head=tail;
    public void put(Object value){
        Node<Object> tmp=new Node(value);
        while(true){
            if(size.get()>=capacity)
                throw new RuntimeException("queue is full");
            if(tail.next!=null)
                tail=tail.next;//我们需要防止成功的线程没有成功更新后面那个tail=tmp这个命令，但是这个多余的东西会极大的影响性能
            boolean right=unsafe.compareAndSwapObject(tail,nextFieldOffset,null,tmp);
            if(right) {
                tail=tmp;
                size.getAndIncrement();//最后我觉得这个必须要放在这个地方，因为take的时候需要用到next引用，以免指向null，没有成功指向真正的tail
                return;
            }
        }
    }
    public Object take(){
        while(true) {
            if (head.next!=null) {
                Node tmp=head;
                boolean right = unsafe.compareAndSwapObject(this,headFieldOffset, tmp, head.next);
                if (right) {
                    size.getAndDecrement();//我觉得在这个地方就可以进行减了,加快放的速度
                    tmp.next = tmp;//help gc
                    Object res=head.value;
                    head.value=null;
                    return res;
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
        System.out.println(queue.take());
    }
}
