import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class LongestRiver {
    public static class Node{
        public String pre;
        public Set<String> sons;
        public long diameter;
        public long smallest=Long.MAX_VALUE;
        public long path=0;
    }
    public static void main(String[] args)throws FileNotFoundException{
        Long start=System.currentTimeMillis();
        Scanner scanner=new Scanner(new FileInputStream("D:\\chrome\\download\\wf2016testdata\\wf2016testdata\\rivers\\secret\\25.in"));
        int n=scanner.nextInt();
        int m=scanner.nextInt();
        String[] names=new String[n];
        TreeMap<Long,Set<String>> paths=new TreeMap<Long,Set<String>>();
        Map<String,Integer> results=new HashMap<String,Integer>();
        Map<String,Node> map=new HashMap<String,Node>();
        scanner.nextLine();
        for(int i=0;i<n;i++){
            String line=scanner.nextLine();
            String[] seg=line.split(" ");
            Node tmp=new Node();
            tmp.pre=seg[1];
            tmp.diameter=Long.valueOf(seg[2]);
            tmp.smallest=tmp.diameter;
            map.put(seg[0],tmp);
            names[i]=seg[0];
            if(map.get(seg[1])==null){
                Node r=new Node();
                r.sons=new HashSet<String>();
                r.sons.add(seg[0]);
                map.put(seg[1],r);
            }else{
                map.get(seg[1]).sons.add(seg[0]);
            }
        }
        for(int i=1;i<=m;i++){
            String name=String.valueOf(i);
            String line=scanner.nextLine();
            String[] seg=line.split(" ");
            if(map.get(name)==null){
                Node r=new Node();
                r.pre=seg[0];
                r.diameter=Long.valueOf(seg[1]);
                r.sons=new HashSet<String>();
                map.put(name,r);
            }else{
                map.get(name).diameter=Long.valueOf(seg[1]);
                map.get(name).pre=seg[0];
            }
            if(map.get(seg[0])==null){
                Node r=new Node();
                r.sons=new HashSet<String>();
                r.sons.add(name);
                map.put(seg[0],r);
            }else{
                map.get(seg[0]).sons.add(name);
            }
        }
        //compute path;
        //--------------------------------------------------------------------------------
        Stack<String> stack=new Stack<String>();
        stack.push("0");
        while(!stack.isEmpty()){
            String name=stack.pop();
            if(map.get(name).sons!=null && map.get(name).sons.size()!=0){
                for (String son : map.get(name).sons) {
                    map.get(son).path=map.get(name).path+map.get(son).diameter;
                    stack.push(son);
                }
            }else{
                Set<String> sets=paths.get(map.get(name).path);
                if(sets!=null){
                    sets.add(name);
                }else{
                    sets=new HashSet<String>();
                    sets.add(name);
                    paths.put(map.get(name).path,sets);
                }
            }
        }
        TreeMap<Long,Set<String>> middle=new TreeMap<>();
        int count=0;
        for(String s:names){
            count++;
            Set<String> sets=middle.get(map.get(s).diameter);
            if(sets!=null){
                sets.add(s);
            }else{
                sets=new HashSet<String>();
                sets.add(s);
                middle.put(map.get(s).diameter,sets);
            }
        }
        Iterator<Map.Entry<Long,Set<String>>> iter=paths.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<Long,Set<String>> entry=iter.next();
            NavigableMap<Long,Set<String>> pro= middle.subMap(0L,true,entry.getKey(),true);
            Iterator<Map.Entry<Long,Set<String>>> iter2=pro.entrySet().iterator();
            Map<Long,Set<String>> newAdd=new TreeMap<Long,Set<String>>();
            while(iter2.hasNext()){
                Map.Entry<Long,Set<String>> entry2=iter2.next();
                Set<String> sets=entry2.getValue();
                for(String s:sets){
                    count--;
                    while(true) {
                        Node tmp = map.get(s);
                        String pre = tmp.pre;
                        if (!"0".equals(pre)) {
                            Node preNode = map.get(pre);
                            preNode.smallest = preNode.smallest > (tmp.smallest + preNode.diameter) ? (tmp.smallest + preNode.diameter) : preNode.smallest;
                            preNode.sons.remove(s);
                            if (preNode.sons.isEmpty()) {
                                if(preNode.smallest<=entry.getKey())
                                    s=pre;
                                else{
                                    //add and break
                                    Set<String> newAddSet=newAdd.get(preNode.smallest);
                                    if(newAddSet==null){
                                        newAddSet=new HashSet<String>();
                                        newAddSet.add(pre);
                                        newAdd.put(preNode.smallest,newAddSet);
                                    }else{
                                        newAddSet.add(pre);
                                    }
                                    break;
                                }
                            }else{
                                break;
                            }
                        }else{
                            break;
                        }
                    }
                }
            }
            pro.clear();
            Iterator<Map.Entry<Long,Set<String>>> iter3=newAdd.entrySet().iterator();
            while(iter3.hasNext()){
                Map.Entry<Long,Set<String>> entry3=iter3.next();
                count+=entry3.getValue().size();
                Set<String> set3=middle.get(entry3.getKey());
                if(set3==null){
                    set3=entry3.getValue();
                    middle.put(entry3.getKey(),set3);
                }else{
                    set3.addAll(entry3.getValue());
                }
            }
            Set<String> res=entry.getValue();
            for(String s:res){
                results.put(s,count+1);
            }
        }
        for(String s:names){
            System.out.println(s+" "+results.get(s));
        }
        //---------------------------------------------------------------------
        Long end=System.currentTimeMillis();
        System.out.println(end-start);
    }
} 