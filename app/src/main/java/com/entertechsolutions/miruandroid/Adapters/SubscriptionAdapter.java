package com.entertechsolutions.miruandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.entertechsolutions.miruandroid.Models.SubscriptionModel;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Utils.Functions;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>{


    List<SubscriptionModel> mValues;
    Context mContext;
    private SubscriptionAdapter.OnItemClickListener mListener;


    public SubscriptionAdapter(Context context, List<SubscriptionModel> values, SubscriptionAdapter.OnItemClickListener listener1){

        this.mContext = context;
        this.mValues = values;
        this.mListener = listener1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,amount,trial,system,duration;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.DF_name);
            amount = itemView.findViewById(R.id.amount);
            trial = itemView.findViewById(R.id.trial);
            system = itemView.findViewById(R.id.system);
            duration = itemView.findViewById(R.id.duration);

            // cancel = itemView.findViewById(R.id.task_btn_cancel);

        }

        public void bind(final SubscriptionModel item, final SubscriptionAdapter.OnItemClickListener listener) {


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
    public SubscriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.subscription_item, parent, false);

        return new SubscriptionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionAdapter.ViewHolder holder, int position) {

        holder.bind(mValues.get(position), mListener);

        SubscriptionModel data = mValues.get(position);

        holder.name.setText(data.getName());
        holder.amount.setText(data.getAmount().toString()+" USD");
        holder.trial.setText(Functions.getTrial(data.getIsTrail()));
        holder.duration.setText(data.getDurationName());
        holder.system.setText(data.getSystemName());



    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface OnItemClickListener {
        void onClick(SubscriptionModel main_task_model);
    }




}
