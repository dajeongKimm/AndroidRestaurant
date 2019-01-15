package com.example.tje.food;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tje.food.Model.VisitView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class VisitAdapter extends RecyclerView.Adapter<VisitListHoler> {

    private static final String URL = Const.CUSTOMAPATER_IP;

    //1. 사용할 데이터 정의
    List<VisitView> data;
    Intent receiveIntent;

    public VisitAdapter(List<VisitView> data, Intent receiveIntent){
        this.data = data;
        this.receiveIntent = receiveIntent;
    }

    //3. 레이아웃을 객체로 변경
    @Override
    public VisitListHoler onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_visit, viewGroup, false);

        return new VisitListHoler(view, receiveIntent);
    }

    //4. 데이터를 화면에 세팅
    @Override
    public void onBindViewHolder(final VisitListHoler visitListHoler, int i) {
        final VisitView list = data.get(i);

        visitListHoler.rIdxTv.setText(list.getRestaurant_id() + "");

        visitListHoler.visitName.setText(list.getRestaurant_name());
        /*총점
        if((list.getSum_score()/list.getAllcount() + "").equals("NaN")){
            visitListHoler.visitTotal.setText("0.0");
        }else {
            String total = String.format("%.1f",list.getSum_score() / list.getAllcount());
            visitListHoler.visitTotal.setText(total);
        }
        */
        if(list.getSum_score() == 0 || list.getAllcount() == 0){
            visitListHoler.visitTotal.setText("0.0");
        }else {
            //String total = String.format("%.1f",list.getSum_score() / list.getAllcount());
            float total = list.getSum_score()/list.getAllcount();
            visitListHoler.visitTotal.setText(total + "");
        }

        //이미지 피카소라이브러리로 가져오기
        Picasso.get().load(Const.CUSTOMAPATER_IP + list.getRestaurant_mainimage()).into(visitListHoler.vsitImage);

    }


    //리스트의 개수를 정의
    @Override
    public int getItemCount() {
        return data.size();
    }
}
