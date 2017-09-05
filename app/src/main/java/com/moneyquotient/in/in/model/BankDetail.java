package com.moneyquotient.in.in.model;

import java.io.Serializable;

/**
 * Created by Raja M on 9/1/2017.
 */

public class BankDetail implements Serializable {
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String amount, date, desc;
}
