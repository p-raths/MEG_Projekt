package com.example.hsr.meg_projekt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import com.example.hsr.meg_projekt.service.LibraryService;

public class Settings extends OverlayActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        SharedPreferences prefs = this.getSharedPreferences(
                "server", Context.MODE_PRIVATE);

        String server = prefs.getString("server", "");

        if (server.length()> 1){
            TextView textView = (TextView) findViewById(R.id.text_Server);
            textView.setText(server);
        }

        AutoCompleteTextView textView2 =(AutoCompleteTextView) findViewById(R.id.text_Server);

        String[] servers = getResources().getStringArray(R.array.server_array);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, servers);
        textView2.setAdapter(adapter);

        activityName = getResources().getString(R.string.title_activity_settings);
    }


    public void submit(View view){


        EditText serverEdit = (EditText) findViewById(R.id.text_Server);
        String server = serverEdit.getText().toString();

        SharedPreferences.Editor editor = getSharedPreferences("server", MODE_PRIVATE).edit();
        editor.putString("server", server);
        editor.commit();

        LibraryService.setServerAddress(server);


        LibraryService.setServerAddress(server);
        Snackbar.make(view, "Server changed", Snackbar.LENGTH_LONG).show();


    }

}
