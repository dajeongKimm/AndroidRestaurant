package com.example.tje.food;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.tje.food.MenuType.Bunsik;
import com.example.tje.food.MenuType.Coffee;
import com.example.tje.food.MenuType.Hansik;
import com.example.tje.food.MenuType.Ilsik;
import com.example.tje.food.MenuType.Jungsik;
import com.example.tje.food.MenuType.Pizza;
import com.example.tje.food.MenuType.Yangsik;
import com.example.tje.food.Model.Member;

import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    LinearLayout showStoreList;
    TextView showText;

    LinearLayout goReview, goMypage;
    LinearLayout goHansik, goJungsik, goIlsik, goPizza, goYangsik, goBunsik, goCoffee;
    TextView defaultReviewTv, ckReviewTv, dafaultMyTv, ckMyTv;

    EditText keywordTv;
    ImageButton goKeyword,btn_mypage_form;



    public static final int PER_GARRERY = 98;


    // 서버에서 받아올 객체
    Member loginmember;

    private BackPressClose backPressClose;



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

        backPressClose = new BackPressClose(this);



    }

    @Override
    public void onBackPressed(){
        backPressClose.onBackPressed();
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
        btn_mypage_form = (ImageButton)findViewById(R.id.btn_mypage_form);

        goHansik = (LinearLayout)findViewById(R.id.goHansik);
        goJungsik = (LinearLayout)findViewById(R.id.goJungsik);
        goIlsik = (LinearLayout)findViewById(R.id.goIlsik);
        goPizza = (LinearLayout)findViewById(R.id.goPizza);
        goYangsik = (LinearLayout)findViewById(R.id.goYangksik);
        goBunsik = (LinearLayout)findViewById(R.id.goBunsik);
        goCoffee = (LinearLayout)findViewById(R.id.goCoffee);

        loginmember = null;

    }

    public void setEvents(){

        //음식점 전체 보기
        showStoreList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //화면 전환
                Intent intent = new Intent(getApplicationContext(), ShowStoreList.class);
                if (loginmember != null){
                    intent.putExtra("loginmember",loginmember);
                }
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

        btn_mypage_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inetent = getIntent();
                loginmember =(Member) inetent.getSerializableExtra("loginmember");

                if(loginmember == null){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);

                }else{

                    Intent intent = new Intent(getApplicationContext(),MyPageActivity.class);
                    intent.putExtra("loginmember",loginmember);
                    startActivity(intent);

                }
            }
        });

        goHansik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Hansik.class);
                startActivity(intent);
            }
        });

        goJungsik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),  Jungsik.class);
                startActivity(intent);
            }
        });

        goIlsik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),  Ilsik.class);
                startActivity(intent);
            }
        });

        goPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),  Pizza.class);
                startActivity(intent);
            }
        });

        goYangsik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),  Yangsik.class);
                startActivity(intent);
            }
        });

        goBunsik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),  Bunsik.class);
                startActivity(intent);
            }
        });

        goCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),  Coffee.class);
                startActivity(intent);
            }
        });
    }
}
