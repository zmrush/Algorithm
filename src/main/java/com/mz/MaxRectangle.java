package com.mz;

/**
 * Created by mingzhu7 on 2016/8/22.
 * leetcode 363
 */
public class MaxRectangle {
    public static int maxSumSubmatrix(int[][] matrix, int k) {
        if(matrix==null || matrix.length<=1 || matrix[0].length <=1)
            throw new RuntimeException("input is wrong");
        int height=matrix.length;
        int width=matrix[0].length;
        if(height>=width){
            int num=width*(width-1)/2;
            int[] store=new int[num*height];
            int target=Integer.MIN_VALUE;
            for(int i=0;i<height;i++){   //row
                int t=0; //new row store num
                int last=matrix[i][0];//last sum
                for(int j=0;j<(width-1);j++){
                    if((j & 0x1)==0){
                        int l=j+1;
                        while(l<width){
                            store[i*num+t]=last=last+matrix[i][l];
                            t++;l++;
                        }
                        last=last-matrix[i][j];
                    }else{
                        int l=width-2;
                        store[i*num+(t)]=last;
                        t++;
                        while(l>j){
                            store[i*num+t]=last=last-matrix[i][l+1];
                            t++;l--;
                        }
                        last=last-matrix[i][j];
                    }
                }
                for(int j=0;j<i;j++){
                    for(int l=0;l<num;l++){
                        store[j*num+l]=store[i*num+l]+store[j*num+l];
                        if(store[j*num+l]<= k && store[j*num+l] >target)
                            target=store[j*num+l];
                    }
                }
            }
            return target;
        }else{
            int[][] matrixt=new int[width][height];
            for(int i=0;i<width;i++){
                for(int j=0;j<height;j++)
                    matrixt[i][j]=matrix[j][i];
            }
            return maxSumSubmatrix(matrixt,k);
        }
    }
    public static void main(String[] args){
        int[][] matrix = new int[3][3];
        matrix[0]=new int[]{1,  0, 1};
        matrix[1]=new int[]{0, -2, 3};
        matrix[2]=new int[]{4,-5,6};

        int k = 2;
        int largest=maxSumSubmatrix(matrix,100);
        System.out.println(largest);
    }
}
