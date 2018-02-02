import java.util.Comparator;

/**
 * Created by mingzhu7 on 2018/2/2.
 */
public class LoveTree {
    public static class Segment{
        public int start;
        public int end;
    }
    public static class OpenSegment{
        public int start;
    }
    public static class SegmentComparetor implements Comparator<Segment>{

        @Override
        public int compare(Segment o1, Segment o2) {
            if(o1.start>o2.end)
                return 1;
            else if(o1.start>=o2.start)
                return 0;
            else
                return -1;
        }
    }
    public static void main(String[] args){
    }
}
