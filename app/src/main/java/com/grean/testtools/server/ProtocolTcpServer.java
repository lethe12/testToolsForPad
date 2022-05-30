package com.grean.testtools.server;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProtocolTcpServer implements ProtocolCommand{
    private static String tag = "ProtocolTcpServer";
    private static ProtocolTcpServer instance = new ProtocolTcpServer();
    private ConcurrentLinkedQueue<byte[]> sendBuff = new ConcurrentLinkedQueue<>();
    private boolean isConnected = false,isRun = false;
    private OutputStream out;
    private InputStream in;
    private Socket socket;
    private ProtocolRec protocolRec;
    private String ip;
    private int port;

    private ProtocolTcpServer(){

    }

    public void connectServer(String ip,int port,ProtocolRec protocolRec){
        this.protocolRec = protocolRec;
        this.ip = ip;
        this.port = port;
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
                    new ReceiverThread(ip,port).run();
                    try {
                        sleep(19900);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        public ReceiverThread(String ip,int port){
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(ip,port),5000);
                socket.setTcpNoDelay(true);
                socket.setSoLinger(true,30);
                socket.setSendBufferSize(10240);
                socket.setKeepAlive(true);
                in = socket.getInputStream();
                out = socket.getOutputStream();
                socket.setOOBInline(true);

                int count;
                byte[] readBuff = new byte[4096];


                isConnected = true;
                while (isConnected){
                    if (socket.isConnected()&&(!socket.isClosed())){
                        while ((count = in.read(readBuff))!=-1 && isConnected){
                            //info.getProtocolState().handleReceiveBuff(readBuff,count);
                            protocolRec.handleProtocol(readBuff,count);
                        }
                        isConnected = false;
                        break;
                    }else {
                        isConnected = false;
                    }
                    Log.d(tag,"one turn");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            finally {
                isConnected = false;
            }
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
