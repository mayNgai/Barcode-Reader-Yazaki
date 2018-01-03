package com.dtc.service.yazaki.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.model.TblMember;
import com.dtc.service.yazaki.model.TblTask;
import com.dtc.service.yazaki.service.ApiService;
import com.dtc.service.yazaki.until.ApplicationController;
import com.dtc.service.yazaki.until.DialogController;
import com.dtc.service.yazaki.until.NetworkUtils;
import com.dtc.service.yazaki.view.MainActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 11/8/2017 AD.
 */

public class MainPresenter {
    private ProgressDialog dialog;
    private ApiService mForum;
    private MainActivity mView;
    private DialogController dialogController;
    private Activity activity;
    public MainPresenter(MainActivity view,ApiService forum){
        mForum = forum;
        mView = view;
        dialogController = new DialogController();
        activity = ApplicationController.getAppActivity();
    }

    public void loadTasks(){
        try {
            if(!NetworkUtils.isConnected(activity)){
                dialogController.dialogNormal(activity,mView.getResources().getString(R.string.txtWarning),mView.getResources().getString(R.string.txt_internet_is_not));
                mView.stopSwipe();
            }else {
                dialog = ProgressDialog.show(activity, mView.getResources().getString(R.string.txtWait), mView.getResources().getString(R.string.txt_loading));
                mForum.getApi()
                        .getTasks(mView.member)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<TblTask>>() {
                            @Override
                            public void onCompleted() {
                                if(dialog!=null)
                                    dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadTask Error", e.getMessage());
                                if(dialog!=null)
                                    dialog.dismiss();
                            }

                            @Override
                            public void onNext(List<TblTask> taskList) {
                                updateTask(taskList);
                                Log.i("loadTask","Ok");
                                if(dialog!=null)
                                    dialog.dismiss();
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
            mView.stopSwipe();
        }
    }

    public void updateTask(List<TblTask> taskList){
        try {
            mView.updateTask(taskList);
            Log.i("updateTask","Ok");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadBussinessPerson(){
        try {
            if(!NetworkUtils.isConnected(activity)){
                dialogController.dialogNormal(activity,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));

            }else {
                //dialog = ProgressDialog.show(activity, mView.getString(R.string.txtWait), mView.getString(R.string.txt_loading));
                mForum.getApi()
                        .getBussinessPerson(mView.tblTask)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<TblTask>>() {
                            @Override
                            public void onCompleted() {
                                if(dialog!=null)
                                    dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadTask Error", e.getMessage());
                                if(dialog!=null)
                                    dialog.dismiss();
                            }

                            @Override
                            public void onNext(List<TblTask> taskList) {
                                mView.setNextScanPage(taskList);
                                Log.i("loadTask","Ok");
                                if(dialog!=null)
                                    dialog.dismiss();
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void checkPO(){
        try {
            if(!NetworkUtils.isConnected(activity)){
                dialogController.dialogClickOkClose(activity,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                //dialog = ProgressDialog.show(activity, mView.getString(R.string.txtWait), mView.getString(R.string.txt_loading));
                mForum.getApi()
                        .addTask(mView.tblTask)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblTask>() {
                            @Override
                            public void onCompleted() {
                                if(dialog != null)
                                    dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("addTask Error", e.getMessage());
                                if(dialog != null)
                                    dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblTask taskList) {
                                if(mView.tblTask.getType().equalsIgnoreCase("check")){
                                    if(taskList.getSuccess().equalsIgnoreCase("true")){
                                        loadBussinessPerson();
                                    }else if(taskList.getSuccess().equalsIgnoreCase("false")){
                                        dialogClickOkAndCancel(activity,activity.getString(R.string.txtWarning),taskList.getMessage() + " " + activity.getString(R.string.txt_want_to_update),taskList);
                                    }
                                }

                                if(dialog != null)
                                    dialog.dismiss();
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dialogClickOkAndCancel(final Context _context, String title, String messes,final TblTask task){
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(messes);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //mView.updateDataPODup(task);
                        }
                    });
            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
