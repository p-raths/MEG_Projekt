package com.example.hsr.meg_projekt;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsr.meg_projekt.domain.Reservation;
import com.example.hsr.meg_projekt.helpers.ReservationAdappter;
import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;

import java.util.List;

public class Reservations extends OverlayActivity {

    RecyclerView recyclerView;
    ItemTouchHelper.SimpleCallback ItemCallback;
    SwipeRefreshLayout SwipeLayout;
    ReservationAdappter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_reservations);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));



        recyclerView = (RecyclerView) findViewById(R.id.reservation_container);
        recyclerView.setHasFixedSize(true);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);


        LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>() {
            @Override
            public void onCompletion(List<Reservation> reservations) {

                if(reservations.size() != 0) {
                    adapter = new ReservationAdappter(reservations, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }

                activityName = getResources().getString(R.string.title_activity_reservation);
                setHeadertext();
            }

            @Override
            public void onError(String message) {
                Snackbar.make(recyclerView, "Error getting Reservations", Snackbar.LENGTH_LONG).show();
            }
        });
        ItemCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                adapter.onItemDismiss(viewHolder.getAdapterPosition(), recyclerView);

            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(ItemCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        SwipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        SwipeLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        SwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>() {
                    @Override
                    public void onCompletion(List<Reservation> reservations) {

                        if (reservations.size() != 0) {

                            adapter = new ReservationAdappter(reservations, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                        SwipeLayout.setRefreshing(false);

                    }

                    @Override
                    public void onError(String message) {
                        Snackbar.make(recyclerView, "Error getting Reservations", Snackbar.LENGTH_LONG).show();
                        SwipeLayout.setRefreshing(false);
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
                    adapter = new ReservationAdappter(reservations, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onError(String message) {
                Snackbar.make(recyclerView, "Error getting Reservations", Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
