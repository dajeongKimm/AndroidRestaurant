package com.example.tje.food;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

public class ShowStoreList extends AppCompatActivity {

    private static final String LOG_TAG = "comexamplefoodtag";
    private static final String SHOW_LIST_STORE_URL =  Const.SHOWSTORELIST_IP;

    //사용할 데이터 담기
    List<RestaurantListView> dataList;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_store_list);

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

                        Log.d(LOG_TAG, buffer.toString());

                        dataList = gson.fromJson(buffer.toString(), type);

                        Log.d(LOG_TAG, "datalist Size : "+dataList.size()+"");



                        StringBuffer data = new StringBuffer();
                        for(int i=0; i<dataList.size(); i++){
                            RestaurantListView showList = dataList.get(i);
                            data.append("R_idx : " + showList.getRestaurant_id() + "\n");
                            data.append("음식점 이름 : " + showList.getRestaurant_name() + "\n");
                            data.append("한줄 소개 : " + showList.getRestaurant_description() + "\n");
                            data.append("음식 종류 : " + showList.getMenu_type() + "\n");
                            data.append("조회수 : " + showList.getRead_count() + "\n");
                            data.append("총 평점 : " + showList.getSum_score() + "\n");
                            data.append("리뷰 수 : " + showList.getAllcount() + "\n");
                            data.append("-------------------------\n");


                        }

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
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                //2. 아답터 생성
                CustomAdapter adapter = new CustomAdapter(dataList);
                //adapter.setData(dataList);
                //3.리사이클러뷰와 아답터 연결
                recyclerView.setAdapter(adapter);
                //4.리사이클러뷰매니저
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                Log.d(LOG_TAG, dataList.size() + "");
                super.onPostExecute(s);
            }
        }.execute();
    }
}
