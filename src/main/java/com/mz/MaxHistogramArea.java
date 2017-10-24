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
        for(int i=0;i<components.length-1;i++){
            if(stack.isEmpty())
                stack.push(components[i]);
            else{
                Component tmp=stack.peek();
                if(components[i].value>=tmp.value){
                    stack.push(components[i]);
                }else{
                    while(tmp.value>components[i].value && !stack.isEmpty()){
                        stack.pop();
                        if(!stack.isEmpty()){

                        }
                    }
                }
            }
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

        }

    }
}
