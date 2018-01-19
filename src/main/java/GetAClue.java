import java.util.*;

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
        public Composite(byte[] key){
            this.key=key;
        }
        @Override
        public int hashCode(){
            int count=0;
            for(int i=0;i<key.length;i++)
                count+=key[i];
            return count;
        }
        @Override
        public boolean equals(Object obj){
            if(!(obj instanceof Composite))
                return false;
            Composite tmp=(Composite)obj;
            if(key.length!=tmp.key.length)
                return false;
            for(int i=0;i<key.length;i++)
                if(key[i]!=tmp.key[i])
                    return false;
            return true;
        }

    }
    public static void fill(int[] tmp,Information[] players,HashSet<Byte> extraInclude){
        for(int i=0;i<('U'-'A'+1);i++) {
            if(extraInclude.contains((byte)('A'+i))){
                if (i < 6) {
                    //murder[i] = 1;
                    tmp[0]|=1<<i;
                } else if (i < 12) {
                    tmp[1]|=1<<(i-6);
                } else {
                    tmp[2]|=1<<(i-12);
                }
                continue;
            }
            for(int j=0;j<players.length;j++) {
                if(players[j].include.contains((byte) ('A' + i))){
                    if (i < 6) {
                        //murder[i] = 1;
                        tmp[0]|=1<<i;
                    } else if (i < 12) {
                        tmp[1]|=1<<(i-6);
                    } else {
                        tmp[2]|=1<<(i-12);
                    }
                    break;
                }
            }
        }
    }
    public static boolean isCross(byte[] left,byte[] right){
        int i=0;int j=0;
        while(i<left.length&& j<right.length){
            if(left[i]==right[j])
                return true;
            else if(left[i]<right[j]){
                i++;
            }else
                j++;
        }
        return false;
    }
    public static void simplify(int curIndex,byte[] chars,int condition,Information[] players,HashSet<Byte> extraInclude,Map<Composite,HashSet<Integer>> map){
        if(condition==-1){
            while(true) {
                boolean change=false;
                Iterator<Composite> iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    Composite composite=iter.next();
                    if(isCross(composite.key,chars)){
                        if(map.get(composite).contains(curIndex)){
                            map.get(composite).remove(curIndex);
                            simplify(curIndex,composite.key,1,players,extraInclude,map);
                            change=true;
                            break;
                        }
                    }
                }
                if(change==false)
                    break;
            }
        }else if(condition==1){
            int[] notpossible=new int[chars.length];
            int possble=getPossiblity(chars,players,curIndex,notpossible);
            if(possble==1){
                for(int l=0;l<chars.length;l++){
                    if(notpossible[l]==0){
                        players[curIndex].include.add(chars[l]);
                        byte[] cc=new byte[1];
                        cc[0]=chars[l];
                        simplify(curIndex,cc,0,players,extraInclude,map);
                        break;
                    }
                }
            }else if(possble>1){
                byte[] tmp=new byte[possble];
                int cursor=0;
                for(int l=0;l<3;l++){
                    if(notpossible[l]==0){
                        tmp[cursor++]=chars[l];
                    }
                }
                Composite composite=new Composite(tmp);
                if(!map.containsKey(composite)){
                    HashSet<Integer> hashSet=new HashSet<Integer>();
                    hashSet.add(curIndex);
                    map.put(composite,hashSet);
                }else{
                    map.get(composite).add(curIndex);
                }
                if(map.get(composite).size()==composite.key.length){
                    for(byte bb:composite.key){
                        extraInclude.add(bb);
                    }
                    map.remove(composite);
                }else{

                }
                if(composite.key.length==2){

                }else{//3

                }
            }else{ //<=0,nothing

            }
        }else if(condition==0){
            if(extraInclude.contains(chars[0])){
                extraInclude.remove(chars[0]);
            }
            while(true) {
                boolean change=false;
                Iterator<Composite> iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    Composite composite=iter.next();
                    if(isCross(composite.key,chars)){
                        if(map.get(composite).contains(curIndex)){
                            map.get(composite).remove(curIndex);
                            simplify(curIndex,composite.key,1,players,extraInclude,map);
                            change=true;
                            break;
                        }
                    }
                }
                if(change==false)
                    break;
            }
        }
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
    public static int getOneNumers(int x){
        int number=0;
        while(x!=0){
            number++;
            x=x&(x-1);
        }
        return number;
    }
    public static int getFirstZero(int x,int mm){
        int t=mm-1-x;
        int number=0;
        while((t>>=1)!=0)
            number++;
        return number;
    }
    public static void main(String[] args){
        Information[] players=new Information[4];
        players[0]=new Information(5);
        players[1]=new Information(5);
        players[2]=new Information(4);
        players[3]=new Information(4);
        int murder=0;
        int weapon=0;
        int room=0;
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
        HashSet<Byte> extraInclude=new HashSet<Byte>();
        Map<Composite,HashSet<Integer>> map=new HashMap<Composite,HashSet<Integer>>();
        for(int i=0;i<rounds;i++){
            String line=scanner.nextLine();
            String[] chars=line.split(" ");
            for(int j=3;j<chars.length;j++){
                if(chars[j].charAt(0)=='-'){
                    players[(player_index+(j-3)+1)%4].exclude.add(chars[0].getBytes()[0]);
                    players[(player_index+(j-3)+1)%4].exclude.add(chars[1].getBytes()[0]);
                    players[(player_index+(j-3)+1)%4].exclude.add(chars[2].getBytes()[0]);
                    byte[] cc=new byte[3];
                    cc[0]=chars[0].getBytes()[0];cc[1]=chars[1].getBytes()[0];cc[2]=chars[2].getBytes()[0];
                    simplify((player_index+(j-3)+1)%4,cc,-1,players,extraInclude,map);
                }else if(chars[j].charAt(0)=='*'){
                    byte[] cc=new byte[3];
                    cc[0]=chars[0].getBytes()[0];cc[1]=chars[1].getBytes()[0];cc[2]=chars[2].getBytes()[0];
                    simplify((player_index+(j-3)+1)%4,cc,1,players,extraInclude,map);

                }else{
                    players[(player_index+(j-3)+1)%4].include.add(chars[j].getBytes()[0]);
                    byte[] cc=new byte[1];
                    cc[0]=chars[j].getBytes()[0];
                    simplify((player_index+(j-3)+1)%4,cc,0,players,extraInclude,map);
                }
            }
            player_index=(player_index+1)%4;
        }
        //exclude  cross
        int exclude_num=0;
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<21;i++){
            if(players[0].exclude.contains((byte)('A'+i)) && players[1].exclude.contains((byte)('A'+i)) && players[2].exclude.contains((byte)('A'+i)) && players[3].exclude.contains((byte)('A'+i)) && !extraInclude.contains((byte)('A'+i))) {
                exclude_num++;
                sb.append((char)('A'+i));
            }
        }
        if(exclude_num==3){
            System.out.println(sb);
            return;
        }
        //include combine
        int include_num=0;
        sb=new StringBuilder();
        for(int i=0;i<21;i++){
            if(extraInclude.contains((byte)('A'+i))) {
                include_num++;
                continue;
            }
            for(int j=0;j<4;j++){
                if(players[j].include.contains((byte)('A'+i))){
                    include_num++;
                    break;
                }
            }
        }
        if(include_num==18){
            for(int i=0;i<21;i++){
                if(!players[0].include.contains((byte)('A'+i)) && !players[1].include.contains((byte)('A'+i)) && !players[2].include.contains((byte)('A'+i)) && !players[3].include.contains((byte)('A'+i)) && !extraInclude.contains((byte)('A'+i))) {
                    sb.append((char)('A'+i));
                }
            }
            System.out.println(sb);
            return;
        }
        int[] tmp=new int[3];
        fill(tmp,players,extraInclude);
        murder=tmp[0];weapon=tmp[1];room=tmp[2];
        if(getOneNumers(murder)==5){
            System.out.print((char)(getFirstZero(murder,64)+'A'));
        }else{
            System.out.print("?");
        }
        if(getOneNumers(weapon)==5){
            System.out.print((char)(getFirstZero(murder,64)+'G'));
        }else{
            System.out.print("?");
        }
        if(getOneNumers(room)==8){
            System.out.print((char)(getFirstZero(room,512))+'M');
        }else{
            System.out.print('?');
        }
    }
}
