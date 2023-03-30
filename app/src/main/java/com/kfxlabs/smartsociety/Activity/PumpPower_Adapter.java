package com.kfxlabs.smartsociety.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.module.PowerLogDetails;
import com.kfxlabs.smartsociety.module.PumpPowerLogDetails;

import java.util.List;

public class PumpPower_Adapter extends RecyclerView.Adapter<PumpPower_Adapter.ViewHolder>{

    Context context;
    List<PumpPowerLogDetails.PumpPowerLog>  pumppower_details;

    public PumpPower_Adapter(Context context, List<PumpPowerLogDetails.PumpPowerLog> pumppower_details){
        this.context = context;
        this.pumppower_details = pumppower_details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pump_power_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (pumppower_details != null && pumppower_details.size() > 0){
            PumpPowerLogDetails.PumpPowerLog model = pumppower_details.get(position);
            holder.id_tv_date.setText(model.date);
            holder.id_info.setText(model.info);
            holder.id_tv_dur.setText(model.dur);
            holder.id_tv_time.setText(model.time);


        }
    }

    @Override
    public int getItemCount() {
        return pumppower_details.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_tv_date,id_info,id_tv_dur,id_tv_time;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            id_tv_date =itemView.findViewById(R.id.id_tv_date);
            id_info = itemView.findViewById(R.id.id_tv_info);
            id_tv_dur =itemView.findViewById(R.id.id_dur);
            id_tv_time = itemView.findViewById(R.id.id_tv_time);


        }

    }
}
