package com.mz;

/**
 * Created by mingzhu7 on 2017/10/27.
 */
public class PrefixTree {
    public static class Node{
        public boolean isLeaf;
        public Node[] children;
    }
    public static Node buildPrefixTree(String[] components){
        Node root=new Node();
        root.children=new Node[26];
        for(String component:components){
            Node current=root;
            for(int i=0;i<component.length();i++){
                if(current.children[component.charAt(i)-'a']==null){
                    current.children[component.charAt(i)-'a']=new Node();
                    current.children[component.charAt(i)-'a'].children=new Node[26];
                }
                current=current.children[component.charAt(i)-'a'];
            }
            current.isLeaf=true;
        }
        return root;
    }
    public static boolean matchSequence(Node root,String target){
        for(int i=0;i<target.length();i++){
            if(root.children[target.charAt(i)-'a']==null)
                return false;
            root=root.children[target.charAt(i)-'a'];
        }
        if(root.isLeaf==true)
            return true;
        return false;
    }
    public static void main(String[] args){
        String[] components=new String[]{"apple","appear","terrorist","tear","please"};
        Node root=buildPrefixTree(components);
        boolean isfind=matchSequence(root,"hello");
        isfind=matchSequence(root,"appera");
        System.out.println(isfind);


    }
}
