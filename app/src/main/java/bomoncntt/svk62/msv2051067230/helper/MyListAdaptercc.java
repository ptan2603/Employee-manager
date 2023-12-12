package bomoncntt.svk62.msv2051067230.helper;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bomoncntt.svk62.msv2051067230.R;
import bomoncntt.svk62.msv2051067230.model.ChamCong;

public class MyListAdaptercc extends ArrayAdapter<ChamCong> {
    private final Activity context;
    private ArrayList<ChamCong> listcc;

    //Lớp tỉnh
    //static
    private static class ViewHolder {
        TextView txtCmnd_fk;
        TextView txtTennvcham;
        TextView txtNgchamcong;
        TextView txtSngaycong;
    }
    //ViewHolder.txtcmnd="001"
    public MyListAdaptercc(Activity context, ArrayList<ChamCong> data) {
        super(context, R.layout.layout_item_cc, data);
        this.context=context;

        //listsv chứa 3 object
        this.listcc = data;
        Log.d("listcc",""+listcc);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChamCong dataModel = getItem(position); //lấy object
        ViewHolder viewHolder; //cục bộ
        final View result;

        //convertView biến quản lý cho biết các view đã xuất hiện hoặc tồn tại chưa
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            //khác null
            convertView = inflater.inflate(R.layout.layout_item_cc, parent, false);

            //Ánh xạ và gán view vào biến của thộc tính
            viewHolder.txtCmnd_fk =  convertView.findViewById(R.id.item_txtcmnd_fk);
            viewHolder.txtTennvcham =  convertView.findViewById(R.id.item_txttennvcham);
            viewHolder.txtNgchamcong= convertView.findViewById(R.id.item_txtngchamcong);
            viewHolder.txtSngaycong= convertView.findViewById(R.id.item_txtsngaycong);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.txtMasv,... khác null đều có nghĩa là nó đã cấp
        viewHolder.txtCmnd_fk.setText(dataModel.getCmnd_fk());
        viewHolder.txtTennvcham.setText(dataModel.getTennvcham());
        viewHolder.txtNgchamcong.setText(dataModel.getNgchamcong());
        viewHolder.txtSngaycong.setText(dataModel.getSngaycong());
        return convertView;

    };

    public int getItemCount() {
        return listcc.size();
    }

}
