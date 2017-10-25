package com.mz;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by mingzhu7 on 2017/10/24.
 */
public class MaxHistogramArea {
    public static class Component{
        public int cursor;
        public int value;
    }
    public static int computeMaxArea(Component[] components){
        Stack<Component> stack=new Stack<Component>();
        int maxArea=0;
        for(int i=0;i<components.length;i++){
            if(stack.isEmpty())
                stack.push(components[i]);
            else{
                Component tmp=stack.peek();
                if(components[i].value>=tmp.value){
                    stack.push(components[i]);
                }else{
                    while(tmp.value>components[i].value && !stack.isEmpty()){
                        stack.pop();
                        int areaTmp=tmp.value;
                        if(!stack.isEmpty()){
                            tmp=stack.peek();
                            areaTmp=areaTmp*(i-tmp.cursor-1);
                        }else{
                            areaTmp=areaTmp*(i);
                        }
                        if(areaTmp>maxArea)
                            maxArea=areaTmp;
                    }
                    stack.push(components[i]);
                }
            }
        }
        while(!stack.isEmpty()){
            Component tmp=stack.pop();
            int areaTmp=tmp.value;
            if(!stack.isEmpty()){
                tmp=stack.peek();
                areaTmp=areaTmp*(components.length-tmp.cursor-1);
            }else{
                areaTmp=areaTmp*(components.length);
            }
            if(areaTmp>maxArea)
                maxArea=areaTmp;
        }
        return maxArea;

    }
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int numCase=scanner.nextInt();
        int[] result=new int[numCase];
        for(int i=0;i<numCase;i++){
            int base=scanner.nextInt();
            Component[] components=new Component[base];
            for(int j=0;j<base;j++) {
                components[j] = new Component();
                components[j].value=scanner.nextInt();
                components[j].cursor=j;
            }
            result[i]=computeMaxArea(components);
        }
        for(int i=0;i<numCase;i++){
            System.out.println(result[i]);
        }

    }
}
