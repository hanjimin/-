package com.example.sec.woodongsa.evaluation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.sec.woodongsa.Evaluation_main;
import com.example.sec.woodongsa.R;
import com.example.sec.woodongsa.Select_Service_Activity;
import com.example.sec.woodongsa.SubActivity2;
import com.example.sec.woodongsa.evaluation.Comment.IconTextItem_Comment;
import com.example.sec.woodongsa.evaluation.Comment.IconTextListAdapter_comment;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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


public class Evaluation_Content_Activity extends Activity {

    ViewFlipper viewFlipper;
    TextView content_text_content;
    EditText content_edit_comment;
    Button content_btn_comment;
    ListView content_list_comment;
    String myJSON, myJSON1;
    JSONArray sub1_list = null;
    JSONArray comment_json = null;
    private int index = 0;
    OnSwipeTouchListener onSwipeTouchListener;
    String b_no;
    IconTextListAdapter_comment adapter;
    SharedPreferences c_id;
    String nickname;
    String date_time;
    ScrollView content_scroll;
    SharedPreferences login_mode;
    int login_mode_temp = 0;
    ImageLoader imageLoader;
    static TextView mDotsText[];
    private int mDotsCount = 0;
    private LinearLayout mDotsLayout;

    String[] image_id = {"","","","","","","","","",""};

