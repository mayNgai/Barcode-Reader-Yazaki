package com.dtc.service.yazaki.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.functionsAndVariable.GlobalFunctions;
import com.dtc.service.yazaki.model.TblSetting;
import com.dtc.service.yazaki.model.TblTask;
import com.dtc.service.yazaki.until.TaskController;
import com.dtc.service.yazaki.view.MainActivity;
import com.dtc.service.yazaki.view.ScanActivity;

import java.util.List;

/**
 * Created by May on 11/6/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
    private static Context mcontext;
    private List<TblTask> arrayList;
    private static MainActivity mView;
    public static TaskController taskController = new TaskController();

    public MainAdapter(Context context, List<TblTask> _arrayList){
        this.arrayList = _arrayList;
        this.mcontext = context;
        mView = new MainActivity();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MainAdapter.ViewHolder holder, final int i) {
        holder.txt_p_o.setText(arrayList.get(i).getPo());
        holder.txt_tag.setText(arrayList.get(i).getTag());
        holder.txt_destination.setText(arrayList.get(i).getDistribution());
        holder.txt_delivery_date_time.setText(GlobalFunctions.ChangeCalendarFormat(getCalendar_FromDbStorage(),
                GlobalFunctions.ChangeDateFormate(getDate_FormatFromDbStorage(),arrayList.get(i).getDeliveryDateTime().substring(0,10)))
                + arrayList.get(i).getDeliveryDateTime().substring(10,arrayList.get(i).getDeliveryDateTime().length()));
        holder.txt_status.setText(arrayList.get(i).getDeliveryName());
        holder.linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, ScanActivity.class);
                intent.putExtra("member",mView.member);
                intent.putExtra("fragNew","update");
                intent.putExtra("task_select",arrayList.get(i));
                mcontext.startActivity(intent);
                //mView.setClickList(view);
            }
        });
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_main, vGroup, false);
        return new MainAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_p_o,txt_tag,txt_destination,txt_delivery_date_time,txt_status;
        private LinearLayout linear_list;

        public ViewHolder(View v) {
            super(v);
            txt_p_o = (TextView) v.findViewById(R.id.txt_p_o);
            txt_tag = (TextView) v.findViewById(R.id.txt_tag);
            txt_destination = (TextView) v.findViewById(R.id.txt_destination);
            txt_delivery_date_time = (TextView) v.findViewById(R.id.txt_delivery_date_time);
            txt_status = (TextView) v.findViewById(R.id.txt_status);
            linear_list = (LinearLayout) v.findViewById(R.id.linear_list);
        }
    }

    public String getDate_FormatFromDbStorage(){
        String date_format;
        if(taskController != null){
            List<TblSetting> tblSettings = taskController.getSetting();
            date_format = GlobalFunctions.convertDateFormat(tblSettings.get(0).getDateFormat());
        }else{
            date_format = "dd/mm/yyyy";
        }
        return date_format;
    }

    public String getCalendar_FromDbStorage(){
        String calendar;
        if(taskController != null){
            List<TblSetting> tblSettings = taskController.getSetting();
            calendar = GlobalFunctions.convertCalendar(tblSettings.get(0).getCalendar());
        }else{
            calendar = "christian";
        }
        return calendar;
    }

}
