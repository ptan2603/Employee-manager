package bomoncntt.svk62.msv2051067230;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bomoncntt.svk62.msv2051067230.helper.DatabaseHelper;

public class AddTamung extends AppCompatActivity {
    EditText txtcmndfk,txttennvung,txtngayung,txtsotienung;
    Button btnluu;
    DatabaseHelper mydb=null;

    private final static int ALL_PERMISSIONS_RESULT = 107;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tu);
        mydb = new DatabaseHelper(this);
        btnluu =  findViewById(R.id.btnluu);
        txtcmndfk =  findViewById(R.id.txtcmnd);
        txttennvung =  findViewById(R.id.txttennv);
        txtngayung =  findViewById(R.id.txtngaycham);
        txtsotienung =  findViewById(R.id.txtsongay);


        ///get du lieu gui tu MainActivity
        Intent intent = getIntent();
        String flag = intent.getStringExtra("Flag");
        if (flag.equals("EDIT")) {
//            String cmnd = intent.getStringExtra("CMNDFK");
//            String tennv = intent.getStringExtra("TENNVCHAM");
            String cmndfk =intent.getStringExtra("CMNDFK");
            String tennvung =intent.getStringExtra("TENNVUNG");
            String ngayung =intent.getStringExtra("NGAYUNG");
            String sotienung =intent.getStringExtra("SOTIENUNG");


            txtcmndfk.setText(cmndfk);
            txttennvung.setText(tennvung);
            txtngayung.setText(ngayung);
            txtsotienung.setText(sotienung);

        }

        // Bắt sự kiện click button "Lưu"
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag.equals("ADD")){
                    //  setTitle("Thêm mới");
                    Boolean Insertedtamung = mydb.insertDataTamung(txtcmndfk.getText().toString(), txttennvung.getText().toString(), txtngayung.getText().toString(),txtsotienung.getText().toString());
                    if (Insertedtamung) {
                        Toast.makeText(AddTamung.this, "Data đã thêm", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddTamung.this, "Data thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    finish();// đóng cửa hiện tại
                    Intent in = new Intent(getApplicationContext(), Tamung.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //xóa đi cửa sổ parent
                    startActivity(in);//mở lên cửa sổ MainActivity

                }else if(flag.equals("EDIT")){

                    Boolean updateDatatamung  = mydb.updateTamung(txtcmndfk.getText().toString(), txttennvung.getText().toString(), txtngayung.getText().toString(),txtsotienung.getText().toString());
                    if (updateDatatamung ) {
                        Toast.makeText(AddTamung.this, "DATA được cập nhật", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(AddTamung.this, "Cập nhật DATA không thành công", Toast.LENGTH_SHORT).show();
                    }

                    Intent editIntent = new Intent(AddTamung.this, User.class);
                    editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Xóa cửa sổ parent
                    startActivity(editIntent); // Mở activity User
                    finish(); // Đóng activity hiện tại
                }

            }

        });
    }
}
