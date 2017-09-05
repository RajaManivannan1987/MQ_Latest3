package com.moneyquotient.in.in.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karth on 8/16/2017.
 */

public class EMI extends CommonActivity {

    @BindView(R.id.mChart)
    PieChart mChart;
    @BindView(R.id.emi_amount)
    EditText emiAmount;
    @BindView(R.id.emi_amount_seek_bar)
    SeekBar emiAmountSeekBar;
    @BindView(R.id.emi_years)
    EditText emiYears;
    @BindView(R.id.emi_years_seek_bar)
    SeekBar emiYearsSeekBar;
    @BindView(R.id.emi_interest)
    EditText emiInterest;
    @BindView(R.id.emi_interest_seek_bar)
    SeekBar emiInterestSeekBar;
    @BindView(R.id.emi_monthly)
    TextView emiMonthly;
    @BindView(R.id.emi_total_interest)
    TextView emiTotalInterest;
    @BindView(R.id.emi_totalPay)
    TextView emiTotalPay;

    private String TAG = EMI.class.getSimpleName();
    private String[] xValues = {"Loan Amount", "Interest Amount"};
    private int[] yValues1 = {40, 60};
    private int[] yValues2;
    public static final int[] MY_COLORS = {
            Color.rgb(50, 205, 50), Color.rgb(100, 149, 237)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_emi, "EMI Calculator");
        ButterKnife.bind(this);
        setseekBar(emiAmount, emiAmountSeekBar, 0, 50000000, 1.0f);
        setseekBar(emiYears, emiYearsSeekBar, 1, 30, 1.0f);
        setseekBar(emiInterest, emiInterestSeekBar, 1, 120, 4.0f);

    }


    private void setseekBar(final EditText editText, final SeekBar seekBar, final int min, int max, final float convertion) {
        seekBar.setProgress(min);
        seekBar.setMax(max);
        seekBar.incrementProgressBy(min);
        editText.setText(min + "");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                progress = progress / min;
//                progress = progress * min;
                if (progress > min) {
                    seekBar.setProgress(progress);
                    if (editText.getId() == R.id.emi_interest)
                        editText.setText(progress / convertion + "");
                    else
                        editText.setText(progress + "");
                } else {
                    progress = min;
                    seekBar.setProgress(progress);
                    if (editText.getId() == R.id.emi_interest)
                        editText.setText(progress / convertion + "");
                    else
                        editText.setText(progress + "");
                }
                float rValue = (Float.valueOf(emiInterest.getText().toString()) / 12) / 100;
                CommonMethod.showLog(TAG, rValue + "");
                double sqValue = Math.pow((1 + rValue), (Integer.parseInt(emiYears.getText().toString()) * 12));
                CommonMethod.showLog(TAG, sqValue + "");
//                emiTotalTextView.setText("EMI \u20B9 " + Math.round((Integer.parseInt(emiAmount.getText().toString()) * rValue) * (sqValue / (sqValue - 1))));
                emiTotalPay.setText("" + Math.round((Integer.parseInt(emiAmount.getText().toString()) * rValue) * (sqValue / (sqValue - 1)) * (Integer.parseInt(emiYears.getText().toString()) * 12)));
//                emiTotalPayTextView.setText("Total Pay \u20B9 " + Math.round((Integer.parseInt(emiAmount.getText().toString()) * rValue) * (sqValue / (sqValue - 1)) * (Integer.parseInt(emiYears.getText().toString()) * 12)));
                emiTotalInterest.setText("" + Math.round(((Integer.parseInt(emiAmount.getText().toString()) * rValue) * (sqValue / (sqValue - 1)) * (Integer.parseInt(emiYears.getText().toString()) * 12)) - Integer.parseInt(emiAmount.getText().toString())));
                emiMonthly.setText("" + Math.round((Integer.parseInt(emiAmount.getText().toString()) * rValue) * (sqValue / (sqValue - 1))));
//                emiLoanAmount.setText("Loan Amount \n \u20B9" + emiAmount.getText().toString());
//                emiinterestRate.setText("Total Interest \n \u20B9" + emiTotalInterest.getText().toString());
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
                    if (editText.getId() == R.id.emi_interest) {
                        if (!editText.getText().toString().equalsIgnoreCase("0")) {
                            CommonMethod.showLog(TAG, "" + Math.round(Float.valueOf(v.getText().toString()) * convertion));
                            seekBar.setProgress(Math.round(Float.valueOf(v.getText().toString()) * convertion));
                        } else
                            seekBar.setProgress(Math.round(0.25f * convertion));
                    } else
                        seekBar.setProgress(Integer.parseInt(v.getText().toString()));
                    return true;


                }
                return false;
            }
        });
    }


    public void setPieChart() {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setDrawHoleEnabled(false);


        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);


        // add a selection listener
//        mChart.setOnChartValueSelectedListener(this);


        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(Float.valueOf(emiAmount.getText().toString())));
        entries.add(new PieEntry(Float.valueOf(emiTotalInterest.getText().toString())));
        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setColors(MY_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();

        Legend l = mChart.getLegend();
        l.setEnabled(false);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);


    }
}
