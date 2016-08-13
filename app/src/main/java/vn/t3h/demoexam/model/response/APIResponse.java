package vn.t3h.demoexam.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hoang on 8/12/16.
 */

public class APIResponse extends BaseResponse {
    @SerializedName("APIKey")
    @Expose
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
