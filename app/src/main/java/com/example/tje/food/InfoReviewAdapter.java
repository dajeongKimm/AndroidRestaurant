package com.example.tje.food;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tje.food.Model.RestaurantListView;
import com.example.tje.food.Model.ReviewListView;

import java.util.List;

public class InfoReviewAdapter extends RecyclerView.Adapter<InfoReviewListHoler> {


    //1. 사용할 데이터 정의
    List<ReviewListView> data;

    public InfoReviewAdapter(List<ReviewListView> data){
        this.data = data;
    }

    //3. 레이아웃을 객체로 변경
    @Override
    public InfoReviewListHoler onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item_list, viewGroup, false);

        return new InfoReviewListHoler(view);
    }


    //4. 데이터를 화면에 세팅
    @Override
    public void onBindViewHolder(InfoReviewListHoler infoReviewListHoler, int i) {
        ReviewListView list = data.get(i);

        infoReviewListHoler.nicknameTv.setText(list.getMember_nickname());
        infoReviewListHoler.likeCountTv.setText(list.getSimple_review_like_count() + "");
        infoReviewListHoler.writeDateTv.setText(list.getSimple_review_registdate() + "");
        infoReviewListHoler.contentsTv.setText(list.getSimple_review_contents_text());
        infoReviewListHoler.ratingBar.setRating((float)list.getTotal_score());
        infoReviewListHoler.ratingBar.setIsIndicator(true);
        infoReviewListHoler.reviewId.setText(list.getSimple_review_id()+"");

    }


    //리스트의 개수를 정의
    @Override
    public int getItemCount() {
        return data.size();
    }
}
