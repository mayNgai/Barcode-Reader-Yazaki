package com.dtc.service.yazaki.until;

import android.app.Activity;

/**
 * Created by May on 11/6/2017.
 */

public class ApplicationController {
    private static ApplicationController instance = null;
    protected static Activity _activity;

    public static ApplicationController getInstance() {
        if (null == instance) {
            instance = new ApplicationController();
        }
        return instance;
    }

    public static Activity getAppActivity() {
        return _activity;

    }

    public static void setAppActivity(Activity a) {
        _activity = a;

    }
}
