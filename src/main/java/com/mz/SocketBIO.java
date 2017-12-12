package com.mz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.InterruptibleChannel;

/**
 * Created by mingzhu7 on 2017/10/17.
 */
public class SocketBIO {
//    public static void main(String[] args) throws Exception{
//        ServerSocket serverSocket=new ServerSocket(1023);
//        while(!Thread.interrupted()){
//            new Thread(new Handler(serverSocket.accept())).start();
//        }
//    }
    public static class Handler implements Runnable{
        private Socket socket;
        public Handler(Socket socket){
            this.socket=socket;
        }

        public void run(){
            try {
                byte[] buffer = new byte[1024];
                int readLength=socket.getInputStream().read(buffer);
                byte[] output=process(buffer,readLength);
                socket.getOutputStream().write(output);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        public byte[] process(byte[] input,int readLength){
            return null;
        }
    }
    public static void main(String args[]) throws Exception {
        Example6 thread = new Example6();
        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        Thread.currentThread().interrupt();// 再调用interrupt方法
        thread.socket.close();// 再调用close方法
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        System.out.println("Stopping application...");
    }
    static class Example6 extends Thread {
        volatile ServerSocket socket;

        public void run() {
            try {
                socket = new ServerSocket(8888);
            } catch (IOException e) {
                System.out.println("Could not create the socket...");
                return;
            }
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Waiting for connection...");
                try {
                    socket.accept();
                } catch (IOException e) {
                    System.out.println("accept() failed or interrupted...");
                    Thread.currentThread().interrupt();//重新设置中断标示位
                }
            }
            System.out.println("Thread exiting under request...");
        }
    }
}
