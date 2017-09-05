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
import com.moneyquotient.in.in.utlity.Constant.ConstantValues;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karth on 8/17/2017.
 */

public class ROICalculator extends CommonActivity {

    @BindView(R.id.roi_totalGain_textview)
    TextView roiTotalGainTextView;
    @BindView(R.id.roi_pieChart)
    PieChart roiPieChart;
    @BindView(R.id.roi_invested_amount_textview)
    TextView roiInvestedAmountTextview;
    @BindView(R.id.roi_invested_amount)
    EditText roiInvestedAmount;
    @BindView(R.id.roi_intvested_amount_seek_bar)
    SeekBar roiIntvestedAmountSeekBar;
    @BindView(R.id.roi_interim_returns)
    EditText roiInterimReturns;
    @BindView(R.id.roi_interim_returns_seek_bar)
    SeekBar roiInterimReturnsSeekBar;
    @BindView(R.id.roi_maturity_value)
    EditText roiMaturityValue;
    @BindView(R.id.roi_maturity_value_seek_bar)
    SeekBar roiMaturityValueSeekBar;
    @BindView(R.id.roi_period)
    EditText roiPeriod;
    @BindView(R.id.roi_period_seek_bar)
    SeekBar roiPeriodSeekBar;
    @BindView(R.id.roi_capital_gain)
    TextView roiCapitalGain;
    @BindView(R.id.roi_total_gain)
    TextView roiTotalGain;
    @BindView(R.id.roi_return_on_investment)
    TextView roi_return_on_investment;
    @BindView(R.id.roi_available_return)
    TextView roiAvailableReturn;

    private String TAG = ROICalculator.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_roi, "ROI Calculator");
        ButterKnife.bind(this);
        setseekBar(roiInvestedAmount, roiIntvestedAmountSeekBar, 100000, 20000000, 1.0f);
        setseekBar(roiInterimReturns, roiInterimReturnsSeekBar, 10000, 300000, 1.0f);
        setseekBar(roiMaturityValue, roiMaturityValueSeekBar, Integer.parseInt(roiInvestedAmount.getText().toString()), 50000000, 1.0f);
        setseekBar(roiPeriod, roiPeriodSeekBar, 1, 30, 1.0f);
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
                    if (seekBar.getId() == R.id.roi_intvested_amount_seek_bar) {
                        setseekBar(roiMaturityValue, roiMaturityValueSeekBar, progress, 50000000, 1.0f);
                    }
                    editText.setText(progress + "");
                } else {
                    progress = min;
                    seekBar.setProgress(progress);
                    editText.setText(progress + "");
                    if (seekBar.getId() == R.id.roi_intvested_amount_seek_bar) {
                        setseekBar(roiMaturityValue, roiMaturityValueSeekBar, progress, 50000000, 1.0f);
                    }
                }
                float capitalGain = Integer.parseInt(roiMaturityValue.getText().toString()) - Integer.parseInt(roiInvestedAmount.getText().toString());
                float totalGain = Integer.parseInt(roiInterimReturns.getText().toString()) + capitalGain;
                float returnOnInvestment = (totalGain / Integer.parseInt(roiInvestedAmount.getText().toString())) * 100;
                roiCapitalGain.setText("\u20B9 " + (int) capitalGain + ".00");
                roiTotalGain.setText("\u20B9 " + (int) totalGain + ".00");
                roi_return_on_investment.setText(returnOnInvestment + " % ");
                roiAvailableReturn.setText(returnOnInvestment / Integer.parseInt(roiPeriod.getText().toString()) + " % ");
                roiTotalGainTextView.setText("Total Gain \n \u20B9 " + (int) totalGain + ".00");
                roiInvestedAmountTextview.setText("Invested Amount \n \u20B9" + roiInvestedAmount.getText().toString());
                setPieChart((int) totalGain);
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


    public void setPieChart(int totalGain) {
        roiPieChart.setUsePercentValues(true);
        roiPieChart.getDescription().setEnabled(false);
        roiPieChart.setExtraOffsets(5, 10, 5, 5);

        roiPieChart.setDragDecelerationFrictionCoef(0.95f);

        roiPieChart.setDrawHoleEnabled(false);

        roiPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        roiPieChart.setRotationEnabled(true);
        roiPieChart.setHighlightPerTapEnabled(true);

        // add a selection listener
//        roiPieChart.setOnChartValueSelectedListener(this);


        roiPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // roiPieChart.spin(2000, 0, 360);

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(totalGain));
        entries.add(new PieEntry(Float.valueOf(roiInvestedAmount.getText().toString())));
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

        roiPieChart.setData(data);

        // undo all highlights
        roiPieChart.highlightValues(null);

        roiPieChart.invalidate();

        Legend l = roiPieChart.getLegend();
        l.setEnabled(false);

        // entry label styling
        roiPieChart.setEntryLabelColor(Color.WHITE);
        roiPieChart.setEntryLabelTextSize(12f);


    }


}
