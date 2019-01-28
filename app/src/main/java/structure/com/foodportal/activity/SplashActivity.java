package structure.com.foodportal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import jp.shts.android.storiesprogressview.StoriesProgressView;
import structure.com.foodportal.R;
import structure.com.foodportal.helper.BasePreferenceHelper;

public class SplashActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {

    final int MIN_TIME_INTERVAL_FOR_SPLASH = 2500; // in millis
    StoriesProgressView storiesProgressView;
    ProgressBar mProgress;
    BasePreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferenceHelper = new BasePreferenceHelper(this);
        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);

        // storiesProgressView = findViewById(R.id.stories);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            public void run() {
                doWork();
                try {
                    Thread.sleep(500);
                    initActivity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //   finish();
            }
        }).start();


        //launchTimerAndTask();
        // initAnimation();
    }

    private void doWork() {
        for (int progress = 0; progress < 100; progress += 10) {
            try {
                Thread.sleep(500);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
                //  Timber.e(e.getMessage());
            }
        }
    }


    private void initAnimation() {
        storiesProgressView.setStoriesCount(1); // <- set stories
        storiesProgressView.setStoryDuration(3200L); // <- set a story duration
        storiesProgressView.setStoriesListener(this); // <- set listener
        storiesProgressView.startStories();
       /* YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .repeat(0)
                .onStart(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        txtAppname.setVisibility(View.VISIBLE);
                    }
                })
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        initActivity();
                    }
                })
                .playOn(findViewById(R.id.txtAppname));*/
    }

    private void initActivity() {
        if (preferenceHelper.getLoginStatus()) {
            showMainActivity();
        } else {
            showRegistration();
        }
    }


    private void showRegistration() {
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
        finish();
    }

    private void showMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        // Very important !
//        storiesProgressView.destroy();
        super.onDestroy();
    }

    @Override
    public void onNext() {

    }

    @Override
    public void onPrev() {

    }

    @Override
    public void onComplete() {
        initActivity();
    }
}