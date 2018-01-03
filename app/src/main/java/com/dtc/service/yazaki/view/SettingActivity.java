package com.dtc.service.yazaki.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.functionsAndVariable.GlobalFunctions;
import com.dtc.service.yazaki.helper.LanguageHelper;
import com.dtc.service.yazaki.model.TblSetting;
import com.dtc.service.yazaki.presenters.SettingPresenter;
import com.dtc.service.yazaki.service.ApiService;
import com.dtc.service.yazaki.until.TaskController;

import java.util.List;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llLanguage, llCalendar, llDateFormat;
    public static TextView tvLanguage, tvCalendar, tvDateFormat;
    private TblSetting tblSetting;
    private String qwerty = "28/05/2560"; //test
    private Toolbar toolbar;
    private ApiService apiService;
    private SettingPresenter settingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
        set();
    }

    private void init() {


        apiService = new ApiService();
        llLanguage = (LinearLayout) findViewById(R.id.llLanguage);
        llLanguage.setOnClickListener(this);
        llCalendar = (LinearLayout) findViewById(R.id.llCalendar);
        llCalendar.setOnClickListener(this);
        llDateFormat = (LinearLayout) findViewById(R.id.llDateFormat);
        llDateFormat.setOnClickListener(this);
        tvLanguage = (TextView) findViewById(R.id.tvLanguage);
        tvCalendar = (TextView) findViewById(R.id.tvCalendar);
        tvDateFormat = (TextView) findViewById(R.id.tvDateFormat);
        tblSetting = new TblSetting();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                // mView.setPre(1);
            }
        });

    }

    private void set() {
        if (getLanguageFromDB().equalsIgnoreCase("th"))
            tvLanguage.setText(R.string.thai);
        else
            tvLanguage.setText(R.string.english);

        if (getCalendar_FromDbStorage().equalsIgnoreCase("christian"))
            tvCalendar.setText(R.string.christian);
        else
            tvCalendar.setText(R.string.buddhist);

        if (getDate_FormatFromDbStorage().equalsIgnoreCase("dd/mm/yyyy"))
            tvDateFormat.setText(R.string.dd);
        else
            tvDateFormat.setText(R.string.yy);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llLanguage:

                final Dialog Setting = new Dialog(this);
                Setting.setTitle(R.string.txtLanguage);
                Setting.setContentView(R.layout.list_language);
                Setting.setCancelable(true);
                LinearLayout langThai = (LinearLayout) Setting.findViewById(R.id.lThai);
                langThai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callService("language", "0");
                        Setting.dismiss();
                    }
                });

                LinearLayout langEng = (LinearLayout) Setting.findViewById(R.id.lEng);
                langEng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callService("language", "1");
                        Setting.dismiss();
                    }
                });
                Setting.show();

                break;

            case R.id.llCalendar:
                final Dialog Setting2 = new Dialog(this);
                Setting2.setTitle(R.string.txtCalendar);
                Setting2.setContentView(R.layout.list_calendar);
                Setting2.setCancelable(true);
                LinearLayout calendarChris = (LinearLayout) Setting2.findViewById(R.id.lChrist);
                calendarChris.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callService("calendar", "0");
                        Setting2.dismiss();
                    }
                });

                LinearLayout calendarBud = (LinearLayout) Setting2.findViewById(R.id.lBud);
                calendarBud.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callService("calendar", "1");
                        Setting2.dismiss();
                    }
                });
                Setting2.show();
                break;

            case R.id.llDateFormat:

                final Dialog Setting3 = new Dialog(this);
                Setting3.setTitle(R.string.txtDateFormat);
                Setting3.setContentView(R.layout.list_date_format);
                Setting3.setCancelable(true);
                LinearLayout dateFormatDD = (LinearLayout) Setting3.findViewById(R.id.lDD);
                dateFormatDD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callService("date_format", "0");
                        Setting3.dismiss();
                    }
                });

                LinearLayout dateFormatYY = (LinearLayout) Setting3.findViewById(R.id.lYY);
                dateFormatYY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callService("date_format", "1");
                        tvDateFormat.setText(R.string.yy);
                        Setting3.dismiss();
                    }
                });
                Setting3.show();
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.setLocale(base, getLanguageFromDB()));
    }

    @Override
    public void onBackPressed() {
        Intent j = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(j);
    }

    private String getLanguageFromDB() {
        TaskController taskController = new TaskController();
        String lang;
        if (taskController != null) {
            List<TblSetting> tblSettings = taskController.getSetting();
//            Log.i("lang", tblSettings.get(0).getLanguage());
            lang = GlobalFunctions.convertLanguage(tblSettings.get(0).getLanguage());
        } else {
            lang = "en";
        }
        return lang;
    }

    public String getDate_FormatFromDbStorage() {
        TaskController taskController = new TaskController();
        String date_format;
        if (taskController != null) {
            List<TblSetting> tblSettings = taskController.getSetting();
            date_format = GlobalFunctions.convertDateFormat(tblSettings.get(0).getDateFormat());
        } else {
            date_format = "dd/mm/yyyy";
        }
        return date_format;
    }

    public String getCalendar_FromDbStorage() {
        TaskController taskController = new TaskController();
        String calendar;
        if (taskController != null) {
            List<TblSetting> tblSettings = taskController.getSetting();
            calendar = GlobalFunctions.convertCalendar(tblSettings.get(0).getCalendar());
        } else {
            calendar = "christian";
        }
        return calendar;
    }

    private void callService(String type, String value) {
        try {
            apiService = new ApiService();
            settingPresenter = new SettingPresenter(SettingActivity.this, apiService);
            settingPresenter.settingUpdate(type, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
