package com.dtc.service.yazaki.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.adapter.ScanFragmentTwoAdapter;
import com.dtc.service.yazaki.model.TblIdAndName;
import com.dtc.service.yazaki.model.TblItem;
import com.dtc.service.yazaki.model.TblPickUpAndDestination;
import com.dtc.service.yazaki.model.TblVehicle;
import com.dtc.service.yazaki.until.ApplicationController;
import com.dtc.service.yazaki.until.TaskController;
import com.dtc.service.yazaki.view.ScanActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 11/7/2017 AD.
 */

public class ScanFragmentTwo extends Fragment {
    private View rootView;
    private static Toolbar toolbar;
    private static ScanActivity mView;
    private static RecyclerView recycler_view;
    private List<TblIdAndName> nameList;
    private static Activity activity;
    private TaskController taskController;

    public static ScanFragmentTwo newInstance() {
        ScanFragmentTwo fragment = new ScanFragmentTwo();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_scan_two, container, false);
        init();
        setData();
        return rootView;
    }

    private void init(){
        try {
            taskController = new TaskController();
            mView = new ScanActivity();
            activity = mView;
            recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            recycler_view.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recycler_view.setLayoutManager(mLayoutManager);
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            toolbar.setTitle(mView.strName.toUpperCase());
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setData(){
        nameList = new ArrayList<TblIdAndName>();
        if(mView.strName.equalsIgnoreCase("pickup")){
            List<TblPickUpAndDestination> listPickup = mView.listPickup;
            if(listPickup!=null){
                for(TblPickUpAndDestination p : listPickup){
                    TblIdAndName tblIdAndName = new TblIdAndName();
                    tblIdAndName.setId(p.getDistributionPlaceCode());
                    tblIdAndName.setName(p.getDistributionPlaceName());
                    nameList.add(tblIdAndName);
                }
            }else {
                nameList = new ArrayList<TblIdAndName>();
            }

        }else if(mView.strName.equalsIgnoreCase("destination")){
            List<TblPickUpAndDestination> listDestination = mView.listDestination;
            if(listDestination!=null){
                for(TblPickUpAndDestination p : listDestination){
                    TblIdAndName tblIdAndName = new TblIdAndName();
                    tblIdAndName.setId(p.getDistributionPlaceCode());
                    tblIdAndName.setName(p.getDistributionPlaceName());
                    nameList.add(tblIdAndName);
                }
            }else {
                nameList = new ArrayList<TblIdAndName>();
            }

        }else if(mView.strName.equalsIgnoreCase("vehicle")){
            List<TblVehicle> listPickup = mView.listVehicle;
            if(listPickup!=null){
                for(TblVehicle v : listPickup){
                    TblIdAndName tblIdAndName = new TblIdAndName();
                    tblIdAndName.setId(v.getVehicleCode());
                    tblIdAndName.setName(v.getVehicleName());
                    nameList.add(tblIdAndName);
                }
            }else {
                nameList = new ArrayList<TblIdAndName>();
            }
        }else if(mView.strName.equalsIgnoreCase("item")){
            List<TblItem> listPickup = mView.listItem;
            if(listPickup!=null){
                for(TblItem i : listPickup){
                    TblIdAndName tblIdAndName = new TblIdAndName();
                    tblIdAndName.setId(i.getItemCode());
                    tblIdAndName.setName(i.getItemName());
                    nameList.add(tblIdAndName);
                }
            }else {
                nameList = new ArrayList<TblIdAndName>();
            }
        }else if(mView.strName.equalsIgnoreCase("delay")){
            nameList = taskController.getDelay();
        }
//        nameList = mView.tblIdAndName;
        setAdapter();
    }

    private void setAdapter(){
        try {
            ScanFragmentTwoAdapter adapter = new ScanFragmentTwoAdapter(getActivity(),nameList);
            recycler_view.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void backToFragmentOne(){
        try {
            ApplicationController.getAppActivity().onBackPressed();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
