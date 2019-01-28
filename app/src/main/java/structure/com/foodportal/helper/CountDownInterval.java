package structure.com.foodportal.helper;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public abstract class CountDownInterval extends CountDownTimer {

    TextView txtTime;

    public CountDownInterval(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.txtTime = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (txtTime != null)
            txtTime.setText("" + String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
    }

}
