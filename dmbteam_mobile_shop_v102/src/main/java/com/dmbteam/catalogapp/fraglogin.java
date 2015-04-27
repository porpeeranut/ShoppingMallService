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
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dmbteam.catalogapp.lib.Connecter;
import com.dmbteam.catalogapp.lib.Normal;
import com.dmbteam.catalogapp.task.LoginAsyncTask;

import org.json.JSONException;

import java.io.IOException;

public class fraglogin extends Fragment {

    Button skipbutton, btnLogin;
    EditText editUsername, editPassword;
    RadioButton radStore;
    Context context;
    Activity act;
    public Connecter api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fraglogin, container, false);
       context = rootView.getContext();
        final RadioButton userrdo = (RadioButton)rootView.findViewById(R.id.radUser);
        final RadioButton shoprdo = (RadioButton)rootView.findViewById(R.id.radStore);
        skipbutton=(Button)rootView.findViewById(R.id.skipbtn);
        context = rootView.getContext();
        act = getActivity();

        editUsername = (EditText)rootView.findViewById(R.id.editUsername);
        editPassword = (EditText)rootView.findViewById(R.id.editPassword);
        radStore = (RadioButton)rootView.findViewById(R.id.radStore);
        btnLogin = (Button)rootView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editUsername.getText().toString();
                String pass = editPassword.getText().toString();
                if (Normal.isInternetConnected(getActivity())) {
                    if (user.length() != 0 && pass.length() != 0) {
                        String url;
                        if (radStore.isChecked())
                            url = "http://vps.bongtrop.com/shoppingmall/index.php/store";
                        else
                            url = "http://vps.bongtrop.com/shoppingmall/index.php/consumer";
                        Normal.set_apiURL_in_Pref(context, url);
                        new LoginAsyncTask((LoginActivity)act, url, user ,pass).execute();
                    }
                } else
                    Toast.makeText(context, "Internet not connect.", Toast.LENGTH_SHORT).show();
            }
        });

        skipbutton = (Button)rootView.findViewById(R.id.skipbtn);
        skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userrdo.isChecked()){
                    Intent intent1 = new Intent(context, MainActivity.class);
                    startActivity(intent1);
                    getActivity().finish();
                }
                else
                {
                    if(shoprdo.isChecked())
                    {
                        Intent intent2 = new Intent(context, ShopMainActivity.class);
                        startActivity(intent2);
                        getActivity().finish();
                    }
                }
            }
        });
        return rootView;

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fraglogin, container, false);
    }

}
