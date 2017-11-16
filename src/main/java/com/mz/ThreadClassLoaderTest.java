package com.mz;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by mingzhu7 on 2017/11/14.
 */
public class ThreadClassLoaderTest {
    public static void main(String[] args){
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                    Thread.currentThread().setContextClassLoader(new URLClassLoader(new URL[]{}));
                    System.out.println(Thread.currentThread().getContextClassLoader());
                }catch (Exception e){

                }
            }
        }).start();
        System.out.println("end");
    }
}
