package com.mz;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mingzhu7 on 2017/10/17.
 */
public class SocketBIO {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket=new ServerSocket(1023);
        while(!Thread.interrupted()){
            new Thread(new Handler(serverSocket.accept())).start();
        }
    }
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
}
