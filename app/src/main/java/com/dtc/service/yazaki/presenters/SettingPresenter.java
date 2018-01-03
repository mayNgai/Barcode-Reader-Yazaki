package com.dtc.service.yazaki.presenters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.model.TblSetting;
import com.dtc.service.yazaki.model.TblTask;
import com.dtc.service.yazaki.service.ApiService;
import com.dtc.service.yazaki.until.DialogController;
import com.dtc.service.yazaki.until.NetworkUtils;
import com.dtc.service.yazaki.until.TaskController;
import com.dtc.service.yazaki.view.MainActivity;
import com.dtc.service.yazaki.view.SettingActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Koh on 21-Nov-17.
 */

public class SettingPresenter {

    private ProgressDialog dialog;
    private ApiService mForum;
    private SettingActivity mView;
    private DialogController dialogController;
    TblSetting settings = new TblSetting();
    private ApiService apiService;

    public SettingPresenter(SettingActivity view, ApiService forum) {
        mForum = forum;
        mView = view;
        dialogController = new DialogController();
    }

    public void settingUpdate(final String typeUpdate, final String value) {
        try {
            if (!NetworkUtils.isConnected(mView)) {
                dialogController.dialogNormal(mView, mView.getString(R.string.txtWarning), mView.getString(R.string.txt_internet_is_not));
            } else {
                dialog = ProgressDialog.show(mView, mView.getString(R.string.txtWait), mView.getString(R.string.txt_loading));
                settings = new TaskController().getSetting().get(0);
                settings.setUser(new TaskController().getAllMember().get(0).getUser());
                if (typeUpdate.equalsIgnoreCase("language"))
                    settings.setLanguage(value);
                else if (typeUpdate.equalsIgnoreCase("calendar"))
                    settings.setCalendar(value);
                else if (typeUpdate.equalsIgnoreCase("date_format"))
                    settings.setDateFormat(value);

                mForum.getApi()
                        .updateSetting(settings)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblSetting>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadTask Error", e.getMessage());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblSetting settingList) {
                                Log.i("loadTaskxxx", "Ok");
//                                if (settingList.getSuccess().equalsIgnoreCase("true"))
                                if (settingList.getSuccess().equalsIgnoreCase("true"))
                                    updateSetting(settings, typeUpdate, value);
                            }
                        });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSetting(TblSetting settings, String type, String value) {
        try {
            if (settings != null) {
                TaskController taskController = new TaskController();
                TblSetting tblSetting = new TblSetting();

                if (type.equalsIgnoreCase("language")) {
                    settings.setLanguage(value);
                } else if (type.equalsIgnoreCase("calendar")) {
                    settings.setCalendar(value);
                } else if (type.equalsIgnoreCase("date_format")) {
                    settings.setDateFormat(value);
                }

                if (taskController.updateSetting(settings)) {
                    Log.i("updateSetting_2", "Ok");
                    Intent intent = new Intent(mView, SettingActivity.class);
                    mView.startActivity(intent);
                    mView.finish();
                    dialog.dismiss();
                }

            } else {
                dialogController.dialogNormal(mView, mView.getString(R.string.error_incorrect_login), mView.getString(R.string.error_invalid_username));
                Log.i("updateSetting_", "Fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
