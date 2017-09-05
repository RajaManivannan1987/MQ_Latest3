package com.moneyquotient.in.in.adapter.fragementpageadapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.moneyquotient.in.in.fragments.BankDetailFragment;
import com.moneyquotient.in.in.fragments.CreditCardFragment;
import com.moneyquotient.in.in.fragments.MoniterPieChartFragment;
import com.moneyquotient.in.in.fragments.MutualFundFragment;
import com.moneyquotient.in.in.model.Bank;
import com.moneyquotient.in.in.model.MainPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Im033 on 3/9/2017.
 */

public class CommonViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentsList = new ArrayList<>();
    private ArrayList<String> fragmentTitleList = new ArrayList<>();
    MainPojo mainPojo;

    JSONObject chartValue;

    public CommonViewPagerAdapter(FragmentManager fm, ArrayList<String> bundle, MainPojo mainPojo,JSONObject chartValue) {
        super(fm);
        this.fragmentTitleList = bundle;
        this.mainPojo = mainPojo;
        this.chartValue=chartValue;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MoniterPieChartFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("chart",  chartValue.toString());
                bundle1.putSerializable("asset",  mainPojo);
                fragment.setArguments(bundle1);
                break;
            case 1:
                fragment = new BankDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("bankdetail", (Serializable) mainPojo.getBankdetails().getData().getBank());
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = new CreditCardFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("bankdetail", (Serializable) mainPojo.getBankdetails().getData().getCreditcard());
                fragment.setArguments(bundle2);
                break;
            case 3:
                fragment = new MutualFundFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return fragmentTitleList.size();
    }

    public void addFragment(String title) {
//        fragmentsList.add(fragment);
        fragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
