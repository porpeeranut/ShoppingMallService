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
import android.util.Log;
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
                        Vibrator vibe = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                        vibe.vibrate(10);
                        transaction.replace(R.id.fragment_shop, oneFragment);
                        transaction.commit();



                        break;
                    case 1:
                        OfferListFragment offer = new OfferListFragment();
                        FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                        Vibrator vibe2 = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                        vibe2.vibrate(10);
                        transaction2.replace(R.id.fragment_shop, offer);
                        transaction2.commit();
                        break;
                    case 2:
                        PromoShopListFragment promo = new PromoShopListFragment();
                        FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                        Vibrator vibe3 = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                        vibe3.vibrate(10);
                        transaction3.replace(R.id.fragment_shop, promo);
                        transaction3.commit();
                        break;
                    case 3:
                        WithdrawFragment withdraw = new WithdrawFragment();
                        FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
                        Vibrator vibe4 = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                        vibe4.vibrate(10);
                        transaction4.replace(R.id.fragment_shop, withdraw);
                        transaction4.commit();
                        break;
                    case 4:
                        ShopSittingFragment sitting = new ShopSittingFragment();
                        FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
                        Vibrator vibe5 = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
                        vibe5.vibrate(10);
                        transaction5.replace(R.id.fragment_shop, sitting);
                        transaction5.commit();
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
