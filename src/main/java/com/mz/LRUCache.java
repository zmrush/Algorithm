package com.mz;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

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
                hashMap.put(key, node);
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
    public static void main(String[] args){
    }
}
