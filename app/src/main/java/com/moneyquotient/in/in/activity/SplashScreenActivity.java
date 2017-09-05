package com.moneyquotient.in.in.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonMethod;
import com.moneyquotient.in.in.utlity.sharedPreferance.PassocodePreferance;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karth on 8/10/2017.
 */

public class SplashScreenActivity extends AppCompatActivity {
    @BindView(R.id.splash_video)
    VideoView splashVideo;
    @BindView(R.id.splash_image)
    ImageView splashImage;

    private String TAG = SplashScreenActivity.class.getSimpleName();
    private Handler mHandler = new Handler();
    Handler mCheckLoginHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ButterKnife.bind(this);
        if (PassocodePreferance.getInstance(getApplicationContext(), TAG).checkAsPasscode()) {
            CommonMethod.changeActivityWithText(SplashScreenActivity.this, FingerprintActivity.class, "");
            finish();
        } else {

            splashVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mq_logo_best));
            splashVideo.start();
            splashVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    CommonMethod.showLog(TAG, "Video Completed!!");
                    splashImage.setVisibility(View.GONE);
                    splashImage.setVisibility(View.VISIBLE);
                    setImage();
                }
            });
        }
    }

    private void setImage() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splashImage.setImageResource(R.drawable.launch_screen_two_new);
            }
        }, 2000);
        mCheckLoginHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Session.getInstance(getApplicationContext(), TAG).getIsLogin()) {
                    CommonMethod.changeActivity(SplashScreenActivity.this, WealthSummaryActivity.class);
                    finish();
                } else {
                    CommonMethod.changeActivity(SplashScreenActivity.this, DashBoardActivity.class);
                    finish();
                }
            }
        }, 4000);

    }
}
