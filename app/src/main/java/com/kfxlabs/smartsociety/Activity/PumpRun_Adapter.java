package com.kfxlabs.smartsociety.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.module.PumpRunLogDetails;
import com.kfxlabs.smartsociety.module.RunLogDetails;

import java.util.List;

public class PumpRun_Adapter extends RecyclerView.Adapter<PumpRun_Adapter.ViewHolder>{

    Context context;
    List<PumpRunLogDetails.PumpRunLog>  pumprun_details;

    public PumpRun_Adapter(Context context, List<PumpRunLogDetails.PumpRunLog> pumprun_details){
        this.context = context;
        this.pumprun_details = pumprun_details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pump_run_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (pumprun_details != null && pumprun_details.size() > 0){
            PumpRunLogDetails.PumpRunLog model = pumprun_details.get(position);
            holder.id_tv_date.setText(model.date);
            holder.id_tv_info.setText(model.info);
            holder.id_tv_dur.setText(model.dur);
            holder.id_time.setText(model.time);




        }
    }

    @Override
    public int getItemCount() {
        return pumprun_details.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_tv_date,id_tv_info,id_tv_dur,id_time;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            id_tv_date =itemView.findViewById(R.id.id_tv_date);
            id_tv_info =itemView.findViewById(R.id.id_tv_info);
            id_tv_dur = itemView.findViewById(R.id.id_tv_dur);
            id_time = itemView.findViewById(R.id.id_tv_time);


        }

    }
}
