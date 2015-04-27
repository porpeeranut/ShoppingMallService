package com.dmbteam.catalogapp.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dmbteam.catalogapp.LoginActivity;
import com.dmbteam.catalogapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShopMainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShopMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopMainFragment extends Fragment {



    // TODO: Rename and change types and number of parameters
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_main, container, false);
        ListView menu = (ListView) view.findViewById(R.id.menulistView);

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub



                //Toast.makeText(getActivity(),"55555",Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        ShopItemSettingFragment oneFragment = new ShopItemSettingFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_shop, oneFragment);
                        getFragmentManager().popBackStack();
                        transaction.commit();


                        Vibrator vibe = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                        vibe.vibrate(100);
                        break;
                    case 1:
                        Vibrator vibe2 = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                        vibe2.vibrate(50);
                        break;
                    case 2:
                        Vibrator vibe3 = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                        vibe3.vibrate(10);
                        break;
                    case 3:
                        Vibrator vibe4 = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                        vibe4.vibrate(10);
                        break;
                    case 4:
                        Vibrator vibe5 = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                        vibe5.vibrate(10);
                        break;
                    case 5:
                        final Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    default:
                        Vibrator vibe6 = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                        vibe6.vibrate(0);
                        break;

                }

            }
        });
        return view;
    }

}
