package com.example.hsr.meg_projekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;

public class MainActivity extends AppCompatActivity{

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

        if(LibraryService.isLoggedIn()){
            Intent intent = new Intent(this, Item_Overview.class);
            startActivity(intent);
        }


    }

    public void login(View view){

        EditText usernameEdit = (EditText) findViewById(R.id.username_text);
        String username = usernameEdit.getText().toString();

        EditText passwordEdit = (EditText) findViewById(R.id.password_text);
        String password = passwordEdit.getText().toString();


        SharedPreferences prefs = this.getSharedPreferences(
                "server", Context.MODE_PRIVATE);

        String server = prefs.getString("server", "");


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
