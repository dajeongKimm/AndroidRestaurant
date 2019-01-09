package com.example.tje.food;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.food.Model.Count;
import com.example.tje.food.Model.Member;
import com.example.tje.food.Model.Member_address;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyPageUpdateActivity extends AppCompatActivity {

    private  static final String SERVER_ADDRESS = "http://192.168.10.8:8080/Final";
    private static final String LOG_TAG = "signuptestapp";


    TextView str_update_hidden_id;
    TextView str_update_hidden_add_id;
    TextView str_mypage_update_name;
    EditText str_mypage_update_nickname;
    EditText str_mypage_update_tel;;
    EditText str_mypage_update_email;
    EditText str_add_postcode;
    EditText str_add_general;
    EditText str_add_detail;
    EditText str_mypage_update_pwd;
    EditText str_mypage_update_pwdconfirm;
    TextView str_update_nickcheck;

    TextView str_update_pwd_check;
    TextView str_update_pwdconfim_check;




    Member loginmember;



    Button btn_add_search1;
    Button btn_update_confirm;
    Button btn_update_cancle;


    Count nickname_count;


    public void setRefs(){
        str_update_hidden_id = findViewById(R.id.str_update_hidden_id);
        str_update_hidden_add_id = findViewById(R.id.str_update_hidden_add_id);
        str_mypage_update_nickname = findViewById(R.id.str_mypage_update_nickname);
        str_mypage_update_name = findViewById(R.id.str_mypage_update_name);
        str_mypage_update_tel = findViewById(R.id.str_mypage_update_tel);
        str_mypage_update_email = findViewById(R.id.str_mypage_update_email);
        str_add_postcode = findViewById(R.id.str_add_postcode);
        str_add_general = findViewById(R.id.str_add_general);
        str_add_detail = findViewById(R.id.str_add_detail);
        str_mypage_update_pwd = findViewById(R.id.str_mypage_update_pwd);
        str_mypage_update_pwdconfirm = findViewById(R.id.str_mypage_update_pwdconfirm);
        str_update_nickcheck = findViewById(R.id.str_update_nickcheck);
        str_update_pwd_check = findViewById(R.id.str_update_pwd_check);
        str_update_pwdconfim_check = findViewById(R.id.str_update_pwdconfim_check);

        btn_add_search1 = findViewById(R.id.btn_add_search1);
        btn_update_confirm = findViewById(R.id.btn_update_confirm);
        btn_update_cancle = findViewById(R.id.btn_update_cancle);




        loginmember = null;

    }

    public void setEvents(){
        Intent intent = getIntent();
        loginmember =(Member) intent.getSerializableExtra("loginmember");



        str_update_hidden_id.setText(loginmember.getMember_id());
        str_update_hidden_add_id.setText(Integer.toString(loginmember.getMember_address_id()));
        str_mypage_update_nickname.setText(loginmember.getMember_nickname());
        str_mypage_update_name.setText(loginmember.getMember_name());
        str_mypage_update_tel.setText(loginmember.getMember_tel());
        str_mypage_update_email.setText(loginmember.getMember_email());
        str_add_postcode.setText(loginmember.getMember_address().getAddress_postcode());
        str_add_general.setText(loginmember.getMember_address().getAddress_general());
        str_add_detail.setText(loginmember.getMember_address().getAddress_detail());




        btn_update_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AsyncTask.execute(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            URL endPoint = new URL(SERVER_ADDRESS+"/member/myPageUpdate.m");
                            final HttpURLConnection myConnection =
                                    (HttpURLConnection) endPoint.openConnection();
                            // 메소드 방식 변경
                            myConnection.setRequestMethod("POST");




                            String id_data = str_update_hidden_id.getText().toString();
                            String add_id_data = str_update_hidden_add_id.getText().toString();
                            String pwd_data = str_mypage_update_pwd.getText().toString();
                            String pwd_confirm_data = str_mypage_update_pwdconfirm.getText().toString();
                            String nickname_data = str_mypage_update_nickname.getText().toString();
                            String tel_data = str_mypage_update_tel.getText().toString();
                            String email_data = str_mypage_update_email.getText().toString();
                            String name_data = str_mypage_update_name.getText().toString();

                            // String photo_data = " ";
                            String add1_data = str_add_postcode.getText().toString();
                            String add2_data = str_add_general.getText().toString();
                            String add3_data = str_add_detail.getText().toString();




                            if(pwd_data == null  || pwd_data.isEmpty()){
                                str_mypage_update_pwd.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        str_mypage_update_pwd.setFocusableInTouchMode(true);
                                        str_mypage_update_pwd.requestFocus();

                                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                                        imm.showSoftInput(str_mypage_update_pwd,0);
                                        Log.d(LOG_TAG,"str_pwd 값이 null값 종료");

                                        return ;

                                    }
                                });
                            }else if(!pwd_data.equals(pwd_confirm_data)){
                                str_mypage_update_pwdconfirm.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        str_mypage_update_pwdconfirm.setFocusableInTouchMode(true);
                                        str_mypage_update_pwdconfirm.requestFocus();

                                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                                        imm.showSoftInput(str_mypage_update_pwdconfirm,0);
                                        Log.d(LOG_TAG,"패스워드가 일치하지 않음  종료");

                                        return ;

                                    }
                                });
                            }else if( pwd_confirm_data == null|| pwd_confirm_data.isEmpty()) {
                                str_mypage_update_pwdconfirm.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        str_mypage_update_pwdconfirm.setFocusableInTouchMode(true);
                                        str_mypage_update_pwdconfirm.requestFocus();

                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                        imm.showSoftInput(str_mypage_update_pwdconfirm, 0);
                                        Log.d(LOG_TAG, "str_pwd_confirm 값이 null값 종료");

                                        return;

                                    }
                                });
                            }else if( nickname_data == null|| nickname_data.isEmpty() || nickname_count.getCount() == 1 || nickname_count == null) {
                                str_mypage_update_nickname.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        str_mypage_update_nickname.setFocusableInTouchMode(true);
                                        str_mypage_update_nickname.requestFocus();

                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                        imm.showSoftInput(str_mypage_update_nickname, 0);
                                        Log.d(LOG_TAG, "str_nickname 값이 null값 종료");

                                        return;

                                    }
                                });
                            }else if( tel_data == null|| tel_data.isEmpty()) {
                                str_mypage_update_tel.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        str_mypage_update_tel.setFocusableInTouchMode(true);
                                        str_mypage_update_tel.requestFocus();

                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                        imm.showSoftInput(str_mypage_update_tel, 0);
                                        Log.d(LOG_TAG, "str_tel 값이 null값 종료");

                                        return;

                                    }
                                });
                            }else if( email_data == null|| email_data.isEmpty()) {
                                str_mypage_update_email.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        str_mypage_update_email.setFocusableInTouchMode(true);
                                        str_mypage_update_email.requestFocus();

                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                        imm.showSoftInput(str_mypage_update_email, 0);
                                        Log.d(LOG_TAG, "str_email 값이 null값 종료");

                                        return;

                                    }
                                });

                            }else if( add1_data == null|| add2_data == null|| add3_data == null|| add1_data.isEmpty() || add2_data.isEmpty() || add3_data.isEmpty() ) {
                                str_add_detail.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        str_add_detail.setFocusableInTouchMode(true);
                                        str_add_detail.requestFocus();

                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                        imm.showSoftInput(str_add_detail, 0);
                                        Log.d(LOG_TAG, "str_add3 값이 null값 종료");

                                        return;

                                    }
                                });
                            }







                            Log.d(LOG_TAG,"str_id 값 존재 계속하여 실행 ");


                            String requestParam = String.format("member_id=%s&member_password=%s&member_name=%s&member_nickname=%s&member_tel=%s&member_email=%s&member_address.member_address_id=%s&member_address.address_postcode=%s&member_address.address_general=%s&member_address.address_detail=%s",
                                    id_data, pwd_data,name_data, nickname_data, tel_data,email_data,add_id_data,add1_data,add2_data,add3_data);


                            // 출력스트림을 사용하여 POST 방식의 파라메터 전달
                            myConnection.setDoOutput(true);

                            myConnection.getOutputStream().write(requestParam.getBytes());

                            Log.d(LOG_TAG,"멤버 테이블에 쿼리 스트링 보냄 ");



                            if (myConnection.getResponseCode() == 200) {
                                BufferedReader in =
                                        new BufferedReader(
                                                new InputStreamReader(
                                                        myConnection.getInputStream()
                                                )
                                        );

                                StringBuffer buffer = new StringBuffer();
                                String temp = null;
                                while ((temp = in.readLine()) != null) {
                                    buffer.append(temp);


                                }

                                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                                loginmember = gson.fromJson(buffer.toString(), Member.class);
/*

                                    StringBuffer data = new StringBuffer();
                                    data.append("id:" + member.getId());
                                    data.append("password:" + member.getPwd());
                                    data.append("name:" + member.getName() +"\n");

                                    str_output.append(data.toString());
*/



                            } else {
                                //Error


                                Log.d(LOG_TAG, myConnection.getResponseCode()+"");
                                return;
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"멤버 정보 수정되었습니다.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MyPageActivity.class);
                                    intent.putExtra("loginmember", loginmember);
                                    startActivity(intent);
                                    finish();

                                }
                            });



                        } catch (Exception e) {
                            Log.d(LOG_TAG, e.getMessage());

                        }
                    }
                });





            }
        });
        Log.d(LOG_TAG,loginmember.getMember_address().getAddress_postcode());





        str_mypage_update_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwdformat = "^[a-zA-Z0-9]{4,12}$";
                String pwd_data = str_mypage_update_pwd.getText().toString();
                String pwd_confirm_data = str_mypage_update_pwdconfirm.getText().toString();
                Pattern pattern = Pattern.compile(pwdformat);
                Matcher matcher = pattern.matcher(pwd_data);

                if(!matcher.matches()){
                    str_update_pwd_check.setText("패스워드는 4~12자의 영문 대소문자와 숫자로만 입력");
                    str_update_pwd_check.setTextColor(Color.RED);
                }else{
                    str_update_pwd_check.setText("");
                    str_update_pwd_check.setTextColor(Color.BLACK);
                }

                if(pwd_data.equals(pwd_confirm_data)){
                    str_update_pwdconfim_check.setText("비밀번호가 일치합니다.");
                    str_update_pwdconfim_check.setTextColor(Color.BLUE);
                }else {
                    str_update_pwdconfim_check.setText("비밀번호가 일치하지 않습니다 다시 확인해 주세요.");
                    str_update_pwdconfim_check.setTextColor(Color.RED);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 비밀번호 확인 및 유효성 검사 이벤트 처리
        str_mypage_update_pwdconfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = str_mypage_update_pwd.getText().toString();
                String confirm = str_mypage_update_pwdconfirm.getText().toString();

                if(password.equals(confirm)){
                    str_update_pwdconfim_check.setText("비밀번호가 일치합니다.");
                    str_update_pwdconfim_check.setTextColor(Color.BLUE);
                }else {
                    str_update_pwdconfim_check.setText("비밀번호가 일치하지 않습니다 다시 확인해 주세요.");
                    str_update_pwdconfim_check.setTextColor(Color.RED);
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });









        str_mypage_update_nickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, final boolean hasFocus) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            if (hasFocus == false) {

                                URL endPoint = new URL(SERVER_ADDRESS+"/member/checkNickname.m");
                                final HttpURLConnection myConnection =
                                        (HttpURLConnection) endPoint.openConnection();

                                myConnection.setRequestMethod("POST");

                                String nickname_data = str_mypage_update_nickname.getText().toString();
                                String requestParam = String.format("member_nickname=%s",nickname_data);
                                myConnection.setDoOutput(true);
                                myConnection.getOutputStream().write(requestParam.getBytes());




                                if (myConnection.getResponseCode() == 200) {
                                    BufferedReader in =
                                            new BufferedReader(
                                                    new InputStreamReader(
                                                            myConnection.getInputStream()
                                                    )
                                            );

                                    StringBuffer buffer = new StringBuffer();
                                    String temp = null;
                                    while ((temp = in.readLine()) != null)
                                        buffer.append(temp);
                                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                                    //   Member member = gson.fromJson(buffer.toString(), Member.class);
                                    nickname_count = gson.fromJson(buffer.toString(), Count.class);


                                    Log.d(LOG_TAG,"count.getCount():"+nickname_count.getCount());

                                    if(nickname_count.getCount() == 1){
                                        str_update_nickcheck.setText("이미 존재하는 닉네임 입니다.");
                                        str_update_nickcheck.setTextColor(Color.RED);
                                    }else if(nickname_count.getCount() == 0 ){
                                        str_update_nickcheck.setText("사용 가능한 닉네임 입니다.");
                                        str_update_nickcheck.setTextColor(Color.BLUE);
                                    }

                                } else {
                                    //Error
                                    Log.d(LOG_TAG, "에러 코드 :");
                                    return;
                                }
                            }
                        } catch (Exception e) {
                            Log.d(LOG_TAG, e.getMessage());

                        }

                    }
                });


            }
        });


        // 다음 주소 API 상세주소 버튼 처리
        btn_add_search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.tje.food.MyPageUpdateActivity.this, DaumPostCodeActivity.class);
                startActivityForResult(i, 3000);

            }
        });


        btn_update_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"취소 하셨습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MyPageActivity.class);
                intent.putExtra("loginmember",loginmember);
                startActivity(intent);
            }
        });




            }









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_update);
        setRefs();
        setEvents();
    }



    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, @Nullable Intent data) {
        EditText str_add1 = (EditText) findViewById(R.id.str_add_postcode);
        EditText str_add2 = (EditText) findViewById(R.id.str_add_general);
        EditText str_add3 = (EditText) findViewById(R.id.str_add_detail);

        // 다음 주소 api 를 intent로 값을 받기 위한 처리 부분
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 3000:
                    str_add1.setText(data.getStringExtra("add1"));
                    str_add2.setText(data.getStringExtra("add2"));
                    str_add3.setText(data.getStringExtra("add3"));
                    break;
            }
        }


    }
}
