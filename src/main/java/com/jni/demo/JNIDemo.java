package com.jni.demo;

import java.lang.reflect.Field;

/**
 * Created by mingzhu7 on 2017/11/8.
 */
public class JNIDemo {
    public native void sayHello();
    public static void main(String[] args) throws Exception{
        System.out.println(System.getProperty("java.library.path"));
        System.setProperty("java.library.path",System.getProperty("java.library.path")+":/home/yxgly/");
        System.out.println(System.getProperty("java.library.path"));
        Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
        fieldSysPath.setAccessible(true);
        fieldSysPath.set(null, null);
        System.loadLibrary("ctest");
        JNIDemo jniDemo=new JNIDemo();
        jniDemo.sayHello();
    }
}
