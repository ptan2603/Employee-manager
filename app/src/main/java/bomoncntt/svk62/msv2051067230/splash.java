package bomoncntt.svk62.msv2051067230;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    // thời gian chờ 3 giây
    int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT); // Đặt thời gian hiển thị màn hình chào (3 giây ở đây)
    }
}