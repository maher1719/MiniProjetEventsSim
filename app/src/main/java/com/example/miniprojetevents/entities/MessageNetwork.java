package com.example.miniprojetevents.entities;

import com.google.gson.annotations.SerializedName;

public class MessageNetwork {


    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private String success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
