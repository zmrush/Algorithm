package com.mz;

import jdk.nashorn.internal.ir.LexicalContextNode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by mingzhu7 on 2017/10/17.
 */
public class SocketNIO implements Runnable{
    public final Selector selector;
    public final ServerSocketChannel serverSocketChannel;
    public SocketNIO(int port) throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(1023));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor());
    }
    public void run(){

        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set sets=selector.selectedKeys();
                Iterator iterator=sets.iterator();
                while(iterator.hasNext()){
                    dispatch((SelectionKey)iterator.next());
                }
                sets.clear();
            }
        }catch (Exception e){

        }
    }
    public void dispatch(SelectionKey selectionKey){
        Runnable r=(Runnable)selectionKey.attachment();
        r.run();
    }

    public class Acceptor implements Runnable{

        public void run() {
            try {
                SocketChannel socketChannel=serverSocketChannel.accept();
                //socketChannel.configureBlocking(false);
                new Handler(socketChannel,selector);
            }catch (Exception e){

            }
        }
    }
    public class Handler implements Runnable{
        SocketChannel socketChannel;
        SelectionKey selectionKey;
        ByteBuffer readBuffer= ByteBuffer.allocate(1024);
        ByteBuffer writeBuffer=ByteBuffer.allocate(1024);
        static final int READ=0,WRITE=1;
        void process(){

        }
        int state=READ;
        public Handler(SocketChannel socketChannel,Selector selector) throws Exception{
            this.socketChannel=socketChannel;
            selectionKey=socketChannel.register(selector,0);
            selectionKey.attach(this);
            selectionKey.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        }
        public void run() {
            if(state==READ)
                read();
            else if(state==WRITE)
                write();
        }
        public void read(){
            try {
                int bytes=socketChannel.read(readBuffer);
                if(bytes>0){//读取完毕
                    process();
                    state=WRITE;
                    selectionKey.interestOps(SelectionKey.OP_WRITE);

                }
            }catch (IOException e){

            }

        }
        public void write(){
            try {
                int bytes = socketChannel.write(writeBuffer);
                if (bytes > 0)//写完毕
                    selectionKey.cancel();
            }catch (IOException e){

            }
        }
    }
}
