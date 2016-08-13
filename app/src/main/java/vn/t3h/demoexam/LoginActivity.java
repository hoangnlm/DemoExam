package vn.t3h.demoexam;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rx.Subscription;
import vn.t3h.demoexam.model.User;
import vn.t3h.demoexam.model.response.BaseResponse;
import vn.t3h.demoexam.network.APIServiceBuilder;
import vn.t3h.demoexam.network.Constants;
import vn.t3h.demoexam.network.IAPIService;
import vn.t3h.demoexam.network.NetworkRequest;
import vn.t3h.demoexam.utils.CustomSharedPref;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    public static final int REQUEST_SIGNUP = 0;

    private EditText etUsername, etPassword;
    private Button btLogin;

    private Subscription subscription;
    private IAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
//        etPassword.setError(null, getResources().getDrawable(android.R.drawable.ic_menu_help, getTheme()));
        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btLogin_click();
            }
        });
    }

    private void btLogin_click() {
        // Check APIKey co chua, neu chua thi call service
        String apiKey = CustomSharedPref.getApiKey(this);
        apiService = APIServiceBuilder.buildAPIService(this, Constants.DOMAIN);
        if (apiKey.isEmpty()) {
            subscription = NetworkRequest.performAsyncRequest(this, apiService.getApiKey(), data -> {
                String newApiKey = data.getApiKey();
                if (newApiKey != null && !newApiKey.isEmpty()) {
                    CustomSharedPref.saveApiKey(LoginActivity.this, newApiKey);
                    data.setStatus(true);
                } else {
                    data.setStatus(false);
                }
                return data;
            }, next -> {
                if (next.getStatus()) {
                    Snackbar.make(btLogin, "Got API key!", Snackbar.LENGTH_SHORT).show();
                    login();
                } else {
                    Snackbar.make(btLogin, "Cannot get API key!", Snackbar.LENGTH_SHORT).show();
                }
            });
        } else {
            Snackbar.make(btLogin, "APIKey existing: " + apiKey, Snackbar.LENGTH_SHORT).show();
            login();
        }
    }

    private void login() {
        apiService = APIServiceBuilder.buildAPIService(this, Constants.DOMAIN);
        User user = new User();
        user.setUserName(etUsername.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setApiKey(CustomSharedPref.getApiKey(this));

        subscription = NetworkRequest.
                performAsyncRequest(this, apiService.login(user), data -> {
                    // Luu token
                    if (data.getStatus() == BaseResponse.STATUS_SUCCESS) {
                        if (data.getToken() != null) {
                            CustomSharedPref.saveToken(LoginActivity.this, data.getToken());
                        }
                    }
                    return data;
                }, next -> {
                    if (next.getStatus() == BaseResponse.STATUS_SUCCESS) {
                        // Di tiep toi man hinh email list
                        Intent intent = new Intent(LoginActivity.this, EmailListActitvity.class);
                        startActivity(intent);
                    } else {
                        // Xuat thong bao
                        Snackbar.make(btLogin, next.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}
