package bomoncntt.svk62.msv2051067230;

import static bomoncntt.svk62.msv2051067230.Login.MyPREFERENCES;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private ImageView ImageView1,ImageView2,ImageView3,ImageView4;
    SharedPreferences pref;
    SharedPreferences.Editor editor;//chỉnh sửa dữ liệu

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ImageView1 = findViewById(R.id.imageView1);
        ImageView2 = findViewById(R.id.imageView2);
        ImageView3 = findViewById(R.id.imageView3);
        ImageView4 = findViewById(R.id.imageView4);

        ImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the User activity
                Intent in = new Intent(MainActivity.this, User.class);
                startActivity(in);
                finish();
            }
        });
        ImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the User activity
                Intent in = new Intent(MainActivity.this, Chamcong.class);
                startActivity(in);
                finish();
            }
        });
        ImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the User activity
                Intent in = new Intent(MainActivity.this, Tamung.class);
                startActivity(in);
                finish();
            }
        });

        ImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clear user and password information
                clearUserInfo();
                // Start the LoginActivity
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }

            private void clearUserInfo() {
                editor=pref.edit(); //chỉnh sửa file  MYPREFS.xml
                editor.remove("USERNAME");
                editor.remove("PASSWORD");
                editor.commit();
                Intent out=new Intent(getApplicationContext(), Login.class);
                startActivity(out);
                finish();
            }

        });

    }

}