import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by mingzhu7 on 2018/1/8.
 */
public class OldAirportConstruction {
    public static class Point{
        public double x;
        public double y;
        public Point(double x,double y){
            this.x=x;
            this.y=y;
        }
        public boolean equals(Point t){
            return this.x==t.x && this.y==t.y;
        }
        public boolean isLeft(Point t){
            return this.x<t.x || (this.x==t.x && this.y<=t.y);
        }
    }
    public static double cosine(Point x_start,Point x_end,Point y_start,Point y_end){
        return ((x_end.x-x_start.x)*(y_end.x-y_start.x)+(x_end.y-x_start.y)*(y_end.y-y_start.y))/(getLength(x_start,x_end)*getLength(y_start,y_end));
    }
    public static double crossP(Point common_start,Point P,Point Q){
        return (P.x-common_start.x)*(Q.y-common_start.y)-(P.y-common_start.y)*(Q.x-common_start.x);
    }
    public static class Line{
        public Point start;
        public Point end;
        public double a;
        public double b;
        public double c;
        Line(double a,double b,double c,Point start,Point end){
            this.a=a;
            this.b=b;
            this.c=c;
            this.start=start.x<end.x?start:(start.x>end.x?end:(start.y<end.y?start:end));
            this.end=start.x>end.x?start:(start.x<end.x?end:(start.y>end.y?start:end));
        }
    }
    public static Line getLine(Point start,Point end){
        double x1=start.x;double y1=start.y;
        double x2=end.x;double y2=end.y;
        return new Line(y1-y2,x2-x1,x1*y2-x2*y1,start,end);
    }
    public static Point cross(Line line1,Line line2){
        if(line1.a*line2.b==line1.b*line2.a) {
            throw new RuntimeException("1");
        }
        long c1=(long)line1.c;long c2=(long)line2.c;
        long b1=(long)line1.b;long b2=(long)line2.b;
        long a1=(long)line1.a;long a2=(long)line2.a;
        long x11=c2*b1-c1*b2;long x12=a1*b2-a2*b1;
        long y11=c2*a1-c1*a2;long y12=-x12;
        double x=x11/x12+(x11-x12*(x11/x12))/(double)x12;//well if we use the shed one,the answer will be wrong
        //double x=(line2.c*line1.b-line1.c*line2.b)/(line1.a*line2.b-line2.a*line1.b);
        double y=y11/y12+(y11-y12*(y11/y12))/(double)y12;
        //double y=(line2.c*line1.a-line1.c*line2.a)/(line1.b*line2.a-line2.b*line1.a);
        return new Point(x,y);
    }
    public static boolean isOnLine(Line line,Point point){
        return (point.x>line.start.x && point.x<line.end.x) || (point.y>line.start.y && point.y<line.end.y);

    }
    public static double getLength(Point x,Point y){
        return Math.sqrt((x.x-y.x)*(x.x-y.x)+(x.y-y.y)*(x.y-y.y));
    }

