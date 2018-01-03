package com.dtc.service.yazaki.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 11/17/2017 AD.
 */

public class TblItem implements Serializable {
    @SerializedName("company_code")
    @Expose
    private String companyCode;
    @SerializedName("item_code")
    @Expose
    private String itemCode;
    @SerializedName("item_name")
    @Expose
    private String itemName;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
