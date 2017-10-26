package com.mz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class LongestValidParentheses {
    public static void main(String[] args){
        int caseNum;
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        try {
            caseNum = Integer.valueOf(br.readLine());
            int[] results=new int[caseNum];
            String tmpLine;
            for(int i=0;i<caseNum;i++){
                tmpLine=br.readLine();
                int maxLength=0;
                Stack<Integer> stack=new Stack<Integer>();
                for(int j=0;j<tmpLine.length();j++){
                    if(tmpLine.charAt(j)=='(')
                        stack.push(-1);
                    else{//  ')'
                        if(!stack.empty()){
                            int tmpHead=0;
                            int total=0;
                            while(!stack.empty() && (tmpHead=stack.pop())!=-1){
                                total+=tmpHead;
                            }
                            if(tmpHead==-1){ //means we find -1 and we can now add 2
                                stack.push(total+2);
                            }else{ //means we cannot find -1 and we compare total with maxLength
                                if(total>maxLength)
                                    maxLength=total;
                            }
                        }
                    }
                }//string is end
                int maxTotal=0;
                int tmpLength;
                while(!stack.empty()){
                    tmpLength=stack.pop();
                    if(tmpLength!=-1){
                        maxTotal+=tmpLength;
                    }else{// concat is end;
                        if(maxTotal>maxLength){
                            maxLength=maxTotal;
                        }
                        maxTotal=0;//renew
                    }
                }
                //我居然忘记最后判断最后这个是否是最大的，这个看来我应该加强如何来让自己的逻辑没有漏洞
                if(maxTotal>maxLength)
                    maxLength=maxTotal;
                results[i]=maxLength;
            }// one iterator for one string
            for(int i=0;i<caseNum;i++){
                System.out.println(results[i]);
            }
        }catch (Exception e){
            return;
        }


    }
} 