package com.dmbteam.catalogapp.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dmbteam.catalogapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WithdrawFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WithdrawFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WithdrawFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_withdraw, container, false);
        ImageButton back = (ImageButton)view.findViewById(R.id.bback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                ShopMainFragment oneFragment = new ShopMainFragment();
                Vibrator vibe = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                vibe.vibrate(10);
                transaction.replace(R.id.fragment_shop, oneFragment);
                transaction.commit();
            }
        });



        // Inflate the layout for this fragment
        return view;
    }
}
