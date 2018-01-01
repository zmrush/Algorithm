package com.mz;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by mingzhu7 on 2017/12/29.
 */
public class DinicMaxFlow {
    private static final double DB_EPSLON=0.0000001;
    public static double maxFlow(double[][] edges,int source,int sink){
        double flow=0.0;
        int[] level=new int[edges.length];
        LinkedList<Integer>[] onLevel=new LinkedList[edges.length];
        while(true){
            for(int i=0;i<level.length;i++) {
                level[i] = -1;//initializing
                onLevel[i]=new LinkedList<Integer>();
            }
            //bfs
            LinkedList<Integer> queue=new LinkedList<Integer>();
            queue.push(source);
            level[source]=0;
            while(!queue.isEmpty()){
                Integer from=queue.pop();
                for(int i=0;i<edges.length;i++){
                    if(edges[from][i]>DB_EPSLON && level[i]==-1){
                        level[i]=level[from]+1;
                        queue.push(i);
                    }
                    if(level[i]==level[from]+1){
                        //onLevel[from][i]=1;//put into the level graph
                        onLevel[from].push(i);
                    }
                }
            }
            if(level[sink]==-1)
                break;//there is no way from source to sink
            //dfs until there is no path from source to sink in the level graph
            LinkedList<Integer> dfsQueue=new LinkedList<Integer>();
            dfsQueue.push(source);
            while(!dfsQueue.isEmpty()){
                int from=dfsQueue.peek();
                if(from==sink){
                    Iterator<Integer> iter=dfsQueue.descendingIterator();
                    int last =iter.next();
                    double mincapacity=Double.MAX_VALUE;
                    int minLevel=Integer.MAX_VALUE;
                    while (iter.hasNext()) {
                        int next=iter.next();
                        if(edges[last][next]<mincapacity){
                            mincapacity=edges[last][next];
                            minLevel=level[last];
                        }
                        last=next;
                    }
                    flow+=mincapacity;
                    iter=dfsQueue.iterator();
                    last=iter.next();
                    iter.remove();
                    while(iter.hasNext()){
                        int next=iter.next();
                        edges[next][last]-=mincapacity;
                        if(edges[next][last]<=DB_EPSLON){
                            edges[next][last]=0.0;
                            onLevel[next].pop();
                        }
                        edges[last][next]+=mincapacity;
                        if(level[next]>minLevel)
                            iter.remove();
                        last=next;
                    }
                }else{
                    if(onLevel[from].size()<=0){
                        dfsQueue.pop();
                        if(!dfsQueue.isEmpty()){
                            onLevel[dfsQueue.peek()].pop();
                        }
                    }else{
                        dfsQueue.push(onLevel[from].peek());
                    }
                }
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
        for(int i=0;i<testNum;i++){
            System.out.println(result[i]);
        }
    }
}
