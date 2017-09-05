package com.moneyquotient.in.in.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.moneyquotient.in.in.activity.DashBoardActivity;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;

import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by karth on 8/10/2017.
 */

public class CommonMethod {

    public static void changeActivity(Context context, Class<?> c) {
        Intent i = new Intent(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
    }

    public static void changeActivityWithText(Context context, Class<?> c, String text) {
        Intent i = new Intent(context, c);
        i.putExtra("text", text);
        context.startActivity(i);
    }

    public static void showSnackbar(View view, String message) {
        try {
            Snackbar snackbar = Snackbar
                    .make(view, message, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void showLog(String TAG, String message) {
        Log.v(TAG, message);
    }

    public static void showLogError(String TAG, String message) {
        Log.e(TAG, message);
    }

    public static boolean checkEmpty(EditText editText, String error) {
        try {

            if (!editText.getText().toString().equalsIgnoreCase("")) {
                editText.setError(null);
                return true;
            } else {
                editText.setError(error);
                editText.requestFocus();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean emailPhoneValidation(EditText editText, String error) {
        if (Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()) {
            editText.setError(null);
            return true;
        } else {
            editText.setError(error);
            editText.requestFocus();
            return false;
        }

    }

    public static boolean passwordValidation(EditText editText, String error) {
        if (editText.getText().toString().length() > 5) {
            editText.setError(null);
            return true;
        } else {
            editText.setError(error);
            editText.requestFocus();
            return false;
        }
    }

    public static boolean checkEqual(EditText editText, EditText editText1, String error) {
        if (editText.getText().toString().equalsIgnoreCase(editText1.getText().toString())) {
            editText.setError(null);
            editText1.setError(null);
            return true;
        } else {
            editText.setError(error);
            editText1.setError(error);
            editText1.requestFocus();
            return false;
        }
    }

    public static boolean checkTokenStauts(JSONObject jsonObject, Activity activity) {
        try {
            if (!jsonObject.getString("token_status").equalsIgnoreCase("0")) {
                return true;
            } else {
                Session.getInstance(activity, TAG).logout();
                changeActivity(activity, DashBoardActivity.class);
                activity.finish();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

}
