package com.dtc.service.yazaki.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by May on 11/6/2017.
 */
@DatabaseTable(tableName="TblTask")
public class TblTask implements Serializable {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @DatabaseField(id = true, useGetSet = true)
    private String guid;
    @SerializedName("po")
    @Expose
    @DatabaseField( useGetSet = true)
    private String po;
    @SerializedName("tag")
    @Expose
    @DatabaseField( useGetSet = true)
    private String tag;
    @SerializedName("delivery_name")
    @Expose
    @DatabaseField( useGetSet = true)
    private String deliveryName;
    @SerializedName("destibnation")
    @Expose
    @DatabaseField( useGetSet = true)
    private String destibnation;
    @SerializedName("delivery_status")
    @Expose
    @DatabaseField( useGetSet = true)
    private String deliveryStatus;
    @SerializedName("delivery_date_time")
    @Expose
    @DatabaseField( useGetSet = true)
    private String deliveryDateTime;
    @SerializedName("distribution_code")
    @Expose
    @DatabaseField( useGetSet = true)
    private String distributionCode;
    @SerializedName("distribution")
    @Expose
    @DatabaseField( useGetSet = true)
    private String distribution;
    @SerializedName("language")
    @Expose
    @DatabaseField( useGetSet = true)
    private String language;
    @SerializedName("display_date_format")
    @Expose
    @DatabaseField( useGetSet = true)
    private String displayDateFormat;
    @SerializedName("company_code")
    @Expose
    @DatabaseField( useGetSet = true)
    private String companyCode;
    @SerializedName("login_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private String loginId;
    @SerializedName("pick_up_point")
    @Expose
    @DatabaseField( useGetSet = true)
    private String pickupPoint;
    @SerializedName("pickup_name")
    @Expose
    @DatabaseField( useGetSet = true)
    private String pickupName;
    @SerializedName("vehicle")
    @Expose
    @DatabaseField( useGetSet = true)
    private String vehicle;
    @SerializedName("vehicle_name")
    @Expose
    @DatabaseField( useGetSet = true)
    private String vehicleName;
    @SerializedName("item_code")
    @Expose
    @DatabaseField( useGetSet = true)
    private String itemCode;
    @SerializedName("business_person")
    @Expose
    @DatabaseField( useGetSet = true)
    private String bussinessPerson;
    @SerializedName("item_name")
    @Expose
    @DatabaseField( useGetSet = true)
    private String itemName;
    @SerializedName("delay_judgment_time")
    @Expose
    @DatabaseField( useGetSet = true)
    private String delayJudgmentTime;
    @SerializedName("user")
    @Expose
    @DatabaseField( useGetSet = true)
    private String user;
    @SerializedName("project_code")
    @Expose
    @DatabaseField( useGetSet = true)
    private String project_code;
    @SerializedName("type")
    @Expose
    private String type;

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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDisplayDateFormat() {
        return displayDateFormat;
    }

    public void setDisplayDateFormat(String displayDateFormat) {
        this.displayDateFormat = displayDateFormat;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDelayJudgmentTime() {
        return delayJudgmentTime;
    }

    public void setDelayJudgmentTime(String delayJudgmentTime) {
        this.delayJudgmentTime = delayJudgmentTime;
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

    public String getPickupName() {
        return pickupName;
    }

    public void setPickupName(String pickupName) {
        this.pickupName = pickupName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDestibnation() {
        return destibnation;
    }

    public void setDestibnation(String destibnation) {
        this.destibnation = destibnation;
    }

    public String getDistributionCode() {
        return distributionCode;
    }

    public void setDistributionCode(String distributionCode) {
        this.distributionCode = distributionCode;
    }

    public String getBussinessPerson() {
        return bussinessPerson;
    }

    public void setBussinessPerson(String bussinessPerson) {
        this.bussinessPerson = bussinessPerson;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
