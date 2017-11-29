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
//    public static int parseInt(String s){
//        if(s==null)
//            throw new RuntimeException("s is null");
//        int max=-0x7fffffff;
//        int multmax=max/10;
//        boolean is_neg=false;
//        int index=0;
//        int value=0;
//        if(s.charAt(0)=='+'){
//            if(s.length()<=1)
//                throw new RuntimeException("s format is wrong");
//            index++;
//        }else if(s.charAt(0)=='-'){
//            index++;
//            max=0x80000000;
//            multmax=max/10;
//            is_neg=true;
//            if(s.length()<=1)
//                throw new RuntimeException("s format is wrong");
//        }
//        while(index<s.length()){
//            if(!isDigit(s.charAt(index)))
//                throw new RuntimeException("s format is wrong");
//            if(value<multmax)
//                throw new RuntimeException("number overflow");
//            if(value*10<max+(s.charAt(index)-'0'))
//                throw new RuntimeException("number is overflow");
//            value=value*10-(s.charAt(index)-'0');
//            index++;
//        }
//        if(is_neg==false)
//            return -value;
//        else
//            return value;
//
//
//    }
//    public static int parseInt(String s){
//        if(s==null || s.length() == 0)
//            throw new RuntimeException("parameter wrong");
//        int value=0;
//        int i=0;
//        boolean isNeg=false;
//        int limit=-0x7fffffff;
//        if(s.charAt(i)== '+' || s.charAt(i)=='-'){
//            if(s.length()==1)
//                throw new RuntimeException("parameter wrong");
//            if(s.charAt(i)=='-'){
//                isNeg=true;
//                limit=0x80000000;
//            }
//            i++;
//        }else{
//            throw new RuntimeException("paramter wrong");
//        }
//        while(i<s.length()){
//            if(!isDigit(s.charAt(i)))
//                throw new RuntimeException("parameter wrong");
//            if(value<limit/10)
//                throw new RuntimeException("parameter wrong");
//            if(value*10<(limit+s.charAt(i)-'0'))
//                throw new RuntimeException("parameter wrong");
//            value=value*10-(s.charAt(i)-'0');
//            i++;
//        }
//        if(isNeg==false)
//            return -value;
//        return value;
//    }

//    public static int parseInt(String s){
//        if(s==null || s.length()==0)
//            throw new RuntimeException("s is null");
//        boolean isNeg=false;
//        int min=-0x7fffffff;
//        int mulmin=min/10;
//        int result=0;
//        int index=0;
//        if(s.charAt(index)=='+'){
//            index++;
//            if(s.length()<=1)
//                throw new RuntimeException("input format is wrong");
//        }else if(s.charAt(index)=='-'){
//            index++;
//            isNeg=true;
//            min=0x80000000;
//            mulmin=min/10;
//            if(s.length()<=1){
//                throw new RuntimeException("input format is wrong");
//            }
//        }
//        while(index<s.length()){
//            char tmp=s.charAt(index);
//            if(!isDigit(tmp))
//                throw new RuntimeException(tmp +" cannot be parsed to int");
//            if(result<mulmin)
//                throw new RuntimeException("overfow");
//            if(result*10<min+(tmp-'0'))
//                throw new RuntimeException("overflow");
//            result=result*10-(tmp-'0');
//            index++;
//        }
//        if(!isNeg)
//            return -result;
//        return result;
//    }









//    public static int parseInt(String s){
//        if(s==null || s.length()==0)
//            throw new RuntimeException("input format wrong");
//        boolean isNeg=false;
//        int limit=-0x7fffffff;
//        int mullimit=limit/10;
//        int index=0;
//        int length=s.length();
//        int value=0;
//        if(s.charAt(index)=='-'){
//            isNeg=true;
//            limit=0x80000000;
//            mullimit=limit/10;
//            if(length==1)
//                throw new RuntimeException();
//            index++;
//        }else if(s.charAt(index)=='+'){
//            if(length==1)
//                throw new RuntimeException();
//            index++;
//        }
//        while(index<length){
//            char tmp=s.charAt(index);
//            if(!isDigit(tmp))
//                throw new RuntimeException();
//            if(value<mullimit)
//                throw new RuntimeException();
//            if(value*10<limit+(tmp-'0'))
//                throw new RuntimeException();
//            value=value*10-(tmp-'0');
//            index++;
//        }
//        if(isNeg)
//            return value;
//        return -value;
//    }


    public static int parseInt(String s){
        if(s.length()>=13)
            throw new RuntimeException("overflow");
        int index=0;
        int min=-0x7fffffff;
        int multmin=min/10;
        boolean isNeg=false;
        int value=0;
        if(s==null || s.length()<=0)
            throw new RuntimeException("input is null");
        if(s.charAt(index)=='-'){
            if(s.length()<=1)
                throw new RuntimeException("iput is wrong");
            min=0x80000000;
            multmin=min/10;
            index++;
            isNeg=true;
        }else if(s.charAt(index)=='+'){
            index++;
        }
        while(index<s.length()){
            if(!isDigit(s.charAt(index)))
                throw new RuntimeException("input is wrong");
            if(value<multmin)
                throw new RuntimeException("overflow");
            int tmp=s.charAt(index)-'0';
            if(value*10<min+tmp)
                throw new RuntimeException("overflow");
            value=value*10-tmp;
            index++;
        }
        if(isNeg)
            return value;
        return -value;
    }
    public static void main(String[] args) {
        String s =String.valueOf("2147483648");
        int x=parseInt("+2147483647");
        x=Integer.valueOf(s);
        System.out.println(x);
    }
}
