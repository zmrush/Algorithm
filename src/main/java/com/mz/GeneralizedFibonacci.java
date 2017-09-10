package com.mz;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeneralizedFibonacci {
    public static int getFibonacci(int a,int b,int c,int n,int m){
        if(n==1 || n==2)
            return 1;
        int f1=1;
        int f2=1;
        int f3=0;
        long tmp;
        long tmp2;
        for(int i=3;i<=n;i++){
            tmp=a;
            tmp=tmp*f2;
            tmp2=b;
            tmp2=tmp2*f1;
            tmp+=tmp2+c;
            f3=(int)(tmp%m);
            f1=f2;
            f2=f3;
        }
        return f3;
    }
    public static void main(String[] args){
        try {
            Scanner scanner = new Scanner(System.in);
            int caseNum = scanner.nextInt();
            List<Integer> results = new ArrayList<Integer>(caseNum);
            int a,b,c,n,m;
            for (int i = 0; i < caseNum; i++) {
                a=scanner.nextInt();
                b=scanner.nextInt();
                c=scanner.nextInt();
                n=scanner.nextInt();
                m=scanner.nextInt();
                results.add(getFibonacci(a,b,c,n,m));
            }
            for(int i=0;i<caseNum;i++){
                System.out.println(results.get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
} 