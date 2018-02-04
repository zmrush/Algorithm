import java.util.*;

public class VisualPython {
    public static class Position{
        int x;
        int y;
        public Position(int x,int y){
            this.x=x;this.y=y;
        }
    }
    public static class PositionComparetor implements Comparator<Position>{

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
        TreeMap<Integer,TreeMap<Integer,Integer>> leftUp=new TreeMap<>();
        TreeMap<Integer,TreeMap<Integer,Integer>> rightBottom=new TreeMap<>();
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        int[] results=new int[n];
        int x,y;
        for(int i=0;i<n;i++){
            x=scanner.nextInt();y=scanner.nextInt();
            if(leftUp.containsKey(y)){
                leftUp.get(y).put(x,i);
            }else{
                TreeMap<Integer,Integer> newTree=new TreeMap<>();
                newTree.put(x,i);
                leftUp.put(y,newTree);
            }
        }
        for(int i=0;i<n;i++){
            x=scanner.nextInt();y=scanner.nextInt();
            if(rightBottom.containsKey(x)){
                rightBottom.get(x).put(y,i);
            }else{
                TreeMap<Integer,Integer> newTree=new TreeMap<>();
                newTree.put(y,i);
                rightBottom.put(x,newTree);
            }
        }
        boolean success=true;
        TreeMap<Position,Integer> alreayExists=new TreeMap<>(new PositionComparetor());
        TreeMap<Integer,TreeMap<Position,Integer>> another=new TreeMap<>();
        while(!leftUp.isEmpty()){
            Map.Entry<Integer,TreeMap<Integer,Integer>> entry=leftUp.lastEntry();
            Integer curY=entry.getKey();TreeMap<Integer,Integer> curMap=entry.getValue();
            while(!curMap.isEmpty()){
                Map.Entry<Integer,Integer> lastEntry=curMap.lastEntry();
                curMap.remove(lastEntry.getKey());
                Integer curX=lastEntry.getKey();Integer cursor=lastEntry.getValue();
                Map.Entry<Integer,TreeMap<Integer,Integer>> poseEntry=rightBottom.higherEntry(curX);// or it may be curX+1;
                if(poseEntry==null){
                    success=false;break;
                }
                Integer endX=poseEntry.getKey();
                TreeMap<Integer,Integer> poseMap=poseEntry.getValue();
                NavigableMap<Integer,Integer> navigableMap=poseMap.subMap(curY,false,Integer.MAX_VALUE,false);
                if(navigableMap.size()>1 || navigableMap.size()<0){
                    success=false;break;
                }
                int result=navigableMap.firstEntry().getValue();
                Integer endY=navigableMap.firstKey();
                navigableMap.remove(endY);
                if(poseMap.size()<=0){
                    rightBottom.remove(endX);
                }
                //--------------------------------------------------------------------------------------
                Position tmp1=new Position(curX,curX);Position tmp2=new Position(endX,endX);
                NavigableMap<Position,Integer> navigableMap1=alreayExists.subMap(tmp1,true,tmp2,true);
                Map.Entry<Position, Integer> firstEntry = navigableMap1.firstEntry();
                if(firstEntry==null){
                    alreayExists.put(new Position(curX, endX), curY);
                }else {
                    Position firstPosition = firstEntry.getKey();
                    Integer firstY = firstEntry.getValue();
                    if(curX>=firstPosition.x && endY>=firstY){
                        success=false;break;
                    }
                    Map.Entry<Position, Integer> endEntry = navigableMap1.lastEntry();
                    navigableMap1.clear();
                    if(curX>firstPosition.x){
                        alreayExists.put(new Position(firstPosition.x,curX-1),firstY);
                    }
                    if(endEntry!=null){
                        Position endPosition = endEntry.getKey();
                        Integer endPositionY = endEntry.getValue();
                        if(endX<endPosition.y){
                            alreayExists.put(new Position(endX+1,endPosition.y),endPositionY);
                        }
                    }
                    alreayExists.put(new Position(curX, endX), curY);
                }
                //--------------------------------------------------------------------------------------
                //runing cross test;
                results[cursor]=result+1;
                if(another.containsKey(curX)){
                    another.get(-curX).put(new Position(curY,endY),endX);
                }else{
                    TreeMap<Position,Integer> treeMap=new TreeMap<>(new PositionComparetor());
                    treeMap.put(new Position(curY,endY),endX);
                    another.put(-curX,treeMap);
                }
            }
            leftUp.remove(entry.getKey());
            //
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
                    Map.Entry<Position, Integer> endEntry = navigableMap1.lastEntry();
                    if(entry2.getKey().y<=endEntry.getKey().y && entry2.getValue()>=endEntry.getValue()){
                        success=false;break;
                    }
                    Position firstPosition = firstEntry.getKey();
                    Integer firstX = firstEntry.getValue();
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