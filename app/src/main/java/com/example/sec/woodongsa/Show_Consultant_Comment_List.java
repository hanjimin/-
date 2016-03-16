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
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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


public class Show_Consultant_Comment_List extends Activity {

    ListView consultant_comment_list;
    String myJSON;
    JSONArray sub1_list = null;
    SharedPreferences u_nickname, user_id, login_mode, selected_consultant_id;
    IconTextListAdapter_evaluation_main adapter;
    SwipeRefreshLayout comment_list_refresh_layout;
    int login_mode_temp = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__consultant__comment__list);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("");


        selected_consultant_id = getApplicationContext().getSharedPreferences("selected_consultant_id",0);

        comment_list_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.comment_list_refresh_layout);
        consultant_comment_list = (ListView) findViewById(R.id.consultant_comment_list);

        sms_getList(selected_consultant_id.getString("selected_consultant_id",""));

        System.out.println(selected_consultant_id.getString("selected_consultant_id",""));


        comment_list_refresh_layout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        comment_list_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sms_getList(selected_consultant_id.getString("selected_consultant_id",""));
                        comment_list_refresh_layout.setRefreshing(false);
                    }
                }, 2500);

            }
        });

    }

    protected void sms_getList(String a){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            Dialog loadingDialog;

            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Show_Consultant_Comment_List.this, "리스트 불러오는 중", "Loading...");
            }
            @Override
            protected String doInBackground(String... params) {

                String paramSi = params[0];


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("c_id", params[0]));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_get_consultant_comment_list.php");

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
                loadingDialog.dismiss();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(a);
    }

    protected void showList(){
        try {
            if(myJSON == null){
                AlertDialog dialog = createDialogBox_network();
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


                        consultant_comment_list.setAdapter(adapter);

                        consultant_comment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                IconTextItem_evaluation_main curItem = (IconTextItem_evaluation_main) adapter.getItem(position);
                                String[] curData = curItem.getData();


                                Intent intent = new Intent(getApplicationContext(), Evaluation_Content_Activity.class);
                                intent.putExtra("nickname", curData[1]);
                                intent.putExtra("date_time",curData[4]);
                                intent.putExtra("b_no",curData[8]);
                                startActivity(intent);

                               // Toast.makeText(Show_Consultant_Comment_List.this, "Selected : " + curData[0], Toast.LENGTH_LONG).show();

                            }

                        });
                    }
                } else {
                    //Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private AlertDialog createDialogBox_network(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Show_Consultant_Comment_List.this);

        builder.setTitle("네트워크가 불안정 합니다.");
        builder.setMessage("네트워크 연결을 확인 해 주세요.");

        builder.setPositiveButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(Show_Consultant_Comment_List.this, Show_Consultant_Comment_List.class);
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

    public static Drawable createDrawableFromUrl(String imageWebAddress)
    {
        Drawable drawable = null;

        try
        {
            InputStream inputStream = new URL(imageWebAddress).openStream();
            drawable = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        }
        catch (MalformedURLException ex) { }
        catch (IOException ex) { }

        return drawable;
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show__consultant__comment__list, menu);
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
