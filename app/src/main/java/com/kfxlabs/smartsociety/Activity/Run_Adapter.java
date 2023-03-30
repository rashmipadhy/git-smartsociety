package com.kfxlabs.smartsociety.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.module.MaintLogDetails;
import com.kfxlabs.smartsociety.module.RunLogDetails;

import java.util.List;

public class Run_Adapter extends RecyclerView.Adapter<Run_Adapter.ViewHolder>{

    Context context;
    List<RunLogDetails.RunLog>  run_details;

    public Run_Adapter(Context context, List<RunLogDetails.RunLog> run_details){
        this.context = context;
        this.run_details = run_details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.run_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (run_details != null && run_details.size() > 0){
            RunLogDetails.RunLog model = run_details.get(position);
            holder.id_tv_date.setText(model.date);
            holder.id_time.setText(model.time);
            holder.id_tv_info.setText(model.info);
            holder.id_tv_dur.setText(model.dur);



        }
    }

    @Override
    public int getItemCount() {
        return run_details.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_tv_date,id_time,id_tv_info,id_tv_dur;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            id_tv_date =itemView.findViewById(R.id.id_tv_date);
            id_time = itemView.findViewById(R.id.id_tv_time);
            id_tv_info =itemView.findViewById(R.id.id_tv_info);
            id_tv_dur = itemView.findViewById(R.id.id_tv_dur);
        }

    }
}
