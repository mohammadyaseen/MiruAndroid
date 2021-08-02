package com.entertechsolutions.miruandroid.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenericResponseModelArray <T> {

    @SerializedName("isSuccess")
    @Expose
    private
    Boolean status;

    @SerializedName("Result")
    @Expose
    private
    String error;

    @SerializedName("data")
    @Expose
    private
    List<T> data;

    public GenericResponseModelArray() {
    }

    public GenericResponseModelArray(Boolean status, String error, List<T> data) {
        this.status = status;
        this.error = error;
        this.data = data;
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


}
