package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LoginActivity extends Activity implements RadioGroup.OnCheckedChangeListener{

    Button btn_login_guest, btn_login_login, btn_login_register;
    public SharedPreferences prefs1, prefs2, prefs3;
    EditText edit_login_id, edit_login_passwd;
    public SharedPreferences login_mode, user_id, consultant_id,u_name,u_si,u_gu,u_tel,u_email;
    LinearLayout radio_layout;
    RadioButton radio_user, radio_consultant;
    RadioGroup radio_group;
    TextView text_find_id, text_find_passwd, text_connect;
    int mode_temp = 0;
    SharedPreferences selected_consultant_id, special_case;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        selected_consultant_id = getApplicationContext().getSharedPreferences("selected_consultant_id",0);
        special_case = getApplicationContext().getSharedPreferences("special_case",0);

        text_find_id = (TextView) findViewById(R.id.text_find_id);
        text_find_passwd = (TextView) findViewById(R.id.text_find_passwd);
        text_connect = (TextView) findViewById(R.id.text_connect);

        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        radio_layout = (LinearLayout) findViewById(R.id.login_radio_layout);
        radio_user = (RadioButton) findViewById(R.id.radio_user);
        radio_consultant = (RadioButton) findViewById(R.id.radio_consultant);
        radio_user.setChecked(true);

        mode_temp = 1;

        radio_group.setOnCheckedChangeListener(this);

        login_mode = getApplicationContext().getSharedPreferences("login_mode",0);

        user_id = getSharedPreferences("user_id", 0);
        SharedPreferences.Editor editor_id = user_id.edit();
        editor_id.putString("user_id","");

        consultant_id = getSharedPreferences("consultant_id", 0);
        SharedPreferences.Editor editor_con_id = consultant_id.edit();
        editor_con_id.putString("consultant_id","");

        u_name = getSharedPreferences("u_name", 0);
        SharedPreferences.Editor editor_1 = u_name.edit();
        editor_1.putString("u_name","");

        u_si = getSharedPreferences("u_si", 0);
        SharedPreferences.Editor editor_2 = u_si.edit();
        editor_2.putString("u_si","");

        u_gu = getSharedPreferences("u_gu", 0);
        SharedPreferences.Editor editor_3= u_gu.edit();
        editor_3.putString("u_gu","");

        u_tel = getSharedPreferences("u_tel", 0);
        SharedPreferences.Editor editor_4 = u_tel.edit();
        editor_4.putString("u_tel","");

        u_email = getSharedPreferences("u_email", 0);
        SharedPreferences.Editor editor_5= u_email.edit();
        editor_5.putString("u_email","");

        prefs1 = getSharedPreferences("si",0);
        SharedPreferences.Editor editor = prefs1.edit();
        editor.putString("si","서울");

        prefs2 = getSharedPreferences("gu",0);
        SharedPreferences.Editor editor2 = prefs2.edit();
        editor2.putString("gu","노원구");

        prefs3 = getSharedPreferences("bohum",0);
        SharedPreferences.Editor editor3 = prefs3.edit();
        editor3.putInt("bohum",0);


        btn_login_guest = (Button) findViewById(R.id.btn_login_guest);
        btn_login_login = (Button) findViewById(R.id.btn_login_login);
        btn_login_register = (Button) findViewById(R.id.btn_login_register);

        edit_login_id = (EditText) findViewById(R.id.edit_login_id);
        edit_login_passwd = (EditText) findViewById(R.id.edit_login_passwd);

        edit_login_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_login_id.setHint("");
                } else {
                    edit_login_id.setHint(" 아이디 입력");
                }
            }
        });

        edit_login_passwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_login_passwd.setHint("");
                } else {
                    edit_login_passwd.setHint(" 비밀번호 입력");
                }
            }
        });

        text_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindIDActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        text_find_passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindPasswdActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        login_mode = getApplicationContext().getSharedPreferences("login_mode",0);
        if((login_mode.getInt("login_mode",0) == 1) || (login_mode.getInt("login_mode",0) == 2)){
            btn_login_register.setText("로그아웃");
            btn_login_guest.setText("회원으로 보기");
            btn_login_login.setVisibility(View.INVISIBLE);
            edit_login_id.setVisibility(View.INVISIBLE);
            edit_login_passwd.setVisibility(View.INVISIBLE);
            radio_layout.setVisibility(View.INVISIBLE);
            text_find_id.setVisibility(View.INVISIBLE);
            text_find_passwd.setVisibility(View.INVISIBLE);
            text_connect.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(LoginActivity.this,Select_Service_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if(login_mode.getInt("login_mode",0) == 0){
            btn_login_guest.setText("비회원으로 보기");
            btn_login_register.setText("회원가입");
            btn_login_login.setVisibility(View.VISIBLE);
            edit_login_id.setVisibility(View.VISIBLE);
            edit_login_passwd.setVisibility(View.VISIBLE);
            btn_login_guest.setVisibility(View.VISIBLE);
            text_find_id.setVisibility(View.VISIBLE);
            text_find_passwd.setVisibility(View.VISIBLE);
        }

        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(edit_login_id.getText().toString(),edit_login_passwd.getText().toString());
            }
        });

        btn_login_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_mode.getInt("login_mode",0) == 0){
                    SharedPreferences.Editor editor_mode = login_mode.edit();
                    editor_mode.putInt("login_mode",0);
                    editor_mode.commit();

                    Intent intent = new Intent(LoginActivity.this, Select_Service_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(LoginActivity.this, Select_Service_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

            }
        });

        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_mode.getInt("login_mode",0) == 0){
                    Intent intent = new Intent(getApplicationContext(),Login_go_button_activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    SharedPreferences.Editor editor_mode = login_mode.edit();
                    editor_mode.putInt("login_mode",0);
                    editor_mode.commit();

                    Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (radio_group.getCheckedRadioButtonId() == R.id.radio_user) {
            mode_temp = 1;
        } else if (radio_group.getCheckedRadioButtonId() == R.id.radio_consultant) {
            mode_temp = 2;
        }
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_login, menu);
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
    private void login(String id_, String password_){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(LoginActivity.this, "확인 중입니다.", "Loading...");
            }

            @Override
            protected String doInBackground(String... params){

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id_", params[0]));
                nameValuePairs.add(new BasicNameValuePair("password_", params[1]));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    if(mode_temp == 2){
                        HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_consultant_login.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
                    } else {
                        HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_login.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
                    }

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


                if(s.equalsIgnoreCase("﻿" + "success")){
                    if(mode_temp == 1){

                        if(special_case.getInt("special_case",0) == 1){
                            Toast.makeText(getApplicationContext(),edit_login_id.getText().toString() + " 회원님 반갑습니다!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, SubActivity2.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("id",selected_consultant_id.getString("selected_consultant_id",""));

                            SharedPreferences.Editor editor_mode = login_mode.edit();
                            editor_mode.putInt("login_mode",1);
                            editor_mode.commit();

                            SharedPreferences.Editor editor_id = user_id.edit();
                            editor_id.putString("user_id", edit_login_id.getText().toString());
                            editor_id.commit();

                            SharedPreferences.Editor editor_special = special_case.edit();
                            editor_special.putInt("special_case", 0);
                            editor_special.commit();

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),edit_login_id.getText().toString() + " 회원님 반갑습니다!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, Select_Service_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            SharedPreferences.Editor editor_mode = login_mode.edit();
                            editor_mode.putInt("login_mode",1);
                            editor_mode.commit();

                            SharedPreferences.Editor editor_id = user_id.edit();
                            editor_id.putString("user_id", edit_login_id.getText().toString());
                            editor_id.commit();

                            startActivity(intent);
                            finish();
                        }


                    } else {

                        if(special_case.getInt("special_case",0) == 1){
                            Toast.makeText(getApplicationContext(),edit_login_id.getText().toString() + " 설계사님 반갑습니다!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, SubActivity2.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("id",selected_consultant_id.getString("selected_consultant_id",""));


                            SharedPreferences.Editor editor_mode = login_mode.edit();
                            editor_mode.putInt("login_mode",2);
                            editor_mode.commit();

                            SharedPreferences.Editor editor_con_id = consultant_id.edit();
                            editor_con_id.putString("consultant_id", edit_login_id.getText().toString());
                            editor_con_id.commit();

                            SharedPreferences.Editor editor_special = special_case.edit();
                            editor_special.putInt("special_case", 0);
                            editor_special.commit();

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),edit_login_id.getText().toString() + " 설계사님 반갑습니다!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, Select_Service_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            SharedPreferences.Editor editor_mode = login_mode.edit();
                            editor_mode.putInt("login_mode",2);
                            editor_mode.commit();

                            SharedPreferences.Editor editor_con_id = consultant_id.edit();
                            editor_con_id.putString("consultant_id", edit_login_id.getText().toString());
                            editor_con_id.commit();

                            startActivity(intent);
                            finish();
                        }
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인 해주세요.", Toast.LENGTH_LONG).show();
                    System.out.println(s);
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(id_,password_);
    }


}
