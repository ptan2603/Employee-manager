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
import bomoncntt.svk62.msv2051067230.helper.MyListAdaptertu;
import bomoncntt.svk62.msv2051067230.model.TamUng;

public class Tamung extends AppCompatActivity {
    private static final int EDIT_REQUEST_CODE = 1;
    DatabaseHelper mydb;

    ArrayList<TamUng> arrayListTU=null;//chứa tất cả các phần tử trong csdl
    MyListAdaptertu adapter=null;; //adapter custom
    private FloatingActionButton fab;
    private AlertDialog alertDialog;
    private ListView lvtu = null;
    private List<TamUng> TamungCheckedItemList = new ArrayList<TamUng>();

    SharedPreferences pref;// khai báo

    SharedPreferences.Editor editor;//chỉnh sửa dữ liệu
    @SuppressLint("Range")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamung);
        mydb = new DatabaseHelper(this);
        pref=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        arrayListTU=new ArrayList<>();//Khởi tạo mảng lưu các đối tượng

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });


        lvtu = findViewById(R.id.listtamung); // ánh xạ từ listview sang Java
        Cursor cursor =mydb.ShowdataTamung();//đổ dữ liệu từ trong sqlite ra cursor

        while (cursor.moveToNext()) {
            String cmndfk = cursor.getString(cursor.getColumnIndex("cmndfk"));
            String tennvung = cursor.getString(cursor.getColumnIndex("tennvung"));
            String ngayung = cursor.getString(cursor.getColumnIndex("ngayung"));
            String sotienung = cursor.getString(cursor.getColumnIndex("sotienung"));


            TamUng tu = new TamUng(cmndfk,tennvung,ngayung,sotienung);
            arrayListTU.add(tu);
        }

        //Truyen arrayadaper qua constructor
        adapter = new MyListAdaptertu(this, arrayListTU);//gán data mảng vào adapter mà mình custom
        lvtu.setAdapter(adapter);//gán data mảng vào adapter mà mình custom
        lvtu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.tamung_list_item_checkbox);
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
                TamUng tu=new TamUng();
                tu.setCmndfk(arrayListTU.get(position).getCmndfk());
                tu.setTennvung(arrayListTU.get(position).getTennvung());
                tu.setNgayung(arrayListTU.get(position).getNgayung());
                tu.setSotienung(arrayListTU.get(position).getSotienung());
                Toast.makeText(getApplicationContext(),"Select id : " +arrayListTU.get(position).getNgayung() , Toast.LENGTH_SHORT).show();


                addCheckListItem(tu, checkboxChecked);
                Log.v("itemchecklist",TamungCheckedItemList.toString());            }
        });
    }

    // Phương thức để đặt kiểu chữ đậm cho TextView
    private void setBoldText(TextView textView) {
        textView.setTypeface(null, Typeface.BOLD);
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.fab_menu_tamung);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_add) {
                    // TODO: Handle add action
                    Log.v("add", "ok");
                    //1 Mở cửa sổ InfoSVActivity
                    Intent intent = new Intent(getApplicationContext(), AddCC.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("CMNDFK", "");
                    intent.putExtra("TENNVCHAM", "");
                    intent.putExtra("NGCHAMCONG", "");
                    intent.putExtra("SNGAYCONG", "");
                    intent.putExtra("Flag", "ADD");
                    startActivity(intent);
                    return true;
                }else if (menuItem.getItemId() == R.id.menu_edit) {
                    // TODO: Handle edit action
                    if (TamungCheckedItemList != null) {
                        int size = TamungCheckedItemList.size();
                        if (size != 1) {
                            Toast.makeText(getApplicationContext(), "Chọn 1 phần tử để sửa", Toast.LENGTH_SHORT).show();
                        } else {
                            TamUng tmpDto = TamungCheckedItemList.get(0);
                            Intent editIntent = new Intent(getApplicationContext(), AddTamung.class);
                            editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            editIntent.putExtra("CMNDFK", tmpDto.getCmndfk());
                            editIntent.putExtra("TENNVUNG", tmpDto.getTennvung());
                            editIntent.putExtra("NGAYUNG", tmpDto.getNgayung());
                            editIntent.putExtra("SOTIENUNG", tmpDto.getSotienung());
                            editIntent.putExtra("Flag", "EDIT");

                            String[] selectedIds = new String[size];
                            for (int i = 0; i < size; i++) {
                                tmpDto = TamungCheckedItemList.get(i);
                                selectedIds[i] = tmpDto.getCmndfk();
                            }

                            editIntent.putExtra("SelectedIds", selectedIds);
                            startActivityForResult(editIntent, EDIT_REQUEST_CODE);
                        }
                    }
                    return true;
                }else if (menuItem.getItemId() == R.id.menu_delete) {
                    // TODO: Handle delete action
                    if (TamungCheckedItemList != null) {
                        int size = TamungCheckedItemList.size();
                        if (size == 0) {
                            Toast.makeText(getApplicationContext(), "Chọn ít nhất 1 phần tử.", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < size; i++) {
                                TamUng tmpDto = TamungCheckedItemList.get(i);
                                Integer deleteTamung = mydb.deleteTamung(tmpDto.getNgayung());
                                if (deleteTamung > 0) {
                                    //  Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                                    //   arrayListSV.remove(i);
                                    TamungCheckedItemList.remove(i);
                                    size = TamungCheckedItemList.size();

                                } else {
                                    Toast.makeText(Tamung.this, "Data không bị xóa", Toast.LENGTH_SHORT).show();
                                }
                                i--;
                            }
                            arrayListTU.clear();

                            arrayListTU = getAllTamung(mydb);
                            adapter = new MyListAdaptertu(Tamung.this, arrayListTU);
                            lvtu.setAdapter(adapter);
                        }
                    }
                    return true;
                }else if (menuItem.getItemId() == R.id.menu_chamcong) {
                    // TODO: Handle chamcong action
                    Intent chamcongintent = new Intent(Tamung.this, Chamcong.class);
                    chamcongintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(chamcongintent);
                    return true;
                }else if (menuItem.getItemId() == R.id.menu_Nhanvien) {
                    // TODO: Handle chamcong action
                    Intent nhanvienintent = new Intent(Tamung.this, User.class);
                    nhanvienintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(nhanvienintent);
                    return true;
                }else if (menuItem.getItemId() == R.id.menu_home) {
                    // TODO: Handle home action
                    Intent homeintent = new Intent(Tamung.this, MainActivity.class);
                    homeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(homeintent);
                    return true;
                }
                        return false;
                }
        });
        popupMenu.show();
    }
    private void addCheckListItem(TamUng userAccountDto, boolean add)
    {
        if(TamungCheckedItemList !=null)
        {
            boolean accountExist = false;
            int existPosition = -1;
            // Loop to check whether the user account dto exist or not.
            int size = TamungCheckedItemList.size();
            for(int i=0;i<size;i++)
            {
                TamUng tmpDto = TamungCheckedItemList.get(i);
                if(tmpDto.getNgayung() .equals(userAccountDto.getNgayung()))
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
                    TamungCheckedItemList.add(userAccountDto);
                }
            }else
            {
                // If exist then remove it.
                if(accountExist)
                {
                    if(existPosition!=-1)
                    {
                        TamungCheckedItemList.remove(existPosition);
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

    public ArrayList<TamUng> getAllTamung(DatabaseHelper mydb ){

        ArrayList<TamUng> tamungs = new ArrayList<>();

        Cursor cursor = mydb.ShowdataTamung();
        while (cursor.moveToNext()){
            TamUng tu = new TamUng(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
            tamungs.add(tu);
        }
        return  tamungs;

    }
}