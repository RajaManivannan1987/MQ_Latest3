package com.moneyquotient.in.in.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonActivity;
import com.moneyquotient.in.in.common.CommonMethod;
import com.moneyquotient.in.in.utlity.Constant.ConstantValues;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karth on 8/18/2017.
 */

public class RetirementCalculator extends CommonActivity {
    @BindView(R.id.retirementTotalTextView)
    TextView retirementTotalTextView;
    @BindView(R.id.retirement_monthly_expenses_textview)
    TextView retirementMonthlyExpensesTextview;
    @BindView(R.id.retirement_pieChart)
    PieChart retirementPieChart;
    @BindView(R.id.retirement_present_monthly_expenses_textview)
    TextView retirementPresentMonthlyExpensesTextview;
    @BindView(R.id.retirement_monthly_savings_required)
    TextView retirementMonthlySavingsRequired;
    @BindView(R.id.retirement_present_age)
    EditText retirementPresentAge;
    @BindView(R.id.retirement_present_age_seekbar)
    SeekBar retirementPresentAgeSeekbar;
    @BindView(R.id.retirement_age)
    EditText retirementAge;
    @BindView(R.id.retirement_age_seekbar)
    SeekBar retirementAgeSeekbar;
    @BindView(R.id.retirement_life_expectancy)
    EditText retirementLifeExpectancy;
    @BindView(R.id.retirement_life_expectancy_seekbar)
    SeekBar retirementLifeExpectancySeekbar;
    @BindView(R.id.retirement_present_monthly_expenses)
    EditText retirementPresentMonthlyExpenses;
    @BindView(R.id.retirement_present_monthly_expenses_seekBar)
    SeekBar retirementPresentMonthlyExpensesSeekBar;
    @BindView(R.id.retirement_inflation_rate)
    EditText retirementInflationRate;
    @BindView(R.id.retirement_inflation_rate_seekbar)
    SeekBar retirementInflationRateSeekbar;
    @BindView(R.id.retirement_return_on_investment)
    EditText retirementReturnOnInvestment;
    @BindView(R.id.retirement_return_on_investment_seekbar)
    SeekBar retirementReturnOnInvestmentSeekbar;
    @BindView(R.id.retirement_sum_required_on_investment)
    TextView retirementSumRequiredOnInvestment;
    @BindView(R.id.retirement_monthly_expenses_on_retirement)
    TextView retirementMonthlyExpensesOnRetirement;
    @BindView(R.id.retirement_monthly_savings_required_textview)
    TextView retirementMonthlySavingsRequiredTextview;

    private String TAG = RetirementCalculator.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_retirement, "Retirement Calculator");
        ButterKnife.bind(this);
        setseekBar(retirementPresentAge, retirementPresentAgeSeekbar, 18, 50, 1);
        setseekBar(retirementAge, retirementAgeSeekbar, 40, 85, 1);
        setseekBar(retirementLifeExpectancy, retirementLifeExpectancySeekbar, 60, 100, 1);
        setseekBar(retirementPresentMonthlyExpenses, retirementPresentMonthlyExpensesSeekBar, 1000, 200000, 1);
        setseekBar(retirementInflationRate, retirementInflationRateSeekbar, 4, 14, 1);
        setseekBar(retirementReturnOnInvestment, retirementReturnOnInvestmentSeekbar, 1, 100, 1);

    }

    private void setseekBar(final EditText editText, final SeekBar seekBar, final int min, int max, final float convertion) {
//        seekBar.setProgress(min);
        seekBar.setMax(max);
        seekBar.incrementProgressBy(min);
        editText.setText(min + "");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                progress = progress / min;
//                progress = progress * min;
                if (progress > min) {
                    editText.setText(progress + "");
                } else {
                    progress = min;
                    seekBar.setProgress(progress);
                    editText.setText(progress + "");

                }

                float MONTHLY_EXPENSES_ON_RETIREMENT = (float) (Float.valueOf(retirementPresentMonthlyExpenses.getText().toString())
                        * Math.pow((1 + (Float.valueOf(retirementInflationRate.getText().toString()) / 100))
                        , (Float.valueOf(retirementAge.getText().toString()) - Float.valueOf(retirementPresentAge.getText().toString()))));
                retirementMonthlyExpensesOnRetirement.setText("\u20B9 " + MONTHLY_EXPENSES_ON_RETIREMENT);

                float rop = (Float.valueOf(retirementReturnOnInvestment.getText().toString()) / 100) / 12;
                float nop = (Float.valueOf(retirementLifeExpectancy.getText().toString()) - Float.valueOf(retirementAge.getText().toString())) * 12;
                float SUM_REQUIRED_ON_RETIREMENT = (float) (Float.valueOf(MONTHLY_EXPENSES_ON_RETIREMENT) *
                        ((1 - (Math.pow((1 + rop), (-nop)))) / rop));


                float numberOfMonth = (Float.valueOf(retirementAge.getText().toString()) - Float.valueOf(retirementPresentAge.getText().toString())) * 12;
                float MONTHLY_SAVING_REQUIRED = (float) ((SUM_REQUIRED_ON_RETIREMENT * rop) / (1 - Math.pow((1 + rop), (-numberOfMonth))));

                CommonMethod.showLog(TAG, (1 - Math.pow((1 + rop), (-numberOfMonth))) + "");
                CommonMethod.showLog(TAG, rop + "retrunOnInvestment");
                CommonMethod.showLog(TAG, numberOfMonth + "numberOfMonth");
                retirementMonthlySavingsRequired.setText(MONTHLY_SAVING_REQUIRED + "");
                retirementSumRequiredOnInvestment.setText("\u20B9 " + (int) SUM_REQUIRED_ON_RETIREMENT);
                setPieChart();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    seekBar.setProgress(Integer.parseInt(v.getText().toString()));
                    return true;
                }
                return false;
            }
        });
    }

    public void setPieChart() {
        retirementPieChart.setUsePercentValues(true);
        retirementPieChart.getDescription().setEnabled(false);
        retirementPieChart.setExtraOffsets(5, 10, 5, 5);

        retirementPieChart.setDragDecelerationFrictionCoef(0.95f);

        retirementPieChart.setDrawHoleEnabled(false);

        retirementPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        retirementPieChart.setRotationEnabled(true);
        retirementPieChart.setHighlightPerTapEnabled(true);

        // add a selection listener
//        retirementPieChart.setOnChartValueSelectedListener(this);


        retirementPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // retirementPieChart.spin(2000, 0, 360);

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(Float.valueOf(retirementPresentMonthlyExpenses.getText().toString())));
        entries.add(new PieEntry(Float.valueOf(retirementMonthlyExpensesOnRetirement.getText().toString().replaceAll("\u20B9", ""))));
        PieDataSet dataSet = new PieDataSet(entries, "ROICalculator");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ConstantValues.MY_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        retirementPieChart.setData(data);

        // undo all highlights
        retirementPieChart.highlightValues(null);

        retirementPieChart.invalidate();

        Legend l = retirementPieChart.getLegend();
        l.setEnabled(false);

        // entry label styling
        retirementPieChart.setEntryLabelColor(Color.WHITE);
        retirementPieChart.setEntryLabelTextSize(12f);


    }

}
