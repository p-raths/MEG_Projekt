package com.example.hsr.meg_projekt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;

public class OverlayActivity extends AppCompatActivity{
    static NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = this.getSharedPreferences(
                "server", Context.MODE_PRIVATE);

        String server = prefs.getString("server", "");


        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_main, null);
        mDrawerLayout = fullView;
        super.setContentView(fullView);

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
                InputMethodManager inputMethodManager = (InputMethodManager) OverlayActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(OverlayActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        });

        mNavigationView = (NavigationView) findViewById(R.id.navigation_View);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.drawer_submenu_click:
                        if (getResources().getString(R.string.drawer_submenu_click).equals("Configure")) {
                            startActivity(new Intent(OverlayActivity.this, Settings.class));
                        } else {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                        }

                        break;
                    case R.id.drawer_myloans:
                        if (LibraryService.isLoggedIn())
                            startActivity(new Intent(OverlayActivity.this, Loaned_Items.class));
                        else
                            Snackbar.make(findViewById(android.R.id.content), "You must be logged in for this shit pleb!", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.drawer_overview:
                        if (LibraryService.isLoggedIn())
                            startActivity(new Intent(OverlayActivity.this, Item_Overview.class));
                        else
                            Snackbar.make(findViewById(android.R.id.content), "You must be logged in for this shit pleb!", Snackbar.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
    }
}
