package com.mz;

public class Kmp {
    public static class Node{
        public Node[] children;
        public Node last;
        public boolean isLeaf;
    }
    public static Node buildKmp(String pattern){
        Node root=new Node();
        Node current=root;
        root.children=new Node[256];
        for(int j=0;j<256;j++){
            if(j!=pattern.charAt(0))
                current.children[j]=root;
        }
        current.children[pattern.charAt(0)]=new Node();
        current.children[pattern.charAt(0)].last=root;
        current=current.children[pattern.charAt(0)];
        for(int i=1;i<pattern.length();i++){
            current.children=new Node[256];
            for(int j=0;j<256;j++){
                if(j!=pattern.charAt(i))
                    current.children[j]=current.last.children[j];
            }
            current.children[pattern.charAt(i)]=new Node();
            current.children[pattern.charAt(i)].last=current.last.children[pattern.charAt(i)];
            current=current.children[pattern.charAt(i)];
        }
        current.isLeaf=true;
        return root;
    }
    public static void main(String[] args){
        String pattern="aaa";
        String target="sbglneogbowbgwpgbqgpg";
        Node root=buildKmp(pattern);
        for(int i=0;i<target.length();i++){
            if(root.children[target.charAt(i)].isLeaf){
                System.out.println("find");
                return;
            }else{
                root=root.children[target.charAt(i)];
            }

        }
        System.out.println("not found");
    }
} 