package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ViewPage extends Activity {

    private TextView bti;
    private TextView bco;

    String returnTi;
    String returnCo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
        bti = (TextView) findViewById(R.id.Title);
        bco = (TextView) findViewById(R.id.Content);
        final String getResult = getIntent().getStringExtra("b_no");

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("");

        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("b_no", getResult));
        String response = null;

        try {
            response = CustomHttpClient.executeHttpPost("http://woodongsa.cafe24.com/app_nam/view_get_db.php", postParameters);
            String result = response.toString();
            try{
                returnTi = "";
                returnCo = "";
                if(result == null){
                    AlertDialog dialog = createDialogBox_network();
                    dialog.show();
                } else {
                    JSONArray jArray = new JSONArray(result);
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        Log.i("log_tag", "b_no: " + json_data.getString("b_no")
                                + ", b_title: " + json_data.getString("b_title")
                                + ", b_content: " + json_data.getString("b_content")
                                + ", b_date: " + json_data.getString("b_date")
                                + ", b_hit: " + json_data.getString("b_hit")
                                + ", b_id: " + json_data.getString("b_id")
                                + ", b_password: " + json_data.getString("b_password"));
                        returnTi = json_data.getString("b_title");
                        returnCo = json_data.getString("b_content");
                    }
                }
            }
            catch(JSONException e){
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
            try{
                bti.setText(returnTi);
                bco.setText(returnCo);
            }
            catch(Exception e){
                Log.e("log_tag","Error in Display!" + e.toString());
            }
        }
        catch (Exception e) {
            Log.e("log_tag","Error in http connection!!" + e.toString());
        }
    }

    private AlertDialog createDialogBox_network(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPage.this);

        builder.setTitle("네트워크가 불안정 합니다.");
        builder.setMessage("네트워크 연결을 확인 해 주세요.");

        builder.setPositiveButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(ViewPage.this, ViewPage.class);
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
        getMenuInflater().inflate(R.menu.menu_view_page, menu);
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
