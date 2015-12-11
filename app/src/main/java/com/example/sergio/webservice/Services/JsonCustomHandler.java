package com.example.sergio.webservice.Services;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sergio on 12/10/15.
 */
public class JsonCustomHandler extends JsonHttpResponseHandler {
    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Log.i("Response",responseString);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.e("RequestError", responseString);
    }
}
