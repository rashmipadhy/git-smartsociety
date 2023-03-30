package com.kfxlabs.smartsociety.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.model.Alerts_Details;
import com.kfxlabs.smartsociety.module.DgAlerts;

import java.util.List;

public class DgAlerts_Adapter extends RecyclerView.Adapter<DgAlerts_Adapter.ViewHolder>{

    Context context;
    List<Alerts_Details> alertValueDetailsList;

    public DgAlerts_Adapter(Context context, List<Alerts_Details> alerts_details){
        this.context = context;
        this.alertValueDetailsList = alerts_details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.alerts_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (alertValueDetailsList != null && alertValueDetailsList.size() > 0){

            Alerts_Details model = alertValueDetailsList.get(position);

            holder.id_date.setText(model.date);
            holder.id_alerts.setText(model.alerts);
            holder.id_time.setText(model.time);

        }
    }

    @Override
    public int getItemCount() {
        return alertValueDetailsList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_date,id_alerts,id_time;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            id_date =itemView.findViewById(R.id.id_date);
            id_alerts =itemView.findViewById(R.id.id_alerts);
            id_time = itemView.findViewById(R.id.id_time);
        }

    }
}
