package com.grean.testtools.ui.notifications;

import android.content.SharedPreferences;

public class NotificationsModel {
    private SharedPreferences serverInfo;
    private String ssid,ip,portString;
    private int port;
    public NotificationsModel(SharedPreferences sp){
        serverInfo = sp;
        ssid = sp.getString("SSID","SUR_W610");
        ip = sp.getString("Ip","10.10.100.254");
        port = sp.getInt("Port",8899);
        portString = String.valueOf(port);
    }


}
