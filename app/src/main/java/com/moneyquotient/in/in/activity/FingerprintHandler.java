package com.moneyquotient.in.in.activity;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.LinearLayout;

import com.moneyquotient.in.in.common.CommonMethod;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;

/**
 * Created by karth on 8/22/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


    private Activity context;
    private LinearLayout errorText;


    // Constructor
    public FingerprintHandler(Activity mContext, LinearLayout fingerErrorText) {
        context = mContext;
        errorText = fingerErrorText;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }


    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }


    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }


    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false);
    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Fingerprint Authentication succeeded.", true);
    }


    public void update(String e, Boolean success) {

        if (success) {
            if (Session.getInstance(context, "FIngerPrint").getIsLogin()) {
                CommonMethod.changeActivity(context, WealthSummaryActivity.class);
                context.finish();
            } else {
                CommonMethod.changeActivity(context, DashBoardActivity.class);
                context.finish();
            }
        } else
            CommonMethod.showSnackbar(errorText, e);
    }
}
