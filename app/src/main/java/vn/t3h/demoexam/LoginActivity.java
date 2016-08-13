package vn.t3h.demoexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import vn.t3h.demoexam.model.Email;
import vn.t3h.demoexam.model.response.BaseResponse;
import vn.t3h.demoexam.model.response.LoginResponse;
import vn.t3h.demoexam.model.User;
import vn.t3h.demoexam.network.APIServiceBuilder;
import vn.t3h.demoexam.network.Constants;
import vn.t3h.demoexam.network.IAPIService;
import vn.t3h.demoexam.network.NetworkRequest;
import vn.t3h.demoexam.utils.CustomSharedPref;

import static android.text.Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE;

public class LoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
//        Snackbar.make(btLogin, "APIKey: "+apiKey.isEmpty(), Snackbar.LENGTH_SHORT).show();
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
        Log.d(TAG, "Login");

       /* if (!validate()) {
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);
*/
        apiService = APIServiceBuilder.buildAPIService(this, Constants.DOMAIN);
        User user = new User();
        user.setUserName(etUsername.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setApiKey(CustomSharedPref.getApiKey(this));

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setVersion((double) BuildConfig.VERSION_CODE).create();
        Log.d(TAG, gson.toJson(user));
        String contentType = "application/json";
        subscription = NetworkRequest.
                performAsyncRequest(this, apiService.login(contentType, user), data -> {
                    // Luu token
                    if (data.getStatus() == BaseResponse.STATUS_SUCCESS) {
                        if (data.getToken() != null) {
                            CustomSharedPref.saveToken(LoginActivity.this, data.getToken());
                        }
                    }
                    return data;
                }, next -> {
                    if (next.getStatus() == BaseResponse.STATUS_SUCCESS) {
                        // Call API lay danh sach email
//                        List<Email> emailList = new ArrayList<>();
                        // Di tiep toi man hinh email list
                        Intent intent = new Intent(LoginActivity.this, EmailListActitvity.class);
//                        intent.putExtra("emailList", (Serializable) emailList);
                        startActivity(intent);
                    } else {
                        // Xuat thong bao
                        Snackbar.make(btLogin, next.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
