package com.example.sec.woodongsa;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Mypage_consultant_tab1 extends Fragment {
    private Button btnRegister, btn_change_phone;
    private TextView bid;
    private TextView bna;
    private EditText bpa;
    private EditText bem, edit_introduce;
    private TextView bph;
    EditText inputNewPasswd, inputNewPasswdCheck;

    String returnId;
    String returnNa;
    String returnPa;
    String returnEm;
    String returnSi, returnIntro;
    String returnGu, returnPh;

    Spinner spin_si, spin_gu;

    String[] sis = {"서울", "부산", "인천", "대구", "울산", "광주", "대전", "강원",
            "전남", "전북", "충남", "충북", "경북", "경남", "경기", "세종", "제주"};
    String[] seoul = {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구",
            "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구",
            "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구",
            "용산구", "은평구", "종로구", "중구", "중랑구"};
    String[] pusan = {"강서구", "금정구", "기장군", "남구", "동구",
            "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구",
            "연제구", "영도구", "중구", "해운대구"};
    String[] incheon = {"강화군", "계양구", "남구", "남동구",
            "동구", "부평구", "서구", "연수구", "옹진군", "중구"};
    String[] daegu = {"남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"};
    String[] ulsan = {"남구", "동구", "북구", "울주군", "중구"};
    String[] kwangju = {"광산군", "남구", "동구", "북구", "서구"};
    String[] daejun = {"대덕구", "동구", "서구", "유성구", "중구"};
    String[] kangwon = {"강릉시", "고성군", "동해시", "삼척시", "속초시",
            "양구군", "양양군", "영월군", "원주시", "인제군", "정선군", "철원군",
            "춘천시", "태백시", "평창군", "홍천군", "화천군", "횡성군"};
    String[] junnam = {"강지군", "고흥군", "곡성군", "광양시", "구례군",
            "나주시", "담양군", "목포시", "무안군", "보성군", "순천시", "신안군", "여수시",
            "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"};
    String[] junbuk = {"고창군", "군산시", "김제시", "남원시", "무주군",
            "부안군", "순창군", "완주군", "익산시", "임실군", "장수군", "전주시", "정읍시", "진안군"};
    String[] chungnam = {"계룡시", "공주시", "금산군", "논산시", "당진시",
            "보령시", "부여군", "서산시", "서천군", "아산시", "연기군", "예산군", "천안시", "청양군", "태안군", "홍성군"};
    String[] chungbuk = {"괴산군", "단양군", "보은군", "영동군", "옥천군",
            "음성군", "제천시", "증평군", "진천군", "청원군", "청주시", "충주시"};
    String[] kyungbuk = {"경산시", "경주시", "고령군", "구미시", "군위군",
            "김천시", "문경시", "봉화군", "상주시", "성주군", "안동시", "영덕군", "영양군",
            "영주시", "영천시", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군", "포항시"};
    String[] kyungnam = {"거제시", "거창군", "고성군", "김해시", "남해군",
            "밀양시", "사천시", "산청군", "양산시", "의령군", "진주시", "창녕군",
            "창원시", "통영시", "하동군", "함안군", "함양군", "합천군"};
    String[] kyunggi = {"가평군", "고양시", "과천시", "광명시", "광주시",
            "구리시", "군포시", "남양주시", "동두천시", "부천시", "성남시", "수원시",
            "시흥시", "안산시", "안성시", "안양시", "양주시", "양평군", "여주시", "연천군",
            "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시"};
    String[] sejong = {"전체"};
    String[] jeju = {"서귀포시", "제주시"};
    SharedPreferences c_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        View rootView = inflater.inflate(R.layout.activity_mypage_consultant_tab1, container, false);

        bid = (TextView) rootView.findViewById(R.id.viewId);
        bna = (TextView) rootView.findViewById(R.id.viewName);
        bpa = (EditText) rootView.findViewById(R.id.inputPassword);
        bem = (EditText) rootView.findViewById(R.id.inputEmail);
        edit_introduce = (EditText) rootView.findViewById(R.id.edit_introduce);
        bph = (TextView) rootView.findViewById(R.id.inputPhone);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        spin_si = (Spinner) rootView.findViewById(R.id.spinner_si);
        spin_gu = (Spinner) rootView.findViewById(R.id.spinner_gu);

        btn_change_phone = (Button) rootView.findViewById(R.id.btn_change_phone);

        inputNewPasswd = (EditText) rootView.findViewById(R.id.inputNewPassword);
        inputNewPasswdCheck = (EditText) rootView.findViewById(R.id.inputNewPasswordCheck);

        bpa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    bpa.setHint("");
                } else {
                    bpa.setHint(" 비밀번호");
                }
            }
        });
        bem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    bem.setHint("");
                } else {
                    bem.setHint(" 이메일");
                }
            }
        });
        inputNewPasswd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    inputNewPasswd.setHint("");
                } else {
                    inputNewPasswd.setHint(" 새 비밀번호");
                }
            }
        });
        inputNewPasswdCheck.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    inputNewPasswdCheck.setHint("");
                } else {
                    inputNewPasswdCheck.setHint(" 새 비밀번호 확인");
                }
            }
        });

        btn_change_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Change_PhoneNum.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        c_id = getActivity().getSharedPreferences("consultant_id",0);
        returnId = c_id.getString("consultant_id","");


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,sis);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_si.setAdapter(adapter1);

        spin_si.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                if (position == 0) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, seoul);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 1) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pusan);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 2) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, incheon);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 3) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, daegu);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 4) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ulsan);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 5) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kwangju);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 6) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, daejun);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 7) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kangwon);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 8) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, junnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 9) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, junbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 10) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, chungnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 11) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, chungbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 12) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kyungbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 13) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kyungnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 14) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kyunggi);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 15) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sejong);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                } else if (position == 16) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, jeju);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                    int a = adapter2.getPosition(returnGu);
                    spin_gu.setSelection(a);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spin_gu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("id",returnId));
        String response = null;

        try {
            response = CustomHttpClient.executeHttpPost("http://woodongsa.cafe24.com/app_nam/Con_user_get_db.php", postParameters);
            String result = response.toString();

            try{
                returnNa = "";
                returnPa = "";
                returnEm = "";
                returnSi = "";
                returnGu = "";
                returnPh = "";
                returnIntro = "";

                if(result == null){
                    AlertDialog dialog = createDialogBox_network();
                    dialog.show();
                } else{
                    JSONArray jArray = new JSONArray(result);
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        Log.i("log_tag", "id: " + json_data.getString("id")
                                + ", name: " + json_data.getString("name")
                                + ", passwd: " + json_data.getString("passwd")
                                + ", email: " + json_data.getString("email")
                                + ", si: " + json_data.getString("si")
                                + ", gu: " + json_data.getString("gu"));
                        returnNa = json_data.getString("name");
                        returnPa = json_data.getString("passwd");
                        returnEm = json_data.getString("email");
                        returnSi = json_data.getString("si");
                        returnGu = json_data.getString("gu");
                        returnPh = json_data.getString("phone_num");
                        returnIntro = json_data.getString("introduce");
                    }
                }
            }
            catch(JSONException e){
                Log.e("log_tag", "Error parsing data "+e.toString());
            }
            try{
                bid.setText(returnId);
                bna.setText(returnNa);
                bem.setText(returnEm);
                bph.setText(returnPh);
                edit_introduce.setText(returnIntro);

                int i = adapter1.getPosition(returnSi);
                spin_si.setSelection(i);
            }
            catch(Exception e){
                Log.e("log_tag","Error in Display!" + e.toString());
            }
        }
        catch (Exception e) {
            Log.e("log_tag","Error in http connection!!" + e.toString());
        }
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bpa.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"정보를 수정하려면 비밀번호를 입력하세요",Toast.LENGTH_SHORT).show();
                } else {
                    if(inputNewPasswdCheck.getText().toString().equals("") && inputNewPasswd.getText().toString().equals("")){
                        if(bph.getText().toString().equals("") || bem.getText().toString().equals("")){
                            Toast.makeText(getActivity(),"휴대폰 번호와 이메일은 꼭 있어야합니다.",Toast.LENGTH_SHORT).show();
                        } else {
                            if(!(bph.getText().toString().length() == 11) && !(bph.getText().toString().length() == 10)){
                                Toast.makeText(getActivity(),"휴대폰 번호가 정확하지 않습니다.",Toast.LENGTH_SHORT).show();
                            } else {
                                update_user_no_passwd(returnId, bpa.getText().toString(), bem.getText().toString(), spin_si.getSelectedItem().toString(), spin_gu.getSelectedItem().toString(),edit_introduce.getText().toString());
                            }
                        }
                    } else if(!inputNewPasswd.getText().toString().equals("") && !inputNewPasswdCheck.getText().toString().equals("")){
                        if(!inputNewPasswd.getText().toString().equals(inputNewPasswdCheck.getText().toString())){
                            Toast.makeText(getActivity(),"새 비밀번호가 맞지 않습니다.",Toast.LENGTH_SHORT).show();
                            inputNewPasswdCheck.setText("");
                            inputNewPasswd.setText("");
                        } else {
                            insertToDatabase(returnId, returnNa, bpa.getText().toString() ,bem.getText().toString() , spin_si.getSelectedItem().toString(), spin_gu.getSelectedItem().toString(), inputNewPasswd.getText().toString(),edit_introduce.getText().toString());
                            System.out.println(spin_si.getSelectedItem().toString() + spin_gu.getSelectedItem().toString());
                        }
                    } else {
                        Toast.makeText(getActivity(),"새 비밀번호를 확인 해 주세요",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return rootView;
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

    private void update_user_no_passwd(String a, String b, String d, String e, String f, String g){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "확인 중입니다.", "Loading...");
            }

            @Override
            protected String doInBackground(String... params){

                String paramid = params[0];
                String parampasswd = params[1];
                String paramemail = params[2];
                String paramsi = params[3];
                String paramgu = params[4];
                String paramIntro = params[5];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", paramid));
                nameValuePairs.add(new BasicNameValuePair("passwd", parampasswd));
                nameValuePairs.add(new BasicNameValuePair("email", paramemail));
                nameValuePairs.add(new BasicNameValuePair("si", paramsi));
                nameValuePairs.add(new BasicNameValuePair("gu", paramgu));
                nameValuePairs.add(new BasicNameValuePair("introduce", paramIntro));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://woodongsa.com/app_nam/Con_user_update_db_no_passwd.php");
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

                if(s.equalsIgnoreCase("﻿" + "success")){
                    Toast.makeText(getActivity(), "수정 되었습니다.", Toast.LENGTH_LONG).show();

                } else if(s.equalsIgnoreCase("﻿" + "different")){
                    Toast.makeText(getActivity(), "현재 비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "비밀번호를 다시 확인 해주세요.", Toast.LENGTH_LONG).show();
                    System.out.println(s);
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(a,b,d,e,f,g);
    }



    private void insertToDatabase(String Id, String Na, String Pa, String Em, String Si, String Gu, String new_passwd, String introduce) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "확인 중입니다.", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {

                String paramid = params[0];
                String paramname = params[1];
                String parampasswd = params[2];
                String paramemail = params[3];
                String paramsi = params[4];
                String paramgu = params[5];
                String paramnew_passwd = params[6];
                String paramIntro = params[7];

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", paramid));
                nameValuePairs.add(new BasicNameValuePair("name", paramname));
                nameValuePairs.add(new BasicNameValuePair("passwd", parampasswd));
                nameValuePairs.add(new BasicNameValuePair("email", paramemail));
                nameValuePairs.add(new BasicNameValuePair("si", paramsi));
                nameValuePairs.add(new BasicNameValuePair("gu", paramgu));
                nameValuePairs.add(new BasicNameValuePair("new_passwd", paramnew_passwd));
                nameValuePairs.add(new BasicNameValuePair("introduce", paramIntro));

                InputStream is = null;

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://woodongsa.com/app_nam/Con_user_update_db.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
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
            protected void onPostExecute(String result) {
                String s = result.trim();
                loadingDialog.dismiss();

                if (s.equalsIgnoreCase("﻿" + "success")) {
                    Toast.makeText(getActivity(), "수정 되었습니다.", Toast.LENGTH_LONG).show();

                } else if (s.equalsIgnoreCase("﻿" + "different")) {
                    Toast.makeText(getActivity(), "현재 비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "비밀번호를 다시 확인 해주세요.", Toast.LENGTH_LONG).show();
                    System.out.println(s);
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(Id, Na, Pa, Em, Si, Gu, new_passwd,introduce);
    }
}




