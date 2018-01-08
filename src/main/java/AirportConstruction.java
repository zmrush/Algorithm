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
                        crossPoint=points.get(k+1);
                    }
                    if(isOnLine(cross,crossPoint)){
                        if(isOnLine(cur,crossPoint)){
                            find=false; //intersect with the sea;
                            break;
                        }
                        if(crossPoint.x>end.x || (crossPoint.x==end.x && crossPoint.y>=end.y)) {
                            end = crossPoint;
                            if(cross_end==null){
                                cross_end=crossPoint;
                            }else{
                                if(cross_end.x>crossPoint.x || (cross_end.x==crossPoint.x && cross_end.y>=crossPoint.y))
                                    cross_end=crossPoint;
                            }
                        }
                        else{
                            if(cross_start==null)
                                cross_start=crossPoint;
                            else{
                                if(cross_start.x<crossPoint.x || (cross_start.x==crossPoint.x && cross_start.y<=crossPoint.y))
                                    cross_start=crossPoint;
                            }
                        }
                    }else if(crossPoint.equals(points.get(k+1))){
                        if(crossPoint.equals(start) || crossPoint.equals(end)){//not end

                        }
                        double lambda=cur.a*points.get(k).x+cur.b*points.get(k).y+cur.c;
                        Point nextCross=points.get((k+2)%points.size());
                        double lambda2=cur.a*nextCross.x+cur.b*nextCross.y+cur.c;
                        if(lambda*lambda2<0){
                            find=false;
                            break;
                        }
                        else if(lambda2*lambda==0){
                            if(lambda==0){
                                Line nextLine=getLine(points.get(k+1),nextCross);
                                double lambda3=nextLine.a*end.x+nextLine.b*end.y+nextLine.c;
                                if(lambda3<0){
                                    find=false;
                                    break;
                                }
                            }else{
                                double lambda3=cross.a*start.x+cross.b*start.y+cross.c;
                                if(lambda3<0){
                                    find=false;
                                    break;
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
        System.out.println(getDiameter(points));
    }
}
