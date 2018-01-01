import com.mz.DinicMaxFlow;

import java.util.Scanner;

public class PipStream {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int vertext=scanner.nextInt();
        int pipNum=scanner.nextInt();
        double v=scanner.nextDouble();
        double a=scanner.nextDouble();
        double[][] edges=new double[vertext+1][vertext+1];
        edges[0][1]=Double.MAX_VALUE;
        edges[0][2]=Double.MAX_VALUE;
        int[] outputstart=new int[pipNum];
        int[] outputend=new int[pipNum];
        for(int i=0;i<pipNum;i++){
            outputstart[i]=scanner.nextInt();
            outputend[i]=scanner.nextInt();
            edges[outputstart[i]][outputend[i]]=scanner.nextInt();
            edges[outputend[i]][outputstart[i]]=edges[outputstart[i]][outputend[i]];
        }
        double max_flow= DinicMaxFlow.maxFlow(edges,0,3);
        System.out.println(Math.pow(a*max_flow/v,a)*Math.pow((1-a)*max_flow,(1-a)));
    }
} 