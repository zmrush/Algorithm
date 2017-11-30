package com.mz;

import java.util.*;

public class DiameterNTree {
    public static class NTreeNode{
        NTreeNode[] children;
        public NTreeNode(int childrenNum){
            children=new NTreeNode[childrenNum];
        }
        public void set(int i,NTreeNode child){
            children[i]=child;
        }
    }
    public int getDiameter(NTreeNode root){
        Stack<NTreeNode> stack=new Stack<NTreeNode>();
        Map<NTreeNode,Integer> map=new HashMap<NTreeNode,Integer>();
        stack.push(root);
        int diameter=0;
        while(!stack.isEmpty()){
            NTreeNode tmp=stack.peek();
            int depth=0;
            boolean isCurrentPop=true;
            if(tmp.children!=null && tmp.children.length>0){
                for(NTreeNode child:tmp.children){
                    if(!map.containsKey(child)){
                        isCurrentPop=false;
                        stack.push(child);
                    }else{
                        depth=depth>=map.get(child)?depth:map.get(child);
                    }
                }
            }
            if(isCurrentPop==true){
                stack.pop();
                map.put(tmp,depth);
            }
        }
        LinkedList<NTreeNode> list=new LinkedList<NTreeNode>();
        list.push(root);
        while(!list.isEmpty()){
            NTreeNode cur=list.poll();
            NTreeNode first=null;
            NTreeNode second=null;
            if(cur.children!=null && cur.children.length>0){
                for(NTreeNode child:cur.children){
                    if(first ==null || map.get(child)>map.get(first))
                        first=child;
                    else if(second==null map.get(child)>map.get(second)){
                        second=child;
                    }
                }
            }
            if(second==null){
                if(first==null){

                }else{
                    if((map.get(first)+1)>diameter){
                        diameter=map.get(first)+1;
                    }
                }
            }else{
                if((map.get(first)+map.get(second)+2)>diameter)
                    diameter=map.get(first)+map.get(second)+2;
            }
        }
        return diameter;
    }
    public static void main(String[] args){

    }
} 