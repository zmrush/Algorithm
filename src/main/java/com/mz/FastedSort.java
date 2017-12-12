package com.mz;

/**
 * Created by mingzhu7 on 2016/11/24.
 */
public class FastedSort {
//    public int sort(int[] in){
//        int index=0;
//        while(index<in.length){
//            if(in[index]==index){
//                index++;continue;
//            }else{
//                if(in[in[index]]==in[index])
//                    return in[index];
//                int tmp=in[in[index]];
//                in[in[index]]=in[index];
//                in[index]=tmp;
//            }
//        }
//        return -1;
//    }
    public int sort(int[] in){
        int i=0;
        while(i<in.length){
            if(in[i]==i)
                i++;
            else{
                if(in[in[i]]==in[i])
                    return in[i];
                else{
                    int tmp=in[in[i]];
                    in[in[i]]=in[i];
                    in[i]=tmp;
                }
            }
        }
        return -1;
    }
    public static void main(String[] args){
        int[] a={9,1,2,5,3,4,6,7,8,0};
        FastedSort fastedSort=new FastedSort();
        int x=fastedSort.sort(a);
        System.out.println(x);

    }
}
