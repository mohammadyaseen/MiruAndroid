package com.entertechsolutions.miruandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.entertechsolutions.miruandroid.Models.HierarchyList;
import com.entertechsolutions.miruandroid.Models.Task_Model;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Utils.Constant;
import com.entertechsolutions.miruandroid.Utils.Functions;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder>{



    List<Task_Model> mValues;
    Context mContext;
    private MainListAdapter.OnItemClickListener mListener;


    public MainListAdapter(Context context, List<Task_Model> values, MainListAdapter.OnItemClickListener listener1){

        this.mContext = context;
        this.mValues = values;
        this.mListener = listener1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.imageFlag);

            // cancel = itemView.findViewById(R.id.task_btn_cancel);

        }

        public void bind(final Task_Model item, final MainListAdapter.OnItemClickListener listener) {


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onClick(item);

                }
            });



        }

    }

    @NonNull
    @Override
    public MainListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hierarchy_item, parent, false);

        return new MainListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainListAdapter.ViewHolder holder, int position) {

        holder.bind(mValues.get(position), mListener);
       // int stutusint = mValues.get(position).getStatus();


         holder.bind(mValues.get(position), mListener);

        //holder.imageView.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,icon,0);


        holder.name.setText(mValues.get(position).getName());

       /* String pathimage = mValues.get(position).getImagePath();
        if (pathimage.isEmpty()){
            holder.imageView.setVisibility(View.GONE);
        }
        else {
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(holder.imageView)
                    .load(Constant.BASE_URL+pathimage)
                    .skipMemoryCache( false )
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(holder.imageView);
        }*/

       // holder.imageView.(mValues.get(position).getTaskDetail());


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface OnItemClickListener {
        void onClick(Task_Model main_task_model);
    }




}
