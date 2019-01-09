package com.example.tje.food;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tje.food.Model.DetailRestaurantView;
import com.example.tje.food.Model.MenuList;
import com.example.tje.food.Model.RestaurantListView;
import com.example.tje.food.Model.ReviewListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowStoreInfo extends AppCompatActivity {

    private static final String LOG_TAG = "comexamplefoodtag";
    private static final String SHOW_INFO_STORE_URL = Const.SHOWSTOREINFO_IP;


    TextView storeTitle, totalTv, addressTv, telTv, menutypeTv, bTimeTv, openDateTv, introTv, menuListTv, discountTv, serviceTv, alertMsg;
    Intent receiveIntent;
    RecyclerView reviewRecy;
    ArrayList<ReviewListView> reviewlist;
    Button btnMoreReview, btnBlogReview;
    LinearLayout writeReviewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);

        setFindByViewId();

        receiveIntent = getIntent();

        init();
        setEvents();
    }

    public void setFindByViewId(){
        storeTitle = (TextView)findViewById(R.id.storeTitle);
        totalTv = (TextView)findViewById(R.id.totalTv);
        addressTv = (TextView)findViewById(R.id.addressTv);
        telTv = (TextView)findViewById(R.id.telTv);
        menutypeTv = (TextView)findViewById(R.id.menutypeTv);
        bTimeTv = (TextView)findViewById(R.id.bTimeTv);
        openDateTv = (TextView)findViewById(R.id.openDateTv);
        introTv = (TextView)findViewById(R.id.introTv);
        menuListTv = (TextView)findViewById(R.id.menuListTv);
        discountTv = (TextView)findViewById(R.id.discountTv);
        serviceTv = (TextView)findViewById(R.id.serviceTv);
        btnMoreReview = (Button)findViewById(R.id.btnMoreReview);
        btnBlogReview = (Button)findViewById(R.id.btnBlogReview);
        alertMsg = (TextView)findViewById(R.id.alertMsg);

        //

        writeReviewLayout = (LinearLayout)findViewById(R.id.writeReviewLayout);

    }

    public void setEvents(){
        btnMoreReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restaurant_id = receiveIntent.getStringExtra("restaurant_id");
                String restaurant_name = receiveIntent.getStringExtra("restaurant_name");

                Intent intent = new Intent(getApplicationContext(), ShowMoreReviewListActivity.class);
                intent.putExtra("restaurant_id", restaurant_id);
                intent.putExtra("restaurant_name",restaurant_name);
                startActivity(intent);
            }
        });
        writeReviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restaurant_id = receiveIntent.getStringExtra("restaurant_id");
                String restaurant_name = receiveIntent.getStringExtra("restaurant_name");

                Intent intent = new Intent(getApplicationContext(), WriteReviewActivity.class);
                intent.putExtra("restaurant_id", restaurant_id);
                intent.putExtra("restaurant_name",restaurant_name);
                startActivity(intent);
            }
        });
        btnBlogReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restaurant_name = receiveIntent.getStringExtra("restaurant_name");

                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("restaurant_name",restaurant_name);
                startActivity(intent);
            }
        });

    }



    public void init(){
        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL endPoint = new URL(SHOW_INFO_STORE_URL);
                    HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                    myConnection.setRequestMethod("POST");

                    String restaurant_id = receiveIntent.getStringExtra("restaurant_id");

                    String dataAll = String.format("restaurant_id=%s",restaurant_id);
                    myConnection.setDoOutput(true);
                    myConnection.getOutputStream().write(dataAll.getBytes());


                    if(myConnection.getResponseCode() == 200){ // 200번은 성공인 경우
                        BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));

                        StringBuffer buffer = new StringBuffer();
                        String temp = null;

                        while((temp = in.readLine()) != null) {
                            buffer.append(temp);
                        }


                        //Date 타입 잘 가져오기 위함
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
                        //Map을 객체로 받아오려면 JsonObject 클래스를 써야됨. ->Gson 라이브러리에 기본으로 있는 것.
                        JsonObject jsonObject = gson.fromJson(buffer.toString(), JsonObject.class);

                        //음식점 정보 - 객체 1
                        DetailRestaurantView storeInfo = gson.fromJson(jsonObject.get("dataStore"), DetailRestaurantView.class);

                        //음식점 메뉴 - 리스트
                        Type typeMenu = new TypeToken<ArrayList<MenuList>>(){}.getType();
                        ArrayList<MenuList> menulist = gson.fromJson(jsonObject.get("dataMenu"), typeMenu);

                        //리뷰3개 보이기 - 리스트
                        Type typeReview = new TypeToken<ArrayList<ReviewListView>>(){}.getType();
                        reviewlist = gson.fromJson(jsonObject.get("dataReview"), typeReview);

                        //리뷰개수 가져오기
                        Type typeReviewCount = new TypeToken<Integer>(){}.getType();
                        int reviewCount = gson.fromJson(jsonObject.get("dataReviewCount"), typeReviewCount);

                        //리뷰가 보이지 않으면, 버튼 감추기
                        if(reviewCount == 0){
                            btnMoreReview.setVisibility(View.GONE);
                        }

                        //data 세팅
                        storeTitle.setText(storeInfo.getRestaurant_name());
                        String total = String.format("%.1f", storeInfo.getSum_score()/storeInfo.getAllcount() );
                        if(total.equals("NaN")){
                            totalTv.setText("0.0");
                        }else{
                            totalTv.setText(total);
                        }
                        addressTv.setText(storeInfo.getAddress_city() + " " + storeInfo.getAddress_gu() + " " + storeInfo.getAddress_dong() + " " + storeInfo.getAddress_detail());
                        telTv.setText(storeInfo.getRestaurant_tel());
                        menutypeTv.setText(storeInfo.getMenu_type());
                        bTimeTv.setText(storeInfo.getRestaurant_businesstime());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        openDateTv.setText(sdf.format(storeInfo.getRestaurant_opendate()));
                        introTv.setText(storeInfo.getRestaurant_description());
                        menuListTv.setText("메뉴 가져와야 됨");

                        StringBuffer discountSb = new StringBuffer();
                        if(storeInfo.isDiscount_coupon())
                            discountSb.append(" 쿠폰사용 ");
                        if(storeInfo.isDiscount_mobile())
                            discountSb.append(" 통신사할인 ");
                        if(storeInfo.isDiscount_savemoney())
                            discountSb.append(" 적립금사용 ");
                        discountTv.setText(discountSb.toString());

                        StringBuffer serviceSb = new StringBuffer();
                        if(storeInfo.isService_kidszon())
                            serviceSb.append(" 키즈존 ");
                        if(storeInfo.isService_pet())
                            serviceSb.append(" 애완동물출입 ");
                        if(storeInfo.isService_wipi())
                            serviceSb.append(" 와이파이 ");
                        if(storeInfo.isService_reservation())
                            serviceSb.append(" 예약 ");
                        if(storeInfo.isService_takeout())
                            serviceSb.append(" TakeOut ");
                        if(storeInfo.isService_parking())
                            serviceSb.append(" 주차가능 ");
                        if(storeInfo.isService_toilet())
                            serviceSb.append(" 남녀화장실구분 ");
                        if(storeInfo.isService_delivery())
                            serviceSb.append(" 배달가능 ");

                        serviceTv.setText(serviceSb.toString());

                        //메뉴리스트 텍스트뷰에 뿌리기
                        StringBuffer data = new StringBuffer();
                        for(int i=0; i<menulist.size(); i++){
                            MenuList menu = menulist.get(i);
                            data.append(menu.getMenu_title()+ "/");
                            data.append(menu.getMenu_price() + "원\n");
                        }

                        menuListTv.setText(data.toString());

                    }else{//그외에 400번 500번 에러가 있는 경우
                        Log.d(LOG_TAG, "서버 연결 및 메세지 읽기 실패1\n");
                        Log.d(LOG_TAG,myConnection.getResponseCode() + "");
                    }
                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                    Log.d(LOG_TAG, "서버 연결 및 메세지 읽기 실패2\n");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                //1. 리사이클러뷰 화면 연결
                reviewRecy = (RecyclerView)findViewById(R.id.reviewRecy);
                //2. 아답터 생성
                InfoReviewAdapter adapter = new InfoReviewAdapter(reviewlist);
                //adapter.setData(dataList);
                //3.리사이클러뷰와 아답터 연결
                reviewRecy.setAdapter(adapter);
                //4.리사이클러뷰매니저
                reviewRecy.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                /*
                if(reviewlist == null){
                    //리뷰가 더이상 없으면 버튼 숨기기.
                    btnMoreReview.setVisibility(View.GONE);
                    //리뷰 없다고 알림
                    alertMsg.setVisibility(View.VISIBLE);
                    Log.d(LOG_TAG,"리뷰없음");
                }
                */


                super.onPostExecute(s);
            }
        }.execute();
    }

}
