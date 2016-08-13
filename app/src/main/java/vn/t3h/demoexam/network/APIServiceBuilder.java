package vn.t3h.demoexam.network;

import android.content.Context;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.t3h.demoexam.BuildConfig;

public class APIServiceBuilder {
    //cấu hình phương thức kết nối server, phương thức parse data,...
    public static IAPIService buildAPIService(Context context, String domain){
        //view log retrofit
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

        //cau hinh gson:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(domain)//lưu ý: domain nên kết thúc = dấu /
                // Data converter --> gson, su dung expose va version de quan ly qua trinh serialize va deseialize
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .setVersion((double) BuildConfig.VERSION_CODE).create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient) //okhttp
                .build();

        return retrofit.create(IAPIService.class);
    }
}
