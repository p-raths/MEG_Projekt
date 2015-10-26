package com.example.hsr.meg_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void register(View view){

        EditText usernameEdit = (EditText) findViewById(R.id.username_text);
        String username = usernameEdit.getText().toString();

        EditText passwordEdit = (EditText) findViewById(R.id.text_password);
        String password = passwordEdit.getText().toString();

        EditText nameEdit = (EditText) findViewById(R.id.text_name);
        String name = nameEdit.getText().toString();

        EditText numberEdit = (EditText) findViewById(R.id.text_nr);
        String number = numberEdit.getText().toString();





        LibraryService.register(username, password, name, number, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                Log.d("Registration", "Completed");
            }

            @Override
            public void onError(String message) {
                Log.d("Registration","Failed");
            }

        });



        //Intent intent = new Intent (this, MainActivity.class);
        //startActivity (intent);
    }

}
