package com.moneyquotient.in.in.utlity.sharedPreferance;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by karth on 8/22/2017.
 */

public class PassocodePreferance {
    private String TAG;
    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static String PREF_NAME = "MQ_Passcode";
    private static String PASSCODE_TYPE = "";
    private static String PASSCODE_VALUE = "";
    private static final String AS_PASSCODE = "AS_PASSCODE";


    private PassocodePreferance(Context context, String TAG) {
        this.context = context;
        this.TAG = "Session " + TAG;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    private static PassocodePreferance passcodePreferance;

    public static PassocodePreferance getInstance(Context context, String TAG) {
        if (passcodePreferance == null) {
            passcodePreferance = new PassocodePreferance(context, TAG);
        }
        return passcodePreferance;
    }

    public void createPasscode(String type, String passcode) {
        editor.putBoolean(AS_PASSCODE, true);
        editor.putString(this.PASSCODE_TYPE, type);
        editor.putString(this.PASSCODE_VALUE, passcode);
        editor.commit();
    }

    public String getPasscodeType() {
        return pref.getString(PASSCODE_TYPE, "");
    }

    public String getPasscodeValue() {
        return pref.getString(PASSCODE_VALUE, "");
    }

    public void clearPasscode() {
        editor.clear();
        editor.commit();
    }

    public boolean checkAsPasscode() {
        return pref.getBoolean(AS_PASSCODE, false);
    }
}


