import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TarotShamboast {
    public static List<Integer> kms(char[] buf,int n){
        int length=buf.length;
        List<Integer> res=new ArrayList<Integer>();
        for(int i=1;i<buf.length && i<=(n-length);i++){
            boolean isMatch=true;
            for(int j=i;j<buf.length;j++){
                if(buf[j]!=buf[j-i]){
                    isMatch=false;
                    break;
                }
            }
            if(isMatch==true){
                res.add(i);
            }
        }
        return res;
    }
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        int s=scanner.nextInt();
        scanner.nextLine();
        List<char[]> list=new ArrayList<char[]>(s);
        List<List<Integer>> kmps=new ArrayList<List<Integer>>(s);
        for(int i=0;i<s;i++){
            String tmp=scanner.nextLine();
            char[] buf=new char[tmp.length()];
            tmp.getChars(0,tmp.length(),buf,0);
            list.add(buf);
            kmps.add(kms(buf,n));
        }
        //bubble sort
        for(int i=0;i<(list.size()-1);i++){
            for(int j=list.size()-1;j>=(i+1);j--){
                List<Integer> last=kmps.get(j);
                List<Integer> next=kmps.get(j-1);
                boolean isSwitch=false;
                int cursor=0;
                while(last.size()>cursor&&next.size()>cursor){
                    if(last.get(cursor)>next.get(cursor)){
                        isSwitch=true;
                        break;
                    }else if(last.get(cursor)<next.get(cursor)){
                        isSwitch=false;
                        break;
                    }
                    cursor++;
                }
                if(isSwitch==false){
                    if(cursor>=last.size() && cursor<next.size())
                        isSwitch=true;
                }
                if(isSwitch==true){
                    kmps.set(j,next);kmps.set(j-1,last);
                    char[] tmp=list.get(j);list.set(j,list.get(j-1));list.set(j-1,tmp);
                }
            }
        }
        for(int i=0;i<list.size();i++){
            System.out.println(new String(list.get(i)));
        }
    }
} 