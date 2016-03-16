package com.example.sec.woodongsa;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Mypage_tab4 extends Fragment {

    ListView listView1;
    IconTextListAdapter_mypage_user adapter;
    JSONArray sub1_list = null;
    SharedPreferences u_id;
    String myJSON;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_mypage_tab4, container, false);

        listView1 = (ListView) rootView.findViewById(R.id.mypage_tab4_list);

        u_id = getActivity().getSharedPreferences("user_id",0);


        get_complete_consultant_list(u_id.getString("user_id",""));

        return rootView;
    }

    protected void get_complete_consultant_list(String a){
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
                nameValuePairs.add(new BasicNameValuePair("u_id", paramSi));


                InputStream inputStream = null;
                String result = null;
                try {


                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_get_complete_consultant_list.php");

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

                if(sub1_list.length() != 0){
                    adapter = new IconTextListAdapter_mypage_user(getActivity());

                    for(int i=0;i<sub1_list.length();i++) {
                        JSONObject c = sub1_list.getJSONObject(i);


                        String imageURL = "http://woodongsa.cafe24.com/" + c.getString("img");

                        //setImage(imageURL);


                        adapter.addItem(new IconTextItem_mypage_user(imageURL, c.getString("c_id"),c.getString("cs_time")));

                    }
                    listView1.setAdapter(adapter);

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            IconTextItem_mypage_user curItem = (IconTextItem_mypage_user) adapter.getItem(position);
                            String[] curData = curItem.getData();

                            Intent intent = new Intent(getActivity(), SubActivity2.class);
                            intent.putExtra("id", curData[0]);
                            startActivity(intent);

                          //  Toast.makeText(getActivity(), "Selected : " + curData[0], Toast.LENGTH_LONG).show();

                        }

                    });
                } else {
                    String[] no_result = {"계약한 설계사가 없습니다"};
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
                Intent intent1 = new Intent(getActivity(), Mypage.class);
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

}