    TextView text_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation__content_);


        content_edit_comment = (EditText) findViewById(R.id.content_edit_comment);
        content_btn_comment = (Button) findViewById(R.id.content_btn_comment);
        content_list_comment = (ListView) findViewById(R.id.comment_list);

        //content_scroll = (ScrollView) findViewById(R.id.content_scroll);


        View header = getLayoutInflater().inflate(R.layout.comment_listview_header,null,false);

        content_list_comment.addHeaderView(header);

        viewFlipper = (ViewFlipper) header.findViewById(R.id.viewFlipper);
        content_text_content = (TextView) header.findViewById(R.id.content_content);
        mDotsLayout = (LinearLayout) header.findViewById(R.id.image_count);
        text_info = (TextView) header.findViewById(R.id.text_info);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        header.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("");

        nickname = getIntent().getStringExtra("nickname");
        date_time = getIntent().getStringExtra("date_time");
        b_no = getIntent().getStringExtra("b_no");

        System.out.println(nickname + date_time);

        c_id = getApplicationContext().getSharedPreferences("consultant_id",0);

        login_mode = getApplicationContext().getSharedPreferences("login_mode",0);
        login_mode_temp = login_mode.getInt("login_mode",0);

        getEvaluationContent(nickname, date_time);
        getCommentList(b_no);



        onSwipeTouchListener = new OnSwipeTouchListener(this) {
            public void onSwipeRight(){
                viewFlipper.showNext();
            }
            public void onSwipeLeft(){
                viewFlipper.showPrevious();
            }
        };
        viewFlipper.setOnTouchListener(onSwipeTouchListener);

        if(login_mode_temp != 2){
            content_btn_comment.setVisibility(View.INVISIBLE);
            content_edit_comment.setVisibility(View.INVISIBLE);
        } else {
            content_btn_comment.setVisibility(View.VISIBLE);
            content_edit_comment.setVisibility(View.VISIBLE);
        }

        content_btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_mode_temp != 2 ){
                    Toast.makeText(getApplicationContext(),"죄송합니다.\n" + "설계사님들만 댓글을 달 수 있습니다.",Toast.LENGTH_SHORT).show();
                } else {
                    add_comment(b_no, content_edit_comment.getText().toString(), c_id.getString("consultant_id",""));
                }
            }
        });

        content_list_comment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewFlipper.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void add_comment(final String content_num, String comment_content, String co_id){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("b_no", params[0]));
                nameValuePairs.add(new BasicNameValuePair("comment_content", params[1]));
                nameValuePairs.add(new BasicNameValuePair("co_id", params[2]));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_add_comment.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "댓글 등록 완료";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);


                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Evaluation_Content_Activity.this,Evaluation_Content_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("nickname", nickname);
                intent.putExtra("date_time",date_time);
                intent.putExtra("b_no",b_no);

                startActivity(intent);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(content_num,comment_content,co_id);
    }

    protected void getCommentList(String a){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            Dialog loadingDialog;

            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Evaluation_Content_Activity.this, "리스트 불러오는 중", "Loading...");
            }
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("b_no", params[0]));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_get_evaluation_comment.php");

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
                myJSON1=result;
                showList_comment();
                loadingDialog.dismiss();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(a);
    }

    protected void showList_comment(){
        try {
            if(myJSON1 == null){
                AlertDialog dialog = createDialogBox();
                dialog.show();
            } else {
                JSONObject jsonObject = new JSONObject(myJSON1);
                comment_json = jsonObject.getJSONArray("result");
                System.out.println(comment_json.length());

                if(comment_json.length() != 0){
                    adapter = new IconTextListAdapter_comment(getApplicationContext());
                    Resources res = getResources();

                    for(int i=0;i<comment_json.length();i++) {
                        JSONObject c = comment_json.getJSONObject(i);


                        String imageURL = "http://woodongsa.cafe24.com/" + c.getString("co_img");

                        //setImage(imageURL);


                        adapter.addItem(new IconTextItem_Comment(imageURL, c.getString("co_name"), c.getString("co_si"), c.getString("co_gu"), c.getString("co_date"), c.getString("co_content"), c.getString("co_id")));


                        content_list_comment.setAdapter(adapter);

                        content_list_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                IconTextItem_Comment curItem = (IconTextItem_Comment) adapter.getItem(position);
                                String[] curData = curItem.getData();


                                Intent intent = new Intent(Evaluation_Content_Activity.this, SubActivity2.class);
                                intent.putExtra("id",curData[5]);
                                startActivity(intent);
                            }

                        });
                    }
                } else {
                    String[] no_comment = {"댓글이 없습니다"};
                    ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listview_textview_item,R.id.textView, no_comment);

                    content_list_comment.setAdapter(adapter);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private AlertDialog createDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Evaluation_Content_Activity.this);

        builder.setTitle("네트워크가 불안정 합니다.");
        builder.setMessage("네트워크 연결을 확인 해 주세요.");

        builder.setPositiveButton("다시시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(Evaluation_Content_Activity.this, Evaluation_Content_Activity.class);
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


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        onSwipeTouchListener.gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    protected void getEvaluationContent(String a, String b){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            Dialog loadingDialog;

            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Evaluation_Content_Activity.this, "리스트 불러오는 중", "Loading...");
            }
            @Override
            protected String doInBackground(String... params) {

                String paramSi = params[0];


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("b_id", params[0]));
                nameValuePairs.add(new BasicNameValuePair("b_date", params[1]));

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app/app_get_evaluation_content.php");

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
        g.execute(a,b);
    }

    protected void showList(){
        try {
            if(myJSON == null){
                AlertDialog dialog = createDialogBox();
                dialog.show();
            } else {
                JSONObject jsonObject = new JSONObject(myJSON);
                sub1_list = jsonObject.getJSONArray("result");

                if(sub1_list.length() != 0){
                    mDotsCount = 0;

                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.drawable.loading_image_good)
                            .showImageForEmptyUri(R.drawable.no_image)
                            .showImageOnFail(R.drawable.no_image)
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .bitmapConfig(Bitmap.Config.ARGB_8888)
                            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                            .considerExifParams(true)
                            .build();

                    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                            .writeDebugLogs()
                            .defaultDisplayImageOptions(options)
                            .diskCacheExtraOptions(480,360,null)
                            .memoryCacheExtraOptions(480,360)
                            .build();
                    imageLoader.getInstance().init(config);

                    JSONObject c = sub1_list.getJSONObject(0);

                    String info = "작성자 : " + c.getString("b_id") + " 지역 : " + c.getString("u_si") + " " + c.getString("u_gu");
                    text_info.setText(info);

                    content_text_content.setText(c.getString("b_content"));



                    if(!c.getString("img_src").equals("")){
                        image_id[mDotsCount]="http://woodongsa.com/board/upload/" + c.getString("img_src");
                        ImageView image = new ImageView(getApplicationContext());
                        //UrlImageViewHelper.setUrlDrawable(image, "http://woodongsa.com/board/upload/" + c.getString("img_src"));
                        mDotsCount++;

                        imageLoader.getInstance().displayImage("http://woodongsa.com/board/upload/" + c.getString("img_src"),image);

                        viewFlipper.addView(image);
                    } else {
                        ImageView image = new ImageView(getApplicationContext());
                        image.setImageResource(R.drawable.no_image);
                        viewFlipper.addView(image);
                    }

                    if(!c.getString("img_src2").equals("")){
                        image_id[mDotsCount]="http://woodongsa.com/board/upload/" + c.getString("img_src2");
                        mDotsCount++;
                        ImageView image = new ImageView(getApplicationContext());
                        //UrlImageViewHelper.setUrlDrawable(image, "http://woodongsa.com/board/upload/" + c.getString("img_src2"));
                        imageLoader.getInstance().displayImage("http://woodongsa.com/board/upload/" + c.getString("img_src2"),image);
                        viewFlipper.addView(image);
                    }

                    if(!c.getString("img_src3").equals("")){
                        image_id[mDotsCount]="http://woodongsa.com/board/upload/" + c.getString("img_src3");
                        mDotsCount++;
                        ImageView image = new ImageView(getApplicationContext());
                        //UrlImageViewHelper.setUrlDrawable(image, "http://woodongsa.com/board/upload/" + c.getString("img_src3"));
                        imageLoader.getInstance().displayImage("http://woodongsa.com/board/upload/" + c.getString("img_src3"),image);
                        viewFlipper.addView(image);
                    }

                    if(!c.getString("img_src4").equals("")){
                        image_id[mDotsCount]="http://woodongsa.com/board/upload/" + c.getString("img_src4");
                        mDotsCount++;
                        ImageView image = new ImageView(getApplicationContext());
                        //UrlImageViewHelper.setUrlDrawable(image, "http://woodongsa.com/board/upload/" + c.getString("img_src4"));
                        imageLoader.getInstance().displayImage("http://woodongsa.com/board/upload/" + c.getString("img_src4"),image);
                        viewFlipper.addView(image);
                    }

                    if(!c.getString("img_src5").equals("")){
                        image_id[mDotsCount]="http://woodongsa.com/board/upload/" + c.getString("img_src5");
                        mDotsCount++;
                        ImageView image = new ImageView(getApplicationContext());
                        //UrlImageViewHelper.setUrlDrawable(image, "http://woodongsa.com/board/upload/" + c.getString("img_src5"));
                        imageLoader.getInstance().displayImage("http://woodongsa.com/board/upload/" + c.getString("img_src5"),image);
                        viewFlipper.addView(image);
                    }

                    if(!c.getString("img_src6").equals("")){
                        image_id[mDotsCount]="http://woodongsa.com/board/upload/" + c.getString("img_src6");
                        mDotsCount++;
                        ImageView image = new ImageView(getApplicationContext());
                        //UrlImageViewHelper.setUrlDrawable(image, "http://woodongsa.com/board/upload/" + c.getString("img_src6"));
                        imageLoader.getInstance().displayImage("http://woodongsa.com/board/upload/" + c.getString("img_src6"),image);
                        viewFlipper.addView(image);
                    }

                    if(!c.getString("img_src7").equals("")){
                        image_id[mDotsCount]="http://woodongsa.com/board/upload/" + c.getString("img_src7");
                        mDotsCount++;
                        ImageView image = new ImageView(getApplicationContext());
                        //UrlImageViewHelper.setUrlDrawable(image, "http://woodongsa.com/board/upload/" + c.getString("img_src7"));
                        imageLoader.getInstance().displayImage("http://woodongsa.com/board/upload/" + c.getString("img_src7"),image);
                        viewFlipper.addView(image);
                    }

                    if(!c.getString("img_src8").equals("")){
                        image_id[mDotsCount]="http://woodongsa.com/board/upload/" + c.getString("img_src8");
                        mDotsCount++;
                        ImageView image = new ImageView(getApplicationContext());
                        //UrlImageViewHelper.setUrlDrawable(image, "http://woodongsa.com/board/upload/" + c.getString("img_src8"));
                        imageLoader.getInstance().displayImage("http://woodongsa.com/board/upload/" + c.getString("img_src8"),image);
                        viewFlipper.addView(image);
                    }

                    if(!c.getString("img_src9").equals("")){
                        image_id[mDotsCount]="http://woodongsa.com/board/upload/" + c.getString("img_src9");
                        mDotsCount++;
                        ImageView image = new ImageView(getApplicationContext());
                        //UrlImageViewHelper.setUrlDrawable(image, "http://woodongsa.com/board/upload/" + c.getString("img_src9"));
                        imageLoader.getInstance().displayImage("http://woodongsa.com/board/upload/" + c.getString("img_src9"),image);
                        viewFlipper.addView(image);
                    }

                    if(!c.getString("img_src10").equals("")){
                        image_id[mDotsCount]="http://woodongsa.com/board/upload/" + c.getString("img_src10");
                        mDotsCount++;
                        ImageView image = new ImageView(getApplicationContext());
                        //UrlImageViewHelper.setUrlDrawable(image, "http://woodongsa.com/board/upload/" + c.getString("img_src10"));
                        imageLoader.getInstance().displayImage("http://woodongsa.com/board/upload/" + c.getString("img_src10"),image);
                        viewFlipper.addView(image);
                    }

                    mDotsText = new TextView[mDotsCount];

                    //here we set the dots
                    for (int i = 0; i < mDotsCount; i++) {
                        mDotsText[i] = new TextView(this);
                        mDotsText[i].setText(".");
                        mDotsText[i].setTextSize(45);
                        mDotsText[i].setTypeface(null, Typeface.BOLD);
                        mDotsText[i].setTextColor(Color.BLACK);
                        mDotsLayout.addView(mDotsText[i]);
                    }

                    viewFlipper.setInAnimation(this, android.R.anim.fade_in);
                    viewFlipper.setOutAnimation(this, android.R.anim.fade_out);

                    viewFlipper.setOnTouchListener(new View.OnTouchListener(){
                                                       @Override
                                                       public boolean onTouch(View view, MotionEvent e) {
                                                           // TODO Auto-generated method stub
                                                           switch(e.getActionMasked())
                                                           {
                                                               case MotionEvent.ACTION_DOWN:

                                                                   if(image_id[0].equals("")){
                                                                       Toast.makeText(getApplicationContext(),"등록된 이미지가 없습니다.",Toast.LENGTH_SHORT).show();
                                                                   } else {
                                                                       Intent intent = new Intent(Evaluation_Content_Activity.this, FullscreenViewFlipper.class);
                                                                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                       intent.putExtra("imageurl",image_id);

                                                                       startActivity(intent);
                                                                   }
                                                                   break;
                                                           }
                                                           return false;  //means that the listener dosen't consume the event
                                                       }
                                                   }
                    );

                } else {
                    Toast.makeText(Evaluation_Content_Activity.this,"불러오기 실패",Toast.LENGTH_SHORT).show();
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluation__content_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(Evaluation_Content_Activity.this, Select_Service_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
