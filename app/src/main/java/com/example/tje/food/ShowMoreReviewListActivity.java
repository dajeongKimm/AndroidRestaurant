package com.example.tje.food;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowMoreReviewListActivity extends AppCompatActivity {

    TextView checkTv;
    Intent receiveIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more_review_list);

        init();
        receiveIntent = getIntent();
        String restaurant_id =  receiveIntent.getStringExtra("restaurant_id");
        String restaurant_name = receiveIntent.getStringExtra("restaurant_name");
        checkTv.setText("음식점 아이디 : "+restaurant_id + "\n" + "가게 이름 : " +  restaurant_name);

    }

    public void init(){
        checkTv = (TextView)findViewById(R.id.checkTv);
    }
}
