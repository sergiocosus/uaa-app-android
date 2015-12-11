package com.example.sergio.webservice.Services;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sergio on 12/7/15.
 */
public interface DataReadyListener {
    public abstract void onSuccess(List objects);
    public abstract void onError(int statusCode, Header[] headers, String responseString, Throwable throwable);
}
