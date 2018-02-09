import java.util.Scanner;

/**
 * Created by mingzhu7 on 2018/2/9.
 */
public class ForeverYoung {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        long y=scanner.nextLong();long l=scanner.nextLong();
        String s=String.valueOf(l);
        int length=s.length();
        if(length<=2){
            for(int x=s.charAt(1)-'0';x<10;x++){
                long t=(y-x)/(s.charAt(0)-'0');
                long y0=t*(s.charAt(0)-'0')+x;
                if(y0==y){
                    System.out.println(t);
                    return;
                }
            }
            for(int x=s.charAt(0)-'0'+1;x<=9;x++){
                long t=y/x;
                long t0=y-t*x;
                if(t0<=9){
                    System.out.println(t);
                    return;
                }
            }
            s="100";
        }
        length=s.length();
        //brute-force
        //search the biggest x;then brute-force
        long low=(long)Math.ceil(Math.pow((y/(s.charAt(0)-'0')),1.0/(s.length()-1)));
        while(true){
            long addtmp=1;
            long y0=0;
            long tmp;
            boolean stop=true;
            for(int i=0;i<length;i++){
                tmp=(s.charAt(length-i-1)-'0')*addtmp;
                if(y0>=(Long.MAX_VALUE-tmp)) {
                    stop=false;
                    break;
                }
                y0+=tmp;
                addtmp*=low;
            }
            if(stop && y0<=y){
                break;
            }
            low--;
        }
        for(;;low--){
            long y0=y;
            long remainder;
            boolean find=true;
            while(y0>0){
                remainder=y0-y0/low*low;
                if(remainder>9) {
                    find = false;
                    break;
                }
                y0=y0/low;
            }
            if(find==true)
                break;
        }
        System.out.println(low);
    }
}
