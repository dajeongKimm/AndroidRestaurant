package com.example.tje.food;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tje.food.Model.RestaurantListView;
import com.example.tje.food.R;
import com.example.tje.food.StoreListHoler;
//import com.example.tje.food.StoreListHoler;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<StoreListHoler> {


    //1. 사용할 데이터 정의
    List<RestaurantListView> data;

    public CustomAdapter(List<RestaurantListView> data){
        this.data = data;
    }

    //3. 레이아웃을 객체로 변경
    @Override
    public StoreListHoler onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);

        return new StoreListHoler(view);
    }

    //4. 데이터를 화면에 세팅
    @Override
    public void onBindViewHolder(StoreListHoler storeListHoler, int i) {
        RestaurantListView list = data.get(i);



        storeListHoler.rIdxTv.setText(list.getRestaurant_id()+"");
        //이미지 우선 생략
        storeListHoler.storeNameTv.setText(list.getRestaurant_name());
        storeListHoler.storeIntroTv.setText(list.getRestaurant_description());

        //총점
        if((list.getSum_score()/list.getAllcount() + "").equals("NaN")){
            storeListHoler.totalTv.setText("0.0");
        }else {
            String total = String.format("%.1f",list.getSum_score() / list.getAllcount());
            storeListHoler.totalTv.setText(total);
        }
        storeListHoler.readCntTv.setText(list.getRead_count() + "");

    }


    //리스트의 개수를 정의
    @Override
    public int getItemCount() {
        return data.size();
    }
}
