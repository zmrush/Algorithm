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
    public static int computeMinimum(int[][] matrix){
        int[][] el=new int[matrix.length][matrix.length];
        for(int i=0;i<el.length;i++){
            for(int j=0;j<el.length;j++){
                el[i][j]=matrix[i][j];
            }
        }
        int[] matches=new int[matrix.length]; //y->x match
        int match_count=0;
        for(int i=0;i<matches.length;i++){
            matches[i]=-1;
        }
        for(int i=0;i<matrix.length;i++){
            int min=EL_MAX;
            for(int j=0;j<matrix.length;j++){
                if(el[i][j]<min){
                    min=el[i][j];
                }
            }
            for(int j=0;j<matrix.length;j++){
                el[i][j]-=min;
            }
        }
        BitSet xmatch=new BitSet(matrix.length);
        BitSet ymatch=new BitSet(matrix.length);
        while(match_count<matrix.length){
            int free=-1;
            for(int i=0;i<matrix.length;i++){
                if(!xmatch.get(i)){
                    free=i;break;
                }
            }
            int free_y=-1;
            Set<Integer> S=new HashSet<Integer>(); //notice we use set not list,because S is a mathematic set,but in fact we use it just to quickly judge if it exist in already set
            List<Integer> SS=new ArrayList<Integer>();
            S.add(free);
            SS.add(free);
            int[] N=new int[matrix.length];
            for(int i=0;i<matrix.length;i++){
                N[i]=-1;
            }
            ListIterator<Integer> iter=SS.listIterator();
            while(iter.hasNext()) {
                free=iter.next();
                for (int i = 0; i < matrix.length; i++) {
                    if (el[free][i] == 0 && !ymatch.get(i)) {
                        N[i]=free;
                        free_y = i;
                        break;
                    }
                    if (el[free][i] == 0 && ymatch.get(i)) {
                        if(!S.contains(matches[i])) {
                            S.add(matches[i]);
                            iter.add(matches[i]);
                            N[i] = free;
                            iter.previous();
                        }
                    }
                }
                if(free_y>-1){
                    break;
                }
            }
            if(free_y>-1){//add match
                subroutine(ymatch,xmatch,matches,free_y,matrix,N);
                match_count++;
            }else{//需要增加0来进行先关的操作
                int min=10000;
                iter=SS.listIterator();
                int free_new=-1;
                while(iter.hasNext()){
                    int x=iter.next();
                    for(int j=0;j<matrix.length;j++){
                        if(N[j]>-1){
                            continue;
                        }else{
                            if(el[x][j]<min){
                                min=el[x][j];
                                free_y=j;
                                free_new=x;
                            }
                        }
                    }
                }

                iter=SS.listIterator();
                while(iter.hasNext()){
                    int x=iter.next();
                    for(int j=0;j<matrix.length;j++){
                        el[x][j]-=min;
                    }
                }
                for(int j=0;j<matrix.length;j++){
                    if(N[j]>-1){
                        for(int i=0;i<matrix.length;i++){
                            el[i][j]+=min;
                        }
                    }
                }
                if(ymatch.get(free_y)==false) {
                    N[free_y]=free_new;
                    subroutine(ymatch,xmatch,matches,free_y,matrix,N);
                    match_count++;
                }
            }
        }
        int cost=0;
        for(int i=0;i<matrix.length;i++){
            cost+=matrix[matches[i]][i];
        }
        return cost;
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