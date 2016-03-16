package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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


public class Select_Service_Activity extends Activity {

    Button btn_find_consultant, btn_evaluation_board, btn_select_login;
    ImageButton btn_guide;

    SharedPreferences selected_consultant_id, login_mode;
    JSONArray sub2_list1 = null;
    String myJSON;
    SharedPreferences user_id;
    SharedPreferences loading_state;
    SharedPreferences u_name,u_si,u_gu,u_tel,u_email,u_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__service_);

        btn_guide = (ImageButton) findViewById(R.id.btn_guide);
        btn_find_consultant = (Button) findViewById(R.id.btn_find_consultant);
        btn_evaluation_board = (Button) findViewById(R.id.btn_evaluation_board);
        btn_select_login = (Button) findViewById(R.id.btn_select_login);

        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        u_name = getApplicationContext().getSharedPreferences("u_name",0);
        u_si = getApplicationContext().getSharedPreferences("u_si",0);
        u_gu = getApplicationContext().getSharedPreferences("u_gu",0);
        u_tel = getApplicationContext().getSharedPreferences("u_tel",0);
        u_email = getApplicationContext().getSharedPreferences("u_email",0);
        u_nickname = getApplicationContext().getSharedPreferences("u_nickname",0);
        user_id = getApplicationContext().getSharedPreferences("user_id",0);

        get_user_info(user_id.getString("user_id",""));

        login_mode = getApplicationContext().getSharedPreferences("login_mode",0);
        if(login_mode.getInt("login_mode",0) == 0){
            btn_select_login.setText("로그인");
        } else {
            btn_select_login.setText("로그아웃");

        }

        btn_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Select_Service_Activity.this, Guide_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        btn_select_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_mode.getInt("login_mode",0) == 0){
                    Intent intent = new Intent(Select_Service_Activity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    SharedPreferences.Editor editor_mode = login_mode.edit();
                    editor_mode.putInt("login_mode",0);
                    editor_mode.commit();

                    Intent intent = new Intent(Select_Service_Activity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });

        selected_consultant_id = getApplicationContext().getSharedPreferences("selected_consultant_id", 0);
        SharedPreferences.Editor editor_id = selected_consultant_id.edit();
        editor_id.putString("selected_consultant_id","");

        btn_find_consultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Select_Service_Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        btn_evaluation_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Select_Service_Activity.this, Evaluation_main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    protected void get_user_info(String a){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            Dialog loadingDialog;

            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Select_Service_Activity.this, "리스트 불러오는 중", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {

                String paramID = params[0];
                System.out.println(params[0]);


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("u_id", params[0]));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_get_user_info.php");

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
                set_user_info();
                loadingDialog.dismiss();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(a);
    }

    protected void set_user_info(){
        try {
            if(myJSON == null){
                AlertDialog dialog = createDialogBox();
                dialog.show();
            } else{
                JSONObject jsonObject = new JSONObject(myJSON);
                sub2_list1 = jsonObject.getJSONArray("result");

                JSONObject c = sub2_list1.getJSONObject(0);

                u_name = getApplicationContext().getSharedPreferences("u_name",0);
                SharedPreferences.Editor editor_1 = u_name.edit();
                editor_1.putString("u_name",c.getString("u_name"));
                editor_1.commit();

                u_si = getApplicationContext().getSharedPreferences("u_si",0);
                SharedPreferences.Editor editor_2 = u_si.edit();
                editor_2.putString("u_si",c.getString("u_si"));
                editor_2.commit();

                u_gu = getApplicationContext().getSharedPreferences("u_gu",0);
                SharedPreferences.Editor editor_3 = u_gu.edit();
                editor_3.putString("u_gu",c.getString("u_gu"));
                editor_3.commit();

                u_tel = getApplicationContext().getSharedPreferences("u_tel",0);
                SharedPreferences.Editor editor_4 = u_tel.edit();
                editor_4.putString("u_tel",c.getString("u_tel"));
                editor_4.commit();

                u_email = getApplicationContext().getSharedPreferences("u_email",0);
                SharedPreferences.Editor editor_5 = u_email.edit();
                editor_5.putString("u_email",c.getString("u_email"));
                editor_5.commit();

                u_nickname = getApplicationContext().getSharedPreferences("u_nickname",0);
                SharedPreferences.Editor editor_6 = u_nickname.edit();
                editor_6.putString("u_nickname",c.getString("u_nickname"));
                editor_6.commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private AlertDialog createDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Select_Service_Activity.this);

        builder.setTitle("네트워크가 불안정 합니다.");
        builder.setMessage("네트워크 연결을 확인 해 주세요.");

        builder.setPositiveButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(Select_Service_Activity.this, Select_Service_Activity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

        builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setMessage("종료하시겠습니까?");
            alertDialog.setPositiveButton("예",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    finishAffinity();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            });
            alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }
        return false;
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select__service_, menu);
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
}
