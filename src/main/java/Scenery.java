import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by mingzhu7 on 2017/12/13.
 */
public class Scenery {
    public static class Pair{
        public int start;
        public int end;
    }
    public static void quicksort(Pair[] pairs,int start,int end){
        if(start>=end)
            return;
        int left=start;
        int right=end;
        Pair middle=pairs[start];
        while(left<right){
            while(left<right && pairs[right].start>=middle.start)
                right--;
            pairs[left]=pairs[right];
            while(left<right && pairs[left].start<=middle.start)
                left++;
            pairs[right]=pairs[left];
        }
        pairs[left]=middle;
        quicksort(pairs,start,left-1);
        quicksort(pairs,left+1,end);
    }
    public static void copy(Pair[] pairs2,Pair[] pairs,int start){
        for(int i=0;i<pairs.length;i++){
            pairs[i]=new Pair();
            pairs[i].start=pairs2[i+start].start;
            pairs[i].end=pairs2[i+start].end;
        }
    }
    public static boolean pickFirstAndCopy(Pair[] pairs2,int start,int length,int queuesize){
        boolean find=false;
        for(int j=0;j<queuesize;j++){
            Pair[] pairs=new Pair[pairs2.length-start];
            copy(pairs2,pairs,start);
            Pair tmp = pairs[j];
            for (int i = j; i >= 1; i--) {
                pairs[i] = pairs[i - 1];
            }
            pairs[0] = tmp;
            //------------------------------------------------
            for (int i = 1; i < pairs.length; i++) {
                pairs[i].start = Integer.max(pairs[0].start + length, pairs[i].start);
                if (pairs[i].end - pairs[i].start < length)
                    return false;
            }
            //return pickFirst(pairs, start + 1, length);
            if(pickFirst(pairs,1,length)){
                find=true;
                break;
            }
        }
        if(find==true)
            return true;
        else
            return false;
    }
    public static boolean pickFirst(Pair[] pairs,int start,int length){
        mainloop:while(true) {
//            int first = start + 1;
//            while (first < pairs.length) {
//                if (pairs[first].start > pairs[start].end - length * 2)
//                    break;
//                first++;
//            }
//            if (start == pairs.length - 1)
//                return true;
//            LinkedList<Integer> queue = new LinkedList<Integer>();
//            queue.add(start);
//            for (int i = start + 1; i < first; i++) {
//                if (pairs[i].end <= pairs[queue.peek()].end) {
//                    while (!queue.isEmpty() && (pairs[i].end - 2 * length < pairs[queue.peek()].start))
//                        queue.pop();
//                    queue.push(i);
//                }
//            }
            if (start == pairs.length - 1) {
                if(pairs[start].end>=pairs[start].start+length)
                    return true;
                return false;
            }
            LinkedList<Integer> queue = new LinkedList<Integer>();
            queue.push(start);
            int first=start+1;
            while(first<pairs.length){
                if((pairs[first].start>pairs[queue.peek()].end-length*2) || (pairs[first].start>pairs[start].start+length))
                    break;
                if (pairs[first].end <= pairs[queue.peek()].end) {
                    while (!queue.isEmpty() && (pairs[first].end - 2 * length < pairs[queue.peek()].start))
                        queue.pop();
                    queue.push(first);
                }
                first++;
            }
            if (queue.size() == 1) {
                //-----------------------------------------------
                Pair tmp = pairs[queue.peek()];
                for (int i = queue.peek(); i >= start + 1; i--) {
                    pairs[i] = pairs[i - 1];
                }
                pairs[start] = tmp;
                //------------------------------------------------
                for (int i = start + 1; i < pairs.length; i++) {
                    pairs[i].start = Integer.max(pairs[start].start + length, pairs[i].start);
                    if (pairs[i].end - pairs[i].start < length)
                        return false;
                }
                //return pickFirst(pairs, start + 1, length);
                start=start+1;
                continue mainloop;
            } else if (queue.size() > 1) {
                //we try all possibility
                return pickFirstAndCopy(pairs,start,length,queue.size());
                //return pickFirst(pairs, start+1, length);
            }
            return false;
        }
    }
    public static class Cn{
        public int E;//end
        public int C;//c
        public int N;//how many are in this interval from  last start s to this end
    }
    public static boolean pick(Pair[] pairs,int length){
        //List<Pair> blocks=new ArrayList<Pair>();
        TreeMap<Integer,Pair> blocks=new TreeMap<Integer,Pair>();//block start map to block startn and end
        LinkedList<Cn> cns=new LinkedList<Cn>();
        for(int i=pairs.length-1;i>=0;i--){
            Pair cur=pairs[i];
            int lowerNum=0;
            boolean hasEqual=false;
            int minLower=Integer.MAX_VALUE;
            Iterator<Cn> iter=cns.iterator();
            while(iter.hasNext()){
                Cn lower=iter.next();
                if(lower.E>cur.end){
                    lower.N=lower.N+1;
                    lower.C=lower.C-length;
                    //----------------------------
                    Map.Entry<Integer,Pair> pairEntry=blocks.lowerEntry(lower.C);
                    if(pairEntry!=null && lower.C>pairEntry.getValue().start && lower.C<pairEntry.getValue().end)
                        lower.C=pairEntry.getValue().start;
                    if(lower.C<cur.start)
                        return false;
                    //----------------------------
                    minLower=lower.C<minLower?lower.C:minLower;
                }else if(lower.E==cur.end){
                    hasEqual=true;
                    lower.N=lower.N+1;
                    lower.C=lower.C-length;
                    //--------------------------
                    Map.Entry<Integer,Pair> pairEntry=blocks.lowerEntry(lower.C);
                    if(pairEntry!=null &&  lower.C>pairEntry.getValue().start && lower.C<pairEntry.getValue().end)
                        lower.C=pairEntry.getValue().start;
                    if(lower.C<cur.start)
                        return false;
                    //--------------------------
                    minLower=lower.C<minLower?lower.C:minLower;
                }else{
                    lowerNum=lower.N>lowerNum?lower.N:lowerNum;
                }
            }
            if(hasEqual==false){
                Cn cn=new Cn();
                cn.N=lowerNum+1;
                cn.E=cur.end;
//                cn.C=cn.E-length*cn.N; //this has some problem
//                //----------------
//                Map.Entry<Integer,Pair> pairEntry=blocks.lowerEntry(cn.C);
//                if(pairEntry!=null && cn.C>pairEntry.getValue().start && cn.C<pairEntry.getValue().end)
//                    cn.C=pairEntry.getValue().start;
                cn.C=cn.E;
                for(int j=0;j<cn.N;j++){
                    cn.C=cn.C-length;
                    Map.Entry<Integer,Pair> pairEntry=blocks.lowerEntry(cn.C);
                    if(pairEntry!=null && cn.C>pairEntry.getValue().start && cn.C<pairEntry.getValue().end)
                        cn.C=pairEntry.getValue().start;
                }
                if(cn.C<cur.start)
                    return false;
                //----------------
                cns.add(cn);
                minLower=cn.C<minLower?cn.C:minLower;
            }
            if(minLower-length<cur.start){
                Pair block=new Pair();
                block.start=minLower-length;
                block.end=cur.start;
                Map.Entry<Integer,Pair> firstEntry=blocks.firstEntry();
                if(firstEntry!=null && block.end>firstEntry.getValue().start){
                    block.end=firstEntry.getValue().end;
                    blocks.remove(firstEntry.getKey());
                    blocks.put(block.start,block);
                }else{
                    blocks.put(block.start,block);
                }
            }
        }
        return true;
    }
    public static void main(String[] args){
        String s;
        Scanner scanner=new Scanner(System.in);
        int numbers=scanner.nextInt();
        int length=scanner.nextInt();
        Pair[] pair=new Pair[numbers];
        int cur=0;
        boolean check=false;
        for(int i=0;i<numbers;i++){
            pair[i]=new Pair();
            pair[i].start=scanner.nextInt();
            pair[i].end=scanner.nextInt();
            if(pair[i].end-pair[i].start<length){
                check=true;
            }
        }
        quicksort(pair,0,numbers-1);
        if(check==false && pick(pair,length))
            System.out.println("yes");
        else
            System.out.println("no");
    }
}
