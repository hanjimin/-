package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Change_PhoneNum_Process extends Activity {

    SharedPreferences user_id, login_mode, consultant_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__phone_num__process);


        ActionBarInit();

        user_id = getApplicationContext().getSharedPreferences("user_id",0);
        login_mode = getApplicationContext().getSharedPreferences("login_mode",0);
        consultant_id = getApplicationContext().getSharedPreferences("consultant_id",0);

        int login_mode_temp = login_mode.getInt("login_mode",0);

        Intent intent = getIntent();

        String phoneNo = intent.getStringExtra("phoneNo");

        if(login_mode_temp == 1){
            update_phone_num(user_id.getString("user_id",""), phoneNo);
        } else if(login_mode_temp == 2){
            update_phone_num_consultant(consultant_id.getString("consultant_id", ""), phoneNo);
        }


    }

    private void update_phone_num_consultant(String a, String b){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Change_PhoneNum_Process.this, "수정 중 입니다.", "Loading...");
            }

            @Override
            protected String doInBackground(String... params){

                String id = params[0];
                String tel = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id",id));
                nameValuePairs.add(new BasicNameValuePair("tel", tel));
                System.out.println(id + tel + "********************************");

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://woodongsa.com/app/app_update_phone_no_consultant.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();

                if(s.equalsIgnoreCase("success")){
                    Toast.makeText(getApplicationContext(), "수정 되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Change_PhoneNum_Process.this,Mypage_consultant.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("current_mypage_tab_num",1);
                    finish();
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                    System.out.println(s);
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(a,b);
    }



    private void ActionBarInit(){

        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void update_phone_num(String a, String b){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Change_PhoneNum_Process.this, "수정 중 입니다.", "Loading...");
            }

            @Override
            protected String doInBackground(String... params){

                String id = params[0];
                String tel = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id",id));
                nameValuePairs.add(new BasicNameValuePair("tel", tel));
                System.out.println(id + tel + "********************************");

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://woodongsa.com/app/app_update_phone_no.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();

                if(s.equalsIgnoreCase("success")){
                    Toast.makeText(getApplicationContext(), "수정 되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),Mypage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("current_mypage_tab_num",1);
                    finish();
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                    System.out.println(s);
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(a,b);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change__phone_num__process, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
