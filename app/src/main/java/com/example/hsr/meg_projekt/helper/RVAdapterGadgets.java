package com.example.hsr.meg_projekt.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;

import com.example.hsr.meg_projekt.R;
import com.example.hsr.meg_projekt.domain.Gadget;
import com.example.hsr.meg_projekt.service.Callback;
import com.example.hsr.meg_projekt.service.LibraryService;


public class RVAdapterGadgets extends RecyclerView.Adapter<RVAdapterGadgets.GadgetViewHolder>{
    List<Gadget> gadgets;

    public static class GadgetViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        Gadget gadget = null;
        CardView cv;
        LinearLayout gadgetDetailContainer;
        TextView gadgetDetailTitle;
        TextView gadgetDetailId;
        TextView gadgetDetailManufacturer;
        TextView gadgetDetailCondition;
        TextView gadgetDetailPrice;
        FloatingActionButton fabAddReservation;
        int defaultColor;


        GadgetViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cv = (CardView)itemView.findViewById(R.id.gadget_item);
            gadgetDetailContainer = (LinearLayout)itemView.findViewById(R.id.gadget_detail_container);
            gadgetDetailTitle = (TextView)itemView.findViewById(R.id.gadget_detail_title);
            gadgetDetailId = (TextView)itemView.findViewById(R.id.gadget_detail_id);
            gadgetDetailManufacturer = (TextView)itemView.findViewById(R.id.gadget_detail_manufacturer);
            gadgetDetailCondition = (TextView)itemView.findViewById(R.id.gadget_detail_condition);
            gadgetDetailPrice = (TextView)itemView.findViewById(R.id.gadget_detail_price);
            fabAddReservation = (FloatingActionButton)itemView.findViewById(R.id.fab_add_reservation);
            defaultColor = gadgetDetailTitle.getCurrentTextColor();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if(gadgetDetailContainer.getVisibility() == View.GONE){
                        gadgetDetailTitle.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        gadgetDetailTitle.setTextColor(Color.WHITE);
                        gadgetDetailContainer.setVisibility(View.VISIBLE);
                        fabAddReservation.setVisibility(View.VISIBLE);
                    } else {
                        gadgetDetailTitle.setBackgroundColor(Color.WHITE);
                        gadgetDetailTitle.setTextColor(defaultColor);
                        gadgetDetailContainer.setVisibility(View.GONE);
                        fabAddReservation.setVisibility(View.GONE);

                    }
                }
            });
            fabAddReservation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    LibraryService.reserveGadget(gadget, new Callback<Boolean>() {
                        @Override
                        public void onCompletion(Boolean input) {
                            Snackbar.make(v, "Gadget Reserved!", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(String message) {
                            Snackbar.make(v, "Could not reserve Gadget!", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }

    public RVAdapterGadgets(List<Gadget> gadgets){
        this.gadgets = gadgets;
    }

    @Override
    public int getItemCount() {
        return gadgets.size();
    }

    @Override
    public GadgetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gadget, viewGroup, false);
        return new GadgetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GadgetViewHolder GadgetViewHolder, int i) {
        GadgetViewHolder.gadget = gadgets.get(i);
        GadgetViewHolder.gadgetDetailTitle.setText(gadgets.get(i).getName());
        GadgetViewHolder.gadgetDetailId.setText(gadgets.get(i).getInventoryNumber());
        GadgetViewHolder.gadgetDetailManufacturer.setText(gadgets.get(i).getManufacturer());
        GadgetViewHolder.gadgetDetailCondition.setText(gadgets.get(i).getCondition().toString());

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("CHF");
        dfs.setGroupingSeparator('\'');
        dfs.setMonetaryDecimalSeparator('.');
        ((DecimalFormat) formatter).setDecimalFormatSymbols(dfs);

        GadgetViewHolder.gadgetDetailPrice.setText(formatter.format(gadgets.get(i).getPrice()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
