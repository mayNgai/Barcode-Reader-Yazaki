package com.dtc.service.yazaki.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.helper.SharedPreference;
import com.dtc.service.yazaki.model.TblMember;
import com.dtc.service.yazaki.presenters.LoginPresenter;
import com.dtc.service.yazaki.service.ApiService;
import com.dtc.service.yazaki.until.ApplicationController;
import com.dtc.service.yazaki.functionsAndVariable.GlobalVariables;
import com.dtc.service.yazaki.functionsAndVariable.GlobalVariables;
import com.dtc.service.yazaki.helper.LanguageHelper;
import com.dtc.service.yazaki.model.TblSetting;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // UI references.
    private EditText edt_username;
    private Button btn_login;
    private CheckBox chk_remember;
    public static TblMember member;
    public static TblSetting setting;
    private ApiService apiService;
    private LoginPresenter loginPresenter;
    private TblSetting defultSetting;
    private boolean checkRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        member = new TblMember();
        init();
        initlistenner();
        setData();
        setCheckRemember();
    }

    private void init(){
        ApplicationController.setAppActivity(LoginActivity.this);
        edt_username = (EditText) findViewById(R.id.edt_username);
        btn_login = (Button) findViewById(R.id.btn_login);
        chk_remember = (CheckBox)findViewById(R.id.chk_remember);
        defultSetting = new TblSetting();
        checkRemember = false;

    }

    private void initlistenner(){
        btn_login.setOnClickListener(this);
    }

    private void setData(){
        try {
            SharedPreference.getPreferences(this);
            checkRemember = SharedPreference.getBooleanValue(this,"remember");
            chk_remember.setChecked(checkRemember);
            if(checkRemember){
                edt_username.setText(SharedPreference.getStringValue(this,"username"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setCheckRemember(){
        chk_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSharedPreference(isChecked);
            }
        });
    }

    private void setSharedPreference(boolean isChecked){
        try {
            SharedPreference.setBooleanValue(LoginActivity.this,"remember",isChecked);
            if(isChecked){
                SharedPreference.setStringValue(LoginActivity.this,"username", edt_username.getText().toString());
            }else {
                SharedPreference.setStringValue(LoginActivity.this,"username", "");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void attemptLogin() {
        String user_name = edt_username.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(user_name)) {
            edt_username.setError(getString(R.string.error_field_required));
            focusView = edt_username;
            cancel = true;
        } else if (!isUserNameValid(user_name)) {
            edt_username.setError(getString(R.string.error_invalid_username));
            focusView = edt_username;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();
        } else {
            member.setUser(user_name);
            member.setProject_code(getResources().getString(R.string.txt_project_code));
            if(chk_remember.isChecked()){
                setSharedPreference(true);
            }else {
                setSharedPreference(false);
            }
            callService();

        }
    }

    private void callService(){
        try {
            apiService =  new ApiService();
            loginPresenter = new LoginPresenter(LoginActivity.this,apiService);
            loginPresenter.loadLogin();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isUserNameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length()>1;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
//                defultSetting.setLanguage("th"); //test
//                defultSetting.setCalendar("buddhist"); //test
//                defultSetting.setdateFormat("dd/mm/yyyy"); //test
//                defaultSettingFromWebService(defultSetting);
                attemptLogin();
                break;
        }
    }

    private void defaultSettingFromWebService(TblSetting tblSetting) {
//        tblSetting.setCalendarOld(tblSetting.getCalendarOld());
        GlobalVariables.tblSetting = tblSetting;
    }
}

