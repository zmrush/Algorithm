package com.mz;

import java.util.TreeSet;

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
    public static int maxSumSubmatrix3(int[][] matrix,int k){
        if (matrix.length == 0) return 0;
        int row = matrix.length;
        int col = matrix[0].length;
        int max = Integer.MIN_VALUE;
        // outer loop should use smaller axis
        for (int left = 0; left < col; left++) {
            int[] sums = new int[row];
            for (int right = left; right < col; right++) {
                int sum = 0, maxSum = Integer.MIN_VALUE;
                for (int i = 0; i < row; i++) {
                    sums[i] += matrix[i][right];
                    sum = Math.max(sum + sums[i], sums[i]);
                    maxSum = Math.max(maxSum, sum);
                }
                if (maxSum <= k) {
                    max = Math.max(max, maxSum);
                    continue;
                }

                TreeSet<Integer> set = new TreeSet<Integer>();
                set.add(0);
                int curSum = 0;
                for (int s : sums) {
                    curSum += s;
                    Integer num = set.ceiling(curSum - k);
                    if (num != null) {
                        max = Math.max(max, curSum - num);
                    }
                    set.add(curSum);
                }
            }
        }
        return max;
    }
    public static int maxSumSubmatrix2(int[][] matrix, int k) {
        int height=matrix.length;
        int width=matrix[0].length;
        if(height>width){
            int[][] matrixt=new int[width][height];
            for(int i=0;i<width;i++){
                for(int j=0;j<height;j++)
                    matrixt[i][j]=matrix[j][i];
            }
            return maxSumSubmatrix2(matrixt,k);
        }
        int[] columnSum=new int[width];
        int target=Integer.MIN_VALUE;
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++)
                columnSum[j]=0;// fresh to zero
            for(int j=i;j>=0;j--){
                int maxSum=Integer.MIN_VALUE;
                int maxSumTmp=0;
                for(int m=0;m<width;m++) {
                    columnSum[m] += matrix[j][m];  //add current row value
                    maxSumTmp=Integer.max(columnSum[m],columnSum[m]+maxSumTmp);// well,ignite by last solution,i realize this tmp may decrease the execution time largely
                    maxSum=Integer.max(maxSum,maxSumTmp);
                }
                if(maxSum<k){
                    if(maxSum>target){
                        target=maxSum;
                    }
                    continue;
                }
                TreeSet<Integer> set=new TreeSet<Integer>();  //store now sum before k;
                set.add(0);
                int sum=0;
                for(int m=0;m<width;m++) {
                    Integer find=set.floor(k -sum- columnSum[m]);
                    if(find!=null){
                        if((find+sum+columnSum[m])>target)
                            target=find+sum+columnSum[m];
                        if(target==k)
                            return k;
                    }
                    sum+=columnSum[m];
                    set.add(-sum);
                }
            }

        }
        return target;
    }
    public static void main(String[] args){
        int[][] matrix = new int[2][3];
        matrix[0]=new int[]{1,  0, 1};
        matrix[1]=new int[]{0, -2, 3};
//        matrix[2]=new int[]{4,-5,6};

//        int[][] matrix = new int[4][4];
//        matrix[0]=new int[]{0,-2,-7,0};
//        matrix[1]=new int[]{9,2,-6,2};
//        matrix[2]=new int[]{-4,1,-4,1};
//        matrix[3]=new int[]{-1,8,0,-2};

        int k = 2;
        int largest=maxSumSubmatrix2(matrix,2);
        System.out.println(largest);
    }
}
