package com.example.hsr.meg_projekt;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;

public class MainActivity extends OverlayActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        SharedPreferences username_prefs = this.getSharedPreferences(
                "username", Context.MODE_PRIVATE);
        String user_pref = username_prefs.getString("username", "");

        SharedPreferences password_prefs = this.getSharedPreferences(
                "password", Context.MODE_PRIVATE);
        String pass_pref = password_prefs.getString("password", "");


        if (user_pref.length()> 1){
            TextView textView = (TextView) findViewById(R.id.username_text);
            textView.setText(user_pref);
        }
        if (pass_pref.length()> 1){
            TextView textView = (TextView) findViewById(R.id.password_text);
            textView.setText(pass_pref);
        }


        SharedPreferences server_prefs = this.getSharedPreferences(
                "server", Context.MODE_PRIVATE);
        String server_pref = server_prefs.getString("server", "");
       if (server_pref.length()< 1){

            SharedPreferences.Editor editor = getSharedPreferences("server", MODE_PRIVATE).edit();
            editor.putString("server", "http://mge1.dev.ifs.hsr.ch");
            editor.commit();
            LibraryService.setServerAddress("http://mge1.dev.ifs.hsr.ch");

       }else
       {
           LibraryService.setServerAddress(server_pref);
       }


        activityName = getResources().getString(R.string.title_activity_main);
    }

    public void login(View view){

        EditText usernameEdit = (EditText) findViewById(R.id.username_text);
        final String username = usernameEdit.getText().toString();

        EditText passwordEdit = (EditText) findViewById(R.id.password_text);
        final String password = passwordEdit.getText().toString();

        Log.d("Email", username);
        Log.d("password", password);

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
                    user=username;

                    SharedPreferences.Editor editor = getSharedPreferences("username", MODE_PRIVATE).edit();
                    editor.putString("username", username);
                    editor.commit();

                    editor = getSharedPreferences("password", MODE_PRIVATE).edit();
                    editor.putString("password", password);
                    editor.commit();


                    loginSuccessfull(findViewById(android.R.id.content));
                } else {
                    loginFailed(findViewById(android.R.id.content));
                    Log.d("Server Response", "Login Failsed");
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
