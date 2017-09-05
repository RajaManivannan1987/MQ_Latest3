package com.moneyquotient.in.in.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonActivity;
import com.moneyquotient.in.in.common.CommonMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by karth on 8/21/2017.
 */

public class RiskCalculator extends CommonActivity {
    @BindView(R.id.q1A)
    RadioButton q1A;
    @BindView(R.id.q1B)
    RadioButton q1B;
    @BindView(R.id.q1C)
    RadioButton q1C;
    @BindView(R.id.q1D)
    RadioButton q1D;
    @BindView(R.id.optionsQ1)
    RadioGroup optionsQ1;
    @BindView(R.id.q2A)
    RadioButton q2A;
    @BindView(R.id.q2B)
    RadioButton q2B;
    @BindView(R.id.q2C)
    RadioButton q2C;
    @BindView(R.id.q2D)
    RadioButton q2D;
    @BindView(R.id.optionsQ2)
    RadioGroup optionsQ2;
    @BindView(R.id.q3A)
    RadioButton q3A;
    @BindView(R.id.q3B)
    RadioButton q3B;
    @BindView(R.id.q3C)
    RadioButton q3C;
    @BindView(R.id.q3D)
    RadioButton q3D;
    @BindView(R.id.optionsQ3)
    RadioGroup optionsQ3;
    @BindView(R.id.q4A)
    RadioButton q4A;
    @BindView(R.id.q4B)
    RadioButton q4B;
    @BindView(R.id.q4C)
    RadioButton q4C;
    @BindView(R.id.q4D)
    RadioButton q4D;
    @BindView(R.id.optionsQ4)
    RadioGroup optionsQ4;
    @BindView(R.id.q5A)
    RadioButton q5A;
    @BindView(R.id.q5B)
    RadioButton q5B;
    @BindView(R.id.q5C)
    RadioButton q5C;
    @BindView(R.id.optionsQ5)
    RadioGroup optionsQ5;
    @BindView(R.id.q6A)
    RadioButton q6A;
    @BindView(R.id.q6B)
    RadioButton q6B;
    @BindView(R.id.q6C)
    RadioButton q6C;
    @BindView(R.id.optionsQ6)
    RadioGroup optionsQ6;
    @BindView(R.id.q7A)
    RadioButton q7A;
    @BindView(R.id.q7B)
    RadioButton q7B;
    @BindView(R.id.q7C)
    RadioButton q7C;
    @BindView(R.id.optionsQ7)
    RadioGroup optionsQ7;
    @BindView(R.id.q8A)
    RadioButton q8A;
    @BindView(R.id.q8B)
    RadioButton q8B;
    @BindView(R.id.q8C)
    RadioButton q8C;
    @BindView(R.id.optionsQ8)
    RadioGroup optionsQ8;
    @BindView(R.id.q9A)
    RadioButton q9A;
    @BindView(R.id.q9B)
    RadioButton q9B;
    @BindView(R.id.q9C)
    RadioButton q9C;
    @BindView(R.id.q9D)
    RadioButton q9D;
    @BindView(R.id.optionsQ9)
    RadioGroup optionsQ9;
    @BindView(R.id.q10A)
    RadioButton q10A;
    @BindView(R.id.q10B)
    RadioButton q10B;
    @BindView(R.id.q10C)
    RadioButton q10C;
    @BindView(R.id.q10D)
    RadioButton q10D;
    @BindView(R.id.optionsQ10)
    RadioGroup optionsQ10;
    @BindView(R.id.submit)
    Button submit;

    private int i = 0, j = 0, k = 0, l = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_risk, "Risk Calculator");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (checkAnswered(optionsQ1) && checkAnswered(optionsQ2) && checkAnswered(optionsQ3)
                && checkAnswered(optionsQ4) && checkAnswered(optionsQ5) && checkAnswered(optionsQ6) && checkAnswered(optionsQ7)
                && checkAnswered(optionsQ8) && checkAnswered(optionsQ9) && checkAnswered(optionsQ10)) {
            calculateScore();
        } else {
            CommonMethod.showSnackbar(optionsQ1, "Fill  All Answers");
        }
    }

    private void calculateScore() {
        if (q1A.isChecked()) {
            i++;
        }
        if (q2A.isChecked()) {
            i++;
        }
        if (q3A.isChecked()) {
            i++;
        }
        if (q4A.isChecked()) {
            i++;
        }
        if (q5A.isChecked()) {
            i++;
        }
        if (q6A.isChecked()) {
            i++;
        }
        if (q7A.isChecked()) {
            i++;
        }
        if (q8A.isChecked()) {
            i++;
        }
        if (q9A.isChecked()) {
            i++;
        }
        if (q10A.isChecked()) {
            i++;
        }
        //B Selected
        if (q1B.isChecked()) {
            j++;
        }
        if (q2B.isChecked()) {
            j++;
        }
        if (q3B.isChecked()) {
            j++;
        }
        if (q4B.isChecked()) {
            j++;
        }
        if (q5B.isChecked()) {
            j++;
        }
        if (q6B.isChecked()) {
            j++;
        }
        if (q7B.isChecked()) {
            j++;
        }
        if (q8B.isChecked()) {
            j++;
        }
        if (q9B.isChecked()) {
            j++;
        }
        if (q10B.isChecked()) {
            j++;
        }
        //C selected
        if (q1C.isChecked()) {
            k++;
        }
        if (q2C.isChecked()) {
            k++;
        }
        if (q3C.isChecked()) {
            k++;
        }
        if (q4C.isChecked()) {
            k++;
        }
        if (q5C.isChecked()) {
            k++;
        }
        if (q6C.isChecked()) {
            k++;
        }
        if (q7C.isChecked()) {
            k++;
        }
        if (q8C.isChecked()) {
            k++;
        }
        if (q9C.isChecked()) {
            k++;
        }
        if (q10C.isChecked()) {
            k++;
        }
        //D selected
        if (q1D.isChecked()) {
            l++;
        }
        if (q2D.isChecked()) {
            l++;
        }
        if (q3D.isChecked()) {
            l++;
        }
        if (q4D.isChecked()) {
            l++;
        }
        if (q9D.isChecked()) {
            l++;
        }
        if (q10D.isChecked()) {
            l++;
        }
        dispRemark(i * 5 + j * 4 + k * 2 + l * 1);
        i = 0;
        j = 0;
        k = 0;
        l = 0;
    }

    public void dispRemark(int i) {
        String remark = "";
        if (i >= 41 && i <= 50) {
            remark = "Aggressive";

        }
        if (i >= 36 && i <= 40) {
            remark = "Moderately Aggressive";

        }
        if (i >= 26 && i <= 35) {
            remark = "Moderate";
        }
        if (i >= 21 && i <= 30) {
            remark = "Moderately Conservative ";
        }
        if (i < 20) {
            remark = "Conservative";
        }
        showResult(remark, i);
    }

    public void showResult(String remarktext, int i) {
        final Dialog result = new Dialog(this);
        result.setContentView(R.layout.dialog_risk_result);
        TextView remark = (TextView) result.findViewById(R.id.risk_dialog_remark);
        TextView star = (TextView) result.findViewById(R.id.risk_dialog_star);
        ImageView close = (ImageView) result.findViewById(R.id.risk_dialog_close);
        star.setText(i + "");
        remark.setText(remarktext);
        Window window = result.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        result.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        result.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.dismiss();
            }
        });
    }


    private boolean checkAnswered(RadioGroup radioGroup) {
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            return true;
        } else
            return false;
    }
}
