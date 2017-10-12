package com.mz;

import java.util.Scanner;

/**
 * Created by mingzhu7 on 2017/10/12.
 */
public class SimpleFraction {
    public static String getResult(int N,int D){
        StringBuilder result=new StringBuilder();
        int[] index=new int[D];
        result.append(N/D);
        if(N-D*(N/D)==0)
            return result.toString();
        result.append(".");
        N=N-D*(N/D);
        boolean isFind=false;
        while(N!=0 && index[N]==0){
            index[N]=result.length();
            N=N*10;
            while(N<D){
                result.append("0");
                if(index[N]!=0){ // 10 / 27 let me think it is important
                    isFind=true;
                    break;
                }
                index[N]=result.length();  // 3 / 90 let me think is is important
                N=N*10;
            }
            if(isFind==true)
                break;
            result.append(N/D);
            N=N-D*(N/D);
        }
        if(N==0)
            return result.toString();
        else{
            hi:
            result.insert(index[N],'(');
            result.append(")");
            return result.toString();
        }
    }
    public static void main(String[] args){
        int testNum;
        Scanner scanner=new Scanner(System.in);
        testNum=scanner.nextInt();
        String[] result=new String[testNum];
        int N;
        int D;
        for(int i=0;i<testNum;i++){
            N=scanner.nextInt();
            D=scanner.nextInt();
            result[i]=getResult(N,D);
        }
        for(int i=0;i<testNum;i++){
            System.out.println(result[i]);
        }
    }
}
