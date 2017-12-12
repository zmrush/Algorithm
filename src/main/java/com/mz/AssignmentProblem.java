package com.mz;

import java.util.*;

public class AssignmentProblem {
    public static final int EL_MAX=10000;
    public static void subroutine(BitSet ymatch,BitSet xmatch,int[] matches,int free_y,int[][] matrix,int[] N){
        ymatch.set(free_y,true);
        while(xmatch.get(N[free_y])){
            //matches[free_y]=N[free_y];
            for(int i=0;i<matrix.length;i++){
                if(matches[i]==N[free_y]){
                    matches[free_y]=N[free_y];
                    free_y=i;
                    break;
                }
            }
        }
        xmatch.set(N[free_y],true);
        matches[free_y]=N[free_y];
    }
//    public static int computeMinimum(int[][] matrix){
//        int[][] el=new int[matrix.length][matrix.length];
//        for(int i=0;i<el.length;i++){
//            for(int j=0;j<el.length;j++){
//                el[i][j]=matrix[i][j];
//            }
//        }
//        int[] matches=new int[matrix.length]; //y->x match
//        int match_count=0;
//        for(int i=0;i<matches.length;i++){
//            matches[i]=-1;
//        }
//        for(int i=0;i<matrix.length;i++){
//            int min=EL_MAX;
//            for(int j=0;j<matrix.length;j++){
//                if(el[i][j]<min){
//                    min=el[i][j];
//                }
//            }
//            for(int j=0;j<matrix.length;j++){
//                el[i][j]-=min;
//            }
//        }
//        BitSet xmatch=new BitSet(matrix.length);
//        BitSet ymatch=new BitSet(matrix.length);
//        while(match_count<matrix.length){
//            int free=-1;
//            for(int i=0;i<matrix.length;i++){
//                if(!xmatch.get(i)){
//                    free=i;break;
//                }
//            }
//            int free_y=-1;
//            Set<Integer> S=new HashSet<Integer>(); //notice we use set not list,because S is a mathematic set,but in fact we use it just to quickly judge if it exist in already set
//            List<Integer> SS=new ArrayList<Integer>();
//            S.add(free);
//            SS.add(free);
//            int[] N=new int[matrix.length];
//            for(int i=0;i<matrix.length;i++){
//                N[i]=-1;
//            }
//            ListIterator<Integer> iter=SS.listIterator();
//            while(iter.hasNext()) {
//                free=iter.next();
//                for (int i = 0; i < matrix.length; i++) {
//                    if (el[free][i] == 0 && !ymatch.get(i)) {
//                        N[i]=free;
//                        free_y = i;
//                        break;
//                    }
//                    if (el[free][i] == 0 && ymatch.get(i)) {
//                        if(!S.contains(matches[i])) {
//                            S.add(matches[i]);
//                            iter.add(matches[i]);
//                            N[i] = free;
//                            iter.previous();
//                        }
//                    }
//                }
//                if(free_y>-1){
//                    break;
//                }
//            }
//            if(free_y>-1){//add match
//                subroutine(ymatch,xmatch,matches,free_y,matrix,N);
//                match_count++;
//            }else{//需要增加0来进行先关的操作
//                int min=10000;
//                iter=SS.listIterator();
//                int free_new=-1;
//                while(iter.hasNext()){
//                    int x=iter.next();
//                    for(int j=0;j<matrix.length;j++){
//                        if(N[j]>-1){
//                            continue;
//                        }else{
//                            if(el[x][j]<min){
//                                min=el[x][j];
//                                free_y=j;
//                                free_new=x;
//                            }
//                        }
//                    }
//                }
//
//                iter=SS.listIterator();
//                while(iter.hasNext()){
//                    int x=iter.next();
//                    for(int j=0;j<matrix.length;j++){
//                        el[x][j]-=min;
//                    }
//                }
//                for(int j=0;j<matrix.length;j++){
//                    if(N[j]>-1){
//                        for(int i=0;i<matrix.length;i++){
//                            el[i][j]+=min;
//                        }
//                    }
//                }
//                if(ymatch.get(free_y)==false) {
//                    N[free_y]=free_new;
//                    subroutine(ymatch,xmatch,matches,free_y,matrix,N);
//                    match_count++;
//                }
//            }
//        }
//        int cost=0;
//        for(int i=0;i<matrix.length;i++){
//            cost+=matrix[matches[i]][i];
//        }
//        return cost;
//    }
    public static int computeMinimum(int[][] matrix){
        BitSet xmatch=new BitSet(matrix.length);
        BitSet ymatch=new BitSet(matrix.length);
        int[] yo=new int[matrix.length];
        int[] xw=new int[matrix.length];
        int[] yw=new int[matrix.length];
        int matchcount=0;
        for(int i=0;i<matrix.length;i++){
            yo[i]=-1;
            int minweight=Integer.MAX_VALUE;
            for(int j=0;j<matrix.length;j++){
                minweight=matrix[j][i]<minweight?matrix[j][i]:minweight;
            }
            yw[i]=minweight;
        }
        while(matchcount<matrix.length){
            Set<Integer> xset=new HashSet<Integer>();
            Set<Integer> yset=new HashSet<Integer>();
            Set<Integer> newxset=new HashSet<>();
            Set<Integer> newxset2=new HashSet<>();
            int[] yn=new int[matrix.length];  //不在T中的y的最小权重
            int[] yny=new int[matrix.length];//不在T中的y的最小权重对应的S中的x
            for(int i=0;i<matrix.length;i++){
                yn[i]=Integer.MAX_VALUE;
                yny[i]=-1;
            }
            for(int i=0;i<matrix.length;i++){
                if(!xmatch.get(i)){
                    newxset.add(i);
                    break;
                }
            }
            boolean find=false;
            int free_y=-1;
            int min=Integer.MAX_VALUE;
            for(;;){
                Iterator<Integer> iter=newxset.iterator();
                while(iter.hasNext()){
                    Integer x=iter.next();
                    for(int i=0;i<matrix.length;i++){
                        if(!yset.contains(i)) {
                            if (matrix[x][i] == (xw[x] + yw[i])) {
                                yset.add(i);
                                if (!ymatch.get(i)) {
                                    free_y = i;
                                    yn[i] = 0;
                                    yny[i]=x;
                                    find = true;
                                    break;
                                } else {
                                    if (!xset.contains(yo[i]) && !newxset.contains(yo[i])) {
                                        newxset2.add(yo[i]);
                                        yny[i]=x;  //我忘记写这句话了，导致没有正确得修改这个yny数组
                                    }
                                }
                            } else {
                                if(yn[i]>(matrix[x][i] - xw[x] - yw[i]) ){
                                    yn[i]=matrix[x][i] - xw[x] - yw[i];
                                    yny[i]=x;
                                }
                                if (yn[i] < min) {
                                    min = yn[i];
                                }
                            }
                        }//y add or not
                    }//y over
                    if(find==true)
                        break;
                    xset.add(x);
                }//while
                if(find==true)
                    break;
                newxset.clear();
                if(newxset2.isEmpty()){
                    Iterator<Integer> iter2=xset.iterator();
                    while(iter2.hasNext()){
                        xw[iter2.next()]+=min;
                    }
                    iter2=yset.iterator();
                    while(iter2.hasNext()){
                        yw[iter2.next()]-=min;
                    }
                    //我的思路居然卡在这里，我想在这里将所有没有匹配的y进行遍历，看哪个x与这个y的对应的权重等于矩阵的值，并且去更新这个
                    //yn数组，导致时间复杂度没法降下来。其实是不需要进行这种遍历的，我们只需要遍历一下这个y就行了啊
                    for(int i=0;i<matrix.length;i++){
                        if(!yset.contains(i)){
                            yn[i]=yn[i]-min;
                            if(yn[i]==0){
                                if(!ymatch.get(i)){
                                    free_y = i;  //我这个地方写错了，将free_y=i 写成了 free_y=yny[i];
                                    find = true;
                                    break;
                                }else{
                                    newxset2.add(yo[i]);
                                }
                            }
                        }
                    }
                    min=Integer.MAX_VALUE;//重新开始计算最小值
                }//if y and x is full,add new ones
                if(find==true)
                    break;
                newxset.addAll(newxset2);
                newxset2.clear();
            }
            subroutine(ymatch,xmatch,yo,free_y,matrix,yny);
            matchcount++;
        }
        int mincost=0;
        for( int i=0;i<matrix.length;i++){
            mincost+=matrix[yo[i]][i];
        }
        return mincost;
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