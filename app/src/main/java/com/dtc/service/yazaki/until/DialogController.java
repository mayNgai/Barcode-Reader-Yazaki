package com.dtc.service.yazaki.until;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by May on 11/6/2017.
 */

public class DialogController {
    public void dialogNormal(final Context _context, String title, String messes){
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(messes);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dialogClickOkClose(final Context _context, String title, String messes){
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(messes);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            ApplicationController.getAppActivity().finish();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dialogClickOkAndCancel(final Context _context, String title, String messes){
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(messes);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ApplicationController.getAppActivity().finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
