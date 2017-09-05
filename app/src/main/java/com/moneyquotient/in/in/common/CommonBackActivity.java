package com.moneyquotient.in.in.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moneyquotient.in.in.R;


/**
 * Created by karth on 7/21/2017.
 */

public class CommonBackActivity extends AppCompatActivity {
    private static String TAG = CommonBackActivity.class.getSimpleName();


    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private ImageView back;
    private TextView title;
    private RelativeLayout commonProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_back_activity);
        commonProgressBar = (RelativeLayout) findViewById(R.id.commonProgressBar);
        toolbar = (Toolbar) findViewById(R.id.backcommonActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        back = (ImageView) findViewById(R.id.backCommonActivityBackImageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    public void setView(int viewLayout, String title) {
        frameLayout = (FrameLayout) findViewById(R.id.bcakcommonActivityFrameLayout);
        this.title = (TextView) findViewById(R.id.backCommonActivityTitle);
        this.title.setText(title);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(viewLayout, null, false);
        frameLayout.addView(activityView);
    }


    public void setCommonProgressBar() {
        commonProgressBar.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
    }

    public void hideCommonProgressBar() {
        commonProgressBar.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }


}