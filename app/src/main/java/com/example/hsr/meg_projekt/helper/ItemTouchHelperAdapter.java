package com.example.hsr.meg_projekt.helper;

import android.support.v7.widget.RecyclerView;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position, RecyclerView view);

}
