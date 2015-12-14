package com.example.sergio.webservice.Services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by sergio on 12/7/15.
 */
public class WebService {
    private final static String SERVER = "http://162.243.170.44:7111/";
    protected final static String SERVICE =  "";

    protected final static String DEBUGTAG = "@Request";

    protected static AsyncHttpClient client;

    protected static Context context;
    public static String database = "aapppp.db";

    public static void setContext(Context contexto){
        context = contexto;
    }

    static {
        client = new AsyncHttpClient();
    }

    public static void post(String resource, JSONObject jsonObject, ResponseHandlerInterface responseHandler) {
        Log.i(DEBUGTAG, "POST: "+SERVER+SERVICE+resource);

        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(context,SERVER + SERVICE + resource, entity,  "application/json",responseHandler);
    }

    public static void get(String resource, JsonHttpResponseHandler handler){
        Log.i(DEBUGTAG, "GET: "+SERVER+SERVICE+resource);
        client.get(SERVER + SERVICE + resource, handler);
    }

    public static Date toDate(String dateTime) {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateParser.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  static boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork == null){
            return  false;
        }else{
            return activeNetwork.isConnectedOrConnecting();
        }
    }



}
