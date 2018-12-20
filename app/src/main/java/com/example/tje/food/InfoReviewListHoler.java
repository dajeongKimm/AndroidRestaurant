package com.example.tje.food;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.food.R;


public class InfoReviewListHoler extends RecyclerView.ViewHolder {

    //2. 리스트 아이템에 있는 사용할 위젯 정의
    TextView nicknameTv, likeCountTv, writeDateTv, contentsTv, reviewId;
    ImageButton memberImageBtn;
    RatingBar ratingBar;

    //1. 생성자
    public InfoReviewListHoler(View v){
        super(v);

        //3. findViewById로 연결
        nicknameTv = (TextView)v.findViewById(R.id.nicknameTv);
        likeCountTv = (TextView)v.findViewById(R.id.likeCountTv);
        writeDateTv = (TextView)v.findViewById(R.id.writeDateTv);
        contentsTv = (TextView)v.findViewById(R.id.contentsTv);
        ratingBar = (RatingBar)v.findViewById(R.id.ratingBar);
        memberImageBtn = (ImageButton)v.findViewById(R.id.memberImageBtn);

        reviewId = (TextView)v.findViewById(R.id.reviewId);

        memberImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext().getApplicationContext(), "버튼 클릭" + reviewId.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
