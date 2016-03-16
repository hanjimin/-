package com.example.sec.woodongsa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class LoadingActivity extends Activity {

    ImageView image_loading;
    SharedPreferences login_mode,selected_consultant_id;
    SharedPreferences special_case;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        image_loading = (ImageView) findViewById(R.id.image_loading);
        image_loading.setImageResource(R.drawable.woodongsa_loading_screen);

        login_mode = getSharedPreferences("login_mode", 0);
        SharedPreferences.Editor editor_login = login_mode.edit();
        editor_login.putInt("login_mode",0);

        selected_consultant_id = getSharedPreferences("selected_consultant_id", 0);
        SharedPreferences.Editor editor_select = selected_consultant_id.edit();
        editor_select.putString("selected_consultant_id","");

        special_case = getSharedPreferences("special_case", 0);
        SharedPreferences.Editor editor_special = special_case.edit();
        editor_special.putInt("special_case",0);



        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {
                    login_mode = getApplicationContext().getSharedPreferences("login_mode",0);

                    if(login_mode.getInt("login_mode",0) == 0){
                        Intent i = new Intent(LoadingActivity.this,
                                LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(LoadingActivity.this,
                                Select_Service_Activity.class);
                        startActivity(i);
                        finish();
                    }


                }
            }
        };
        welcomeThread.start();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
