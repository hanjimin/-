package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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


public class MainActivity extends ActionBarActivity {

//    TextView textView_address;
    JSONArray sub2_list1 = null;
    String myJSON;
    Spinner spin1, spin2;
    String[] sis = {"지역","서울", "부산", "인천", "대구", "울산", "광주", "대전", "강원",
            "전남", "전북", "충남", "충북", "경북", "경남", "경기", "세종", "제주"};
    String[] gu_total = {"전체"};
    String[] seoul = {"전체", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구",
            "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구",
            "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구",
            "용산구", "은평구", "종로구", "중구", "중랑구"};
    String[] pusan = {"전체", "강서구", "금정구", "기장군", "남구", "동구",
            "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구",
            "연제구", "영도구", "중구", "해운대구"};
    String[] incheon = {"전체", "강화군", "계양구", "남구", "남동구",
            "동구", "부평구", "서구", "연수구", "옹진군", "중구"};
    String[] daegu = {"전체", "남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"};
    String[] ulsan = {"전체", "남구", "동구", "북구", "울주군", "중구"};
    String[] kwangju = {"전체", "광산군", "남구", "동구", "북구", "서구"};
    String[] daejun = {"전체", "대덕구", "동구", "서구", "유성구", "중구"};
    String[] kangwon = {"전체", "강릉시", "고성군", "동해시", "삼척시", "속초시",
            "양구군", "양양군", "영월군", "원주시", "인제군", "정선군", "철원군",
            "춘천시", "태백시", "평창군", "홍천군", "화천군", "횡성군"};
    String[] junnam = {"전체", "강지군", "고흥군", "곡성군", "광양시", "구례군",
            "나주시", "담양군", "목포시", "무안군", "보성군", "순천시", "신안군", "여수시",
            "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"};
    String[] junbuk = {"전체", "고창군", "군산시", "김제시", "남원시", "무주군",
            "부안군", "순창군", "완주군", "익산시", "임실군", "장수군", "전주시", "정읍시", "진안군"};
    String[] chungnam = {"전체", "계룡시", "공주시", "금산군", "논산시", "당진시",
            "보령시", "부여군", "서산시", "서천군", "아산시", "연기군", "예산군", "천안시", "청양군", "태안군", "홍성군"};
    String[] chungbuk = {"전체", "괴산군", "단양군", "보은군", "영동군", "옥천군",
            "음성군", "제천시", "증평군", "진천군", "청원군", "청주시", "충주시"};
    String[] kyungbuk = {"전체", "경산시", "경주시", "고령군", "구미시", "군위군",
            "김천시", "문경시", "봉화군", "상주시", "성주군", "안동시", "영덕군", "영양군",
            "영주시", "영천시", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군", "포항시"};
    String[] kyungnam = {"전체", "거제시", "거창군", "고성군", "김해시", "남해군",
            "밀양시", "사천시", "산청군", "양산시", "의령군", "진주시", "창녕군",
            "창원시", "통영시", "하동군", "함안군", "함양군", "합천군"};
    String[] kyunggi = {"전체", "가평군", "고양시", "과천시", "광명시", "광주시",
            "구리시", "군포시", "남양주시", "동두천시", "부천시", "성남시", "수원시",
            "시흥시", "안산시", "안성시", "안양시", "양주시", "양평군", "여주시", "연천군",
            "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시"};
    String[] sejong = {"전체"};
    String[] jeju = {"전체", "서귀포시", "제주시"};
    String si, gu;
    public SharedPreferences prefs1, prefs2, prefs3;     //공유변수

    private String[] navItems = {"로그인 화면", "회원가입", "고객센터","보험평가받기"};
    private ListView lvNavList;
    private LinearLayout flContainer;

    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;

    ViewFlipper main_viewflipper;
    final Activity activity = this;
    float xAtDown;
    float xAtUp;

    int login_mode_temp = 0;
    SharedPreferences login_mode, user_id;
    SharedPreferences loading_state;
    SharedPreferences u_name,u_si,u_gu,u_tel,u_email,u_nickname;

    private int mPhotoSize, mPhotoSpacing;
    private ImageAdapter imageAdapter;

    private static final String[] CONTENT = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    private static final int[] ICONS = new int[] { R.drawable.damage, R.drawable.cancer, R.drawable.baby,R.drawable.life_long,
            R.drawable.pension, R.drawable.saving, R.drawable.fire, R.drawable.car, R.drawable.real,
            R.drawable.etc};

            TelephonyManager mTelephoneManeger;
    SharedPreferences selected_consultant_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_mode = getApplicationContext().getSharedPreferences("login_mode",0);
        login_mode_temp = login_mode.getInt("login_mode",0);


        selected_consultant_id = getApplicationContext().getSharedPreferences("selected_consultant_id", 0);

        main_viewflipper = (ViewFlipper) findViewById(R.id.main_viewFlipper);
        main_viewflipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.abc_fade_in));
        main_viewflipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.abc_fade_out));
        main_viewflipper.setFlipInterval(3000);
        main_viewflipper.startFlipping();

        System.out.println(login_mode_temp);
