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

    //well,we use manamatch algorithm
//    public static String getMaxRecurrentText2(String s){
//        StringBuilder sb=new StringBuilder(s.length()*2+1);
//        sb.append("#");
//        for(int i=0;i<s.length();i++){
//            sb.append(s.charAt(i));
//            sb.append("#");
//        }
//        int[] p=new int[sb.length()];
//        p[0]=0;
//        int R=0;
//        int center=0;
//        int maxR=0;
//        int maxRIndex=0;
//        for(int i=1;i<sb.length();i++){
//            if(i>R){
//                center=i;
//                int count=0;
//                while((i-count-1)>=0  && (i+count+1)<sb.length() && sb.charAt(i+count+1)==sb.charAt(i-count-1))
//                    count++;
//                R=i+count;
//                p[i]=count;
//                if(p[i]>maxR) {
//                    maxR = p[i];
//                    maxRIndex=i;
//                }
//            }else{
//                if((p[2*center-i]+(i-center))<(R-center)){
//                    p[i]=p[2*center-i];
//                }else if((p[2*center-i]+(i-center))==(R-center)){
//                    int count=0;
//                    while((2*i-R-count-1)>=0 && (R+count+1)<sb.length() &&sb.charAt(2*i-R-count-1)==sb.charAt(R+count+1)){
//                        count++;
//                    }
//                    center=i;
//                    R=R+count;
//                    p[i]=R-i;
//                    if(p[i]>maxR) {
//                        maxR = p[i];
//                        maxRIndex=i;
//                    }
//                }else{
//                    p[i]=R-i;
//                }
//            }
//
//        }
//        StringBuilder result=new StringBuilder(maxR);
//        for(int i=maxRIndex-maxR+1;i<maxRIndex+maxR;i=i+2){
//            result.append(sb.charAt(i));
//        }
//        return result.toString();
//    }








































    public static String getMaxRecurrentText2(String s) {
        StringBuilder sb = new StringBuilder(s.length() * 2 + 1);
        sb.append("#");
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i));
            sb.append("#");
        }
        int maxr=0;
        int maxcenter=0;
        int r=0;
        int center=0;
        int index=1;
        int[] p=new int[sb.length()];
        p[0]=0;
        while(r<sb.length()){
            while(r <sb.length() && index<=r){
                if(p[2*center-index]>(r-index)){
                    p[index]=r-index;
                }else if(p[2*center-index]<(r-index)){
                    p[index]=p[2*center-index];
                }else{
                    while((r+1)<sb.length() && (2*index-r-1)>=0 && sb.charAt(r+1)==sb.charAt(2*index-r-1))
                        r+=1;
                    center=index;
                    p[index]=r-index;
                    if(p[index]>maxr) {
                        maxr = p[index];
                        maxcenter=index;
                    }
                }
                index++;
            }
            if(index>r && index<sb.length()){
                r=index;
                while((r+1)<sb.length() && (2*index-r-1)>=0 && sb.charAt(r+1)==sb.charAt(2*index-r-1))
                    r+=1;
                center=index;
                p[index]=r-index;
                if(p[index]>maxr) {
                    maxr = p[index];
                    maxcenter=index;
                }
                index++;
            }
            if(index>=sb.length())
                break;
        }
        StringBuilder result=new StringBuilder();
        for(int i=maxcenter-maxr+1;i<maxcenter+maxr;i+=2){
            result.append(sb.charAt(i));
        }
        return result.toString();
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
                results.add(getMaxRecurrentText2(s));
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
