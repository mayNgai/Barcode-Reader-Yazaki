package com.dtc.service.yazaki.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.functionsAndVariable.GlobalFunctions;
import com.dtc.service.yazaki.functionsAndVariable.GlobalVariables;
import com.dtc.service.yazaki.helper.DatabaseHelper;
import com.dtc.service.yazaki.model.TblMember;
import com.dtc.service.yazaki.model.TblSetting;
import com.dtc.service.yazaki.until.ApplicationController;
import com.dtc.service.yazaki.until.TaskController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by May on 11/7/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private Handler handler;
    private TextView textView;
    private long startTime, currentTime, finishedTime = 0L;
    private int duration = 6000 / 4;
    private int endTime = 0;
    private Activity _activity;
    private DatabaseHelper databaseHelper = null;
    private TaskController taskController;
    private List<TblMember> members;
    private TblSetting defultSetting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ApplicationController.setAppActivity(_activity);

        if (GlobalFunctions.subFullPermission(SplashActivity.this, SplashActivity.this)) {
            countdownStart();
        }
    }

    private void countdownStart() {
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(R.string.app_name);
        _activity = SplashActivity.this;
        taskController = new TaskController();
        defultSetting = new TblSetting();
        ApplicationController.setAppActivity(_activity);
        databaseHelper = DatabaseHelper.getHelper(_activity);
        ApplicationController.setAppActivity(_activity);
        handler = new Handler();
        startTime = Long.valueOf(System.currentTimeMillis());
        currentTime = startTime;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                currentTime = Long.valueOf(System.currentTimeMillis());
                finishedTime = Long.valueOf(currentTime) - Long.valueOf(startTime);

                if (finishedTime >= duration + 30) {
                    members = taskController.getAllMember();
                    if (members.size() > 0) {
                        defultSetting.setLanguage("th"); //test
                        defultSetting.setCalendar("buddhist"); //test
//                        defultSetting.setdateFormat("dd/mm/yyyy"); //test
//                        defaultSettingFromWebService(defultSetting);
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    endTime = (int) (finishedTime / 250);
                    Spannable spannableString = new SpannableString(textView
                            .getText());
                    spannableString.setSpan(new ForegroundColorSpan(
                                    Color.BLACK), 0, endTime,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    textView.setText(spannableString);
                    handler.postDelayed(this, 10);
                }
            }
        }, 10);
    }

    private void defaultSettingFromWebService(TblSetting tblSetting) {
//        tblSetting.setCalendarOld(tblSetting.getCalendarOld());
//        GlobalVariables.tblSetting = tblSetting;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (GlobalFunctions.CheckFullPermission(requestCode, permissions, grantResults, SplashActivity.this, SplashActivity.this)) {
            countdownStart();
        }
    }

}
