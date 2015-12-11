package com.example.sergio.webservice;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by sergio on 12/7/15.
 */
public class WebService {
    private final static String SERVER = "http://162.243.170.44:7111/";
    protected final static String SERVICE =  "";

    protected final static String DEBUGTAG = "@BuildingModel";

    protected static AsyncHttpClient client;

    static {
        client = new AsyncHttpClient();
    }

    public static void post(String resource, JsonHttpResponseHandler handler){
        client.post(SERVER+SERVICE+resource,handler);
    }

    public static void get(String resource, JsonHttpResponseHandler handler){
        client.get(SERVER + SERVICE + resource, handler);
    }

}
