package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.List;


public class FindPasswdActivity extends Activity implements RadioGroup.OnCheckedChangeListener{

    RadioButton radio_user, radio_consultant;
    RadioGroup radio_group;
    EditText find_passwd_id, find_passwd_phone;
    Button btn_find_passwd;
    String myJSON;
    JSONArray json_list = null;
    Dialog loadingDialog;
    String found_passwd;
    int mode_temp=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_passwd);

        ActionBarInit();

        radio_user = (RadioButton) findViewById(R.id.radio_user);
        radio_consultant = (RadioButton) findViewById(R.id.radio_consultant);
        radio_group = (RadioGroup) findViewById(R.id.radio_group);

        find_passwd_id = (EditText) findViewById(R.id.find_passwd_id);
        find_passwd_phone = (EditText) findViewById(R.id.find_passwd_phone);

        find_passwd_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    find_passwd_id.setHint("");
                } else {
                    find_passwd_id.setHint(" 아이디를 입력하세요");
                }
            }
        });

        find_passwd_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    find_passwd_phone.setHint("");
                } else {
                    find_passwd_phone.setHint(" 휴대폰 번호를 입력하세요");
                }
            }
        });

        btn_find_passwd = (Button) findViewById(R.id.btn_find_passwd);

        radio_user.setChecked(true);

        radio_group.setOnCheckedChangeListener(this);

        btn_find_passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(find_passwd_id.getText().toString().equals("") || find_passwd_phone.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"아이디나 휴대폰 번호를 다시 확인 해 주세요",Toast.LENGTH_SHORT).show();
                } else {
                    if(mode_temp == 1){
                        find_passwd_user(find_passwd_id.getText().toString(),find_passwd_phone.getText().toString());
                    } else if(mode_temp == 2){
                        find_passwd_consultant(find_passwd_id.getText().toString(), find_passwd_phone.getText().toString());
                    }
                }
            }
        });
    }

    protected void find_passwd_user(String id, String phone){
        class GetDataJSON extends AsyncTask<String, Void, String> {


            protected void onPreExecute(){
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", params[0]));
                nameValuePairs.add(new BasicNameValuePair("phone", params[1]));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.com/app/app_find_passwd_user.php");

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
        g.execute(id, phone);
    }

    protected void setList(){
        try {
            if(myJSON == null){
                AlertDialog dialog = createDialogBox_network();
                dialog.show();
            } else{
                JSONObject jsonObject = new JSONObject(myJSON);
                json_list = jsonObject.getJSONArray("result");

                if(json_list.length() == 0){
                    Toast.makeText(getApplicationContext(),"알맞은 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                } else{
                    for(int i=0;i<json_list.length();i++){
                        JSONObject c = json_list.getJSONObject(i);

                        new SendMailTask(FindPasswdActivity.this).execute("woodongsa@gmail.com",
                                "blueman123", c.getString("email"), "우동사 비밀번호 찾기", "회원님의 비밀번호는 " + c.getString("passwd") + " 입니다.");


                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected void find_passwd_consultant(String id, String phone){
        class GetDataJSON extends AsyncTask<String, Void, String> {


            protected void onPreExecute(){
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", params[0]));
                nameValuePairs.add(new BasicNameValuePair("phone", params[1]));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.com/app/app_find_passwd_consultant.php");

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
                setList1();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(id, phone);
    }

    protected void setList1(){
        try {
            if(myJSON == null){
                AlertDialog dialog = createDialogBox_network();
                dialog.show();
            } else{
                JSONObject jsonObject = new JSONObject(myJSON);
                json_list = jsonObject.getJSONArray("result");

                if(json_list.length() == 0){
                    Toast.makeText(getApplicationContext(),"알맞은 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                } else{
                    for(int i=0;i<json_list.length();i++){
                        JSONObject c = json_list.getJSONObject(i);

                        new SendMailTask(FindPasswdActivity.this).execute("woodongsa@gmail.com",
                                "blueman123", c.getString("email"), "우동사 비밀번호 찾기", "설계사 님의 비밀번호는 " + c.getString("passwd") + " 입니다.");

                        find_passwd_id.setText("");
                        find_passwd_phone.setText("");

                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (radio_group.getCheckedRadioButtonId() == R.id.radio_user) {
            mode_temp = 1;
        } else if (radio_group.getCheckedRadioButtonId() == R.id.radio_consultant) {
            mode_temp = 2;
        }
    }


    private AlertDialog createDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FindPasswdActivity.this);

        builder.setTitle("비밀번호 찾기");
        builder.setMessage("메일이 발송되었습니다.");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(FindPasswdActivity.this, FindPasswdActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    private AlertDialog createDialogBox_network(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FindPasswdActivity.this);

        builder.setTitle("네트워크가 불안정 합니다.");
        builder.setMessage("네트워크 연결을 확인 해 주세요.");

        builder.setPositiveButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(FindPasswdActivity.this, FindPasswdActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

        builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(FindPasswdActivity.this, LoginActivity.class);
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
