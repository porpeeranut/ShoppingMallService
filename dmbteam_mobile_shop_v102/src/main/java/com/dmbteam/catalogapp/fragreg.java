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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dmbteam.catalogapp.lib.Connecter;
import com.dmbteam.catalogapp.task.RegisterAsyncTask;


public class fragreg extends Fragment {

    Activity act;
    Context context;
    RadioButton radUser, radStore;
    Button btnSubmit;

    EditText editUser;
    EditText editPass;
    EditText editConfPass;
    EditText editFullname;
    EditText editEmail;
    EditText editPhone;
    EditText editAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragreg, container, false);
        act = getActivity();
        context = rootView.getContext();
        radUser = (RadioButton)rootView.findViewById(R.id.radUser);
        radStore = (RadioButton)rootView.findViewById(R.id.radStore);

        editUser = (EditText)rootView.findViewById(R.id.editUser);
        editPass = (EditText)rootView.findViewById(R.id.editPass);
        editConfPass = (EditText)rootView.findViewById(R.id.editConfPass);
        editFullname = (EditText)rootView.findViewById(R.id.editFullname);
        editEmail = (EditText)rootView.findViewById(R.id.editEmail);
        editPhone = (EditText)rootView.findViewById(R.id.editPhone);
        editAddress = (EditText)rootView.findViewById(R.id.editAddress);

        btnSubmit = (Button)rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editUser.getText().toString();
                String pass = editPass.getText().toString();
                String confpass = editConfPass.getText().toString();
                String fullname = editFullname.getText().toString();
                String email = editEmail.getText().toString();
                String phone = editPhone.getText().toString();
                String address = editAddress.getText().toString();
                if (user.length() != 0 && pass.length() != 0 && confpass.length() != 0
                        && fullname.length() != 0 && email.length() != 0
                        && phone.length() != 0 && address.length() != 0) {
                    String url;
                    if (radStore.isChecked())
                        url = "http://vps.bongtrop.com/shoppingmall/index.php/store";
                    else
                        url = "http://vps.bongtrop.com/shoppingmall/index.php/consumer";

                    if (pass.equals(confpass))
                        new RegisterAsyncTask((LoginActivity)act, url, user, pass, fullname, email, phone, address).execute();
                    else
                        Toast.makeText(context, "Password ไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
