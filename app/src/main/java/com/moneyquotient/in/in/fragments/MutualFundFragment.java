package com.moneyquotient.in.in.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.moneyquotient.in.in.Interface.VolleyResponseListerner;
import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.adapter.RecyclerViewAdapter.MutualFundAdapter;
import com.moneyquotient.in.in.adapter.RecyclerViewAdapter.MutualFundsAdapter;
import com.moneyquotient.in.in.common.CustomAutoCompleteView;
import com.moneyquotient.in.in.model.Mutual;
import com.moneyquotient.in.in.model.MutualFunds;
import com.moneyquotient.in.in.utlity.Constant.ConstantValues;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;
import com.moneyquotient.in.in.utlity.webservice.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Im033 on 6/27/2017.
 */

public class MutualFundFragment extends Fragment {
    @BindView(R.id.getNAVButton)
    Button getNAVButton;
    @BindView(R.id.myautocomplete)
    CustomAutoCompleteView myautocomplete;
    @BindView(R.id.intervelAutoCompleteTextView)
    CustomAutoCompleteView intervelAutoCompleteTextView;
    @BindView(R.id.schemeAutoCompleteTextView)
    CustomAutoCompleteView schemeAutoCompleteTextView;
    @BindView(R.id.mutualfunds_recyclerview)
    RecyclerView mutualfundsRecyclerview;
    @BindView(R.id.schemeHeader)
    LinearLayout schemeHeader;
    private Unbinder unbind;
    private String TAG = "MoniterPieChartFragment";
    private ArrayList<MutualFunds> mutualfundsMainList = new ArrayList<>();
    private ArrayList<MutualFunds> mutualfundsCategoryList = new ArrayList<>();
    private ArrayList<MutualFunds> mutualfundsSubCategoryList = new ArrayList<>();
    private ArrayList<Mutual> list = new ArrayList<>();
    private MutualFundsAdapter adapter;
    private MutualFundAdapter schemeAdapter;
    private static String catagoryValue = "", subCategoryValue = "", schemeId = "";
    private Gson gson = new Gson();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mutual_funds, container, false);
        unbind = ButterKnife.bind(this, view);
        mutualfundsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myautocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catagoryValue = mutualfundsMainList.get(position).getFundid();
                loadData(ConstantValues.MUTUALFUNDCATEGORY, "categories", "fundid", catagoryValue, "", "", "", "");
            }
        });
        intervelAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subCategoryValue = mutualfundsCategoryList.get(position).getCategoryid();
                loadData(ConstantValues.MUTUALFUNDSUBCATEGORY, "subcategories", "fundid", catagoryValue, "categoryid", subCategoryValue, "", "");
            }
        });
        schemeAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                schemeId = mutualfundsSubCategoryList.get(position).getSubid();
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData(ConstantValues.MUTUALFUNDSMAIN, "home", "", "", "", "", "", "");
        }
    }

    private void loadData(String url, final String type, String c_key, String c_id, String sc_key, String sc_id, String schemeKey, String schemeId) {
        WebServices.getInstance(getActivity(), TAG).getMutualFunds(url, Session.getInstance(getActivity(), "tag").getUserid(),
                Session.getInstance(getActivity(), "tag").getToken(), type, c_key, c_id, sc_key, sc_id, schemeKey, schemeId, new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        if (type.equalsIgnoreCase("home")) {
                            mutualfundsMainList.clear();
                            if (response.getString("status").equalsIgnoreCase("ok")) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MutualFunds setValue = new MutualFunds();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    setValue.setType("home");
                                    setValue.setFundid(jsonObject.getString("fundid"));
                                    setValue.setFundname(jsonObject.getString("fundname"));
                                    mutualfundsMainList.add(setValue);
                                }
                            }
                            adapter = new MutualFundsAdapter(getActivity(), R.layout.mutual_funds_list_item, mutualfundsMainList, type);
                            myautocomplete.setAdapter(adapter);

                        } else if (type.equalsIgnoreCase("categories")) {
                            mutualfundsCategoryList.clear();
                            if (response.getString("status").equalsIgnoreCase("ok")) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MutualFunds setValue = new MutualFunds();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    setValue.setType("categories");
                                    setValue.setCategoryid(jsonObject.getString("categoryid"));
                                    setValue.setCategoryname(jsonObject.getString("categoryname"));
                                    mutualfundsCategoryList.add(setValue);
                                }
                            }
                            adapter = new MutualFundsAdapter(getActivity(), R.layout.mutual_funds_list_item, mutualfundsCategoryList, type);
                            intervelAutoCompleteTextView.setAdapter(adapter);

                        } else if (type.equalsIgnoreCase("subcategories")) {
                            mutualfundsSubCategoryList.clear();
                            if (response.getString("status").equalsIgnoreCase("ok")) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MutualFunds setValue = new MutualFunds();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    setValue.setType("subcategories");
                                    setValue.setSubid(jsonObject.getString("subid"));
                                    setValue.setSubcategoryname(jsonObject.getString("subcategoryname"));
                                    mutualfundsSubCategoryList.add(setValue);
                                }
                            }
                            adapter = new MutualFundsAdapter(getActivity(), R.layout.mutual_funds_list_item, mutualfundsSubCategoryList, type);
                            schemeAutoCompleteTextView.setAdapter(adapter);
                        } else if (type.equalsIgnoreCase("scheme")) {
                            mutualfundsSubCategoryList.clear();
                            if (response.getString("status").equalsIgnoreCase("ok")) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    list.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), Mutual.class));
                                }
                            }
                            schemeHeader.setVisibility(View.VISIBLE);
                            mutualfundsRecyclerview.setVisibility(View.VISIBLE);
                            schemeAdapter = new MutualFundAdapter(getActivity(), list);
                            mutualfundsRecyclerview.setAdapter(schemeAdapter);
                        }
                    }

                    @Override
                    public void onError(String message, String title) {

                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }


    @OnClick(R.id.getNAVButton)
    public void onViewClicked() {
        loadData(ConstantValues.SUBMIYMUTUALFUND, "scheme", "fundid", catagoryValue, "categoryid", subCategoryValue, "subid", schemeId);
    }
}
