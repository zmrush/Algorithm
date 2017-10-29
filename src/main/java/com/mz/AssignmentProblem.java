package com.mz;

import java.util.Scanner;

public class AssignmentProblem {
    public static int computeMinimum(int[][] matrix){
        return 0;
    }
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int testNum=scanner.nextInt();
        int[] results=new int[testNum];
        for(int i=0;i<testNum;i++){
            int N=scanner.nextInt();
            int[][] matrix=new int[N][N];
            for(int j=0;j<N;j++){
                for(int k=0;k<N;k++){
                    matrix[j][k]=scanner.nextInt();
                }
            }
            results[i]=computeMinimum(matrix);
        }
        for(int i=0;i<testNum;i++){
            System.out.println(results[i]);
        }
    }
} 