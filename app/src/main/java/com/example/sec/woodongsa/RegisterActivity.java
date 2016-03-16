package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sec.woodongsa.evaluation.Evaluation_Content_Activity;
import com.example.sec.woodongsa.evaluation.IconTextItem_evaluation_main;
import com.example.sec.woodongsa.evaluation.IconTextListAdapter_evaluation_main;

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
import java.util.regex.Pattern;


public class RegisterActivity extends Activity {

    EditText edit_register_check_passwd, edit_register_id, edit_register_passwd;
    EditText edit_register_email, edit_register_nick;
    TextView text_register_name, text_register_tel, text_register_sex;
    Button btn_register_checkid, btn_register_register, btn_register_checknick, btn_register_consultant;
    SharedPreferences login_mode;
    String myJSON;
    JSONArray sub1_list = null;

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

    String[] sex = {"남자", "여자"};

    String phoneNo, name, gender;
    int id_check = 0, nick_check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login_mode = getApplicationContext().getSharedPreferences("login_mode",0);

        ActionBarInit();
        id_check = 0;
        nick_check = 0;

        edit_register_check_passwd = (EditText) findViewById(R.id.edit_register_check_passwd);

        edit_register_id = (EditText) findViewById(R.id.edit_register_id);
        edit_register_id.setFilters(new InputFilter[]{filterAlphaNum});

        edit_register_passwd = (EditText) findViewById(R.id.edit_register_passwd);
        text_register_name = (TextView) findViewById(R.id.text_register_name);
        text_register_tel = (TextView) findViewById(R.id.text_register_tel);
        edit_register_email = (EditText) findViewById(R.id.edit_register_email);
        edit_register_nick = (EditText) findViewById(R.id.edit_register_nick);
        text_register_sex = (TextView) findViewById(R.id.text_register_sex);

