package vn.t3h.demoexam.model.request;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.t3h.demoexam.utils.CustomSharedPref;

public class BaseRequest {
    @SerializedName("access_token")
    @Expose
    String token;

    public BaseRequest(Context context) {
        token = CustomSharedPref.getToken(context);
    }
}
