package com.dtc.service.yazaki.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 11/17/2017 AD.
 */

public class TblPickUpAndDestination implements Serializable {
    @SerializedName("company_code")
    @Expose
    private String companyCode;
    @SerializedName("distribution_place_code")
    @Expose
    private String distributionPlaceCode;
    @SerializedName("distribution_place_name")
    @Expose
    private String distributionPlaceName;
    @SerializedName("distribution_place_address")
    @Expose
    private String distributionPlaceAddress;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getDistributionPlaceCode() {
        return distributionPlaceCode;
    }

    public void setDistributionPlaceCode(String distributionPlaceCode) {
        this.distributionPlaceCode = distributionPlaceCode;
    }

    public String getDistributionPlaceName() {
        return distributionPlaceName;
    }

    public void setDistributionPlaceName(String distributionPlaceName) {
        this.distributionPlaceName = distributionPlaceName;
    }

    public String getDistributionPlaceAddress() {
        return distributionPlaceAddress;
    }

    public void setDistributionPlaceAddress(String distributionPlaceAddress) {
        this.distributionPlaceAddress = distributionPlaceAddress;
    }
}
