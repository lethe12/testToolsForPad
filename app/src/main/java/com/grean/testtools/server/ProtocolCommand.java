package com.grean.testtools.server;

public interface ProtocolCommand {
    /**
     * 发送数据接口
     * @param buff
     * @return
     */
    boolean executeSendTask(byte[] buff);

    boolean isConnected();

    void log(String string);


    /**
     * 重连
     */
    void reconnect();

    boolean isOnline();
}
