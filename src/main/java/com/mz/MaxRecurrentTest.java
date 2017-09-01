package com.mz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mingzhu7 on 2017/9/1.
 */
public class MaxRecurrentTest {
    public static int getMaxRecurrentText(String s){
        if(s==null || s.length()<=0)
            return 0;
        int maxLength=1;
        int maxIndex=s.length()-1;
        int length=s.length();
        List<Integer> lengthList=new ArrayList<Integer>();
        lengthList.add(1);
        lengthList.add(0);
        int index=length-2;
        while(index>=0){
            Iterator<Integer> iter=lengthList.iterator();
            int i=0;
            char cur=s.charAt(index);
            while(iter.hasNext()){
                int next=iter.next();
                if((index+next+1)<length && cur==s.charAt(index+next+1)){
                    lengthList.set(i,next+2);
                    if((next+2)>maxLength){
                        maxLength=next+2;
                        maxIndex=index;
                    }
                }else{
                    iter.remove();
                    i--;
                }
                i++;
            }
            index--;
            lengthList.add(1);
            lengthList.add(0);
        }
        System.out.println("pos:"+maxIndex+" length:"+maxLength);
        return maxLength;
    }
    public static void main(String[] args) throws Exception{
        String input=new BufferedReader(new InputStreamReader(System.in)).readLine();
        getMaxRecurrentText(input);
    }
}
