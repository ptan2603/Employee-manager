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
import bomoncntt.svk62.msv2051067230.model.TamUng;

public class MyListAdaptertu extends ArrayAdapter<TamUng> {

    private final Activity context;
    private ArrayList<TamUng> listtu;

    //Lớp tỉnh
    //static
    private static class ViewHolder {
        TextView txtCmndfk;
        TextView txtTennvung;
        TextView txtNgayung;
        TextView txtSotienung;
    }
    //ViewHolder.txtcmnd="001"
    public MyListAdaptertu(Activity context, ArrayList<TamUng> data) {
        super(context, R.layout.layout_item_tu, data);
        this.context=context;

        //listsv chứa 3 object
        this.listtu = data;
        Log.d("listtu",""+listtu);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TamUng dataModel = getItem(position); //lấy object
        ViewHolder viewHolder; //cục bộ
        final View result;

        //convertView biến quản lý cho biết các view đã xuất hiện hoặc tồn tại chưa
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            //khác null
            convertView = inflater.inflate(R.layout.layout_item_tu, parent, false);

            //Ánh xạ và gán view vào biến của thộc tính
            viewHolder.txtCmndfk =  convertView.findViewById(R.id.item_txtcmndfk);
            viewHolder.txtTennvung =  convertView.findViewById(R.id.item_txttennvung);
            viewHolder.txtNgayung= convertView.findViewById(R.id.item_txtngayung);
            viewHolder.txtSotienung= convertView.findViewById(R.id.item_txtsotienung);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.txtMasv,... khác null đều có nghĩa là nó đã cấp
        viewHolder.txtCmndfk.setText(dataModel.getCmndfk());
        viewHolder.txtTennvung.setText(dataModel.getTennvung());
        viewHolder.txtNgayung.setText(dataModel.getNgayung());
        viewHolder.txtSotienung.setText(dataModel.getSotienung());
        return convertView;

    };

    public int getItemCount() {
        return listtu.size();
    }
}
