package com.mz;

import java.io.FileDescriptor;
import java.io.FileOutputStream;

/**
 * Created by mingzhu7 on 2017/12/8.
 */
public class FileTest {
    public static void main(String[] args) throws Exception{
        FileOutputStream fileOutputStream=new FileOutputStream("C:\\Users\\Administrator.CE-20160118BBNF\\Desktop\\test.txt");
        fileOutputStream.write(new String("hahahahah").getBytes());
        //flush只是刷到系统缓存区里面
        fileOutputStream.flush();
        FileDescriptor fd=fileOutputStream.getFD();
        //确定刷到磁盘上
        fd.sync();
        System.out.println("end");

    }
}
