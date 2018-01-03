package com.dtc.service.yazaki.functionsAndVariable;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.dtc.service.yazaki.R;
import com.dtc.service.yazaki.helper.CheckPermission;
import com.dtc.service.yazaki.model.TblSetting;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Koh on 06-Nov-17.
 */

public class GlobalFunctions {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

//    public static String ChangeCalendar(String date) {
//        String newDate = "Change Calendar Failed";
//
//        if (GlobalVariables.tblSetting == null) {
//            GlobalVariables.tblSetting = new TblSetting();
//            GlobalVariables.tblSetting.setCalendar("christian");
//            GlobalVariables.tblSetting.setCalendarOld("christian");
//            GlobalVariables.tblSetting.setdateFormat("dd/mm/yyyy");
//        }
//
//        if (GlobalVariables.tblSetting.getCalendar().equalsIgnoreCase("christian"))
//
//            if (GlobalVariables.tblSetting.getCalendarOld().equalsIgnoreCase(GlobalVariables.tblSetting.getCalendar())) {
//                newDate = date;
//            } else {
//                if (GlobalVariables.tblSetting.getdateFormate().equalsIgnoreCase("dd/mm/yyyy"))
//                    newDate = date.substring(0, 6) + (Integer.parseInt(date.substring(6, date.length())) - 543);
//                else if (GlobalVariables.tblSetting.getdateFormate().equalsIgnoreCase("yyyy/mm/dd")) {
//                    newDate = (Integer.parseInt(date.substring(0, 4)) - 543) + date.substring(4, date.length());
//                }
//            }
//        else if (GlobalVariables.tblSetting.getCalendar().equalsIgnoreCase("buddhist")) {
//            if (GlobalVariables.tblSetting.getCalendarOld().equalsIgnoreCase(GlobalVariables.tblSetting.getCalendar())) {
//                newDate = date;
//            } else {
//                if (GlobalVariables.tblSetting.getdateFormate().equalsIgnoreCase("dd/mm/yyyy"))
//                    newDate = date.substring(0, 6) + (Integer.parseInt(date.substring(6, date.length())) + 543);
//                else if (GlobalVariables.tblSetting.getdateFormate().equalsIgnoreCase("yyyy/mm/dd")) {
//                    newDate = (Integer.parseInt(date.substring(0, 4)) + 543) + date.substring(4, date.length());
//                }
//            }
//        }
//        return newDate;
//    }

    public static String ChangeDateFormate(String taget_format, String date) {
        String newDate, current_format;

        if ((date.substring(4, 5)).equalsIgnoreCase("/")) {
            current_format = "yyyy/mm/dd";
            if (!(current_format.equalsIgnoreCase(taget_format))) {
                newDate = date.substring(8, date.length()) + "/" +
                        date.substring(5, 7) + "/" +
                        date.substring(0, 4);
            } else {
                newDate = date;
            }
        } else {
            current_format = "dd/mm/yyyy";
            if (!(current_format.equalsIgnoreCase(taget_format))) {
                newDate = date.substring(6, date.length()) + "/" +
                        date.substring(3, 5) + "/" +
                        date.substring(0, 2);
            } else
                newDate = date;
        }
        return newDate;
    }

    public static String ChangeCalendarFormat(String target_calendar, String date) {
        String newDate = "", current_format;
        if ((date.substring(4, 5)).equalsIgnoreCase("/")) {
            if (Integer.parseInt(date.substring(0, 4)) > 2500) {
                current_format = "buddhist";
                if (current_format.equalsIgnoreCase(target_calendar)) {
                    newDate = date;
                } else {
                    newDate = String.valueOf(Integer.parseInt(date.substring(0, 4)) - 543) + "/" +
                            date.substring(5, 7) + "/" +
                            date.substring(8, date.length());
                }
            } else {
                current_format = "christian";
                if (current_format.equalsIgnoreCase(target_calendar)) {
                    newDate = date;
                } else {
                    newDate = String.valueOf(Integer.parseInt(date.substring(0, 4)) + 543) + "/" +
                            date.substring(5, 7) + "/" +
                            date.substring(8, date.length());
                }
            }
        } else {
            if (Integer.parseInt(date.substring(6, date.length())) > 2500) {
                current_format = "buddhist";
                if (current_format.equalsIgnoreCase(target_calendar)) {
                    newDate = date;
                } else {
                    newDate = date.substring(0, 2) + "/" +
                            date.substring(3, 5) + "/" +
                            String.valueOf(Integer.parseInt(date.substring(6, date.length())) - 543);
                }
            } else {
                current_format = "christian";
                if (current_format.equalsIgnoreCase(target_calendar)) {
                    newDate = date;
                } else {
                    newDate = date.substring(0, 2) + "/" +
                            date.substring(3, 5) + "/" +
                            String.valueOf(Integer.parseInt(date.substring(6, date.length())) + 543);
                }
            }
        }
        return newDate;
    }

    public static boolean CheckFullPermission(int requestCode, String permissions[], int[] grantResults, final Context context, final Activity activity) {

        boolean pass = false;

        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_PHONE_STATE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {

                            new AlertDialog.Builder(context)
                                    .setMessage("Do you want to enable permission?")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    CheckPermission.checkAndRequestPermissions(context, activity);
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    Toast.makeText(context, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                                                    activity.finish();
                                                    System.exit(0);
                                                    break;
                                            }
                                        }
                                    })
                                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    CheckPermission.checkAndRequestPermissions(context, activity);
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    Toast.makeText(context, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                                                    activity.finish();
                                                    System.exit(0);
                                                    break;
                                            }
                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            pass = true;
                        }
                    }
                }
            }
        }
        return pass;
    }

    public static boolean subFullPermission(final Context context, final Activity activity) {  // Unecessary
        boolean pass = false;
        if (CheckPermission.checkAndRequestPermissions(context, activity)) {
            pass = true;
        } else {
            CheckPermission.checkAndRequestPermissions(context, activity);
        }
        return pass;
    }

    public static String convertLanguage(String langNum){
        String newLang = "en";
        if(langNum.equalsIgnoreCase("0"))
            newLang = "th";
        else if(langNum.equalsIgnoreCase("1"))
            newLang = "en";
        return newLang;
    }

    public static String convertCalendar(String calNum){
        String newCal = "christian";
        if(calNum.equalsIgnoreCase("0"))
            newCal = "christian";
        else if(calNum.equalsIgnoreCase("1"))
            newCal = "buddhist";
        return newCal;
    }

    public static String convertDateFormat(String DFNum){
        String newDF = "yyyy/mm/dd";
        if(DFNum.equalsIgnoreCase("0"))
            newDF = "dd/mm/yyyy";
        else if(DFNum.equalsIgnoreCase("1"))
            newDF = "yyyy/mm/dd";
        return newDF;
    }

//    public static String
}
