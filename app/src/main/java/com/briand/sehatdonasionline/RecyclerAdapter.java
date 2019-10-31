package com.briand.sehatdonasionline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<victimModel> victimModelList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecyclerAdapter(Context context, List<victimModel> uploads){
        mContext = context;
        victimModelList = uploads;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_model, parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        victimModel currentVictim = victimModelList.get(position);
        holder.namaTextView.setText(currentVictim.getNama());
        holder.goalsTextView.setText(currentVictim.getGoals());
        holder.storyTextView.setText(currentVictim.getStory());
        holder.dateTextView.setText(getDateToday());
        Picasso.get()
                .load(currentVictim.getImageurl())
                .placeholder(R.drawable.imgunvaible)
                .fit()
                .centerCrop()
                .into(holder.victimImageView);
    }

    @Override
    public int getItemCount() {
        return victimModelList.size();
    }
    
    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public TextView namaTextView, goalsTextView, storyTextView, dateTextView;
        public ImageView victimImageView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            namaTextView = itemView.findViewById(R.id.tv_item_name);
            goalsTextView = itemView.findViewById(R.id.goals);
            storyTextView = itemView.findViewById(R.id.tv_item_detail);
            dateTextView = itemView.findViewById(R.id.datetime);
            victimImageView = itemView.findViewById(R.id.img_item_photo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            })

        ;}

    }

    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }


}
