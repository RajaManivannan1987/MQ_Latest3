package com.moneyquotient.in.in.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.moneyquotient.in.in.Interface.VolleyResponseListerner;
import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonActivity;
import com.moneyquotient.in.in.common.CommonMethod;
import com.moneyquotient.in.in.utlity.Constant.ConstantValues;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;
import com.moneyquotient.in.in.utlity.webservice.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karth on 8/11/2017.
 */

public class LoginActivity extends CommonActivity implements GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.loginEmailId)
    EditText email;
    @BindView(R.id.loginPassword)
    EditText password;
    @BindView(R.id.loginButton)
    Button login;
    @BindView(R.id.loginForgotPassword)
    TextView forgotPassword;
    @BindView(R.id.loginFacebook)
    ImageButton loginFacebook;
    @BindView(R.id.loginGoogle)
    ImageButton loginGoogle;
    @BindView(R.id.loginNewUser)
    TextView newUser;

    private String TAG = LoginActivity.class.getSimpleName();

    //Facebook
    private CallbackManager callbackManager;

    //Google

    public static GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        setView(R.layout.activity_login, "Login");
        hideCommonProgressBar();
        ButterKnife.bind(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
//        Session.getInstance(getApplicationContext(),TAG).logout();

    }

    @OnClick({R.id.loginEmailId, R.id.loginPassword, R.id.loginButton, R.id.loginForgotPassword, R.id.loginFacebook, R.id.loginGoogle, R.id.loginNewUser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginEmailId:
                break;
            case R.id.loginPassword:
                break;
            case R.id.loginButton:
                login();
                break;
            case R.id.loginForgotPassword:
                break;
            case R.id.loginFacebook:
                fbLogin();
                break;
            case R.id.loginGoogle:
                gmailLogin();
                break;
            case R.id.loginNewUser:
                CommonMethod.changeActivity(LoginActivity.this, RegisterActivity.class);
                break;
        }
    }

    private void login() {
        if (CommonMethod.emailPhoneValidation(email, "Enter Valid Email ID")) {
            if (CommonMethod.checkEmpty(password, "Enter Password")) {
                setCommonProgressBar();
                WebServices.getInstance(getApplicationContext(), TAG).Login(ConstantValues.LOGIN, email.getText().toString(), password.getText().toString(), new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        hideCommonProgressBar();
                        if (response.getString("status").equalsIgnoreCase("1")) {
                            Session.getInstance(getApplicationContext(), TAG).createSession(response.getJSONObject("data").getString("name"),
                                    response.getJSONObject("data").getString("token"), response.getJSONObject("data").getString("email"),
                                    response.getJSONObject("data").getString("user_id"));
                            CommonMethod.changeActivity(LoginActivity.this, WealthSummaryActivity.class);
                            finish();
                        } else {
                            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            CommonMethod.showSnackbar(email, response.getString("message"));
                        }
                    }

                    @Override
                    public void onError(String message, String title) {
                        hideCommonProgressBar();
                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        CommonMethod.showSnackbar(email, message);
                    }
                });
            }
        }

    }


    public void fbLogin() {


        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(
                "public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        CommonMethod.showLog(TAG, String.valueOf(loginResult.getAccessToken()));
                        CommonMethod.showLog(TAG, String.valueOf(loginResult.getRecentlyDeniedPermissions()));


                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {

                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        setCommonProgressBar();
                                        WebServices.getInstance(LoginActivity.this, TAG).fbLogin(ConstantValues.FB_LOGIN, object, new VolleyResponseListerner() {
                                            @Override
                                            public void onResponse(JSONObject response) throws JSONException {
                                                hideCommonProgressBar();
                                                if (response.getString("status").equalsIgnoreCase("1")) {
                                                    Session.getInstance(getApplicationContext(), TAG).createSession(response.getJSONObject("data").getString("name"),
                                                            response.getJSONObject("data").getString("token"), response.getJSONObject("data").getString("email"),
                                                            response.getJSONObject("data").getString("user_id"));
                                                    if (response.getJSONObject("data").getString("isRegistered").equalsIgnoreCase("true"))
                                                        CommonMethod.changeActivity(LoginActivity.this, WealthSummaryActivity.class);
                                                    else
                                                        CommonMethod.changeActivity(LoginActivity.this, WealthSummaryActivity.class);

                                                    finish();
                                                } else {
                                                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                                    CommonMethod.showSnackbar(email, response.getString("message"));
                                                }
                                            }

                                            @Override
                                            public void onError(String message, String title) {
                                                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                                hideCommonProgressBar();
                                                CommonMethod.showSnackbar(email, message);
                                            }
                                        });


                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        CommonMethod.showLog("Facebook", exception.toString());
                        CommonMethod.showSnackbar(email, exception.toString());
                    }
                }

        );
    }

    private void gmailLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            if (mGoogleApiClient.hasConnectedApi(Auth.GOOGLE_SIGN_IN_API)) {
                GoogleSignInAccount acct = result.getSignInAccount();
                setCommonProgressBar();
                WebServices.getInstance(getApplicationContext(), TAG).googlePlusLogin(ConstantValues.GMAIL_LOGIN, acct, new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        hideCommonProgressBar();
                        if (response.getString("status").equalsIgnoreCase("1")) {
                            Session.getInstance(getApplicationContext(), TAG).createSession(response.getJSONObject("data").getString("name"),
                                    response.getJSONObject("data").getString("token"), response.getJSONObject("data").getString("email"),
                                    response.getJSONObject("data").getString("user_id"));
                            if (response.getJSONObject("data").getString("isRegistered").equalsIgnoreCase("true"))
                                CommonMethod.changeActivity(LoginActivity.this, WealthSummaryActivity.class);
                            else
                                CommonMethod.changeActivity(LoginActivity.this, WealthSummaryActivity.class);

                            finish();
                        } else {
                            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            CommonMethod.showSnackbar(email, response.getString("message"));
                        }
                    }

                    @Override
                    public void onError(String message, String title) {
                        hideCommonProgressBar();
                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        CommonMethod.showSnackbar(email, message);
                    }
                });
            } else {
                Log.e(TAG, "Google+ not connected");
            }

        } else {
            // Signed out, show unauthenticated UI.
            CommonMethod.showSnackbar(email, "Signed out");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN && data != null) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else if (resultCode == RESULT_OK && data != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


}
