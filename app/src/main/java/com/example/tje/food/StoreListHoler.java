package com.example.tje.food;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tje.food.Model.Member;


public class StoreListHoler extends RecyclerView.ViewHolder {

    //2. 리스트 아이템에 있는 사용할 위젯 정의
    TextView rIdxTv, storeNameTv, storeIntroTv, totalTv, readCntTv;
    ImageView storeimageBtn;
    LinearLayout clickLayout;
    //로그인 검사
    Member loginmember;
    Intent receiveIntent;

    //1. 생성자
    public StoreListHoler(View v){
        super(v);

        //3. findViewById로 연결
        rIdxTv = (TextView)v.findViewById(R.id.rIdxTv);
        storeNameTv = (TextView)v.findViewById(R.id.storeNameTv);
        storeIntroTv = (TextView)v.findViewById(R.id.storeIntroTv);
        totalTv = (TextView)v.findViewById(R.id.totalTv);
        readCntTv = (TextView)v.findViewById(R.id.readCntTv);
        storeimageBtn = (ImageView)v.findViewById(R.id.storeimageBtn);

        clickLayout = (LinearLayout)v.findViewById(R.id.clickLayout);


        setEvents();
    }

    public void setEvents(){
        //상세페이지로 이동
        clickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loginmember = (Member) receiveIntent.getSerializableExtra("loginmember");

                Intent intent = new Intent(v.getContext(),ShowStoreInfo.class);
                intent.putExtra("restaurant_id", rIdxTv.getText().toString());
                intent.putExtra("restaurant_name", storeNameTv.getText().toString());
                //로그인된 멤버 받아서 넘겨주기
                //intent.putExtra("loginmember", loginmember);
                v.getContext().startActivity(intent); //holder안에 있어서 v.getContext().startActivity사용

            }
        });
    }
}
