package com.mz;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by mingzhu7 on 2017/8/1.
 */
public class PrintBinaryTreeOverRow {
    public static class Node{
        public int value;
        public Node left;
        public Node right;
        public Node(int value){
            this.value=value;
        }
        public Node(int value,Node left,Node right){
            this.value=value;
            this.left=left;
            this.right=right;
        }
        public void addChildren(boolean isLeft,Node node){
            if(isLeft){
                this.left=node;
            }else{
                this.right=node;
            }
        }
    }
    public static void printTree(Node root){
        if(root==null)
            return;
        Queue<Node> queue=new LinkedList<Node>();
        int cur=0;
        int next=0;
        queue.add(root);
        cur++;
        while(!queue.isEmpty()){
            Node tmp=queue.poll();
            cur--;
            System.out.print(tmp.value);
            if(tmp.left!=null){
                queue.offer(tmp.left);
                next++;
            }
            if(tmp.right!=null){
                queue.offer(tmp.right);
                next++;
            }
            if(cur==0){
                System.out.print("\n");
                cur=next;
                next=0;
            }else{
                System.out.print(",");
            }
        }

    }
    public static void main(String[] args){
        Node root=new Node(23);
        Node node1=new Node(5);
        Node node2=new Node(6);
        root.addChildren(true,node1);
        root.addChildren(false,node2);
        Node node3=new Node(3);
        node1.addChildren(false,node3);
        Node node4=new Node(9);
        node2.addChildren(true,node4);
        printTree(root);

    }
}
