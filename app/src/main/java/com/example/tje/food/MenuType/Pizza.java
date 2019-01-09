package com.example.tje.food.MenuType;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tje.food.Const;
import com.example.tje.food.CustomAdapter;
import com.example.tje.food.KeywordActivity;
import com.example.tje.food.Model.RestaurantListView;
import com.example.tje.food.R;
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

public class Pizza extends AppCompatActivity {

    private static final String LOG_TAG =  "pizza";
    private static final String SHOW_LIST_STORE_URL =  Const.PIZZA;

    //사용할 데이터 담기
    List<RestaurantListView> dataList;
    RecyclerView recyclerView;
    ImageButton goKeyword;
    EditText keywordTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        goKeyword = (ImageButton)findViewById(R.id.goKeyword);
        keywordTv = (EditText) findViewById(R.id.keywordTv);

        //검색하기
        goKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),KeywordActivity.class);
                //키워드 가지고 화면전환
                String keyword = keywordTv.getText().toString();
                intent.putExtra("keyword", keyword);
                startActivity(intent);
            }
        });

        init();
    }

    public void init(){

        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL endPoint = new URL(SHOW_LIST_STORE_URL);
                    HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                    myConnection.setRequestMethod("POST");

                    if(myConnection.getResponseCode() == 200){ // 200번은 성공인 경우
                        BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));

                        StringBuffer buffer = new StringBuffer();
                        String temp = null;

                        while((temp = in.readLine()) != null) {
                            buffer.append(temp);
                        }

                        //Date 타입 잘 가져오기 위함
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        Type type = new TypeToken<ArrayList<RestaurantListView>>() {}.getType();


                        dataList = gson.fromJson(buffer.toString(), type);


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
                recyclerView = (RecyclerView) findViewById(R.id.pizzaList);
                //2. 아답터 생성
                CustomAdapter adapter = new CustomAdapter(dataList);
                //adapter.setData(dataList);
                //3.리사이클러뷰와 아답터 연결
                recyclerView.setAdapter(adapter);
                //4.리사이클러뷰매니저
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                super.onPostExecute(s);
            }
        }.execute();
    }
}
