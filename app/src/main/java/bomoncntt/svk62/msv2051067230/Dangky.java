package bomoncntt.svk62.msv2051067230;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bomoncntt.svk62.msv2051067230.helper.DatabaseHelper;

public class Dangky extends AppCompatActivity {

    private EditText txtRegisUsername;
    private EditText txtRegisPassword;
    private Button btnRegis;
    private ImageView toolbarBackButton;

    DatabaseHelper mydb=null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Đăng ký");
        mydb=new DatabaseHelper(this);
        setContentView(R.layout.layout_dangky);

        txtRegisUsername = findViewById(R.id.txt_registered_username);
        txtRegisPassword = findViewById(R.id.txt_registered_password);
        btnRegis = findViewById(R.id.btn_register);
        toolbarBackButton = findViewById(R.id.toolbar_back_button);

        //nút backhome
        toolbarBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Call onBackPressed() when back button is clicked
            }
        });

        // nút đăng ký
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u=txtRegisUsername.getText().toString();
                String p=txtRegisPassword.getText().toString();
                Log.v("username",u);
                Log.v("password",p);
                boolean r= mydb.insertLogin(u,p);
                if (r){
                    Toast.makeText(Dangky.this, "Đăng ký Thành Công", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Dangky.this, "Đăng ký Thất Bại", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // xử lý sự kiện nút backhome
        finish();
    }

}
