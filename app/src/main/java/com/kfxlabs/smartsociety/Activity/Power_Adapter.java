package com.kfxlabs.smartsociety.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.module.FuelLogDetails;
import com.kfxlabs.smartsociety.module.PowerLogDetails;

import java.util.List;

public class Power_Adapter extends RecyclerView.Adapter<Power_Adapter.ViewHolder>{

    Context context;
    List<PowerLogDetails.PowerLog>  power_details;

    public Power_Adapter(Context context, List<PowerLogDetails.PowerLog> power_details){
        this.context = context;
        this.power_details = power_details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.power_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (power_details != null && power_details.size() > 0){
            PowerLogDetails.PowerLog model = power_details.get(position);
            holder.id_tv_date.setText(model.date);
            holder.id_tv_time.setText(model.time);
            holder.id_info.setText(model.info);
            holder.id_tv_dur.setText(model.dur);

        }
    }

    @Override
    public int getItemCount() {
        return power_details.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_tv_date,id_tv_time,id_info,id_tv_dur;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            id_tv_date =itemView.findViewById(R.id.id_tv_date);
            id_tv_time = itemView.findViewById(R.id.id_tv_time);
            id_info = itemView.findViewById(R.id.id_tv_info);
            id_tv_dur =itemView.findViewById(R.id.id_dur);

        }

    }
}
