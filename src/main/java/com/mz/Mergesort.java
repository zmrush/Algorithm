package com.mz;

/**
 * Created by mingzhu7 on 2016/8/22.
 */
public class Mergesort {
    static void mergeSort(int[] a){
        if(a==null || a.length<=1)
            return;
        int gap=1;
        while(gap<a.length){
            int scan_index=0;
            while(scan_index<a.length){
                int[] tmp=new int[scan_index+gap*2<=a.length?2*gap:(a.length-scan_index)];
                int s=scan_index;
                int s2=scan_index+gap;
                if(s2>=a.length)
                    break;
                int s3=0;
                while((s<scan_index+gap) && (s2<(scan_index+2*gap) && s2<a.length)){
                    if(a[s]<=a[s2]){
                        tmp[s3++]=a[s++];
                    }
                    else{
                        tmp[s3++]=a[s2++];
                    }
                }
                if(s==scan_index+gap){
                    while(s3<tmp.length)
                        tmp[s3++]=a[s2++];
                }
                else{
                    while(s3<tmp.length)
                        tmp[s3++]=a[s++];
                }
                for(int i=scan_index;i<(scan_index+2*gap) && i<a.length;i++){
                    a[i]=tmp[i-scan_index];
                }
                scan_index+=gap*2;
            }
            gap=gap<<1;
        }
    }
    public static void main(String[] args){
        int length=10;
        int[] a=new int[length];
        for(int i=0;i<length;i++){
            a[i]=(int)(10000*Math.random());
        }
        mergeSort(a);
        for(int i=0;i<length;i++){
            System.out.println(a[i]);
        }
    }
}
