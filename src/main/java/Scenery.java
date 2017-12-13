import java.util.LinkedList;
import java.util.Scanner;

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
            int first = start + 1;
            while (first < pairs.length) {
                if (pairs[first].start > pairs[start].end - length * 2)
                    break;
                first++;
            }
            if (start == pairs.length - 1)
                return true;
            LinkedList<Integer> queue = new LinkedList<Integer>();
            queue.add(start);
            for (int i = start + 1; i < first; i++) {
                if (pairs[i].end <= pairs[queue.peek()].end) {
                    while (!queue.isEmpty() && (pairs[i].end - 2 * length < pairs[queue.peek()].start))
                        queue.pop();
                    queue.push(i);
                }
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
    public static void main(String[] args){
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
        if(check==false && pickFirst(pair,0,length))
            System.out.println("yes");
        else
            System.out.println("no");
    }
}
