package com.dtc.service.yazaki.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.app.Dialog;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.adapter.MainAdapter;
import com.dtc.service.yazaki.functionsAndVariable.GlobalFunctions;
import com.dtc.service.yazaki.model.TblMember;
import com.dtc.service.yazaki.functionsAndVariable.GlobalVariables;
import com.dtc.service.yazaki.helper.LanguageHelper;
import com.dtc.service.yazaki.model.TblScan;
import com.dtc.service.yazaki.model.TblSetting;
import com.dtc.service.yazaki.model.TblTask;
import com.dtc.service.yazaki.presenters.MainPresenter;
import com.dtc.service.yazaki.service.ApiService;
import com.dtc.service.yazaki.until.ApplicationController;
import com.dtc.service.yazaki.until.DateController;
import com.dtc.service.yazaki.until.TaskController;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by May on 11/6/2017.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private TextView edt_date;
    private ImageView img_search;
    private TextView txt_not_found;
    private LinearLayout linear_recycler_view;
    private static RecyclerView recycler_view;
    private static SwipeRefreshLayout swipe_refresh;

    private DateController dateController;
    private Activity _activity;
    private List<TblTask> taskList;
    public static TblMember member;
    private ArrayList<TblSetting> tblSetting;
    private String setting;
    private String savePO = "", saveTag = "";
    private Boolean poBoo;
    private Dialog Setting;
    private TblScan TblScanCamera;
    public static List<TblScan> lsTblScanCamera;
    public static TaskController taskController;
    private Camera camera;
    public TblTask tblTask;

    private ApiService apiService;
    private MainPresenter mainPresenter;
