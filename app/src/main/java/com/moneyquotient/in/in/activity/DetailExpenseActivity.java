package com.moneyquotient.in.in.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
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
import com.moneyquotient.in.in.common.CommonBackActivity;
import com.moneyquotient.in.in.common.CommonMethod;
import com.moneyquotient.in.in.utlity.Constant.ConstantValues;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;
import com.moneyquotient.in.in.utlity.webservice.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Im033 on 6/27/2017.
 */

public class DetailExpenseActivity extends CommonBackActivity implements OnChartValueSelectedListener {
    @BindView(com.moneyquotient.in.in.R.id.textView3)
    TextView textView3;
    @BindView(com.moneyquotient.in.in.R.id.piechart)
    PieChart piechart;
    @BindView(com.moneyquotient.in.in.R.id.moniterScroll)
    ScrollView moniterScroll;
    @BindView(com.moneyquotient.in.in.R.id.detail_utility_image)
    ImageView detailUtilityImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_utility)
    TextView detailUtility;
    @BindView(com.moneyquotient.in.in.R.id.detail_utility_value)
    TextView detailUtilityValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_utility_persentage)
    TextView detailUtilityPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_houserent_image)
    ImageView detailHouserentImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_houserent)
    TextView detailHouserent;
    @BindView(com.moneyquotient.in.in.R.id.detail_houserent_value)
    TextView detailHouserentValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_houserent_persentage)
    TextView detailHouserentPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_grocery_image)
    ImageView detailGroceryImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_grocery)
    TextView detailGrocery;
    @BindView(com.moneyquotient.in.in.R.id.detail_grocery_value)
    TextView detailGroceryValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_grocery_persentage)
    TextView detailGroceryPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_medical_image)
    ImageView detailMedicalImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_medical)
    TextView detailMedical;
    @BindView(com.moneyquotient.in.in.R.id.detail_medical_value)
    TextView detailMedicalValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_medical_persentage)
    TextView detailMedicalPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_entertainment_image)
    ImageView detailEntertainmentImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_entertainment)
    TextView detailEntertainment;
    @BindView(com.moneyquotient.in.in.R.id.detail_entertainment_value)
    TextView detailEntertainmentValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_entertainment_persentage)
    TextView detailEntertainmentPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_dining_image)
    ImageView detailDiningImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_dining)
    TextView detailDining;
    @BindView(com.moneyquotient.in.in.R.id.detail_dining_value)
    TextView detailDiningValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_dining_persentage)
    TextView detailDiningPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_travel_image)
    ImageView detailTravelImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_travel)
    TextView detailTravel;
    @BindView(com.moneyquotient.in.in.R.id.detail_travel_value)
    TextView detailTravelValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_travel_persentage)
    TextView detailTravelPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_education_image)
    ImageView detailEducationImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_education)
    TextView detailEducation;
    @BindView(com.moneyquotient.in.in.R.id.detail_education_value)
    TextView detailEducationValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_education_persentage)
    TextView detailEducationPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_maintenance_image)
    ImageView detailMaintenanceImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_maintenance)
    TextView detailMaintenance;
    @BindView(com.moneyquotient.in.in.R.id.detail_maintenance_value)
    TextView detailMaintenanceValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_maintenance_persentage)
    TextView detailMaintenancePersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_sip_image)
    ImageView detailSipImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_sip)
    TextView detailSip;
    @BindView(com.moneyquotient.in.in.R.id.detail_sip_value)
    TextView detailSipValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_sip_persentage)
    TextView detailSipPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_rd_image)
    ImageView detailRdImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_rd)
    TextView detailRd;
    @BindView(com.moneyquotient.in.in.R.id.detail_rd_value)
    TextView detailRdValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_rd_persentage)
    TextView detailRdPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_others_image)
    ImageView detailOthersImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_others)
    TextView detailOthers;
    @BindView(com.moneyquotient.in.in.R.id.detail_others_value)
    TextView detailOthersValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_others_persentage)
    TextView detailOthersPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_fuel_image)
    ImageView detailFuelImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_fuel)
    TextView detailFuel;
    @BindView(com.moneyquotient.in.in.R.id.detail_fuel_value)
    TextView detailFuelValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_fuel_persentage)
    TextView detailFuelPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_creditcard_image)
    ImageView detailCreditcardImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_creditcard)
    TextView detailCreditcard;
    @BindView(com.moneyquotient.in.in.R.id.detail_creditcard_value)
    TextView detailCreditcardValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_creditcard_persentage)
    TextView detailCreditcardPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_misothers_image)
    ImageView detailMisothersImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_misothers)
    TextView detailMisothers;
    @BindView(com.moneyquotient.in.in.R.id.detail_misothers_value)
    TextView detailMisothersValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_misothers_persentage)
    TextView detailMisothersPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_mortgage_image)
    ImageView detailMortgageImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_mortgage)
    TextView detailMortgage;
    @BindView(com.moneyquotient.in.in.R.id.detail_mortgage_value)
    TextView detailMortgageValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_mortgage_persentage)
    TextView detailMortgagePersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_personalcare_image)
    ImageView detailPersonalcareImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_personalcare)
    TextView detailPersonalcare;
    @BindView(com.moneyquotient.in.in.R.id.detail_personalcare_value)
    TextView detailPersonalcareValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_personalcare_persentage)
    TextView detailPersonalcarePersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_shopping_image)
    ImageView detailShoppingImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_shopping)
    TextView detailShopping;
    @BindView(com.moneyquotient.in.in.R.id.detail_shopping_value)
    TextView detailShoppingValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_shopping_persentage)
    TextView detailShoppingPersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_insurance_image)
    ImageView detailInsuranceImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_insurance)
    TextView detailInsurance;
    @BindView(com.moneyquotient.in.in.R.id.detail_insurance_value)
    TextView detailInsuranceValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_insurance_persentage)
    TextView detailInsurancePersentage;
    @BindView(com.moneyquotient.in.in.R.id.detail_emi_image)
    ImageView detailEmiImage;
    @BindView(com.moneyquotient.in.in.R.id.detail_emi)
    TextView detailEmi;
    @BindView(com.moneyquotient.in.in.R.id.detail_emi_value)
    TextView detailEmiValue;
    @BindView(com.moneyquotient.in.in.R.id.detail_emi_persentage)
    TextView detailEmiPersentage;

    private String TAG = "MoniterPieChartFragment";
    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
    private String total = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(com.moneyquotient.in.in.R.layout.activity_detail_expenses, "Expenses Details");
        ButterKnife.bind(this);

        try {
            loadData(new JSONObject((String) getIntent().getSerializableExtra("chart")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void loadData(JSONObject chart) {
        try {
            setText(chart);
            Iterator i = chart.keys();
            entries.clear();
            while (i.hasNext()) {
                String vbapKey = (String) i.next();
                if (!chart.getString(vbapKey).equalsIgnoreCase("")) {
                    double val = Double.parseDouble(chart.getString(vbapKey));
                    int lastval = (int) Math.round(val);
                    entries.add(new PieEntry(lastval));
                } else {
                    entries.add(new PieEntry(10));
                }
            }
            setPieData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        piechart.setTouchEnabled(true);
        piechart.setCenterTextColor(Color.WHITE);
        piechart.setCenterTextSize(18f);
        piechart.setCenterText("TOTAL" + "\n" + "EXPENSES" + "\n" + total);
        piechart.setContentDescription("");
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
//        Toast.makeText(this, "" + h.getY(), Toast.LENGTH_SHORT).show();
//        piechart.setCenterText("TOTAL" + "\n" + "EXPENSES" + "\n" + piechartArray[Math.round(h.getX())]);
        piechart.setCenterText("TOTAL" + "\n" + "EXPENSES" + "\n" + total);
    }

    @Override
    public void onNothingSelected() {

    }

    void setText(JSONObject object) throws JSONException {
        detailUtilityValue.setText(object.getString("utility"));
        detailHouserentValue.setText(object.getString("houserent"));
        detailGroceryValue.setText(object.getString("grocery"));
        detailMedicalValue.setText(object.getString("medical"));
        detailEntertainmentValue.setText(object.getString("entertainment"));
        detailDiningValue.setText(object.getString("dining"));
        detailTravelValue.setText(object.getString("travel"));
        detailEducationValue.setText(object.getString("education"));
        detailMaintenanceValue.setText(object.getString("maintenance"));
        detailSipValue.setText(object.getString("sip"));
        detailRdValue.setText(object.getString("rd"));
        detailOthersValue.setText(object.getString("others"));
        detailFuelValue.setText(object.getString("fuel"));
        detailCreditcardValue.setText(object.getString("creditcard"));
        detailMisothersValue.setText(object.getString("misothers"));
        detailMortgageValue.setText(object.getString("mortgage"));
        detailPersonalcareValue.setText(object.getString("personalcare"));
        detailShoppingValue.setText(object.getString("shopping"));
        detailInsuranceValue.setText(object.getString("insurance"));
        detailEmiValue.setText(object.getString("emi"));
    }

}
