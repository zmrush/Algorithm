package com.mz;

public class Root {
    public static double computeRoot(double s,int n){
        double pre=1.0;
        double next=pre-(Math.pow(pre,n)-s)/(n*Math.pow(pre,n-1));
        double epita=0.0000001;
        int count=0;
        while(Math.abs(pre-next)>epita || count++<1000){
            pre=next;
            next=pre-(Math.pow(pre,n)-s)/(n*Math.pow(pre,n-1));
        }
        return next;
    }
    public static void main(String[] args){
        System.out.println(computeRoot(5,2));
    }

} 