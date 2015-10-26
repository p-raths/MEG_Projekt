package com.example.hsr.meg_projekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

        LibraryService service = new LibraryService();
        service.setServerAddress(server);

        service.login(username, password, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                if (input){
                    loginSuccessfull(findViewById(android.R.id.content));
                } else {
                    Log.d("login funktionirt nicht","leel");
                }

            }
            @Override
            public void onError(String message) {

            }
        });


        Intent intent = new Intent(this, Item_Overview.class);
        startActivity(intent);


    }

    public void loginSuccessfull(View view){
        //Intent intent = new Intent(this, Item_Overview.class);
        //startActivity(intent);
    }
    public void register(View view){
        Intent intent = new Intent (this, Registration.class);
        startActivity (intent);
    }

}
