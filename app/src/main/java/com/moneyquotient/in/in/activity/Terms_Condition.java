package com.moneyquotient.in.in.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karth on 8/16/2017.
 */

public class Terms_Condition extends CommonActivity {
    @BindView(R.id.termsCondition)
    TextView termsCondition;

    private String sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_terms_condition, getIntent().getStringExtra("text"));
        ButterKnife.bind(this);
        if(!getIntent().getStringExtra("text").equalsIgnoreCase("Terms & Conditions"))
        {
            termsCondition.setText(R.string.privacy_policy);
        }else {
            termsCondition.setText(R.string.terms_condition);
        }
    }
}
