package com.example.tje.food;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tje.food.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class InfoReviewListHoler extends RecyclerView.ViewHolder {

    private static final String LOG_TAG = "inforeviewlistholder";
    private static final String URL_MAPPING = Const.INFOREVIEWLISTHOLDER_IP;

    //2. 리스트 아이템에 있는 사용할 위젯 정의
    TextView nicknameTv, likeCountTv, badCountTv, writeDateTv, contentsTv, reviewId;
    ToggleButton like_btn, bad_btn;
    RatingBar ratingBar;
    ImageView memberImage, infoImage;

    LinearLayout moreReview;

    final Handler likeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            likeCountTv.setText(msg.arg1 + "");
        }
    };
    final Handler badHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            badCountTv.setText(msg.arg1 + "");
        }
    };

    //1. 생성자
    public InfoReviewListHoler(View v){
        super(v);

        //3. findViewById로 연결
        nicknameTv = (TextView)v.findViewById(R.id.nicknameTv);
        likeCountTv = (TextView)v.findViewById(R.id.likeCountTv);
        badCountTv = (TextView) v.findViewById(R.id.badCountTv);
        writeDateTv = (TextView)v.findViewById(R.id.writeDateTv);
        contentsTv = (TextView)v.findViewById(R.id.contentsTv);
        ratingBar = (RatingBar)v.findViewById(R.id.ratingBar);
        like_btn = (ToggleButton)v.findViewById(R.id.like_btn);
        bad_btn = (ToggleButton)v.findViewById(R.id.bad_btn);

        memberImage = (ImageView)v.findViewById(R.id.memberImage);
        infoImage = (ImageView)v.findViewById(R.id.infoImage);

        reviewId = (TextView)v.findViewById(R.id.reviewId);

        moreReview = (LinearLayout)v.findViewById(R.id.moreReview);

        moreReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SimpleReviewDetailActivity.class);
                //상세리뷰보기에서 보내줘야 할것들.
                intent.putExtra("simple_review_id", reviewId.getText());
                v.getContext().getApplicationContext().startActivity(intent);
            }
        });

        // like
        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(LOG_TAG, "like btn click");

                        if(like_btn.isChecked()) {      // toggle btn on

                            if(bad_btn.isChecked()) {
                                bad_btn.setChecked(false);
                                //
                                try {
                                    URL endPoint = new URL(URL_MAPPING + "/cancelBadCount");
                                    HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                                    myConnection.setRequestMethod("POST");

                                    int simple_review_id = Integer.parseInt(reviewId.getText().toString());

                                    String requestParam = String.format("simple_review_id=%d", simple_review_id);
                                    myConnection.getOutputStream().write(requestParam.getBytes());

                                    if( myConnection.getResponseCode() == 200 ) {
                                        Log.d(LOG_TAG, "cancel bad count" + myConnection.getResponseCode() + "");

                                        BufferedReader in = new BufferedReader(
                                                new InputStreamReader(myConnection.getInputStream()));
                                        StringBuffer buffer = new StringBuffer();
                                        String temp = null;

                                        while((temp = in.readLine()) != null) {
                                            buffer.append(temp);
                                        }

                                        Log.d(LOG_TAG, buffer.toString() + "");

                                        Message count = badHandler.obtainMessage();
                                        count.arg1 = Integer.parseInt(buffer.toString());
                                        badHandler.sendMessage(count);
                                    }

                                } catch (Exception e) {
                                    Log.d(LOG_TAG, "에러 발생");
                                    e.getMessage();
                                }
                            }



                            try {
                                Log.d(LOG_TAG, "1");
                                URL endPoint = new URL(URL_MAPPING + "/addLikeCount");
                                HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                                myConnection.setRequestMethod("POST");
                                Log.d(LOG_TAG, "2");

                                int simple_review_id = Integer.parseInt(reviewId.getText().toString());

                                String requestParam = String.format("simple_review_id=%d", simple_review_id);
                                myConnection.getOutputStream().write(requestParam.getBytes());
                                Log.d(LOG_TAG, "3");

                                if( myConnection.getResponseCode() == 200 ) {
                                    Log.d(LOG_TAG, "add like count" + myConnection.getResponseCode() + "");

                                    BufferedReader in = new BufferedReader(
                                            new InputStreamReader(myConnection.getInputStream()));
                                    StringBuffer buffer = new StringBuffer();
                                    String temp = null;

                                    while((temp = in.readLine()) != null) {
                                        buffer.append(temp);
                                    }

                                    Log.d(LOG_TAG, buffer.toString() + "");

                                    Message count = likeHandler.obtainMessage();
                                    count.arg1 = Integer.parseInt(buffer.toString());
                                    likeHandler.sendMessage(count);
                                }

                            } catch (Exception e) {
                                Log.d(LOG_TAG, "에러 발생" + e.getMessage());
                                e.getMessage();
                            }

                        } else {
                            Log.d(LOG_TAG, "like btn cancel");

                            try {
                                URL endPoint = new URL(URL_MAPPING + "/cancelLikeCount");
                                HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                                myConnection.setRequestMethod("POST");

                                int simple_review_id = Integer.parseInt(reviewId.getText().toString());

                                String requestParam = String.format("simple_review_id=%d", simple_review_id);
                                myConnection.getOutputStream().write(requestParam.getBytes());

                                if( myConnection.getResponseCode() == 200 ) {
                                    Log.d(LOG_TAG, "cancel like count" + myConnection.getResponseCode() + "");

                                    BufferedReader in = new BufferedReader(
                                            new InputStreamReader(myConnection.getInputStream()));
                                    StringBuffer buffer = new StringBuffer();
                                    String temp = null;

                                    while((temp = in.readLine()) != null) {
                                        buffer.append(temp);
                                    }

                                    Log.d(LOG_TAG, buffer.toString() + "");

                                    Message count = likeHandler.obtainMessage();
                                    count.arg1 = Integer.parseInt(buffer.toString());
                                    likeHandler.sendMessage(count);
                                }

                            } catch (Exception e) {
                                Log.d(LOG_TAG, "에러 발생");
                                e.getMessage();
                            }
                        }

                    }
                });
            }
        });

        // bad
        bad_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(LOG_TAG, "bad btn click");

                        if(bad_btn.isChecked()) {

                            if(like_btn.isChecked()) {
                                like_btn.setChecked(false);
                                //
                                try {
                                    URL endPoint = new URL(URL_MAPPING + "/cancelLikeCount");
                                    HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                                    myConnection.setRequestMethod("POST");

                                    int simple_review_id = Integer.parseInt(reviewId.getText().toString());

                                    String requestParam = String.format("simple_review_id=%d", simple_review_id);
                                    myConnection.getOutputStream().write(requestParam.getBytes());

                                    if( myConnection.getResponseCode() == 200 ) {
                                        Log.d(LOG_TAG, "cancel like count" + myConnection.getResponseCode() + "");

                                        BufferedReader in = new BufferedReader(
                                                new InputStreamReader(myConnection.getInputStream()));
                                        StringBuffer buffer = new StringBuffer();
                                        String temp = null;

                                        while((temp = in.readLine()) != null) {
                                            buffer.append(temp);
                                        }

                                        Log.d(LOG_TAG, buffer.toString() + "");

                                        Message count = likeHandler.obtainMessage();
                                        count.arg1 = Integer.parseInt(buffer.toString());
                                        likeHandler.sendMessage(count);
                                    }

                                } catch (Exception e) {
                                    Log.d(LOG_TAG, "에러 발생");
                                    e.getMessage();
                                }
                            }


                            try {
                                Log.d(LOG_TAG, "1");
                                URL endPoint = new URL(URL_MAPPING + "/addBadCount");
                                HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                                myConnection.setRequestMethod("POST");
                                Log.d(LOG_TAG, "2");

                                int simple_review_id = Integer.parseInt(reviewId.getText().toString());

                                String requestParam = String.format("simple_review_id=%d", simple_review_id);
                                myConnection.getOutputStream().write(requestParam.getBytes());
                                Log.d(LOG_TAG, "3");

                                if( myConnection.getResponseCode() == 200 ) {
                                    Log.d(LOG_TAG, "add bad count" + myConnection.getResponseCode() + "");

                                    BufferedReader in = new BufferedReader(
                                            new InputStreamReader(myConnection.getInputStream()));
                                    StringBuffer buffer = new StringBuffer();
                                    String temp = null;

                                    while((temp = in.readLine()) != null) {
                                        buffer.append(temp);
                                    }

                                    Log.d(LOG_TAG, buffer.toString() + "");

                                    //
                                    Message count = badHandler.obtainMessage();
                                    count.arg1 = Integer.parseInt(buffer.toString());
                                    badHandler.sendMessage(count);
                                }

                            } catch (Exception e) {
                                Log.d(LOG_TAG, "에러 발생" + e.getMessage());
                                e.getMessage();
                            }

                        } else {
                            Log.d(LOG_TAG, "bad btn cancel");

                            try {
                                URL endPoint = new URL(URL_MAPPING + "/cancelBadCount");
                                HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                                myConnection.setRequestMethod("POST");

                                int simple_review_id = Integer.parseInt(reviewId.getText().toString());

                                String requestParam = String.format("simple_review_id=%d", simple_review_id);
                                myConnection.getOutputStream().write(requestParam.getBytes());

                                if( myConnection.getResponseCode() == 200 ) {
                                    Log.d(LOG_TAG, "cancel bad count" + myConnection.getResponseCode() + "");

                                    BufferedReader in = new BufferedReader(
                                            new InputStreamReader(myConnection.getInputStream()));
                                    StringBuffer buffer = new StringBuffer();
                                    String temp = null;

                                    while((temp = in.readLine()) != null) {
                                        buffer.append(temp);
                                    }

                                    Log.d(LOG_TAG, buffer.toString() + "");

                                    Message count = badHandler.obtainMessage();
                                    count.arg1 = Integer.parseInt(buffer.toString());
                                    badHandler.sendMessage(count);
                                }

                            } catch (Exception e) {
                                Log.d(LOG_TAG, "에러 발생");
                                e.getMessage();
                            }
                        }

                    }
                });
            }
        });

    }


}
