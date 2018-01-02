import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by mingzhu7 on 2018/1/2.
 */
public class NewPipStream {

    static final int MAXN=200;

    static int N, P;
    static int[][] cap=new int[MAXN+1][MAXN+1];
    static int[][] cap2=new int[MAXN+1][MAXN+1];
    static int[][] cap3=new int[MAXN+1][MAXN+1];
    static double V, A, B;
    static double[][] wflow=new double[MAXN+1][MAXN+1];
    static double[][] fflow=new double[MAXN+1][MAXN+1];

    static boolean[] seen=new boolean[MAXN+1];
    static int flow_dfs(int[][] c, int x, int e, int flow) {
        if (x == e) return flow;
        if (seen[x]) return 0;
        seen[x] = true;
        for (int y = 1; y <= N; y++) if (c[x][y]!=0) {
            int f = flow_dfs(c, y, e, Integer.min(flow, c[x][y]));
            if (f!=0) {
                c[x][y] -= f;
                c[y][x] += f;
                return f;
            }
        }
        return 0;
    }

    static int flow(int[][] c, int x, int e) {
        return flow_dfs(c, x, e, 1000000000);
    }

    static double flubber_dfs(int x, int e, double flubber) {
        seen[x] = true;
        if (x == e) return flubber;
        for (int y = 1; y <= N && flubber > 1e-7; y++) {
            if (!seen[y] && wflow[x][y] > 1e-7) {
                double f = flubber_dfs(y, e, Double.min(wflow[x][y], flubber));
                if (f > 0) {
                    wflow[x][y] -= f; wflow[y][x] += f;
                    fflow[x][y] += f; fflow[y][x] -= f;
                    return f;
                }
            }
        }
        return 0;
    }
    public static void copyEdges(int[][] source,int[][] dst){
        for(int i=0;i<source.length;i++)
            for(int j=0;j<source.length;j++)
                dst[i][j]=source[i][j];
    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        N=scanner.nextInt();
        P=scanner.nextInt();
        V=scanner.nextDouble();
        A=scanner.nextDouble();
        B = 1.0-A;
        int[] X=new int[P];
        int[] Y=new int[P];
        int[] C=new int[P];
        for (int i = 0; i < P; i++) {
            X[i]=scanner.nextInt(); Y[i]=scanner.nextInt();  C[i]=scanner.nextInt();
            cap[X[i]][Y[i]] = cap[Y[i]][X[i]] = C[i];
        }
        copyEdges(cap,cap2);
        copyEdges(cap,cap3);
        int mnF = 0, mnW = 0, mxF = 0, mxW = 0, f;
        while ((f = flow(cap, 1, 3))!=0) mxF += f;
        while ((f = flow(cap, 2, 3))!=0) mnW += f;
        while ((f = flow(cap2, 2, 3))!=0) mxW += f;
        while ((f = flow(cap2, 1, 3))!=0) mnF += f;

        f = mxF + mnW;
        // Maximum of (x/V)^A * (f-x)^B lies at A/V * (f-x) - B * x/V = 0, or
        // F = Af / (A+B)
        double F = Double.max((double)mnF, Double.min((double)mxF, A*f / (double)(A+B)));
        double v1, v2;
        if (mnF == mxF) {
            v1 = v2 = 0.5;
        } else {
            v1 = (F-mnF)/(mxF-mnF);
            v2 = (mxF-F)/(mxF-mnF);
        }
        for (int x = 1; x <= N; x++)
            for (int y = 1; y <= N; y++) {
                wflow[x][y] = cap3[x][y] - (v1*cap[x][y] + v2*cap2[x][y]);
            }
        for (double rem = F; rem > 1e-7; ) {
            for(int i=0;i<seen.length;i++)
                seen[i]=false;
            rem -= flubber_dfs(1, 3, rem);
        }
        for (int i = 0; i < P; i++) {

            System.out.print(fflow[X[i]][Y[i]] / V);
            System.out.print(" ");
            System.out.println(wflow[X[i]][Y[i]]);
        }
        System.out.println(Math.pow(F/V, A) * Math.pow(f-F, B));
    }
}
