package com.example.hsr.meg_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hsr.meg_projekt.domain.Loan;
import com.example.hsr.meg_projekt.domain.Reservation;
import com.example.hsr.meg_projekt.helpers.LoanedAdappter;
import com.example.hsr.meg_projekt.helpers.ReservationAdappter;
import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;

import java.util.List;

public class Loaned_Items extends OverlayActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaned_items);

        try {
            LibraryService.getLoansForCustomer(new Callback<List<Loan>>() {
                @Override

                public void onCompletion(List<Loan> input) {
                    mRecyclerView = (RecyclerView) findViewById(R.id.loaned_Items_RV);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new LoanedAdappter(input, getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);
                    activityName = getResources().getString(R.string.title_activity_loaned_items);
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



}
