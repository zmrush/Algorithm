package com.mz;

//import com.creditease.toumi.SortInterface;
import com.sun.corba.se.impl.io.InputStreamHook;
import com.sun.corba.se.spi.orbutil.fsm.Input;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Created by mingzhu7 on 2016/11/25.
 */
public class DefaultUrlClassLoader{
    private static final String ROOT="d:/hotjars/";
    private  static HashMap<String,URLClassLoader> loaderMap=new HashMap<String,URLClassLoader>();
    private  static HashMap<String,Class> plugins=new HashMap<String,Class>();
    private  static HashMap<String,Set<String>> relation=new HashMap<String, Set<String>>();
    private void add(String jar) throws Exception{
        synchronized (getClass()){
            String key="file:"+ROOT+jar;
            if(loaderMap.containsKey(key)){
                throw new RuntimeException("already load!");
            }
            URLClassLoader urlClassLoader=new URLClassLoader(new URL[]{new URL("file:"+ROOT+jar)});
            loaderMap.put(key,urlClassLoader);
            InputStream is=urlClassLoader.getResourceAsStream("service");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            String line;
            while((line=bufferedReader.readLine())!=null){
                String[] pair=line.split("#");
                if(relation.containsKey(key)){
                    Set<String> sets=relation.get(key);
                    sets.add(pair[0]);
                }else{
                    Set<String> sets=new HashSet<String>();
                    sets.add(pair[0]);
                    relation.put(key,sets);
                }
                Class cls=urlClassLoader.loadClass(pair[1]);
                plugins.put(pair[0],cls);
                System.out.println(cls.getCanonicalName()+" has loaded");
            }
        }
    }
    private void remove(String jar) throws Exception{
        synchronized (getClass()){
            String key="file:"+ROOT+jar;
            if(!loaderMap.containsKey(key))
                return;
            Set<String> sets=relation.get(key);
            Iterator<String> iterator=sets.iterator();
            while(iterator.hasNext()){
                String type=iterator.next();
                plugins.remove(type);
                System.out.println(type+" is unloaded");
                iterator.remove();
            }
            relation.remove(key);
            loaderMap.remove(key);  //最后再来删除这个map
        }
    }
    public static class DefaultInvocationHandler implements InvocationHandler{

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
    }
    public static void main(String[] args) throws Exception{
//        URL url1=new URL("http://localhost:8080/jar/pullList?groupCode=normal");
//        InputStream is= url1.openStream();
//        byte[] buffer=new byte[1024];
//        int length=1024;
//        while((length=is.read(buffer))>0){
//            System.out.println(new String(buffer,0,length));
//        }
//-------------------------------------------------------------------------------------------------------
//        URL url=new URL("file:///D:/workspace4j/PrintClassLoader/target/printclassloader-1.0-SNAPSHOT.jar");
//        URLClassLoader urlClassLoader=new URLClassLoader(new URL[]{url});
//        Class cls=urlClassLoader.loadClass("com.test.PrintClassLoader");
//        Object obj=Proxy.newProxyInstance(urlClassLoader,new Class[]{cls},new DefaultInvocationHandler());
//        System.out.println("hello");
        //---------------------------------------------------------------------------------------------------
//        URL url=new URL("file:///D:/workspace4j/PrintClassLoader/target/printclassloader-1.0-SNAPSHOT.jar");
//        URLClassLoader urlClassLoader=new URLClassLoader(new URL[]{url});
//        Class cls=urlClassLoader.loadClass("com.test.PrintClassLoader");
//        Object obj=cls.newInstance();
//        Method[] methods=cls.getDeclaredMethods();
//        methods[0].invoke(obj);
//        DefaultUrlClassLoader defaultUrlClassLoader=new DefaultUrlClassLoader();
//        File file=new File(ROOT);
//        if(file.isDirectory()){
//            String[] files=file.list();
//            for(int i=0;i<files.length;i++){
//                defaultUrlClassLoader.add(files[i]);
//            }
//            for(int i=0;i<files.length;i++){
//                defaultUrlClassLoader.remove(files[i]);
//            }
//        }
//        System.out.println("end");
        //----------------------------------------------------------------------------------------------------
        {
            //if printclass has a timer,then we cannot unloading the class;
            URL url = new URL("file:///D:/workspace/PrintPathTest/target/print-path-test-1.0-SNAPSHOT.jar");
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
            Class cls = urlClassLoader.loadClass("com.test.PrintClass");
            Object obj = cls.newInstance();
            Method method=cls.getMethods()[0];
            method.invoke(obj);
            obj=null;
            cls=null;
            urlClassLoader.close();
            urlClassLoader=null;
            Thread.sleep(5000);
            System.gc();
        }
        Thread.sleep(5000);
        System.out.println("hello");
    }
}