    public static double getLambda(Line line,Point t){
        double lambda=line.a*t.x+line.b*t.y+line.c;
        if(line.b<0 || (line.b==0 && line.a>0))
            lambda=-lambda;
        return lambda;
    }
    public static int startJudge(List<Point> points,int k,Line cur){
        int N=points.size();
        double lambda=getLambda(cur,points.get(k));
        double lambda2=getLambda(cur,points.get((k+2)%points.size()));
        if(lambda>0){
            if(lambda2<0)
                return 1;//cross as boundry
            if(lambda2==0){
                if(cosine(cur.end,cur.start,points.get((k+1)%N),points.get((k+2)%points.size()))<0){//cosine -1
                    return 1;
                }
                return 2;//success but cross not boundry
            }
            if(lambda2>0){
                //if(cosine(points.get(k),points.get((k+1)%N),cur.end,cur.start)>cosine(points.get((k+2)%points.size()),points.get((k+1)%N),cur.end,cur.start)){
                if(crossP(points.get((k+1)%N),points.get(k),points.get((k+2)%N))>0){
                    return 2;//no boundry
                }else{
                    return -1;//fail
                }
            }
        }if(lambda<0){
            if(lambda2>0)
                return -1;
            if(lambda2==0){
                if(cosine(cur.end,cur.start,points.get((k+1)%N),points.get((k+2)%points.size()))>0)//cosine 1
                    return -1;//fail
                return 2;// no boundry
            }
            //else if(cosine(points.get((k+1)%N),points.get((k+2)%points.size()),cur.start,cur.end)>cosine(points.get((k+1)%N),points.get(k),cur.start,cur.end))
            else if(crossP(points.get((k+1)%N),points.get(k),points.get((k+2)%N))>0)
                return 2;
            return -1;
        }else{
            if(cosine(points.get(k),points.get((k+1)%N),cur.end,cur.start)>0){ //cosine 1
                if(lambda2<0)
                    return 1;//boundry
                return 2;//no boundry
            }else{
                if(lambda2>0)
                    return -1;
                return 2;
            }
        }
    }
    public static int endJudge(List<Point> points,int k,Line cur) {
        int N=points.size();
        double lambda = getLambda(cur,points.get(k));
        double lambda2 =getLambda(cur,points.get((k+2)%points.size()));
        if(lambda>0){
            if(lambda2<0)
                return -1;
            else if(lambda2==0){
                if(cosine(cur.start,cur.end,points.get((k+1)%N),points.get((k+2)%points.size()))<0){//cosine -1
                    //return 1;
                    return 2;//well the last line is wrong
                }
                return -1;
            }
            else{
                //if(cosine(cur.end,cur.start,points.get((k+1)%N),points.get((k+2)%points.size()))>cosine(cur.end,cur.start,points.get((k+1)%N),points.get(k)))
                if(crossP(points.get((k+1)%N),points.get(k),points.get((k+2)%N))>0)
                    return 2;
                return -1;
            }
        }else if(lambda<0){
            if(lambda2>0)
                return 1;//boundry
            else if(lambda2==0){
                if(cosine(cur.end,cur.start,points.get((k+1)%N),points.get((k+2)%points.size()))>0)//cosine 1
                    return 1;//boundry
                return 2;//no boundry
            }else{
                //if(cosine(cur.end,cur.start,points.get((k+1)%N),points.get(k))>cosine(cur.end,cur.start,points.get((k+1)%N),points.get((k+2)%points.size())))
                if(crossP(points.get((k+1)%N),points.get(k),points.get((k+2)%N))>0)
                    return 2;
                return -1;
            }
        }else{
            if(cosine(cur.start,cur.end,points.get((k+1)%N),points.get(k))>0){//cosine 1
                if(lambda2<0)
                    return -1;
                return 2;//no boundry
            }else{ //cosine -1
                if(lambda2>0)
                    return 1;// boundry
                return 2;//no boundry
            }
        }
    }
    public static double getDiameter(List<Point> points){
        double diameter=0.0;
        int N=points.size();
        for(int i=0;i<points.size()-1;i++){
            for(int j=i+1;j<points.size();j++){
                Line cur=getLine(points.get(i),points.get(j));
                Point start=cur.start;
                Point end=cur.end;
                Point cross_start=null;
                Point cross_end=null;

                boolean find=true;
                for(int k=0;k<points.size();k++){
                    Line cross = getLine(points.get(k), points.get((k + 1)%N));
                    Point crossPoint;
                    try {
                        crossPoint= cross(cur, cross);
                    }catch (Exception e){
                        if(cur.c*cross.a==cur.a*cross.c && cur.c*cross.b==cur.b*cross.c) // the same line
                            crossPoint=points.get((k+1)%N);
                        else
                            continue;
                    }
                    if(isOnLine(cross,crossPoint)){
                        if(isOnLine(cur,crossPoint)){
                            find=false; //intersect with the sea;
                            break;
                        }
                        if(end.isLeft(crossPoint)){

                            if(cross_end==null){
                                cross_end=crossPoint;
                            }else{
                                if(crossPoint.isLeft(cross_end))
                                    cross_end=crossPoint;
                            }
                        }
                        else{
                            if(cross_start==null)
                                cross_start=crossPoint;
                            else{
                                if(cross_start.isLeft(crossPoint))
                                    cross_start=crossPoint;
                            }
                        }
                    }else if(crossPoint.equals(points.get((k+1)%N))){
                        if(crossPoint.equals(start)){//
                            int status=startJudge(points,k,cur);
                            if(status<0){
                                find=false;
                                break;
                            }else if(status==1){
                                cross_start=crossPoint;
                            }
                        }else if(crossPoint.equals(end)){
                            int status=endJudge(points,k,cur);
                            if(status<0){
                                find=false;
                                break;
                            }else if(status==1) {
                                cross_end = crossPoint;
                            }
                        }
                        else if(isOnLine(cur,crossPoint)){
                            double lambda = getLambda(cur,points.get(k));
                            double lambda2 = getLambda(cur,points.get((k+2)%points.size()));
                            if(lambda>0){
                                if(lambda2<0){
                                    find=false;
                                    break;
                                }
                                else if(lambda2==0){
                                    if(cosine(points.get((k+1)%N),points.get((k+2)%points.size()),cur.start,cur.end)>0){//cosine 1
                                        find=false;
                                        break;
                                    }
                                }else{
                                    //if(cosine(cur.start,cur.end,points.get((k+1)%N),points.get(k))<cosine(cur.start,cur.end,points.get((k+1)%N),points.get((k+2)%points.size()))){
                                    if(crossP(points.get((k+1)%N),points.get(k),points.get((k+2)%N))<0){
                                        find=false;
                                        break;
                                    }
                                }
                            }else if(lambda==0){
                                if(cosine(cur.start,cur.end,points.get(k),points.get((k+1)%N))>0){//cosine 1
                                    if(lambda2>0){
                                        find=false;
                                        break;
                                    }
                                }else{
//                                    if(lambda2>0){
//                                        find=false;
//                                        break;
//                                    }//well i think next is right
                                    if(lambda2<0){
                                        find=false;
                                        break;
                                    }
                                }
                            }else{//<0
                                if(lambda2>0){
                                    find=false;
                                    break;
                                }else if(lambda2==0){
                                    if(cosine(cur.end,cur.start,points.get((k+1)%N),points.get((k+2)%points.size()))>0){//cosine 1
                                        find=false;break;
                                    }
                                }else{
                                    //if(cosine(cur.start,cur.end,points.get((k+1)%N),points.get(k))>cosine(cur.start,cur.end,points.get((k+1)%N),points.get((k+2)%points.size()))){
                                    if(crossP(points.get((k+1)%N),points.get(k),points.get((k+2)%N))<0){
                                        find=false;break;
                                    }// well i think next is right
                                }
                            }
                        }
                        else{//crosspoint is out of currrent line
                            if(crossPoint.isLeft(start)){
                                int status=startJudge(points,k,cur);
                                if(status<0){
                                    //find=false;break; //on this state,this may happen
                                }else if(status==1){
                                    if(cross_start==null){
                                        cross_start=crossPoint;
                                    }else if(cross_start.isLeft(crossPoint))
                                        cross_start=crossPoint;
                                }
                            }
                            if(end.isLeft(crossPoint)){
                                int status=endJudge(points,k,cur);
                                if(status<0){
                                    //find=false;break;
                                }else if(status==1) {
                                    if (cross_end == null)
                                        cross_end = crossPoint;
                                    else if (crossPoint.isLeft(cross_end)) {
                                        cross_end = crossPoint;
                                    }
                                }
                            }

                        }
                    }else{//outside not consider

                    }
                }
                if(find==true){
                    if(cross_start==null)
                        cross_start=start;
                    if(cross_end==null)
                        cross_end=end;
                    double newDia=getLength(cross_start,cross_end);
                    if(newDia>diameter)
                        diameter=newDia;
                }
            }
        }
        return diameter;
    }
    //well last wrong is endjudge return 2 wrongly to return 1,and next last wrong is cross double may not be correct.
    public static void main(String[] args){
//        double a=1000000000000000000L;
//        double b=1000000000000000001L;
//        System.out.println(a==b);
//        Point start=new Point(0,0);
//        Point end=new Point(10,15);
//        Line line=getLine(start,end);
//        System.out.println(start.isLeft(end));//true
//        Point start1=new Point(4,9);
//        Point end1=new Point(0,20);
//        Line line1=getLine(start1,end1);
//        System.out.println(start1.isLeft(end1));//false
//        Point start2=new Point(0,0);
//        Point end2=new Point(0,10);
//        Line line3=getLine(start2,end2);
//        System.out.println(start2.isLeft(end2));//true
//        double cosine=cosine(new Point(0,0),new Point(10,0),new Point(0,0),new Point(5,5));//>
//        cosine=cosine(new Point(0,0),new Point(10,0),new Point(0,0),new Point(0,10));//0
//        cosine=cosine(new Point(0,0),new Point(10,0),new Point(0,0),new Point(-1,2));//<
//        cosine=cosine(new Point(0,0),new Point(10,0),new Point(0,0),new Point(-2,0));//-1
//        cosine=cosine(new Point(0,0),new Point(10,0),new Point(0,0),new Point(-2,-2));//<
//        cosine=cosine(new Point(0,0),new Point(10,0),new Point(0,0),new Point(0,-2));//0
//        cosine=cosine(new Point(0,0),new Point(10,0),new Point(0,0),new Point(2,-2));//>
//        Point start=new Point(0,0);
//        Point end=new Point(10,0);
//        Line cur=getLine(start,end);
//        Point point=new Point(5,0);//k+1
//        Point point2=new Point(6,-2);//k
//        Point point3=new Point(4,-2);//k+2
//        System.out.println(cosine(cur.start,cur.end,point,point2));
//        System.out.println(cosine(cur.start,cur.end,point,point3));
//        Point start=new Point(0,0);
//        Point end=new Point(2,2);
//        Line line1=getLine(start,end);
//        Point start1=new Point(0,1);
//        Point end1=new Point(1,0);
//        Line line2=getLine(start1,end1);
//        Point cross=cross(line1,line2);
//        System.out.println(cross.x);

//        Point point1=new Point(-1e6,0);
//        Point point2=new Point(1e6,0);
//        Point point3=new Point(1e6,300);
//        Point point4=new Point(1e6,301);
//        double cosine=cosine(point1,point2,point1,point3);
//        double cosine2=cosine(point1,point2,point1,point4);
//        System.out.println(cosine);

//        Point common_start=new Point(25,5);
//        Point P=new Point(31,10);
//        Point Q=new Point(20,10);
//        double crossP=crossP(common_start,P,Q);
//        System.out.println(crossP);


//        long x1=1234500000000000001L;
//        long x2=0;
//        long x3=-1;
//        long x4=2;
//        long t=x3/x4;
//        double c=(x1-x2)/(double)100000000000000L;
//        double c2=((double)x1-(double)1234500000000000000L)/(double)100000000000000L;
//        long x5=(-100001)/100000;
//        long x6=(-99999)/100000;
//        System.out.println(c==12345);


        Scanner scanner=new Scanner(System.in);
        int num=scanner.nextInt();
        List<Point> points=new ArrayList<Point>(num);
        for(int i=0;i<num;i++){
            Point point=new Point(scanner.nextInt(),scanner.nextInt());
            points.add(point);
        }
        DecimalFormat decimalFormat=new DecimalFormat("0.00000000");
        System.out.println(decimalFormat.format(getDiameter(points)));
    }
}
