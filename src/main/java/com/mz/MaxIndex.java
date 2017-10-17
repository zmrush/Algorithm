package com.mz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by mingzhu7 on 2017/10/13.
 */
public class MaxIndex {
    public static class Composite implements Comparable<Composite>{
        public int cursor;
        public int value;

        public int compareTo(Composite o) {
            if(this.value>o.value || (this.value==o.value && this.cursor>o.cursor))
                return 1;
            else return -1;
        }
    }
    public static void main(String[] args){
        int testNum;
        Scanner scanner=new Scanner(System.in);
        testNum=scanner.nextInt();
        int[] result=new int[testNum];
        for(int i=0;i<testNum;i++){
            int N;
            N=scanner.nextInt();
            ArrayList<Composite> composites=new ArrayList<Composite>(N);
            for(int j=0;j<N;j++){
                composites.add(new Composite());
                composites.get(j).cursor=j;
                composites.get(j).value=scanner.nextInt();
            }
            Collections.sort(composites);
            int maxgap=0;
            int mincursor=composites.get(0).cursor;
            for(int j=1;j<composites.size();j++){
                if((composites.get(j).cursor-mincursor)>maxgap){
                    maxgap=composites.get(j).cursor-mincursor;
                }
                else if(composites.get(j).cursor<mincursor)
                    mincursor=composites.get(j).cursor;
            }
//            int[] left=new int[101];
//            int[] right=new int[101];
//            for(int j=0;j<N;j++){
//                int tmp=scanner.nextInt();
//                if(left[tmp]==0)
//                    right[tmp]=left[tmp]=j+1;
//                else
//                    right[tmp]=j+1;
//            }
//            int maxgap=0;
//            for(int j=1;j<=100;j++){
//                if(right[j]!=0){
//                    for(int k=j;k>=0;k--){
//                        if(left[k]!=0){
//                            maxgap=(right[j]-left[k])>maxgap?(right[j]-left[k]):maxgap;
//                        }
//                    }
//                }
//            }
            result[i]=maxgap;
        }
        for(int i=0;i<testNum;i++){
            System.out.println(result[i]);
        }
    }
}
