package com.mz;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by mingzhu7 on 2018/2/5.
 */
public class TreeMapTest {
    public static void main(String[] args){
        TreeMap<Integer,Integer> treeMap=new TreeMap<>();
        treeMap.put(1,2);
        NavigableMap<Integer,Integer> navigableMap=treeMap.subMap(0,true,2,true);
        Map.Entry<Integer,Integer> firstEntry=navigableMap.firstEntry();
        Map.Entry<Integer,Integer> endEntry=navigableMap.lastEntry();
        System.out.println("hello world");
    }
}
