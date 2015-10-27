package com.example.hsr.meg_projekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;

public class MainActivity extends AppCompatActivity{
    static NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = this.getSharedPreferences(
                "server", Context.MODE_PRIVATE);

        String server = prefs.getString("server", "");

        if (server.length()> 1){
            TextView textView = (TextView) findViewById(R.id.text_Server);
            textView.setText(server);
        }

        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_main, null);
        mDrawerLayout = fullView;
        super.setContentView(fullView);
        
        mNavigationView = (NavigationView) findViewById(R.id.navigation_View);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.drawer_submenu_click:
                        if (getResources().getString(R.string.drawer_submenu_click).equals("Configure")){
                            startActivity(new Intent(MainActivity.this, Settings.class));
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                        }

                        break;
                    case R.id.drawer_myloans:
                        if (LibraryService.isLoggedIn())
                            startActivity(new Intent(MainActivity.this, Loaned_Items.class));
                        else
                            Snackbar.make(findViewById(android.R.id.content), "You must be logged in for this shit pleb!", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.drawer_overview:
                        if (LibraryService.isLoggedIn())
                            startActivity(new Intent(MainActivity.this, Item_Overview.class));
                        else
                            Snackbar.make(findViewById(android.R.id.content), "You must be logged in for this shit pleb!", Snackbar.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
    }

    public void login(View view){

        EditText serverEdit = (EditText) findViewById(R.id.text_Server);
        String server = serverEdit.getText().toString();

        EditText usernameEdit = (EditText) findViewById(R.id.username_text);
        String username = usernameEdit.getText().toString();

        EditText passwordEdit = (EditText) findViewById(R.id.password_text);
        String password = passwordEdit.getText().toString();


        SharedPreferences.Editor editor = getSharedPreferences("server", MODE_PRIVATE).edit();
        editor.putString("server", server);
        editor.commit();

        LibraryService.setServerAddress(server);

        Log.d("Email", username);
        Log.d("passord", password);

        try {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            Log.d("Hide Keyboard","oke...");
        }

        LibraryService.login(username, password, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {

                if (input) {
                    loginSuccessfull(findViewById(android.R.id.content));
                } else {
                    loginFailed(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onError(String message) {
                Log.d("Login", message);
                loginFailed(findViewById(android.R.id.content));
            }
        });


    }

    public void loginFailed(View view){
        Snackbar.make(view, "Login Failed", Snackbar.LENGTH_LONG).show();
    }

    public void loginSuccessfull(View view){
        Intent intent = new Intent(this, Item_Overview.class);

        startActivity(intent);
        Log.d("Login", "Works");
    }
    public void register(View view){
        Intent intent = new Intent (this, Registration.class);
        startActivity (intent);
    }

}
