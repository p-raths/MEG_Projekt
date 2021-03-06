package com.example.hsr.meg_projekt;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;

public class OverlayActivity extends AppCompatActivity{
    static NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    protected String activityName = "";

    static String user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setContentView(int layoutResID)
    {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_overlay, null);
        mDrawerLayout = fullView;
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        super.setContentView(fullView);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_View);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.drawer_submenu_conf:
                        if (!activityName.equals(getResources().getString(R.string.title_activity_settings)))
                            startActivity(new Intent(OverlayActivity.this, Settings.class));
                        break;
                    case R.id.drawer_submenu_loginout:
                        if (!activityName.equals(getResources().getString(R.string.title_activity_main))) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);

                            if (LibraryService.isLoggedIn()) {

                                LibraryService.logout(new Callback<Boolean>() {
                                    @Override
                                    public void onCompletion(Boolean input) {
                                        if (input) {
                                            logoutSuccessfull(findViewById(android.R.id.content));
                                        } else {
                                            logoutFailed(findViewById(android.R.id.content));
                                        }
                                    }

                                    @Override
                                    public void onError(String message) {
                                        logoutFailed(findViewById(android.R.id.content));
                                    }
                                });
                            }
                        }
                        break;
                    case R.id.drawer_myreservations:
                        if (LibraryService.isLoggedIn()) {
                            if (!activityName.equals(getResources().getString(R.string.title_activity_reservation))) {
                                startActivity(new Intent(OverlayActivity.this, Reservations.class));
                            }
                        } else
                            missingLogin(findViewById(android.R.id.content));
                        break;
                    case R.id.drawer_myloans:
                        if (LibraryService.isLoggedIn()) {
                            if (!activityName.equals(getResources().getString(R.string.title_activity_loaned_items))) {
                                startActivity(new Intent(OverlayActivity.this, Loaned_Items.class));
                            }
                        } else
                            missingLogin(findViewById(android.R.id.content));
                        break;
                    case R.id.drawer_overview:
                        if (LibraryService.isLoggedIn()) {
                            if (!activityName.equals(getResources().getString(R.string.title_activity_item_overview))) {
                                startActivity(new Intent(OverlayActivity.this, Item_Overview.class));
                            }
                        } else
                            missingLogin(findViewById(android.R.id.content));
                        break;
                }
                return true;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void logoutSuccessfull(View view){
        Snackbar.make(view, "Logout Successfull", Snackbar.LENGTH_LONG).show();
        this.finish();

    }

    public void logoutFailed(View view){
        Snackbar.make(view, "Logout Successfull", Snackbar.LENGTH_LONG).show();

    }

    public void missingLogin(View view){
        Snackbar.make(view, "You must be logged in for this!", Snackbar.LENGTH_LONG).show();

    }

    public void setHeadertext(){
        TextView headertext = (TextView) findViewById(R.id.drawer_header_login_name);
        String str = "logged in as: " + user;
        headertext.setText(str);

    }
}
