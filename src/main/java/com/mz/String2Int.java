package com.mz;

/**
 * Created by mingzhu7 on 2016/8/22.
 */
public class String2Int {
    public static boolean isDigit(char x){
        if(x<='9' && x>='0')
            return true;
        return false;
    }
    public static int parseInt(String s){
        if(s==null)
            throw new RuntimeException("s is null");
        int max=-0x7fffffff;
        int multmax=max/10;
        boolean is_neg=false;
        int index=0;
        int value=0;
        if(s.charAt(0)=='+'){
            if(s.length()<=1)
                throw new RuntimeException("s format is wrong");
            index++;
        }else if(s.charAt(0)=='-'){
            index++;
            max=0x80000000;
            multmax=max/10;
            is_neg=true;
            if(s.length()<=1)
                throw new RuntimeException("s format is wrong");
        }
        while(index<s.length()){
            if(!isDigit(s.charAt(index)))
                throw new RuntimeException("s format is wrong");
            if(value<multmax)
                throw new RuntimeException("number overflow");
            if(value*10<max+(s.charAt(index)-'0'))
                throw new RuntimeException("number is overflow");
            value=value*10-(s.charAt(index)-'0');
            index++;
        }
        if(is_neg==false)
            return -value;
        else
            return value;


    }
    public static void main(String[] args) {
        String s ="122345";
        int x=parseInt(s);
        System.out.println(x);
    }
}
