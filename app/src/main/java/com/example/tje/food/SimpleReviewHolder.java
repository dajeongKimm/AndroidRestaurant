package com.example.tje.food;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleReviewHolder extends RecyclerView.ViewHolder {

    private static final String LOG_TAG = "reviewholder";
    private static final String URL_MAPPING = "http://192.168.0.18:8080/Final/m";

    // 리스트 아이템에 있는 사용할 위젯 정의
    ImageButton member_photo_btn;
    TextView nicknameTv, like_count, bad_count, review_registdate, contents_text, review_id;
    ToggleButton like_btn, bad_btn;
    RatingBar total_score_rating;
    LinearLayout review_layout;
    ImageView list_image;

    final Handler likeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            like_count.setText(msg.arg1 + "");
        }
    };
    final Handler badHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            bad_count.setText(msg.arg1 + "");
        }
    };

    // 생성자 생성
    public SimpleReviewHolder(@NonNull final View itemView) {
        super(itemView);

        //
        list_image = (ImageView) itemView.findViewById(R.id.list_image);
        member_photo_btn = (ImageButton) itemView.findViewById(R.id.member_photo_btn);

        nicknameTv = (TextView) itemView.findViewById(R.id.nicknameTv);
        like_count = (TextView) itemView.findViewById(R.id.like_count);
        bad_count = (TextView) itemView.findViewById(R.id.bad_count);
        review_registdate = (TextView) itemView.findViewById(R.id.review_registdate);
        contents_text = (TextView) itemView.findViewById(R.id.contents_text);
        review_id = (TextView) itemView.findViewById(R.id.review_id);

        like_btn = (ToggleButton) itemView.findViewById(R.id.like_btn);
        bad_btn = (ToggleButton) itemView.findViewById(R.id.bad_btn);

        total_score_rating = (RatingBar) itemView.findViewById(R.id.total_score_rating);

        review_layout = (LinearLayout) itemView.findViewById(R.id.review_layout);

        review_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), SimpleReviewDetailActivity.class);
                intent.putExtra("simple_review_id", review_id.getText().toString());
                Log.d(LOG_TAG, "intent 전");
                itemView.getContext().startActivity(intent);
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

                                    int simple_review_id = Integer.parseInt(review_id.getText().toString());

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

                                int simple_review_id = Integer.parseInt(review_id.getText().toString());

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

                                int simple_review_id = Integer.parseInt(review_id.getText().toString());

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

                                    int simple_review_id = Integer.parseInt(review_id.getText().toString());

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

                                int simple_review_id = Integer.parseInt(review_id.getText().toString());

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

                                int simple_review_id = Integer.parseInt(review_id.getText().toString());

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
