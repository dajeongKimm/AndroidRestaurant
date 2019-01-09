package com.example.tje.food;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.food.R;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WriteReviewActivity extends AppCompatActivity {

    private static final String LOG_TAG = "restaurant";
    private static final String URL = "http://192.168.10.11:8080/Final/m";

    public static final int REQ_GARRERY = 100;

    RatingBar flavor, volume, service, total;
    Button btn_write_review, btn_open_gallery;
    EditText contents_text;
    TextView restaurant_name;
    ImageView review_image;

    private String img_path;
    private String img_name;
    String restaurant_id;

    public void initRefs() {
        flavor = (RatingBar) findViewById(R.id.score_flavor);
        volume = (RatingBar) findViewById(R.id.score_volume);
        service = (RatingBar) findViewById(R.id.score_service);
        total = (RatingBar) findViewById(R.id.total_score);

        btn_write_review = (Button) findViewById(R.id.btn_write_review);
        btn_open_gallery = (Button) findViewById(R.id.btn_open_gallery);

        contents_text = (EditText) findViewById(R.id.contents_text);

        restaurant_name = (TextView) findViewById(R.id.restaurant_name);

        review_image = (ImageView) findViewById(R.id.review_image);

    }

    public void setEvents() {


        btn_write_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (total.getRating() == 0.0 ) {
                    Toast.makeText(getApplicationContext(), "총점은 필수체크항목 입니다.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (contents_text.getText().length() > 500) {
                    Toast.makeText(getApplicationContext(), "글자수는 500자로 제한됩니다.", Toast.LENGTH_LONG).show();
                    return;
                }


                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int score_flavor = (int) flavor.getRating();
                        int score_volume = (int) volume.getRating();
                        int score_service = (int) service.getRating();
                        int total_score = (int) total.getRating();
                        String simple_review_contents_text = contents_text.getText().toString();

                        // 입력받은 값 출력
                        Log.d(LOG_TAG, score_flavor + score_volume + score_service + total_score + "");
                        Log.d(LOG_TAG, simple_review_contents_text + "");


                        try {
                            String boundary = "^-----^";
                            String LINE_FEED = "\r\n";
                            String charset = "UTF-8";
                            OutputStream outputStream;
                            PrintWriter writer;

                            URL endPoint = new URL(URL + "/allRestaurantList/" + restaurant_id + "/writeSimpleReview");
                            HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();
                            // POST 형식으로 값 전달

                            myConnection.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary=" + boundary);
                            myConnection.setRequestMethod("POST");
                            myConnection.setDoInput(true);
                            myConnection.setDoOutput(true);
                            myConnection.setUseCaches(false);
                            myConnection.setConnectTimeout(15000);




                            outputStream = myConnection.getOutputStream();
                            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);


                          /** Body에 데이터를 넣어줘야 할경우 없으면 Pass **/
                          writer.append("--" + boundary).append(LINE_FEED);
                          writer.append("Content-Disposition: form-data; name=\"score_flavor\"").append(LINE_FEED);
                          writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
                          writer.append(LINE_FEED);
                          writer.append(score_flavor + "").append(LINE_FEED);
                          writer.flush();

                            /** Body에 데이터를 넣어줘야 할경우 없으면 Pass **/
                            writer.append("--" + boundary).append(LINE_FEED);
                            writer.append("Content-Disposition: form-data; name=\"score_volume\"").append(LINE_FEED);
                            writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
                            writer.append(LINE_FEED);
                            writer.append(score_volume + "").append(LINE_FEED);
                            writer.flush();

                            /** Body에 데이터를 넣어줘야 할경우 없으면 Pass **/
                            writer.append("--" + boundary).append(LINE_FEED);
                            writer.append("Content-Disposition: form-data; name=\"score_service\"").append(LINE_FEED);
                            writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
                            writer.append(LINE_FEED);
                            writer.append(score_service + "").append(LINE_FEED);
                            writer.flush();

                            /** Body에 데이터를 넣어줘야 할경우 없으면 Pass **/
                            writer.append("--" + boundary).append(LINE_FEED);
                            writer.append("Content-Disposition: form-data; name=\"total_score\"").append(LINE_FEED);
                            writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
                            writer.append(LINE_FEED);
                            writer.append(total_score + "").append(LINE_FEED);
                            writer.flush();

                            // form-data; name="simple_review_photo"
                            /** Body에 데이터를 넣어줘야 할경우 없으면 Pass **/
                            writer.append("--" + boundary).append(LINE_FEED);
                            writer.append("Content-Disposition: form-data; name=\"simple_review_contents_text\"").append(LINE_FEED);
                            writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
                            writer.append(LINE_FEED);
                            writer.append(simple_review_contents_text + "").append(LINE_FEED);
                            writer.flush();


                            if ( review_image.getVisibility() == View.VISIBLE ) {
                                /** 파일 데이터를 넣는 부분**/
                                writer.append("--" + boundary).append(LINE_FEED);
                                writer.append("Content-Disposition: form-data; name=\"simple_review_photo\"; filename=\"" + img_name + "\"").append(LINE_FEED);
                                writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(img_name)).append(LINE_FEED);
                                writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
                                writer.append(LINE_FEED);
                                writer.flush();

                                File file = new File(img_path);
                                FileInputStream inputStream = new FileInputStream(file);
                                byte[] buffer = new byte[(int) file.length()];
                                int bytesRead = -1;
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                                outputStream.flush();
                                inputStream.close();
                                writer.append(LINE_FEED);
                                writer.flush();
                            }

                            writer.append("--" + boundary + "--").append(LINE_FEED);
                            writer.close();

                            int responseCode = myConnection.getResponseCode();

                            Log.d(LOG_TAG, responseCode + "");





//                            String requestParam = String.format("score_flavor=%d&score_volume=%d&score_service=%d&total_score=%d&simple_review_contents_text=%s",
//                                    flavor, volume, service, total, contents);
//                            myConnection.getOutputStream().write(requestParam.getBytes());


                        } catch (Exception e) {
                            Log.d(LOG_TAG, e.getMessage());//

                        }

                    }
                });

                Toast.makeText(getApplicationContext(), "리뷰가 작성되었습니다.", Toast.LENGTH_LONG).show();

                finish();

            }
        });

        btn_open_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_GARRERY);
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
        restaurant_id = intent.getStringExtra("restaurant_id");
        restaurant_name.setText(intent.getStringExtra("restaurant_name"));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            final Uri imageUrl;

            switch(requestCode) {
                case REQ_GARRERY :
                    imageUrl = data.getData();

                    review_image.setVisibility(View.VISIBLE);
                    review_image.setImageURI(imageUrl);
                    Log.d("onActivityResult", imageUrl.getPath());

                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(imageUrl, projection, null, null, null);
                    startManagingCursor(cursor);
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();

                    img_path = cursor.getString(columnIndex);
                    File file = new File(img_path);
                    img_name = file.getName();
                    Log.d(LOG_TAG, img_name);
            }
        }
    }
}
