package com.moneyquotient.in.in.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.activity.DashBoardActivity;
import com.moneyquotient.in.in.activity.EMICalculator;
import com.moneyquotient.in.in.activity.IncomeTaxCalculator;
import com.moneyquotient.in.in.activity.ROICalculator;
import com.moneyquotient.in.in.activity.RetirementCalculator;
import com.moneyquotient.in.in.activity.RiskCalculator;
import com.moneyquotient.in.in.activity.SecuritySettings;
import com.moneyquotient.in.in.activity.Terms_Condition;
import com.moneyquotient.in.in.adapter.NavigationDrawerBaseAdapter;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;


/**
 * Created by karth on 7/21/2017.
 */

public class CommonActivity extends AppCompatActivity {
    private static String TAG = CommonActivity.class.getSimpleName();


    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private ImageView back;
    private TextView title;
    private RelativeLayout commonProgressBar;

    protected DrawerLayout drawerLayout;
    public ListView listView;
    private ActionBarDrawerToggle toggle;
    public NavigationDrawerBaseAdapter navigationDrawerBaseAdapter;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        commonProgressBar = (RelativeLayout) findViewById(R.id.commonProgressBar);
        toolbar = (Toolbar) findViewById(R.id.backcommonActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = (DrawerLayout) findViewById(R.id.commonActivityDrawerLayout);
        back = (ImageView) findViewById(R.id.backCommonActivityBackImageView);
        listView = (ListView) findViewById(R.id.commonActivityFrameLayoutLeftDrawerListView);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
                invalidateOptionsMenu();
                navigationDrawerBaseAdapter.notifyDataSetInvalidated();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                invalidateOptionsMenu();
                navigationDrawerBaseAdapter.notifyDataSetInvalidated();
            }
        };

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Session.getInstance(getApplicationContext(), TAG).getIsLogin()) {
            back.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            setMenu();
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.syncState();
            back.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    private void setMenu() {
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        listView.setAdapter(navigationDrawerBaseAdapter = new NavigationDrawerBaseAdapter(this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    switch (navigationDrawerBaseAdapter.getItem(i + 1).toString()) {
                        case "EMI Calculator":
                            CommonMethod.changeActivity(CommonActivity.this, EMICalculator.class);
                            break;
                        case "ROI Calculator":
                            CommonMethod.changeActivity(CommonActivity.this, ROICalculator.class);
                            break;
                        case "Retirement Calculator":
                            CommonMethod.changeActivity(CommonActivity.this, RetirementCalculator.class);
                            break;
                        case "Risk Calculator":
                            CommonMethod.changeActivity(CommonActivity.this, RiskCalculator.class);
                            break;
                        case "Income Tax Calculator":
                            CommonMethod.changeActivity(CommonActivity.this, IncomeTaxCalculator.class);
                            break;
                        case "Terms & Conditions":
                            CommonMethod.changeActivityWithText(CommonActivity.this, Terms_Condition.class,"Terms & Conditions");
                            break;
                        case "Privacy Policy":
                            CommonMethod.changeActivityWithText(CommonActivity.this,Terms_Condition.class,"Privacy Policy");
                            break;
                        case "Security Settings":
                            CommonMethod.changeActivity(CommonActivity.this, SecuritySettings.class);
                            break;
                        case "Logout":
                            Session.getInstance(CommonActivity.this, TAG).logout();
                            CommonMethod.changeActivity(CommonActivity.this, DashBoardActivity.class);
                            finish();
                            break;
                    }
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                }

            }
        });


    }


}
