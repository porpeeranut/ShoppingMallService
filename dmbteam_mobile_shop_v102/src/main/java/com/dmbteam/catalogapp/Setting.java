package com.dmbteam.catalogapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class Setting extends Fragment {

    Context context;

    public Setting(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_setting, container, false);
        setHasOptionsMenu(false);

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
}
