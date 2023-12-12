package bomoncntt.svk62.msv2051067230;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import bomoncntt.svk62.msv2051067230.helper.DatabaseHelper;

public class AddCC extends AppCompatActivity {

    EditText txtcmndfk,txttennvcham,txtngchamcong,txtsngaycong;
    Button btnluu;

    List<String> nhanvienList;
    DatabaseHelper mydb=null;


    private final static int ALL_PERMISSIONS_RESULT = 107;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cc);
        mydb = new DatabaseHelper(this);
        btnluu =  findViewById(R.id.btnluu);
        txtcmndfk =  findViewById(R.id.txtcmnd);
        txttennvcham =  findViewById(R.id.txttennv);
        txtngchamcong =  findViewById(R.id.txtngaycham);
        txtsngaycong =  findViewById(R.id.txtsongay);

        // Truy van cs du lieu
        Cursor nhanvienCursor = mydb.Showdata();
        nhanvienList = new ArrayList<>();

        ///get du lieu gui tu MainActivity
        Intent intent = getIntent();
        String flag = intent.getStringExtra("Flag");
        if (flag != null && flag.equals("EDIT")) {
            String cmndfk =intent.getStringExtra("CMNDFK");
            String tennvcham =intent.getStringExtra("TENNVCHAM");
            String ngchamcong =intent.getStringExtra("NGCHAMCONG");
            String sngaycong =intent.getStringExtra("SNGAYCONG");


            txtcmndfk.setText(cmndfk);
            txttennvcham.setText(tennvcham);
            txtngchamcong.setText(ngchamcong);
            txtsngaycong.setText(sngaycong);


        }

        // Bắt sự kiện click button "Lưu"
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag.equals("ADD")){
                    //  setTitle("Thêm mới");
                    Boolean Insertedchamcong = mydb.insertDataChamcong(txtcmndfk.getText().toString(), txttennvcham.getText().toString(), txtngchamcong.getText().toString(),txtsngaycong.getText().toString());
                    if (Insertedchamcong) {
                        Toast.makeText(AddCC.this, "Data đã thêm", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddCC.this, "Data thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    finish();// đóng cửa hiện tại
                    Intent in = new Intent(getApplicationContext(), Chamcong.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //xóa đi cửa sổ parent
                    startActivity(in);//mở lên cửa sổ MainActivity

                }else if(flag.equals("EDIT")){

                    Boolean updateDatachamcong  = mydb.updateChamcong(txtcmndfk.getText().toString(), txttennvcham.getText().toString(), txtngchamcong.getText().toString(),txtsngaycong.getText().toString());
                    if (updateDatachamcong ) {
                        Toast.makeText(AddCC.this, "DATA được cập nhật", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(AddCC.this, "Cập nhật DATA không thành công", Toast.LENGTH_SHORT).show();
                    }

                    Intent editIntent = new Intent(AddCC.this, User.class);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Xóa cửa sổ parent
                    startActivity(editIntent); // Mở activity User
                    finish(); // Đóng activity hiện tại
                }

            }

        });


    }
}