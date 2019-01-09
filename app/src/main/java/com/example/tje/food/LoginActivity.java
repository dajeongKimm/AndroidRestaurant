package com.example.tje.food;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.food.Model.Member;
import com.example.tje.food.Model.Member_address;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String LOG_TAG = "signuptestapp";
    private  static final String SERVER_ADDRESS = Const.LOGINACTIVITY_IP;

    Button btn_login;
    Button btn_signup_form;
   // Button btn_logout;


    EditText str_login_id;
    EditText str_login_pwd;



    Member loginmember;


    public void initRefs(){

        btn_login = findViewById(R.id.btn_login);
     //   btn_logout = findViewById(R.id.btn_logout);
        btn_signup_form = findViewById(R.id.btn_signup_form);

        str_login_id = findViewById(R.id.str_login_id);
        str_login_pwd = findViewById(R.id.str_login_pwd);


        loginmember = null;

    }
    public void setEvents(){


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            URL endPoint = new URL(SERVER_ADDRESS + "/member/login.m");
                            HttpURLConnection myConnection = (HttpURLConnection) endPoint.openConnection();

                            // JSESSIONID 관련 - 1 시작
                            String cookieString = CookieManager.getInstance().getCookie(
                                    SERVER_ADDRESS);
                            if (cookieString != null) {
                                myConnection.setRequestProperty("Cookie", cookieString);
                            }
                            // JSESSIONID 관련 - 1 끝

                            // POST 값 전달
                            myConnection.setRequestMethod("POST");

                            final String login_id_data = str_login_id.getText().toString();
                            final String login_pwd_data = str_login_pwd.getText().toString();

                            String requestParam = String.format("member_id=%s&member_password=%s", login_id_data, login_pwd_data);
                            myConnection.setDoOutput(true);
                            myConnection.getOutputStream().write(requestParam.getBytes());
                            // POST 값 전달 끝

                            // JSESSIONID 관련 - 2 시작
                            Map<String, List<String>> headerFields = myConnection.getHeaderFields();
                            String COOKIES_HEADER = "Set-Cookie";
                            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                            if (cookiesHeader != null) {
                                for (String cookie : cookiesHeader) {
                                    String cookieName = HttpCookie.parse(cookie).get(0).getName();
                                    String cookieValue = HttpCookie.parse(cookie).get(0).getValue();

                                    cookieString = cookieName + "=" + cookieValue;
                                    CookieManager.getInstance().setCookie(SERVER_ADDRESS, cookieString);
                                }
                            }
                            // JSESSIONID 관련 - 2 끝

                            if (myConnection.getResponseCode() == 200) {
                                Log.d(LOG_TAG, "200번 성공으로 들어옴");
                                BufferedReader in =
                                        new BufferedReader(
                                                new InputStreamReader(
                                                        myConnection.getInputStream()));
                                StringBuffer buffer = new StringBuffer();
                                String temp = null;
                                while ((temp = in.readLine()) != null) {
                                    buffer.append(temp);
                                }
                                Log.d(LOG_TAG, "중간체크");
                                Log.d(LOG_TAG, buffer.toString());
                                // 서버에서로부터 객체를 json 형식으로 받아 로그인한 해당 멤버의 정보를 받아와서
                                // Member 객체에 저장
                                Gson gson = new Gson();
                                loginmember = gson.fromJson(buffer.toString(), Member.class);


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (loginmember == null){
                                            Toast.makeText(getApplicationContext(),"아이디와 패스워드가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        Toast.makeText(getApplicationContext(),"로그인 하셨습니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);


                                        intent.putExtra("loginmember",loginmember);
                                        startActivity(intent);
                                        finish();

                                    }
                                });

                            } else {    // 200이 아닐 때

                                Log.d(LOG_TAG, "200번x");
                            }
                        }catch (Exception e){

                            e.printStackTrace();
                            Log.d(LOG_TAG, "login catch - 연결실패");
                            Log.d(LOG_TAG, e.getMessage());

                        }

                    }
                });
            }
        });



/*
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL endPoint = new URL(SERVER_ADDRESS+"/member/logout.m");
                            HttpURLConnection myConnection =
                                    (HttpURLConnection) endPoint.openConnection();





                            // myConnection.setRequestMethod("POST");
                            if (myConnection.getResponseCode() == 200) {
                                Log.d(LOG_TAG,"웹 접속 성공");

                                //쿠키 삭제
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        CookieManager cookieManager = CookieManager.getInstance();
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            cookieManager.removeSessionCookies(new ValueCallback<Boolean>() {
                                                @Override
                                                public void onReceiveValue(Boolean value) {
                                                    Log.d(LOG_TAG, "## 롤리팝 이상 버전의 removeSessionCookie() 호출 후");
                                                }
                                            });
                                        }else{
                                            cookieManager.removeSessionCookie();
                                            Log.d(LOG_TAG, "## 롤리팝 미만 버전의 removeSessionCookie() 호출 후");
                                        }

                                    }
                                });

                            }else{
                                //Error
                                Log.d(LOG_TAG,"웹 접속 실패");


                            }
                        }catch (Exception e){
                            Log.d(LOG_TAG,"예외처리 :"+e.getMessage());

                        }
                    }
                });

            }
        });

       */

        btn_signup_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);

            }
        });







    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initRefs();
        setEvents();
    }
}
