package com.mz;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by mingzhu7 on 2017/12/29.
 */
public class DinicMaxFlow {
    static double maxFlow(double[][] edges,int source,int sink){
        double flow=0.0;
        int[][] onLevel=new int[edges.length][edges.length];
        int[] level=new int[edges.length];
        int[] marked=new int[edges.length];
        while(true){
            for(int i=0;i<level.length;i++) {
                level[i] = -1;//initializing
                marked[i]=0;
            }
            //bfs
            LinkedList<Integer> queue=new LinkedList<Integer>();
            queue.push(source);
            level[source]=0;
            while(!queue.isEmpty()){
                Integer from=queue.pop();
                for(int i=0;i<edges.length;i++){
                    if(edges[from][i]>0.0 && level[i]==-1){
                        level[i]=level[from]+1;
                        queue.push(i);
                    }
                    if(level[i]==level[from]+1){
                        onLevel[from][i]=1;//put into the level graph
                    }
                }
            }
            if(level[sink]==-1)
                break;//there is no way from source to sink
            //dfs
            LinkedList<Integer> dfsQueue=new LinkedList<Integer>();
            dfsQueue.push(source);
            while(!dfsQueue.isEmpty()){
                int from=dfsQueue.peek();
                if(from==sink){
                    Iterator<Integer> iter=dfsQueue.iterator();
                    while (iter.hasNext()) {

                    }
                }
            }
            //reset level graph
            for(int i=0;i<edges.length;i++){
                for(int j=0;j<edges.length;j++)
                    onLevel[i][j]=0;
            }
        }
        return flow;
    }
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int testNum=scanner.nextInt();
        int[] result=new int[testNum];
        for(int i=0;i<testNum;i++){
            int vertext=scanner.nextInt();
            double[][] edges=new double[vertext][vertext];
            int edgeNum=scanner.nextInt();
            int start;int end;int capacity;
            for(int j=0;j<edgeNum;j++){
                start=scanner.nextInt();
                end=scanner.nextInt();
                capacity=scanner.nextInt();
                edges[start-1][end-1]=capacity;
                //because it is non-directed graph,so we should add this
                edges[end-1][start-1]=capacity;
            }
            result[i]=(int)maxFlow(edges,0,vertext-1);
        }
    }
}
