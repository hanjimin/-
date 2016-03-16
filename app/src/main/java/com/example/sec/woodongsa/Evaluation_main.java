package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sec.woodongsa.evaluation.Evaluation_Content_Activity;
import com.example.sec.woodongsa.evaluation.IconTextItem_evaluation_main;
import com.example.sec.woodongsa.evaluation.IconTextListAdapter_evaluation_main;
import com.squareup.picasso.Picasso;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Evaluation_main extends Activity {

    ListView list_evaluation_main;
    Button btn_evaluation_main_write;
    String myJSON;
    JSONArray sub1_list = null;
    SharedPreferences u_nickname, user_id, login_mode;
    IconTextListAdapter_evaluation_main adapter;
    SwipeRefreshLayout Evaluation_main_refreshlayout;
    int login_mode_temp = 0;
    SharedPreferences user_si, user_gu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_main);

        list_evaluation_main = (ListView) findViewById(R.id.list_evaluation_main);
        btn_evaluation_main_write = (Button) findViewById(R.id.btn_evaluation_main_write);

        Evaluation_main_refreshlayout = (SwipeRefreshLayout) findViewById(R.id.evaluation_main_refresh_layout);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("");

        login_mode = getApplicationContext().getSharedPreferences("login_mode",0);
        login_mode_temp = login_mode.getInt("login_mode",0);



        sms_getList("a");

        Evaluation_main_refreshlayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        Evaluation_main_refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sms_getList("a");
                        Evaluation_main_refreshlayout.setRefreshing(false);
                    }
                }, 2500);

            }
        });

        if(login_mode_temp == 2){
            btn_evaluation_main_write.setVisibility(View.INVISIBLE);
        } else {
            btn_evaluation_main_write.setVisibility(View.VISIBLE);
        }


        btn_evaluation_main_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(Evaluation_main.this, Evaluation_Write_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                */

                if(login_mode_temp == 0){
                    Toast.makeText(getApplicationContext(),"로그인이 필요한 서비스입니다.",Toast.LENGTH_SHORT).show();
                } else if(login_mode_temp == 1){
                    u_nickname = getApplicationContext().getSharedPreferences("u_nickname",0);
                    user_id = getApplicationContext().getSharedPreferences("user_id",0);
                    user_si = getApplicationContext().getSharedPreferences("u_si",0);
                    user_gu = getApplicationContext().getSharedPreferences("u_gu",0);

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://woodongsa.com/board/evaluation_write_app.php?a_nickname=" + u_nickname.getString("u_nickname","") + "&a_id=" + user_id.getString("user_id","") + "&a_si=" + user_si.getString("u_si","") + "&a_gu=" + user_gu.getString("u_gu","")));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"일반회원님만 사용하실 수 있는 서비스 입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    protected void sms_getList(String a){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            Dialog loadingDialog;

            protected void onPreExecute(){
                super.onPreExecute();
                //loadingDialog = ProgressDialog.show(Evaluation_main.this, "리스트 불러오는 중", "Loading...");
            }
            @Override
            protected String doInBackground(String... params) {

                String paramSi = params[0];


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("u_id", paramSi));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_get_evaluation_main_list.php");

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
                showList();
                //loadingDialog.dismiss();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(a);
    }

    protected void showList(){
        try {
            if(myJSON == null){
                AlertDialog dialog = createDialogBox();
                dialog.show();
            } else {
                JSONObject jsonObject = new JSONObject(myJSON);
                sub1_list = jsonObject.getJSONArray("result");
                System.out.println(sub1_list.length());

                if(sub1_list.length() != 0){
                    adapter = new IconTextListAdapter_evaluation_main(getApplicationContext());
                    Resources res = getResources();

                    for(int i=0;i<sub1_list.length();i++) {
                        JSONObject c = sub1_list.getJSONObject(i);


                        String imageURL = "http://woodongsa.com/board/upload/" + c.getString("img_src");
                        System.out.println(imageURL);

                        //setImage(imageURL);


                        adapter.addItem(new IconTextItem_evaluation_main(imageURL, c.getString("b_title"), c.getString("b_id"), c.getString("user_si"), c.getString("user_gu"), c.getString("b_date"), c.getString("b_content"), c.getString("b_hit"), c.getString("comment_num"), c.getString("b_no")));


                        list_evaluation_main.setAdapter(adapter);

                        list_evaluation_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                IconTextItem_evaluation_main curItem = (IconTextItem_evaluation_main) adapter.getItem(position);
                                String[] curData = curItem.getData();


                                Intent intent = new Intent(getApplicationContext(), Evaluation_Content_Activity.class);
                                intent.putExtra("nickname", curData[1]);
                                intent.putExtra("date_time",curData[4]);
                                intent.putExtra("b_no",curData[8]);
                                startActivity(intent);

                             //   Toast.makeText(Evaluation_main.this, "Selected : " + curData[0], Toast.LENGTH_LONG).show();

                            }

                        });
                    }
                } else {
                  //  Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private AlertDialog createDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Evaluation_main.this);

        builder.setTitle("네트워크가 불안정 합니다.");
        builder.setMessage("네트워크 연결을 확인 해 주세요.");

        builder.setPositiveButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(Evaluation_main.this, Evaluation_main.class);
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
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluation_main, menu);
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
            Intent intent = new Intent(this, Select_Service_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
