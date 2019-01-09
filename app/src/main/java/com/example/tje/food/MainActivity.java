package com.example.tje.food;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    LinearLayout showStoreList;
    TextView showText;

    LinearLayout goReview, goMypage;
    TextView defaultStoreTv, ckStoreTv, defaultCategoryTv, ckCategoryTv, defaultReviewTv, ckReviewTv, dafaultMyTv, ckMyTv;

    EditText keywordTv;
    ImageButton goKeyword;


    public static final int PER_GARRERY = 98;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        int permission_internet = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);
        if(permission_internet == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 112);
        }

        int permission_gallery = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission_gallery == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
        }
        */


        if(checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            init();
            setEvents();
        }else{ //2.2 권한이 없으면 - 사용자에게 권한요청
            String permissions[] = {Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, PER_GARRERY); //(String[] , int)
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case PER_GARRERY :
                if (grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "권한 요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    init();
                    setEvents();
                }
        }
    }

    public void init(){
        showStoreList = (LinearLayout)findViewById(R.id.showStoreList);
        goReview = (LinearLayout)findViewById(R.id.goReview);
        goMypage = (LinearLayout)findViewById(R.id.goMypage);

        defaultReviewTv = (TextView)findViewById(R.id.defaultReviewTv);
        ckReviewTv = (TextView)findViewById(R.id.ckReviewTv);
        dafaultMyTv = (TextView)findViewById(R.id.dafaultMyTv);
        ckMyTv = (TextView)findViewById(R.id.ckMyTv);

        keywordTv = (EditText)findViewById(R.id.keywordTv);
        goKeyword = (ImageButton)findViewById(R.id.goKeyword);


    }

    public void setEvents(){

        //음식점 전체 보기
        showStoreList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //화면 전환
                Intent intent = new Intent(getApplicationContext(), ShowStoreList.class);
                startActivity(intent);
            }
        });


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


        goReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클릭된것만 보이고
                defaultReviewTv.setVisibility(View.GONE);
                ckReviewTv.setVisibility(View.VISIBLE);
                //나머지는 원래대로!
                dafaultMyTv.setVisibility(View.VISIBLE);
                ckMyTv.setVisibility(View.GONE);
            }
        });

        goMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클릭된것만 보이고
                dafaultMyTv.setVisibility(View.GONE);
                ckMyTv.setVisibility(View.VISIBLE);
                //나머지는 원래대로!
                defaultReviewTv.setVisibility(View.VISIBLE);
                ckReviewTv.setVisibility(View.GONE);
            }
        });
    }
}
