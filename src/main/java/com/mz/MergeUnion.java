package com.mz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mingzhu7 on 2016/11/24.
 */
public class MergeUnion {
    public static class Intersect{
        public int start;
        public int end;
        public Intersect(int start,int end){
            this.start=start;
            this.end=end;
        }
    }
//    public static void sortInternal(Intersect[] intersects,int start,int end){
//        if(start>=end)
//            return;
//        int middle=intersects[start].start;
//        Intersect middleIntersect=intersects[start];
//        int sindex=start;
//        int eindex=end;
//        while(sindex<eindex){
//            while(sindex<eindex && intersects[eindex].start>=middle)
//                eindex--;
//            intersects[sindex]=intersects[eindex];
//            while(sindex<eindex && intersects[sindex].start<=middle)
//                sindex++;
//            intersects[eindex]=intersects[sindex];
//        }
//        intersects[sindex]=middleIntersect;
//        sortInternal(intersects,start,sindex-1);
//        sortInternal(intersects,sindex+1,end);
//    }
    public static void sortIntersects(Intersect[] intersects){
    }
//    public static List<Intersect> unionIntersects(Intersect[] intersects){
//        List<Intersect> init= Arrays.asList(intersects);
//        List<Intersect> result=new ArrayList<Intersect>();
//        for(int i=0;i<init.size();i++){
//            Intersect tmp=init.get(i);
//            if(result.size()<=0) {
//                result.add(tmp);
//                continue;
//            }
//            Intersect last=result.get(result.size()-1);
//            if(tmp.start>last.end){
//                result.add(tmp);
//                continue;
//            }else if(tmp.end<=last.end){
//                continue;
//            }else{
//                last.end=tmp.end;
//            }
//
//        }
//        return result;
//    }
//    public static List<Intersect> unionIntersects(Intersect[] input){
//        List<Intersect> results=new ArrayList<Intersect>();
//        if(input==null || input.length==0)
//            return results;
//        sortInternal(input,0,input.length-1);
//        results.add(input[0]);
//        int i=1;
//        int length=input.length;
//        while(i<length){
//            Intersect pos=results.get(results.size()-1);
//            Intersect now=input[i++];
//            if(now.start>pos.end){
//                results.add(now);
//            }else if(now.end<=pos.end){
//
//            }else{
//                pos.end=now.end;
//            }
//        }
//        return results;
//    }
    public static void sortInternal(Intersect[] input,int start,int end){
        if(end<=start)
            return;
        Intersect middle=input[start];
        int left=start;
        int right=end;
        while(left<right){
            while(left<right && input[right].start>=middle.start)
                right--;
            input[left]=input[right];
            while(left<right && input[left].start<=middle.start){
                left++;
            }
            input[right]=input[left];
        }
        input[left]=middle;
        sortInternal(input,start,left-1);
        sortInternal(input,left+1,end);
    }
    public static List<Intersect> unionIntersects(Intersect[] input){
        if(input==null || input.length<=0){
            return new ArrayList<Intersect>();
        }
        sortInternal(input,0,input.length-1);
        List<Intersect> intersects=new ArrayList<Intersect>();
        int index=1;
        intersects.add(input[0]);
        while(index<input.length){
            Intersect cur=input[index];
            Intersect last=intersects.get(intersects.size()-1);
            if(cur.start>last.end){
                intersects.add(cur);
            }else if(cur.end>last.end){
                last.end=cur.end;
            }
            index++;
        }
        return intersects;
    }
    public static void main(String[] args){
        Intersect[] intersects={new Intersect(3,4),new Intersect(2,3),new Intersect(5,8),new Intersect(5,7)};
        List<Intersect> results=unionIntersects(intersects);
        System.out.println("end");


    }

}
