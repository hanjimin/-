package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SubActivity2 extends Activity {
    TextView sub2_introduce, sub2_code_num;
    ImageView sub2_image;
    String myJSON, myJSON1;
    JSONArray sub2_list = null;
    JSONArray sub2_list1 = null;
    String id = null;
    String sub2_phone_num, image_url, name,sosok,age,certificateDate,pay,major_item,certification,prize;
    String career, introduce, c_id, passwd, si, gu, code_num;
    Button sub2_btn_zzim, sub2_btn_sms;
    Dialog loadingDialog;
    SharedPreferences user_id, login_mode;
    SharedPreferences u_name,u_si,u_gu,u_tel,u_email,selected_consultant_id;
    TextView sub2_text_comment_list, sub2_text_comment_list_click;
    String comment_num = "0";
    SharedPreferences special_case;

    ImageLoader imageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_activity2);

        ActionBarInit();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        selected_consultant_id = getApplicationContext().getSharedPreferences("selected_consultant_id",0);

        SharedPreferences.Editor editor_select = selected_consultant_id.edit();
        editor_select.putString("selected_consultant_id", id);
        editor_select.commit();

        special_case = getApplicationContext().getSharedPreferences("special_case",0);

        user_id = getApplicationContext().getSharedPreferences("user_id",0);
        login_mode = getApplicationContext().getSharedPreferences("login_mode",0);
        u_name = getApplicationContext().getSharedPreferences("u_name",0);
        u_si = getApplicationContext().getSharedPreferences("u_si",0);
        u_gu = getApplicationContext().getSharedPreferences("u_gu",0);
        u_tel = getApplicationContext().getSharedPreferences("u_tel",0);
        u_email = getApplicationContext().getSharedPreferences("u_email",0);

        sub2_code_num = (TextView) findViewById(R.id.sub2_code_num);
        sub2_introduce = (TextView) findViewById(R.id.sub2_introduce);
        sub2_image = (ImageView) findViewById(R.id.sub2_image);
        sub2_btn_sms = (Button) findViewById(R.id.sub2_btn_sms);
        sub2_btn_zzim = (Button) findViewById(R.id.sub2_btn_zzim);
        sub2_text_comment_list = (TextView) findViewById(R.id.sub2_text_comment_list);
        sub2_text_comment_list_click = (TextView) findViewById(R.id.sub2_text_comment_list_click);

        sub2_setList(id);

        if(login_mode.getInt("login_mode",0) == 1 || login_mode.getInt("login_mode",0) == 0){
            sub2_btn_zzim.setEnabled(true);
            sub2_btn_sms.setEnabled(true);
        } else {
            sub2_btn_zzim.setVisibility(View.INVISIBLE);
            sub2_btn_sms.setVisibility(View.INVISIBLE);
        }

        sub2_btn_zzim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_mode.getInt("login_mode",0) == 1){
                    zzim_check(user_id.getString("user_id",""),id);
                } else {
                    AlertDialog dialog = createDialogBox_zzim_sms();
                    dialog.show();
                }
            }
        });

        sub2_btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_mode.getInt("login_mode",0) == 1){
                    AlertDialog dialog = createDialogBox();
                    dialog.show();
                } else {
                    AlertDialog dialog = createDialogBox_zzim_sms();
                    dialog.show();
                }
            }
        });

        sub2_text_comment_list_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment_num.equals("0")){
                    Toast.makeText(getApplicationContext(),"댓글 내역이 없습니다.",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(SubActivity2.this, Show_Consultant_Comment_List.class);
                    startActivity(intent1);
                }
            }
        });

    }



    private AlertDialog createDialogBox_zzim_sms(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SubActivity2.this);

        builder.setTitle("로그인이 필요한 서비스입니다.");
        builder.setMessage("로그인 화면으로 가시겠습니까?");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor_special = special_case.edit();
                editor_special.putInt("special_case", 1);
                editor_special.commit();

                Intent intent1 = new Intent(SubActivity2.this, LoginActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);

            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }


    protected void zzim_check(String u_id, String c_id){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(SubActivity2.this, "찜 목록 확인 중입니다.", "Loading...");
            }

            @Override
            protected String doInBackground(String... params){
                String pass = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("u_id", params[0]));
                nameValuePairs.add(new BasicNameValuePair("c_id", params[1]));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_zzim_check.php");
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
                    Toast.makeText(getApplicationContext(),"이미 찜 목록에 존재합니다.", Toast.LENGTH_SHORT).show();
                }else {
                    zzim(user_id.getString("user_id", ""), id, name, sosok, age, certificateDate, pay, major_item, certification, prize, career, introduce, passwd, si, gu, image_url, sub2_phone_num, code_num, "zzim","","","","","");
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(u_id,c_id);
    }

    private void zzim(String a, String b, String c, String d, String e, String f, String g, String h, String i, String j, String k, String l, String m, String n, String o, String p, String q, String r, String s, String t, String u, String v, String w, String x){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("u_id", params[0]));
                nameValuePairs.add(new BasicNameValuePair("c_id", params[1]));
                nameValuePairs.add(new BasicNameValuePair("name", params[2]));
                nameValuePairs.add(new BasicNameValuePair("sosok", params[3]));
                nameValuePairs.add(new BasicNameValuePair("age", params[4]));
                nameValuePairs.add(new BasicNameValuePair("certificateDate", params[5]));
                nameValuePairs.add(new BasicNameValuePair("pay", params[6]));
                nameValuePairs.add(new BasicNameValuePair("major_item", params[7]));
                nameValuePairs.add(new BasicNameValuePair("certification", params[8]));
                nameValuePairs.add(new BasicNameValuePair("prize", params[9]));
                nameValuePairs.add(new BasicNameValuePair("career", params[10]));
                nameValuePairs.add(new BasicNameValuePair("introduce", params[11]));
                nameValuePairs.add(new BasicNameValuePair("passwd", params[12]));
                nameValuePairs.add(new BasicNameValuePair("si", params[13]));
                nameValuePairs.add(new BasicNameValuePair("gu", params[14]));
                nameValuePairs.add(new BasicNameValuePair("img", params[15]));
                nameValuePairs.add(new BasicNameValuePair("phone_num", params[16]));
                nameValuePairs.add(new BasicNameValuePair("code_num", params[17]));
                nameValuePairs.add(new BasicNameValuePair("u_name", params[19]));
                nameValuePairs.add(new BasicNameValuePair("u_si", params[20]));
                nameValuePairs.add(new BasicNameValuePair("u_gu", params[21]));
                nameValuePairs.add(new BasicNameValuePair("u_tel", params[22]));
                nameValuePairs.add(new BasicNameValuePair("u_email", params[23]));


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost;
                    if(params[18].equals("zzim")){
                        httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_insert_zzim.php");
                    } else {
                        httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_insert_sms.php");
                    }

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                if(params[18].equals("zzim")){
                    return "찜한 설계사 목록은 '마이페이지' 에서 확인 할 수 있습니다.";
                } else {
                    return "상담신청 한 설계사 목록은 '마이페이지' 에서 확인 할 수 있습니다.";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x);
    }

    private AlertDialog createDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SubActivity2.this);



        builder.setTitle("연락처를 보내시겠습니까?");
        builder.setMessage(u_tel.getString("u_tel","") +
                "\n확인 시 위의 번호가 문자로 발송됩니다.\n" +
                "현재 사용하시는 번호와 다를 경우 '마이페이지'에서 수정해주세요.");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                sms_check(user_id.getString("user_id",""),id);

            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    protected void sms_check(String u_id, String c_id){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(SubActivity2.this, "목록 확인 중입니다.", "Loading...");
            }

            @Override
            protected String doInBackground(String... params){
                String pass = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("u_id", params[0]));
                nameValuePairs.add(new BasicNameValuePair("c_id", params[1]));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_sms_check.php");
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
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(result == null){
                    AlertDialog dialog = createDialogBox();
                    dialog.show();
                    return result;
                } else {
                    return result;
                }
            }
            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Toast.makeText(getApplicationContext(),"이미 연락한 설계사 입니다.", Toast.LENGTH_SHORT).show();
                }else {
                    try
                    {
                        sendMsg(id, name, sub2_phone_num, si, gu, u_name.getString("u_name",""), u_tel.getString("u_tel",""), u_si.getString("u_si",""), u_gu.getString("u_gu",""));
                 //       sendMsg_to_user(id, name, sub2_phone_num, si, gu, u_name.getString("u_name",""), u_tel.getString("u_tel",""), u_si.getString("u_si",""), u_gu.getString("u_gu",""));
                        //SmsManager smsManager = SmsManager.getDefault();
                        //smsManager.sendTextMessage(sub2_phone_num, null, "fuck you", null, null);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "문자 보내기 실패, 나중에 다시 시도해 주세요", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    zzim(user_id.getString("user_id",""),id,name,sosok,age,certificateDate,pay,major_item,certification,prize,career,introduce,passwd,si,gu,image_url,sub2_phone_num,code_num,"sms",u_name.getString("u_name",""),u_si.getString("u_si",""),u_gu.getString("u_gu",""),u_tel.getString("u_tel",""),u_email.getString("u_email",""));
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(u_id,c_id);
    }

    protected void sub2_setList(String a){
        class GetDataJSON extends AsyncTask<String, Void, String> {


            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(SubActivity2.this, "리스트 불러오는 중", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {

                String paramID = params[0];
                String sub2_list_id = id;


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("sub2_list_item_id", sub2_list_id));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_sub2_list_item.php");

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
        g.execute(a);
    }

    protected void setList(){
        try {
            if(myJSON == null){
                AlertDialog dialog = createDialogBox_network();
                dialog.show();
            } else{
                JSONObject jsonObject = new JSONObject(myJSON);
                sub2_list = jsonObject.getJSONArray("result");

                Resources res = getResources();
                for(int i=0;i<sub2_list.length();i++){
                    JSONObject c = sub2_list.getJSONObject(i);

                    name = c.getString("sub1_list_item_name");
                    sosok = c.getString("sub1_list_item_corp_name");
                    age = c.getString("sub1_list_item_age");
                    certificateDate = c.getString("sub1_list_item_certificateDate");
                    pay = c.getString("sub1_list_item_pay");
                    major_item = c.getString("sub1_list_item_major");
                    certification = c.getString("sub1_list_item_certification");
                    prize = c.getString("sub1_list_item_prize");
                    career = c.getString("sub1_list_item_career");
                    introduce = c.getString("sub1_list_item_introduce");
                    c_id = c.getString("sub1_list_item_id");
                    passwd = c.getString("sub1_list_item_passwd");
                    si = c.getString("sub1_list_item_si");
                    gu = c.getString("sub1_list_item_gu");
                    code_num = c.getString("sub1_list_item_code_num");
                    comment_num = c.getString("comment_num");

                    String code_num_temp = code_num.substring(0,4) + " - " + code_num.substring(4);

                    sub2_text_comment_list.setText("(" + c.getString("comment_num") + ")");

                    String temp = c.getString("sub1_list_item_introduce");
                    temp = temp.replace("<br>","\r\n");

                    sub2_introduce.setText(temp);
                    sub2_code_num.setText(code_num_temp);
                    image_url = c.getString("sub1_list_item_image");
                    sub2_phone_num = c.getString("sub1_list_item_phone_num");
                    String imageURL = "http://woodongsa.cafe24.com/" + image_url;

                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.drawable.loading_image_good)
                            .showImageForEmptyUri(R.drawable.no_image)
                            .showImageOnFail(R.drawable.no_image)
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .considerExifParams(true)
                            .build();

                    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                            .writeDebugLogs()
                            .defaultDisplayImageOptions(options)
                            .diskCacheExtraOptions(480,320,null)
                            .build();



                    imageLoader.getInstance().init(config);

                    imageLoader.getInstance().displayImage(imageURL, sub2_image, options);

                    loadingDialog.dismiss();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private AlertDialog createDialogBox_network(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SubActivity2.this);

        builder.setTitle("네트워크가 불안정 합니다.");
        builder.setMessage("네트워크 연결을 확인 해 주세요.");

        builder.setPositiveButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(SubActivity2.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

        builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(SubActivity2.this, LoginActivity.class);
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
            getMenuInflater().inflate(R.menu.menu_sub_activity2, menu);
            return true;
        }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(SubActivity2.this, Select_Service_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void ActionBarInit(){

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("");
    }

    private void sendMsg(String c_id, String c_name, String c_phone_num, String c_si, String c_gu, String user_name, String user_tel, String user_si, String user_gu) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramc_id = params[0];
                String paramc_name = params[1];
                String paramc_phone_num = params[2];
                String paramc_si = params[3];
                String paramc_gu = params[4];
                String paramuser_name = params[5];
                String paramuser_tel = params[6];
                String paramuser_si = params[7];
                String paramuser_gu = params[8];

                String c_id = id;
                String c_name = name;
                String c_phone_num = sub2_phone_num;
                String c_si = si;
                String c_gu = gu;
                String user_name = u_name.getString("u_name","");
                String user_tel = u_tel.getString("u_tel","");
                String user_si = u_si.getString("u_si","");
                String user_gu = u_gu.getString("u_gu","");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("c_id", c_id));
                nameValuePairs.add(new BasicNameValuePair("c_name", c_name));
                nameValuePairs.add(new BasicNameValuePair("c_phone_num", c_phone_num));
                nameValuePairs.add(new BasicNameValuePair("c_si", c_si));
                nameValuePairs.add(new BasicNameValuePair("c_gu", c_gu));
                nameValuePairs.add(new BasicNameValuePair("user_name", user_name));
                nameValuePairs.add(new BasicNameValuePair("user_tel", user_tel));
                nameValuePairs.add(new BasicNameValuePair("user_si", user_si));
                nameValuePairs.add(new BasicNameValuePair("user_gu", user_gu));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/dotname_sms_app/apitool/now_sms_send.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();
                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "연락처 남기기 완료";
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(c_id, c_name, c_phone_num, c_si, c_gu, user_name, user_tel, user_si, user_gu);
    }

    private void sendMsg_to_user(String c_id, String c_name, String c_phone_num, String c_si, String c_gu, String user_name, String user_tel, String user_si, String user_gu) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramc_id = params[0];
                String paramc_name = params[1];
                String paramc_phone_num = params[2];
                String paramc_si = params[3];
                String paramc_gu = params[4];
                String paramuser_name = params[5];
                String paramuser_tel = params[6];
                String paramuser_si = params[7];
                String paramuser_gu = params[8];

                String c_id = id;
                String c_name = name;
                String c_phone_num = sub2_phone_num;
                String c_si = si;
                String c_gu = gu;
                String user_name = u_name.getString("u_name","");
                String user_tel = u_tel.getString("u_tel","");
                String user_si = u_si.getString("u_si","");
                String user_gu = u_gu.getString("u_gu","");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("c_id", c_id));
                nameValuePairs.add(new BasicNameValuePair("c_name", c_name));
                nameValuePairs.add(new BasicNameValuePair("c_phone_num", c_phone_num));
                nameValuePairs.add(new BasicNameValuePair("c_si", c_si));
                nameValuePairs.add(new BasicNameValuePair("c_gu", c_gu));
                nameValuePairs.add(new BasicNameValuePair("user_name", user_name));
                nameValuePairs.add(new BasicNameValuePair("user_tel", user_tel));
                nameValuePairs.add(new BasicNameValuePair("user_si", user_si));
                nameValuePairs.add(new BasicNameValuePair("user_gu", user_gu));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/dotname_sms_app/apitool/now_sms_send_to_user.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();
                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "연락처 남기기 완료";
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(c_id, c_name, c_phone_num, c_si, c_gu, user_name, user_tel, user_si, user_gu);
    }
}
