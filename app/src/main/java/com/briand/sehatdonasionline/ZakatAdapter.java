package com.briand.sehatdonasionline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ZakatAdapter extends RecyclerView.Adapter<ZakatAdapter.ListViewHolder> {

    private ArrayList<Zakat> list;

    public ZakatAdapter(ArrayList<Zakat> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrowzakat, parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        Zakat zakat = list.get(position);

        holder.tv_nama.setText(zakat.getZakat());
        holder.tv_detail.setText(zakat.getDetail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama, tv_detail;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama = itemView.findViewById(R.id.nama_zakat);
            tv_detail = itemView.findViewById(R.id.detil_zakat);
        }
    }
}
