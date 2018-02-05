import java.util.*;

/**
 * Created by mingzhu7 on 2018/2/5.
 */
public class NewVisualPython {
    public static class Position{
        int x;
        int y;
        public Position(int x,int y){
            this.x=x;this.y=y;
        }
    }
    public static class PositionComparetor implements Comparator<Position> {

        @Override
        public int compare(Position o1, Position o2) {
            if(o1.x>o2.y)
                return 1;
            else if(o1.x>=o2.x && o1.y<=o2.y)
                return 0;
            else
                return -1;
        }
    }
    public static void main(String[] args){
        TreeMap<Integer,TreeMap<Integer,Integer>> total=new TreeMap<>();
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        int[] results=new int[n];
        int x,y;
        for(int i=0;i<n;i++){
            x=scanner.nextInt();y=scanner.nextInt();
            if(total.containsKey(x)){
                total.get(x).put(y,i);
            }else{
                TreeMap<Integer,Integer> newTree=new TreeMap<>();
                newTree.put(y,i);
                total.put(x,newTree);
            }
        }
        for(int i=0;i<n;i++){
            x=scanner.nextInt();y=scanner.nextInt();
            if(total.containsKey(x)){
                total.get(x).put(y,-(i+1));
            }else{
                TreeMap<Integer,Integer> newTree=new TreeMap<>();
                newTree.put(y,-(i+1));
                total.put(x,newTree);
            }
        }
        TreeMap<Integer,TreeMap<Integer,Integer>> nm=new TreeMap<>();//not matched y->x->cursor
        boolean success=true;
        TreeMap<Position,Integer> alreayExists=new TreeMap<>(new PositionComparetor());
        TreeMap<Integer,TreeMap<Position,Integer>> another=new TreeMap<>();
        Iterator<Map.Entry<Integer,TreeMap<Integer,Integer>>> colIter=total.entrySet().iterator();
        while(colIter.hasNext()){
            Map.Entry<Integer,TreeMap<Integer,Integer>> colEntry=colIter.next();
            Integer endX=colEntry.getKey();TreeMap<Integer,Integer> rowMap=colEntry.getValue();
            Iterator<Map.Entry<Integer,Integer>> rowIter=rowMap.entrySet().iterator();
            Integer lastColValue=Integer.MIN_VALUE;
            while(rowIter.hasNext()) {
                Map.Entry<Integer, Integer> rowEntry = rowIter.next();
                if (rowEntry.getValue() >= 0) {
                    if (nm.containsKey(rowEntry.getKey())) {
                        nm.get(rowEntry.getKey()).put(endX, rowEntry.getValue());
                    } else {
                        TreeMap<Integer, Integer> cursors = new TreeMap<Integer, Integer>();
                        cursors.put(endX, rowEntry.getValue());
                        nm.put(rowEntry.getKey(), cursors);
                    }
                    continue;
                }
                Map.Entry<Integer, TreeMap<Integer, Integer>> candicates = nm.floorEntry(rowEntry.getKey());
                //well we should use floorentry
                if (candicates == null || candicates.getValue().size() > 1) {
                    success = false;
                    break;
                }
                Integer endY = rowEntry.getKey();
                Integer curY = candicates.getKey();
                Integer curX = candicates.getValue().firstKey();
                Integer cursor = candicates.getValue().firstEntry().getValue();
                Integer result = -rowEntry.getValue();
                if (curY > lastColValue) {
                    lastColValue = endY;
                } else {
                    success = false;
                    break;
                }
                nm.remove(candicates.getKey());//remove the candicate
                //--------------------------------------------------------------------------------------
                //runing cross test;
                Position tmp1 = new Position(curY, curY);
                Position tmp2 = new Position(endY, endY);
                NavigableMap<Position, Integer> navigableMap1 = alreayExists.subMap(tmp1, true, tmp2, true);
                Map.Entry<Position, Integer> lastEntry = navigableMap1.lastEntry();
                if (lastEntry == null) {
                    alreayExists.put(new Position(curY, endY), endX);
                } else {
                    Position lastPosition = lastEntry.getKey();
                    Integer lastX = lastEntry.getValue();
                    if (endY <= lastPosition.y && curX <= lastX) {
                        success = false;
                        break;
                    }
                    Map.Entry<Position, Integer> firstEntry = navigableMap1.firstEntry();
                    navigableMap1.clear();
                    if (endY<lastPosition.y) {
                        alreayExists.put(new Position(endY+1, lastPosition.y), lastX);
                    }
                    if (firstEntry != null) {
                        Position firstPosition = firstEntry.getKey();
                        Integer firstX = firstEntry.getValue();
                        if (curY > firstPosition.x) {
                            alreayExists.put(new Position(firstPosition.x, curY-1), firstX);
                        }
                    }
                    alreayExists.put(new Position(curY, endY), endX);
                }
                //--------------------------------------------------------------------------------------
                results[cursor] = result;
                if (another.containsKey(-curY)) {
                    another.get(-curY).put(new Position(curX, endX), endY);
                } else {
                    TreeMap<Position, Integer> treeMap = new TreeMap<>(new PositionComparetor());
                    treeMap.put(new Position(curX, endX), endY);
                    another.put(-curY, treeMap);
                }
            }
            if(success==false){
                break;
            }
        }
        if(success==false){
            System.out.println("syntax error");
            return;
        }
        //--------------------------------------------------------
        alreayExists.clear();
        Iterator<Map.Entry<Integer,TreeMap<Position,Integer>>> iter=another.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<Integer,TreeMap<Position,Integer>> entry=iter.next();
            TreeMap<Position,Integer> treeMap=entry.getValue();
            Iterator<Map.Entry<Position,Integer>> iter2=treeMap.entrySet().iterator();
            while(iter2.hasNext()) {
                Map.Entry<Position,Integer> entry2=iter2.next();
                Position tmp1 = new Position(entry2.getKey().x, entry2.getKey().x);
                Position tmp2 = new Position(entry2.getKey().y,entry2.getKey().y);
                NavigableMap<Position, Integer> navigableMap1 = alreayExists.subMap(tmp1, true, tmp2, true);
                Map.Entry<Position,Integer> firstEntry=navigableMap1.firstEntry();
                if(firstEntry==null){
                    alreayExists.put(new Position(entry2.getKey().x,entry2.getKey().y),-entry.getKey());
                }else {
                    if(entry2.getKey().x>=firstEntry.getKey().x && entry2.getValue()>=firstEntry.getValue()){
                        success=false;break;
                    }
                    Position firstPosition = firstEntry.getKey();
                    Integer firstX = firstEntry.getValue();
                    Map.Entry<Position, Integer> endEntry = navigableMap1.lastEntry();
                    navigableMap1.clear();
                    if(entry2.getKey().x>firstPosition.x){
                        alreayExists.put(new Position(firstPosition.x,entry2.getKey().x-1),firstX);
                    }
                    if(endEntry!=null) {
                        Position endPosition = endEntry.getKey();
                        Integer endPositionX = endEntry.getValue();
                        if (entry2.getKey().y < endPosition.y) {
                            alreayExists.put(new Position(entry2.getKey().y + 1, endPosition.y), endPositionX);
                        }
                    }
                    alreayExists.put(new Position(entry2.getKey().x, entry2.getKey().y), -entry.getKey());
                }
            }
            if(success==false)
                break;
        }
        if(success==false){
            System.out.println("syntax error");
            return;
        }
        for(int i=0;i<n;i++){
            System.out.println(results[i]);
        }
        //--------------------------------------------------------
    }
}
