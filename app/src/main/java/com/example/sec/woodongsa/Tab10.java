package com.example.sec.woodongsa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Tab10 extends Fragment {
    ListView listView1;
    IconTextListAdapter adapter;
    String si, gu;
    TextView search_result_num;
    Drawable [] images;
    String[] bohums = {"실손","암", "태아/자녀", "종신", "연금", "저축", "화재",
            "자동차/운전자", "상해", "기타"};
    Spinner spin_corps;
    String[] corps = {"보험사 별로 보기(전체)","교보생명", "농협생명", "동부화재","롯데손해보험", "메리츠화재", "메트라이프", "미래에셋생명","삼성생명","삼성화재",
            "신한생명","알리안츠생명", "프루덴셜","한화생명","한화손보","현대해상","흥국생명","흥국화재","AIA생명","ING생명","KB손해보험","MG손해보험","PCA생명"  };

    String[] corps_eng = {"all","kyobo_s","nonghyup_s","dongbu_f","lotte_f","meritz_f","merit_f","mirae_s","samsung_s","samsung_f",
            "shinhan_s","allianz_s","prudential_f","hanwha_s","hanhwa_f","hyundai_f","heungkuk_s","heungkuk_f","AIA_s","ING_s","KB_f","MG_s","pca_s"};

    int arr_images[] = {R.drawable.all,R.drawable.kyobo_sangmyeung,R.drawable.nonghyup_sangmyeng,R.drawable.dongbu_hwajae,R.drawable.lotte_sonhae,R.drawable.meritz_hwajae,R.drawable.metlife,R.drawable.miraeasset,R.drawable.samsung_sangmyeng,R.drawable.samsung_hwajae,
            R.drawable.sinhan_sangmyeng,R.drawable.allianz,R.drawable.prudential,R.drawable.hanhwa_sangmyeng,R.drawable.hanhwa_sonhae,R.drawable.hyundae_haesang,R.drawable.heungkuk_sangmyeng,R.drawable.heungkuk_hwajae,R.drawable.aia_sangmyeung,R.drawable.ing_sangmyeng,R.drawable.kb_sonhae,R.drawable.mg_sonhae,R.drawable.pca_life};
    String myJSON;
    JSONArray sub1_list = null;
    public SharedPreferences prefs1,prefs2,prefs3, selected_consultant_id;
    TextView text_address;
    LinearLayout no_search_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_tab10, container, false);

        no_search_layout = (LinearLayout) rootView.findViewById(R.id.no_search_activity);


        spin_corps = (Spinner) rootView.findViewById(R.id.tab1_spinner);
        search_result_num = (TextView) rootView.findViewById(R.id.search_result_num);
        selected_consultant_id = getActivity().getSharedPreferences("selected_consultant_id",0);
        text_address = (TextView) rootView.findViewById(R.id.text_address);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, corps);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_corps.setAdapter(new MyAdapter(getActivity(), R.layout.corps_custom_spinner, corps));

        prefs1 = getActivity().getSharedPreferences("si", 0);
        si = prefs1.getString("si","서울");

        prefs2 = getActivity().getSharedPreferences("gu", 0);
        gu = prefs2.getString("gu","노원구");

        prefs3 = getActivity().getSharedPreferences("bohum", 0);

        text_address.setText(si + " > " + gu + " > " + "기타");

        //sub1_getList(si,gu,bohum);

        listView1 = (ListView) rootView.findViewById(R.id.list_tab1);

        spin_corps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           //         ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                if(position == 0){
                    sub1_getList(si,gu);
                } else {
                    int index = spin_corps.getSelectedItemPosition();

                    sub1_getList(si,gu,corps_eng[index]);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sub1_getList(si,gu);
            }
        });

        return rootView;
    }

    public class MyAdapter extends ArrayAdapter<String>{

        public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getActivity().getLayoutInflater();
            View row=inflater.inflate(R.layout.corps_custom_spinner, parent, false);
            TextView label=(TextView)row.findViewById(R.id.company);
            label.setText(corps[position]);


            ImageView icon=(ImageView)row.findViewById(R.id.image);
            icon.setImageResource(arr_images[position]);
            return row;
        }
    }


    protected void sub1_getList(String a, String b){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            Dialog loadingDialog;

            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "리스트 불러오는 중", "Loading...");
            }
            @Override
            protected String doInBackground(String... params) {

                String paramSi = params[0];
                String paramGu = params[1];

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("sub1_list_item_si", paramSi));
                nameValuePairs.add(new BasicNameValuePair("sub1_list_item_gu", paramGu));

                System.out.println(params[0] + params[1]);

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    if(gu.equals("전체")){
                        HttpPost httpPost = new HttpPost("http://woodongsa.com/app/app_get_whole_consultant_info.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
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
                    } else {
                        HttpPost httpPost = new HttpPost("http://woodongsa.com/app/app_get_consultant_info.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
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
                    }
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
        g.execute(a,b);
    }

    protected void sub1_getList(String a, String b, String d){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            Dialog loadingDialog;

            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "리스트 불러오는 중", "Loading...");
            }
            @Override
            protected String doInBackground(String... params) {

                String paramSi = params[0];
                String paramGu = params[1];
                String paramCorp = params[2];

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("sub1_list_item_si", paramSi));
                nameValuePairs.add(new BasicNameValuePair("sub1_list_item_gu", paramGu));
                nameValuePairs.add(new BasicNameValuePair("sub1_list_item_corp", paramCorp));

                InputStream inputStream = null;
                String result = null;
                try {

                    if(gu.equals("전체")){
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://woodongsa.com/app/app_whole_select_corp.php");

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
                    } else {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://woodongsa.com/app/app_select_corp.php");

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
                    }

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
        g.execute(a,b,d);
    }


    protected void showList(){
        try {
            if(myJSON == null){
                AlertDialog dialog = createDialogBox();
                dialog.show();
            } else {
                JSONObject jsonObject = new JSONObject(myJSON);
                sub1_list = jsonObject.getJSONArray("result");

                search_result_num.setText(sub1_list.length() + "명");

                if(sub1_list.length() != 0){
                    listView1.setVisibility(View.VISIBLE);
                    adapter = new IconTextListAdapter(getActivity());
                    Resources res = getResources();
                    images = new Drawable[sub1_list.length()];
                    for(int i=0;i<sub1_list.length();i++) {
                        JSONObject c = sub1_list.getJSONObject(i);


                        String imageURL = "http://woodongsa.cafe24.com/" + c.getString("sub1_list_item_image");

                        //setImage(imageURL);


                        adapter.addItem(new IconTextItem(imageURL, c.getString("sub1_list_item_id")));

                    }
                    listView1.setAdapter(adapter);

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            IconTextItem curItem = (IconTextItem) adapter.getItem(position);
                            String[] curData = curItem.getData();

                            SharedPreferences.Editor editor_select = selected_consultant_id.edit();
                            editor_select.putString("selected_consultant_id", curData[0]);
                            editor_select.commit();

                            Intent intent = new Intent(getActivity(), SubActivity2.class);
                            intent.putExtra("id", curData[0]);
                            startActivity(intent);

                            //    Toast.makeText(getActivity(), "Selected : " + curData[0], Toast.LENGTH_LONG).show();

                        }

                    });
                } else {
                    String[] no_result = {"검색결과가 없습니다"};
                    ArrayAdapter adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.listview_textview_item,R.id.textView, no_result);

                    listView1.setAdapter(adapter1);

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }

                    });
                    if(spin_corps.getSelectedItem().toString().equals("전체")){

                    } else{
                        if(spin_corps.getSelectedItemPosition() == 0){

                        } else {
                            Toast.makeText(getActivity(),spin_corps.getSelectedItem().toString()+" 의 검색 결과가 없습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
    private AlertDialog createDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("네트워크가 불안정 합니다.");
        builder.setMessage("네트워크 연결을 확인 해 주세요.");

        builder.setPositiveButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(getActivity(), SubActivity1.class);
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

