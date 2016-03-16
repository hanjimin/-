package com.example.sec.woodongsa;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Mypage_consultant_tab3 extends Fragment {

    public ListView listView1;
    SharedPreferences con_id;
    String myJSON, myJSON1;
    JSONArray sub1_list = null;
    IconTextAdapter_user adapter;
    public int user_index;
    String u_id,c_id,img,u_name,u_si,u_gu,u_tel,u_email, complete_bohum_temp;

    private static final int MENU_ID_MODIFY = Menu.FIRST;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_mypage_consultant_tab3, container, false);

        listView1 = (ListView) rootView.findViewById(R.id.mypage_consultant_sms_list);
        registerForContextMenu(listView1);

        con_id = getActivity().getSharedPreferences("consultant_id",0);

        sms_getList(con_id.getString("consultant_id", ""));

        registerForContextMenu(listView1);

        return rootView;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);

        menu.setHeaderTitle("계약여부");
        menu.add(0,MENU_ID_MODIFY,Menu.NONE, "완료");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        super.onContextItemSelected(item);

        AdapterView.AdapterContextMenuInfo menuInfo;
        int index;
        switch (item.getItemId()) {
            case MENU_ID_MODIFY:
                menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                index = menuInfo.position;
                user_index = index;

                IconTextItem_user curItem = (IconTextItem_user) adapter.getItem(user_index);
                String[] curData = curItem.getData();

                delete_sms(curData[1],con_id.getString("consultant_id",""));

                Toast.makeText(getActivity(),curData[1] + "계약이 완료되었습니다!",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(),Mypage_consultant.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("current_mypage_tab_num", 3);
                intent.putExtra("u_id",u_id);
                intent.putExtra("c_id",c_id);
                intent.putExtra("img",img);
                intent.putExtra("u_name",u_name);
                intent.putExtra("u_si",u_si);
                intent.putExtra("u_gu",u_gu);
                intent.putExtra("u_tel",u_tel);
                intent.putExtra("u_email",u_email);
                startActivity(intent);
/*          보험 상품 입력 기능
                LayoutInflater inflater = getActivity().getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.dialog_set_bohum,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("계약한 보험상품 입력");
                builder.setView(dialogView);
                builder.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText complete_bohum = (EditText) dialogView.findViewById(R.id.dialog_edit_bohum);
                        if(complete_bohum.getText().toString().equals("")){
                            Toast.makeText(getActivity(),"내용을 입력하세요.",Toast.LENGTH_SHORT).show();
                        } else {
                            complete_bohum_temp = complete_bohum.getText().toString();
                            IconTextItem_user curItem = (IconTextItem_user) adapter.getItem(user_index);
                            String[] curData = curItem.getData();

                            delete_sms(curData[1],con_id.getString("consultant_id",""));

                            Toast.makeText(getActivity(),curData[1] + "계약완료!",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getActivity(),Mypage_consultant.class);
                            intent.putExtra("current_mypage_tab_num", 3);
                            intent.putExtra("u_id",u_id);
                            intent.putExtra("c_id",c_id);
                            intent.putExtra("img",img);
                            intent.putExtra("u_name",u_name);
                            intent.putExtra("u_si",u_si);
                            intent.putExtra("u_gu",u_gu);
                            intent.putExtra("u_tel",u_tel);
                            intent.putExtra("u_email",u_email);
                            startActivity(intent);
                        }

                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                */


                return true;
        }
        return false;
    }

    protected void delete_sms(String u_id, String c_id){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "삭제 중입니다.", "Loading...");
            }

            @Override
            protected String doInBackground(String... params){
                String delete_id = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("u_id", params[0]));
                nameValuePairs.add(new BasicNameValuePair("c_id", params[1]));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_delete_sms.php");
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
                System.out.println(s);
                if(s.equalsIgnoreCase("success")){
                    IconTextItem_user curItem = (IconTextItem_user) adapter.getItem(user_index);
                    String[] curData = curItem.getData();

                   // add_complete(curData[1],con_id.getString("consultant_id", ""),img,u_name,u_si,u_gu,u_tel,u_email);
                }else {
                    // Toast.makeText(getActivity(), "삭제가 되지 않았습니다.", Toast.LENGTH_LONG).show();
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(u_id,c_id);
    }
/*
    private void add_complete(String a, String b, String c, String d, String e, String f, String g, String h){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("u_id", params[0]));
                nameValuePairs.add(new BasicNameValuePair("c_id", params[1]));
                nameValuePairs.add(new BasicNameValuePair("img", params[2]));
                nameValuePairs.add(new BasicNameValuePair("u_name", params[3]));
                nameValuePairs.add(new BasicNameValuePair("u_si", params[4]));
                nameValuePairs.add(new BasicNameValuePair("u_gu", params[5]));
                nameValuePairs.add(new BasicNameValuePair("u_tel", params[6]));
                nameValuePairs.add(new BasicNameValuePair("u_email", params[7]));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost;

                    httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_insert_complete.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(a,b,c,d,e,f,g,h);
    }
*/
    protected void sms_getList(String a){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            Dialog loadingDialog;

            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "리스트 불러오는 중", "Loading...");
            }
            @Override
            protected String doInBackground(String... params) {

                String paramSi = params[0];


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("c_id", paramSi));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_get_sms_user_id.php");

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

                    adapter = new IconTextAdapter_user(getActivity());
                    Resources res = getResources();

                    // getActivity().getActionBar().setTitle("상담 중 회원 " + sub1_list.length() + " 명");

                    for(int i=0;i<sub1_list.length();i++) {
                        JSONObject c = sub1_list.getJSONObject(i);

                        adapter.addItem(new IconTextItem_user(c.getString("u_name"),c.getString("u_id"),c.getString("u_si"),c.getString("u_gu"),c.getString("u_tel"), c.getString("u_email"), c.getString("sms_time")));

                        u_id = c.getString("u_id");
                        c_id = c.getString("c_id");
                        img = c.getString("img");
                        u_name = c.getString("u_name");
                        u_si = c.getString("u_si");
                        u_gu = c.getString("u_gu");
                        u_tel = c.getString("u_tel");
                        u_email = c.getString("u_email");
                    }
                    listView1.setAdapter(adapter);

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            IconTextItem_user curItem = (IconTextItem_user) adapter.getItem(position);
                            String[] curData = curItem.getData();

                            Toast.makeText(getActivity(),curData[1],Toast.LENGTH_SHORT).show();
                        }

                    });
                } else {
                    String[] no_result = {"상담중인 회원님이 없습니다."};
                    ArrayAdapter adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.listview_textview_item,R.id.textView, no_result);

                    listView1.setAdapter(adapter1);

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }

                    });
                }
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
