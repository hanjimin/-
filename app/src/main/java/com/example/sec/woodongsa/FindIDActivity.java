package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class FindIDActivity extends Activity implements RadioGroup.OnCheckedChangeListener{

    RadioButton radio_user, radio_consultant;
    RadioGroup radio_group;
    EditText find_id_name, find_id_email;
    Button btn_find_id;
    String myJSON,myJSON_consultant;
    JSONArray json_list = null;
    Dialog loadingDialog;
    String found_id;

    int mode_temp=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        radio_user = (RadioButton) findViewById(R.id.radio_user);
        radio_consultant = (RadioButton) findViewById(R.id.radio_consultant);
        radio_group = (RadioGroup) findViewById(R.id.radio_group);

        find_id_name = (EditText) findViewById(R.id.find_id_name);
        find_id_email = (EditText) findViewById(R.id.find_id_email);
        btn_find_id = (Button) findViewById(R.id.btn_find_id);

        find_id_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    find_id_name.setHint("");
                } else {
                    find_id_name.setHint(" 이름을 입력하세요");
                }
            }
        });

        find_id_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    find_id_email.setHint("");
                } else {
                    find_id_email.setHint(" 이메일을 입력하세요");
                }
            }
        });

        radio_user.setChecked(true);

        radio_group.setOnCheckedChangeListener(this);

        ActionBarInit();

        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(find_id_name.getText().toString().equals("") || find_id_email.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"이름이나 비밀번호를 다시 확인 해 주세요",Toast.LENGTH_SHORT).show();
                } else {

                    if(mode_temp == 1){
                        find_id(find_id_name.getText().toString(),find_id_email.getText().toString());
                    } else if(mode_temp == 2) {
                        find_id_consultant(find_id_name.getText().toString(),find_id_email.getText().toString());
                    }


                }
            }
        });

    }

    protected void find_id(String name, String email){
        class GetDataJSON extends AsyncTask<String, Void, String> {


            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(FindIDActivity.this, "아이디 확인 중", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", params[0]));
                nameValuePairs.add(new BasicNameValuePair("email", params[1]));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_find_id.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                    //httpPost.setHeader("Content-type", "application/json");


                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                } finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                myJSON=result;
                setList();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(name, email);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (radio_group.getCheckedRadioButtonId() == R.id.radio_user) {
            mode_temp = 1;
        } else if (radio_group.getCheckedRadioButtonId() == R.id.radio_consultant) {
            mode_temp = 2;
        }
    }

    protected void setList(){
        try {
            if(myJSON == null){
                loadingDialog.dismiss();
                AlertDialog dialog = createDialogBox_network();
                dialog.show();
            } else{
                JSONObject jsonObject = new JSONObject(myJSON);
                json_list = jsonObject.getJSONArray("result");

                if(json_list.length() == 0){
                    Toast.makeText(getApplicationContext(),"알맞은 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                } else{
                    loadingDialog.dismiss();
                    final String[] id_list = new String[json_list.length()];
                    for(int i=0;i<json_list.length();i++){
                        JSONObject c = json_list.getJSONObject(i);

                        id_list[i] = c.getString("id");

                    }
                    found_id = id_list[0];
                    for(int i = 1; i < id_list.length; i++){
                        found_id += " , " + id_list[i];
                    }

                    AlertDialog dialog = createDialogBox();
                    dialog.show();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private AlertDialog createDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FindIDActivity.this);

        builder.setTitle("아이디 찾기");
        builder.setMessage("회원님의 아이디는 " + found_id + " 입니다.");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(FindIDActivity.this, FindIDActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    private AlertDialog createDialogBox_network(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FindIDActivity.this);

        builder.setTitle("네트워크가 불안정 합니다.");
        builder.setMessage("네트워크 연결을 확인 해 주세요.");

        builder.setPositiveButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(FindIDActivity.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

        builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(FindIDActivity.this, LoginActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    protected void find_id_consultant(String name, String email){
        class GetDataJSON extends AsyncTask<String, Void, String> {


            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(FindIDActivity.this, "아이디 확인 중", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", params[0]));
                nameValuePairs.add(new BasicNameValuePair("email", params[1]));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.com/app/app_find_id_consultant.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                    //httpPost.setHeader("Content-type", "application/json");


                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                } finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                myJSON_consultant=result;
                setList_consultant();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(name, email);
    }

    protected void setList_consultant(){
        try {
            if(myJSON_consultant == null){
                AlertDialog dialog = createDialogBox_network();
                dialog.show();
            } else{
                JSONObject jsonObject = new JSONObject(myJSON_consultant);
                json_list = jsonObject.getJSONArray("result");

                if(json_list.length() == 0){
                    Toast.makeText(getApplicationContext(),"알맞은 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                } else{
                    final String[] id_list = new String[json_list.length()];
                    for(int i=0;i<json_list.length();i++){
                        JSONObject c = json_list.getJSONObject(i);

                        id_list[i] = c.getString("id");

                    }
                    found_id = id_list[0];
                    for(int i = 1; i < id_list.length; i++){
                        found_id += " , " + id_list[i];
                    }
                    loadingDialog.dismiss();

                    AlertDialog dialog = createDialogBox_consultant();
                    dialog.show();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private AlertDialog createDialogBox_consultant(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FindIDActivity.this);

        builder.setTitle("아이디 찾기");
        builder.setMessage("설계사님의 아이디는 " + found_id + " 입니다.");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(FindIDActivity.this, FindIDActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_id, menu);
        return true;
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), Select_Service_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return false;
    }

    private void ActionBarInit(){

        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setTitle("");
    }
}
