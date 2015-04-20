package com.dmbteam.catalogapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dmbteam.catalogapp.R;


public class BaiSed extends Fragment {

    Context context;

    public BaiSed(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_bai_sed, container, false);
        setHasOptionsMenu(true);

        context = getActivity().getApplicationContext();

        /*Button btnChangePhoto = (Button)rootView.findViewById(R.id.btnChangePhoto);
        Button btnChangeCover = (Button)rootView.findViewById(R.id.btnChangeCover);
        btnChangePhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                modeSelectDialog(0);
            }
        });*/
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu,inflater);
    }
}
