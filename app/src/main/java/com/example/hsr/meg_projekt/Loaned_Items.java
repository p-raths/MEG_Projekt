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

import com.example.hsr.meg_projekt.domain.Loan;
import com.example.hsr.meg_projekt.helper.LoansAdapter;
import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;

import java.util.List;

public class Loaned_Items extends OverlayActivity {


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaned__items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setContentView(R.layout.activity_loan_user);
        try {
            LibraryService.getLoansForCustomer(new Callback<List<Loan>>() {
                @Override
                public void onCompletion(List<Loan> input) {
                    RecyclerView rvLoans = (RecyclerView) findViewById(R.id.rvLoans);
                    rvLoans.setHasFixedSize(true);
                    LoansAdapter adapter = new LoansAdapter(input, getApplicationContext());
                    rvLoans.setAdapter(adapter);
                    rvLoans.setLayoutManager(new LinearLayoutManager(Loaned_Items.this));
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(Loaned_Items.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IllegalStateException e) {
            Intent intent = new Intent(Loaned_Items.this, MainActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.d("Exception", "getLoansForCustomer");
        }

        activityName = getResources().getString(R.string.title_activity_loaned_items);
    }



}
