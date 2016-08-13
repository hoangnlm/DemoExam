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
import vn.t3h.demoexam.model.Email;
import vn.t3h.demoexam.viewmodel.EmailListAdapter;

public class EmailListActitvity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private EmailListAdapter adapter;
    private List<Email> emailList;
    private ListView lvEmailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_list_actitvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        // Khoi tao listview
//        emailList = (List<Email>) savedInstanceState.getSerializable("emailList");
        emailList = new ArrayList<>();
        emailList.add(new Email(1, "Mail1","Description1",100000));
        emailList.add(new Email(2, "Mail1","Description1",100000));
        emailList.add(new Email(3, "Mail1","Description1",100000));
        emailList.add(new Email(4, "Mail1","Description1",100000));
        emailList.add(new Email(5, "Mail1","Description1",100000));
        emailList.add(new Email(6, "Mail1","Description1",100000));
        emailList.add(new Email(7, "Mail1","Description1",100000));
        emailList.add(new Email(7, "Mail1","Description1",100000));
        emailList.add(new Email(7, "Mail1","Description1",100000));
        emailList.add(new Email(7, "Mail1","Description1",100000));
        emailList.add(new Email(7, "Mail1","Description1",100000));
        emailList.add(new Email(7, "Mail1","Description1",100000));
        adapter = new EmailListAdapter(this, emailList);
        lvEmailList = (ListView) findViewById(R.id.content_email_list_actitvity);
        lvEmailList.setAdapter(adapter);

        Snackbar.make(lvEmailList, "whyeesadfasdf", Snackbar.LENGTH_SHORT).show();
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
