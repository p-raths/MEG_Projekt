package com.example.hsr.meg_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hsr.meg_projekt.domain.Gadget;
import com.example.hsr.meg_projekt.helpers.MyAdapter;
import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;

import java.util.List;

public class Item_Overview extends OverlayActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));


        try {
            LibraryService.getGadgets(new Callback<List<Gadget>>() {
                @Override
                public void onCompletion(List<Gadget> input) {

                    setContentView(R.layout.activity_item__overview);
                    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new MyAdapter(input);
                    mRecyclerView.setAdapter(mAdapter);
                    activityName = getResources().getString(R.string.title_activity_item_overview);
                    setHeadertext();
                }

                @Override
                public void onError(String message) {

                }
            });
        } catch (IllegalStateException e) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.d("Exception", "getLoansForCustomer");
        }
    }

    public void onBackPressed(){
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

    public void logoutSuccessfull(View view){
        Snackbar.make(view, "Logout Successfull", Snackbar.LENGTH_LONG).show();
        this.finish();

    }

    public void logoutFailed(View view){
        Snackbar.make(view, "Logout Successfull", Snackbar.LENGTH_LONG).show();

    }

}
