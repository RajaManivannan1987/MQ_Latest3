package com.moneyquotient.in.in.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.moneyquotient.in.in.Interface.VolleyResponseListerner;
import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonActivity;
import com.moneyquotient.in.in.common.CommonMethod;
import com.moneyquotient.in.in.utlity.Constant.ConstantValues;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;
import com.moneyquotient.in.in.utlity.webservice.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karth on 8/11/2017.
 */


public class RegisterActivity extends CommonActivity {
    @BindView(R.id.registerName)
    EditText registerName;
    @BindView(R.id.registerEmailId)
    EditText registerEmailId;
    @BindView(R.id.registerPassword)
    EditText registerPassword;
    @BindView(R.id.registerConformPassword)
    EditText registerConformPassword;
    @BindView(R.id.registerButton)
    Button registerButton;
    @BindView(R.id.registerTerms)
    CheckBox registerTerms;
    @BindView(R.id.registerTerms1)
    TextView registerTerms1;

    private String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_register, "Register");
        ButterKnife.bind(this);
        registerTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    registerButton.setVisibility(View.VISIBLE);
                else
                    registerButton.setVisibility(View.GONE);
            }
        });
    }

    @OnClick({R.id.registerButton, R.id.registerTerms1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.registerButton:
                if (CommonMethod.checkEmpty(registerName, "Enter Name") && CommonMethod.emailPhoneValidation(registerEmailId, "Enter Valid Email Id") &&
                        CommonMethod.passwordValidation(registerPassword, "Enter Valid Password") && CommonMethod.checkEmpty(registerConformPassword, "Enter Confirm Passowrd")
                        && CommonMethod.checkEqual(registerPassword, registerConformPassword, "Password not mach")) {
                    setCommonProgressBar();
                    WebServices.getInstance(getApplicationContext(), TAG).register(ConstantValues.REGISTER, registerName.getText().toString(),
                            registerEmailId.getText().toString(), registerPassword.getText().toString(), registerConformPassword.getText().toString(),
                            new VolleyResponseListerner() {
                                @Override
                                public void onResponse(JSONObject response) throws JSONException {
                                    hideCommonProgressBar();
                                    if (response.getString("status").equalsIgnoreCase("1")) {
                                        Session.getInstance(getApplicationContext(), TAG).createSession(response.getJSONObject("data").getString("name"),
                                                response.getJSONObject("data").getString("token"), response.getJSONObject("data").getString("email"),
                                                response.getJSONObject("data").getString("user_id"));
                                        if (response.getJSONObject("data").getString("isRegistered").equalsIgnoreCase("true"))
                                            CommonMethod.changeActivity(RegisterActivity.this, WealthSummaryActivity.class);
                                        else
                                            CommonMethod.changeActivity(RegisterActivity.this, WealthSummaryActivity.class);

                                        finish();
                                    } else {
                                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                        CommonMethod.showSnackbar(registerConformPassword, response.getString("message"));
                                    }
                                }

                                @Override
                                public void onError(String message, String title) {
                                    hideCommonProgressBar();
                                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    CommonMethod.showSnackbar(registerConformPassword, message);
                                }
                            });
                }
                break;
            case R.id.registerTerms1:
                CommonMethod.changeActivityWithText(RegisterActivity.this, Terms_Condition.class, "Terms & Conditions");
                break;
        }
    }


}
