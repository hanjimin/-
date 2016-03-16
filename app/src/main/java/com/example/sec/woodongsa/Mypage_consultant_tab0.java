package com.example.sec.woodongsa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


public class Mypage_consultant_tab0 extends Fragment {

    TextView consultant_num, user_num, sms_num;
    String myJSON;
    JSONArray num_list = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_mypage_consultant_tab0, container, false);

        consultant_num = (TextView) rootView.findViewById(R.id.consultant_num);
        user_num = (TextView) rootView.findViewById(R.id.user_num);
        sms_num = (TextView) rootView.findViewById(R.id.sms_num);

        show_current_app("");


        return rootView;
    }

    protected void show_current_app(String a){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            Dialog loadingDialog;

            protected void onPreExecute(){
                super.onPreExecute();
       //         loadingDialog = ProgressDialog.show(getActivity(), "리스트 불러오는 중", "Loading...");
            }
            @Override
            protected String doInBackground(String... params) {

                String paramSi = params[0];


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("", paramSi));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_get_current_app.php");

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
                System.out.println(result + " 간디라댜라ㅓ댜ㅣㅓㅏ어ㅣ랻ㄹ");
                showList();
            //    loadingDialog.dismiss();
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
            }
            JSONObject jsonObject = new JSONObject(myJSON);
            num_list = jsonObject.getJSONArray("result");

            if(num_list.length() != 0){


                for(int i=0;i<num_list.length();i++) {
                    JSONObject c = num_list.getJSONObject(i);

                    consultant_num.setText(c.getString("consultant_member_num") + " 명");
                    user_num.setText(c.getString("user_num") + " 명");
                    sms_num.setText(c.getString("sms_num") + " 명");

                }
            } else {
                //Toast.makeText(getActivity(),"상담중인 회원이 없습니다.",Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private AlertDialog createDialogBox_network(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("네트워크가 불안정 합니다.");
        builder.setMessage("네트워크 연결을 확인 해 주세요.");

        builder.setPositiveButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(getActivity(), Mypage_consultant.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

        builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                getActivity().finish();
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }


}
