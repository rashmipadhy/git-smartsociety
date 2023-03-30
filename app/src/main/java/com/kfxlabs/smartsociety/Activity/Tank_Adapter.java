package com.kfxlabs.smartsociety.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.module.TankPlotDetails;

import java.util.List;

public class Tank_Adapter extends RecyclerView.Adapter<Tank_Adapter.ViewHolder>{

    Context context;
    List<TankPlotDetails.TankPlot> tank_plot;

    public Tank_Adapter(Context context, List<TankPlotDetails.TankPlot> tank_plot){
        this.context = context;
        this.tank_plot = tank_plot;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.power_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tank_Adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
