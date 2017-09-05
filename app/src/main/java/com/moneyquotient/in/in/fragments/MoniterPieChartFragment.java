package com.moneyquotient.in.in.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.moneyquotient.in.in.Interface.VolleyResponseListerner;
import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.activity.DetailExpenseActivity;
import com.moneyquotient.in.in.common.CommonMethod;
import com.moneyquotient.in.in.model.MainPojo;
import com.moneyquotient.in.in.utlity.Constant.ConstantValues;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;
import com.moneyquotient.in.in.utlity.webservice.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Im033 on 6/27/2017.
 */

public class MoniterPieChartFragment extends Fragment implements OnChartValueSelectedListener {
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.piechart)
    PieChart piechart;
    @BindView(R.id.moniterScroll)
    ScrollView moniterScroll;
    @BindView(R.id.asset_bankBalance)
    TextView assetBankBalance;
    @BindView(R.id.asset_realEstate)
    TextView assetRealEstate;
    @BindView(R.id.asset_fixedDetails)
    TextView assetFixedDetails;
    @BindView(R.id.asset_equity)
    TextView assetEquity;
    @BindView(R.id.asset_miscellaneous)
    TextView assetMiscellaneous;
    @BindView(R.id.asset_mutualFund)
    TextView assetMutualFund;
    @BindView(R.id.asset_bond)
    TextView assetBond;
    @BindView(R.id.asset_cash)
    TextView assetCash;
    @BindView(R.id.asset_epf)
    TextView assetEpf;
    @BindView(R.id.asset_gold_Jewels)
    TextView assetGoldJewels;
    @BindView(R.id.asset_total)
    TextView assetTotal;
    @BindView(R.id.liability_homeLoan)
    TextView liabilityHomeLoan;
    @BindView(R.id.liability_educationLoan)
    TextView liabilityEducationLoan;
    @BindView(R.id.liability_personalLoan)
    TextView liabilityPersonalLoan;
    @BindView(R.id.liability_carLoan)
    TextView liabilityCarLoan;
    @BindView(R.id.liability_otherLoan)
    TextView liabilityOtherLoan;
    @BindView(R.id.asset_liabilityTotal)
    TextView assetLiabilityTotal;
    @BindView(R.id.mq_score)
    TextView mqScore;
    @BindView(R.id.mq_meterStatus)
    TextView mqMeterStatus;
    @BindView(R.id.seekBarScale)
    SeekBar seekBarScale;
    @BindView(R.id.detailExpenceTextView)
    TextView detailExpenceTextView;
    @BindView(R.id.imgScale)
    ImageView imgScale;
    @BindView(R.id.networthTextView)
    TextView networthTextView;
    private Unbinder unbind;
    private String TAG = "MoniterPieChartFragment";
    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
    private String total = "";
    JSONObject object;
    MainPojo asset;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moniter_piechart, container, false);
        unbind = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        try {
            object = new JSONObject(bundle.getString("chart"));
            asset = (MainPojo) bundle.getSerializable("asset");
            loadDashboard(asset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        seekBarScale.setEnabled(false);
        loadData(object);
        return view;
    }

    private void loadData(JSONObject object) {
        Iterator i = object.keys();
        entries.clear();
        while (i.hasNext()) {
            String vbapKey = (String) i.next();
            try {
                if (!object.getString(vbapKey).equalsIgnoreCase("")) {
                    double val = Double.parseDouble(object.getString(vbapKey));
                    int lastval = (int) Math.round(val);
                    entries.add(new PieEntry(lastval));
                } else {
                    entries.add(new PieEntry(10));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setPieData();
    }

    private void loadDashboard(final MainPojo asset) {
        assetTotal.setText(asset.getAsset());
        assetLiabilityTotal.setText(asset.getLiability());
        mqScore.setText(asset.getScore() + "%");
        seekBarScale.setProgress(Integer.parseInt(asset.getScore()));
        networthTextView.setText("Net Worth RS " + asset.getNetworth());
        total = asset.getInflow();


        MainPojo.Assetlist assets = asset.getAssetlist();

        assetBankBalance.setText(assets.getBankBalance());
        assetRealEstate.setText(assets.getRealEstate());
        assetFixedDetails.setText(assets.getFixedDeposit());
        assetEquity.setText(assets.getEquity());
        assetMiscellaneous.setText(assets.getMiscellaneous());
        assetMutualFund.setText(assets.getMutualFund());
        assetBond.setText(assets.getBond());
        assetCash.setText(assets.getCash());
        assetEpf.setText(assets.getEpf());
        assetGoldJewels.setText(assets.getGold_Jewels());

        MainPojo.Liabilitylist liabilitylist = asset.getLiabilitylist();

        liabilityHomeLoan.setText(liabilitylist.getHomeLoan());
        liabilityEducationLoan.setText(liabilitylist.getEducationLoan());
        liabilityPersonalLoan.setText(liabilitylist.getPersonalLoan());
        liabilityCarLoan.setText(liabilitylist.getCarLoan());
        liabilityOtherLoan.setText(liabilitylist.getOtherLoan());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    private void setPieData() {
        piechart.setUsePercentValues(false);
        piechart.setDrawSliceText(false);
        PieDataSet dataSet = new PieDataSet(entries, "");

        piechart.setDrawHoleEnabled(true);
        piechart.setHoleColor(Color.TRANSPARENT);
        piechart.setHoleRadius(70f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        final int[] MY_COLORS = {Color.rgb(249, 134, 28), Color.rgb(243, 105, 102), Color.rgb(84, 111, 122),
                Color.rgb(94, 198, 211), Color.rgb(144, 109, 175), Color.rgb(153, 188, 58), Color.rgb(235, 82, 68), Color.rgb(135, 130, 134), Color.rgb(8, 53, 82)};
        for (int c : MY_COLORS) colors.add(c);
        dataSet.setColors(colors);

        //dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        piechart.setOnChartValueSelectedListener(this);
        piechart.setRotationEnabled(false);
        piechart.setHighlightPerTapEnabled(true);
        piechart.animateXY(2500, 2500);

        Legend legend = piechart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        legend.setWordWrapEnabled(false);
        legend.setEnabled(false);

        Description des = piechart.getDescription();
        des.setEnabled(false);
        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.WHITE);
        data.setDrawValues(false);
        piechart.setData(data);
        piechart.setTouchEnabled(false);
        piechart.setCenterTextColor(Color.WHITE);
        piechart.setCenterTextSize(18f);
        piechart.setCenterText("TOTAL" + "\n" + "EXPENSES" + "\n" + total);
        piechart.setContentDescription("");
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
//        Toast.makeText(getActivity(), "" + h.getY(), Toast.LENGTH_SHORT).show();
//        piechart.setCenterText("TOTAL" + "\n" + "EXPENSES" + "\n" + piechartArray[Math.round(h.getX())]);
        piechart.setCenterText("TOTAL" + "\n" + "EXPENSES" + "\n" + total);
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void setMenuVisibility(boolean isVisibleToUser) {
        if (isVisibleToUser && moniterScroll != null) {
//            setScreenTop(thertreScroll);
        }
        super.setMenuVisibility(isVisibleToUser);
    }


    @OnClick(R.id.detailExpenceTextView)
    public void onViewClicked() {
//        startActivity(new Intent(getActivity(), DetailExpenseActivity.class).putExtra("chart", object.toString()));
    }
}
