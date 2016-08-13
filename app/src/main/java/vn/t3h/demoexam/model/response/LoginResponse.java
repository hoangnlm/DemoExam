package vn.t3h.demoexam.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.t3h.demoexam.model.User;

/**
 * Created by Hoang on 8/12/16.
 */

public class LoginResponse extends BaseResponse {
    @SerializedName("access_token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
