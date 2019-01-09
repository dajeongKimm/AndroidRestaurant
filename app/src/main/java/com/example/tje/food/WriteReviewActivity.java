package com.example.tje.food;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;

public class WriteReviewActivity extends AppCompatActivity {

    private static final String LOG_TAG = "restaurant";
    private static final String URL = "http://192.168.10.11:8080/Final/m";

    RatingBar score_flavor, score_volume, score_service, total_score;
    Button btn_write_review;
    EditText contents_text;
    TextView restaurant_name;

    int restaurant_id;

    public void initRefs() {
        score_flavor = (RatingBar) findViewById(R.id.score_flavor);
        score_volume = (RatingBar) findViewById(R.id.score_volume);
        score_service = (RatingBar) findViewById(R.id.score_service);
        total_score = (RatingBar) findViewById(R.id.total_score);

        btn_write_review = (Button) findViewById(R.id.btn_write_review);

        contents_text = (EditText) findViewById(R.id.contents_text);

        restaurant_name = (TextView) findViewById(R.id.restaurant_name);

    }

    public void setEvents() {
        btn_write_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int flavor = (int) score_flavor.getRating();
                        int volume = (int) score_volume.getRating();
                        int service = (int) score_service.getRating();
                        int total = (int) total_score.getRating();
                        String contents = contents_text.getText().toString();

                        // 입력받은 값 출력
                        Log.d(LOG_TAG, flavor + volume + service + total + "");
                        Log.d(LOG_TAG, contents + "");


                        try {
                            URL endPoint = new URL(URL + "/allRestaurantList/" + restaurant_id + "/writeSimpleReview");
                            HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                            // POST 형식으로 값 전달
                            myConnection.setRequestMethod("POST");


                            String requestParam = String.format("score_flavor=%d&score_volume=%d&score_service=%d&total_score=%d&simple_review_contents_text=%s",
                                                                    flavor, volume, service, total, contents);
                            myConnection.getOutputStream().write(requestParam.getBytes());
                            Log.d(LOG_TAG, myConnection.getResponseCode() + "");

                        } catch (Exception e) {
                            Log.d(LOG_TAG, e.getMessage());//

                        }

                    }
                });

                finish();

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_simple_review);

        // 권한 얻기
        int permission_internet = ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.INTERNET);

        if( permission_internet == PackageManager.PERMISSION_DENIED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, 112);
        }

        initRefs();
        setEvents();

        Intent intent = getIntent();
        restaurant_id = intent.getIntExtra("restaurant_id", 1);
        restaurant_name.setText(intent.getStringExtra("restaurant_name"));
    }

}
