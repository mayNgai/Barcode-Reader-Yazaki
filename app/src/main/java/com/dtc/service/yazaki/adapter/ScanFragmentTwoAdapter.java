package com.dtc.service.yazaki.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.fragment.ScanFragmentOne;
import com.dtc.service.yazaki.fragment.ScanFragmentTwo;
import com.dtc.service.yazaki.model.TblIdAndName;
import com.dtc.service.yazaki.model.TblTask;
import com.dtc.service.yazaki.view.ScanActivity;

import java.util.List;

/**
 * Created by admin on 11/17/2017 AD.
 */

public class ScanFragmentTwoAdapter extends RecyclerView.Adapter<ScanFragmentTwoAdapter.ViewHolder>{

    private static Context mcontext;
    private List<TblIdAndName> arrayList;
    private ScanActivity mView;
    private static ScanFragmentOne fragmentOne;
    private static ScanFragmentTwo fragmentTwo;

    public ScanFragmentTwoAdapter(Context context, List<TblIdAndName> _arrayList){
        this.arrayList = _arrayList;
        this.mcontext = context;
        mView = new ScanActivity();
        fragmentOne = new ScanFragmentOne();
        fragmentTwo = new ScanFragmentTwo();
    }

    @Override
    public void onBindViewHolder(final ScanFragmentTwoAdapter.ViewHolder holder, final int i) {
        holder.txt_name.setText(arrayList.get(i).getName());
        holder.linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mView.strName.equalsIgnoreCase("pickup")){
                    fragmentOne.txt_pick_up_point.setText(arrayList.get(i).getName());
                    mView.tblTask.setPickupName(arrayList.get(i).getName());
                    mView.tblTask.setPickupPoint(arrayList.get(i).getId());
                }else if(mView.strName.equalsIgnoreCase("destination")){
                    fragmentOne.txt_destination.setText(arrayList.get(i).getName());
                    mView.tblTask.setDistribution(arrayList.get(i).getName());
                    mView.tblTask.setDistributionCode(arrayList.get(i).getId());
                }else if(mView.strName.equalsIgnoreCase("vehicle")){
                    fragmentOne.txt_vehicle.setText(arrayList.get(i).getName());
                    mView.tblTask.setVehicleName(arrayList.get(i).getName());
                    mView.tblTask.setVehicle(arrayList.get(i).getId());
                }else if(mView.strName.equalsIgnoreCase("item")){
                    fragmentOne.txt_item_name.setText(arrayList.get(i).getName());
                    mView.tblTask.setItemName(arrayList.get(i).getName());
                    mView.tblTask.setItemCode(arrayList.get(i).getId());
                }else if(mView.strName.equalsIgnoreCase("delay")){
                    fragmentOne.txt_delay_arrival.setText(arrayList.get(i).getName());
                    mView.tblTask.setDelayJudgmentTime(arrayList.get(i).getName());
                }
                fragmentTwo.backToFragmentOne();
            }

        });
    }

    @Override
    public ScanFragmentTwoAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_fragment_two, vGroup, false);
        return new ScanFragmentTwoAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name;
        private LinearLayout linear_list;

        public ViewHolder(View v) {
            super(v);
            txt_name = (TextView) v.findViewById(R.id.txt_name);
            linear_list = (LinearLayout) v.findViewById(R.id.linear_list);
        }
    }
}
