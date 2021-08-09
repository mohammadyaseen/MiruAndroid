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

import com.entertechsolutions.miruandroid.Models.TopicsModel;
import com.entertechsolutions.miruandroid.Models.VideosModel;
import com.entertechsolutions.miruandroid.R;

import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder>{



    List<VideosModel> mValues;
    Context mContext;
    private VideosAdapter.OnItemClickListener mListener;


    public VideosAdapter(Context context, List<VideosModel> values, VideosAdapter.OnItemClickListener listener1){

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

            name = itemView.findViewById(R.id.videoName);
            course = itemView.findViewById(R.id.topicName2);
            topicsClick = itemView.findViewById(R.id.watchBtn);

            // cancel = itemView.findViewById(R.id.task_btn_cancel);

        }

        private void bind(final VideosModel item, final VideosAdapter.OnItemClickListener listener) {


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onClick(item);

                }
            });

            topicsClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.topic(item);
                }
            });



        }

    }

    @NonNull
    @Override
    public VideosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.videos_item, parent, false);

        return new VideosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosAdapter.ViewHolder holder, int position) {

        holder.bind(mValues.get(position), mListener);
        // int stutusint = mValues.get(position).getStatus();

        //  holder.bind(mValues.get(position), mListener);

        //holder.imageView.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,icon,0);

        holder.name.setText(""+ mValues.get(position).getName());
        holder.course.setText(""+mValues.get(position).getTopicName());
        // holder.imageView.(mValues.get(position).getTaskDetail());


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface OnItemClickListener {
        void onClick(VideosModel main_task_model);
        void topic(VideosModel hierarchyList);
    }

}
