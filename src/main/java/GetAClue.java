import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by mingzhu7 on 2018/1/18.
 */
public class GetAClue {
    public static class Information{
        public int number;
        Set<Byte> include=new HashSet<Byte>();
        Set<Byte> exclude=new HashSet<Byte>();
        LinkedList<byte[]> possible=new LinkedList<byte[]>();
        public Information(int count){
            this.number=count;
        }
    }
    public static class Composite{
        public byte[] key;
        public LinkedList<Integer> ids=new LinkedList<>();
    }
    public static void fill(int[] murder,int[] weapon,int[] room,Information[] players){
        for(int i=0;i<('U'-'A'+1);i++) {
            for(int j=0;j<players.length;j++) {
                if (i < 6) {
                    if (players[j].include.contains((byte) ('A' + i)))
                        murder[i] = 1;
                } else if (i < 12) {
                    if (players[j].include.contains((byte) ('A' + i)))
                        weapon[i - 6] = 1;
                } else {
                    if (players[j].include.contains((byte) ('A' + i)))
                        room[i - 12] = 1;
                }
            }
            //extra include:
        }
    }
    public void simplify(Information cur,Information[] players){
    }
    public static int getPossiblity(byte[] chars,Information[] players,int curIndex,int[] notpossible){
        int len=chars.length;
        int possble=len;
        for(int l=0;l<len;l++) {
            for (int k = 0; k < 4; k++) {
                if (k != curIndex) {
                    if (players[k].include.contains(chars[l])){
                        notpossible[l]=1;possble-=1;break;
                    }
                }else if(players[k].exclude.contains(chars[l])){
                    notpossible[l]=1;possble-=1;break;
                }else if(players[k].include.contains(chars[l])){
                    possble=0;break;//if self include,well it contains no information
                }
            }
        }
        return possble;
    }
    public static void main(String[] args){
        Information[] players=new Information[4];
        players[0]=new Information(5);
        players[1]=new Information(5);
        players[2]=new Information(4);
        players[3]=new Information(4);
        int[] murder=new int[6];
        int[] weapon=new int[6];
        int[] room=new int[9];
        Scanner scanner=new Scanner(System.in);
        int rounds=scanner.nextInt();
        scanner.nextLine();
        String[] first=scanner.nextLine().split(" ");
        players[0].include.add(first[0].getBytes()[0]);
        players[0].include.add(first[1].getBytes()[0]);
        players[0].include.add(first[2].getBytes()[0]);
        players[0].include.add(first[3].getBytes()[0]);
        players[0].include.add(first[4].getBytes()[0]);
        for(int i=0;i<('U'-'A'+1);i++){
            if(!players[0].include.contains((byte)('A'+i)))
                players[0].exclude.add((byte)('A'+i));
        }
        int player_index=0;
        for(int i=0;i<rounds;i++){
            String line=scanner.nextLine();
            String[] chars=line.split(" ");
            for(int j=3;j<chars.length;j++){
                if(chars[j].charAt(0)=='-'){
                    players[(player_index+(j-3)+1)%4].exclude.add(chars[0].getBytes()[0]);
                    players[(player_index+(j-3)+1)%4].exclude.add(chars[1].getBytes()[0]);
                    players[(player_index+(j-3)+1)%4].exclude.add(chars[2].getBytes()[0]);
                }else if(chars[j].charAt(0)=='*'){
                    int[] notpossible=new int[3];
                    byte[] cc=new byte[3];
                    cc[0]=chars[0].getBytes()[0];cc[1]=chars[1].getBytes()[0];cc[2]=chars[2].getBytes()[0];
                    int possble=getPossiblity(cc,players,(player_index+(j-3)+1)%4,notpossible);
                    if(possble==1){
                        for(int l=0;l<3;l++){
                            if(notpossible[l]==0){
                                players[(player_index+(j-3)+1)%4].include.add(chars[l].getBytes()[0]);
                            }
                        }
                    }else if(possble>1){
                        byte[] tmp=new byte[possble];
                        int cursor=0;
                        for(int l=0;l<3;l++){
                            if(notpossible[l]==0){
                                tmp[cursor++]=chars[l].getBytes()[0];
                            }
                        }
                        players[(player_index+(j-3)+1)%4].possible.add(tmp);
                    }else{

                    }
                }else{
                    players[(player_index+(j-3)+1)%4].include.add(chars[j].getBytes()[0]);
                }
            }
            player_index=(player_index+1)%4;
        }
        //personal information simplify
        //exclude  include
        boolean find=false;
        Set<Character> exludes=new HashSet<Character>();
        for(int i=0;i<21;i++){

        }
        if(find==true){
            return;
        }
        //include combine
        if(find==true){
            return;
        }
        //fill
    }
}
