package com.example.sec.woodongsa;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Login_go_button_activity extends Activity {

    private Button btn_go_register;
    private Button btn_go_register_consultant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_go_button_activity);

        ActionBarInit();

        btn_go_register = (Button) findViewById(R.id.btn_go_register);
        btn_go_register_consultant = (Button) findViewById(R.id.btn_go_register_consultant);

        btn_go_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Certificate_Webview_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(),"현재는 테스트기간입니다. 회원가입은 정상적으로 진행됩니다.",Toast.LENGTH_LONG).show();
            }
        });

        btn_go_register_consultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Certificate_Webview_Consultant_Activity4.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(),"현재는 테스트기간입니다. 회원가입은 정상적으로 진행됩니다.",Toast.LENGTH_LONG).show();
            }
        });

    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_go_button_activity, menu);
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
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            finish();
        }
        return false;
    }


    private void ActionBarInit(){

        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
