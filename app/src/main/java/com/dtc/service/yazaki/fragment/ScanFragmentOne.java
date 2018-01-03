package com.dtc.service.yazaki.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.functionsAndVariable.GlobalFunctions;
import com.dtc.service.yazaki.model.TblScan;
import com.dtc.service.yazaki.until.ApplicationController;
import com.dtc.service.yazaki.until.DateController;
import com.dtc.service.yazaki.until.DialogController;
import com.dtc.service.yazaki.until.TaskController;
import com.dtc.service.yazaki.view.MainActivity;
import com.dtc.service.yazaki.view.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.*;

/**
 * Created by admin on 11/9/2017 AD.
 */

public class ScanFragmentOne extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private View rootView;
    private Toolbar toolbar;
    private Button btn_read,btn_upload,btn_delete,btn_cancel;
    private LinearLayout linear_p_o,linear_pick_up_point,linear_destination,linear_vehicle,linear_tag,linear_bussiness_person,linear_item_name,linear_delivery_date_time,linear_delay_arrival;
    public static TextView txt_p_o,txt_pick_up_point,txt_destination,txt_vehicle,txt_tag,txt_bussiness_person,txt_item_name,txt_delivery_date_time,txt_delay_arrival;
    private ScanActivity mView;
    private Context mContext;
    private List<TblScan> scanList;
    private TaskController taskController;
    private Dialog Setting;
    private Boolean poBoo;
    private boolean fragNew = false;
    private String savePO = "", saveTag = "";
    private TblScan TblScanCamera;
    public static List<TblScan> lsTblScanCamera;
    public static TaskController taskControllerM;
    private CompoundBarcodeView barcodeScannerView;
    private ToggleButton switchFlashlightButton;
    private Activity activity;
    private DialogController dialogController;
    private DateController dateController;
    public static ScanFragmentOne newInstance() {
        ScanFragmentOne fragment = new ScanFragmentOne();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_scan_one, container, false);
        init();
        initlistenner();
        setTask();
        return rootView;
    }

    private void init(){
        try {
            dateController = new DateController();
            dialogController = new DialogController();
            taskController = new TaskController();
            TblScanCamera = new TblScan();
            lsTblScanCamera = new ArrayList<TblScan>();
            taskControllerM = new TaskController();
            mView = new ScanActivity();
            mContext = getContext();
            Setting = new Dialog(mContext);
            btn_read = (Button)rootView.findViewById(R.id.btn_read);
            btn_upload = (Button)rootView.findViewById(R.id.btn_upload);
            btn_delete = (Button)rootView.findViewById(R.id.btn_delete);
            btn_cancel = (Button)rootView.findViewById(R.id.btn_cancel);
            linear_p_o = (LinearLayout) rootView.findViewById(R.id.linear_p_o);
            linear_pick_up_point = (LinearLayout) rootView.findViewById(R.id.linear_pick_up_point);
            linear_destination = (LinearLayout) rootView.findViewById(R.id.linear_destination);
            linear_vehicle = (LinearLayout) rootView.findViewById(R.id.linear_vehicle);
            linear_tag = (LinearLayout) rootView.findViewById(R.id.linear_tag);
            linear_bussiness_person = (LinearLayout) rootView.findViewById(R.id.linear_bussiness_person);
            linear_item_name = (LinearLayout) rootView.findViewById(R.id.linear_item_name);
            linear_delivery_date_time = (LinearLayout) rootView.findViewById(R.id.linear_delivery_date_time);
            linear_delay_arrival = (LinearLayout) rootView.findViewById(R.id.linear_delay_arrival);
            txt_p_o = (TextView) rootView.findViewById(R.id.txt_p_o);
            txt_pick_up_point = (TextView) rootView.findViewById(R.id.txt_pick_up_point);
            txt_destination = (TextView) rootView.findViewById(R.id.txt_destination);
            txt_vehicle = (TextView) rootView.findViewById(R.id.txt_vehicle);
            txt_tag = (TextView) rootView.findViewById(R.id.txt_tag);
            txt_bussiness_person = (TextView) rootView.findViewById(R.id.txt_bussiness_person);
            txt_item_name = (TextView) rootView.findViewById(R.id.txt_item_name);
            txt_delivery_date_time = (TextView) rootView.findViewById(R.id.txt_delivery_date_time);
            txt_delay_arrival = (TextView) rootView.findViewById(R.id.txt_delay_arrival);
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            toolbar.setTitle(getActivity().getResources().getString(R.string.app_name));


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initlistenner(){
        try {
            linear_p_o.setOnClickListener(this);
            linear_pick_up_point.setOnClickListener(this);
            linear_destination.setOnClickListener(this);
            linear_vehicle.setOnClickListener(this);
            linear_tag.setOnClickListener(this);
            linear_bussiness_person.setOnClickListener(this);
            linear_item_name.setOnClickListener(this);
            linear_delivery_date_time.setOnClickListener(this);
            linear_delay_arrival.setOnClickListener(this);
            btn_read.setOnClickListener(this);
            btn_upload.setOnClickListener(this);
            btn_delete.setOnClickListener(this);
            btn_cancel.setOnClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setTask(){
        txt_p_o.setText(((mView.tblTask.getPo() == null) ? "" : mView.tblTask.getPo()));
        txt_pick_up_point.setText(((mView.tblTask.getPickupName() == null) ? "" : mView.tblTask.getPickupName()));
        txt_destination.setText(((mView.tblTask.getDistribution()== null) ? "" : mView.tblTask.getDistribution()));
        txt_vehicle.setText(((mView.tblTask.getVehicleName() == null) ? "" : mView.tblTask.getVehicleName()));
        txt_tag.setText(((mView.tblTask.getTag() == null) ? "" : mView.tblTask.getTag()));
        txt_bussiness_person.setText(((mView.tblTask.getBussinessPerson() == null) || (mView.tblTask.getBussinessPerson().equalsIgnoreCase("undefined")) ? "" : mView.tblTask.getBussinessPerson()));
        txt_item_name.setText(((mView.tblTask.getItemName() == null) ? "" : mView.tblTask.getItemName()));
        txt_delivery_date_time.setText(((mView.tblTask.getDeliveryDateTime() == null) ? "" : mView.tblTask.getDeliveryDateTime()));
        txt_delay_arrival.setText(((mView.tblTask.getDelayJudgmentTime() == null||mView.tblTask.getDelayJudgmentTime() == "") ? "15" : mView.tblTask.getDelayJudgmentTime()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.linear_p_o:

                break;
            case R.id.linear_pick_up_point:
                mView.strName = "pickup";
                addFragment();
                //mView.callService("pickup");
                break;
            case R.id.linear_destination:
                mView.strName = "destination";
                addFragment();
                break;
            case R.id.linear_vehicle:
                mView.strName = "vehicle";
                addFragment();
                break;
            case R.id.linear_tag:

                break;
            case R.id.linear_bussiness_person:

                break;
            case R.id.linear_item_name:
                mView.strName = "item";
                addFragment();
                break;
            case R.id.linear_delivery_date_time:
                setDatePicker(txt_delivery_date_time);
                break;
            case R.id.linear_delay_arrival:
                mView.strName = "delay";
                addFragment();
                break;
            case R.id.btn_read:

                savePO = "";
                poBoo = true;
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan P.O.");
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.forSupportFragment(this).initiateScan();

                break;

            case R.id.btn_upload:
//                MainActivity.lsTblScanCamera.clear();
                setValidateData();
                break;

            case R.id.btn_delete:
//                MainActivity.lsTblScanCamera.clear();
                if(txt_p_o.getText().toString().length()>0){
                    AlertDialog(getActivity(),getActivity().getResources().getString(R.string.txtWarning),getActivity().getResources().getString(R.string.txt_want_to_delete),"Delete");
                }else {
                    dialogController.dialogNormal(getActivity(),getActivity().getResources().getString(R.string.txtWarning),getActivity().getResources().getString(R.string.error_p_o));
                }

                break;

            case R.id.btn_cancel:
//                MainActivity.lsTblScanCamera.clear();
                AlertDialog(getActivity(),getActivity().getResources().getString(R.string.txtWarning),getActivity().getResources().getString(R.string.txt_want_to_cancel),"Cancel");
                break;
        }
    }

    private void setValidateData(){
        try {
            String txterror = "";
            boolean error = false;
            if(txt_p_o.getText().toString().length() == 0){
                txterror = getActivity().getResources().getString(R.string.error_p_o);
                error = true;
                txt_p_o.setHint(txterror);
                txt_p_o.setHintTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            }else if(txt_pick_up_point.getText().toString().length() == 0){
                txterror = getActivity().getResources().getString(R.string.error_pick_up_point);
                error = true;
                txt_pick_up_point.setHint(txterror);
                txt_pick_up_point.setHintTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            }else if(txt_destination.getText().toString().length() == 0){
                txterror = getActivity().getResources().getString(R.string.error_destination);
                error = true;
                txt_destination.setHint(txterror);
                txt_destination.setHintTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            }else if(txt_vehicle.getText().toString().length() == 0){
                txterror = getActivity().getResources().getString(R.string.error_vehicle);
                error = true;
                txt_vehicle.setHint(txterror);
                txt_vehicle.setHintTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            }else if(txt_tag.getText().toString().length() == 0){
                txterror = getActivity().getResources().getString(R.string.error_tag);
                error = true;
                txt_tag.setHint(txterror);
                txt_tag.setHintTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            }else if(txt_item_name.getText().toString().length() == 0){
                txterror = getActivity().getResources().getString(R.string.error_item_name);
                error = true;
                txt_item_name.setHint(txterror);
                txt_item_name.setHintTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            }else if(txt_delivery_date_time.getText().toString().length() == 0){
                mView.tblTask.setDelayJudgmentTime(String.valueOf(0));
//                txterror = getActivity().getResources().getString(R.string.error_delivery_date_time);
//                error = true;
//                txt_delivery_date_time.setHint(txterror);
//                txt_delivery_date_time.setHintTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            }
//            else if(txt_delay_arrival.getText().toString().length() == 0){
//                txterror = getActivity().getResources().getString(R.string.error_delay_arrival);
//                error = true;
//                dialogController.dialogNormal(getActivity(),getActivity().getResources().getString(R.string.txtWarning),getActivity().getResources().getString(R.string.error_delay_arrival));
//            }

            if(!error){
                AlertDialog(getActivity(),getActivity().getResources().getString(R.string.txtWarning),getActivity().getResources().getString(R.string.txt_want_to_upload),"upload");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addFragment(){
        try {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.contentFragmentMain,new ScanFragmentTwo()).addToBackStack(null).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return true;
    }

//    @Override
//    protected void startActivityForResult(Intent intent, int code) {
//        fragment.startActivityForResult(intent, code);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Setting.setTitle(R.string.txtLanguage);
        Setting.setContentView(R.layout.dialog_next_scan);
        Setting.setCancelable(false);
        final TextView po = (TextView) Setting.findViewById(R.id.poNumber);
        final TextView tag = (TextView) Setting.findViewById(R.id.tagNumber);
        FloatingActionButton scanPO = (FloatingActionButton) Setting.findViewById(R.id.floatingActionButtonScanPO);
        FloatingActionButton scanTaf = (FloatingActionButton) Setting.findViewById(R.id.floatingActionButtonScanTag);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(mContext, "Cancelled", Toast.LENGTH_LONG).show();

                Setting.dismiss();

                if (poBoo){
                    savePO = mView.tblTask.getPo();
                    saveTag = mView.tblTask.getTag();
                    fragNew = false;
                }else{
                    saveTag = mView.tblTask.getTag();
                }

                scanPO.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator integrator = new IntentIntegrator(getActivity());
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                        integrator.setPrompt("Scan P.O.");
                        integrator.setOrientationLocked(false);
                        integrator.setBeepEnabled(true);
                        integrator.forSupportFragment(ScanFragmentOne.this).initiateScan();
                        poBoo = true;
                    }
                });

                scanTaf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator integrator = new IntentIntegrator(getActivity());
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                        integrator.setPrompt("Scan Tag");
                        integrator.setOrientationLocked(false);
                        integrator.setBeepEnabled(true);
                        integrator.forSupportFragment(ScanFragmentOne.this).initiateScan();
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
                            }else{
                                bTag = true;
                            }
                        }else {
                            tag.setText(R.string.necessary);
                            tag.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }


                        if (savePO != null) {
                            if (savePO.equalsIgnoreCase("") || savePO.equalsIgnoreCase(getResources().getString(R.string.necessary))) {
                                po.setText(R.string.necessary);
                                po.setTextColor(getResources().getColor(R.color.colorPrimary));
                            }else{
                                bPO = true;
                            }
                        }else {
                            po.setText(R.string.necessary);
                            po.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }

                        if(savePO.length()>0 && saveTag.length()>0){
//                            lsTblScanCamera.clear();
//                            TblScanCamera.setName("P.O.");
//                            TblScanCamera.setValue(savePO);
//                            lsTblScanCamera.add(0, TblScanCamera);
//                            TblScanCamera.setName("Tag");
//                            TblScanCamera.setValue(saveTag);
//                            lsTblScanCamera.add(1, TblScanCamera);
                            if(fragNew){
                                mView.fragNew = true;
                            }
                            mView.tblTask.setPo(savePO);
                            mView.tblTask.setTag(saveTag);
                            mView.callService("check");
                            Setting.dismiss();

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

                if (poBoo){
                    savePO = result.getContents();
                    saveTag = mView.tblTask.getTag();
                    fragNew = true;
                }else
                    saveTag = result.getContents();

                scanPO.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator integrator = new IntentIntegrator(getActivity());
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                        integrator.setPrompt("Scan P.O.");
                        integrator.setOrientationLocked(false);
                        integrator.setBeepEnabled(true);
                        integrator.forSupportFragment(ScanFragmentOne.this).initiateScan();
                        poBoo = true;
                    }
                });

                scanTaf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator integrator = new IntentIntegrator(getActivity());
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                        integrator.setPrompt("Scan Tag");
                        integrator.setOrientationLocked(false);
                        integrator.setBeepEnabled(true);
                        integrator.forSupportFragment(ScanFragmentOne.this).initiateScan();
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
                            }else{
                                bTag = true;
                            }
                        }else {
                            tag.setText(R.string.necessary);
                            tag.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }

                        if (savePO != null) {
                            if (savePO.equalsIgnoreCase("") || savePO.equalsIgnoreCase(getResources().getString(R.string.necessary))) {
                                po.setText(R.string.necessary);
                                po.setTextColor(getResources().getColor(R.color.colorPrimary));
                            }else{
                                bPO = true;
                            }
                        }else {
                            po.setText(R.string.necessary);
                            po.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }

                        if(savePO.length()>0 && saveTag.length()>0){
//                            lsTblScanCamera.clear();
//                            TblScanCamera.setName("P.O.");
//                            TblScanCamera.setValue(savePO);
//                            lsTblScanCamera.add(0, TblScanCamera);
//                            TblScanCamera.setName("Tag");
//                            TblScanCamera.setValue(saveTag);
//                            lsTblScanCamera.add(1, TblScanCamera);
                            if(fragNew){
                                mView.fragNew = true;
                            }
                            mView.tblTask.setPo(savePO);
                            mView.tblTask.setTag(saveTag);
                            mView.callService("check");
                            Setting.dismiss();

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

    public void AlertDialog(final Context _context, final String title, String messes, final String action){
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(messes);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton(getActivity().getResources().getString(R.string.Ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            if(action.equalsIgnoreCase("Cancel")){
                                Setting.dismiss();
                                Intent i = new Intent(getActivity(), MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                getActivity().startActivity(i);
                                getActivity().finish();
                            }else if(action.equalsIgnoreCase("Delete")){
                                mView.callService("delete");
                            }else if(action.equalsIgnoreCase("Upload")){
                                mView.callService("upload");
                            }
                        }
                    });
            alertDialogBuilder.setNegativeButton(getActivity().getResources().getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(action.equalsIgnoreCase("Upload")){
                                if(mView.tblTask.getDelayJudgmentTime()== null||mView.tblTask.getDelayJudgmentTime().equalsIgnoreCase("0"))
                                    mView.tblTask.setDelayJudgmentTime("");
                            }
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setDatePicker(final TextView editText) {
        //String txtDate = dateController.convertDateFormat2To1(editText.getText().toString());
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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

//                editText.setText(GlobalFunctions.ChangeCalendarFormat(getCalendar_FromDbStorage(),
//                        GlobalFunctions.ChangeDateFormate(getDate_FormatFromDbStorage(),
//                                aa.substring(0, aa.length()))));
//
////                editText.setText(dateController.convertDateFormat2To1(aa));
//
//                String aaa = aa.replace("/","-");
//
//                member.setDelivery_date(aaa);
//                getTask();
                setTimePicker(editText,aa);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void setTimePicker(final TextView editText, final String aa){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        String strDate = "";

                        if(minute<10) {
                            String time = "0" + minute;
                            strDate = GlobalFunctions.ChangeDateFormate("dd/mm/yyyy",aa) + " " + hourOfDay + ":" + time + ":" + "00";
                            editText.setText(strDate);
                        }else {
                            strDate = GlobalFunctions.ChangeDateFormate("dd/mm/yyyy",aa) + " " + hourOfDay + ":" + minute + ":" + "00";
                            editText.setText(strDate);
                        }

                        mView.tblTask.setDeliveryDateTime(strDate);
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }
}
