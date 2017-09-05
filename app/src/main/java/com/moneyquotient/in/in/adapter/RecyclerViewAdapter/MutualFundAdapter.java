package com.moneyquotient.in.in.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.model.Mutual;

import java.util.ArrayList;

/**
 * Created by Raja M on 8/22/2017.
 */

public class MutualFundAdapter extends RecyclerView.Adapter<MutualFundAdapter.CustomHolder> {
    ArrayList<Mutual> list;
    Context context;

    public MutualFundAdapter(Context context, ArrayList<Mutual> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MutualFundAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomHolder(LayoutInflater.from(context).inflate(R.layout.mutual_funds_scheme_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MutualFundAdapter.CustomHolder holder, final int position) {
        holder.schemecode.setText(list.get(position).getSchemecode());
        holder.schemename.setText(list.get(position).getSchemename());
        holder.price.setText(list.get(position).getPrice());
        holder.bought.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=s.toString().trim();
                if (!val.equalsIgnoreCase("")) {
                    double value = Double.valueOf(val) * Double.valueOf(list.get(position).getPrice());
                    holder.total.setText(String.valueOf(value));
                } else {
                    holder.total.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {

        TextView schemecode, schemename, price;
        EditText bought, total;

        public CustomHolder(View itemView) {
            super(itemView);
            schemecode = (TextView) itemView.findViewById(R.id.schemeIdText);
            schemename = (TextView) itemView.findViewById(R.id.schemeNameText);
            price = (TextView) itemView.findViewById(R.id.schemePriceText);
            bought = (EditText) itemView.findViewById(R.id.schemeBoughtEdittext);
            total = (EditText) itemView.findViewById(R.id.schemeTotalEdittext);

        }
    }
}
