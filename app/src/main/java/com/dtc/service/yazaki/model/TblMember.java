package com.dtc.service.yazaki.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by May on 11/6/2017.
 */
@DatabaseTable(tableName="TblMember")
public class TblMember implements Serializable {
    private String success;
    private String message;
    @DatabaseField(id = true, useGetSet = true)
    private String guid;
    @SerializedName("user")
    @Expose
    @DatabaseField( useGetSet = true)
    private String user;

    @SerializedName("project_code")
    @Expose
    @DatabaseField( useGetSet = true)
    private String project_code;

    @SerializedName("login_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private String login_id;

    @SerializedName("delivery_date")
    @Expose
    @DatabaseField( useGetSet = true)
    private String delivery_date;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }
}
