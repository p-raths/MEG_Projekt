package com.example.hsr.meg_projekt;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Item_Detail extends OverlayActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item__detail);

        activityName = getResources().getString(R.string.title_activity_item_detail);
        setHeadertext();
    }

}
