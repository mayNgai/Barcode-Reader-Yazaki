package com.dtc.service.yazaki.presenters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.model.TblMember;
import com.dtc.service.yazaki.model.TblSetting;
import com.dtc.service.yazaki.service.ApiService;
import com.dtc.service.yazaki.until.DialogController;
import com.dtc.service.yazaki.until.NetworkUtils;
import com.dtc.service.yazaki.until.TaskController;
import com.dtc.service.yazaki.view.LoginActivity;
import com.dtc.service.yazaki.view.MainActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by May on 11/6/2017.
 */

public class LoginPresenter {
    private ProgressDialog dialog;
    private ApiService mForum;
    private LoginActivity mView;
    private DialogController dialogController;
    private TaskController taskController;
    public LoginPresenter(LoginActivity view,ApiService forum){
        mForum = forum;
        mView = view;
        dialogController = new DialogController();
        taskController = new TaskController();
    }

    public void loadLogin(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNormal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                dialog = ProgressDialog.show(mView, mView.getString(R.string.txtWait), mView.getString(R.string.txt_loading));
                mForum.getApi()
                        .getLogin(mView.member)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<TblMember>>() {
                            @Override
                            public void onCompleted() {
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("userLoginCall Error", e.getMessage());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNext(List<TblMember> member) {
                                updateLogin(member);
                                Log.i("loadLogin","Ok");
//                                dialog.dismiss();
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateLogin(List<TblMember> members){
        try {
            if(members.size()>0){
                members.get(0).setProject_code(mView.member.getProject_code());
                if(taskController.createMember(members.get(0))){
                    List<TblMember> tblMembers = taskController.getAllMember();
                    loadSetting();
                    Log.i("updateLogin","Ok");
                }

            }else {
                dialogController.dialogNormal(mView,mView.getString(R.string.error_incorrect_login),mView.getString(R.string.error_invalid_username));
                Log.i("updateLogin","Fail");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadSetting(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNormal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
//                dialog = ProgressDialog.show(mView, mView.getString(R.string.txtWait), mView.getString(R.string.txt_loading));
                mForum.getApi()
                        .getSetting(mView.member)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<TblSetting>>() {
                            @Override
                            public void onCompleted() {
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("settingCall Error", e.getMessage());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNext(List<TblSetting> setting) {
                                Log.i("loadSetting","Ok");
                                updateSetting(setting);
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateSetting(List<TblSetting> settings){
        try {
            if(settings.size()>0){
//                settings.get(0).setProject_code(mView.member.getProject_code());
                if(taskController.createSetting(settings.get(0))){
                    List<TblSetting> settings1 = taskController.getSetting();
//                    loadSetting();
                    Log.i("updateSetting_","Ok");
                    Intent intent = new Intent(mView,MainActivity.class);
                    mView.startActivity(intent);
                    mView.finish();
                    dialog.dismiss();
                }

            }else {
                dialogController.dialogNormal(mView,mView.getString(R.string.error_incorrect_login),mView.getString(R.string.error_invalid_username));
                Log.i("updateSetting_","Fail");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
