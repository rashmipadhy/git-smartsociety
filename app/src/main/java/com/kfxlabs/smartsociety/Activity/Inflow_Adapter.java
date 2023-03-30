package com.kfxlabs.smartsociety.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.module.InflowLogDetails;
import com.kfxlabs.smartsociety.module.OutflowLogDetails;

import java.util.List;

public class Inflow_Adapter extends RecyclerView.Adapter<Inflow_Adapter.ViewHolder>{

    Context context;
    List<InflowLogDetails.InflowLog>  inflow_details;

    public Inflow_Adapter(Context context, List<InflowLogDetails.InflowLog> inflow_details){
        this.context = context;
        this.inflow_details = inflow_details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.inflow_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (inflow_details != null && inflow_details.size() > 0){
            InflowLogDetails.InflowLog model = inflow_details.get(position);
            holder.id_date.setText(model.date);
            holder.id_litres.setText(model.litres);
            holder.id_time.setText(model.time);

        }
    }

    @Override
    public int getItemCount() {
        return inflow_details.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_date,id_litres,id_time;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            id_date =itemView.findViewById(R.id.id_date);
            id_litres =itemView.findViewById(R.id.id_litres);
            id_time = itemView.findViewById(R.id.id_time);
        }

    }
}
