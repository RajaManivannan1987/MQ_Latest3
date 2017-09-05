package com.moneyquotient.in.in.activity;

import android.os.Bundle;
import android.widget.Button;

import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonActivity;
import com.moneyquotient.in.in.common.CommonMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karth on 8/28/2017.
 */

public class SYNCBankAccount extends CommonActivity {
    @BindView(R.id.sync_bank_proceed_button)
    Button syncBankProceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_sync_bank_account, "SYNC Bank Account");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sync_bank_proceed_button)
    public void onViewClicked() {
        CommonMethod.changeActivity(SYNCBankAccount.this, Yodlee.class);
    }
}
