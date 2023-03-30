package com.kfxlabs.smartsociety.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.module.OutflowLogDetails;
import com.kfxlabs.smartsociety.module.PowerLogDetails;

import java.util.List;

public class Outflow_Adapter extends RecyclerView.Adapter<Outflow_Adapter.ViewHolder>{

    Context context;
    List<OutflowLogDetails.OutflowLog>  outflow_details;

    public Outflow_Adapter(Context context, List<OutflowLogDetails.OutflowLog> outflow_details){
        this.context = context;
        this.outflow_details = outflow_details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.outflow_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (outflow_details != null && outflow_details.size() > 0){
            OutflowLogDetails.OutflowLog model = outflow_details.get(position);
            holder.id_tv_date.setText(model.date);
            holder.id_tv_dur.setText(model.litres);
            holder.id_tv_time.setText(model.time);

        }
    }

    @Override
    public int getItemCount() {
        return outflow_details.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_tv_date,id_tv_dur,id_tv_time;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            id_tv_date =itemView.findViewById(R.id.id_tv_date);
            id_tv_dur =itemView.findViewById(R.id.id_dur);
            id_tv_time = itemView.findViewById(R.id.id_tv_time);
        }

    }
}
