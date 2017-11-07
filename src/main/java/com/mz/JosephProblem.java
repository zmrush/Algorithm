package com.mz;

import java.util.Scanner;

/**
 * Created by mingzhu7 on 2017/11/1.
 */
public class JosephProblem {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        int k=scanner.nextInt();
        int f=0;
        for(int i=2;i<=n;i++){
            if(f<=(i-1-k%i))
                f=f+k%i;
            else
                f=f-(i-1-k%i);
        }
        System.out.println(f);
    }
}
