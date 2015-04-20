package com.dmbteam.catalogapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fraglogin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fraglogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fraglogin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    Button skipbutton;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fraglogin, container, false);
       context = rootView.getContext();

        skipbutton=(Button)rootView.findViewById(R.id.skipbtn);
        skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, MainActivity.class);
                startActivity(intent1);
                getActivity().finish();
            }
        });
        return rootView;

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fraglogin, container, false);
    }

}
