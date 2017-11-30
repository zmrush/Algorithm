package com.mz;

public class FrequencyOfString {
    public static int[] getNext(String s){
        int[] next=new int[s.length()];
        next[0]=-1;
        for(int i=1;i<s.length();i++){
            int tmp=i-1;
            while(tmp>=0 && s.charAt(i)!=s.charAt(next[tmp]+1)){
                tmp=next[tmp];
            }
            if(tmp>=0)
                next[i]=next[tmp]+1;
            else
                next[i]=-1;
        }
        return next;
    }
    public static void main(String[] args){
        String target="nn";
        String pattern="Banana";
        int count=0;
        int targetIndex=0;
        int patternIndex=0;
        int[] next=getNext(pattern);
        while(targetIndex<target.length()){
            if(target.charAt(targetIndex)==pattern.charAt(patternIndex)){
                if(patternIndex==(pattern.length()-1)){
                    count++;
                    patternIndex=next[patternIndex]+1;
                    targetIndex++;
                }else{
                    targetIndex++;
                    patternIndex++;
                }
            }else{
                if(patternIndex>0){
                    patternIndex=next[patternIndex-1]+1;
                }else{
                    patternIndex=0;
                    targetIndex++;
                }

            }
        }
        System.out.println(count);
    }
} 