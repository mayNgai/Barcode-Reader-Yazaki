package com.dtc.service.yazaki.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.functionsAndVariable.GlobalVariables;
import com.dtc.service.yazaki.model.TblSetting;
import com.dtc.service.yazaki.until.TaskController;

/**
 * Created by admin on 11/7/2017 AD.
 */

public class BaseActivity extends AppCompatActivity {

    private boolean flag_open = false;
    private Dialog mBottomSheetDialog;
    private LinearLayout linear_list;
    private TblSetting setting;
    private TaskController taskController;
    static int werawat = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        init();
    }

    private void init(){
        try {
            taskController = new TaskController();
            setting = new TblSetting();
            linear_list = (LinearLayout)findViewById(R.id.linear_list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.info:
                setDialogInfo();
                return true;
            case R.id.setting:
                Intent i = new Intent(this, SettingActivity.class);
//                setting.setLanguage("en");
//                setting.setCalendar("christian");
//                setting.setdateFormat("dd/mm/yyyy");
//                GlobalVariables.arSetting.add(setting);
                startActivity(i);
                return true;
            case R.id.logout:
                setLogout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDialogInfo(){
        try {
            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.dialog_info, null);
            TextView txt_version = (TextView)view.findViewById( R.id.txt_version);
            txt_version.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    werawat = werawat + 1;
                    if(werawat == 27){

                        werawat = 0;
                        Dialog dialog = new Dialog(getApplicationContext());
                        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        dialog.setContentView(R.layout.imdev);
                        dialog.setCancelable(true);
                        dialog.setTitle("DEV. KOH AND DEV. MAY");
                        dialog.show();
                    }
                }
            });
            TextView txt_project = (TextView)view.findViewById( R.id.txt_project);
            Dialog mBottomSheetDialog = new Dialog(this,R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (true);
            PackageManager manager = BaseActivity.this.getPackageManager();
            PackageInfo info = null;
            info = manager.getPackageInfo(BaseActivity.this.getPackageName(), 0);
            String version = info.versionName;
            String project_call_center = getResources().getString(R.string.txt_project_code) + " " + getResources().getString(R.string.txt_call_center);
            txt_project.setText(project_call_center);
            txt_version.setText("Y_"+"\u03B2"+"_"+version);
            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
            mBottomSheetDialog.show ();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setLogout(){
        try {
            if(taskController.deleteMember(taskController.getAllMember())){
                Intent intent = new Intent(BaseActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
