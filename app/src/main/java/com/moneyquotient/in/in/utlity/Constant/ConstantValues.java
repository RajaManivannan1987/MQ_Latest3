package com.moneyquotient.in.in.utlity.Constant;

import android.graphics.Color;

/**
 * Created by karth on 7/20/2017.
 */

public class ConstantValues {

    //by kiran
//    private static String APP_URL = " http://www.moneyquotient.in/demo/server/";

    private static String APP_URL = " http://www.moneyquotient.in/mqlive/server/";
    public static String LOGIN = APP_URL + "userLogin";
    public static String FB_LOGIN = APP_URL + "facebookLogin";
    public static String GMAIL_LOGIN = APP_URL + "gmailLogin";
    public static String REGISTER = APP_URL + "registerNew";
    public static String INCOME_TAX = APP_URL + "incomesavecalculator";
    public static String GET_ACCESS_TOKEN = APP_URL + "getaccesstoken";
    public static String NODE_URL = "https://internationalyodleesdkmasternode.yodleeinteractive.com/authenticate/moneyquotient/?channelAppName=pfmlaw";


    //by raja
    public static String WEALTHMETER = APP_URL + "getsoutcomes";
    public static String DASHBOARD = APP_URL + "dashboard";
    public static String GETACCOUNTS = APP_URL + "getyodleeaccounts";
    public static String MUTUALFUNDSMAIN = APP_URL + "get_mutual_fund_name";
    public static String DELETEACCOUNT = APP_URL + "deleteyodleeaccounts";
    public static String MUTUALFUNDCATEGORY = APP_URL + "get_mutal_categories";
    public static String MUTUALFUNDSUBCATEGORY = APP_URL + "get_mutual_subcategories";
    public static String SUBMIYMUTUALFUND = APP_URL + "submit_mutual_fund";


    //by kiran
    public static final int[] MY_COLORS = {
            Color.rgb(135, 168, 33), Color.rgb(237, 140, 43)
    };

    public static String formHtmlContent = "<div class='center processText'></div>"
            + "<div>"
            + "<form action='${NODE_URL}' method='post' id='rsessionPost'>"
            + "	<input type='hidden' name='rsession' placeholder='rsession' value='${RSESSION}' id='rsession'/><br/>"
            + " <input type='hidden' name='app' placeholder='FinappId' value='${FINAPP_ID}' id='finappId'/><br/>"
            + "	<input type='hidden' name='redirectReq' placeholder='true/false' value='true'/><br/>"
            + "	<input type='hidden' name='token' placeholder='token' value='${TOKEN}' id='token'/><br/>"
            + "	<input type='hidden' name='extraParams' placeholer='Extra Params' value='${EXTRA_PARAMS}' id='extraParams'/><br/>"
            + "</form></div><script>document.getElementById('rsessionPost').submit();</script>";

}
