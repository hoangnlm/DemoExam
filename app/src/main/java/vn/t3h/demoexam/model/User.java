package vn.t3h.demoexam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("APIKey")
    @Expose
    String apiKey;

    @SerializedName("user_name")
    @Expose
    String userName;

    @SerializedName("pass")
    @Expose (deserialize = false)
    String password;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