/* - 휴대폰 번호 가져오기 소스
        mTelephoneManeger = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String telephone = mTelephoneManeger.getLine1Number();
        System.out.println(telephone);
*/
        /* - 메일보내기 소스
        new SendMailTask(MainActivity.this).execute("woodongsa@gmail.com",
                "blueman123", "mohada18@naver.com", "메일임다", "메일내용임다");
*/
        if(login_mode_temp == 1){
            user_id = getApplicationContext().getSharedPreferences("user_id",0);
            get_user_info(user_id.getString("user_id",""));

            u_name = getApplicationContext().getSharedPreferences("u_name",0);

            navItems  = new String[4];
            navItems[0] = "로그아웃";
            navItems[1] = "마이 페이지";
            navItems[2] = "고객센터";
            navItems[3] = "보험평가받기";

        } else if(login_mode_temp == 2){
            navItems  = new String[4];
            navItems[0] = "로그아웃";
            navItems[1] = "마이 페이지";
            navItems[2] = "고객센터";
            navItems[3] = "보험 평가받기";
        }


        lvNavList = (ListView)findViewById(R.id.lv_activity_main_nav_list);
        flContainer = (LinearLayout)findViewById(R.id.fl_activity_main_container);

        lvNavList.setAdapter(
                new ArrayAdapter<String>(this, R.layout.simpleitem, navItems));
        lvNavList.setOnItemClickListener(new DrawerItemClickListener());

        dlDrawer = (DrawerLayout)findViewById(R.id.dl_activity_main_drawer);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.drawable.icon_drawer, R.string.open_drawer, R.string.close_drawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                main_viewflipper.startFlipping();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                main_viewflipper.stopFlipping();
            }

        };
        dlDrawer.setDrawerListener(dtToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_drawer_png);
        this.getSupportActionBar().setTitle("우리 동네 설계사");

        Intent intent = getIntent();
        if(intent.getIntExtra("drawer_state",0) == 0){
            dlDrawer.closeDrawer(lvNavList);
            main_viewflipper.startFlipping();
        } else {
            dlDrawer.openDrawer(lvNavList);
            main_viewflipper.stopFlipping();
        }

  //      textView_address = (TextView) findViewById(R.id.textView_address);
        spin1 = (Spinner) findViewById(R.id.spinner_si);
        spin2 = (Spinner) findViewById(R.id.spinner_gu);

        prefs1 = getApplicationContext().getSharedPreferences("si", 0);
        si = prefs1.getString("si","서울");

        prefs2 = getApplicationContext().getSharedPreferences("gu", 0);
        gu = prefs2.getString("gu","노원구");

    //    textView_address.setText(si + " > " + gu);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1,sis);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter1);
        //spin1.setAdapter(new MyAdapter(getApplicationContext(), R.layout.sis_custom_spinner, sis));

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                if (position == 0) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, gu_total);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, gu_total));
                } else if (position == 1) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, seoul);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, seoul));
                } else if (position == 2) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, pusan);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, pusan));
                } else if (position == 3) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, incheon);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, incheon));
                } else if (position == 4) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, daegu);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, daegu));
                } else if (position == 5) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, ulsan);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, ulsan));
                } else if (position == 6) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, kwangju);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, kwangju));
                } else if (position == 7) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, daejun);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, daejun));
                } else if (position == 8) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, kangwon);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, kangwon));
                } else if (position == 9) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, junnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, junnam));
                } else if (position == 10) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, junbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, junbuk));
                } else if (position == 11) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, chungnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, chungnam));
                } else if (position == 12) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, chungbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, chungbuk));
                } else if (position == 13) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, kyungbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, kyungbuk));
                } else if (position == 14) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, kyungnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, kyungnam));
                } else if (position == 15) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, kyunggi);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, kyunggi));
                } else if (position == 16) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, sejong);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, sejong));
                } else if (position == 17) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_simple_item1, jeju);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adapter2);
                    //spin2.setAdapter(new MyAdapter_gu(getApplicationContext(), R.layout.gus_custom_spinner, jeju));
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                //textView_address.setText(spin1.getSelectedItem() + " > " + spin2.getSelectedItem());
                si = spin1.getSelectedItem().toString();
                gu = spin2.getSelectedItem().toString();
                //  db_helper.insertData("address", spin1.getSelectedItem().toString(), spin2.getSelectedItem().toString());
                SharedPreferences.Editor editor = prefs1.edit();
                editor.putString("si", si);
                editor.commit();

                SharedPreferences.Editor editor1 = prefs2.edit();
                editor1.putString("gu", gu);
                editor1.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);
        mPhotoSpacing = getResources().getDimensionPixelSize(R.dimen.photo_spacing);
        imageAdapter = new ImageAdapter(getApplicationContext());
        final GridView gridView = (GridView) findViewById(R.id.main_gridView);
        gridView.setAdapter(imageAdapter);


        gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (imageAdapter.getNumColumns() == 0) {
                    final int numColumns = (int) Math.floor(gridView.getWidth() / (mPhotoSize + mPhotoSpacing));
                    if (numColumns > 0) {
                        final int columnWidth = (gridView.getWidth() / numColumns) - mPhotoSpacing;
                        imageAdapter.setNumColumns(numColumns);
                        imageAdapter.setItemHeight(columnWidth);

                    }
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(spin1.getSelectedItem().equals("지역")){
                    Toast.makeText(getApplicationContext(),"지역을 선택 해 주세요",Toast.LENGTH_SHORT).show();
                } else {
                    prefs3 = getApplicationContext().getSharedPreferences("bohum", 0);
                    SharedPreferences.Editor editor2 = prefs3.edit();
                    editor2.putInt("bohum", position);
                    editor2.commit();

                    Intent intent = new Intent(MainActivity.this, SubActivity1.class);
                    intent.putExtra("tabNum",position);

                    intent.putExtra("selected_si",spin1.getSelectedItem().toString());
                    intent.putExtra("selected_gu",spin2.getSelectedItem().toString());
                    finish();

                    startActivity(intent);
                }
            }
        });
        main_viewflipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(main_viewflipper.getDisplayedChild()){
                    case 0:
                        Intent intent = new Intent(MainActivity.this, Banner_fullscreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("banner_num",0);
                        startActivity(intent);
                        main_viewflipper.stopFlipping();

                        break;

                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, Banner_fullscreen.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent1.putExtra("banner_num",1);
                        startActivity(intent1);
                        main_viewflipper.stopFlipping();

                        break;


                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, Banner_fullscreen.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent2.putExtra("banner_num",2);
                        startActivity(intent2);
                        main_viewflipper.stopFlipping();

                        break;
                    default:
                        break;
                }
                return false;
            }
        });
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

            LayoutInflater inflater= getLayoutInflater();
            View row=inflater.inflate(R.layout.sis_custom_spinner, parent, false);
            TextView label=(TextView)row.findViewById(R.id.sis);
            label.setText(sis[position]);

            return row;
        }
    }

    public class MyAdapter_gu extends ArrayAdapter<String>{
        Context context;
        String[] gu_adapter;


        public MyAdapter_gu(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);

            this.context = context;
            this.gu_adapter = objects;
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

            LayoutInflater inflater= getLayoutInflater();
            View row=inflater.inflate(R.layout.gus_custom_spinner, parent, false);
            TextView label=(TextView)row.findViewById(R.id.gus);
            label.setText(gu_adapter[position]);

            return row;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        main_viewflipper.startFlipping();
    }

    @Override
    protected void onResume(){
        super.onResume();
        main_viewflipper.startFlipping();
    }

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private int mItemHeight = 0;
        private int mNumColumns = 0;
        private RelativeLayout.LayoutParams mImageViewLayoutParams;

        public ImageAdapter(Context applicationContext) {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mImageViewLayoutParams = new RelativeLayout.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT,
                    GridLayout.LayoutParams.MATCH_PARENT);
        }

        public int getCount() {
            return CONTENT.length;
        }

        // set numcols
        public void setNumColumns(int numColumns) {
            mNumColumns = numColumns;
        }

        public int getNumColumns() {
            return mNumColumns;
        }

        // set photo item height
        public void setItemHeight(int height) {
            if (height == mItemHeight) {
                return;
            }
            mItemHeight = height;
            mImageViewLayoutParams = new RelativeLayout.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, mItemHeight);
            notifyDataSetChanged();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {

            if (view == null)
                view = mInflater.inflate(R.layout.photo_item, null);

            ImageView cover = (ImageView) view.findViewById(R.id.cover);
            TextView title = (TextView) view.findViewById(R.id.title);

            cover.setLayoutParams(mImageViewLayoutParams);

            // Check the height matches our calculated column width
            if (cover.getLayoutParams().height != mItemHeight) {
                cover.setLayoutParams(mImageViewLayoutParams);
            }

            cover.setImageResource(ICONS[position % ICONS.length]);
         //   title.setText(CONTENT[position % CONTENT.length]);

            return view;
        }
    }

    protected void get_user_info(String a){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            Dialog loadingDialog;

            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(MainActivity.this, "리스트 불러오는 중", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {

                String paramID = params[0];
                System.out.println(params[0]);


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("u_id", params[0]));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_get_user_info.php");

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
                set_user_info();
                loadingDialog.dismiss();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(a);
    }

    protected void set_user_info(){
        try {
            if(myJSON == null){
                AlertDialog dialog = createDialogBox();
                dialog.show();
            } else{
                JSONObject jsonObject = new JSONObject(myJSON);
                sub2_list1 = jsonObject.getJSONArray("result");

                JSONObject c = sub2_list1.getJSONObject(0);

                u_name = getApplicationContext().getSharedPreferences("u_name",0);
                SharedPreferences.Editor editor_1 = u_name.edit();
                editor_1.putString("u_name",c.getString("u_name"));
                editor_1.commit();

                u_si = getApplicationContext().getSharedPreferences("u_si",0);
                SharedPreferences.Editor editor_2 = u_si.edit();
                editor_2.putString("u_si",c.getString("u_si"));
                editor_2.commit();

                u_gu = getApplicationContext().getSharedPreferences("u_gu",0);
                SharedPreferences.Editor editor_3 = u_gu.edit();
                editor_3.putString("u_gu",c.getString("u_gu"));
                editor_3.commit();

                u_tel = getApplicationContext().getSharedPreferences("u_tel",0);
                SharedPreferences.Editor editor_4 = u_tel.edit();
                editor_4.putString("u_tel",c.getString("u_tel"));
                editor_4.commit();

                u_email = getApplicationContext().getSharedPreferences("u_email",0);
                SharedPreferences.Editor editor_5 = u_email.edit();
                editor_5.putString("u_email",c.getString("u_email"));
                editor_5.commit();

                u_nickname = getApplicationContext().getSharedPreferences("u_nickname",0);
                SharedPreferences.Editor editor_6 = u_nickname.edit();
                editor_6.putString("u_nickname",c.getString("u_nickname"));
                editor_6.commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private AlertDialog createDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("네트워크가 불안정 합니다.");
        builder.setMessage("네트워크 연결을 확인 해 주세요.");

        builder.setPositiveButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
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
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
    */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(dlDrawer.isDrawerOpen(lvNavList) == true){
                dlDrawer.closeDrawer(lvNavList);
                main_viewflipper.startFlipping();
            } else{
                Intent intent = new Intent(MainActivity.this,Select_Service_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }

        }
        return false;
    }

    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(dtToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            if(login_mode_temp == 0) {
                switch (position) {
                    case 0:
                        Intent a = new Intent(MainActivity.this, LoginActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);
                        finish();
                        break;
                    case 1:
                        Intent b = new Intent(MainActivity.this, Login_go_button_activity.class);
                        b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(b);
                        finish();
                        break;
                    case 2:
                        Intent d = new Intent(MainActivity.this, Gogeckcenter.class);
                        d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(d);
                        finish();
                        break;

                    case 3:
                        Intent f = new Intent(MainActivity.this, Evaluation_main.class);
                        f.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(f);
                        finish();
                        break;
                }
            } else if(login_mode_temp == 1){
                switch (position) {
                    case 0:
                        SharedPreferences.Editor editor_mode = login_mode.edit();
                        editor_mode.putInt("login_mode",0);
                        editor_mode.commit();

                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                        break;
                    case 1:
                        Intent c = new Intent(MainActivity.this, Mypage.class);
                        c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(c);
                        finish();
                        break;
                    case 2:
                        Intent d = new Intent(MainActivity.this, Gogeckcenter.class);
                        d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(d);
                        finish();
                        break;
                    case 3:
                        Intent f = new Intent(MainActivity.this, Evaluation_main.class);
                        f.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(f);
                        finish();
                        break;
                }
            } else {
                switch (position) {
                    case 0:
                        SharedPreferences.Editor editor_mode = login_mode.edit();
                        editor_mode.putInt("login_mode",0);
                        editor_mode.commit();

                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        Intent c = new Intent(MainActivity.this, Mypage_consultant.class);
                        c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(c);
                        finish();
                        break;
                    case 2:
                        Intent d = new Intent(MainActivity.this, Gogeckcenter.class);
                        d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(d);
                        finish();
                        break;
                    case 3:
                        Intent f = new Intent(MainActivity.this, Evaluation_main.class);
                        f.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(f);
                        finish();
                        break;
                }
            }
            dlDrawer.closeDrawer(lvNavList);
            main_viewflipper.startFlipping();
        }
    }




}
