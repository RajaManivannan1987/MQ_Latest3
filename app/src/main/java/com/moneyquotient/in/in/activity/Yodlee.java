package com.moneyquotient.in.in.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.moneyquotient.in.in.Interface.VolleyResponseListerner;
import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonMethod;
import com.moneyquotient.in.in.utlity.Constant.ConstantValues;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;
import com.moneyquotient.in.in.utlity.webservice.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karth on 8/28/2017.
 */

public class Yodlee extends AppCompatActivity {
    @BindView(R.id.yodleee_webview)
    WebView webView;
    @BindView(R.id.layoutProgressBar)
    RelativeLayout layoutProgressBar;

    private String TAG = Yodlee.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yodlee);
        ButterKnife.bind(this);
        layoutProgressBar.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        getAccesstoken();

    }

    private void getAccesstoken() {
        WebServices.getInstance(this, TAG).getAccessTOken(ConstantValues.GET_ACCESS_TOKEN, Session.getInstance(this, TAG).getUserid(),
                Session.getInstance(this, TAG).getToken(), new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        layoutProgressBar.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
//                        if (CommonMethod.checkTokenStauts(response, Yodlee.this)) {
                            if (response.getString("status").equalsIgnoreCase("1")) {
                                launchFastlink(response.optJSONObject("data").optString("userSession"), response.optJSONObject("data").optString("value"));
                            } else {
                                CommonMethod.showSnackbar(layoutProgressBar, response.optString("token_message"));
                            }
//                        }
                    }

                    @Override
                    public void onError(String message, String title) {
                        CommonMethod.showSnackbar(layoutProgressBar, message);
                    }
                });
    }

    private void launchFastlink(String userSession, String value) {

        layoutProgressBar.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);

        /*userSessionHashMap = shared_pref.getuserSessionToken();
        USERSESSION = userSessionHashMap.get(Shared_Pref.KEY_USERSESSION);
        accessTokenHashMap = shared_pref.getAccessToken();
        ACCESSTOKEN = accessTokenHashMap.get(Shared_Pref.KEY_ACCESSTOKEN);*/
        String data = ConstantValues.formHtmlContent
                .replace("${NODE_URL}", ConstantValues.NODE_URL)
                .replace("${RSESSION}", userSession) //"10192016_2:3c5c7ed774f166eb254578c504c15747701e1630045d8f7d2213102f448e5deb37098f1bc7980506ab9e361a13938c912b725b86861946543523be5ec94a52c4")
                .replace("${TOKEN}", value)     // "1acfd4de492d88296d4fa235b370ef287a2db244cf6cefc5066b584df55cdf68")
                .replace("${EXTRA_PARAMS}", "callback=http://www.moneyquotient.in/")    //"callback=http://www.google.co.in"
                .replace("${FINAPP_ID}", "10003600");
        webView.getSettings().setJavaScriptEnabled(true);
        layoutProgressBar.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadData(data, "text/html", "UTF-8");
        //dismissLoadingDialog();


        //webview.loadUrl("file:///android_asset/yodleetest.html");

    }

    public class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            if (url.contains("status") && url.length() > 36) {
                url = url.substring(url.indexOf("=") + 1);
                url = url.substring(0, url.indexOf("%"));
//                getAccount();
//                startActivity(new Intent(Yodlee.this, Income_New.class));
            } else {
                CommonMethod.showSnackbar(layoutProgressBar, "Unable to add account");
//                startActivity(new Intent(Yodlee.this, Income_New.class));

            }
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {

        }

        public void onLoadResource(WebView view, String url) {

        }
    }


}