//    private Button camera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _activity = MainActivity.this;
        member = new TblMember();
        ApplicationController.setAppActivity(_activity);
        dateController = new DateController();
        taskList = new ArrayList<TblTask>();
        setting = getIntent().getStringExtra("setting");
        init();
        initlistenner();
        setData();
    }

    private void init() {
        try {
            TblScanCamera = new TblScan();
            lsTblScanCamera = new ArrayList<TblScan>();
            taskController = new TaskController();
            tblTask = new TblTask();
            Setting = new Dialog(this);
            linear_recycler_view = (LinearLayout) findViewById(R.id.linear_recycler_view);
            edt_date = (TextView) findViewById(R.id.edt_date);
            img_search = (ImageView) findViewById(R.id.img_search);
            txt_not_found = (TextView) findViewById(R.id.txt_not_found);
            swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
            recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
            img_search = (ImageView) findViewById(R.id.img_search);
            recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
            recycler_view.setHasFixedSize(true);
            tblSetting = new ArrayList<TblSetting>();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLayoutManager);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initlistenner() {
        try {
            img_search.setOnClickListener(this);
            swipe_refresh.setOnRefreshListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMember() {
        try {
            List<TblMember> members = new ArrayList<TblMember>();
            members = taskController.getAllMember();
            member = members.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        try {
            getMember();
            edt_date.setText(GlobalFunctions.ChangeCalendarFormat(getCalendar_FromDbStorage(),
                    GlobalFunctions.ChangeDateFormate(getDate_FormatFromDbStorage(),
                            dateController.convertDateFormat2To1(dateController.getSystemDate(_activity)).substring(0,
                                    dateController.convertDateFormat2To1(dateController.getSystemDate(_activity)).length()))));

//            member.setDelivery_date("2017-10-17");
            member.setDelivery_date(dateController.convertDateFormat1To2(dateController.convertDateFormat2To1(dateController.getSystemDate(_activity))));
            getTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopSwipe() {
        try {
            swipe_refresh.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        try {
            MainAdapter adapter = new MainAdapter(this, taskList);
            recycler_view.setAdapter(adapter);
            stopSwipe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDatePicker(final TextView editText) {
        //String txtDate = dateController.convertDateFormat2To1(editText.getText().toString());
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String aa = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

                if (monthOfYear + 1 < 10 || dayOfMonth < 10) {
                    if (monthOfYear + 1 < 10)
                        aa = year + "/0" + (monthOfYear + 1) + "/" + dayOfMonth;
                    if (dayOfMonth < 10)
                        aa = year + "/" + (monthOfYear + 1) + "/0" + dayOfMonth;
                    if (monthOfYear + 1 < 10 && dayOfMonth < 10)
                        aa = year + "/0" + (monthOfYear + 1) + "/0" + dayOfMonth;
                }

                editText.setText(GlobalFunctions.ChangeCalendarFormat(getCalendar_FromDbStorage(),
                        GlobalFunctions.ChangeDateFormate(getDate_FormatFromDbStorage(),
                                aa.substring(0, aa.length()))));

//                editText.setText(dateController.convertDateFormat2To1(aa));

                String aaa = aa.replace("/","-");

                member.setDelivery_date(aaa);
                getTask();
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void getTask() {
        try {
            apiService = new ApiService();
            mainPresenter = new MainPresenter(MainActivity.this, apiService);
            mainPresenter.loadTasks();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTask(List<TblTask> taskLists) {
        try {
            taskList = taskLists;
            if (taskLists.size() > 0) {
                txt_not_found.setVisibility(View.GONE);
                linear_recycler_view.setVisibility(View.VISIBLE);
                setAdapter();
            } else {
                txt_not_found.setVisibility(View.VISIBLE);
                linear_recycler_view.setVisibility(View.GONE);
                stopSwipe();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void camera(View view) {
        savePO = "";
        poBoo = true;
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan P.O.");
        integrator.setBeepEnabled(true);
//        integrator.setCaptureActivity(CustomScanner.class);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();

//        Camera.Parameters p;
//        p = camera.getParameters();
//        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//        camera.setParameters(p);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_search:
                setDatePicker(edt_date);
                break;
        }
    }

    @Override
    public void onRefresh() {
        getTask();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.setLocale(base, getLanguageFromDB()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Setting.setTitle(R.string.txtResult);
        Setting.setContentView(R.layout.dialog_next_scan);
        Setting.setCancelable(false);
        final TextView po = (TextView) Setting.findViewById(R.id.poNumber);
        final TextView tag = (TextView) Setting.findViewById(R.id.tagNumber);
        FloatingActionButton scanPO = (FloatingActionButton) Setting.findViewById(R.id.floatingActionButtonScanPO);
        FloatingActionButton scanTaf = (FloatingActionButton) Setting.findViewById(R.id.floatingActionButtonScanTag);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

                Setting.dismiss();

                if (poBoo)
                    savePO = result.getContents();
                else
                    saveTag = result.getContents();

                scanPO.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                        integrator.setPrompt("Scan P.O.");
                        integrator.setOrientationLocked(false);
                        integrator.setBeepEnabled(true);
//                        integrator.setCaptureActivity(CustomScanner.class);
                        integrator.initiateScan();
                        poBoo = true;
                    }
                });

                scanTaf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                        integrator.setPrompt("Scan Tag");
                        integrator.setOrientationLocked(false);
                        integrator.setBeepEnabled(true);
//                        integrator.setCaptureActivity(CustomScanner.class);
                        integrator.initiateScan();
                        poBoo = false;
                    }
                });

                Button confirm = (Button) Setting.findViewById(R.id.confirm);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean bTag = false, bPO = false;
                        if (saveTag != null) {
                            if (saveTag.equalsIgnoreCase("") || saveTag.equalsIgnoreCase(getResources().getString(R.string.necessary))) {
                                tag.setText(R.string.necessary);
                                tag.setTextColor(getResources().getColor(R.color.colorPrimary));
                            } else {
                                bTag = true;
                            }
                        } else {
                            tag.setText(R.string.necessary);
                            tag.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }


                        if (savePO != null) {
                            if (savePO.equalsIgnoreCase("") || savePO.equalsIgnoreCase(getResources().getString(R.string.necessary))) {
                                po.setText(R.string.necessary);
                                po.setTextColor(getResources().getColor(R.color.colorPrimary));
                            } else {
                                bPO = true;
                            }
                        } else {
                            po.setText(R.string.necessary);
                            po.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }

                        if (bTag && bPO) {
                            lsTblScanCamera.clear();
                            TblScanCamera.setName("P.O.");
                            TblScanCamera.setValue(savePO);
                            lsTblScanCamera.add(0, TblScanCamera);
                            TblScanCamera.setName("Tag");
                            TblScanCamera.setValue(saveTag);
                            lsTblScanCamera.add(1, TblScanCamera);
                            Setting.dismiss();
                            tblTask.setUser(member.getUser());
                            tblTask.setPo(savePO);
                            tblTask.setTag(saveTag);
                            tblTask.setDeliveryDateTime(dateController.getSystemTime(MainActivity.this));
                            getBussinessPerson();
                        }
                    }
                });
                Button exit = (Button) Setting.findViewById(R.id.exit);
                exit.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {
                        Setting.dismiss();
                    }
                });

                po.setText(savePO);
                tag.setText(saveTag);
                Setting.show();

            } else {

                Setting.dismiss();

                if (poBoo)
                    savePO = result.getContents();
                else
                    saveTag = result.getContents();

                scanPO.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                        integrator.setPrompt("Scan P.O.");
                        integrator.setOrientationLocked(false);
                        integrator.setBeepEnabled(true);
//                        integrator.setCaptureActivity(CustomScanner.class);
                        integrator.initiateScan();
                        poBoo = true;
                    }
                });

                scanTaf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                        integrator.setPrompt("Scan Tag");
                        integrator.setOrientationLocked(false);
                        integrator.setBeepEnabled(true);
//                        integrator.setCaptureActivity(CustomScanner.class);
                        integrator.initiateScan();
                        poBoo = false;
                    }
                });

                Button confirm = (Button) Setting.findViewById(R.id.confirm);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean bTag = false, bPO = false;
                        if (saveTag != null) {
                            if (saveTag.equalsIgnoreCase("") || saveTag.equalsIgnoreCase(getResources().getString(R.string.necessary))) {
                                tag.setText(R.string.necessary);
                                tag.setTextColor(getResources().getColor(R.color.colorPrimary));
                            } else {
                                bTag = true;
                            }
                        } else {
                            tag.setText(R.string.necessary);
                            tag.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }


                        if (savePO != null) {
                            if (savePO.equalsIgnoreCase("") || savePO.equalsIgnoreCase(getResources().getString(R.string.necessary))) {
                                po.setText(R.string.necessary);
                                po.setTextColor(getResources().getColor(R.color.colorPrimary));
                            } else {
                                bPO = true;
                            }
                        } else {
                            po.setText(R.string.necessary);
                            po.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }

                        if (bTag && bPO) {
                            lsTblScanCamera.clear();
                            TblScanCamera.setName("P.O.");
                            TblScanCamera.setValue(savePO);
                            lsTblScanCamera.add(0, TblScanCamera);
                            TblScanCamera.setName("Tag");
                            TblScanCamera.setValue(saveTag);
                            lsTblScanCamera.add(1, TblScanCamera);
                            Setting.dismiss();
                            tblTask.setUser(member.getUser());
                            tblTask.setPo(savePO);
                            tblTask.setTag(saveTag);
                            tblTask.setDeliveryDateTime(dateController.getSystemTime(MainActivity.this));
                            getBussinessPerson();
                        }
                    }
                });
                Button exit = (Button) Setting.findViewById(R.id.exit);
                exit.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {
                        Setting.dismiss();
                    }
                });

                po.setText(savePO);
                tag.setText(saveTag);
                Setting.show();

                Window window = Setting.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String getLanguageFromDB() {
        String lang;
        if (taskController != null) {
            List<TblSetting> tblSettings = taskController.getSetting();
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


    private void getBussinessPerson(){
        try {
            apiService = new ApiService();
            mainPresenter = new MainPresenter(MainActivity.this,apiService);
            tblTask.setType("check");
            tblTask.setPickupPoint((tblTask.getPickupPoint() == null) ? "" : tblTask.getPickupPoint());
            tblTask.setDistribution((tblTask.getDistribution() == null) ? "" : tblTask.getDistribution());
            tblTask.setVehicle(((tblTask.getVehicle() == null) ? "" : tblTask.getVehicle()));
            tblTask.setBussinessPerson(((tblTask.getBussinessPerson() == null) ? "" : tblTask.getBussinessPerson()));
            tblTask.setItemCode(((tblTask.getItemCode() == null) ? "" : tblTask.getItemCode()));
            tblTask.setDelayJudgmentTime(((tblTask.getDelayJudgmentTime() == null) ? "" : tblTask.getDelayJudgmentTime()));

            mainPresenter.checkPO();
//            mainPresenter.loadBussinessPerson();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setNextScanPage(List<TblTask> task){
        try {
            if(task!=null && task.size()>0){
                tblTask.setBussinessPerson(task.get(0).getBussinessPerson());
            }else {
                tblTask.setBussinessPerson("");
            }
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            intent.putExtra("fragNew","new");
            intent.putExtra("member",member);
            intent.putExtra("task_select",tblTask);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateDataPODup(TblTask task){
        try {
            if(task!=null){
                tblTask = task;
            }
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            intent.putExtra("member",member);
            intent.putExtra("task_select",tblTask);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
