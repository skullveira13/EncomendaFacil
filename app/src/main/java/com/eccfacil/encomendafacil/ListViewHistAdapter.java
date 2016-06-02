package com.eccfacil.encomendafacil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eccfacil.classControl.Encomenda;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by skullveira on 15/05/2016.
 */
public class ListViewHistAdapter extends BaseAdapter {

    private static List<Encomenda> encArrayList;
    private LayoutInflater mInflater;

    public class ViewHolder {
        TextView txtdataAtualizacaoHist;
        TextView txtstatusHist;
        TextView txtlocalHist;
        TextView txtinformacaohist;
    }

    public ListViewHistAdapter(Context context, List<Encomenda> results) {
        encArrayList = results;
        mInflater = LayoutInflater.from(context);
    }
    public int getCount() {
        return encArrayList.size();
    }

    public Object getItem(int position) {
        return encArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.itemlistviewhist, null);
            holder = new ViewHolder();
            holder.txtdataAtualizacaoHist = (TextView) convertView.findViewById(R.id.txtdataAtualizacaoHist);
            holder.txtstatusHist = (TextView) convertView.findViewById(R.id.txtstatusHist);
            holder.txtlocalHist = (TextView) convertView.findViewById(R.id.txtlocalHist);
            holder.txtinformacaohist = (TextView) convertView.findViewById(R.id.txtinformacaohist);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtdataAtualizacaoHist.setText(new SimpleDateFormat("dd/MM/yyyy").format(encArrayList.get(position).getDataAtualizacao()));
        holder.txtstatusHist.setText(encArrayList.get(position).getStatus());
        holder.txtlocalHist.setText(encArrayList.get(position).getLocal());
        holder.txtinformacaohist.setText(encArrayList.get(position).getInformacoes());

        return convertView;
    }
}

