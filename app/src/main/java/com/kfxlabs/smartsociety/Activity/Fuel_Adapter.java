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

import java.util.List;

public class Fuel_Adapter extends RecyclerView.Adapter<Fuel_Adapter.ViewHolder>{

    Context context;
    List<FuelLogDetails.FuelLog>  fuel_details;

    public Fuel_Adapter(Context context,List<FuelLogDetails.FuelLog> fuel_details){
        this.context = context;
        this.fuel_details = fuel_details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.fuel_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (fuel_details != null && fuel_details.size() > 0){
            fuel_details.add(fuel_details.get(position));

            FuelLogDetails.FuelLog model = fuel_details.get(position);

            holder.id_tv_date.setText(model.date);
            holder.id_tv_time.setText(model.time);
            holder.id_ffill.setText(model.fadd);
            holder.id_remark.setText(model.remarks);
            holder.id_new_fuel.setText(model.fval);

        }
    }

    @Override
    public int getItemCount() {
        return fuel_details.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_tv_date,id_tv_time,id_ffill,id_remark,id_new_fuel;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            id_tv_date =itemView.findViewById(R.id.id_tv_date);
            id_tv_time =itemView.findViewById(R.id.id_tv_time);
            id_ffill = itemView.findViewById(R.id.id_ffill);
            id_remark = itemView.findViewById(R.id.id_fremark_text);
            id_new_fuel = itemView.findViewById(R.id.id_new_fuel);

        }



    }
}
