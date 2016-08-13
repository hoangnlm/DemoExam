package vn.t3h.demoexam;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import vn.t3h.demoexam.database.EmailDAO;
import vn.t3h.demoexam.model.Email;
import vn.t3h.demoexam.model.request.BaseRequest;
import vn.t3h.demoexam.model.response.BaseResponse;
import vn.t3h.demoexam.network.APIServiceBuilder;
import vn.t3h.demoexam.network.Constants;
import vn.t3h.demoexam.network.IAPIService;
import vn.t3h.demoexam.network.NetworkRequest;
import vn.t3h.demoexam.viewmodel.EmailListAdapter;

public class EmailListActitvity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private EmailListAdapter adapter;
    private List<Email> emailList;
    private ListView lvEmailList;

    private Subscription subscription;
    private IAPIService apiService;

    private boolean empty;

    private EmailDAO emailDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Khoi tao EmailDAO
        emailDAO = new EmailDAO(this);

        // Khoi tao listview
        lvEmailList = (ListView) findViewById(R.id.content_email_list_actitvity);

//        emailList = (List<Email>) savedInstanceState.getSerializable("emailList");
//        emailList = new ArrayList<>();
//        emailList.add(new Email(1, "Mail1","Description1",100000));
//        emailList.add(new Email(2, "Mail1","Description1",100000));
//        emailList.add(new Email(3, "Mail1","Description1",100000));
//        emailList.add(new Email(4, "Mail1","Description1",100000));
//        emailList.add(new Email(5, "Mail1","Description1",100000));
//        emailList.add(new Email(6, "Mail1","Description1",100000));
//        emailList.add(new Email(7, "Mail1","Description1",100000));
//        emailList.add(new Email(7, "Mail1","Description1",100000));
//        emailList.add(new Email(7, "Mail1","Description1",100000));
//        emailList.add(new Email(7, "Mail1","Description1",100000));
//        emailList.add(new Email(7, "Mail1","Description1",100000));
//        emailList.add(new Email(7, "Mail1","Description1",100000));
//        adapter = new EmailListAdapter(this, emailList);
//        lvEmailList.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        apiService = APIServiceBuilder.buildAPIService(this, Constants.DOMAIN);
        emailList = new ArrayList<>();

        subscription = NetworkRequest.
                performAsyncRequest(this, apiService.getEmails(new BaseRequest(this)), data -> {
                    if (data.getStatus() == BaseResponse.STATUS_SUCCESS) {
                        if (data.getEmails().size() == 0) {
                            empty = true;
                            emailList.clear();
                            // Xoa sach du lieu cu
                            emailDAO.delete();
                        } else {
                            empty = false;
                            emailList = data.getEmails();
                            // Xoa sach du lieu cu trong table cua database
                            emailDAO.delete();
                            // Them du lieu moi vao table cua database
                            emailDAO.insert(emailList);
                        }
                    } else { // Neu server bi loi thi lay database co san tren may
//                        emailList = emailDAO.get();   // Khong nhan duoc, xuong next duoi moi nhan duoc
                    }

                    return data;
                }, next -> {
                    if (next.getStatus() == BaseResponse.STATUS_SUCCESS) {
                        // Xuat thong bao
                        if (!empty) {
                            Snackbar.make(lvEmailList, "Saved to sqlite successfully.", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(lvEmailList, "Database from server is empty!", Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
//                        // Du lieu dummy do server bi loi khong lay duoc danh sach email
//                        emailList.add(new Email(1, "Mail1", "Description1", 100000));
//                        emailList.add(new Email(2, "Mail2", "Description1", 100000));
//                        emailList.add(new Email(3, "Mail3", "Description1", 100000));
//                        emailList.add(new Email(4, "Mail4", "Description1", 100000));
//                        emailList.add(new Email(5, "Mail5", "Description1", 100000));
//                        emailList.add(new Email(6, "Mail6", "Description1", 100000));
//                        emailList.add(new Email(7, "Mail7", "Description1", 100000));
//                        emailList.add(new Email(8, "Mail8", "Description1", 100000));
//                        emailList.add(new Email(9, "Mail9", "Description1", 100000));
//                        emailList.add(new Email(10, "Mail10", "Description1", 100000));
//                        emailList.add(new Email(11, "Mail11", "Description1", 100000));
//                        emailList.add(new Email(12, "Mail12", "Description1", 100000));
//                        emailList.add(new Email(13, "Mail13", "Description1", 100000));
//                        emailList.add(new Email(14, "Mail14", "Description1", 100000));
//                        emailList.add(new Email(15, "Mail15", "Description1", 100000));
//                        emailList.add(new Email(16, "Mail16", "Description1", 100000));
//                        emailList.add(new Email(17, "Mail17", "Description1", 100000));
//                        emailList.add(new Email(18, "Mail18", "Description1", 100000));
//                        emailList.add(new Email(19, "Mail19", "Description1", 100000));
//                        // Them du lieu moi
//                        emailDAO.insert(emailList);

                        emailList = emailDAO.get();
                        // Xuat thong bao
                        Snackbar.make(lvEmailList, next.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    adapter = new EmailListAdapter(EmailListActitvity.this, emailList);
                    lvEmailList.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.email_list_actitvity, menu);
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
