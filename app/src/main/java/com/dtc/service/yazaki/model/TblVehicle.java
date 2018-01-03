package com.dtc.service.yazaki.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 11/17/2017 AD.
 */

public class TblVehicle implements Serializable {
    @SerializedName("vehicle_code")
    @Expose
    private String vehicleCode;
    @SerializedName("vehicle_name")
    @Expose
    private String vehicleName;


    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
}
