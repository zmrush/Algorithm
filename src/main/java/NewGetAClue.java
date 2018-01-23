import java.util.*;
import java.util.stream.Collectors;

public class NewGetAClue {
    public static class Information{
        public int number;
        int include;
        int exclude;
        public Information(int count){
            this.number=count;
        }
    }
    public static void innerLoop(ArrayList<Integer> include,Integer j,HashSet<Integer> methods,Integer now,int howMany,int upper){
        if(j>(include.size()-1)){
            if(howMany==upper){
                methods.add(now);
            }
            return;
        }
        if((now&include.get(j))!=0){
            innerLoop(include,j+1,methods,now,howMany,upper);
        }else if(howMany>=upper){
            return;// no possiblity
        }else{
            int tmp=include.get(j)-now&include.get(j);
            int ones=getOneNumers(tmp);
            if(ones==1){
                innerLoop(include,j+1,methods,now|tmp,howMany+1,upper);
            }else{
                int[] t=getDisperate(tmp);
                for(int i=0;i<t.length;i++){
                    innerLoop(include,j+1,methods,now|t[i],howMany+1,upper);
                }
            }
        }
    }
    public static boolean innerSimplify(ArrayList<ArrayList<Integer>> extra,Information[] players){
        boolean change=false;
        for(int i=0;i<extra.size();i++){
            Integer tmp=0;
            HashSet<Integer> methods=new HashSet<Integer>();
            innerLoop(extra.get(i),0,methods,tmp,getOneNumers(players[i+1].include),players[i+1].number);
            if(methods.size()==1 && methods.iterator().next()!=0){
                players[i+1].include|=methods.iterator().next();
                change=true;
            }
        }
        return change;
    }
    public static void simplify(int curIndex,int chars,int condition,Information[] players,List<Integer> extraInclude,Map<Integer,HashSet<Integer>> map){
        if(condition==-1){
            if((players[curIndex].exclude&(chars))==chars)
                return;
            players[curIndex].exclude|=chars;
            if(getOneNumers(players[curIndex].exclude)==(21-players[curIndex].number)){
                players[curIndex].include=(1<<21)-1-players[curIndex].exclude;
            }
            simplify(curIndex,0,1,players,extraInclude,map);
        }else if(condition==1){
            while(true) {
                boolean change=false;
                Iterator<Integer> iter = map.keySet().iterator();
                ArrayList<ArrayList<Integer>> extra=new ArrayList<ArrayList<Integer>>();
                extra.add(new ArrayList<Integer>());
                extra.add(new ArrayList<Integer>());
                extra.add(new ArrayList<Integer>());
                while (iter.hasNext()) {
                    int key=iter.next();
                    Iterator<Integer> iter2=map.get(key).iterator();
                    while(iter2.hasNext()){
                        int player_cursor=iter2.next();
                        if((players[player_cursor].include&key)!=0){
                            map.get(key).remove(player_cursor);
                            if(map.get(key).size()<=0)
                                map.remove(key);
                            change=true;
                            break;
                        }
                        int tmp=0;
                        for(int i=0;i<4;i++){
                            if(i!=player_cursor){
                                tmp|=players[i].include;
                            }else{
                                tmp|=players[i].exclude;
                            }
                        }
                        if((tmp&key)!=0){
                            map.get(key).remove(player_cursor);
                            if(map.get(key).size()<=0)
                                map.remove(key);
                            if(getOneNumers(key-(tmp&key))==1){
                                players[player_cursor].include|=key-(tmp&key);
                            }else{
                                if(map.containsKey(key-(tmp&key))){
                                    map.get(key-(tmp&key)).add(player_cursor);
                                }else{
                                    HashSet<Integer> set=new HashSet<Integer>();
                                    set.add(player_cursor);
                                    map.put(key-(tmp&key),set);
                                }
                            }
                            change=true;
                            break;
                        }
                        extra.get(player_cursor-1).add(key);
                    }
                    if(change==true)
                        break;
                }
                if(change==true)
                    continue;
                change=innerSimplify(extra,players);
                if(change==false)
                    break;
            }
        }else if(condition==0){
            if((players[curIndex].include&chars)==chars)
                return;
            players[curIndex].include|=chars;
            if(getOneNumers(players[curIndex].include)==players[curIndex].number)
                players[curIndex].exclude=(1<<21)-1-players[curIndex].include;
            simplify(curIndex,0,1,players,extraInclude,map);

        }
        else if(condition==2){
            Iterator<Integer> iter = map.keySet().iterator();
            while (iter.hasNext()) {
                Integer key=iter.next();
                if(map.get(key).size()==getOneNumers(key)){
                    extraInclude.set(0,extraInclude.get(0)|key);
                }
                if(getOneNumers(key)==2){
                    int[] t=getDisperate(key);
                    for(int i=0;i<21;i++){
                        if(((1<<i)&key)==0){
                            HashSet<Integer> newSet=new HashSet<Integer>();
                            newSet.addAll(map.get(key).stream().map((Object ss)-> (Integer)ss).collect(Collectors.toList()));
                            if(map.containsKey(t[0]+(1<<i)))
                                newSet.addAll(map.get(t[0]+(1<<i)).stream().map((Object ss)-> (Integer)ss).collect(Collectors.toList()));
                            if(map.containsKey(t[1]+(1<<i)))
                                newSet.addAll(map.get(t[1]+(1<<i)).stream().map((Object ss)-> (Integer)ss).collect(Collectors.toList()));
                            if(newSet.size()==3){
                                extraInclude.set(0,extraInclude.get(0)|(key+(1<<i)));
                            }
                        }
                    }
                }else{
                    HashSet<Integer> newSet=new HashSet<Integer>();
                    newSet.addAll(map.get(key).stream().map((Object ss)-> (Integer)ss).collect(Collectors.toList()));
                    int[] t=getThree(key);
                    if(map.containsKey(t[0])){
                        newSet.addAll(map.get(t[0]).stream().map((Object ss)-> (Integer)ss).collect(Collectors.toList()));
                    }
                    if(map.containsKey(t[1])){
                        newSet.addAll(map.get(t[1]).stream().map((Object ss)-> (Integer)ss).collect(Collectors.toList()));
                    }
                    if(map.containsKey(t[2])){
                        newSet.addAll(map.get(t[2]).stream().map((Object ss)-> (Integer)ss).collect(Collectors.toList()));
                    }
                    if(newSet.size()==3){
                        extraInclude.set(0,extraInclude.get(0)|key);
                    }
                }
            }
        }
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
    public static int getFirstOne(int x){
        int t=0;
        while((x&1)!=1){
            t++;
            x>>=1;
        }
        return t;
    }
    public static int[] getDisperate(int x){
        int[] t=new int[getOneNumers(x)];
        int number=0;
        int index=0;
        while(x!=0){
            if((x&1)!=0)
                t[index++]=1<<number;
            number++;
            x>>=1;
        }
        return t;
    }
    public static int[] getThree(int x){
        int[] t=new int[3];
        int number=0;
        int index=0;
        while(x!=0){
            if((x&1)!=0)
                t[index++]=1<<number;
            number++;
            x>>=1;
        }
        int[] res=new int[3];
        res[0]=t[0]+t[1];
        res[1]=t[0]+t[2];
        res[2]=t[1]+t[2];
        return res;
    }
    public static void getResult(int x,StringBuilder sb){
        int t=0;
        while(x!=0){
            if((x&1)!=0)
                sb.append((char)('A'+t));
            t++;
            x>>=1;
        }
    }
    public static void getResult2(int x,int y,StringBuilder sb){
        if(getOneNumers(x&((1<<6)-1))!=5 && (y&((1<<6)-1))==0)
            sb.append("?");
        else{
            if(getOneNumers(x&((1<<6)-1))==5)
                sb.append((char)(getFirstZero((x&((1<<6)-1)),1<<6)+'A'));
            else
                sb.append((char)(getFirstOne(y)+'A'));
        }
        x>>=6;
        y>>=6;
        if(getOneNumers(x&((1<<6)-1))!=5 && (y&((1<<6)-1))==0){
            sb.append("?");
        }else{
            if(getOneNumers(x&((1<<6)-1))==5)
                sb.append((char)(getFirstZero((x&((1<<6)-1)),1<<6)+6+'A'));
            else
                sb.append((char)(getFirstOne(y)+6+'A'));
        }
        x>>=6;
        y>>=6;
        if(getOneNumers(x)!=8 && (y*((1<<9)-1))==0){
            sb.append("?");
        }else{
            if(getOneNumers(x)==8)
                sb.append((char)(getFirstZero(x,1<<9)+12+'A'));
            else{
                sb.append((char)(getFirstOne(y)+12+'A'));
            }
        }
    }
    public static void main(String[] args){
        Information[] players=new Information[4];
        players[0]=new Information(5);
        players[1]=new Information(5);
        players[2]=new Information(4);
        players[3]=new Information(4);
        Scanner scanner=new Scanner(System.in);
        int rounds=scanner.nextInt();
        scanner.nextLine();
        String[] first=scanner.nextLine().split(" ");
        players[0].include|=1<<(first[0].getBytes()[0]-'A');
        players[0].include|=1<<(first[1].getBytes()[0]-'A');
        players[0].include|=1<<(first[2].getBytes()[0]-'A');
        players[0].include|=1<<(first[3].getBytes()[0]-'A');
        players[0].include|=1<<(first[4].getBytes()[0]-'A');
        players[0].exclude=(1<<21)-1-players[0].include;
        int player_index=0;
        List<Integer> extraInclude=new ArrayList<Integer>();
        extraInclude.add(0);
        Map<Integer,HashSet<Integer>> map=new HashMap<Integer,HashSet<Integer>>();
        for(int i=0;i<rounds;i++){
            String line=scanner.nextLine();
            String[] chars=line.split(" ");
            for(int j=3;j<chars.length;j++){
                if(chars[j].charAt(0)=='-'){
                    int tmp=0;
                    tmp|=1<<(chars[0].getBytes()[0]-'A');
                    tmp|=1<<(chars[1].getBytes()[0]-'A');
                    tmp|=1<<(chars[2].getBytes()[0]-'A');
                    simplify((player_index+(j-3)+1)%4,tmp,-1,players,extraInclude,map);
                }else if(chars[j].charAt(0)=='*'){
                    int tmp=0;
                    tmp|=1<<(chars[0].getBytes()[0]-'A');
                    tmp|=1<<(chars[1].getBytes()[0]-'A');
                    tmp|=1<<(chars[2].getBytes()[0]-'A');
                    if(map.containsKey(tmp)){
                        map.get(tmp).add((player_index+(j-3)+1)%4);
                    }else{
                        int tmp1=tmp-(1<<(chars[0].getBytes()[0]-'A'));
                        int tmp2=tmp-(1<<(chars[1].getBytes()[0]-'A'));
                        int tmp3=tmp-(1<<(chars[2].getBytes()[0]-'A'));
                        if((map.containsKey(tmp1) &&map.get(tmp1).contains((player_index+(j-3)+1)%4)) ||
                                (map.containsKey(tmp2) &&map.get(tmp2).contains((player_index+(j-3)+1)%4)) ||
                                (map.containsKey(tmp3) && map.get(tmp2).contains((player_index+(j-3)+1)%4)))
                            continue;//no information gain
                        HashSet<Integer> set=new HashSet<Integer>();
                        set.add((player_index+(j-3)+1)%4);
                        map.put(tmp,set);
                    }
                    simplify((player_index+(j-3)+1)%4,tmp,1,players,extraInclude,map);

                }else{
                    int tmp=0;
                    tmp|=1<<(chars[j].getBytes()[0]-'A');
                    simplify((player_index+(j-3)+1)%4,tmp,0,players,extraInclude,map);
                }
            }
            player_index=(player_index+1)%4;
        }
        simplify(0,0,2,players,extraInclude,map);
        StringBuilder sb=new StringBuilder();
        getResult2(extraInclude.get(0)|players[0].include|players[1].include|players[2].include|players[3].include,players[0].exclude&players[1].exclude&players[2].exclude&players[3].exclude,sb);
        System.out.println(sb);
    }
} 