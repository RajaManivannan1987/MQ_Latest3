package com.moneyquotient.in.in.utlity.webservice;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.moneyquotient.in.in.Interface.VolleyResponseListerner;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by im028 on 27/6/17.
 */

public class WebServices {

    private VolleyClass volleyClass;
    private static WebServices webServices;

    public static WebServices getInstance(Context context, String TAG) {
        if (webServices == null) {
            webServices = new WebServices(context, TAG);
        }
        return webServices;
    }

    private WebServices(Context context, String TAG) {

        volleyClass = new VolleyClass(context, TAG);
    }

    public void Login(String url, String email, String password, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyClass.volleyPostData(url, jsonObject, listerner);
    }


    public void fbLogin(String url, JSONObject object, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (!object.getString("email").equalsIgnoreCase(""))
                jsonObject.put("email", object.getString("email"));
            else
                jsonObject.put("email", object.getString("id") + "@facebook.com");

            jsonObject.put("name", object.getString("name"));
            jsonObject.put("facebook_id", object.getString("id"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);

    }

    public void googlePlusLogin(String url, GoogleSignInAccount acct, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", acct.getEmail());
            jsonObject.put("name", acct.getDisplayName());
            jsonObject.put("gmail_id", acct.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);

    }

    public void register(String url, String name, String email, String password, String cpassowrd, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("name", name);
            jsonObject.put("password", password);
            jsonObject.put("cpassword", cpassowrd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);

    }

    public void incomeTax(String url, JSONObject jsonObject, VolleyResponseListerner listerner) {
        volleyClass.volleyPostData(url, jsonObject, listerner);

    }

    public void getAccessTOken(String url, String userId, String token, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userId);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(url, jsonObject, listerner);

    }

    public void getflow(String url, String userid, String token, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userid);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void deleteAccount(String url, String userid, String token, String accountId, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userid);
            jsonObject.put("token", token);
            jsonObject.put("accountid", accountId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

    public void getMutualFunds(String url, String userid, String token, String type, String c_key, String c_id, String sc_key, String sc_id, String scheme_key, String scheme_id, VolleyResponseListerner listerner) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userid);
            jsonObject.put("token", token);
            if (type.equalsIgnoreCase("categories")) {
                jsonObject.put(c_key, c_id);
            } else if (type.equalsIgnoreCase("subcategories")) {
                jsonObject.put(c_key, c_id);
                jsonObject.put(sc_key, sc_id);
            } else if (type.equalsIgnoreCase("scheme")) {
                jsonObject.put(c_key, c_id);
                jsonObject.put(sc_key, sc_id);
                jsonObject.put(scheme_key, scheme_id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyClass.volleyPostData(url, jsonObject, listerner);
    }

}
