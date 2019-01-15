package com.example.tje.food;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.tje.food.Model.Member;
import com.example.tje.food.Model.RestaurantListView;
import com.example.tje.food.Model.VisitView;
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

public class VisitLog extends AppCompatActivity {

    public static final String VISITURL = Const.VISITLOG;
    public static final String LOG_TAG = "visitlog";

    Intent receiveIntent;
    Member loginmember;

    List<VisitView> dataList;
    RecyclerView visitRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_log);

        receiveIntent = getIntent();
        loginmember =(Member) receiveIntent.getSerializableExtra("loginmember");

        init();

    }

    public void init(){
        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL endPoint = new URL(VISITURL);
                    HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                    myConnection.setRequestMethod("POST");

                    String requestParam = String.format("member_id=%s", loginmember.getMember_id());
                    myConnection.getOutputStream().write(requestParam.getBytes());


                    if(myConnection.getResponseCode() == 200){ // 200번은 성공인 경우
                        BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));

                        StringBuffer buffer = new StringBuffer();
                        String temp = null;

                        while((temp = in.readLine()) != null) {
                            buffer.append(temp);
                        }

                        //Date 타입 잘 가져오기 위함
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        Type type = new TypeToken<ArrayList<VisitView>>() {}.getType();


                        dataList = gson.fromJson(buffer.toString(), type);
                        Log.d(LOG_TAG, dataList.toString());


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
                visitRecyclerView = (RecyclerView) findViewById(R.id.visitRecyclerView);
                //2. 아답터 생성
                VisitAdapter adapter = new VisitAdapter(dataList, receiveIntent);
                //adapter.setData(dataList);
                //3.리사이클러뷰와 아답터 연결
                visitRecyclerView.setAdapter(adapter);
                //4.리사이클러뷰매니저
                visitRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                super.onPostExecute(s);
            }
        }.execute();
    }
}
