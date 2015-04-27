package com.dmbteam.catalogapp.lib;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pongsakorn Sommalai on 1/2/2558.
 * Detail: Use to Connect to Server use http protocal
 */
public class Connecter {
    protected String url;
    protected HttpClient httpClient;
    protected String err;
    protected String key;

    public Connecter(String url) {
        this.url = url;
        this.err = "";
        httpClient = new DefaultHttpClient();
    }

    protected JSONObject post(String url, List<NameValuePair> parameter) {
        JSONObject jObject = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(parameter, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            InputStream inputStream = resEntity.getContent();
            jObject = new JSONObject(responseToString(inputStream));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jObject;
    }

    protected JSONObject postFile(String url, MultipartEntityBuilder parameter){
        JSONObject jObject = null;
        try{
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(parameter.build());
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            InputStream inputStream = resEntity.getContent();

            jObject = new JSONObject(responseToString(inputStream));

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jObject;
    }

    protected JSONObject get(String url) {
        JSONObject jObject = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();

            jObject = new JSONObject(responseToString(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jObject;
    }

    protected String responseToString(InputStream inputStream) {
        String result;
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();

        } catch (Exception e) {
            result = e.toString();
            Log.e("error", "Error converting result " + e.toString());
        }
        return result;
    }

    public String getError() {
        return this.err;
    }
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean login(String username, String password) throws IOException, JSONException {
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("username", username));
        parameter.add(new BasicNameValuePair("password", password));
        Log.e("url", this.url + "/login");
        JSONObject obj = post(this.url + "/login", parameter);
        Log.e("url", this.url + "/login");
        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) {
            key = obj.getString("data");
            return true;
        }

        this.err = obj.getString("data");
        return false;
    }

    public boolean register(String username, String password, String fullname, String email, String phone, String address) throws IOException, JSONException {
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("username", username));
        parameter.add(new BasicNameValuePair("password", password));
        parameter.add(new BasicNameValuePair("fullname", fullname));
        parameter.add(new BasicNameValuePair("email", email));
        parameter.add(new BasicNameValuePair("phone", phone));
        parameter.add(new BasicNameValuePair("address", address));

        Log.e("url", this.url + "/register");
        JSONObject obj = post(this.url + "/register", parameter);
        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) {
            key = obj.getString("data");
            return true;
        }

