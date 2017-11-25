package com.example.manitch.resortct.config;

/**
 * Created by MeKa-IT_Nit on 6/10/2560.
 */

public class Config {
    //private String host = "http://192.168.1.54/resort/";
    private String host = "http://ecio.sourcework.co/";
    //private String host = "http://192.168.43.208/kudos/";
    private String serlogin = "auth/login";
    private String serroom = "receive_data/room_status";
    //private String serlogin = "app_controller/login_app";



    public String getHost() {
        return host;
    }

    public String getSerlogin() {
        return  host+serlogin;
    }
    public String getSerroom(){
        return host+serroom;
    }





}
