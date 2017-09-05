package com.moneyquotient.in.in.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.moneyquotient.in.in.Interface.VolleyResponseListerner;
import com.moneyquotient.in.in.adapter.fragementpageadapter.CommonViewPagerAdapter;
import com.moneyquotient.in.in.common.CommonActivity;
import com.moneyquotient.in.in.common.CommonMethod;
import com.moneyquotient.in.in.model.Bank;
import com.moneyquotient.in.in.model.MainPojo;
import com.moneyquotient.in.in.utlity.Constant.ConstantValues;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;
import com.moneyquotient.in.in.utlity.webservice.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Im033 on 6/26/2017.
 */

public class WealthSummaryActivity extends CommonActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String TAG = "WealthSummaryActivity";
    private JSONObject chartvaluesObject;
    Bundle bundle = new Bundle();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<Bank> bankList = new ArrayList<>();
    Gson gson = new Gson();
    CommonViewPagerAdapter adapter;
    MainPojo mainPojo = new MainPojo();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(com.moneyquotient.in.in.R.layout.activity_wealth_summary, "WEALTH SUMMARY");
        viewPager = (ViewPager) findViewById(com.moneyquotient.in.in.R.id.viewpager);
        tabLayout = (TabLayout) findViewById(com.moneyquotient.in.in.R.id.tabs);
        title.add("Moniter");
        title.add("Bank Details");
        title.add("Credit Card");
        title.add("Mutual Funds");
        loadData(ConstantValues.DASHBOARD);


    }

    private void loadData(String url) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        WebServices.getInstance(this, TAG).getflow(url, Session.getInstance(this, "tag").getUserid(),
                Session.getInstance(this, "tag").getToken(), new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        if (CommonMethod.checkTokenStauts(response, WealthSummaryActivity.this))
                            if (response.getString("status").equalsIgnoreCase("1")) {
                                setUpViewPager(viewPager, gson.fromJson(response.getJSONObject("data").toString(), MainPojo.class), response.getJSONObject("data").getJSONObject("chartvalues"));
                            }

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onError(String message, String title) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void setUpViewPager(ViewPager viewPager, MainPojo mainPojo, JSONObject jsonObject) {
        adapter = new CommonViewPagerAdapter(getSupportFragmentManager(), title, mainPojo, jsonObject);
        /*adapter.addFragment("Monitor");
        adapter.addFragment("Bank Details");
        adapter.addFragment("Mutual Funds");
        adapter.addFragment("Credit Card");*/
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        viewPager.setOnPageChangeListener(myOnPageChangeListener);
    }

    ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            hideKeyboard();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            hideKeyboard();
        }
    };

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
