package com.example.sergio.webservice.Services;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sergio on 12/10/15.
 */
public abstract class JsonCustomHandler extends JsonHttpResponseHandler {
    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Log.i("Response",responseString);
        globalSuccess(statusCode, headers, null, null,responseString);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        globalSuccess(statusCode, headers, null,response, null);

    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        globalSuccess(statusCode, headers, response,null,null);
    }


    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.e("RequestError", responseString);
        globalError(statusCode, headers, null, null, responseString);
    }


    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.e("Request:", errorResponse.toString());
        globalError(statusCode, headers, null, errorResponse, null);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        globalError(statusCode,headers,errorResponse,null,null);
    }


    public abstract void globalSuccess(int statusCode,Header[] headers, JSONArray jsonArray, JSONObject jsonObject, String responseString);

    public abstract void globalError(int statusCode,Header[] headers, JSONArray jsonArray, JSONObject jsonObject, String responseString);
}
