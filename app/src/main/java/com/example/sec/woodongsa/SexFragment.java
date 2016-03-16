package com.example.sec.woodongsa;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SexFragment extends Fragment {
    private EditText editTextEmail;
    private EditText editTextTitle;
    private EditText editTextContent;
    private Button btnRegister;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_sex_fragment, container, false);

        editTextEmail = (EditText) rootView.findViewById(R.id.email);
        editTextTitle = (EditText) rootView.findViewById(R.id.title);
        editTextContent = (EditText) rootView.findViewById(R.id.content);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);

        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    editTextEmail.setHint("");
                } else {
                    editTextEmail.setHint(" 이메일");
                }
            }
        });
        editTextTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    editTextTitle.setHint("");
                } else {
                    editTextTitle.setHint(" 제목");
                }
            }
        });
        editTextContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    editTextContent.setHint("");
                } else {
                    editTextContent.setHint(" 내용");
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, title, content;
                if(editTextEmail.getText().toString().equals("") || editTextTitle.getText().toString().equals("") || editTextContent.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"입력하지 않은 정보가 있습니다.",Toast.LENGTH_SHORT).show();
                } else {
                    email = editTextEmail.getText().toString();
                    title = editTextTitle.getText().toString();
                    content = editTextContent.getText().toString();

                    insertToDatabase(email, title, content);

                    editTextTitle.setText("");
                    editTextEmail.setText("");
                    editTextContent.setText("");
                }
            }
        });
        return rootView;
    }

    private void insertToDatabase(String email, String title, String content){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramemail = params[0];
                String paramtitle = params[1];
                String paramcontent = params[2];

                String email = editTextEmail.getText().toString();
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("title", title));
                nameValuePairs.add(new BasicNameValuePair("content", content));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.cafe24.com/app_nam/ask_update_db.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();
                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "문의 완료";
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();

                editTextEmail.setText("");
                editTextTitle.setText("");
                editTextContent.setText("");
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(email, title, content);
    }

}
