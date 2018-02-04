import java.util.*;

/**
 * Created by mingzhu7 on 2018/1/31.
 */
public class MoneyForNothing {
    public static class Composite{
        public int price;
        public int day;
        @Override
        public int hashCode(){
            return day;
        }
        @Override
        public boolean equals(Object obj){
            if(!(obj instanceof Composite))
                return false;
            return (this.price==((Composite)obj).price) && (this.day==((Composite)obj).day);
        }
    }
    public static class CompositeCompare implements Comparator<Composite>{
        @Override
        public int compare(Composite o1, Composite o2) {
            if(o1.day>o2.day ||(o1.day==o2.day && o1.price>o2.price))
                return 1;
            else if(o1.day==o2.day && o1.price==o2.price)
                return 0;
            else
                return -1;
        }
    }
    public static long getMaxMoney(List<Composite> sellers,int start,int end,List<Composite> buyers,int start1,int end1){
        if(start1>end1)
            return Long.MIN_VALUE;
        int middle=(start1+end1)/2;
        long max=Long.MIN_VALUE;
        int index=0;
        long tmp;
        for(int i=start;i<=end;i++){
            if(sellers.get(i).day>=buyers.get(middle).day)
                break;
            tmp=((long)(buyers.get(middle).price-sellers.get(i).price))*(buyers.get(middle).day-sellers.get(i).day);
            if(tmp>max){
                max=tmp;index=i;
            }
        }
        long max1=getMaxMoney(sellers,start,index,buyers,start1,middle-1);
        long max2=getMaxMoney(sellers,index,end,buyers,middle+1,end1);
        if(max1>=max2){
            if(max>=max1)
                return max;
            else return max1;
        }else{
            if(max>=max2)
                return max;
            else return max2;
        }
    }
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int m=scanner.nextInt();int n=scanner.nextInt();
        TreeSet<Composite> sellers=new TreeSet<Composite>(new CompositeCompare());
        TreeSet<Composite> buyers=new TreeSet<Composite>(new CompositeCompare());
        for(int i=0;i<m;i++){
            Composite composite=new Composite();
            composite.price=scanner.nextInt();
            composite.day=scanner.nextInt();
            sellers.add(composite);
        }
        for(int i=0;i<n;i++){
            Composite composite=new Composite();
            composite.price=scanner.nextInt();
            composite.day=scanner.nextInt();
            buyers.add(composite);
        }
        ArrayList<Composite> sellerArray=new ArrayList<>();
        Iterator<Composite> sellerIter=sellers.iterator();
        while(sellerIter.hasNext()){
            Composite tmp=sellerIter.next();
            if(sellerArray.isEmpty()){
                sellerArray.add(tmp);
            }else{
                if(tmp.price<sellerArray.get(sellerArray.size()-1).price)
                    sellerArray.add(tmp);
            }
        }
        Stack<Composite> buyerStack=new Stack<Composite>();
        Iterator<Composite> buyerIter=buyers.iterator();
        while(buyerIter.hasNext()){
            Composite tmp=buyerIter.next();
            if(buyerStack.isEmpty())
                buyerStack.push(tmp);
            else{
                while(!buyerStack.isEmpty() && buyerStack.peek().price<=tmp.price)
                    buyerStack.pop();
                buyerStack.push(tmp);
            }
        }
        ArrayList<Composite> buyerArray=new ArrayList<Composite>();
        buyerArray.addAll(buyerStack);
        long maxMoney=getMaxMoney(sellerArray,0,sellerArray.size()-1,buyerArray,0,buyerArray.size()-1);
        if(maxMoney>0){
            System.out.println(maxMoney);
        }else{
            System.out.println(0);
        }

    }
}
