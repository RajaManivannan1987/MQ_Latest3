package com.moneyquotient.in.in.utlity.sharedPreferance;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by IM028 on 8/2/16.
 */
public class Session {
    private String TAG;
    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static String PREF_NAME = "MQ";
    private static String userid = "customerId";
    private static String name = "name";
    private static String email = "email";
    private static String token = "token";
    private static final String IS_LOGIN = "IsLoggedIn";


    private Session(Context context, String TAG) {
        this.context = context;
        this.TAG = "Session " + TAG;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    private static Session session;

    public static Session getInstance(Context context, String TAG) {
        if (session == null) {
            session = new Session(context, TAG);
        }
        return session;
    }

    public void createSession(String name, String token, String email, String userid) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(this.name, name);
        editor.putString(this.token, token);
        editor.putString(this.email, email);
        editor.putString(this.userid, userid);
        editor.commit();
    }

    public String getToken() {
        return pref.getString(token, "");
    }

    public String getName() {
        return pref.getString(name, "");
    }


    public String getUserid() {
        return pref.getString(userid, "");
    }


    public void logout() {
        editor.clear();
        editor.commit();
    }

    public boolean getIsLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}