package com.example.hsr.meg_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.hsr.meg_projekt.domain.Gadget;
import com.example.hsr.meg_projekt.domain.Loan;
import com.example.hsr.meg_projekt.domain.Reservation;
import com.example.hsr.meg_projekt.helpers.MyAdapter;
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

        activityName = getResources().getString(R.string.title_activity_loaned_items);

        try {
            LibraryService.getReservationsForCustomer(new Callback<List<Loan>>() {
                @Override
                public void onCompletion(List<Loan> input) {
                    setContentView(R.layout.activity_loan_user);
                    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    mRecyclerView.setHasFixedSize(true);

                    // use a linear layout manager
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);

                    // specify an adapter (see also next example)
                    mAdapter = new ReservationAdappter(input, );
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



}
