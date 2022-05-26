package com.grean.testtools.server;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProtocolTcpServer implements ProtocolCommand{
    private static ProtocolTcpServer instance = new ProtocolTcpServer();
    private ConcurrentLinkedQueue<byte[]> sendBuff = new ConcurrentLinkedQueue<>();
    private boolean isConnected = false,isRun = false;
    private OutputStream out;
    private InputStream in;

    private ProtocolTcpServer(){

    }

    public void connectServer(String ip,int port){

    }

    private class ConnectThread extends Thread {
        private String ip;
        private int port;
        public ConnectThread(String ip,int port){
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            isRun = true;
            while (!interrupted()&&isRun){
                if(isConnected){
                    if(!sendBuff.isEmpty()){
                        try {
                            out.write(sendBuff.poll());
                            out.flush();
                            Log.d("ProtocolTcpServer","han send one frame");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else{


                }
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ProtocolTcpServer getInstance() {
        return instance;
    }

    private class ReceiverThread extends Thread{
        private String ip;
        private int port;
        public ReceiverThread(String ip,int prot){
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            super.run();
        }
    }

    @Override
    public boolean executeSendTask(byte[] buff) {
        if(isConnected) {
            sendBuff.add(buff);
            return true;
        }
        return false;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void log(String string) {

    }

    @Override
    public void reconnect() {
        if (!isConnected){

        }
    }

    @Override
    public boolean isOnline() {
        return false;
    }
}
