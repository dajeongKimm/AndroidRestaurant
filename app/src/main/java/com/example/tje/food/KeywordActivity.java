package com.example.tje.food;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.tje.food.Model.RestaurantListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class KeywordActivity extends AppCompatActivity {

    private static final String LOG_TAG = "searchkeyword";
    private static final String SHOW_SEARCH_URL = "http://192.168.0.5:8080/Final/m/show/search";

    Intent receiveIntent;
    List<RestaurantListView> keywordlist;

    //테스트용
    TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword);

        receiveIntent = getIntent();
        String keyword = receiveIntent.getStringExtra("keyword");
        resultTv = (TextView)findViewById(R.id.resultTv);

        init();
        resultTv.setText(keyword + "의 검색결과");

    }

    public void init(){
        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL endPoint = new URL(SHOW_SEARCH_URL);
                    HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                    myConnection.setRequestMethod("POST");

                    String keyword = receiveIntent.getStringExtra("keyword");

                    String dataAll = String.format("keyword=%s",keyword);
                    myConnection.setDoOutput(true);
                    myConnection.getOutputStream().write(dataAll.getBytes());


                    if(myConnection.getResponseCode() == 200){ // 200번은 성공인 경우
                        BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));

                        StringBuffer buffer = new StringBuffer();
                        String temp = null;

                        while((temp = in.readLine()) != null) {
                            buffer.append(temp);
                        }

                        Log.d(LOG_TAG, buffer.toString());

                        //Date 타입 잘 가져오기 위함
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        Type typeKeyword = new TypeToken<ArrayList<RestaurantListView>>(){}.getType();
                        keywordlist = gson.fromJson(buffer.toString(), typeKeyword);

                    }else{//그외에 400번 500번 에러가 있는 경우
                        Log.d(LOG_TAG, "서버 연결 및 메세지 읽기 실패1\n");
                        Log.d(LOG_TAG,myConnection.getResponseCode() + "");
                    }
                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                    Log.d(LOG_TAG, "서버 연결 및 메세지 읽기 실패2\n");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                //1. 리사이클러뷰 화면 연결
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
                //2. 아답터 생성
                CustomAdapter adapter = new CustomAdapter(keywordlist);
                //3.리사이클러뷰와 아답터 연결
                recyclerView.setAdapter(adapter);
                //4.리사이클러뷰매니저
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                Log.d(LOG_TAG, keywordlist.size() + "");

                super.onPostExecute(s);
            }
        }.execute();
    }
}
