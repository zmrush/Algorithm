package com.mz;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by mingzhu7 on 2018/2/5.
 */
public class TreeMapTest {
    public static class Segment{
        public int start;
        public int end;
        public Segment(int start,int end){
            this.start=start;this.end=end;
        }
    }
    public static class SegmentComparetor implements Comparator<Segment>{

        @Override
        public int compare(Segment o1, Segment o2) {
            if(o1.start>o2.end)
                return 1;
            else if(o1.end<o2.start)
                return -1;
            else
                return 0;
        }
    }
    public static void main(String[] args){
        //我们必须使用上面的这个comparetor，才能保证firstentry和lastentry不会为空，如果存在的话
        TreeMap<Segment,Integer> segmentIntegerTreeMap=new TreeMap<>(new SegmentComparetor());
        segmentIntegerTreeMap.put(new Segment(1498,1498),193);
        //segmentIntegerTreeMap.put(new Segment(1507,1512),208);
        NavigableMap<Segment,Integer> navigableMap1=segmentIntegerTreeMap.subMap(new Segment(1498,1498),true,new Segment(1499,1499),true);
        Map.Entry<Segment,Integer> frontEntry=navigableMap1.firstEntry();
        Map.Entry<Segment,Integer> lastEntry=navigableMap1.lastEntry();
        navigableMap1.clear();
        TreeMap<Integer,Integer> treeMap=new TreeMap<>();
        treeMap.put(1,2);
        NavigableMap<Integer,Integer> navigableMap=treeMap.subMap(0,true,2,true);
        Map.Entry<Integer,Integer> firstEntry=navigableMap.firstEntry();
        Map.Entry<Integer,Integer> endEntry=navigableMap.lastEntry();
        System.out.println("hello world");
    }
}
