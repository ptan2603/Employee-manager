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
import bomoncntt.svk62.msv2051067230.helper.MyListAdaptercc;
import bomoncntt.svk62.msv2051067230.model.ChamCong;

public class Chamcong extends AppCompatActivity {
    private static final int EDIT_REQUEST_CODE = 1;
    DatabaseHelper mydb;

    ArrayList<ChamCong> arrayListCC=null;//chứa tất cả các phần tử trong csdl
    MyListAdaptercc adapter=null;; //adapter custom
    private FloatingActionButton fab;
    private AlertDialog alertDialog;
    private ListView lvcc = null;
    private List<ChamCong> ChamcongCheckedItemList = new ArrayList<ChamCong>();

    SharedPreferences pref;// khai báo

    SharedPreferences.Editor editor;//chỉnh sửa dữ liệu
    @SuppressLint("Range")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamcong);
        mydb = new DatabaseHelper(this);
        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        arrayListCC = getAllChamcong(mydb); // khởi tạo mảng lưu

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });


        lvcc = findViewById(R.id.listchamcong); // ánh xạ từ listview sang Java
        Cursor cursor =mydb.ShowdataChamcong();//đổ dữ liệu từ trong sqlite ra cursor

        while (cursor.moveToNext()) {
            String cmndfk = cursor.getString(cursor.getColumnIndex("cmnd_fk"));
            String tennvcham = cursor.getString(cursor.getColumnIndex("tennvcham"));
            String ngchamcong = cursor.getString(cursor.getColumnIndex("ngchamcong"));
            String sngaycong = cursor.getString(cursor.getColumnIndex("sngaycong"));


            ChamCong cc = new ChamCong(cmndfk,tennvcham,ngchamcong,sngaycong);
            arrayListCC.add(cc);
        }

        //Truyen arrayadaper qua constructor
        adapter = new MyListAdaptercc(this, arrayListCC);//gán data mảng vào adapter mà mình custom
        lvcc.setAdapter(adapter);//gán data mảng vào adapter mà mình custom
        lvcc.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.chamcong_list_item_checkbox);
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
                ChamCong cc=new ChamCong();
                cc.setCmnd_fk(arrayListCC.get(position).getCmnd_fk());
                cc.setTennvcham(arrayListCC.get(position).getTennvcham());
                cc.setNgchamcong(arrayListCC.get(position).getNgchamcong());
                cc.setSngaycong(arrayListCC.get(position).getSngaycong());
                Toast.makeText(getApplicationContext(),"Select id : " +arrayListCC.get(position).getNgchamcong() , Toast.LENGTH_SHORT).show();


                addCheckListItem(cc, checkboxChecked);
                Log.v("itemchecklist",ChamcongCheckedItemList.toString());            }
        });
    }

    // Phương thức để đặt kiểu chữ đậm cho TextView
    private void setBoldText(TextView textView) {
        textView.setTypeface(null, Typeface.BOLD);
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.fab_menu_chamcong);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
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
                } else if (menuItem.getItemId() == R.id.menu_edit) {
                    // TODO: Handle edit action
                    if (ChamcongCheckedItemList != null) {
                        int size = ChamcongCheckedItemList.size();
                        if (size != 1) {
                            Toast.makeText(getApplicationContext(), "Chọn 1 phần tử để sửa", Toast.LENGTH_SHORT).show();
                        } else {
                            ChamCong tmpDto = ChamcongCheckedItemList.get(0);
                            Intent editIntent = new Intent(getApplicationContext(), AddCC.class);
                            editIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            editIntent.putExtra("CMNDFK", tmpDto.getCmnd_fk());
                            editIntent.putExtra("TENNVCHAM", tmpDto.getTennvcham());
                            editIntent.putExtra("NGCHAMCONG", tmpDto.getNgchamcong());
                            editIntent.putExtra("SNGAYCONG", tmpDto.getSngaycong());
                            editIntent.putExtra("Flag", "EDIT");

                            String[] selectedIds = new String[size];
                            for (int i = 0; i < size; i++) {
                                tmpDto = ChamcongCheckedItemList.get(i);
                                selectedIds[i] = tmpDto.getCmnd_fk();
                            }

                            editIntent.putExtra("SelectedIds", selectedIds);
                            startActivityForResult(editIntent, EDIT_REQUEST_CODE);
                        }
                    }
                    return true;
                }else if (menuItem.getItemId() == R.id.menu_delete) {
                    // TODO: Handle delete action
                    if (ChamcongCheckedItemList != null) {
                        int size = ChamcongCheckedItemList.size();
                        if (size == 0) {
                            Toast.makeText(getApplicationContext(), "Chọn ít nhất 1 phần tử.", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < size; i++) {
                                ChamCong tmpDto = ChamcongCheckedItemList.get(i);
                                Integer deleteChamcong = mydb.deleteChamcong(tmpDto.getNgchamcong());
                                if (deleteChamcong > 0) {
                                    //  Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                                    //   arrayListSV.remove(i);
                                    ChamcongCheckedItemList.remove(i);
                                    size = ChamcongCheckedItemList.size();

                                } else {
                                    Toast.makeText(Chamcong.this, "Data không bị xóa", Toast.LENGTH_SHORT).show();
                                }
                                i--;
                            }
                            arrayListCC.clear();

                            arrayListCC = getAllChamcong(mydb);
                            adapter = new MyListAdaptercc(Chamcong.this, arrayListCC);
                            lvcc.setAdapter(adapter);
                        }
                    }
                    return true;
                }else if (menuItem.getItemId() == R.id.menu_Nhanvien) {
                    // TODO: Handle chamcong action
                    Intent chamcongintent = new Intent(Chamcong.this, User.class);
                    chamcongintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(chamcongintent);
                    return true;
                }else if (menuItem.getItemId() == R.id.menu_tamung) {
                    // TODO: Handle chamcong action
                    Intent tamungintent = new Intent(Chamcong.this, Tamung.class);
                    tamungintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(tamungintent);
                    return true;
                } else if (menuItem.getItemId() == R.id.menu_home) {
                        // TODO: Handle home action
                        Intent homeintent = new Intent(Chamcong.this, MainActivity.class);
                        homeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homeintent);
                        return true;
                }
                        return false;
                }
        });
        popupMenu.show();
    }
    private void addCheckListItem(ChamCong userAccountDto, boolean add)
    {
        if(ChamcongCheckedItemList !=null)
        {
            boolean accountExist = false;
            int existPosition = -1;
            // Loop to check whether the user account dto exist or not.
            int size = ChamcongCheckedItemList.size();
            for(int i=0;i<size;i++)
            {
                ChamCong tmpDto = ChamcongCheckedItemList.get(i);
                if(tmpDto.getNgchamcong() .equals(userAccountDto.getNgchamcong()))
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
                    ChamcongCheckedItemList.add(userAccountDto);
                }
            }else
            {
                // If exist then remove it.
                if(accountExist)
                {
                    if(existPosition!=-1)
                    {
                        ChamcongCheckedItemList.remove(existPosition);
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

    public ArrayList<ChamCong> getAllChamcong(DatabaseHelper mydb ){

        ArrayList<ChamCong> chamcongs = new ArrayList<>();

        Cursor cursor = mydb.Showdata();
        while (cursor.moveToNext()){
            ChamCong cc = new ChamCong(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
            chamcongs.add(cc);
        }
        return  chamcongs;

    }
}