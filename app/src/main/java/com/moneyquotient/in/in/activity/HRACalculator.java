package com.moneyquotient.in.in.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karth on 8/21/2017.
 */

public class HRACalculator extends CommonBackActivity {
    @BindView(R.id.metroRadio)
    RadioButton metroRadio;
    @BindView(R.id.nonmetroRadio)
    RadioButton nonmetroRadio;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.basicsalaryda_editext)
    EditText basicsalarydaEditext;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.hrareceived_editext)
    EditText hrareceivedEditext;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.actualrentpaid_editext)
    EditText actualrentpaidEditext;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.hraexemptiontextview)
    TextView hraexemptiontextview;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.HRA_Receivedtextview)
    TextView HRAReceivedtextview;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.percentofsalarytextview)
    TextView percentofsalarytextview;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.rentpaidtextview)
    TextView rentpaidtextview;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.done)
    Button done;

    private String city = "Metro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activtiy_hra, "HRA Calculator");
        ButterKnife.bind(this);
        calculateHRA();

    }

    @OnClick({R.id.cancel, R.id.done})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.cancel:
                intent.putExtra("HRA", "0");
                intent.putExtra("city", city);
                intent.putExtra("da", "0");
                intent.putExtra("rent", "0");

                setResult(IncomeTaxCalculator.REQUEST_CODE, intent);
                finish();
                break;
            case R.id.done:
                intent.putExtra("HRA", hraexemptiontextview.getText().toString());
                intent.putExtra("city", city);
                intent.putExtra("da", basicsalarydaEditext.getText().toString());
                intent.putExtra("rent", actualrentpaidEditext.getText().toString());
                intent.putExtra("HRACal", hrareceivedEditext.getText().toString());
                setResult(IncomeTaxCalculator.REQUEST_CODE, intent);
                finish();
                break;
        }
    }

    private void calculateHRA() {

        metroRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!basicsalarydaEditext.getText().toString().equalsIgnoreCase("")) {
                    if (isChecked) {
                        city = "Metro";
                        float basicsalInput = Float.valueOf(basicsalarydaEditext.getText().toString());
                        percentofsalarytextview.setText(Float.valueOf((float) (0.5 * basicsalInput)) + "");
                    } else {
                        city = "nonMetro";
                        float basicsalInput = Float.valueOf(basicsalarydaEditext.getText().toString());
                        percentofsalarytextview.setText(Float.valueOf((float) (0.4 * basicsalInput)) + "");
                    }
                }
            }
        });
        basicsalarydaEditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                percentofsalarytextview.setText("0");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!basicsalarydaEditext.getText().toString().equalsIgnoreCase("") && metroRadio.isChecked()) {
                    float basicsalInput = Float.valueOf(basicsalarydaEditext.getText().toString());
                    percentofsalarytextview.setText(Float.valueOf((float) (0.5 * basicsalInput)) + "");
                }
                if (!basicsalarydaEditext.getText().toString().equalsIgnoreCase("") && nonmetroRadio.isChecked()) {
                    float basicsalInput = Float.valueOf(basicsalarydaEditext.getText().toString());
                    percentofsalarytextview.setText(Float.valueOf((float) (0.4 * basicsalInput)) + "");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        hrareceivedEditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!hrareceivedEditext.getText().toString().equalsIgnoreCase("")) {
                    HRAReceivedtextview.setText(hrareceivedEditext.getText().toString());
                    hraexemptiontextview.setText(hrareceivedEditext.getText().toString());
                } else {
                    HRAReceivedtextview.setText("0");
                    hraexemptiontextview.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        actualrentpaidEditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!actualrentpaidEditext.getText().toString().equalsIgnoreCase("")) {
                    float actualrentpaidInput = Float.valueOf(actualrentpaidEditext.getText().toString());
                    float basicsalaryZInput = (float) (0.1 * Float.valueOf(basicsalarydaEditext.getText().toString()));
                    rentpaidtextview.setText(Float.valueOf(actualrentpaidInput - basicsalaryZInput) + "");
                    /// finding least
                    float hrareceivedOutput = Float.valueOf(HRAReceivedtextview.getText().toString());
                    float percentSalaryOutput = Float.valueOf(percentofsalarytextview.getText().toString());
                    float rentpaidinaxcessOutput = Float.valueOf(rentpaidtextview.getText().toString());
                    if (hrareceivedOutput < percentSalaryOutput && hrareceivedOutput < rentpaidinaxcessOutput) {
                        hraexemptiontextview.setText(hrareceivedOutput + "");
                    } else if (percentSalaryOutput < hrareceivedOutput && percentSalaryOutput < rentpaidinaxcessOutput) {
                        hraexemptiontextview.setText(percentSalaryOutput + "");
                    } else if (rentpaidinaxcessOutput < hrareceivedOutput && rentpaidinaxcessOutput < percentSalaryOutput) {
                        hraexemptiontextview.setText(rentpaidinaxcessOutput + "");
                    }
                } else {
                    rentpaidtextview.setText("0");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
