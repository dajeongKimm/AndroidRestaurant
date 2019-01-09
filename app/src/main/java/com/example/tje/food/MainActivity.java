package com.example.tje.food;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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


import com.example.tje.food.Fragment.CategoryFragment;
import com.example.tje.food.Fragment.MypageFragment;
import com.example.tje.food.Fragment.ReviewFragment;
import com.example.tje.food.Fragment.SearchStoreFragment;
import com.example.tje.food.Model.Member;
import com.example.tje.food.Model.Member_address;


public class MainActivity extends AppCompatActivity {

    LinearLayout showListLayout;
    TextView showText;

    LinearLayout goSearchStore, goCategory, goReview, goMypage;
    TextView defaultStoreTv, ckStoreTv, defaultCategoryTv, ckCategoryTv, defaultReviewTv, ckReviewTv, dafaultMyTv, ckMyTv;

    EditText keywordTv;
    ImageButton goKeyword,btn_mypage_form;


    Fragment categoryFragment, mypageFragment, reviewFragment, searchStoreFragment;

    FragmentManager fm;
    FragmentTransaction tran;


    // 서버에서 받아올 객체
    Member loginmember;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission_internet = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);

        if(permission_internet == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 112);
        }

        init();
        setEvents();

    }

    public void init(){
        showListLayout = (LinearLayout)findViewById(R.id.showListLayout);
        showText = (TextView)findViewById(R.id.showText);

        goSearchStore = (LinearLayout)findViewById(R.id.goSearchStore);
        goCategory = (LinearLayout)findViewById(R.id.goCategory);
        goReview = (LinearLayout)findViewById(R.id.goReview);
        goMypage = (LinearLayout)findViewById(R.id.goMypage);

        defaultStoreTv = (TextView)findViewById(R.id.defaultStoreTv);
        ckStoreTv = (TextView)findViewById(R.id.ckStoreTv);
        defaultCategoryTv = (TextView)findViewById(R.id.defaultCategoryTv);
        ckCategoryTv = (TextView)findViewById(R.id.ckCategoryTv);
        defaultReviewTv = (TextView)findViewById(R.id.defaultReviewTv);
        ckReviewTv = (TextView)findViewById(R.id.ckReviewTv);
        dafaultMyTv = (TextView)findViewById(R.id.dafaultMyTv);
        ckMyTv = (TextView)findViewById(R.id.ckMyTv);

        keywordTv = (EditText)findViewById(R.id.keywordTv);
        goKeyword = (ImageButton)findViewById(R.id.goKeyword);
        btn_mypage_form = (ImageButton)findViewById(R.id.btn_mypage_form);

        //프래그 먼트 객체 생성
        categoryFragment = new CategoryFragment();
        mypageFragment = new MypageFragment();
        reviewFragment = new ReviewFragment();
        searchStoreFragment = new SearchStoreFragment();
        //프래그 먼트 교체 기본값
        //setFrag(0);
        loginmember = null;

    }

    public void setEvents(){

        showText.setOnClickListener(new View.OnClickListener() {
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

        goSearchStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클릭된것만 보이고
                defaultStoreTv.setVisibility(View.GONE);
                ckStoreTv.setVisibility(View.VISIBLE);
                //나머지는 원래대로!
                defaultCategoryTv.setVisibility(View.VISIBLE);
                ckCategoryTv.setVisibility(View.GONE);
                defaultReviewTv.setVisibility(View.VISIBLE);
                ckReviewTv.setVisibility(View.GONE);
                dafaultMyTv.setVisibility(View.VISIBLE);
                ckMyTv.setVisibility(View.GONE);

                //setFrag(0);

            }
        });

        goCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클릭된것만 보이고
                defaultCategoryTv.setVisibility(View.GONE);
                ckCategoryTv.setVisibility(View.VISIBLE);
                //나머지는 원래대로!
                defaultStoreTv.setVisibility(View.VISIBLE);
                ckStoreTv.setVisibility(View.GONE);
                defaultReviewTv.setVisibility(View.VISIBLE);
                ckReviewTv.setVisibility(View.GONE);
                dafaultMyTv.setVisibility(View.VISIBLE);
                ckMyTv.setVisibility(View.GONE);

                //setFrag(1);

            }
        });

        goReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클릭된것만 보이고
                defaultReviewTv.setVisibility(View.GONE);
                ckReviewTv.setVisibility(View.VISIBLE);
                //나머지는 원래대로!
                defaultCategoryTv.setVisibility(View.VISIBLE);
                ckCategoryTv.setVisibility(View.GONE);
                defaultStoreTv.setVisibility(View.VISIBLE);
                ckStoreTv.setVisibility(View.GONE);
                dafaultMyTv.setVisibility(View.VISIBLE);
                ckMyTv.setVisibility(View.GONE);

                //setFrag(2);

            }
        });

        goMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클릭된것만 보이고
                dafaultMyTv.setVisibility(View.GONE);
                ckMyTv.setVisibility(View.VISIBLE);
                //나머지는 원래대로!
                defaultCategoryTv.setVisibility(View.VISIBLE);
                ckCategoryTv.setVisibility(View.GONE);
                defaultReviewTv.setVisibility(View.VISIBLE);
                ckReviewTv.setVisibility(View.GONE);
                defaultStoreTv.setVisibility(View.VISIBLE);
                ckStoreTv.setVisibility(View.GONE);

                //setFrag(3);

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
                    finish();

                }else{

                    Intent intent = new Intent(getApplicationContext(),MyPageActivity.class);
                    intent.putExtra("loginmember",loginmember);
                    startActivity(intent);
                    finish();

                }




            }
        });
    }

    //프래그 먼트 교체하는 메소드
    public void setFrag(int n){
        fm = getSupportFragmentManager();
        tran = fm.beginTransaction();

        switch (n){
            case 0:
                tran.add(R.id.showListLayout, searchStoreFragment);
                tran.commit();
                break;
            case 1 :
                tran.replace(R.id.showListLayout, categoryFragment);
                tran.commit();
                break;
            case 2 :
                tran.replace(R.id.showListLayout, reviewFragment);
                tran.commit();
                break;
            case 3:
                tran.replace(R.id.showListLayout, mypageFragment);
                tran.commit();
                break;
        }
    }
}
