package com.mz;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by mingzhu7 on 2017/6/1.
 */
public class LRUCache {
    class Node{
        Object elem;
        Node next;
        Node pre;
        Node(Object elem){
            this.elem=elem;
        }
    }
    private Node head=new Node(null);
    private Node tail=head;
    private int capacity;
    private ConcurrentHashMap<String,Node> hashMap=new ConcurrentHashMap<String,Node>();
    public LRUCache(int capacity){
        if(capacity<=0)
            throw new IllegalArgumentException("capacity must be greater than zero!");
        this.capacity=capacity;
    }
    public void put(String key,Object elem){
        if(hashMap.contains(key)){
            Node node=hashMap.get(key);
            if(node!=tail) {
                node.pre.next = node.next;
                node.next.pre=node.pre;
                tail.next = node;
                node.pre = tail;
                node.next = null;
                tail = node;
                //hashMap.put(key, node);
            }
            return;
        }
        if(hashMap.size()>=capacity){
            Node tmp=head.next;
            head.next=tmp.next;
            if(head.next!=null)
                head.next.pre=head;
            else{
                tail=head;
            }
            tmp.next=tmp;
            tmp.pre=tmp;
            tmp.elem=null;
            Node newNode=new Node(elem);
            newNode.pre=tail;
            tail.next=newNode;
            tail=newNode;
            hashMap.put(key,newNode);
        }
    }
    public Object get(String key){
        Node result;
        if((result=hashMap.get(key))==null){
            return null;
        }
        if(result!=tail) {
            result.pre.next = result.next;
            result.next.pre = result.pre;
            result.pre = tail;
            tail.next = result;
            tail = result;
        }
        return result.elem;
    }
    private final static int kk;
    static{
        kk=2;
    }
    public static final String A; // 常量A
    public static final String B; // 常量B
    static {
        A = "ab";
        B = "cd";
    }
    public static void main(String[] args){
        String s1 = new String("计算机");
        String s2 = s1.intern();
        String s3 = "计算机";
        System.out.println("s1 == s2? " + (s1 == s2));
        System.out.println("s3 == s2? " + (s3 == s2));


        String s = A + B;  // 将两个常量用+连接对s进行初始化
        String t = "abcd";
        if (s == t) {
            System.out.println("s等于t，它们是同一个对象");
        } else {
            System.out.println("s不等于t，它们不是同一个对象");
        }

        String str1 = "str";
        String str2 = "ing";

        String str3 = "str" + "ing";
        String str4 = str1 + str2;
        System.out.println(str3 == str4);//false

        String str5 = "string";
        System.out.println(str3 == str5);//true



        LinkedHashMap<String,Object> lrucache=new LinkedHashMap<String,Object>();
        lrucache.put("hello","world");
        Iterator<Map.Entry<String,Object>> iter=lrucache.entrySet().iterator();
        ConcurrentHashMap<String,Object> map=new ConcurrentHashMap<String, Object>();
        map.put("hello","world");
        ConcurrentHashMap<String,Object> cmap=new ConcurrentHashMap<String, Object>();
        ThreadLocal<String> trs=new ThreadLocal<String>();
        WeakHashMap<String,Object> weakHashMap;
        ReadWriteLock lock=new ReentrantReadWriteLock();

    }
}
