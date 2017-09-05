package com.moneyquotient.in.in.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karth on 8/10/2017.
 */

public class DashBoardActivity extends AppCompatActivity {

    @BindView(R.id.dashboard_emi)
    ImageView dashboardEmi;
    @BindView(R.id.dashboard_roi)
    ImageView dashboardRoi;
    @BindView(R.id.dashboard_retirement)
    ImageView dashboardRetirement;
    @BindView(R.id.dashboard_risk)
    ImageView dashboardRisk;
    @BindView(R.id.dashboard_incometax)
    ImageView dashboardIncometax;
    @BindView(R.id.dashboard_wealth_meter)
    CardView dashboardWealthMeter;
    @BindView(R.id.dashboard_money_inflow)
    CardView dashboardMoneyInflow;
    @BindView(R.id.dashboard_asserts)
    CardView dashboardAsserts;
    @BindView(R.id.dashboard_sync_bank)
    CardView dashboardSyncBank;
    @BindView(R.id.dashboard_terms)
    LinearLayout dashboardTerms;
    @BindView(R.id.dashboard_privacy)
    LinearLayout dashboardPrivacy;
    @BindView(R.id.dashboard_security)
    LinearLayout dashboardSecurity;
    @BindView(R.id.dashboard_remainders)
    LinearLayout dashboardRemainders;
    @BindView(R.id.dashboard_help)
    LinearLayout dashboardHelp;
    @BindView(R.id.dashboard_login)
    LinearLayout dashboardLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.dashboard_emi, R.id.dashboard_roi, R.id.dashboard_retirement, R.id.dashboard_risk, R.id.dashboard_incometax, R.id.dashboard_wealth_meter, R.id.dashboard_money_inflow, R.id.dashboard_asserts, R.id.dashboard_sync_bank, R.id.dashboard_terms, R.id.dashboard_privacy, R.id.dashboard_security, R.id.dashboard_remainders, R.id.dashboard_help, R.id.dashboard_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dashboard_emi:
                CommonMethod.changeActivity(DashBoardActivity.this, EMICalculator.class);
                break;
            case R.id.dashboard_roi:
                CommonMethod.changeActivity(DashBoardActivity.this, ROICalculator.class);
                break;
            case R.id.dashboard_retirement:
                CommonMethod.changeActivity(DashBoardActivity.this, RetirementCalculator.class);
                break;
            case R.id.dashboard_risk:
                CommonMethod.changeActivity(DashBoardActivity.this, RiskCalculator.class);
                break;
            case R.id.dashboard_incometax:
                CommonMethod.changeActivity(DashBoardActivity.this, IncomeTaxCalculator.class);
                break;
            case R.id.dashboard_wealth_meter:
                CommonMethod.changeActivity(DashBoardActivity.this, LoginActivity.class);
                break;
            case R.id.dashboard_money_inflow:
                CommonMethod.changeActivity(DashBoardActivity.this, LoginActivity.class);
                break;
            case R.id.dashboard_asserts:
                CommonMethod.changeActivity(DashBoardActivity.this, LoginActivity.class);
                break;
            case R.id.dashboard_sync_bank:
                CommonMethod.changeActivity(DashBoardActivity.this, SYNCBankAccount.class);
                break;
            case R.id.dashboard_terms:
                CommonMethod.changeActivityWithText(DashBoardActivity.this, Terms_Condition.class, "Terms & Conditions");
                break;
            case R.id.dashboard_privacy:
                CommonMethod.changeActivityWithText(DashBoardActivity.this, Terms_Condition.class, "Privacy Policy");
                break;
            case R.id.dashboard_security:
                CommonMethod.changeActivity(DashBoardActivity.this, SecuritySettings.class);
                break;
            case R.id.dashboard_remainders:
                break;
            case R.id.dashboard_help:
                break;
            case R.id.dashboard_login:
                CommonMethod.changeActivity(DashBoardActivity.this, LoginActivity.class);
                break;
        }
    }
}
