package vn.t3h.demoexam.network;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import vn.t3h.demoexam.model.response.APIResponse;
import vn.t3h.demoexam.model.request.BaseRequest;
import vn.t3h.demoexam.model.response.EmailResponse;
import vn.t3h.demoexam.model.response.LoginResponse;
import vn.t3h.demoexam.model.User;

/**
 * Created by Hoang on 8/12/16.
 */

public interface IAPIService {
    //danh sách các api
    @GET("getAPIKey")
    Observable<APIResponse> getApiKey();

    @Headers({
            "Content-Type: application/json"
    })
    @POST("login")
    Observable<LoginResponse> login(@Body User user);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("getEmails")
    Observable<EmailResponse> getEmails(@Body BaseRequest baseRequest);
}
