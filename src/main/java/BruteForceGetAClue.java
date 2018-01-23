import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Scanner;

/**
 * Created by mingzhu7 on 2018/1/23.
 */
public class BruteForceGetAClue {
    public static int getFirstOne(int x){
        int t=0;
        while((x&1)==0){
            x>>=1;
            t++;
        }
        return t;
    }
    public static int getOnesNum(int x){
        int count=0;
        while(x!=0){
            x=x&(x-1);
            count++;
        }
        return count;
    }
    public static void testrival(List<ArrayList<Object>> roundtest,List<Integer> results,int player1,BitSet bitSet){
        int[] player=new int[4];
        player[0]=player1;
        for(int i=0;i<6;i++){
            if(!bitSet.get(i)){
                bitSet.set(i);
                int rival=1<<i;
                for(int j=6;j<12;j++){
                    if(!bitSet.get(j)){
                        int rival2=1<<j;
                        bitSet.set(j);
                        for(int k=12;k<21;k++){
                            if(!bitSet.get(k)){
                                int rival3=1<<k;
                                bitSet.set(k);
                                testplayer(roundtest,results,1,0,-1,player,rival|rival2|rival3,bitSet);
                                bitSet.set(k,false);
                            }
                        }
                        bitSet.set(j,false);
                    }
                }
                bitSet.set(i,false);
            }
        }
    }
    public static boolean testplayer(List<ArrayList<Object>> roundtest,List<Integer> results,int cursor,int innercursor,int lastchoice,int[] player,int rival,BitSet bitSet){
        if(cursor==3){
            int mask=(1<<21)-1;
            mask-=rival;
            for(int i=0;i<cursor;i++)
                mask-=player[i];
            player[3]=mask;
            int playerindex=0;
            for(int i=0;i<roundtest.size();i++){
                for(int j=0;j<roundtest.get(i).size();j++){
                    if(j!=(roundtest.get(i).size()-1)){
                        if((((int)roundtest.get(i).get(0))&(player[(playerindex+1+j)%4]))!=0)
                            return false;
                    }else{
                        if((byte)(roundtest.get(i).get(j))=='*'){
                            if((((int)roundtest.get(i).get(0))&(player[(playerindex+1+j)%4]))==0)
                                return false;
                            else{
                                if((((int)roundtest.get(i).get(0))&(player[(playerindex+1+j)%4])&((byte)(roundtest.get(i).get(j))))!=((byte)(roundtest.get(i).get(j))))
                                    return false;
                            }
                        }
                    }
                }
                playerindex=(playerindex+1)%4;
            }
            results.add(rival);
            return true;
        }

        int endcursor=(cursor==1)?4:3;
        int playertmp=player[cursor];
        for(int j=(lastchoice+1);j<21;j++){
            if(!bitSet.get(j)){
                boolean success=false;
                player[cursor]=playertmp|(1<<j);
                bitSet.set(j);
                if(innercursor==endcursor){
                    success=testplayer(roundtest,results,cursor+1,0,-1,player,rival,bitSet);
                }else{
                    success=testplayer(roundtest,results,cursor,innercursor+1,j,player,rival,bitSet);
                }
                player[cursor]=playertmp;
                bitSet.set(j,false);
                if(success==true){
                    return true;
                }
            }
        }
        return false;
    }
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int rounds=scanner.nextInt();
        scanner.nextLine();
        String[] first=scanner.nextLine().split(" ");
        BitSet bitSet=new BitSet(21);
        int player1=((1<<first[0].getBytes()[0]-'A'))|(1<<(first[1].getBytes()[0]-'A'))|(1<<(first[2].getBytes()[0]-'A'))|(1<<(first[3].getBytes()[0]-'A'))|(1<<(first[4].getBytes()[0]-'A'));
        bitSet.set(first[0].getBytes()[0]-'A');
        bitSet.set(first[1].getBytes()[0]-'A');
        bitSet.set(first[2].getBytes()[0]-'A');
        bitSet.set(first[3].getBytes()[0]-'A');
        bitSet.set(first[4].getBytes()[0]-'A');
        List<ArrayList<Object>> roundtest=new ArrayList<ArrayList<Object>>(rounds);
        List<Integer> results=new ArrayList<Integer>();
        for(int i=0;i<rounds;i++){
            String line=scanner.nextLine();
            String[] chars=line.split(" ");
            ArrayList<Object> arrayList=new ArrayList<Object>();
            arrayList.add((1<<(chars[0].getBytes()[0]-'A'))|(1<<(chars[1].getBytes()[0]-'A'))|(1<<chars[2].getBytes()[0]-'A'));
            for(int j=3;j<chars.length;j++){
                arrayList.add(chars[j].getBytes()[0]);
            }
            roundtest.add(arrayList);
        }
        testrival(roundtest,results,player1,bitSet);
        int result=0;
        for(int i=0;i<results.size();i++){
            result|=results.get(i);
        }
        StringBuilder sb=new StringBuilder();
        int murder=result&((1<<6)-1);
        if(getOnesNum(murder)>1)
            sb.append("?");
        else
            sb.append((char)(getFirstOne(murder)+'A'));
        int weapon=result=result>>6;weapon=weapon&((1<<6)-1);
        if(getOnesNum(weapon)>1)
            sb.append("?");
        else sb.append((char)(getFirstOne(weapon)+6+'A'));
        int room=result>>6;room=room&((1<<9)-1);
        if(getOnesNum(room)>1)
            sb.append("?");
        else sb.append((char)(getFirstOne(room)+12+'A'));
        System.out.println(sb);
    }
}
