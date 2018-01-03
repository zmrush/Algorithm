import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class PipStream {
    private static final double DB_EPSLON=0.00000001;
    public static double maxFlow(double[][] edges,int source,int sink,final double flowUpper){
        double flow = 0.0;
        int[] level = new int[edges.length];
        LinkedList<Integer>[] onLevel = new LinkedList[edges.length];
        boolean stopEarly = false;
        double singleFlowUpper = flowUpper;
        while (true) {
            for (int i = 0; i < level.length; i++) {
                level[i] = -1;//initializing
                onLevel[i] = new LinkedList<Integer>();
            }
            //bfs
            LinkedList<Integer> queue = new LinkedList<Integer>();
            queue.push(source);
            level[source] = 0;
            while (!queue.isEmpty()) {
                Integer from = queue.pop();
                for (int i = 0; i < edges.length; i++) {
                    if (edges[from][i] > DB_EPSLON && level[i] == -1) {
                        level[i] = level[from] + 1;
                        queue.push(i);
                    }
                    if ((level[i] == level[from] + 1) && edges[from][i] > DB_EPSLON) {
                        //onLevel[from][i]=1;//put into the level graph
                        onLevel[from].push(i);
                    }
                }
            }
            if (level[sink] == -1)
                break;//there is no way from source to sink
            //dfs until there is no path from source to sink in the level graph
            //we have to use stack because we need pop the last one that has been pushed
            Stack<Integer> dfsQueue = new Stack<Integer>();
            dfsQueue.push(source);
            while (!dfsQueue.isEmpty()) {
                int from = dfsQueue.peek();
                if (from == sink) {
                    Iterator<Integer> iter = dfsQueue.iterator();
                    int last = iter.next();
                    double mincapacity = singleFlowUpper;
                    int minLevel = Integer.MAX_VALUE;
                    while (iter.hasNext()) {
                        int next = iter.next();
                        //well,the last judge in this if sentence is really important,because if there are 0.0000012 and 0.000001,those two
                        //subtraction is smaller than dbl_epslon,then in the next onLevel[last] it will pop,but it will not remove and in the
                        //queue,then it will pop again,then it will throw exception
                        if (edges[last][next] < mincapacity && Math.abs(edges[last][next]-mincapacity)>DB_EPSLON) {
                            mincapacity = edges[last][next];
                            minLevel = level[last];
                        }
                        last = next;
                    }
                    flow += mincapacity;
                    iter = dfsQueue.iterator();
                    last = iter.next();
                    while (iter.hasNext()) {
                        int next = iter.next();
                        edges[last][next] -= mincapacity;
                        if (edges[last][next] <= DB_EPSLON) {
                            edges[last][next] = 0.0;
                            onLevel[last].pop();
                        }
                        edges[next][last] += mincapacity;
                        if (level[last] >= minLevel)  // >= is correct,> is wrong
                            iter.remove();
                        last = next;
                    }
                    if (Math.abs(flowUpper - flow) <= DB_EPSLON) {
                        stopEarly = true;
                        break;
                    }
                    singleFlowUpper -= mincapacity;
                } else {
                    if (onLevel[from].size() <= 0) {
                        dfsQueue.pop();
                        if (!dfsQueue.isEmpty()) {
                            onLevel[dfsQueue.peek()].pop();
                        }
                    } else {
                        dfsQueue.push(onLevel[from].peek());
                    }
                }
            }
            if (stopEarly)
                break;
        }
        return flow;
    }
    public static void copyEdges(double[][] source,double[][] dst){
        for(int i=0;i<source.length;i++)
            for(int j=0;j<source.length;j++)
                dst[i][j]=source[i][j];
    }
    public static void print(int pipNum,double v,int[] outputstart,int[] outputend,double[][] new_edges,double[][] new_edges2,DecimalFormat decimalFormat){
        for(int i=0;i<pipNum;i++){
            if(new_edges[outputstart[i]][outputend[i]]>=DB_EPSLON){
                System.out.print(decimalFormat.format((new_edges[outputstart[i]][outputend[i]]-new_edges2[outputstart[i]][outputend[i]])/v));
                System.out.print(" ");
                System.out.println(decimalFormat.format(new_edges2[outputstart[i]][outputend[i]]));
            }else if(new_edges[outputend[i]][outputstart[i]]>=DB_EPSLON){
                System.out.print(decimalFormat.format((new_edges2[outputend[i]][outputstart[i]]-new_edges[outputend[i]][outputstart[i]])/v));
                System.out.print(" ");
                System.out.println(decimalFormat.format(-new_edges2[outputend[i]][outputstart[i]]));
            }else{
                System.out.print(0);
                System.out.print(" ");
                System.out.println(0);
            }
        }
    }
    //all in one word,there are four errors that i make:1.alpha compute error;2.decimal format is really import;
    //3.maxflow i need source maxflow constraint,because sometimes even though i make constraints on the new mix source based on the
    //mix flow(which has only positive flow on one side,the other zero),
    //but i cannot make sure the source out is smaller than the a*Z(where Z is the max flow)
    //4.the max flow subroutine,very subtle,there will pop once,but the edge not removed and pop again and throw exception
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int vertext=scanner.nextInt();
        int pipNum=scanner.nextInt();
        double v=scanner.nextDouble();
        double a=scanner.nextDouble();
        double[][] edges=new double[vertext+1][vertext+1];
        int[] outputstart=new int[pipNum];
        int[] outputend=new int[pipNum];
        for(int i=0;i<pipNum;i++){
            outputstart[i]=scanner.nextInt();
            outputend[i]=scanner.nextInt();
            edges[outputstart[i]][outputend[i]]=scanner.nextInt();
            edges[outputend[i]][outputstart[i]]=edges[outputstart[i]][outputend[i]];
        }
        //------------------------------------------------------------------------
        double[][] new_edges=new double[vertext+1][vertext+1];
        copyEdges(edges,new_edges);
        new_edges[0][1]=Double.MAX_VALUE;
        new_edges[0][2]=Double.MAX_VALUE;
        double max_flow= maxFlow(new_edges,0,3,Double.MAX_VALUE);
        //------------------------------------------------------------------------
        double[][] new_edges2=new double[vertext+1][vertext+1];
        copyEdges(edges,new_edges2);
        double f_max_flow=maxFlow(new_edges2,1,3,Double.MAX_VALUE);
        double lamda1=maxFlow(new_edges2,2,3,Double.MAX_VALUE);
        //------------------------------------------------------------------------
        double[][] new_edges3=new double[vertext+1][vertext+1];
        copyEdges(edges,new_edges3);
        double w_max_flow=maxFlow(new_edges3,2,3,Double.MAX_VALUE);
        double lamda2=maxFlow(new_edges3,1,3,Double.MAX_VALUE);
        //------------------------------------------------------------------------
        if(f_max_flow<=a*max_flow){//f max,direct ouput new_edges2
            double[][] new_edges4=new double[vertext+1][vertext+1];
            for(int i=0;i<vertext+1;i++){
                for(int j=i+1;j<vertext+1;j++){
                    double f1=new_edges2[i][j]<edges[i][j]?(edges[i][j]-new_edges2[i][j]):(new_edges2[j][i]<edges[j][i]?(new_edges2[j][i]-edges[j][i]):0);
                    double new_capacity=f1;
                    if(new_capacity>=0)
                        new_edges4[i][j]=new_capacity;
                    else
                        new_edges4[j][i]=-new_capacity;
                }
            }
            double[][] new_edges5=new double[vertext+1][vertext+1];
            copyEdges(new_edges4,new_edges5);
            new_edges5[0][1]=Double.MAX_VALUE;
            new_edges5[0][2]=Double.MAX_VALUE;
            double lamdad=maxFlow(new_edges5,0,3,Double.MAX_VALUE);
            if(Math.abs(lamdad-max_flow)>DB_EPSLON)
                throw new OutOfMemoryError("");
            copyEdges(new_edges4,new_edges5);
            double lamda=maxFlow(new_edges5,1,3,f_max_flow);
            DecimalFormat decimalFormat=new DecimalFormat("0.000000000");
            print(pipNum,v,outputstart,outputend,new_edges4,new_edges5,decimalFormat);
            System.out.println(decimalFormat.format(Math.pow(f_max_flow/v,a)*Math.pow((max_flow-f_max_flow),(1-a))));
        }else if((w_max_flow<=(1-a)*max_flow)){//w max,direct output new_edges3
            double[][] new_edges4=new double[vertext+1][vertext+1];
            for(int i=0;i<vertext+1;i++){
                for(int j=i+1;j<vertext+1;j++){
                    double f2=new_edges3[i][j]<edges[i][j]?(edges[i][j]-new_edges3[i][j]):(new_edges3[j][i]<edges[j][i]?(new_edges3[j][i]-edges[j][i]):0);
                    double new_capacity=f2;
                    if(new_capacity>=0)
                        new_edges4[i][j]=new_capacity;
                    else
                        new_edges4[j][i]=-new_capacity;
                }
            }
            double[][] new_edges5=new double[vertext+1][vertext+1];
            copyEdges(new_edges4,new_edges5);
            new_edges5[0][1]=Double.MAX_VALUE;
            new_edges5[0][2]=Double.MAX_VALUE;
            double lamdad=maxFlow(new_edges5,0,3,Double.MAX_VALUE);
            if(Math.abs(lamdad-max_flow)>DB_EPSLON)
                throw new OutOfMemoryError("");
            copyEdges(new_edges4,new_edges5);
            double lamda=maxFlow(new_edges5,1,3,(max_flow-w_max_flow));
            DecimalFormat decimalFormat=new DecimalFormat("0.000000000");
            print(pipNum,v,outputstart,outputend,new_edges4,new_edges5,decimalFormat);
            System.out.println(decimalFormat.format(Math.pow((max_flow-w_max_flow)/v,a)*Math.pow((w_max_flow),(1-a))));
        }else{//mix
            //first error alpha,the
            double alpha=((a-1)*max_flow+w_max_flow)/(f_max_flow+w_max_flow-max_flow);
            double[][] new_edges4=new double[vertext+1][vertext+1];
            for(int i=0;i<vertext+1;i++){
                for(int j=i+1;j<vertext+1;j++){
                    double f1=new_edges2[i][j]<edges[i][j]?(edges[i][j]-new_edges2[i][j]):(new_edges2[j][i]<edges[j][i]?(new_edges2[j][i]-edges[j][i]):0);
                    double f2=new_edges3[i][j]<edges[i][j]?(edges[i][j]-new_edges3[i][j]):(new_edges3[j][i]<edges[j][i]?(new_edges3[j][i]-edges[j][i]):0);
                    double new_capacity=alpha*f1+(1-alpha)*f2;
                    if(new_capacity>=0)
                        new_edges4[i][j]=new_capacity;
                    else
                        new_edges4[j][i]=-new_capacity;
                }
            }
            double[][] new_edges5=new double[vertext+1][vertext+1];
            copyEdges(new_edges4,new_edges5);
            new_edges5[0][1]=Double.MAX_VALUE;
            new_edges5[0][2]=Double.MAX_VALUE;
            double lamdad=maxFlow(new_edges5,0,3,Double.MAX_VALUE);
            if(Math.abs(lamdad-max_flow)>DB_EPSLON)
                throw new OutOfMemoryError("");
            copyEdges(new_edges4,new_edges5);
            double lamda=maxFlow(new_edges5,1,3,a*max_flow);
            DecimalFormat decimalFormat=new DecimalFormat("0.000000000");
            print(pipNum,v,outputstart,outputend,new_edges4,new_edges5,decimalFormat);
            System.out.println(decimalFormat.format(Math.pow(a*max_flow/v,a)*Math.pow((1-a)*max_flow,(1-a))));
        }

    }
} 