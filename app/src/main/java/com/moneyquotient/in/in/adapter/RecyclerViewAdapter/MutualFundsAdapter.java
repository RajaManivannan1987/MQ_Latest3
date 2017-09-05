package com.moneyquotient.in.in.adapter.RecyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.model.MutualFunds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raja M on 8/29/2017.
 */

public class MutualFundsAdapter extends ArrayAdapter {
    ArrayList<MutualFunds> list;
    ArrayList<MutualFunds> list_all;
    ArrayList<MutualFunds> list_sug;
    Context mContext;
    int layoutResourceId;
    String type;

    public MutualFundsAdapter(Context context, int layoutId, ArrayList<MutualFunds> list, String type) {
        super(context, layoutId, list);
        this.list = list;
        this.list_all = list;
        this.list_sug = new ArrayList<>();
        this.mContext = context;
        this.layoutResourceId = layoutId;
        this.type = type;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }
        TextView customerNameLabel = (TextView) convertView.findViewById(R.id.tvName);
        if (customerNameLabel != null) {
            if (type.equalsIgnoreCase("home"))
                customerNameLabel.setText(list.get(position).getFundname());
            else if (type.equalsIgnoreCase("categories"))
                customerNameLabel.setText(list.get(position).getCategoryname());
            else if (type.equalsIgnoreCase("subcategories"))
                customerNameLabel.setText(list.get(position).getSubcategoryname());
        }
        return convertView;

    }

   /* @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((MutualFunds) resultValue).getFundname();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    list_sug.clear();
                    for (MutualFunds department : list_all) {
                        if (department.getFundname().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            list_sug.add(department);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = list_sug;
                    filterResults.count = list_sug.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    List<?> result = (List<?>) results.values;
                    for (Object object : result) {
                        if (object instanceof MutualFunds) {
                            list.add((MutualFunds) object);
                        }
                    }
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    list.addAll(list_all);
                }
                notifyDataSetChanged();
            }
        };
    }*/
}
