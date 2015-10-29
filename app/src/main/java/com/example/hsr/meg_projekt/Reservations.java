package com.example.hsr.meg_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsr.meg_projekt.domain.Reservation;
import com.example.hsr.meg_projekt.helpers.ReservationAdappter;
import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;

import java.util.List;

public class Reservations extends OverlayActivity {

    RecyclerView rv;
    TextView emptyReservations;
    ImageView emptyReservationsImage;
    ReservationAdappter adapter;
    FloatingActionButton fab;
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_reservations);

        rv = (RecyclerView) findViewById(R.id.reservation_container);
        rv.setHasFixedSize(true);

        emptyReservations = (TextView)findViewById(R.id.empty_reservations);
        emptyReservationsImage = (ImageView)findViewById(R.id.empty_reservations_image);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>() {
            @Override
            public void onCompletion(List<Reservation> reservations) {

                if(reservations.size() != 0) {
                    emptyReservations.setVisibility(View.GONE);
                    emptyReservationsImage.setVisibility(View.GONE);
                    adapter = new ReservationAdappter(reservations, getApplicationContext());
                    rv.setAdapter(adapter);
                } else {
                    emptyReservations.setVisibility(View.VISIBLE);
                    emptyReservationsImage.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onError(String message) {
                Snackbar.make(rv, "Error getting Reservations", Snackbar.LENGTH_LONG).show();
            }
        });
        simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                adapter.onItemDismiss(viewHolder.getAdapterPosition(), rv);

            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rv);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>() {
                    @Override
                    public void onCompletion(List<Reservation> reservations) {

                        if(reservations.size() != 0) {
                            emptyReservations.setVisibility(View.GONE);
                            emptyReservationsImage.setVisibility(View.GONE);
                            adapter = new ReservationAdappter(reservations, getApplicationContext());
                            rv.setAdapter(adapter);
                            rv.getAdapter().notifyDataSetChanged();
                        } else {
                            emptyReservations.setVisibility(View.VISIBLE);
                            emptyReservationsImage.setVisibility(View.VISIBLE);
                        }
                        swipeRefreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onError(String message) {
                        Snackbar.make(rv, "Error getting Reservations", Snackbar.LENGTH_LONG).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>() {
            @Override
            public void onCompletion(List<Reservation> reservations) {

                if (reservations.size() != 0) {
                    emptyReservations.setVisibility(View.GONE);
                    emptyReservationsImage.setVisibility(View.GONE);
                    adapter = new ReservationAdappter(reservations, getApplicationContext());
                    rv.setAdapter(adapter);
                } else {
                    emptyReservations.setVisibility(View.VISIBLE);
                    emptyReservationsImage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String message) {
                Snackbar.make(rv, "Error getting Reservations", Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
