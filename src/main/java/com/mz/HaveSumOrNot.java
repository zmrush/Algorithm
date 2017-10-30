package com.mz;

/**
 * Created by mingzhu7 on 2017/10/27.
 */
public class HaveSumOrNot {
    public static int binaryFind(int[] numbers,int target,int begin){
        int start=begin;
        int end=numbers.length-1;
        while(start<=end){
            int middle=(start+end)/2;
            if(numbers[middle]>target){
                end=middle-1;
            }else if(numbers[middle]<target){
                start=middle+1;
            }else{
                return middle;
            }
        }
        return -1;
    }
    public static void main(String[] args){
        //int[] numbers=new int[]{12,13,23,25,27,31,35,37,39,49};
        int[] numbers=new int[]{12,12,12,12,12,12,12,12,12,12};
        int target=24;
        int[] tmp=new int[numbers.length-1];
        for(int i=0;i<tmp.length;i++){
            tmp[i]=numbers[0]+numbers[i+1];
        }
        for(int i=0;i<(numbers.length-1);i++){
            int find=binaryFind(tmp,target,i);
            if(find>=0){
                System.out.println("found:"+i+","+(find+1));
            }
            target-=numbers[i+1]-numbers[i];
        }

    }
}
