package com.example.sec.woodongsa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class Certificate_Webview_Consultant_Activity4 extends Activity {

    private WebView webView;
    private Handler mHandler;
    private boolean mFlag = false;
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
        setContentView(R.layout.activity_certificate__webview_);

        ActionBarInit();

        webView = (WebView) findViewById(R.id.webview);

        webView.setWebViewClient(new WebClient());
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        webView.loadUrl("http://woodongsa.com/app/app_register_consultant.php");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplicationContext(), Login_go_button_activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            finish();
        }
        return false;
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

            if(url.startsWith("http://woodongsa.com/app/app_register_step3_consultant.php") || url.startsWith("http://woodongsa.cafe24.com/app/app_register_step3_consultant.php")){
                String result = Uri.parse(url).getQueryParameter("result");
                String name = Uri.parse(url).getQueryParameter("name");
                String gender = Uri.parse(url).getQueryParameter("gender");
                String phoneNo = Uri.parse(url).getQueryParameter("phoneNo");
                String birthday = Uri.parse(url).getQueryParameter("birthDay");
                if(result != null && result.equals("Y")){
                    Intent intent = new Intent(getApplicationContext(),RegisterActivity_Consultant.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("name",name);
                    intent.putExtra("gender",gender);
                    intent.putExtra("phoneNo",phoneNo);
                    intent.putExtra("birthday",birthday);

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Certificate_Webview_Consultant_Activity4.this, "실패하였습니다", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            view.loadUrl(url);
            return true;
        }
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_certificate__webview_, menu);
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
                Intent intent = new Intent(Certificate_Webview_Consultant_Activity4.this, Select_Service_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ActionBarInit(){

        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
