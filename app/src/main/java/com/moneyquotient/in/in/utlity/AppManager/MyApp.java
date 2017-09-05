package com.moneyquotient.in.in.utlity.AppManager;

import android.support.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Created by Im033 on 5/9/2017.
 */

public class MyApp extends MultiDexApplication {
    private static MyApp sInstanse;
    private RequestQueue nRequestQueue;

    public MyApp() {

    }

    public static synchronized MyApp getInstanse() {
        return sInstanse;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstanse = this;
    }

    public RequestQueue getnRequestQueue() {
        if (nRequestQueue == null) {
            nRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return nRequestQueue;
    }

    public void addToRequestQueue(Request request) {
        getnRequestQueue().add(request);
    }
}
