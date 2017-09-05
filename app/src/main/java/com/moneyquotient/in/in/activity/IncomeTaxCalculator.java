package com.moneyquotient.in.in.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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
 * Created by karth on 8/21/2017.
 */

public class IncomeTaxCalculator extends CommonActivity {

    @BindView(R.id.tax_grossincome)
    EditText taxGrossincome;
    @BindView(R.id.tax_calchra)
    TextView taxCalchra;
    @BindView(R.id.tax_hraexemption)
    EditText taxHraexemption;
    @BindView(R.id.tax_otherexemptions)
    EditText taxOtherexemptions;
    @BindView(R.id.tax_professionaltax)
    EditText taxProfessionaltax;
    @BindView(R.id.tax_salarynetincome)
    TextView taxSalarynetincome;
    @BindView(R.id.tax_investment)
    EditText taxInvestment;
    @BindView(R.id.tax_investmentrgess)
    EditText taxInvestmentrgess;
    @BindView(R.id.tax_medicalinsurance)
    EditText taxMedicalinsurance;
    @BindView(R.id.tax_donations)
    EditText taxDonations;
    @BindView(R.id.tax_interestpaidoneduloan)
    EditText taxInterestpaidoneduloan;
    @BindView(R.id.tax_interestreceived)
    EditText taxInterestreceived;
    @BindView(R.id.tax_interestpaidonhomeloan)
    EditText taxInterestpaidonhomeloan;
    @BindView(R.id.tax_totaldeductionsbenefits)
    TextView taxTotaldeductionsbenefits;
    @BindView(R.id.tax_taxableincome)
    TextView taxTaxableincome;
    @BindView(R.id.tax_lessthansixty)
    RadioButton taxLessthansixty;
    @BindView(R.id.tax_sixtytoeightyyears)
    RadioButton taxSixtytoeightyyears;
    @BindView(R.id.tax_morethaneighty)
    RadioButton taxMorethaneighty;
    @BindView(R.id.tax_button)
    Button next;
    @BindView(R.id.tax_radioGroup)
    RadioGroup taxRadioGroup;
    @BindView(R.id.taxMainScrollView)
    ScrollView mainScrollView;
    @BindView(R.id.taxontotalincome)
    TextView taxontotalincome;
    @BindView(R.id.surcharge)
    TextView taxsurcharge;
    @BindView(R.id.taxwithsurcharge)
    TextView taxwithsurcharge;
    @BindView(R.id.educess)
    TextView taxeducess;
    @BindView(R.id.taxliability)
    TextView taxliability;
    @BindView(R.id.rebate)
    TextView taxrebate;
    @BindView(R.id.tdspaid)
    EditText taxtdspaid;
    @BindView(R.id.nettaxpayable)
    TextView taxnettaxpayable;
    @BindView(R.id.taxNextPage)
    ScrollView taxNextPage;
    @BindView(R.id.taxwithcess)
    TextView taxwithcess;

    private float taxGrossIncome = 0, hraExemption = 0, otherExemption = 0, professional = 0, investmentPF = 0, investmentRGESS = 0, medicalInsurance = 0,
            eligibleDonation = 0, interestEdu = 0, interestRecived = 0, interestPaidHome = 0;
    public static final int REQUEST_CODE = 101;

    private String TAG = IncomeTaxCalculator.class.getSimpleName();

