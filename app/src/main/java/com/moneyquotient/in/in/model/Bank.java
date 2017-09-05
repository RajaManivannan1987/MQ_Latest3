package com.moneyquotient.in.in.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raja M on 9/1/2017.
 */

public class Bank implements Serializable {
    @SerializedName("atmwithdraw")
    public String atmwithdraw;
    @SerializedName("bankid")
    public String bankid;
    @SerializedName("credits")
    public String credits;
    @SerializedName("transfer")
    public String transfer;
    @SerializedName("spent")
    public String spent;
    @SerializedName("atmdetails")
    public List<BankDetail> atmdetails;
    @SerializedName("creditdetails")
    public List<BankDetail> creditdetails;
    @SerializedName("spentdetails")
    public List<BankDetail> spentdetails;
    @SerializedName("bank")
    public String bank;
    @SerializedName("currentbalance")
    public double currentbalance;
    @SerializedName("accountnumber")
    public String accountnumber;

    public String getAtmwithdraw() {
        return atmwithdraw;
    }

    public String getCredits() {
        return credits;
    }
    public String getBankid() {
        return bankid;
    }

    public String getTransfer() {
        return transfer;
    }

    public String getSpent() {
        return spent;
    }

    public List<BankDetail> getAtmdetails() {
        return atmdetails;
    }

    public List<BankDetail> getCreditdetails() {
        return creditdetails;
    }

    public List<BankDetail> getSpentdetails() {
        return spentdetails;
    }

    public String getBank() {
        return bank;
    }

    public double getCurrentbalance() {
        return currentbalance;
    }

    public String getAccountnumber() {
        return accountnumber;
    }
}
