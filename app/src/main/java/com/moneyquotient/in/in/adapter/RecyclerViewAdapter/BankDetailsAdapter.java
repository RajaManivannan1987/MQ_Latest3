package com.moneyquotient.in.in.adapter.RecyclerViewAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.moneyquotient.in.in.Interface.VolleyResponseListerner;
import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.model.Bank;
import com.moneyquotient.in.in.sigleton.ActionOverSingleton;
import com.moneyquotient.in.in.utlity.Constant.ConstantValues;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;
import com.moneyquotient.in.in.utlity.webservice.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Raja M on 8/22/2017.
 */

public class BankDetailsAdapter extends RecyclerSwipeAdapter<BankDetailsAdapter.CustomHolder> {
    ArrayList<Bank> list;
    Context context;

    public BankDetailsAdapter(Context context, ArrayList<Bank> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BankDetailsAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CustomHolder(LayoutInflater.from(context).inflate(R.layout.bankdetails_item_list, parent, false));

    }

    @Override
    public void onBindViewHolder(BankDetailsAdapter.CustomHolder holder, final int position) {
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bankAccount_Right));
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        holder.accountNotext.setText(list.get(position).getAccountnumber());
        String c_bal = String.valueOf(list.get(position).getCurrentbalance());
        if (!c_bal.equalsIgnoreCase("") && c_bal != null) {
            holder.bankBalanceText.setText("Bal : " + "\u20B9 " + c_bal);
        } else {
            holder.bankBalanceText.setText("Bal : " + "\u20B9 " + "00.00");
        }
        String credit = list.get(position).getCredits();
        if (!credit.equalsIgnoreCase("") && credit != null) {
            holder.creditBalanceTextview.setText("Credit : " + "\u20B9 " + credit);
        } else {
            holder.creditBalanceTextview.setText("Credit : " + "\u20B9 " + "0.0");
        }
        String atm = list.get(position).getAtmwithdraw();
        if (!atm.equalsIgnoreCase("") && atm != null) {
            holder.atmAmountText.setText(atm);
        } else {
            holder.atmAmountText.setText("--");
        }
        String transfer = list.get(position).getTransfer();
        if (!transfer.equalsIgnoreCase("") && transfer != null) {
            holder.transferAmountText.setText(transfer);
        } else {
            holder.transferAmountText.setText("--");
        }
        String spend = list.get(position).getSpent();
        if (!spend.equalsIgnoreCase("") && spend != null) {
            holder.spendAmountText.setText(spend);
        } else {
            holder.spendAmountText.setText("--");
        }

        holder.bank_imageView.setBackgroundResource(R.mipmap.ic_launcher);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
                alertbox.setTitle("Confirm Delete...");
                alertbox.setMessage("Are you sure you want delete this account?");
                alertbox.setIcon(android.R.drawable.ic_delete);
                alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        WebServices.getInstance(context, "").deleteAccount(ConstantValues.DELETEACCOUNT, Session.getInstance(context, "").getUserid(), Session.getInstance(context, "").getToken(), list.get(position).getBankid(), new VolleyResponseListerner() {
                            @Override
                            public void onResponse(JSONObject response) throws JSONException {
                                dialog.dismiss();
                                list.remove(position);
                                ActionOverSingleton.getActionOverSingleton().ActionCompleted();
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onError(String message, String title) {

                            }
                        });

                    }
                });
                alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertbox.show();

            }
        });

        mItemManger.bindView(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.bank_Swipe;
    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        public Button deleteButton;
        public SwipeLayout swipeLayout;
        private ImageView bank_imageView;
        TextView accountNotext, bankBalanceText, creditBalanceTextview, atmAmountText, transferAmountText, spendAmountText;

        public CustomHolder(View itemView) {
            super(itemView);
            accountNotext = (TextView) itemView.findViewById(R.id.accountNotext);
            bankBalanceText = (TextView) itemView.findViewById(R.id.bankBalanceText);
            creditBalanceTextview = (TextView) itemView.findViewById(R.id.creditBalanceTextview);
            atmAmountText = (TextView) itemView.findViewById(R.id.atmAmountText);
            transferAmountText = (TextView) itemView.findViewById(R.id.transferAmountText);
            spendAmountText = (TextView) itemView.findViewById(R.id.spendAmountText);
            bank_imageView = (ImageView) itemView.findViewById(R.id.bank_imageView);
            deleteButton = (Button) itemView.findViewById(R.id.account_DeleteButton);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.bank_Swipe);

        }
    }
}
