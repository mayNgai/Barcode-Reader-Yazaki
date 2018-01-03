package com.dtc.service.yazaki.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.model.TblIdAndName;
import com.dtc.service.yazaki.model.TblItem;
import com.dtc.service.yazaki.model.TblPickUpAndDestination;
import com.dtc.service.yazaki.model.TblTask;
import com.dtc.service.yazaki.model.TblVehicle;
import com.dtc.service.yazaki.service.ApiService;
import com.dtc.service.yazaki.until.ApplicationController;
import com.dtc.service.yazaki.until.DialogController;
import com.dtc.service.yazaki.until.NetworkUtils;
import com.dtc.service.yazaki.view.MainActivity;
import com.dtc.service.yazaki.view.ScanActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 11/17/2017 AD.
 */

public class ScanPresenter {
    private ProgressDialog dialog;
    private ApiService mForum;
    private ScanActivity mView;
    private DialogController dialogController;
    private static Activity activity;
    public ScanPresenter(ScanActivity view,ApiService forum){
        mForum = forum;
        mView = view;
        dialogController = new DialogController();
        activity = ApplicationController.getAppActivity();
    }

    public void loadData(){
        loadPickup();
    }

    public void loadPickup(){
        try {
            if(!NetworkUtils.isConnected(activity)){
                dialogController.dialogClickOkClose(activity,activity.getString(R.string.txtWarning),activity.getString(R.string.txt_internet_is_not));
            }else {
                dialog = ProgressDialog.show(activity, activity.getString(R.string.txtWait), activity.getString(R.string.txt_loading));
                mForum.getApi()
                        .getPickup(mView.tblMember)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<TblPickUpAndDestination>>() {
                            @Override
                            public void onCompleted() {
                                if(dialog != null)
                                    dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadPickup Error", e.getMessage());
                                if(dialog != null)
                                    dialog.dismiss();
                                dialogController.dialogClickOkClose(activity,activity.getString(R.string.txtWarning),activity.getString(R.string.txt_not_connecte_to_server));
                            }

                            @Override
                            public void onNext(List<TblPickUpAndDestination> taskList) {
                                mView.listPickup = taskList;
                                Log.i("loadPickup","Ok");
                                loadDestination();
                                //dialog.dismiss();
                            }
                        });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadDestination(){
        try {
            mForum.getApi()
                    .getDestination(mView.tblMember)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<TblPickUpAndDestination>>() {
                        @Override
                        public void onCompleted() {
                            if(dialog != null)
                                dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("loadDestination Error", e.getMessage());
                            if(dialog != null)
                                dialog.dismiss();
                            dialogController.dialogClickOkClose(activity,activity.getString(R.string.txtWarning),activity.getString(R.string.txt_not_connecte_to_server));
                        }

                        @Override
                        public void onNext(List<TblPickUpAndDestination> taskList) {
                            mView.listDestination = taskList;
                            Log.i("loadDestination","Ok");
                            loadVehicle();
                        }
                    });
//           }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadVehicle(){
        try {
            mForum.getApi()
                    .getVehicle(mView.tblMember)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<TblVehicle>>() {
                        @Override
                        public void onCompleted() {
                            if(dialog != null)
                                dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("loadVehicle Error", e.getMessage());
                            if(dialog != null)
                                dialog.dismiss();
                            dialogController.dialogClickOkClose(activity,activity.getString(R.string.txtWarning),activity.getString(R.string.txt_not_connecte_to_server));
                        }

                        @Override
                        public void onNext(List<TblVehicle> taskList) {
                            mView.listVehicle = taskList;
                            Log.i("loadVehicle","Ok");
                            loadItem();
                        }
                    });
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadItem(){
        try {
            mForum.getApi()
                    .getItem(mView.tblMember)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<TblItem>>() {
                        @Override
                        public void onCompleted() {
                            if(dialog != null)
                                dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("loadItem Error", e.getMessage());
                            if(dialog != null)
                                dialog.dismiss();
                            dialogController.dialogClickOkClose(activity,activity.getString(R.string.txtWarning),activity.getString(R.string.txt_not_connecte_to_server));
                        }

                        @Override
                        public void onNext(List<TblItem> taskList) {
                            mView.listItem = taskList;
                            Log.i("loadItem","Ok");
                            mView.callBack();
                            if(dialog != null)
                                dialog.dismiss();
                        }
                    });
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addTask(){
        try {
            if(!NetworkUtils.isConnected(activity)){
                dialogController.dialogClickOkClose(activity,activity.getString(R.string.txtWarning),activity.getString(R.string.txt_internet_is_not));
            }else {
                dialog = ProgressDialog.show(activity, activity.getString(R.string.txtWait), activity.getString(R.string.txt_loading));
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
                                if(mView.tblTask.getType().equalsIgnoreCase("save")){
                                    if(taskList.getSuccess().equalsIgnoreCase("true")){
                                        Log.i("addTask","Ok");
                                        Intent i = new Intent(activity, MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        activity.startActivity(i);
                                        activity.finish();
                                    }else if(taskList.getSuccess().equalsIgnoreCase("false")){
                                        if(taskList.getMessage().equalsIgnoreCase("Data Error")){
                                            dialogController.dialogNormal(activity,activity.getString(R.string.txtWarning),taskList.getMessage());
                                        }else {
                                            dialogClickOkAndCancel(activity,activity.getString(R.string.txtWarning),taskList.getMessage() + " " + activity.getString(R.string.txt_want_to_update),"add");
                                        }

                                    }
                                }else if(mView.tblTask.getType().equalsIgnoreCase("check")){
                                    if(taskList.getSuccess().equalsIgnoreCase("true")){
                                        mView.tblTask.setType("save");
                                        loadBussinessPerson();
                                    }else if(taskList.getSuccess().equalsIgnoreCase("false")){
                                        dialogClickOkAndCancelDup(activity,activity.getString(R.string.txtWarning),taskList.getMessage() + " " + activity.getString(R.string.txt_want_to_update),taskList);
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

    public void updateTask(){
        try {
            if(!NetworkUtils.isConnected(activity)){
                dialogController.dialogClickOkClose(activity,activity.getString(R.string.txtWarning),activity.getString(R.string.txt_internet_is_not));
            }else {
                dialog = ProgressDialog.show(activity, activity.getString(R.string.txtWait), activity.getString(R.string.txt_loading));
                mForum.getApi()
                        .updateTask(mView.tblTask)//mView.tblTask
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
                                Log.e("updateTask Error", e.getMessage());
                                if(dialog != null)
                                    dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblTask taskList) {
                                if(taskList.getSuccess().equalsIgnoreCase("true")){
                                    Log.i("updateTask","Ok");
                                    Intent i = new Intent(activity, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    activity.startActivity(i);
                                    activity.finish();
                                }else if(taskList.getSuccess().equalsIgnoreCase("false")){
                                    dialogController.dialogNormal(activity,activity.getString(R.string.txtWarning),activity.getString(R.string.txt_cannot_update));
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

    public void deleteTask(){
        try {
            if(!NetworkUtils.isConnected(activity)){
                dialogController.dialogClickOkClose(activity,activity.getString(R.string.txtWarning),activity.getString(R.string.txt_internet_is_not));
            }else {
                dialog = ProgressDialog.show(activity, activity.getString(R.string.txtWait), activity.getString(R.string.txt_loading));
                mForum.getApi()
                        .deleteTask(mView.tblTask)//mView.tblTask
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
                                Log.e("deleteTask Error", e.getMessage());
                                if(dialog != null)
                                    dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblTask taskList) {
                                if(taskList.getSuccess().equalsIgnoreCase("true")){
                                    Log.i("deleteTask","Ok");
                                    Intent i = new Intent(activity, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    activity.startActivity(i);
                                    activity.finish();
                                }else if(taskList.getSuccess().equalsIgnoreCase("false")){
                                    dialogController.dialogNormal(activity,activity.getString(R.string.txtWarning),taskList.getMessage());
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

    public void loadBussinessPerson(){
        try {
            if(!NetworkUtils.isConnected(activity)){
                dialogController.dialogNormal(activity,activity.getString(R.string.txtWarning),activity.getString(R.string.txt_internet_is_not));

            }else {
                dialog = ProgressDialog.show(activity, activity.getString(R.string.txtWait), activity.getString(R.string.txt_loading));
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
                                if(taskList.size()>0){
                                    mView.tblTask.setBussinessPerson(taskList.get(0).getBussinessPerson());
                                }else {
                                    mView.tblTask.setBussinessPerson("");
                                }
                                mView.updateBussinessPerson();
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

    public void dialogClickOkAndCancelDup(final Context _context, String title, String messes,final TblTask task){
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(messes);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            mView.fragNew = false;
                            //mView.updateDataPODup(task);
                        }
                    });
            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(activity, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(i);
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dialogClickOkAndCancel(final Context _context, String title, String messes, final String action){
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(messes);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            if(action.equalsIgnoreCase("add")){
                                updateTask();
                            }

                        }
                    });
            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(activity, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(i);
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
