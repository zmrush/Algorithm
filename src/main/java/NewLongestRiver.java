import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class NewLongestRiver {
    public static class Node{
        public int pre;
        public LinkedList<Integer> sons=new LinkedList<Integer>();
        public long diameter;
        public long smallest=Long.MAX_VALUE;
        public long path=0;
    }
    //i have three problems,first one is that variable sons need to be initialized at first;
    //second, need count to represent the count that is bigger other than middle'size which value is set not one elment;
    //third i need to optimize the structure that shorten the computation time
    public static void main(String[] args){
//        Scanner scanner=new Scanner(new FileInputStream("D:\\chrome\\download\\wf2016testdata\\wf2016testdata\\rivers\\secret\\25.in"));
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        int m=scanner.nextInt();
        String[] names=new String[n];
        TreeMap<Long,Set<Integer>> paths=new TreeMap<>();
        int[] results=new int[n];
        Node[] map=new Node[m+n+1];
        scanner.nextLine();
        for(int i=0;i<n;i++){
            String line=scanner.nextLine();
            String[] seg=line.split(" ");
            Node tmp=new Node();
            tmp.pre=Integer.valueOf(seg[1]);
            tmp.diameter=Long.valueOf(seg[2]);
            tmp.smallest=tmp.diameter;
            names[i]=seg[0];
            int pre=Integer.valueOf(seg[1]);
            map[m+1+i]=tmp;
            if(map[pre]==null){
                Node r=new Node();
                r.sons.add(m+1+i);
                map[pre]=r;
            }else{
                map[pre].sons.add(m+1+i);
            }
        }
        for(int i=1;i<=m;i++){
            String line=scanner.nextLine();
            String[] seg=line.split(" ");
            int pre=Integer.valueOf(seg[0]);
            if(map[i]==null){
                Node r=new Node();
                r.pre=pre;
                r.diameter=Long.valueOf(seg[1]);
                map[i]=r;
            }else{
                map[i].diameter=Long.valueOf(seg[1]);
                map[i].pre=pre;
            }
            if(map[pre]==null){
                Node r=new Node();
                r.sons.add(i);
                map[pre]=r;
            }else{
                map[pre].sons.add(i);
            }
        }
        //compute path;
        //--------------------------------------------------------------------------------
        Stack<Integer> stack=new Stack<Integer>();
        stack.push(0);
        while(!stack.isEmpty()){
            Integer name=stack.pop();
            if(map[name].sons!=null && map[name].sons.size()!=0){
                for (Integer son : map[name].sons) {
                    map[son].path=map[name].path+map[son].diameter;
                    stack.push(son);
                }
            }else{
                Set<Integer> sets=paths.get(map[name].path);
                if(sets!=null){
                    sets.add(name);
                }else{
                    sets=new HashSet<Integer>();
                    sets.add(name);
                    paths.put(map[name].path,sets);
                }
            }
        }
        TreeMap<Long,Set<Integer>> middle=new TreeMap<>();
        int count=0;
        for(int s=m+1;s<m+n+1;s++){
            count++;
            Set<Integer> sets=middle.get(map[s].diameter);
            if(sets!=null){
                sets.add(s);
            }else{
                sets=new HashSet<Integer>();
                sets.add(s);
                middle.put(map[s].diameter,sets);
            }
        }
        Iterator<Map.Entry<Long,Set<Integer>>> iter=paths.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<Long,Set<Integer>> entry=iter.next();
            NavigableMap<Long,Set<Integer>> pro= middle.subMap(0L,true,entry.getKey(),true);
            Iterator<Map.Entry<Long,Set<Integer>>> iter2=pro.entrySet().iterator();
            Map<Long,Set<Integer>> newAdd=new TreeMap<>();
            while(iter2.hasNext()){
                Map.Entry<Long,Set<Integer>> entry2=iter2.next();
                Set<Integer> sets=entry2.getValue();
                for(Integer s:sets){
                    count--;
                    while(true) {
                        Node tmp = map[s];
                        int pre = tmp.pre;
                        if (pre!=0) {
                            Node preNode = map[pre];
                            preNode.smallest = preNode.smallest > (tmp.smallest + preNode.diameter) ? (tmp.smallest + preNode.diameter) : preNode.smallest;
                            preNode.sons.pop();
                            if (preNode.sons.isEmpty()) {
                                if(preNode.smallest<=entry.getKey())
                                    s=pre;
                                else{
                                    //add and break
                                    Set<Integer> newAddSet=newAdd.get(preNode.smallest);
                                    if(newAddSet==null){
                                        newAddSet=new HashSet<Integer>();
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
            Iterator<Map.Entry<Long,Set<Integer>>> iter3=newAdd.entrySet().iterator();
            while(iter3.hasNext()){
                Map.Entry<Long,Set<Integer>> entry3=iter3.next();
                count+=entry3.getValue().size();
                Set<Integer> set3=middle.get(entry3.getKey());
                if(set3==null){
                    set3=entry3.getValue();
                    middle.put(entry3.getKey(),set3);
                }else{
                    set3.addAll(entry3.getValue());
                }
            }
            Set<Integer> res=entry.getValue();
            for(Integer s:res){
                results[s-m-1]=count+1;
            }
        }
        for(int s=0;s<n;s++){
            System.out.println(names[s]+" "+results[s]);
        }
        //---------------------------------------------------------------------
    }
} 