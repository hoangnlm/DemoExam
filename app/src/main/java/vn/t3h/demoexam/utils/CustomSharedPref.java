package vn.t3h.demoexam.utils;

import android.content.Context;

public class CustomSharedPref {
    public static void saveApiKey(Context context, String apiKey){
        SharedPreferenceUtil.putString(context, "apiKey", apiKey);
    }

    public static void saveToken(Context context, String token){
        SharedPreferenceUtil.putString(context, "token", token);
    }

    public static String getToken(Context context){
        return SharedPreferenceUtil.getString(context, "token", "");
    }
    public static String getApiKey(Context context){
        return SharedPreferenceUtil.getString(context, "apiKey", "");
    }
}
