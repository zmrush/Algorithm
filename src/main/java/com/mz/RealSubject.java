package com.mz;

/**
 * Created by mingzhu7 on 2017/7/26.
 */
public class RealSubject implements Subject{
    @Override
    public void rent()
    {
        System.out.println("I want to rent my house");
    }

    @Override
    public void hello(String str)
    {
        System.out.println("hello: " + str);
    }
}
