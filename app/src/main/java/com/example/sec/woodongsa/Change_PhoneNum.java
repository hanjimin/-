package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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


public class Change_PhoneNum extends Activity {

    private WebView webView;
    private Handler mHandler;
    private boolean mFlag = false;
    private SharedPreferences user_id;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__phone_num);

        ActionBarInit();
        user_id = getApplicationContext().getSharedPreferences("user_id",0);

        webView = (WebView) findViewById(R.id.webview);

        webView.setWebViewClient(new WebClient());
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        webView.loadUrl("http://woodongsa.com/app/app_register.php");

        webView.setOnKeyListener(new View.OnKeyListener(){

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {

                    if(webView.canGoBack()){
                        handler.sendEmptyMessage(1);
                    } else {
                        Intent intent = new Intent(Change_PhoneNum.this, Mypage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("current_mypage_tab_num",1);
                        finish();
                        startActivity(intent);
                    }


                    return true;
                }
                return false;
            }

        });
    }

    private void webViewGoBack(){
        webView.goBack();
    }

    class WebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view,String url,Bitmap favicon){
            super.onPageStarted(view, url, favicon);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url){
            System.out.println(url);

            if(url.startsWith("http://woodongsa.com/app/app_register_step3.php") || url.startsWith("http://woodongsa.cafe24.com/app/app_register_step3.php")){
                String result = Uri.parse(url).getQueryParameter("result");
                String name = Uri.parse(url).getQueryParameter("name");
                String gender = Uri.parse(url).getQueryParameter("gender");
                String phoneNo = Uri.parse(url).getQueryParameter("phoneNo");
                if(result != null && result.equals("Y")){

                    //update_phone_num(user_id.getString("user_id",""), phoneNo);

                    Intent intent = new Intent(getApplicationContext(),Change_PhoneNum_Process.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("phoneNo",phoneNo);
                    finish();
                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "실패하였습니다", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            view.loadUrl(url);
            return true;
        }
    }

    private void update_phone_num(String a, String b){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getApplicationContext(), "수정 중 입니다.", "Loading...");
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



    private void ActionBarInit(){

        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change__phone_num, menu);
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
            Intent intent = new Intent(Change_PhoneNum.this, Select_Service_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
}


}
