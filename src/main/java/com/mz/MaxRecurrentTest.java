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
    public static String getMaxRecurrentText(String s){
        if(s==null || s.length()<=0)
            return "";
        int maxLength=1;
        int maxIndex=0;
        int length=s.length();
        List<Integer> lengthList=new ArrayList<Integer>();
        lengthList.add(1);
        lengthList.add(0);
        int index=1;
        while(index<length){
            Iterator<Integer> iter=lengthList.iterator();
            int i=0;
            char cur=s.charAt(index);
            while(iter.hasNext()){
                int next=iter.next();
                if((index-next-1)>=0 && cur==s.charAt(index-next-1)){
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
            index++;
            lengthList.add(1);
            lengthList.add(0);
        }
        return s.substring(maxIndex-maxLength+1,maxIndex+1);
    }
    public static void main(String[] args){
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        Integer caseNum=0;
        try{
            caseNum=Integer.valueOf(br.readLine());
        }catch (Exception e){
            return;
        }
        List<String> results=new ArrayList<String>(caseNum);
        int i=0;
        String s=null;
        try {
            while (i < caseNum) {
                s = br.readLine();
                results.add(getMaxRecurrentText(s));
                i++;
            }
        }catch (Exception e){
            return;
        }
        for(i=0;i<caseNum;i++){
            System.out.println(results.get(i));
        }
    }
}
