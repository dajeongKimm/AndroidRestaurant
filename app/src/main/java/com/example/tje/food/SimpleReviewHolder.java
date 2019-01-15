package com.example.tje.food;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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

import com.example.tje.food.Model.Member;
import com.example.tje.food.Model.RestaurantListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SimpleReviewHolder extends RecyclerView.ViewHolder {

    private static final String LOG_TAG = "reviewholder";
    private static final String URL_MAPPING = Const.SIMPLEREVIEWHOLER_IP;

    // 리스트 아이템에 있는 사용할 위젯 정의
    ImageButton member_photo_btn;
    TextView nicknameTv, like_count, bad_count, review_registdate, contents_text, review_id, restaurant_id;
    ToggleButton like_btn, bad_btn;
    RatingBar total_score_rating;
    LinearLayout review_layout;
    ImageView list_image;

    ImageButton moreBtn;
    Button updateBtn, deleteBtn, notifyBtn;
    LinearLayout updateDelete;

    Member loginmember;


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
    public SimpleReviewHolder(@NonNull final View itemView, Intent receiveIntent) {
        super(itemView);

        loginmember = (Member) receiveIntent.getSerializableExtra("loginmember");
        //삭제 버튼
        moreBtn = (ImageButton) itemView.findViewById(R.id.moreBtn);
        updateBtn = (Button) itemView.findViewById(R.id.updateBtn);
        deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
        notifyBtn = (Button) itemView.findViewById(R.id.notifyBtn);
        updateDelete = (LinearLayout)itemView.findViewById(R.id.updateDelete);

        restaurant_id = (TextView) itemView.findViewById(R.id.restaurant_id);

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

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateDelete.getVisibility() == View.GONE) {
                    //로그인검사 성공
                    if(loginmember != null && loginmember.getMember_nickname().equals(nicknameTv.getText())) {
                        updateDelete.setVisibility(View.VISIBLE);
                        deleteBtn.setVisibility(View.VISIBLE);
                        updateBtn.setVisibility(View.VISIBLE);
                        notifyBtn.setVisibility(View.GONE);

                        deleteBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                simple_review_delete();
                            }
                        });

                    }else{//로그인검사 실패
                        updateDelete.setVisibility(View.VISIBLE);
                        deleteBtn.setVisibility(View.GONE);
                        updateBtn.setVisibility(View.GONE);
                        notifyBtn.setVisibility(View.VISIBLE);
                    }
                } else if (updateDelete.getVisibility() == View.VISIBLE) {
                    updateDelete.setVisibility(View.GONE);
                }
            }
        });
    }

    //삭제 통신
    public void simple_review_delete(){
        new AsyncTask<String,Void,String>() {
            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL endPoint = new URL(Const.SIMPLEREVIEW_DELETE);
                    HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                    myConnection.setRequestMethod("POST");

                    String rest_id = restaurant_id.getText().toString();
                    String sim_id = review_id.getText().toString();

                    String dataAll = String.format("restaurant_id=%s&simple_review_id=%s",rest_id,sim_id);
                    myConnection.setDoOutput(true);
                    myConnection.getOutputStream().write(dataAll.getBytes());

                    if (myConnection.getResponseCode() == 200) { // 200번은 성공인 경우
                        BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));



                    } else {//그외에 400번 500번 에러가 있는 경우
                        Log.d(LOG_TAG, "서버 연결 및 메세지 읽기 실패1\n");
                        Log.d(LOG_TAG, myConnection.getResponseCode() + "");
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

                //다이얼로그
                Toast.makeText(itemView.getContext().getApplicationContext(),"리뷰가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(itemView.getContext().getApplicationContext(),ShowStoreList.class);
                itemView.getContext().startActivity(intent);

                super.onPostExecute(s);
            }
        }.execute();

    }
}
