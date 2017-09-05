package com.moneyquotient.in.in.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonActivity;
import com.moneyquotient.in.in.common.CommonMethod;
import com.moneyquotient.in.in.utlity.sharedPreferance.PassocodePreferance;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karth on 8/22/2017.
 */

public class SecuritySettings extends CommonActivity {
    @BindView(R.id.security_passcode)
    Switch securityPasscode;
    @BindView(R.id.changePassword)
    TextView changePassword;

    private String TAG = SecuritySettings.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_security_settings, "Security Settings");
        ButterKnife.bind(this);
        if (PassocodePreferance.getInstance(getApplicationContext(), TAG).checkAsPasscode()) {
            securityPasscode.setChecked(true);
            changePassword.setEnabled(true);
            changePassword.setTextColor(Color.BLACK);
        } else
            securityPasscode.setChecked(false);


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.changeActivityWithText(SecuritySettings.this,FingerprintActivity.class,"Change Password");
            }
        });
        securityPasscode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CommonMethod.changeActivityWithText(SecuritySettings.this, FingerprintActivity.class, "Passcode");
                    finish();
                } else {
                    PassocodePreferance.getInstance(SecuritySettings.this, TAG).clearPasscode();
                }
            }
        });
    }

}
