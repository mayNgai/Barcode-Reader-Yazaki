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
import com.dtc.service.yazaki.model.TblScan;
import com.dtc.service.yazaki.model.TblTask;
import com.dtc.service.yazaki.view.ScanActivity;

import java.util.List;

/**
 * Created by admin on 11/9/2017 AD.
 */

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ViewHolder>{
    private static Context mcontext;
    private List<TblScan> arrayList;
    private ScanFragmentOne mView;

    public ScanAdapter(Context context, List<TblScan> _arrayList){
        this.arrayList = _arrayList;
        this.mcontext = context;
        mView = new ScanFragmentOne();
    }

    @Override
    public void onBindViewHolder(final ScanAdapter.ViewHolder holder, final int i) {
        holder.txt_name.setText(arrayList.get(i).getName());
        holder.txt_value.setText(arrayList.get(i).getValue());
        holder.linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.addFragment();
                //mView.setNext(1);
            }
        });
    }

    @Override
    public ScanAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_data_scan, vGroup, false);
        return new ScanAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name,txt_value;
        private LinearLayout linear_list;

        public ViewHolder(View v) {
            super(v);
            txt_name = (TextView) v.findViewById(R.id.txt_name);
            txt_value = (TextView) v.findViewById(R.id.txt_value);
            linear_list = (LinearLayout) v.findViewById(R.id.linear_list);
        }
    }
}
