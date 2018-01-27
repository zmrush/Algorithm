import java.util.*;

public class NoProbableMission {
    public static void max_flow(int[][] matrix,int start,int end,List<Integer> maxFlow){
        int[] v=new int[matrix.length];
        LinkedList<Integer> queue=new LinkedList<Integer>();
        while(true) {
            for(int i=0;i<v.length;i++)
                v[i]=-1;
            boolean find=false;
            queue.push(start);
            while (!queue.isEmpty()) {
                int pre = queue.pop();
                for (int i = 0; i < matrix.length; i++) {
                    if (matrix[pre][i] > 0 && v[i]==-1) {
                        v[i] = pre;
                        queue.push(i);
                        if (i == end) {
                            find=true;
                            break;
                        }
                    }
                }
                if(find==true)
                    break;
            }
            if(find==true){
                int i=end;
                while(i!=start){
                    matrix[v[i]][i]=0;
                    matrix[i][v[i]]=1;
                    i=v[i];
                }
                maxFlow.set(0,maxFlow.get(0)+1);
            }else{
                break;
            }
            queue.clear();
        }
    }
    //well,in this problem,i have two question,the first one is that when i compute not null,i not check whether columnKey[columnCursor]>0 which cause i
    //directly let notnull+columnMap.get(columnKey[columnCursor]);the other one is the second problem,see below
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int m=scanner.nextInt();int n=scanner.nextInt();
        int[][] matrix=new int[m][n];
        int[] columnMax=new int[n];
        int[] rowMax=new int[m];
        TreeMap<Integer,HashSet<Integer>> rowMap=new TreeMap<Integer,HashSet<Integer>>();
        long total=0;
        int totalNotNull=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++) {
                matrix[i][j] = scanner.nextInt();
                total+=matrix[i][j];
                if(matrix[i][j]>0)
                    totalNotNull+=1;
                rowMax[i]=(rowMax[i]>=matrix[i][j])?rowMax[i]:matrix[i][j];
                columnMax[j]=(columnMax[j]>=matrix[i][j])?columnMax[j]:matrix[i][j];
            }
            if(rowMap.containsKey(rowMax[i])){
                rowMap.get(rowMax[i]).add(i);
            }else{
                HashSet<Integer> arr=new HashSet<Integer>();
                arr.add(i);
                rowMap.put(rowMax[i],arr);
            }
        }
        TreeMap<Integer,HashSet<Integer>> columnMap=new TreeMap<Integer,HashSet<Integer>>();
        for(int j=0;j<n;j++){
            if(columnMap.containsKey(columnMax[j]))
                columnMap.get(columnMax[j]).add(j);
            else{
                HashSet<Integer> arr=new HashSet<Integer>();
                arr.add(j);
                columnMap.put(columnMax[j],arr);
            }
        }
        List<Long> res=new ArrayList<Long>();
        res.add(0L);res.add(0L);
        int[] rowKey=new int[rowMap.keySet().size()];
        int cursor=0;
        Iterator<Integer> rowIter=rowMap.keySet().iterator();
        while(rowIter.hasNext()){
            rowKey[cursor++]=rowIter.next();
        }
        int[] columnKey=new int[columnMap.keySet().size()];
        cursor=0;
        Iterator<Integer> columnIter=columnMap.keySet().iterator();
        while(columnIter.hasNext())
            columnKey[cursor++]=columnIter.next();
        int rowCursor=rowKey.length-1;
        int columnCursor=columnKey.length-1;
        int[][] newMatrix=new int[m+n+2][m+n+2];
        while(true){
            if(rowCursor<0 && columnCursor<0)
                break;
            if(rowCursor<0){
                res.set(0,((long)columnKey[columnCursor])*columnMap.get(columnKey[columnCursor]).size()+res.get(0));
                res.set(1,res.get(1)+((columnKey[columnCursor]>0)?columnMap.get(columnKey[columnCursor]).size():0));
                columnCursor--;
            }else if(columnCursor<0){
                res.set(0,res.get(0)+((long)rowKey[rowCursor])*rowMap.get(rowKey[rowCursor]).size());
                res.set(1,res.get(1)+((rowKey[rowCursor]>0)?rowMap.get(rowKey[rowCursor]).size():0));
                rowCursor--;
            }
            else if (columnKey[columnCursor] == rowKey[rowCursor]) {
                //matching
                for(int i=0;i<m;i++){
                    if(rowMap.get(rowKey[rowCursor]).contains(i)){
                        newMatrix[m+n][i]=1;
                        newMatrix[i][m+n]=0;
                        for(int j=0;j<n;j++){
                            if(columnMap.get(columnKey[columnCursor]).contains(j) && matrix[i][j]>0)
                                newMatrix[i][m+j]=1;
                            else
                                newMatrix[i][m+j]=0;
                            newMatrix[m+j][i]=0;
                        }
                    }else{
                        newMatrix[m+n][i]=0;
                        newMatrix[i][m+n]=0;
                        //the second problem,which i not make it zero,which may cause next time call will have some additional flow,which i thought it may happen,but
                        //in the cases ,i not check it all.
                        for(int j=0;j<n;j++){
                            newMatrix[i][m+j]=0;
                            newMatrix[m+j][i]=0;
                        }
                    }
                }
                for(int j=0;j<n;j++){
                    if(columnMap.get(columnKey[columnCursor]).contains(j))
                        newMatrix[m+j][m+n+1]=1;
                    else
                        newMatrix[m+j][m+n+1]=0;
                    newMatrix[m+n+1][m+j]=0;
                }
                List<Integer> maxFlow=new ArrayList<Integer>();
                maxFlow.add(0);
                max_flow(newMatrix,m+n,m+n+1,maxFlow);
                res.set(0,res.get(0)+((long)columnKey[columnCursor])*(rowMap.get(rowKey[rowCursor]).size()+columnMap.get(columnKey[columnCursor]).size()-maxFlow.get(0)));
                res.set(1,res.get(1)+((rowKey[rowCursor]>0)?(rowMap.get(rowKey[rowCursor]).size()+columnMap.get(columnKey[columnCursor]).size()-maxFlow.get(0)):0));
                columnCursor--;rowCursor--;
            }else if(columnKey[columnCursor]<rowKey[rowCursor]){
                res.set(0,res.get(0)+((long)rowKey[rowCursor])*rowMap.get(rowKey[rowCursor]).size());
                res.set(1,res.get(1)+((rowKey[rowCursor]>0)?rowMap.get(rowKey[rowCursor]).size():0));
                rowCursor--;
            }else{
                res.set(0,((long)columnKey[columnCursor])*columnMap.get(columnKey[columnCursor]).size()+res.get(0));
                res.set(1,res.get(1)+((columnKey[columnCursor]>0)?columnMap.get(columnKey[columnCursor]).size():0));
                columnCursor--;
            }
        }
        System.out.println(total-(res.get(0)+totalNotNull-res.get(1)));
    }
} 