        this.err = obj.getString("data");
        return false;
    }

    public JSONObject getInitData() {
        return get(this.url+"/init/"+this.key);
    }

    public JSONObject getCategory() {
        return get(this.url+"/getcategory/"+this.key);
    }

    public JSONObject getStore() {
        return get(this.url+"/getstore/"+this.key);
    }

    public JSONObject getProductInCategory(String cateID) {
        /*  s(offset)
            l(limit)
            id(category)    */
        return get(this.url+"/getproduct?type=category&s=0&l=50&id="+cateID);
    }

    public JSONObject getProductInStore(String storeID) {
        /*  s(offset)
            l(limit)
            id(store)    */
        return get(this.url+"/getproduct?type=store&s=0&l=50&id="+storeID);
    }

    public boolean editTeacherPassword(String oldpass, String newpass) throws JSONException {
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("oldpass", oldpass));
        parameter.add(new BasicNameValuePair("newpass", newpass));
        JSONObject obj = post(this.url+"/edit_password/"+this.key, parameter);

        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }

    public boolean editStudentLocation(String id, String lat, String lnt) throws JSONException {
        JSONObject obj = get(this.url+"/edit_student_location/"+this.key+"?id="+id+"&lat="+lat+"&lnt="+lnt);

        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }

    public JSONObject addPicture(String caption, String album_id, File picture) throws JSONException {
        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        FileBody fileBody = new FileBody(picture);
        StringBody salbum_id = new StringBody(album_id, ContentType.TEXT_PLAIN);
        StringBody scaption = new StringBody(caption, ContentType.TEXT_PLAIN);
        reqEntity.addPart("picture", fileBody);
        reqEntity.addPart("album_id", salbum_id);
        reqEntity.addPart("caption", scaption);

        JSONObject obj = postFile(this.url+"/add_picture/key/"+this.key, reqEntity);
        if (obj==null) {
            this.err = "Connection Error";
            return null;
        }

        if (obj.getString("status").equals("success")) {
            return obj.getJSONObject("data");
        }

        this.err = obj.getString("data");
        return null;

    }

    public boolean removePicture(String id) throws JSONException {
        JSONObject obj = get(this.url+"/remove_picture/"+this.key+"?id="+id);
        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }

    public boolean editPicture(String id, String caption) throws JSONException {
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("id", id));
        parameter.add(new BasicNameValuePair("caption", caption));
        JSONObject obj = post(this.url+"/edit_picture/"+this.key, parameter);

        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }

    public JSONObject editProfile(File picture) throws JSONException {
        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        FileBody fileBody = new FileBody(picture);
        reqEntity.addPart("profile", fileBody);

        JSONObject obj = postFile(this.url+"/edit_teacher_profile/"+this.key, reqEntity);
        if (obj==null) {
            this.err = "Connection Error";
            return null;
        }

        if (obj.getString("status").equals("success")) {
            return obj.getJSONObject("data");
        }

        this.err = obj.getString("data");
        return null;
    }

    public JSONObject editCover(File picture) throws JSONException {
        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        FileBody fileBody = new FileBody(picture);
        reqEntity.addPart("cover", fileBody);

        JSONObject obj = postFile(this.url+"/edit_teacher_cover/"+this.key, reqEntity);
        if (obj==null) {
            this.err = "Connection Error";
            return null;
        }

        if (obj.getString("status").equals("success")) {
            return obj.getJSONObject("data");
        }

        this.err = obj.getString("data");
        return null;
    }

    public boolean updateAttendanceSchedule(JSONArray data) throws JSONException {
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("data", data.toString()));
        JSONObject obj = post(this.url+"/update_attendance_schedule/"+this.key, parameter);

        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }

    public boolean updateAttendanceClass(JSONArray data) throws JSONException {
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("data", data.toString()));
        JSONObject obj = post(this.url+"/update_attendance_class/"+this.key, parameter);

        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }

    public boolean updateCommentsSchedule(JSONArray data) throws JSONException {
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("data", data.toString()));
        JSONObject obj = post(this.url+"/update_comment_schedule/"+this.key, parameter);

        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }

    public boolean updateCommentsClass(JSONArray data) throws JSONException {
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("data", data.toString()));
        JSONObject obj = post(this.url+"/update_comment_class/"+this.key, parameter);

        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }

    public boolean editAlbum(String id, String caption) throws JSONException {
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("caption", caption));
        parameter.add(new BasicNameValuePair("id", id));
        JSONObject obj = post(this.url+"/edit_album/"+this.key, parameter);

        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }

    public JSONObject addAlbum(String caption, String teacher_id, String student_id) throws JSONException {
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("caption", caption));
        parameter.add(new BasicNameValuePair("teacher_id", teacher_id));
        parameter.add(new BasicNameValuePair("student_id", student_id));
        JSONObject obj = post(this.url+"/add_album/"+this.key, parameter);

        if (obj==null) {
            this.err = "Connection Error";
            return null;
        }

        if (obj.getString("status").equals("success")) {
            return obj.getJSONObject("data");
        }

        this.err = obj.getString("data");
        return null;
    }

    public boolean removeAlbum(String id) throws JSONException {
        JSONObject obj = get(this.url+"/remove_album/"+this.key+"?id="+id);
        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }

    public boolean report(String detail) throws JSONException {
        List<NameValuePair> parameter = new ArrayList<NameValuePair>();
        parameter.add(new BasicNameValuePair("detail", detail));

        JSONObject obj = post(this.url+"/edit_album/"+this.key, parameter);

        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }

    public boolean setSchedulePeriodDate(String schedule_id, String period, String date) throws JSONException {
        JSONObject obj = get(this.url+"/update_sp/"+this.key+"?schedule_id="+schedule_id+"&period="+period+"&date="+date);

        if (obj==null) {
            this.err = "Connection Error";
            return false;
        }

        if (obj.getString("status").equals("success")) return true;

        this.err = obj.getString("data");
        return false;
    }
}