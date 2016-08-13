package vn.t3h.demoexam.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    public static final boolean STATUS_SUCCESS = true;
    public static final boolean STATUS_FAIL = false;

    @SerializedName("status")
    @Expose
    private boolean status;         // Khuyen cao su dung kieu Object nhu: Integer, Long, Double... (thu lai kieu nguyen thuy neu null thi co bi loi hay khong?)
    @SerializedName("error_message")
    @Expose
    private String message;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}