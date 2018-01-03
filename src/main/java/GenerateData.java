import java.text.DecimalFormat;

public class GenerateData {
    public static void main(String[] args){
        int vertext=5;
        int pips=7;
        double a=0.01+0.98*Math.random();
        double v=1+9*Math.random();
        DecimalFormat decimal=new DecimalFormat("0.000000000");
        System.out.println(vertext+" "+pips+" "+decimal.format(v)+" "+decimal.format(a));
        System.out.println("1 4 "+(int)(1+9*Math.random()));
        System.out.println("2 5 "+(int)(1+9*Math.random()));
        System.out.println("1 5 "+(int)(1+9*Math.random()));
        System.out.println("2 4 "+(int)(1+9*Math.random()));
        System.out.println("4 5 "+(int)(1+9*Math.random()));
        System.out.println("4 3 "+(int)(1+9*Math.random()));
        System.out.println("5 3 "+(int)(1+9*Math.random()));
    }
} 