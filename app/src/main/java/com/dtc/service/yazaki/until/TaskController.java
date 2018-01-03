package com.dtc.service.yazaki.until;

import android.app.Activity;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.helper.DatabaseHelper;
import com.dtc.service.yazaki.model.TblIdAndName;
import com.dtc.service.yazaki.model.TblMember;
import com.dtc.service.yazaki.model.TblScan;
import com.dtc.service.yazaki.model.TblSetting;
import com.dtc.service.yazaki.model.TblTask;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 11/9/2017 AD.
 */

public class TaskController {
    private Activity _activity;
    protected static DatabaseHelper databaseHelper = null;
    private RuntimeExceptionDao<TblMember, String> tblMemberRuntimeDao;
    private RuntimeExceptionDao<TblSetting, String> tblSettingRuntimeDao;
    private RuntimeExceptionDao<TblTask, String> tblTaskRuntimeDao;

    private void getConnectDatabaseHelper() {
        try {
            _activity = ApplicationController.getAppActivity();
            databaseHelper  = DatabaseHelper.getHelper(_activity);
            tblMemberRuntimeDao = databaseHelper.getTblMember();
            tblSettingRuntimeDao = databaseHelper.getTblSetting();
            tblTaskRuntimeDao = databaseHelper.getTblTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createMember(TblMember member){
        try {
            getConnectDatabaseHelper();
            List<TblMember> list = new ArrayList<TblMember>();
            list = getAllMember();
            if(list.size()>0){
                deleteMember(list);
            }
            member.setGuid(UUID.randomUUID().toString());
            member.setUser(member.getLogin_id());
            tblMemberRuntimeDao.create(member);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean createSetting(TblSetting setting){  /////////////
        try {
            getConnectDatabaseHelper();
            List<TblSetting> list = new ArrayList<TblSetting>();
            list = getSetting();
            if(list.size()>0){
                deleteSetting(list);
            }
            setting.setGuid(UUID.randomUUID().toString());
//            setting.setUser(member.getLogin_id());
            tblSettingRuntimeDao.create(setting);
//            tblSettingRuntimeDao.update(setting);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteMember(List<TblMember> member){
        try {
            getConnectDatabaseHelper();
            tblMemberRuntimeDao.delete(member);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateSetting(TblSetting settings){ //////////////////////////
        try {
            getConnectDatabaseHelper();
            tblSettingRuntimeDao.update(settings);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteSetting(List<TblSetting> settings){
        try {
            getConnectDatabaseHelper();
            tblSettingRuntimeDao.delete(settings);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<TblMember> getAllMember(){
        List<TblMember> list = new ArrayList<TblMember>();
        try {
            getConnectDatabaseHelper();
            list = tblMemberRuntimeDao.queryForAll();

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<TblSetting> getSetting(){
        List<TblSetting> list = new ArrayList<TblSetting>();
        try {
            getConnectDatabaseHelper();
            list = tblSettingRuntimeDao.queryForAll();

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean createTask(List<TblTask> taskList){
        try {
            getConnectDatabaseHelper();
            List<TblTask> list = new ArrayList<TblTask>();
//            list = checkTask();
//            if(list.size()>0){
//                deleteTask(list);
//
//            }
//            for(TblTask t : taskList){
//                t.setGuid(UUID.randomUUID().toString());
//                tblTaskRuntimeDao.create(t);
//            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<TblIdAndName> getDelay(){
        List<TblIdAndName> itemList = new ArrayList<TblIdAndName>();
        try {
            for(int i = 15; i<=180; i++){
                TblIdAndName tblIdAndName = new TblIdAndName();
                tblIdAndName.setName(String.valueOf(i));
                itemList.add(tblIdAndName);
                i = i + 14;
            }

        }catch (Exception e){
            e.printStackTrace();
            return itemList;
        }
        return itemList;
    }

    public List<TblScan> getDataDetailScan(){
        List<TblScan> scanList = new ArrayList<TblScan>();
        try {
            _activity = ApplicationController.getAppActivity();
            int a = 0;
            for(int i = 0; i<8;i++){
                TblScan tblScan = new TblScan();
                String name = "";
                a = i;
                if(i==0){
                    name = _activity.getString(R.string.txt_p_o);
                }else if(i==1){
                    name = _activity.getString(R.string.txt_pick_up_point);
                }else if(i==2){
                    name = _activity.getString(R.string.txt_destination);
                }else if(i==3){
                    name = _activity.getString(R.string.txt_vehicle);
                }else if(i==4){
                    name = _activity.getString(R.string.txt_tag);
                }else if(i==5){
                    name = _activity.getString(R.string.txt_bussiness_person);
                }else if(i==6){
                    name = _activity.getString(R.string.txt_item_name);
                }else if(i==7){
                    name = _activity.getString(R.string.txt_delivery_date_time);
                }
                tblScan.setName(name);
                tblScan.setValue("");
                scanList.add(tblScan);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return scanList;
    }
}
