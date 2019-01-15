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
import com.example.tje.food.Model.ReviewListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowMoreReviewListActivity extends AppCompatActivity {

    private static final String URL_MAPPING = Const.SHOWMOREREVIEWLISTACTICITY_IP;
    private static final String LOG_TAG = "showmorereview";

    List<ReviewListView> data;
    TextView review_count;
    Intent receiveIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_review_list);

        review_count = (TextView) findViewById(R.id.review_count);

        Log.d(LOG_TAG, 1 + "");
        readData();
        Log.d(LOG_TAG, 2 + "");

        // init();
    }

    public void readData() {

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL endPoint = new URL(URL_MAPPING + "/review_list");
                    HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                    myConnection.setRequestMethod("POST");

                    Intent intent = getIntent();
                    int restaurant_id = Integer.parseInt(intent.getStringExtra("restaurant_id"));
                    String requestParam = String.format("restaurant_id=%d", restaurant_id);
                    myConnection.getOutputStream().write(requestParam.getBytes());

                    if( myConnection.getResponseCode() == 200 ) {
                        Log.d(LOG_TAG, myConnection.getResponseCode() + "");

                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(myConnection.getInputStream()));
                        StringBuffer buffer = new StringBuffer();
                        String temp = null;


                        while((temp = in.readLine()) != null) {
                            buffer.append(temp);
                        }

                        Log.d(LOG_TAG, buffer.toString());


                        // review_list
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();

                        JsonObject jsonObject = gson.fromJson(buffer.toString(), JsonObject.class);

                        Type type = new TypeToken<ArrayList<ReviewListView>>() {}.getType();
                        data = gson.fromJson(jsonObject.get("review_list"), type);

                        Type countType = new TypeToken<Integer>() {}.getType();
                        int count = gson.fromJson(jsonObject.get("review_count"), countType);
                        review_count.setText("리뷰 (" + count + ")");

                        Log.d(LOG_TAG, data.size() + "");

                    } else {
                        Log.d("에러 발생", myConnection.getResponseCode() + "");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                receiveIntent = getIntent();
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                SimpleReviewAdapter adapter = new SimpleReviewAdapter();
                adapter.setData(data, receiveIntent);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                super.onPostExecute(s);
            }
        }.execute();

    }
}
