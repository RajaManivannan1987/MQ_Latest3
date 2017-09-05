package com.moneyquotient.in.in.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raja M on 9/1/2017.
 */

public class CreditCard implements Serializable{
    @SerializedName("bank")
    public String bank;
    @SerializedName("availablelimit")
    public double availablelimit;
    @SerializedName("accountnumber")
    public String accountnumber;
    @SerializedName("paymentmade")
    public BankDetail paymentmade;
    @SerializedName("bankid")
    public int bankid;

    public String getBank() {
        return bank;
    }

    public double getAvailablelimit() {
        return availablelimit;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public BankDetail getPaymentmade() {
        return paymentmade;
    }

    public int getBankid() {
        return bankid;
    }
}
