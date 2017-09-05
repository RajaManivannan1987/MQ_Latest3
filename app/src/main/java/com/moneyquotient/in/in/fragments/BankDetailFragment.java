package com.moneyquotient.in.in.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moneyquotient.in.in.Interface.ActionComplet;
import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.adapter.RecyclerViewAdapter.BankDetailsAdapter;
import com.moneyquotient.in.in.model.Bank;
import com.moneyquotient.in.in.sigleton.ActionOverSingleton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Im033 on 6/27/2017.
 */

public class BankDetailFragment extends Fragment {
    @BindView(R.id.banklist_recyclerview)
    RecyclerView banklistRecyclerview;

    /* @BindView(R.id.refreshLayout)
     SwipeRefreshLayout refreshLayout;*/
    private Unbinder unbind;
    private String TAG = "BankDetailFragment";
    ArrayList<Bank> detail = new ArrayList<>();

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_details, container, false);
        unbind = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        detail = (ArrayList<Bank>) bundle.getSerializable("bankdetail");
        Log.d(TAG, detail.toString());
        banklistRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        banklistRecyclerview.setAdapter(new BankDetailsAdapter(getActivity(), detail));
//        loadData(ConstantValues.GETACCOUNTS);

        ActionOverSingleton.getActionOverSingleton().setComplet(new ActionComplet() {
            @Override
            public void complete() {
//                loadData(ConstantValues.GETACCOUNTS);
            }
        });
        /*refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ActionOverSingleton.getActionOverSingleton().ActionCompleted();
            }
        });*/
        /*refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }
}
