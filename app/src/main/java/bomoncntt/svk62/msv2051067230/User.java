    package bomoncntt.svk62.msv2051067230;

    import static bomoncntt.svk62.msv2051067230.Login.MyPREFERENCES;

    import android.annotation.SuppressLint;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.database.Cursor;
    import android.graphics.Typeface;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.CheckBox;
    import android.widget.EditText;
    import android.widget.ListView;
    import android.widget.PopupMenu;
    import android.widget.SearchView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.android.material.floatingactionbutton.FloatingActionButton;

    import java.util.ArrayList;
    import java.util.List;

    import bomoncntt.svk62.msv2051067230.helper.DatabaseHelper;
    import bomoncntt.svk62.msv2051067230.helper.MyListAdapter;
    import bomoncntt.svk62.msv2051067230.model.NhanVien;

    public class User extends AppCompatActivity {

        private static final int EDIT_REQUEST_CODE = 1;
        DatabaseHelper mydb;
    
        ArrayList<NhanVien> arrayListNV;//chứa tất cả các phần tử trong csdl

        MyListAdapter adapter;; //adapter custom
        private SearchView searchView;
        private FloatingActionButton fab;

        private AlertDialog alertDialog;
        private ListView lvnv = null;
        private List<NhanVien> NhanvienCheckedItemList = new ArrayList<NhanVien>();
        SharedPreferences pref;// khai báo
    
        SharedPreferences.Editor editor;//chỉnh sửa dữ liệu
        @SuppressLint({"Range", "MissingInflatedId"})
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user);

            mydb = new DatabaseHelper(this);
            pref=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            arrayListNV=new ArrayList<>();//Khởi tạo mảng lưu các đối tượng

            fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)  {
                    showPopupMenu(view);

                }
            });


            lvnv = findViewById(R.id.listnhanvien); // ánh xạ từ listview sang Java
            Cursor cursor =mydb.Showdata();//đổ dữ liệu từ trong sqlite ra cursor
    
            while (cursor.moveToNext()) {
                String cmnd = cursor.getString(cursor.getColumnIndex("cmnd"));
                String tennv = cursor.getString(cursor.getColumnIndex("tennv"));
                String gt = cursor.getString(cursor.getColumnIndex("gt"));
                String diachi = cursor.getString(cursor.getColumnIndex("diachi"));
                String sdt = cursor.getString(cursor.getColumnIndex("sdt"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String chucvu = cursor.getString(cursor.getColumnIndex("chucvu"));
                String anhnv = cursor.getString(cursor.getColumnIndex("anhnv"));
    
                NhanVien nv = new NhanVien(cmnd,tennv,gt,diachi,sdt,email,chucvu,anhnv);
                arrayListNV.add(nv);
            }
    
            //Truyen arrayadaper qua constructor
            adapter = new MyListAdapter(this, arrayListNV);//gán data mảng vào adapter mà mình custom
            lvnv.setAdapter(adapter);//gán data mảng vào adapter mà mình custom
            lvnv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
    
                    CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.nhanvien_list_item_checkbox);
                    boolean checkboxChecked = false;
                    if(itemCheckbox.isChecked())
                    {
                        itemCheckbox.setChecked(false);
                        checkboxChecked = false;
                    }else
                    {
                        itemCheckbox.setChecked(true);
                        checkboxChecked = true;
                    }
                    NhanVien nv=new NhanVien();
                    nv.setCmnd(arrayListNV.get(position).getCmnd());
                    nv.setTennv(arrayListNV.get(position).getTennv());
                    nv.setGt(arrayListNV.get(position).getGt());
                    nv.setDiachi(arrayListNV.get(position).getDiachi());
                    nv.setSdt(arrayListNV.get(position).getSdt());
                    nv.setEmail(arrayListNV.get(position).getEmail());
                    nv.setChucvu(arrayListNV.get(position).getChucvu());
                    nv.setAnhnv(arrayListNV.get(position).getAnhnv());
                    Toast.makeText(getApplicationContext(),"Select id : " +arrayListNV.get(position).getCmnd() , Toast.LENGTH_SHORT).show();
    
    
                    addCheckListItem(nv, checkboxChecked);
                    Log.v("itemchecklist",NhanvienCheckedItemList.toString());            }
            });
            lvnv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Thực hiện các hành động khi long click vào item
                    NhanVien selectedNhanVien = arrayListNV.get(position);
                    // Tạo dialog để hiển thị thông tin
                    AlertDialog.Builder builder = new AlertDialog.Builder(User.this);
                    builder.setTitle("Thông tin nhân viên");
                    builder.setMessage("CMND: " + selectedNhanVien.getCmnd() + "\n" +
                            "Tên nhân viên: " + selectedNhanVien.getTennv() + "\n" +
                            "Giới tính: " + selectedNhanVien.getGt() + "\n" +
                            "Địa chỉ: " + selectedNhanVien.getDiachi() + "\n" +
                            "Số điện thoại: " + selectedNhanVien.getSdt() + "\n" +
                            "Email: " + selectedNhanVien.getEmail() + "\n" +
                            "Chức vụ: " + selectedNhanVien.getChucvu());
    
                    builder.setNegativeButton("Đóng", null);
                    // Lưu tham chiếu đến dialog để có thể đóng nó nếu cần
                    alertDialog = builder.create();
                    alertDialog.show();
    
                    return true;
                }
            });
    
    
    
        }

        private void setNormalText(TextView textView) {
            textView.setTypeface(null, Typeface.NORMAL);
        }
    
        // Phương thức để đặt kiểu chữ đậm cho TextView
        private void setBoldText(TextView textView) {
            textView.setTypeface(null, Typeface.BOLD);
        }
        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.inflate(R.menu.fab_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.menu_add) {
                            // TODO: Handle add action
                            Log.v("add", "ok");
                            //1 Mở cửa sổ InfoSVActivity
                            Intent intent = new Intent(getApplicationContext(), AddInfor.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("CMND", "");
                            intent.putExtra("TENNV", "");
                            intent.putExtra("GT", "");
                            intent.putExtra("DIACHI", "");
                            intent.putExtra("SDT", "");
                            intent.putExtra("EMAIL", "");
                            intent.putExtra("CHUCVU", "");
                            intent.putExtra("ANH", "");
                            intent.putExtra("Flag", "ADD");
                            startActivity(intent);
                            return true;
                        }else if (menuItem.getItemId() == R.id.menu_edit) {
                            // TODO: Handle edit action
                            if (NhanvienCheckedItemList != null) {
                                int size = NhanvienCheckedItemList.size();
                                if (size != 1) {
                                    Toast.makeText(getApplicationContext(), "Chọn 1 phần tử để sửa", Toast.LENGTH_SHORT).show();
                                } else {
                                    NhanVien tmpDto = NhanvienCheckedItemList.get(0);
                                    Intent editIntent = new Intent(getApplicationContext(), AddInfor.class);
                                    editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    editIntent.putExtra("CMND", tmpDto.getCmnd());
                                    editIntent.putExtra("TENNV", tmpDto.getTennv());
                                    editIntent.putExtra("GT", tmpDto.getGt());
                                    editIntent.putExtra("DIACHI", tmpDto.getDiachi());
                                    editIntent.putExtra("SDT", tmpDto.getSdt());
                                    editIntent.putExtra("EMAIL", tmpDto.getEmail());
                                    editIntent.putExtra("CHUCVU", tmpDto.getChucvu());
                                    editIntent.putExtra("ANH", tmpDto.getAnhnv());
                                    editIntent.putExtra("Flag", "EDIT");

                                    String[] selectedIds = new String[size];
                                    for (int i = 0; i < size; i++) {
                                        tmpDto = NhanvienCheckedItemList.get(i);
                                        selectedIds[i] = tmpDto.getCmnd();
                                    }

                                    editIntent.putExtra("SelectedIds", selectedIds);
                                    startActivityForResult(editIntent, EDIT_REQUEST_CODE);
                                }
                            }
                            return true;
                        }else if (menuItem.getItemId() == R.id.menu_delete) {
                            // TODO: Handle delete action
                            if (NhanvienCheckedItemList != null) {
                                int size = NhanvienCheckedItemList.size();
                                if (size == 0) {
                                    Toast.makeText(getApplicationContext(), "Chọn ít nhất 1 phần tử.", Toast.LENGTH_SHORT).show();
                                } else {
                                    for (int i = 0; i < size; i++) {
                                        NhanVien tmpDto = NhanvienCheckedItemList.get(i);
                                        Integer delete = mydb.delete(tmpDto.getCmnd());
                                        if (delete > 0) {
                                            //  Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                                            //   arrayListSV.remove(i);
                                            NhanvienCheckedItemList.remove(i);
                                            size = NhanvienCheckedItemList.size();

                                        } else {
                                            Toast.makeText(User.this, "Data không bị xóa", Toast.LENGTH_SHORT).show();
                                        }
                                        i--;
                                    }
                                    arrayListNV.clear();

                                    arrayListNV = getAllNhanvien(mydb);
                                    adapter = new MyListAdapter(User.this, arrayListNV);
                                    lvnv.setAdapter(adapter);
                                }
                            }
                            return true;
                        } else if (menuItem.getItemId() == R.id.menu_chamcong) {
                            // TODO: Handle chamcong action
                            if (NhanvienCheckedItemList != null) {
                                int size = NhanvienCheckedItemList.size();
                                if (size != 1) {
                                    Toast.makeText(getApplicationContext(), "Chọn 1 nhân viên để chấm công", Toast.LENGTH_SHORT).show();
                                } else {
                                    NhanVien tmpDto = NhanvienCheckedItemList.get(0);
                                    Intent chamcongIntent = new Intent(getApplicationContext(), AddCC.class);
                                    startActivity(chamcongIntent);
                                }
                            }
                            return true;
                        } else if (menuItem.getItemId() == R.id.menu_tamung) {
                            // TODO: Handle chamcong action
                            if (NhanvienCheckedItemList != null) {
                                int size = NhanvienCheckedItemList.size();
                                if (size != 1) {
                                    Toast.makeText(getApplicationContext(), "Chọn 1 nhân viên để ứng", Toast.LENGTH_SHORT).show();
                                } else {
                                    NhanVien tmpDto = NhanvienCheckedItemList.get(0);
                                    Intent tamungIntent = new Intent(getApplicationContext(), AddTamung.class);
                                    tamungIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    tamungIntent.putExtra("CMND", tmpDto.getCmnd());
                                    tamungIntent.putExtra("TENNV", tmpDto.getTennv());
                                    tamungIntent.putExtra("Flag", "ADD");
                                    startActivity(tamungIntent);
                                }
                            }
                            return true;
                        } else if (menuItem.getItemId() == R.id.menu_home) {
                            // TODO: Handle home action
                            Intent homeintent = new Intent(User.this, MainActivity.class);
                            homeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(homeintent);
                            return true;
                        }
                            return false;
                    }
            });
            popupMenu.show();
        }
        private void addCheckListItem(NhanVien userAccountDto, boolean add)
        {
            if(NhanvienCheckedItemList !=null)
            {
                boolean accountExist = false;
                int existPosition = -1;
                // Loop to check whether the user account dto exist or not.
                int size = NhanvienCheckedItemList.size();
                for(int i=0;i<size;i++)
                {
                    NhanVien tmpDto = NhanvienCheckedItemList.get(i);
                        if(tmpDto.getCmnd() .equals(userAccountDto.getCmnd()))
                    {
                        accountExist = true;
                        existPosition = i;
                        break;
                    }
                }
                if(add)
                {
                    // If not exist then add it.
                    if(!accountExist)
                    {
                        NhanvienCheckedItemList.add(userAccountDto);
                    }
                }else
                {
                    // If exist then remove it.
                    if(accountExist)
                    {
                        if(existPosition!=-1)
                        {
                            NhanvienCheckedItemList.remove(existPosition);
                        }
                    }
                }
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
           getMenuInflater().inflate(R.menu.search_menu,menu);
           MenuItem menuItem = menu.findItem(R.id.menu_search);
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
//                    adapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return true;
                }

             });
            return super.onCreateOptionsMenu(menu);
        }

        public ArrayList<NhanVien> getAllNhanvien(DatabaseHelper mydb ){
    
            ArrayList<NhanVien> nhanviens = new ArrayList<>();
    
            Cursor cursor = mydb.Showdata();
            while (cursor.moveToNext()){
                NhanVien nv = new NhanVien(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6), null);
                nhanviens.add(nv);
            }
            return  nhanviens;
    
        }
    
    }