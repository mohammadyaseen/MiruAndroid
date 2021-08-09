package com.entertechsolutions.miruandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.entertechsolutions.miruandroid.Models.HierarchyList;
import com.entertechsolutions.miruandroid.Models.TopicsModel;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Utils.Constant;

import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ViewHolder>{



    List<TopicsModel> mValues;
    Context mContext;
    private TopicsAdapter.OnItemClickListener mListener;


    public TopicsAdapter(Context context, List<TopicsModel> values, TopicsAdapter.OnItemClickListener listener1){

        this.mContext = context;
        this.mValues = values;
        this.mListener = listener1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,course;
        ImageView imageView;
        Button topicsClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.topicName);
            course = itemView.findViewById(R.id.courseName);
            //topicsClick = itemView.findViewById(R.id.task_btn_done);

            // cancel = itemView.findViewById(R.id.task_btn_cancel);

        }

        private void bind(final TopicsModel item, final TopicsAdapter.OnItemClickListener listener) {


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onClick(item);

                }
            });

            /*topicsClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.topic(item);
                }
            });*/



        }

    }

    @NonNull
    @Override
    public TopicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.topics_item, parent, false);

        return new TopicsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicsAdapter.ViewHolder holder, int position) {

        holder.bind(mValues.get(position), mListener);
        // int stutusint = mValues.get(position).getStatus();

        //  holder.bind(mValues.get(position), mListener);

        //holder.imageView.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,icon,0);

        holder.name.setText("Topics Name : "+ mValues.get(position).getName());
        holder.course.setText("Course Name : "+mValues.get(position).getCourseName());
        // holder.imageView.(mValues.get(position).getTaskDetail());


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface OnItemClickListener {
        void onClick(TopicsModel main_task_model);
        //void topic(HierarchyList hierarchyList);
    }

}
