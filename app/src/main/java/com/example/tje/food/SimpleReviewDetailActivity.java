package com.example.tje.food;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.tje.food.Model.ReviewListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleReviewDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = "simplereviewdetail";
    private static final String URL_MAPPING = "http://192.168.0.18:8080/Final/m";
    private static final String M_IMAGE_URL = "http://192.168.0.18:8080/Final/resources/upload/memberImage/";
    private static final String D_URL = "http://192.168.0.18:8080/Final/resources/upload/simpleReview/";

    ImageButton member_photo_btn;
    TextView nicknameTv, review_id, like_count, bad_count, review_registdate, contents_text;
    ToggleButton like_btn, bad_btn;
    RatingBar score_flavor, score_volume, score_service, total_score;

    Bitmap bitmap;
    ReviewListView data;
    ImageView detail_image;

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

    public void initRefs() {
        member_photo_btn = (ImageButton) findViewById(R.id.member_photo_btn);

        nicknameTv = (TextView) findViewById(R.id.nicknameTv);

        like_count = (TextView) findViewById(R.id.like_count);
        bad_count = (TextView) findViewById(R.id.bad_count);
        review_registdate = (TextView) findViewById(R.id.review_registdate);
        contents_text = (TextView) findViewById(R.id.contents_text);

        like_btn = (ToggleButton) findViewById(R.id.like_btn);
        bad_btn = (ToggleButton) findViewById(R.id.bad_btn);

        score_flavor = (RatingBar) findViewById(R.id.score_flavor_rating);
        score_volume = (RatingBar) findViewById(R.id.score_volume_rating);
        score_service = (RatingBar) findViewById(R.id.score_service_rating);
        total_score = (RatingBar) findViewById(R.id.total_score_rating);

        review_id = (TextView) findViewById(R.id.review_id);
        detail_image = (ImageView) findViewById(R.id.detail_image);
    }

    public void setEvent() {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_review_detail);

        initRefs();
        setEvent();

        new AsyncTask<String, Integer, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    Log.d(LOG_TAG, "intent 후");
                    java.net.URL endPoint = new URL(URL_MAPPING + "/select_one");
                    HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                    myConnection.setRequestMethod("POST");

                    Intent intent = getIntent();
                    int simple_review_id = Integer.parseInt(intent.getStringExtra("simple_review_id"));
                    String requestParam = String.format("simple_review_id=%d", simple_review_id);
                    myConnection.getOutputStream().write(requestParam.getBytes());

                    if( myConnection.getResponseCode() == 200 ) {
                        Log.d(LOG_TAG, myConnection.getResponseCode() + "");

                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(myConnection.getInputStream()));
                        StringBuffer buffer = new StringBuffer();
                        String temp = null;


                        while((temp = in.readLine()) != null) {
                            buffer.append(temp);
                        }

                        Log.d(LOG_TAG, buffer.toString());


                        /////////
                        // review_list
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();

                        data = gson.fromJson(buffer.toString(), ReviewListView.class);

                        try {
                            URL url = new URL(M_IMAGE_URL + data.getMember_photo());
                            Log.d(LOG_TAG, url + "");//
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.connect();

                            bitmap = BitmapFactory.decodeStream(conn.getInputStream());

                        } catch (Exception e) {
                            Log.d(LOG_TAG, e.getMessage());//
                        }



//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {


//                            }
//                        });

                    } else {
                        Log.d("에러 발생", myConnection.getResponseCode() + "");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return bitmap;
            }
            //
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                member_photo_btn.setImageBitmap(bitmap);
                nicknameTv.setText(data.getMember_nickname());
                review_id.setText(data.getSimple_review_id() + "");
                like_count.setText(data.getSimple_review_like_count() + "");
                bad_count.setText(data.getSimple_review_notify_count() + "");
                review_registdate.setText(data.getSimple_review_registdate());
                contents_text.setText(data.getSimple_review_contents_text());


                score_flavor.setRating(data.getScore_flavor());
                score_volume.setRating(data.getScore_volume());
                score_service.setRating(data.getScore_service());
                total_score.setRating(data.getTotal_score());

                if(data.getFile_name() != null) {
                    Picasso.get().load(D_URL + data.getFile_name()).into(detail_image);
                    detail_image.setVisibility(View.VISIBLE);
                }
            }
        }.execute();



    }
}
