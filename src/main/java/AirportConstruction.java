import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by mingzhu7 on 2018/1/8.
 */
public class AirportConstruction {
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
        double x=(line2.c*line1.b-line1.c*line2.b)/(line1.a*line2.b-line2.a*line1.b);
        double y=(line2.c*line1.a-line1.c*line2.a)/(line1.b*line2.a-line2.b*line1.a);
        return new Point(x,y);
    }
    public static boolean isOnLine(Line line,Point point){
        return point.x>line.start.x && point.x<line.end.x || point.y>line.start.y && point.y<line.end.y;

    }
    public static double getLength(Point x,Point y){
        return Math.sqrt((x.x-y.x)*(x.x-y.x)+(x.y-y.y)*(x.y-y.y));
    }


    public static int startJudge(List<Point> points,int k,Line cur){
        double lambda=cur.a*points.get(k).x+cur.b*points.get(k).y+cur.c;
        if(cur.b<0 || (cur.b==0 || cur.a>0))
            lambda=-lambda;
        double lambda2=cur.a*points.get(k).x+cur.b*points.get(k).y+cur.c;
        if(cur.b<0 || (cur.b==0 && cur.a>0))
            lambda2=-lambda;
        if(lambda>0){
            if(lambda2<0)
                return 1;//cross as boundry
            if(lambda2==0){
                if(cosine(cur.end,cur.start,points.get(k+1),points.get((k+2)%points.size()))==-1){
                    return 1;
                }
                return 2;//success but cross not boundry
            }
            if(lambda2>0){
                if(cosine(points.get(k),points.get(k+1),cur.end,cur.start)>cosine(points.get((k+2)%points.size()),points.get(k+1),cur.end,cur.start)){
                    return 2;//no boundry
                }else{
                    return -1;//fail
                }
            }
        }if(lambda<0){
            if(lambda2>0)
                return -1;
            if(lambda2==0){
                if(cosine(cur.end,cur.start,points.get(k+1),points.get((k+2)%points.size()))==1)
                    return -1;//fail
                return 2;//not boundry
            }
            else
                if(cosine(points.get(k+1),points.get((k+2)%points.size()),cur.start,cur.end)>cosine(points.get(k+1),points.get(k),cur.start,cur.end))
                    return 2;
                return -1;
        }else{
            if(cosine(points.get(k),points.get(k+1),cur.end,cur.start)==1){
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
        double lambda = cur.a * points.get(k).x + cur.b * points.get(k).y + cur.c;
        if (cur.b < 0 || (cur.b == 0 || cur.a > 0))
            lambda = -lambda;
        double lambda2 = cur.a * points.get(k).x + cur.b * points.get(k).y + cur.c;
        if (cur.b < 0 || (cur.b == 0 && cur.a > 0))
            lambda2 = -lambda;
        if(lambda>0){
            if(lambda2<0)
                return -1;
            else if(lambda2==0){
                if(cosine(cur.start,cur.end,points.get(k+1),points.get((k+2)%points.size()))==-1){
                    return 1;
                }
                return -1;
            }
            else{
                if(cosine(cur.end,cur.start,points.get(k+1),points.get((k+2)%points.size()))>cosine(cur.end,cur.start,points.get(k+1),points.get(k)))
                    return 2;
                return -1;
            }
        }else if(lambda<0){
            if(lambda2>0)
                return 1;//boundry
            else if(lambda2==0){
                if(cosine(cur.end,cur.start,points.get(k+1),points.get((k+2)%points.size()))==1)
                    return 1;//boundry
                return 2;//no boundry
            }else{
                if(cosine(cur.end,cur.start,points.get(k+1),points.get(k))>cosine(cur.end,cur.start,points.get(k+1),points.get((k+2)%points.size())))
                    return 2;
                return -1;
            }
        }else{
            if(cosine(cur.start,cur.end,points.get(k+1),points.get(k))==1){
                if(lambda2<0)
                    return -1;
                return 2;//no boundry
            }else{
                if(lambda2>0)
                    return 1;// boundry
                return 2;//no boundry
            }
        }
    }
    public static double getDiameter(List<Point> points){
        double diameter=0.0;
        for(int i=0;i<points.size()-1;i++){
            for(int j=i+1;j<points.size();j++){
                Line cur=getLine(points.get(i),points.get(j));
                Point start=cur.start;
                Point end=cur.end;
                Point cross_start=null;
                Point cross_end=null;

                boolean find=true;
                for(int k=0;k<points.size()-1;k++){
                    Line cross = getLine(points.get(k), points.get(k + 1));
                    Point crossPoint;
                    try {
                         crossPoint= cross(cur, cross);
                    }catch (Exception e){
                        if(cur.c*cross.a==cur.a*cross.c && cur.c*cross.b==cur.b*cross.c) // the same line
                            crossPoint=points.get(k+1);
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
                    }else if(crossPoint.equals(points.get(k+1))){
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
                            double lambda = cur.a * points.get(k).x + cur.b * points.get(k).y + cur.c;
                            if (cur.b < 0 || (cur.b == 0 || cur.a > 0))
                                lambda = -lambda;
                            double lambda2 = cur.a * points.get(k).x + cur.b * points.get(k).y + cur.c;
                            if (cur.b < 0 || (cur.b == 0 && cur.a > 0))
                                lambda2 = -lambda;
                            if(lambda>0){
                                if(lambda2<0){
                                    find=false;
                                    break;
                                }
                                else if(lambda2==0){
                                    if(cosine(points.get(k+1),points.get((k+2)%points.size()),cur.start,cur.end)==1){
                                        find=false;
                                        break;
                                    }
                                }else{
                                    if(cosine(cur.start,cur.end,points.get(k+1),points.get(k))<cosine(cur.start,cur.end,points.get(k+1),points.get((k+2)%points.size()))){
                                        find=false;
                                        break;
                                    }
                                }
                            }else if(lambda==0){
                                if(cosine(cur.start,cur.end,points.get(k),points.get(k+1))==1){
                                    if(lambda2>0){
                                        find=false;
                                        break;
                                    }
                                }else{
                                    if(lambda2>0){
                                        find=false;
                                        break;
                                    }
                                }
                            }else{//<0
                                if(lambda2>0){
                                    find=false;
                                    break;
                                }else if(lambda2==0){
                                    if(cosine(cur.end,cur.start,points.get(k+1),points.get((k+2)%points.size()))==1){
                                        find=false;break;
                                    }
                                }else{
                                    if(cosine(cur.start,cur.end,points.get(k+1),points.get(k))>cosine(cur.start,cur.end,points.get(k+1),points.get((k+2)%points.size()))){
                                        find=false;break;
                                    }
                                }
                            }
                        }
                        else{//crosspoint is not on currrent line
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
    public static void main(String[] args){
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
