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
    public static String getMaxRecurrentText2(String s){
        StringBuilder buf=new StringBuilder(2*s.length()+1);
        for(int i=0;i<s.length();i++){
            buf.append('#');
            buf.append(s.charAt(i));
        }
        buf.append('#');
        int maxR=0;
        int maxRC=0;
        int center=0;
        int r=0;
        int[] p=new int[buf.length()];
        p[0]=0;
        int i=1;
        while(r<buf.length()){
            if(i>r){
                r=r+1;
                if(r>=buf.length())
                    break;
                while((r+1)<buf.length() && (2*i-r-1)>=0 && buf.charAt(r+1)==buf.charAt(2*i-r-1))
                    r++;
                center=i;p[i]=r-i;
                if((r-i)>maxR){
                    maxRC=i;
                    maxR=r-i;
                }
            }else{
                if(p[2*center-i]<(r-i)){
                    p[i]=p[2*center-i];
                }else if(p[2*center-i]==(r-i)){
                    while((r+1)<buf.length() && (2*i-r-1)>=0 && buf.charAt(r+1)==buf.charAt(2*i-r-1))
                        r++;
                    center=i;p[i]=r-i;
                    if((r-i)>maxR){
                        maxRC=i;
                        maxR=r-i;
                    }
                }else{
                    p[i]=r-i;
                }
            }
            i++;
        }
        StringBuffer result=new StringBuffer(maxR);
        for(i=(maxRC-maxR+1);i<(maxRC+maxR);i=i+2){
            result.append(buf.charAt(i));
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
            e.printStackTrace();
        }
        for(i=0;i<caseNum;i++){
            System.out.println(results.get(i));
        }
    }
}
