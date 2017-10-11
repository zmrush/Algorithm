package com.mz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SimilarExpression {
    public static boolean isAlpha(char x){
        return x>='a' && x<='z';
    }
    public static int countNum(String s,int start,int[] count,boolean isPoitive){
        while(start<s.length()){
            if(isAlpha(s.charAt(start))) {
                if(isPoitive)
                    count[s.charAt(start) - 'a']++;
                else
                    count[s.charAt(start)-'a']--;
                start++;
            }else if(s.charAt(start)=='-'){
                if(s.charAt(start+1)=='('){
                    start=countNum(s,start+2,count,!isPoitive);
                }else if(isAlpha(s.charAt(start+1))){
                    if(isPoitive)
                        count[s.charAt(start+1)-'a']--;
                    else
                        count[s.charAt(start+1) - 'a']++;
                    start+=2;
                } else
                    throw new RuntimeException("expression error");
            }else if(s.charAt(start)=='+'){
                if(s.charAt(start+1)=='(')
                    start=countNum(s,start+2,count,isPoitive);
                else if(isAlpha(s.charAt(start+1))){
                    if(isPoitive)
                        count[s.charAt(start+1) - 'a']++;
                    else
                        count[s.charAt(start+1)-'a']--;
                    start+=2;
                }else
                    throw new RuntimeException("expression error");
            }else if(s.charAt(start)==')'){
                return start+1;//stack return
            }
            else if(s.charAt(start)=='('){
                start++;
            }else{
                throw new RuntimeException("input error");
            }
        }
        return start;
    }
    public static void main(String[] args){
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int case_num=0;
        try{
            case_num=Integer.valueOf(br.readLine());
        }catch (Exception e){
            return;
        }
        List<Boolean> results=new ArrayList<Boolean>();
        int alpha_length='z'-'a'+1;
        int[] first_count=new int[alpha_length];
        int[] second_count=new int[alpha_length];
        try {
            for (int i = 0; i < case_num; i++) {
                String first = br.readLine();
                String second = br.readLine();
                countNum(first,0,first_count,true);
                countNum(second,0,second_count,true);
                boolean isEqual=true;
                for(int j=0;j<alpha_length;j++){
                    if(first_count[j]!=second_count[j]){
                        isEqual=false;
                        break;
                    }
                }
                if(isEqual)
                    results.add(true);
                else
                    results.add(false);
                for(int j=0;j<alpha_length;j++){
                    first_count[j]=0;
                    second_count[j]=0;
                }
            }
        }catch (Exception e){
            return;
        }
        for(int i=0;i<case_num;i++){
            if(results.get(i)==true)
                System.out.println("YES");
            else
                System.out.println("NO");
        }
    }
} 