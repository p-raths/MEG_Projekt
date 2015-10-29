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

        activityName = getResources().getString(R.string.title_activity_item_overview);

        try {
            LibraryService.getGadgets(new Callback<List<Gadget>>() {
                @Override
                public void onCompletion(List<Gadget> input) {

                    setContentView(R.layout.activity_item__overview);
                    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    mRecyclerView.setHasFixedSize(true);

                    // use a linear layout manager
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);

                    // specify an adapter (see also next example)
                    mAdapter = new MyAdapter(input);
                    mRecyclerView.setAdapter(mAdapter);

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
