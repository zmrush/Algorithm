package com.mz;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

/**
 * Created by mingzhu7 on 2017/12/26.
 */
public class NetUtil {
    private Set<String> ignoreDisplayNames=new HashSet<String>();
    private Set<String> ignoreInetAddress=new HashSet<String>();
    public boolean ignoreNetworkInterface(String displayName){
        return ignoreDisplayNames.contains(displayName);
    }
    public boolean ignoreInetAddress(String hostAddress){
        return ignoreInetAddress.contains(hostAddress);
    }
    public InetAddress getFirstLocalIp() throws SocketException {
        InetAddress result=null;
        int lowest=Integer.MAX_VALUE;
        Enumeration<NetworkInterface> interfaces=NetworkInterface.getNetworkInterfaces();
        for(;interfaces.hasMoreElements();){
            NetworkInterface networkInterface=interfaces.nextElement();
            if(networkInterface.isUp() && !ignoreNetworkInterface(networkInterface.getDisplayName())){
                if(networkInterface.getIndex()<lowest || result==null){
                    lowest=networkInterface.getIndex();
                    Enumeration<InetAddress> inetAddressEnumeration=networkInterface.getInetAddresses();
                    for(;inetAddressEnumeration.hasMoreElements();){
                        InetAddress inetAddress=inetAddressEnumeration.nextElement();
                        if(inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress() && !ignoreInetAddress(inetAddress.getHostAddress())){
                            result=inetAddress;
                        }
                    }
                }
            }
        }
        if(result !=null)
            return result;
        try {
            return InetAddress.getLocalHost();
        }catch (Exception e){
            return null;
        }

    }
    public static void main(String[] args) throws Exception{
        LinkedList<Integer> list=new LinkedList<>();
        list.add(3);list.add(5);list.add(6);
        ListIterator<Integer> listIterator=list.listIterator();
        while(listIterator.hasNext()){
            Integer tmp=listIterator.next();
            if(tmp==5){
//                listIterator.set(4);
                listIterator.add(4);
                listIterator.next();
                listIterator.previous();
                listIterator.remove();
            }
        }
        Iterator<Integer> iter=list.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
        iter=list.descendingIterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
            iter.remove();
        }
        InetAddress inetAddress2=Inet4Address.getLocalHost();
        System.out.println(inetAddress2);
        NetUtil netUtil=new NetUtil();
        InetAddress inetAddress=netUtil.getFirstLocalIp();
        System.out.println(inetAddress);
    }
}
