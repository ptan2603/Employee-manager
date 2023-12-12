package bomoncntt.svk62.msv2051067230;

import static android.Manifest.permission_group.CAMERA;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bomoncntt.svk62.msv2051067230.helper.DatabaseHelper;
import de.hdodenhof.circleimageview.CircleImageView;


public class AddInfor extends AppCompatActivity {

    private ImageView imgArrowDown;

    private EditText txtchucvu;
    EditText txtcmnd,txttennv,txtdiachi,txtsdt,txtemail;
    Button btnluu;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    DatabaseHelper mydb=null;
    Bitmap myBitmap;
    Uri picUri;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 107;
    CircleImageView croppedImageView;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_infor);

        mydb = new DatabaseHelper(this);
        // Ánh xạ các thành phần trên giao diện

        btnluu =  findViewById(R.id.btnluu);
        txtcmnd =  findViewById(R.id.txtcmnd);
        txttennv =  findViewById(R.id.txttennv);
        txtdiachi =  findViewById(R.id.txtdiachi);
        txtsdt =  findViewById(R.id.txtsdt);
        imgArrowDown = findViewById(R.id.img_arrow_down);
        txtchucvu = findViewById(R.id.txtchucvu);
        txtemail =  findViewById(R.id.txtemail);
        radioSexGroup =  findViewById(R.id.radiogroupsex);
        croppedImageView=(CircleImageView)findViewById(R.id.img_profile1);


        ///get du lieu gui tu MainActivity
        Intent intent = getIntent();
        String flag = intent.getStringExtra("Flag");
        if (flag.equals("EDIT")) {
            String cmnd =intent.getStringExtra("CMND");
            String tennv =intent.getStringExtra("TENNV");
            String gt =intent.getStringExtra("GT");
            String diachi =intent.getStringExtra("DIACHI");
            String sdt =intent.getStringExtra("SDT");
            String email =intent.getStringExtra("EMAIL");
            String chucvu =intent.getStringExtra("CHUCVU");
            String anh =intent.getStringExtra("ANH");

            croppedImageView.setImageBitmap(StringToBitMap(anh));

            txtcmnd.setText(cmnd);
            txttennv.setText(tennv);
            txtdiachi.setText(diachi);
            txtsdt.setText(sdt);
            txtemail.setText(email);
            txtchucvu.setText(chucvu);

            if (gt != null && gt.equals("Nam")) {
                radioSexGroup.check(R.id.radioButtonNam);
            } else {
                radioSexGroup.check(R.id.radioButtonNu);
            }
            // Xử lý ảnh
            if (anh != null && !anh.isEmpty()) {
                byte[] decodedString = Base64.decode(anh, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                croppedImageView.setImageBitmap(decodedByte);
            }
        }

        imgArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChucVuDialog();
            }
        });

        // Bắt sự kiện click button "Lưu"
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag.equals("ADD")){
                    //  setTitle("Thêm mới");
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();
                    radioSexButton = findViewById(selectedId);

                    String str = "";
                    if (myBitmap != null) {
                        str= BitMapToString(myBitmap);
                    } else {
                        str = "";
                    }
                    Log.v("bitmapttttt", "" + str);
                    Boolean Inserted = mydb.insertData(txtcmnd.getText().toString(), txttennv.getText().toString(), radioSexButton.getText().toString(),txtdiachi.getText().toString(),txtsdt.getText().toString(),txtemail.getText().toString(),txtchucvu.getText().toString(), str);
                    if (Inserted) {
                        Toast.makeText(AddInfor.this, "Data đã thêm", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddInfor.this, "Data thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    finish();// đóng cửa hiện tại
                    Intent in = new Intent(getApplicationContext(), User.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //xóa đi cửa sổ parent
                    startActivity(in);//mở lên cửa sổ MainActivity

                    }else if(flag.equals("EDIT")){
                        int selectedId = radioSexGroup.getCheckedRadioButtonId();
                        radioSexButton =  findViewById(selectedId);

                        String str = "";
                        if (myBitmap != null) {
                            str= BitMapToString(myBitmap);
                        } else {
                            str = "";
                        }
                        Log.v("bitmapttttt", "" + str);
                        Boolean updateData  = mydb.update(txtcmnd.getText().toString(), txttennv.getText().toString(), radioSexButton.getText().toString(),txtdiachi.getText().toString(),txtsdt.getText().toString(),txtemail.getText().toString(),txtchucvu.getText().toString(), str);
                        if (updateData ) {
                            Toast.makeText(AddInfor.this, "DATA được cập nhật", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(AddInfor.this, "Cập nhật DATA không thành công", Toast.LENGTH_SHORT).show();
                        }

                            Intent editIntent = new Intent(AddInfor.this, User.class);
                            editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Xóa cửa sổ parent
                            startActivity(editIntent); // Mở activity User
                            finish(); // Đóng activity hiện tại
                    }

            }

        });
        croppedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }
    private void showChucVuDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddInfor.this);
        builder.setTitle("Chọn chức vụ");

        // Tạo danh sách chức vụ
        String[] arraylistChucvu = new String[]{"Bán hàng", "Kỹ thuật", "Quản kho","Bảo hành"," marketing","Kế toán"};

        // Thiết lập adapter cho dialog
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraylistChucvu);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lấy chức vụ được chọn
                String selectedChucVu = arraylistChucvu[which];

                // Hiển thị chức vụ trên thanh chucvu
                txtchucvu.setText(selectedChucVu);
            }
        });

        // Hiển thị dialog
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the action bar menu.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_info, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.menu_camera){
            selectImage();
        }
        return super.onOptionsItemSelected(item);
    }
    private void selectImage(){
        final CharSequence [] options = {"Chụp Ảnh","Chọn ảnh từ thư viện","Thoát"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddInfor.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Chụp Ảnh"))
                {
                    permissions.add(CAMERA);
                    permissionsToRequest = findUnAskedPermissions(permissions);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (permissionsToRequest.size() > 0)
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, ALL_PERMISSIONS_RESULT);
                    }

                    startActivityForResult(getPickImageChooserIntent(), 200);//dialog chup anh

                }
                else if (options[item].equals("Chọn ảnh từ thư viện"))
                {
                    permissions.add(CAMERA);
                    permissionsToRequest = findUnAskedPermissions(permissions);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (permissionsToRequest.size() > 0)
                            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                    }
                    startActivityForResult(getPickImageChooserIntentFile(), 200);//dialog chon anh tu file
                }
                else if (options[item].equals("Thoát")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }
    public Intent getPickImageChooserIntent() {
        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        // collect all camera intents
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
        }
        allIntents.add(0,captureIntent);
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);

        }
        Intent chooserIntent = Intent.createChooser(captureIntent, "Select source");
        //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, captureIntent);

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        Log.v("allIntents",""+allIntents.size());
        return chooserIntent;
    }
    public Intent getPickImageChooserIntentFile() {
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
        }
        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        Log.v("allIntents",""+allIntents.size());
        return chooserIntent;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {
            Log.v("picUri",""+data);
            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);
                // Log.v("picUri",""+picUri.getPath());
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);//hình ảnh trong thư mục mà chụp
                    Log.v("myBitmap",""+myBitmap);
                    //   myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 20); //nén ảnh lại

                    //Đoạn lệnh hiển thị ảnh lên circleimageview
                    croppedImageView.setImageBitmap(myBitmap);
                    //  imageView.setImageBitmap(myBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {

                Log.v("picUri","null");
                bitmap = (Bitmap) data.getExtras().get("data");

                myBitmap = bitmap;

                if (croppedImageView != null) {
                    croppedImageView.setImageBitmap(myBitmap);
                }

                // imageView.setImageBitmap(myBitmap);

            }

        }

    }
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public  static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}

