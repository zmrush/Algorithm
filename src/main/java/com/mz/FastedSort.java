package com.mz;

/**
 * Created by mingzhu7 on 2016/11/24.
 */
public class FastedSort {
    public int sort(int[] in){
        int index=0;
        while(index<in.length){
            if(in[index]==index){
                index++;continue;
            }else{
                if(in[in[index]]==in[index])
                    return in[index];
                int tmp=in[in[index]];
                in[in[index]]=in[index];
                in[index]=tmp;
            }
        }
        return -1;
    }
    public static void main(String[] args){
        int[] a={9,1,2,5,3,4,6,7,9,0,9};
        FastedSort fastedSort=new FastedSort();
        int x=fastedSort.sort(a);
        System.out.println(x);

    }
}
