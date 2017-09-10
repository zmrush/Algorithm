package com.mz;

import java.util.*;

public class WordBreak {
    public static class TrieNode{
        public TrieNode parent;
        public TrieNode[] children=new TrieNode[128];
        public boolean isLeaf;
        public String value="";
    }
    public static void constructTrie(TrieNode root,String[] words){
        for(String s:words){
            int length=s.length();
            TrieNode cur=root;
            for(int j=0;j<length;j++){
                if(cur.children[s.charAt(j)]==null){
                    cur.children[s.charAt(j)]=new TrieNode();
                    cur.children[s.charAt(j)].parent=cur;
                    cur.children[s.charAt(j)].value=cur.value+s.charAt(j);
                }
                cur=cur.children[s.charAt(j)];
            }
            cur.isLeaf=true;
        }
    }
    public static int greedyFind(String obj,Stack<TrieNode> curNodes,List<String> results,int index,TrieNode curNode,TrieNode root){
        for(;index<obj.length();index++){
            if(curNode.children[obj.charAt(index)]!=null) {
                curNode = curNode.children[obj.charAt(index)];
            }
            else{
                if(!curNode.isLeaf)
                    break;
                curNodes.push(curNode);
                //--------------------------------------------------------------------------
//                values[values_index++]=' ';
//                System.arraycopy(curNode.value,0,values,values_index,curNode.value.length());
//                values_index+=curNode.value.length();
                //---------------------------------------------------------------------------
                curNode=root;
                index--;//重置，重新开始
            }
        }
        curNodes.push(curNode);
        if(index>=obj.length() && curNode.isLeaf) { //能够构成一个完整的句子
            StringBuilder result=new StringBuilder();
//            result.append("(");
            Iterator<TrieNode> iter=curNodes.iterator();
            while(iter.hasNext()){
                result.append(iter.next().value);
                result.append(" ");
            }
            result.delete(result.length()-1,result.length());//去掉空格
            results.add(result.toString());
        }
        return index;
    }
    public static String getAllPossibleWords(String obj,TrieNode root){
        char[] values=new char[2000];
        int values_index=0;
        Stack<TrieNode> curNodes=new Stack<TrieNode>();
        StringBuilder result=new StringBuilder();
        TrieNode curNode=root;
        //寻找第一个最长的匹配的word
        int index=0;
        List<String> results=new ArrayList<String>();
        index=greedyFind(obj,curNodes,results,index,curNode,root);
        //开始遍历贪婪算法
        while(!curNodes.isEmpty()){
            curNode=curNodes.pop();
            index-=curNode.value.length();
            //寻找更短的匹配的节点
            while(curNode!=null){
                curNode=curNode.parent;
                if(curNode!=null && curNode.isLeaf)
                    break;
            }
            if(curNode==null){
                //没有寻找到,继续向上找
            }else{
                index+=curNode.value.length();
                curNodes.push(curNode);
                //继续新的一轮贪婪搜索
                index=greedyFind(obj,curNodes,results,index,root,root);
            }
        }
        for(int i=results.size()-1;i>=0;i--){
            result.append("(");
            result.append(results.get(i));
            result.append(")");
        }
        return result.toString();
    }
    public static void main(String[] args){
        int caseNum=0;
        Scanner scanner=new Scanner(System.in);
        caseNum=scanner.nextInt();
        List<String> results=new ArrayList<String>(caseNum);
        for(int i=0;i<caseNum;i++){
            int numberOfWords=scanner.nextInt();
            scanner.nextLine();
            String[] words=scanner.nextLine().split(" ");
            String obj=scanner.nextLine();
            TrieNode root=new TrieNode();
            constructTrie(root,words);
            String result=getAllPossibleWords(obj,root);
            if(result.length()>0)
                results.add(result);
            else
                results.add("Empty");
        }
        for(int i=0;i<caseNum;i++){
            System.out.println(results.get(i));
        }
//        TrieNode trieNode=new TrieNode();
//        constructTrie(trieNode,new String[]{"snake", "snakes", "and", "sand", "ladder"});

    }
} 