        edit_register_check_passwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_register_check_passwd.setHint("");
                } else {
                    edit_register_check_passwd.setHint(" 비밀번호 확인");
                }
            }
        });
        edit_register_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_register_id.setHint("");
                } else {
                    edit_register_id.setHint(" 아이디");
                }
            }
        });
        edit_register_passwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_register_passwd.setHint("");
                } else {
                    edit_register_passwd.setHint(" 비밀번호");
                }
            }
        });
        edit_register_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_register_email.setHint("");
                } else {
                    edit_register_email.setHint(" 이메일");
                }
            }
        });
        edit_register_nick.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    edit_register_nick.setHint("");
                } else {
                    edit_register_nick.setHint(" 닉네임");
                }
            }
        });

        btn_register_checkid = (Button) findViewById(R.id.btn_register_checkid);
        btn_register_register = (Button) findViewById(R.id.btn_register_register);
        btn_register_checknick = (Button) findViewById(R.id.btn_register_checknick);

        spin_si = (Spinner) findViewById(R.id.spinner_register_si);
        spin_gu = (Spinner) findViewById(R.id.spinner_register_gu);

        Intent intent = getIntent();

        phoneNo = intent.getStringExtra("phoneNo");
        name = intent.getStringExtra("name");
        gender = intent.getStringExtra("gender");

        text_register_name.setText(name);
        if(gender.equals("0")){
            text_register_sex.setText(sex[0]);
        } else {
            text_register_sex.setText(sex[1]);
        }
        text_register_tel.setText(phoneNo);

        btn_register_checkid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((edit_register_id.getText().toString().equals(null)) ||
                        (edit_register_id.getText().toString().length() < 5) ){
                    Toast.makeText(RegisterActivity.this,"아이디 형식이 맞지 않습니다.",Toast.LENGTH_SHORT).show();
                    edit_register_id.setText(null);
                } else {
                    checkID(edit_register_id.getText().toString());
                }
            }
        });

        btn_register_checknick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((edit_register_nick.getText().toString().equals(null)) ){
                    Toast.makeText(RegisterActivity.this,"닉네임을 입력하세요",Toast.LENGTH_SHORT).show();
                    edit_register_nick.setText(null);
                } else {
                    checkNick(edit_register_nick.getText().toString());
                }
            }
        });


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,sis);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_si.setAdapter(adapter1);

        spin_si.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                if (position == 0) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, seoul);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 1) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, pusan);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 2) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, incheon);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 3) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, daegu);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 4) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, ulsan);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 5) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, kwangju);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 6) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, daejun);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 7) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, kangwon);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 8) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, junnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 9) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, junbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 10) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, chungnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 11) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, chungbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 12) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, kyungbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 13) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, kyungnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 14) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, kyunggi);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 15) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, sejong);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 16) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, jeju);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spin_gu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id_check == 0 || nick_check == 0){
                    if(id_check == 0){
                        Toast.makeText(RegisterActivity.this, "아이디 중복확인을 해주세요",Toast.LENGTH_SHORT).show();
                    } else if(nick_check == 0){
                        Toast.makeText(RegisterActivity.this, "닉네임 중복확인을 해주세요",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if((edit_register_check_passwd.getText().toString().equals("")) ||
                            (edit_register_id.getText().toString().equals("")) ||
                            (edit_register_passwd.getText().toString().equals("")) ||
                            (text_register_name.getText().toString().equals("")) ||
                            (text_register_tel.getText().toString().equals("")) ||
                            (edit_register_email.getText().toString().equals("")) ||
                            (edit_register_nick.getText().toString().equals(""))) {
                        Toast.makeText(RegisterActivity.this,"입력하지 않은 정보가 있습니다.",Toast.LENGTH_SHORT).show();
                    } else if(!edit_register_passwd.getText().toString().equals(edit_register_check_passwd.getText().toString())){
                        Toast.makeText(RegisterActivity.this,"비밀번호를 다시 확인해주세요.",Toast.LENGTH_SHORT).show();
                    } else if((text_register_tel.length()!=10) && (text_register_tel.length()!=11)){
                        Toast.makeText(RegisterActivity.this,"휴대폰 번호를 양식에 맞게 작성해주세요.",Toast.LENGTH_SHORT).show();
                    }else {
                        String id = edit_register_id.getText().toString();
                        String name = text_register_name.getText().toString();
                        String passwd = edit_register_passwd.getText().toString();
                        String si = spin_si.getSelectedItem().toString();
                        String gu = spin_gu.getSelectedItem().toString();
                        String tel = text_register_tel.getText().toString();
                        String sex = text_register_sex.getText().toString();
                        String email = edit_register_email.getText().toString();
                        String nick = edit_register_nick.getText().toString();

                        register_user(id,name,passwd,si,gu,tel,sex,email,nick);
                    }
                }


            }
        });
    }

    public InputFilter filterAlphaNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]*$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    private void register_user(String id, String name, String passwd, String si, String gu, String tel, String sex, String email, String nick){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", params[0]));
                nameValuePairs.add(new BasicNameValuePair("name", params[1]));
                nameValuePairs.add(new BasicNameValuePair("passwd", params[2]));
                nameValuePairs.add(new BasicNameValuePair("si", params[3]));
                nameValuePairs.add(new BasicNameValuePair("gu", params[4]));
                nameValuePairs.add(new BasicNameValuePair("tel", params[5]));
                nameValuePairs.add(new BasicNameValuePair("sex", params[6]));
                nameValuePairs.add(new BasicNameValuePair("email", params[7]));
                nameValuePairs.add(new BasicNameValuePair("nick", params[8]));


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_insert_user.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "회원가입 완료";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(id,name,passwd,si,gu,tel,sex,email,nick);
    }

    protected void checkID(String id){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(RegisterActivity.this, "확인 중입니다.", "Loading...");
            }

            @Override
            protected String doInBackground(String... params){
                String pass = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", pass));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_id_check.php");
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
                if(s.equalsIgnoreCase("success")){
                    Toast.makeText(getApplicationContext(),"이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    id_check = 0;
                }else {
                    Toast.makeText(getApplicationContext(), "사용 가능 한 아이디 입니다.", Toast.LENGTH_LONG).show();
                    id_check=1;
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(id);
    }

    protected void checkNick(String id){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(RegisterActivity.this, "확인 중입니다.", "Loading...");
            }

            @Override
            protected String doInBackground(String... params){
                String pass = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("nick", pass));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_nick_check.php");
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
                System.out.println(s);
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Toast.makeText(getApplicationContext(),"이미 존재하는 닉네임 입니다.", Toast.LENGTH_SHORT).show();
                    nick_check = 0;
                }else {
                    Toast.makeText(getApplicationContext(), "사용 가능 한 닉네임 입니다.", Toast.LENGTH_LONG).show();
                    nick_check = 1;
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(id);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);

        login_mode = getApplicationContext().getSharedPreferences("login_mode",0);
        if(login_mode.getInt("login_mode",0) == 0){
            menu.findItem(R.id.login_logout).setTitle("로그인 화면");
            menu.findItem(R.id.register_mypage).setTitle("회원가입");
        } else {
            menu.findItem(R.id.login_logout).setTitle("로그아웃");
            menu.findItem(R.id.register_mypage).setTitle("마이페이지");
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.login_logout:
                if(login_mode.getInt("login_mode",0) == 0){
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent a = new Intent(RegisterActivity.this, LoginActivity.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                    finish();
                } else if(login_mode.getInt("login_mode",0) == 1){
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    SharedPreferences.Editor editor_mode = login_mode.edit();
                    editor_mode.putInt("login_mode",0);
                    editor_mode.commit();

                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                } else {
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    SharedPreferences.Editor editor_mode = login_mode.edit();
                    editor_mode.putInt("login_mode",0);
                    editor_mode.commit();

                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
                break;

            case R.id.register_mypage:
                if(login_mode.getInt("login_mode",0) == 0){
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent b = new Intent(RegisterActivity.this, RegisterActivity.class);
                    b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(b);
                    finish();
                    break;
                } else if(login_mode.getInt("login_mode",0) == 1){
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent c = new Intent(RegisterActivity.this, Mypage.class);
                    c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(c);
                    finish();
                } else {
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent c = new Intent(RegisterActivity.this, Mypage_consultant.class);
                    c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(c);
                    finish();
                }
                break;

            case R.id.gogeckcenter:
                if(login_mode.getInt("login_mode",0) == 0){
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent d = new Intent(RegisterActivity.this, Gogeckcenter.class);
                    d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(d);
                    finish();
                } else if(login_mode.getInt("login_mode",0) == 1){
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent d = new Intent(RegisterActivity.this, Gogeckcenter.class);
                    d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(d);
                    finish();
                } else {
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent d = new Intent(RegisterActivity.this, Gogeckcenter.class);
                    d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(d);
                    finish();
                }
                break;

            case R.id.deunglok:
                if(login_mode.getInt("login_mode",0) == 0){
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent e = new Intent(RegisterActivity.this, Duenglokmojip.class);
                    e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(e);
                    finish();
                } else if(login_mode.getInt("login_mode",0) == 1){
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent e = new Intent(RegisterActivity.this, Duenglokmojip.class);
                    e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(e);
                    finish();
                } else {
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent e = new Intent(RegisterActivity.this, Duenglokmojip.class);
                    e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(e);
                    finish();
                }
                break;

            case R.id.evaluation:
                if(login_mode.getInt("login_mode",0) == 0){
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent f = new Intent(RegisterActivity.this, Evaluation_main.class);
                    f.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(f);
                    finish();
                } else if(login_mode.getInt("login_mode",0) == 1){
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent f = new Intent(RegisterActivity.this, Evaluation_main.class);
                    f.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(f);
                    finish();
                } else {
                    SharedPreferences back_activity;
                    back_activity=getApplicationContext().getSharedPreferences("back_activity",0);

                    SharedPreferences.Editor editor_back = back_activity.edit();
                    editor_back.putString("back_activity","register");
                    editor_back.commit();

                    Intent f = new Intent(RegisterActivity.this, Evaluation_main.class);
                    f.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(f);
                    finish();
                }
                break;
            case android.R.id.home:
                finish();
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            Intent intent = new Intent(this, Certificate_Webview_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return false;
    }


    private void ActionBarInit(){

        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
