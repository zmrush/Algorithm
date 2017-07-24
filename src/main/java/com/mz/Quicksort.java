package com.mz;

/**
 * Created by mingzhu7 on 2016/8/22.
 */
public class Quicksort {
//    public static void sort(int[] a,int st,int ed){
//        if(st>=ed)
//            return;
//        int start=st;
//        int end=ed;
//        int middle=a[st];
//        while(start<end){
//            while(start<end&&a[end]>=middle)
//                end--;
//            a[start]=a[end];
//            while(start<end && a[start]<=middle)
//                start++;
//            a[end]=a[start];
//        }
//        a[end]=middle;
//        sort(a,st,end-1);
//        sort(a,end+1,ed);
//    }
    public static void sort(int[] a,int st,int ed){
        if(st>=ed)
            return;
        int middle=a[st];
        int left=st;
        int right=ed;
        while(left<right){
            while(left<right && a[right]>=middle)
                right--;
            a[left]=a[right];
            while(left<right && a[left]<=middle)
                left++;
            a[right]=a[left];
        }
        a[left]=middle;
        sort(a,st,left-1);
        sort(a,left+1,ed);
    }
    public static void main(String[] args) throws Exception{
        int length=10;
        int[] test=new int[length];
        for(int i=0;i<length;i++){
            test[i]=(int)(10000*Math.random());
        }
        sort(test,0,9);
        for(int i=0;i<length;i++){
            System.out.println(test[i]);
        }
        System.in.read();
    }
}
