package com.example.sergio.webservice.Services;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by sergio on 12/7/15.
 */
public class Auth extends WebService {

    public static void login(String id, String password, final DataReadyListener dataReadyListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password",password);
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        post("auth/login", jsonObject, new JsonCustomHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                dataReadyListener.onSuccess(null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String
                    responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.i("Code", "" + statusCode);
                dataReadyListener.onError(statusCode, headers, responseString, throwable);
            }
        });
    }
}
