package com.mz;

import java.util.Scanner;

/**
 * Created by mingzhu7 on 2017/10/26.
 */
public class MaxPairSum {
    public static void quicksort(int[] numbers,int start,int end){
        if(end<=start)
            return;
        int middle=numbers[start];
        int i=start;
        int j=end;
        while(i<j){
            while(i<j && numbers[j]>=middle)
                j--;
            numbers[i]=numbers[j];
            while(i<j && numbers[i]<=middle)
                i++;
            numbers[j]=numbers[i];
        }
        numbers[i]=middle;
        quicksort(numbers,start,i-1);
        quicksort(numbers,i+1,end);
    }
//    public static int findMaxZero(int[] numbers){
//        int start=0;int end=numbers.length-1;
//        if(numbers[start]*numbers[end]>=0){
//            if(numbers[start]>=0)
//                return -1;
//            else
//                return end;
//        }
//        int middle=(start+end)/2;
//        while(start<end){
//            if(numbers[middle]>0) {
//                if(numbers[middle-1]<=0)
//                    return (middle-1);
//                end = middle-1;
//                middle=(start+end)/2;
//            }
//            else if(numbers[middle]<0) {
//                if(middle>start)
//                    start = middle;
//                else
//                    return start;
//                middle=(start+end)/2;
//            }else {
//                while(numbers[middle+1]==0)
//                    middle=middle+1;
//                return middle;
//
//            }
//        }
//        return middle;
//    }
    public static int getMaxSum(int[] numbers){
        int overflow=1000000007;
        quicksort(numbers,0,numbers.length-1);
        int maxSum=0;
        int neg=0;
        int pos=0;
        boolean jump=false;
        if((numbers.length&0x01)==0){
            for(int i=0;i<numbers.length;i+=2){
                if(numbers[i]<0 && numbers[i+1]>0){
                    neg=numbers[i]*numbers[i+1];
                }else {
                    if((maxSum+numbers[i]*numbers[i+1])>overflow)
                        jump=true;
                    maxSum = (maxSum + numbers[i] * numbers[i + 1]) % overflow;
                }
            }
            if(neg!=0){  //if has turned from neg to pos
                if(jump==false)
                    maxSum+=neg;
                else{
                    maxSum+=neg;
                    if(maxSum<0)
                        maxSum+=overflow;
                }
            }
            return maxSum;
        }else{
            int start=0;
            if(numbers[start]>=0) {
                start = 1;
                pos=numbers[0];
            }
            for(int i=start;i<numbers.length;i+=2){
                if(numbers[i]<=0 && (i+1)<numbers.length && numbers[i+1]>0){
                    neg=numbers[i];
                    i=i-1;
                }else{
                    if((maxSum+numbers[i]*numbers[i+1])>overflow)
                        jump=true;
                    maxSum = (maxSum + numbers[i] * numbers[i + 1]) % overflow;
                    if(numbers[i]<=0 && (i+2)<numbers.length && numbers[i+2]>0){
                        pos=numbers[i+2];
                        i=i+1;
                    }
                }
            }
            if(neg!=0){  //if has turned from neg to pos
                if(jump==false)
                    maxSum+=neg;
                else{
                    maxSum+=neg;
                    if(maxSum<0)
                        maxSum+=overflow;
                }
            }
            if(pos!=0){
                maxSum=(maxSum+pos)%overflow;
            }
            return maxSum;
        }

    }
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int testCase=scanner.nextInt();
        int[] results=new int[testCase];
        for(int i=0;i<testCase;i++){
            int base=scanner.nextInt();
            int[] numbers=new int[base];
            for(int j=0;j<base;j++)
                numbers[j]=scanner.nextInt();
            results[i]=getMaxSum(numbers);
        }
        for(int i=0;i<testCase;i++){
            System.out.println(results[i]);
        }
    }
}
