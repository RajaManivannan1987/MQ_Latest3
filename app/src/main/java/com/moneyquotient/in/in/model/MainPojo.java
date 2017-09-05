package com.moneyquotient.in.in.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raja M on 9/1/2017.
 */

public class MainPojo implements Serializable{

    @SerializedName("asset")
    public String asset;
    @SerializedName("assetlist")
    public Assetlist assetlist;
    @SerializedName("liability")
    public String liability;
    @SerializedName("liabilitylist")
    public Liabilitylist liabilitylist;
    @SerializedName("totalemi")
    public String totalemi;
    @SerializedName("creditcard")
    public String creditcard;
    @SerializedName("withdrawamount")
    public String withdrawamount;
    @SerializedName("networth")
    public String networth;
    @SerializedName("bankbalance")
    public String bankbalance;
    @SerializedName("upcoming")
    public String upcoming;
    @SerializedName("inflow")
    public String inflow;
    @SerializedName("outflow")
    public String outflow;
    @SerializedName("score")
    public String score;
    @SerializedName("cashmeter")
    public String cashmeter;
    @SerializedName("loanmessage")
    public String loanmessage;
    @SerializedName("bankdetails")
    public Bankdetails bankdetails;


    public String getAsset() {
        return asset;
    }

    public Assetlist getAssetlist() {
        return assetlist;
    }

    public String getLiability() {
        return liability;
    }

    public Liabilitylist getLiabilitylist() {
        return liabilitylist;
    }

    public String getTotalemi() {
        return totalemi;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public String getWithdrawamount() {
        return withdrawamount;
    }

    public String getNetworth() {
        return networth;
    }

    public String getBankbalance() {
        return bankbalance;
    }

    public String getUpcoming() {
        return upcoming;
    }

    public String getInflow() {
        return inflow;
    }

    public String getOutflow() {
        return outflow;
    }

    public String getScore() {
        return score;
    }

    public String getCashmeter() {
        return cashmeter;
    }

    public String getLoanmessage() {
        return loanmessage;
    }

    public Bankdetails getBankdetails() {
        return bankdetails;
    }



    public static class Assetlist implements Serializable {
        @SerializedName("Bank Balance")
        public String BankBalance;
        @SerializedName("Real Estate")
        public String RealEstate;
        @SerializedName("Fixed Deposit")
        public String FixedDeposit;
        @SerializedName("Equity")
        public String Equity;
        @SerializedName("Miscellaneous")
        public String Miscellaneous;
        @SerializedName("Mutual Fund")
        public String MutualFund;
        @SerializedName("Bond")
        public String Bond;
        @SerializedName("Cash")
        public String Cash;
        @SerializedName("Epf")
        public String Epf;
        @SerializedName("Gold/Jewels")
        public String Gold_Jewels;

        public String getBankBalance() {
            return BankBalance;
        }

        public String getRealEstate() {
            return RealEstate;
        }

        public String getFixedDeposit() {
            return FixedDeposit;
        }

        public String getEquity() {
            return Equity;
        }

        public String getMiscellaneous() {
            return Miscellaneous;
        }

        public String getMutualFund() {
            return MutualFund;
        }

        public String getBond() {
            return Bond;
        }

        public String getCash() {
            return Cash;
        }

        public String getEpf() {
            return Epf;
        }

        public String getGold_Jewels() {
            return Gold_Jewels;
        }
    }

    public static class Liabilitylist implements Serializable{
        @SerializedName("Home Loan")
        public String HomeLoan;
        @SerializedName("Education Loan")
        public String EducationLoan;
        @SerializedName("Personal Loan")
        public String PersonalLoan;
        @SerializedName("Car Loan")
        public String CarLoan;
        @SerializedName("Other Loan")
        public String OtherLoan;

        public String getHomeLoan() {
            return HomeLoan;
        }

        public String getEducationLoan() {
            return EducationLoan;
        }

        public String getPersonalLoan() {
            return PersonalLoan;
        }

        public String getCarLoan() {
            return CarLoan;
        }

        public String getOtherLoan() {
            return OtherLoan;
        }
    }


    public static class Data implements Serializable  {
        @SerializedName("Bank")
        public List<Bank> Bank;
        @SerializedName("Creditcard")
        public List<CreditCard> Creditcard;

        public List<com.moneyquotient.in.in.model.Bank> getBank() {
            return Bank;
        }

        public List<CreditCard> getCreditcard() {
            return Creditcard;
        }
    }

    public static class Bankdetails implements Serializable{
        @SerializedName("status")
        public int status;
        @SerializedName("message")
        public String message;
        @SerializedName("data")
        public Data data;

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public Data getData() {
            return data;
        }
    }
}