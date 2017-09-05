package com.moneyquotient.in.in.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moneyquotient.in.in.R;

/**
 * Created by karth on 8/16/2017.
 */

public class NavigationDrawerBaseAdapter extends BaseAdapter {
    private Activity activity;
    private String[] menuList = {"Wealth\nSummary",
            "Money Inflow/\nOutflow",
            "Assert/\nLiabilities",
            "SYNC Bank\n Account",
            "EMI Calculator",
            "ROI Calculator",
            "Retirement Calculator",
            "Risk Calculator",
            "Income Tax Calculator",
            "Terms & Conditions",
            "Privacy Policy",
            "Security Settings",
            "Reminder",
            "Help",
            "Logout"};
    private int[] menuIconList = {R.drawable.wealth,
            R.drawable.moneyinflowoutflow,
            R.drawable.assetsliabilities,
            R.drawable.bank,
            R.drawable.emi,
            R.drawable.roi,
            R.drawable.retirement,
            R.drawable.risk,
            R.drawable.incometax,
            R.drawable.terms,
            R.drawable.security,
            R.drawable.help,
            R.drawable.reminders,
            R.drawable.reminders,
            R.drawable.help,
            R.drawable.login};

    public NavigationDrawerBaseAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return menuList.length;
    }

    @Override
    public Object getItem(int i) {
        return menuList[i - 1].toString();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = activity.getLayoutInflater().inflate(R.layout.item_list_navigation_list_view, viewGroup, false);

        TextView textView = (TextView) view.findViewById(R.id.itemListNavigationListViewTextView);
        textView.setText(menuList[i]);
        ImageView imageView = (ImageView) view.findViewById(R.id.itemListNavigationListViewImageView);
        textView.setText(menuList[i]);
        imageView.setImageResource(menuIconList[i]);
        return view;
    }
}
