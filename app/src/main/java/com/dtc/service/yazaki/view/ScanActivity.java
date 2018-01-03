package com.dtc.service.yazaki.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.fragment.ScanFragmentOne;
import com.dtc.service.yazaki.fragment.ScanFragmentTwo;
import com.dtc.service.yazaki.model.TblIdAndName;
import com.dtc.service.yazaki.model.TblItem;
import com.dtc.service.yazaki.model.TblMember;
import com.dtc.service.yazaki.model.TblPickUpAndDestination;
import com.dtc.service.yazaki.model.TblSetting;
import com.dtc.service.yazaki.model.TblTask;
import com.dtc.service.yazaki.model.TblVehicle;
import com.dtc.service.yazaki.presenters.ScanPresenter;
import com.dtc.service.yazaki.service.ApiService;
import com.dtc.service.yazaki.until.ApplicationController;
import com.dtc.service.yazaki.until.DateController;
import com.dtc.service.yazaki.until.TaskController;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by admin on 11/7/2017 AD.
 */

public class ScanActivity extends FragmentActivity {
    public static TblTask tblTask;
    public static TblMember tblMember;
    public static List<TblIdAndName> tblIdAndName;
    private Intent extra;
    private ApiService apiService;
    private ScanPresenter scanPresenter;
    private static ScanFragmentOne scanFragmentOne;
    public static String strName = "";
    public static List<TblPickUpAndDestination> listPickup;
    public static List<TblPickUpAndDestination> listDestination;
    public static List<TblVehicle> listVehicle;
    public static List<TblItem> listItem;
    public static boolean fragNew = false;
    private TblSetting tblSetting;
    private TaskController taskController;
    private DateController dateController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment2);
        extra = getIntent();
        if (extra != null) {
            if (extra.getSerializableExtra("member") != null)
                tblMember = (TblMember) extra.getSerializableExtra("member");

            if(extra.getStringExtra("fragNew")!= null){
                if(extra.getStringExtra("fragNew").equalsIgnoreCase("new"))
                    fragNew = true;
                else
                    fragNew = false;

            }

            if (extra.getSerializableExtra("task_select") != null) {
                tblTask = (TblTask) extra.getSerializableExtra("task_select");
                if(tblMember != null){
                    tblTask.setUser(tblMember.getUser());
                    tblTask.setProject_code(tblMember.getProject_code());
                }
            }else
                tblTask = new TblTask();

        }
        ApplicationController.setAppActivity(ScanActivity.this);
        scanFragmentOne = new ScanFragmentOne();
        callService("loadData");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void callService(String action){
        try {
            apiService = new ApiService();
            scanPresenter = new ScanPresenter(ScanActivity.this,apiService);
            if(action.equalsIgnoreCase("add")||action.equalsIgnoreCase("check")){
                if(action.equalsIgnoreCase("add")){
                    setDateFormat();
                    tblTask.setType("save");
                }else if(action.equalsIgnoreCase("check")){
                    tblTask.setType("check");
                    tblTask.setPickupPoint((tblTask.getPickupPoint() == null) ? "" : tblTask.getPickupPoint());
                    tblTask.setDistribution((tblTask.getDistribution() == null) ? "" : tblTask.getDistribution());
                    tblTask.setVehicle(((tblTask.getVehicle() == null) ? "" : tblTask.getVehicle()));
                    tblTask.setBussinessPerson(((tblTask.getBussinessPerson() == null) ? "" : tblTask.getBussinessPerson()));
                    tblTask.setItemCode(((tblTask.getItemCode() == null) ? "" : tblTask.getItemCode()));
                    tblTask.setDelayJudgmentTime(((tblTask.getDelayJudgmentTime() == null) ? "" : tblTask.getDelayJudgmentTime()));
                }
                scanPresenter.addTask();
            }else if(action.equalsIgnoreCase("upload")){
                setDateFormat();
                if(fragNew)
                    scanPresenter.addTask();
                else
                    scanPresenter.updateTask();

            }else if(action.equalsIgnoreCase("delete")){
                scanPresenter.deleteTask();
            }else if(action.equalsIgnoreCase("loadData")){
                scanPresenter.loadData();
            }else if(action.equalsIgnoreCase("bussiness_person")){
                scanPresenter.loadBussinessPerson();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setDateFormat(){
        try {
            taskController = new TaskController();
            dateController = new DateController();
            getSetting();
            if(tblSetting.getDateFormat().equalsIgnoreCase("0"))
                tblTask.setDeliveryDateTime(dateController.convertDateFormat10To11(tblTask.getDeliveryDateTime()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getSetting(){
        try {
            List<TblSetting> settingList = taskController.getSetting();
            if(settingList!= null && settingList.size()>0)
                tblSetting = settingList.get(0);
            else
                tblSetting = new TblSetting();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callBack(){
        try {
            getSupportFragmentManager().beginTransaction().add(R.id.contentFragmentMain,new ScanFragmentOne()).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateBussinessPerson(){
        try {
            scanFragmentOne.setTask();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateDataPODup(TblTask task){
        try {
            if(task!=null){
                tblTask = task;
            }
            fragNew = false;
            scanFragmentOne.setTask();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
