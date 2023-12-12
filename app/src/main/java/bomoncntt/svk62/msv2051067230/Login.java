package bomoncntt.svk62.msv2051067230;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bomoncntt.svk62.msv2051067230.helper.DatabaseHelper;

public class Login extends AppCompatActivity {

    private EditText txtusername, txtpassword;
    private CheckBox checkbox_password;
    private Button btn_login,btn_register;

    SharedPreferences pref;// khai báo

    SharedPreferences.Editor editor;//chỉnh sửa dữ liệu
    public static final String MyPREFERENCES = "MyPrefs";

    DatabaseHelper mydb=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mydb=new DatabaseHelper(this);


        // Khởi tạo txtusername,txtpassword,checkbox, btn_login,btn_register
        txtusername =  findViewById(R.id.txtusername);
        txtpassword =  findViewById(R.id.txtpassword);
        checkbox_password = findViewById(R.id.checkBoxRemember);
        btn_login =  findViewById(R.id.btn_login);
        btn_register =  findViewById(R.id.btn_register);
        // khởi tạo SharedPreferences
        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Đọc thông tin từ share ra
        String username=pref.getString("USERNAME","");
        String password=pref.getString("PASSWORD","");
        Log.v("username",username);
        if (!username.equals("")){
            txtusername.setText(username);
            txtpassword.setText(password);

            Intent in=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(in);
            finish();

        }

        // nhấp chuột
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy username và password đã nhập
                String u = txtusername.getText().toString();
                String p = txtpassword.getText().toString();
                Cursor cursor=mydb.queryLogin(u,p);

                // So sánh username và password đã nhập với thông tin tài khoản đã đăng ký
                if (cursor.moveToNext()){
                    if(checkbox_password.isChecked()){
                        //lưu thông tin xuống sharepreferences
                        editor=pref.edit(); //chỉnh sửa file  MYPREFS.xml
                        editor.putString("USERNAME",u); //ghi thông tin vào fields USERNAME='admin'
                        editor.putString("PASSWORD",p);
                        editor.apply();
                    }else
                    {
                        //xóa preferences
                        editor=pref.edit();
                        editor.clear();
                        editor.apply();
                    }
                    Intent in=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(in);
                    finish();
                }else{
                    Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

                }
                finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(), Dangky.class);
                startActivity(in);
            }
        });
    }


}
