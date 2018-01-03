package com.dtc.service.yazaki.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Koh on 07-Nov-17.
 */
@DatabaseTable(tableName="TblSetting")
public class TblSetting implements Serializable {

    @DatabaseField(id = true, useGetSet = true)
    @SerializedName("login_id")
    @Expose
    private String guid;

    @DatabaseField( useGetSet = true)
    @SerializedName("view_language_setting")
    @Expose
    private String language;

    @DatabaseField( useGetSet = true)
    @SerializedName("display_calendar_setting")
    @Expose
    private String calendar;
    //
    @DatabaseField( useGetSet = true)
    @SerializedName("user")
    @Expose
    private String user;

    @DatabaseField( useGetSet = true)
    @SerializedName("display_date_format")
    @Expose
    private String dateFormat;

    @DatabaseField( useGetSet = true)
    @SerializedName("success")
    @Expose
    private String success;

//    @SerializedName("setting")
//    @Expose
//    private String user_name;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String success) {
        this.language = success;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

//    public String getCalendarOld() {
//        return calendarold;
//    }
//
//    public void setCalendarOld(String calendarold) {
//        this.calendarold = calendarold;
//    }



//    public String getdateFormateOld() {
//        return dateFormatold;
//    }

//    public void setdateFormatOld(String dateFormatold) {
//        this.dateFormatold = dateFormatold;
//    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
