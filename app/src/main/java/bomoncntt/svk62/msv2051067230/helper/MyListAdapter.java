package bomoncntt.svk62.msv2051067230.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import bomoncntt.svk62.msv2051067230.R;
import bomoncntt.svk62.msv2051067230.model.NhanVien;
import de.hdodenhof.circleimageview.CircleImageView;


public class MyListAdapter extends ArrayAdapter<NhanVien> implements Filterable {

    private final Activity context;
    private ArrayList<NhanVien> listnv;


    //Lớp tỉnh
    //static
    private static class ViewHolder {
        TextView txtCmnd;
        TextView txtTennv;
        TextView txtsdt;
        TextView txtchucvu;
        CircleImageView anhnv;
    }
    //ViewHolder.txtcmnd="001"
    public MyListAdapter(Activity context, ArrayList<NhanVien> data) {
        super(context, R.layout.layout_item_nv, data);
        this.context=context;

        //listsv chứa 3 object
        this.listnv = data;
        Log.d("listnv",""+listnv);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        NhanVien dataModel = getItem(position); //lấy object
        ViewHolder viewHolder; //cục bộ
        final View result;

        //convertView biến quản lý cho biết các view đã xuất hiện hoặc tồn tại chưa
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            //khác null
            convertView = inflater.inflate(R.layout.layout_item_nv, parent, false);

            //Ánh xạ và gán view vào biến của thộc tính
            viewHolder.txtCmnd =  convertView.findViewById(R.id.item_txtcmnd);
            viewHolder.txtTennv =  convertView.findViewById(R.id.item_txttennv);
            viewHolder.txtsdt= convertView.findViewById(R.id.item_txtsdt);
            viewHolder.txtchucvu= convertView.findViewById(R.id.item_txtchucvu);
            viewHolder.anhnv= convertView.findViewById(R.id.imageViewnv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.txtMasv,... khác null đều có nghĩa là nó đã cấp
        viewHolder.txtCmnd.setText(dataModel.getCmnd());
        viewHolder.txtTennv.setText(dataModel.getTennv());
        viewHolder.txtsdt.setText(dataModel.getSdt());
        viewHolder.txtchucvu.setText(dataModel.getChucvu());
        viewHolder.anhnv.setImageBitmap(StringToBitMap(dataModel.getAnhnv()));
        return convertView;

    };

    public int getItemCount() {
        return listnv.size();
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}