package com.dmbteam.catalogapp.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dmbteam.catalogapp.LoginActivity;
import com.dmbteam.catalogapp.lib.Connecter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class RegisterAsyncTask extends AsyncTask<String, String, Void> {

    LoginActivity loginActivity;
    Context context;
    ProgressDialog progressDialog;
    String url;
    String username;
    String password;
    String Fullname;
    String Email;
    String Phone;
    String Address;
    String status;
    String sess = null;

    //Database database;
    String[] data;

    public RegisterAsyncTask(LoginActivity loginActivity, String url, String username, String password, String Fullname, String Email, String Phone, String Address) {
        this.loginActivity = loginActivity;
        this.context = loginActivity.getApplicationContext();
        this.url = url;
        this.username = username;
        this.password = password;
        this.Fullname = Fullname;
        this.Email = Email;
        this.Phone = Phone;
        this.Address = Address;
        progressDialog = new ProgressDialog(loginActivity);
    }

    protected void onPreExecute() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface arg0) {
                RegisterAsyncTask.this.cancel(true);
            }
        });
    }

    @Override
    protected Void doInBackground(String... params) {
        Connecter api = new Connecter(url);
        try {
            if (api.register(username, password, Fullname, Email, Phone, Address)) {
                //sess = api.getKey();
                status = "Success";

                /*database = new Database(context);
                JSONObject jObj = api.getInitData();
                Log.v("data", jObj.toString());
                if (!database.init(jObj))
                    status = "Can't create database.";

                database.close();*/

            } else
                status = api.getError();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void v) {
        if (status.equals("Success")) {
            //new LoadAllPicAsyncTask(loginActivity).execute();
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            //Log.v("sess", sess);
        } else
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
        this.progressDialog.dismiss();
    }
}
