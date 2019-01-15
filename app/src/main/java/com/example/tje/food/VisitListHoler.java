package com.example.tje.food;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tje.food.Model.Member;


public class VisitListHoler extends RecyclerView.ViewHolder {

    //2. 리스트 아이템에 있는 사용할 위젯 정의
    TextView visitName, visitTotal, rIdxTv;
    ImageView vsitImage;
    LinearLayout goDetailInfo;

    //로그인 검사
    Member loginmember;

    //1. 생성자
    public VisitListHoler(View v, Intent receiveIntent){
        super(v);

        //3. findViewById로 연결
        visitName = (TextView)v.findViewById(R.id.visitName);
        visitTotal = (TextView)v.findViewById(R.id.visitTotal);
        vsitImage = (ImageView)v.findViewById(R.id.vsitImage);

        rIdxTv = (TextView)v.findViewById(R.id.rIdxTv);

        goDetailInfo = (LinearLayout)v.findViewById(R.id.goDetailInfo);

        //로그인된 객체 받기
        loginmember = (Member) receiveIntent.getSerializableExtra("loginmember");

        setEvents();
    }

    public void setEvents(){
        //상세페이지로 이동
        goDetailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),ShowStoreInfo.class);
                intent.putExtra("restaurant_id", rIdxTv.getText().toString());
                intent.putExtra("restaurant_name", visitName.getText().toString());
                //로그인된 멤버 받아서 넘겨주기
                intent.putExtra("loginmember", loginmember);
                v.getContext().startActivity(intent); //holder안에 있어서 v.getContext().startActivity사용

            }
        });

    }
}
