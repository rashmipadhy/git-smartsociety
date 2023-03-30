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
import com.kfxlabs.smartsociety.module.MaintLogDetails;

import java.util.List;

public class Maint_Adapter extends RecyclerView.Adapter<Maint_Adapter.ViewHolder>{

    Context context;
    List<MaintLogDetails.MaintLog>  maint_details;

    public Maint_Adapter(Context context, List<MaintLogDetails.MaintLog> maint_details){
        this.context = context;
        this.maint_details = maint_details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.maint_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (maint_details != null && maint_details.size() > 0){
            MaintLogDetails.MaintLog model = maint_details.get(position);
            holder.id_tv_date.setText(model.date);
            holder.id_tv_type.setText(model.mtype);
            holder.id_tv_ehours.setText(model.ehours);
           // holder.id_uid.setText(model.uid);
            holder.id_remarks.setText(model.remarks);

        }
    }

    @Override
    public int getItemCount() {
        return maint_details.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_tv_date,id_tv_type,id_tv_ehours,id_uid,id_remarks;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            id_tv_date =itemView.findViewById(R.id.id_tv_date);
            id_tv_type =itemView.findViewById(R.id.id_tv_type);
            id_tv_ehours = itemView.findViewById(R.id.id_tv_ehours);

            id_remarks = itemView.findViewById(R.id.id_remarks);
        }

    }
}
