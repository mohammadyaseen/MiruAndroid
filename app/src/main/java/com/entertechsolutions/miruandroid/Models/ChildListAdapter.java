package com.entertechsolutions.miruandroid.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.entertechsolutions.miruandroid.Adapters.MainListAdapter;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Utils.Constant;

import java.util.List;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ViewHolder>{



    List<Task_Model> mValues;
    Context mContext;
    private ChildListAdapter.OnItemClickListener mListener;


    public ChildListAdapter(Context context, List<Task_Model> values, ChildListAdapter.OnItemClickListener listener1){

        this.mContext = context;
        this.mValues = values;
        this.mListener = listener1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.childName);
            imageView = itemView.findViewById(R.id.imagechile);

            // cancel = itemView.findViewById(R.id.task_btn_cancel);

        }

        public void bind(final Task_Model item, final ChildListAdapter.OnItemClickListener listener) {


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
    public ChildListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.child_item, parent, false);

        return new ChildListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildListAdapter.ViewHolder holder, int position) {

        holder.bind(mValues.get(position), mListener);
        // int stutusint = mValues.get(position).getStatus();


        //  holder.bind(mValues.get(position), mListener);

        //holder.imageView.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,icon,0);


        holder.name.setText(mValues.get(position).getName());
       // int icon = R.drawable.ic_clear_black_24dp;

      /*  String pathimage = "images/Hierarchy/test/2021/8/1/"+ mValues.get(position).getImagePath();
        if (mValues.get(position).getImagePath()==null){
            // holder.imageView.setBackgroundResource(R.drawable.ic_clear_black_24dp);
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
