package com.mz;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mingzhu7 on 2016/8/22.
 * leetcode 381
 */
public class RandomSet {
    public static class RandomizedSet{
        private Map<Integer,Integer> index=new HashMap<Integer,Integer>();
        private Map<Integer,Integer> value=new HashMap<Integer,Integer>();
        private AtomicInteger count=new AtomicInteger(0);
        public RandomizedSet() {

        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if(value.containsKey(val))
                return false;
            else {
                int c=count.getAndIncrement();
                index.put(c,val);
                value.put(val,c);
                return true;
            }
        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if(!value.containsKey(val))
                return false;
            else {
                int oldindex=value.get(val);
                value.remove(val);
                //---------------------------------------------------------------------
                int x=count.decrementAndGet();
                int newval=index.get(x);
                value.put(newval,oldindex);
                index.put(oldindex,newval);
                //---------------------------------------------------------------------
                index.remove(x);
                return true;
            }
        }

        /** Get a random element from the set. */
        public int getRandom() {
            int size=count.get();
            if(size<=0)
                throw new RuntimeException("no elements");
            int randomindex=(int)(size*Math.random());
            return index.get(randomindex);
        }
    }
    public static void main(String[] args){
        RandomizedSet randomSet=new RandomizedSet();
        // Inserts 1 to the set. Returns true as 1 was inserted successfully.
        boolean result=randomSet.insert(1);

        int result2=randomSet.getRandom();
// Returns false as 2 does not exist in the set.
        result=randomSet.remove(2);

// Inserts 2 to the set, returns true. Set now contains [1,2].
        result=randomSet.insert(2);

// getRandom should return either 1 or 2 randomly.
       result2=randomSet.getRandom();

// Removes 1 from the set, returns true. Set now contains [2].
        result=randomSet.remove(1);

// 2 was already in the set, so return false.
        result=randomSet.insert(2);

// Since 1 is the only number in the set, getRandom always return 1.
        result2=randomSet.getRandom();

        result=randomSet.insert(3);
        result=randomSet.insert(5);
        result=randomSet.insert(3);
        result2=randomSet.getRandom();
        result2=randomSet.getRandom();
        result2=randomSet.getRandom();
        System.out.println("end");
    }


    public static class RandomizedSet2{
        private ConcurrentSkipListSet<Integer> values=new ConcurrentSkipListSet<Integer>();
        Iterator<Integer> iterator=values.iterator();
        public RandomizedSet2() {

        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if(values.contains(val))
                return false;
            else{
                values.add(val);
                return true;
            }
        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if(!values.contains(val))
                return false;
            else{
                if(iterator.hasNext()) {
                    int vals=iterator.next();
                    if(vals==val){
                        if(val==values.last())
                            iterator=values.iterator();
                        else
                            iterator.next();
                    }
                }
                values.remove(val);
                return true;
            }
        }

        /** Get a random element from the set. */
        public int getRandom() {
            if(iterator.hasNext())
                return iterator.next();
            else{
                if(values.size()>0){
                    iterator=values.iterator();
                    return iterator.next();
                }else{
                    throw new RuntimeException("has no elements");
                }
            }
        }
    }
}