    private String city = "Metro", da = "0", rent = "0", hraCal = "0";
    private boolean hracalculated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_income_tax, "Income Tax Calculator");
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Session.getInstance(this, TAG).getIsLogin()) {
            calculateSalaryNetIncome(taxGrossincome);
            calculateSalaryNetIncome(taxHraexemption);
            calculateSalaryNetIncome(taxOtherexemptions);
            calculateSalaryNetIncome(taxProfessionaltax);

            calculateTotalDeduction(taxInvestment);
            calculateTotalDeduction(taxInvestmentrgess);
            calculateTotalDeduction(taxMedicalinsurance);
            calculateTotalDeduction(taxDonations);
            calculateTotalDeduction(taxInterestpaidoneduloan);
            calculateTotalDeduction(taxInterestreceived);
            calculateTotalDeduction(taxInterestpaidonhomeloan);
        } else
            CommonMethod.changeActivity(this, LoginActivity.class);
    }

    private void calculateTotalDeduction(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateTotalDeduction1();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void calculateTotalDeduction1() {
        if (!taxInvestment.getText().toString().equalsIgnoreCase("")) {
            if (Float.valueOf(taxInvestment.getText().toString()) > 150000) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                CommonMethod.showSnackbar(taxCalchra, "Cannot be more than 1,50,000");
                taxInvestment.setText("150000");
                investmentPF = 150000;
            } else
                investmentPF = Float.valueOf(taxInvestment.getText().toString());
        }
        if (!taxInvestmentrgess.getText().toString().equalsIgnoreCase("") && !
                taxInvestmentrgess.getText().toString().equalsIgnoreCase("Not Applic"))
            investmentRGESS = Float.valueOf(taxInvestmentrgess.getText().toString()) / 2;

        if (!taxMedicalinsurance.getText().toString().equalsIgnoreCase("")) {
            if (Float.valueOf(taxMedicalinsurance.getText().toString()) > 55000) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                CommonMethod.showSnackbar(taxCalchra, "Cannot be more than 55,000");
                taxMedicalinsurance.setText("55000");
                medicalInsurance = 55000;
            } else
                medicalInsurance = Float.valueOf(taxMedicalinsurance.getText().toString());
        }

        if (!taxDonations.getText().toString().equalsIgnoreCase(""))
            eligibleDonation = Float.valueOf(taxDonations.getText().toString());
        if (!taxInterestpaidoneduloan.getText().toString().equalsIgnoreCase(""))
            interestEdu = Float.valueOf(taxInterestpaidoneduloan.getText().toString());
        if (!taxInterestreceived.getText().toString().equalsIgnoreCase("")) {
            if (Float.valueOf(taxInterestreceived.getText().toString()) > 10000) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                CommonMethod.showSnackbar(taxCalchra, "Cannot be more than 10,000");
                taxInterestreceived.setText("10000");
                investmentPF = 10000;
            } else
                interestRecived = Float.valueOf(taxInterestreceived.getText().toString());
        }

        if (!taxInterestpaidonhomeloan.getText().toString().equalsIgnoreCase("")) {
            if (Float.valueOf(taxInterestpaidonhomeloan.getText().toString()) > 200000) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                CommonMethod.showSnackbar(taxCalchra, "Cannot be more than 55,000");
                taxInterestpaidonhomeloan.setText("200000");
                interestPaidHome = 200000;
            } else
                interestPaidHome = Float.valueOf(taxInterestpaidonhomeloan.getText().toString());
        }


        taxTotaldeductionsbenefits.setText(investmentPF + investmentRGESS + medicalInsurance +
                eligibleDonation + interestEdu + interestRecived + interestPaidHome + "");
        taxTaxableincome.setText(Float.valueOf(taxSalarynetincome.getText().toString()) - Float.valueOf(taxTotaldeductionsbenefits.getText().toString()) + "");

    }

    private void calculateSalaryNetIncome(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getId() == R.id.tax_hraexemption)
                    hracalculated = false;
                calculateSalaryNetIncome1();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void calculateSalaryNetIncome1() {
        if (!taxGrossincome.getText().toString().equalsIgnoreCase("")) {
            if (Float.valueOf(taxGrossincome.getText().toString()) > 1200000) {
                taxInvestmentrgess.setText("Not Applicable");
                taxInvestmentrgess.setEnabled(false);
                investmentRGESS = 0;
            } else {
                taxInvestmentrgess.setText("");
                investmentRGESS = 0;
                taxInvestmentrgess.setEnabled(true);
            }
            taxGrossIncome = Float.valueOf(taxGrossincome.getText().toString());
        }
        if (!taxHraexemption.getText().toString().equalsIgnoreCase("")) {
            hraExemption = Float.valueOf(taxHraexemption.getText().toString());
        }
        if (!taxOtherexemptions.getText().toString().equalsIgnoreCase(""))
            otherExemption = Float.valueOf(taxOtherexemptions.getText().toString());
        if (!taxProfessionaltax.getText().toString().equalsIgnoreCase(""))
            professional = Float.valueOf(taxProfessionaltax.getText().toString());
        taxSalarynetincome.setText(taxGrossIncome - hraExemption - otherExemption - professional + "");


    }


    @OnClick({R.id.tax_calchra, R.id.tax_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tax_calchra:
                Intent intent = new Intent(IncomeTaxCalculator.this, HRACalculator.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.tax_button:
                if (next.getText().toString().equalsIgnoreCase("NEXT")) {
                    if (CommonMethod.checkEmpty(taxGrossincome, "") && CommonMethod.checkEmpty(taxHraexemption, "") &&
                            CommonMethod.checkEmpty(taxOtherexemptions, "") && CommonMethod.checkEmpty(taxProfessionaltax, "") && CommonMethod.checkEmpty(taxInvestment, "")
                            && CommonMethod.checkEmpty(taxInvestmentrgess, "") && CommonMethod.checkEmpty(taxMedicalinsurance, "") && CommonMethod.checkEmpty(taxDonations, "")
                            && CommonMethod.checkEmpty(taxInterestpaidoneduloan, "") && CommonMethod.checkEmpty(taxInterestreceived, "") && CommonMethod.checkEmpty(taxInterestpaidonhomeloan, "")
                            && taxRadioGroup.getCheckedRadioButtonId() != -1) {
                        next.setText("BACK");
                        mainScrollView.setVisibility(View.GONE);
                        taxNextPage.setVisibility(View.VISIBLE);
                        callTaxServices();
                    } else
                        CommonMethod.showSnackbar(taxCalchra, "Enter all fields");
                } else {
                    mainScrollView.setVisibility(View.VISIBLE);
                    taxNextPage.setVisibility(View.GONE);
                    next.setText("NEXT");
                }
                break;
        }
    }

    private void callTaxServices() {
        setCommonProgressBar();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", Session.getInstance(this, TAG).getUserid());
            jsonObject.put("token", Session.getInstance(this, TAG).getToken());
            jsonObject.put("total_income", taxGrossIncome);
            jsonObject.put("hra", hraExemption);
            jsonObject.put("professional", professional);
            jsonObject.put("other", otherExemption);
            jsonObject.put("SalaryNetIncome", taxSalarynetincome.getText().toString());
            jsonObject.put("deduct80c", investmentPF);
            jsonObject.put("deduct80ccg", investmentRGESS);
            jsonObject.put("deduct80d", medicalInsurance);
            jsonObject.put("deduct80e", interestEdu);
            jsonObject.put("deduct80g", eligibleDonation);
            jsonObject.put("deduct80tta", interestRecived);
            jsonObject.put("interest", interestPaidHome);
            jsonObject.put("TotalDeduction", taxTotaldeductionsbenefits.getText().toString());
            jsonObject.put("TaxableIncome", taxTaxableincome.getText().toString());
            if (hracalculated) {
                jsonObject.put("living_city", city);
                jsonObject.put("basic_da", da);
                jsonObject.put("hra_received", taxHraexemption.getText().toString());
                jsonObject.put("actual_rent", rent);
                jsonObject.put("calculated_hra", hraCal);
            } else {
                jsonObject.put("living_city", "Metro");
                jsonObject.put("basic_da", "0");
                jsonObject.put("hra_received", "0");
                jsonObject.put("actual_rent", "0");
                jsonObject.put("calculated_hra", "0");

            }
            if (taxLessthansixty.isChecked())
                jsonObject.put("age_type", "junior");
            else if (taxSixtytoeightyyears.isChecked())
                jsonObject.put("age_type", "seniour");
            else if (taxMorethaneighty.isChecked())
                jsonObject.put("age_type", "superseniour");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WebServices.getInstance(this, TAG).incomeTax(ConstantValues.INCOME_TAX, jsonObject, new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                hideCommonProgressBar();
                if (CommonMethod.checkTokenStauts(response, IncomeTaxCalculator.this)) {
                    if (response.getString("status").equalsIgnoreCase("1")) {
                        JSONObject data = response.getJSONObject("data");
                        taxontotalincome.setText(data.optString("total_taxable_value"));
                        taxsurcharge.setText(data.optString("surcharge"));
                        taxwithsurcharge.setText(data.optString("total_tax_surcharge_value"));
                        taxeducess.setText(data.optString("education_interest"));
                        taxwithcess.setText(data.optString("credit_value"));
                        taxliability.setText(data.optString("total_tax_liability"));
                        taxrebate.setText(data.optString("rebate"));
                        taxnettaxpayable.setText(data.optString("net_tax_pay"));
                        taxtdspaid.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (!taxtdspaid.getText().toString().equalsIgnoreCase("")) {
                                    taxnettaxpayable.setText(Float.valueOf(taxnettaxpayable.getText().toString()) - Float.valueOf(taxtdspaid.getText().toString().replaceAll(",", "")) + "");
                                } else {
                                    taxnettaxpayable.setText(Float.valueOf(taxnettaxpayable.getText().toString()) - 0 + "");
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    } else {
                        CommonMethod.showSnackbar(taxCalchra, response.getString("message"));
                    }
                }
            }

            @Override
            public void onError(String message, String title) {
                hideCommonProgressBar();
                CommonMethod.showSnackbar(taxCalchra, message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (data != null && data.hasExtra("HRA")) {
                taxHraexemption.setText(data.getStringExtra("HRA"));
                city = data.getStringExtra("city");
                da = data.getStringExtra("da");
                rent = data.getStringExtra("rent");
                hraCal = data.getStringExtra("HRACal");
                calculateSalaryNetIncome1();
                calculateTotalDeduction1();
                hracalculated = true;
            }

        }
    }

}
