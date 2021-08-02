package com.entertechsolutions.miruandroid.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenericResponseModel<T> {
    @SerializedName("success")
    @Expose
    private
    Boolean status;

    @SerializedName("error")
    @Expose
    private
    String error;

    @SerializedName("data")
    @Expose
    private
    T data;

    @SerializedName("recordcount")
    @Expose
    private int recordcount;

    public GenericResponseModel() {
    }

    public GenericResponseModel(Boolean status, String error, T data, int recordcount) {
        this.status = status;
        this.error = error;
        this.data = data;
        this.recordcount = recordcount;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(int recordcount) {
        this.recordcount = recordcount;
    }
}
