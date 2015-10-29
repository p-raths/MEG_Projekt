package com.example.hsr.meg_projekt.helper;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import com.example.hsr.meg_projekt.R;
import com.example.hsr.meg_projekt.domain.Loan;


public class LoansAdapter extends
        RecyclerView.Adapter<LoansAdapter.ViewHolder> {
    private final Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView itemImage;
        TextView nameTextView;
        TextView pickupDateTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            nameTextView = (TextView) itemView.findViewById(R.id.item_name);
            pickupDateTextView = (TextView) itemView.findViewById(R.id.pickupDate);
            itemImage = (ImageView) itemView.findViewById(R.id.item_photo);
        }
    }

    private List<Loan> mLoans;
    public LoansAdapter(List<Loan> loans, Context context) {

        this.mLoans = loans;
        this.context = context;
    }

    @Override
    public LoansAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View loanView = inflater.inflate(R.layout.activity_loaned__items, parent, false);
        return new ViewHolder(loanView);
    }

    @Override
    public void onBindViewHolder(LoansAdapter.ViewHolder viewHolder, int position) {
        Loan loans = mLoans.get(position);

        ImageView photoIV = viewHolder.itemImage;
        if(loans.getGadget().getName().contains("IPhone")) {
            photoIV.setImageResource(R.mipmap.ic_apple);
        } else if (loans.getGadget().getName().contains("Android")) {
            photoIV.setImageResource(R.drawable.ic_android_black_24dp);
        } else {
            photoIV.setImageResource(R.drawable.ic_help_outline_black_24dp);

        }
        TextView nameTV = viewHolder.nameTextView;
        nameTV.setText(loans.getGadget().getName());

        Calendar cal = Calendar.getInstance();
        cal.setTime(loans.getPickupDate());
        cal.add(Calendar.DATE, 7);
        Date result = cal.getTime();
        SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();
        if(currentDate.after(result)) {
            viewHolder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.deleted));
        } else if (currentDate.before(result)) {
            viewHolder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.green));
        }

        TextView pickupDateTV = viewHolder.pickupDateTextView;
        pickupDateTV.setText("Return Date : " + dt.format(result));
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mLoans.size();
    }
}


