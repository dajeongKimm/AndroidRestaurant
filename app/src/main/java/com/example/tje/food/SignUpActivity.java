package com.example.tje.food;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.food.Model.Count;
import com.example.tje.food.Model.Member;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class SignUpActivity extends AppCompatActivity {


        private static final String LOG_TAG = "signuptestapp";
        private  static final String SERVER_ADDRESS = Const.SIGNUPACTIVITY_IP;

        Button btn_insert;
        Button btn_reset;
        Button btn_add_search;


        EditText str_id;
        EditText str_pwd;
        EditText str_pwd_confirm;
        EditText str_name;
        EditText str_nickname;
        EditText str_email;
        EditText str_tel;
        EditText str_add1;
        EditText str_add2;
        EditText str_add3;

        EditText str_birthday;
        RadioGroup radio_group;
        RadioButton radio_man;
        RadioButton radio_woman;

        // 유효성 검사를 위한 텍스트 필드
        TextView str_id_check;
        TextView str_pwd_check;
        TextView str_pwdconfirm_check;
        TextView str_nickname_check;
        TextView str_tel_check;
        TextView str_email_check;


        ArrayList<RadioButton> radioButtons;

        // 서버에서 받아온 객체 정보를 받기 위한 멤버

        Member loginmember;
        // 중복체크시 카운트
        Count count;
        Count nickname_count;

        public void initRefs() {
            // 버튼
            btn_insert = (Button)findViewById(R.id.btn_insert);
            btn_reset = (Button)findViewById(R.id.btn_reset);
            btn_add_search = (Button)findViewById(R.id.btn_add_search);


            //EditText
            str_id = findViewById(R.id.str_id);
            str_pwd = findViewById(R.id.str_pwd);
            str_pwd_confirm = findViewById(R.id.str_pwd_confirm);
            str_name = findViewById(R.id.str_name);
            str_nickname = findViewById(R.id.str_nickname);
            str_email = findViewById(R.id.str_email);
            str_tel = findViewById(R.id.str_tel);
            str_add1 = findViewById(R.id.str_add1);
            str_add2 = findViewById(R.id.str_add2);
            str_add3 = findViewById(R.id.str_add3);
            str_birthday = findViewById(R.id.str_birthday);


            // 성별
            radioButtons = new ArrayList<>();
            radioButtons.add((RadioButton) findViewById(R.id.radio_man));
            radioButtons.add((RadioButton) findViewById(R.id.radio_woman));
            radio_group = (RadioGroup) findViewById(R.id.radio_group);

            // 유효성 검사를 위한 TextView
            str_id_check =findViewById(R.id.str_id_check);
            str_pwdconfirm_check = findViewById(R.id.str_pwdconfirm_check);
            str_pwd_check = findViewById(R.id.str_pwd_check);
            str_nickname_check = findViewById(R.id.str_nickname_check);
            str_tel_check = findViewById(R.id.str_tel_check);
            str_email_check = findViewById(R.id.str_email_check);

            loginmember = null;



        }

        public void setEvents() {

//        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//        loginmember = (Member)intent.getSerializableExtra("loginmember");

            btn_insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AsyncTask.execute(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                URL endPoint = new URL(SERVER_ADDRESS+"/member/insert.m");
                                final HttpURLConnection myConnection =
                                        (HttpURLConnection) endPoint.openConnection();
                                // 메소드 방식 변경
                                myConnection.setRequestMethod("POST");
                                String id_data = str_id.getText().toString();
                                String pwd_data = str_pwd.getText().toString();
                                String pwd_confirm_data = str_pwd_confirm.getText().toString();
                                String name_data = str_name.getText().toString();
                                final String nickname_data = str_nickname.getText().toString();
                                String tel_data = str_tel.getText().toString();
                                String email_data = str_email.getText().toString();
                                String birthday_data = str_birthday.getText().toString();
                               // String photo_data = " ";
                                String add1_data = str_add1.getText().toString();
                                String add2_data = str_add2.getText().toString();
                                String add3_data = str_add3.getText().toString();

                                int genderId = radio_group.getCheckedRadioButtonId();
                                RadioButton button = findViewById(genderId);
                                String gender_data = button.getText().toString();






                                if(id_data == null || id_data.isEmpty() || count.getCount() == 1 ){
                                    str_id.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            str_id.setFocusableInTouchMode(true);
                                            str_id.requestFocus();

                                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                                            imm.showSoftInput(str_id,0);
                                            Log.d(LOG_TAG,"str_id 값이 null값 종료");

                                            return ;


                                        }
                                    });

                                }else if(pwd_data == null  || pwd_data.isEmpty()){
                                    str_pwd.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            str_pwd.setFocusableInTouchMode(true);
                                            str_pwd.requestFocus();

                                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                                            imm.showSoftInput(str_pwd,0);
                                            Log.d(LOG_TAG,"str_pwd 값이 null값 종료");

                                            return ;

                                        }
                                    });
                                }else if(!pwd_data.equals(pwd_confirm_data)){
                                    str_pwd.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            str_pwd_confirm.setFocusableInTouchMode(true);
                                            str_pwd_confirm.requestFocus();

                                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                                            imm.showSoftInput(str_pwd_confirm,0);
                                            Log.d(LOG_TAG,"패스워드가 일치하지 않음  종료");

                                            return ;

                                        }
                                    });
                                }else if( pwd_confirm_data == null|| pwd_confirm_data.isEmpty()) {
                                    str_pwd_confirm.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            str_pwd_confirm.setFocusableInTouchMode(true);
                                            str_pwd_confirm.requestFocus();

                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                            imm.showSoftInput(str_pwd_confirm, 0);
                                            Log.d(LOG_TAG, "str_pwd_confirm 값이 null값 종료");

                                            return;

                                        }
                                    });
                                }else if( name_data == null|| name_data.isEmpty()) {
                                    str_name.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            str_name.setFocusableInTouchMode(true);
                                            str_name.requestFocus();

                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                            imm.showSoftInput(str_name, 0);
                                            Log.d(LOG_TAG, "str_name 값이 null값 종료");

                                            return;

                                        }
                                    });
                                }else if( nickname_data == null|| nickname_data.isEmpty() || nickname_count.getCount() == 1) {
                                    str_nickname.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            str_nickname.setFocusableInTouchMode(true);
                                            str_nickname.requestFocus();

                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                            imm.showSoftInput(str_nickname, 0);
                                            Log.d(LOG_TAG, "str_nickname 값이 null값 종료");

                                            return;

                                        }
                                    });
                                }else if( tel_data == null|| tel_data.isEmpty()) {
                                    str_tel.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            str_tel.setFocusableInTouchMode(true);
                                            str_tel.requestFocus();

                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                            imm.showSoftInput(str_tel, 0);
                                            Log.d(LOG_TAG, "str_tel 값이 null값 종료");

                                            return;

                                        }
                                    });
                                }else if( email_data == null|| email_data.isEmpty()) {
                                    str_email.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            str_email.setFocusableInTouchMode(true);
                                            str_email.requestFocus();

                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                            imm.showSoftInput(str_email, 0);
                                            Log.d(LOG_TAG, "str_email 값이 null값 종료");

                                            return;

                                        }
                                    });
                                }else if( birthday_data == null|| birthday_data.isEmpty()) {
                                    str_birthday.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            str_birthday.setFocusableInTouchMode(true);
                                            str_birthday.requestFocus();

                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                            imm.showSoftInput(str_birthday, 0);
                                            Log.d(LOG_TAG, "str_birthday 값이 null값 종료");

                                            return;

                                        }
                                    });
                                }else if( add1_data == null|| add2_data == null|| add3_data == null|| add1_data.isEmpty() || add2_data.isEmpty() || add3_data.isEmpty() ) {
                                    str_add3.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            str_add3.setFocusableInTouchMode(true);
                                            str_add3.requestFocus();

                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                            imm.showSoftInput(str_add3, 0);
                                            Log.d(LOG_TAG, "str_add3 값이 null값 종료");

                                            return;

                                        }
                                    });
                                }







                                Log.d(LOG_TAG,"str_id 값 존재 계속하여 실행 ");


                                String requestParam = String.format("member_id=%s&member_password=%s&member_name=%s&member_nickname=%s&member_tel=%s&member_email=%s&member_birthday=%s&member_gender=%s&member_photo=null&member_type%d&member_address.address_postcode=%s&member_address.address_general=%s&member_address.address_detail=%s",
                                        id_data, pwd_data, name_data, nickname_data, tel_data, email_data, birthday_data, gender_data, 0,add1_data,add2_data,add3_data);


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
                                    Member member = gson.fromJson(buffer.toString(), Member.class);
/*

                                    StringBuffer data = new StringBuffer();
                                    data.append("id:" + member.getId());
                                    data.append("password:" + member.getPwd());
                                    data.append("name:" + member.getName() +"\n");

                                    str_output.append(data.toString());
*/



                                } else {
                                    //Error


                                    Log.d(LOG_TAG, "else 종료");
                                    return;
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"회원가입을 축하드립니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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




            // 다음 주소 API 상세주소 버튼 처리
            btn_add_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(com.example.tje.food.SignUpActivity.this, DaumPostCodeActivity.class);
                    startActivityForResult(i, 3000);

                }
            });

            // ID 중복 검사 및 유효성 검사 처리
            str_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, final boolean hasFocus) {

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String idformat = "^[a-zA-Z0-9]{4,12}$";

                                if (hasFocus == false) {

                                    URL endPoint = new URL(SERVER_ADDRESS+"/member/checkId.m");
                                    final HttpURLConnection myConnection =
                                            (HttpURLConnection) endPoint.openConnection();

                                    myConnection.setRequestMethod("POST");

                                    String id_data = str_id.getText().toString();
                                    String requestParam = String.format("member_id=%s",id_data);
                                    myConnection.setDoOutput(true);
                                    myConnection.getOutputStream().write(requestParam.getBytes());


                                    Pattern pattern = Pattern.compile(idformat);
                                    Matcher matcher = pattern.matcher(id_data);
                                    if (!matcher.matches()) {
                                        Log.d(LOG_TAG, "ID 유효성 검사에 맞지 않습니다."); // 저는 로그를 찍어봤는데요. 포커스 이동될때마다 로그가 잘 찍히고 있습니다

                                    }
                                   // int responsecode = myConnection.getResponseCode();

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

                                        count = gson.fromJson(buffer.toString(), Count.class);


                                        Log.d(LOG_TAG,"count.getCount():"+count.getCount());

                                        if(count.getCount() == 1){
                                            str_id_check.setText("이미 존재하는 ID 입니다.");
                                            str_id_check.setTextColor(Color.RED);
                                        }else if(count.getCount() == 0 && matcher.matches()){
                                            str_id_check.setText("사용 가능한 ID 입니다.");
                                            str_id_check.setTextColor(Color.BLUE);

                                        }else if(!matcher.matches()){
                                            str_id_check.setText("아이디는 4~12자의 영문 대소문자와 숫자로만 입력");
                                            str_id_check.setTextColor(Color.BLACK);
                                        }




                                    } else {
                                        //Error
                                        Log.d(LOG_TAG, "접속실패 ");
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



            str_pwd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String pwdformat = "^[a-zA-Z0-9]{4,12}$";
                    String pwd_data = str_pwd.getText().toString();
                    String pwd_confirm_data = str_pwd_confirm.getText().toString();
                    Pattern pattern = Pattern.compile(pwdformat);
                    Matcher matcher = pattern.matcher(pwd_data);

                    if(!matcher.matches()){
                        str_pwd_check.setText("패스워드는 4~12자의 영문 대소문자와 숫자로만 입력");
                        str_pwd_check.setTextColor(Color.RED);
                    }else{
                        str_pwd_check.setText("");
                        str_pwd_check.setTextColor(Color.BLACK);
                    }

                    if(pwd_data.equals(pwd_confirm_data) && pwd_data.length() > 0){
                        str_pwdconfirm_check.setText("비밀번호가 일치합니다.");
                        str_pwdconfirm_check.setTextColor(Color.BLUE);
                    }else {
                        str_pwdconfirm_check.setText("비밀번호가 일치하지 않습니다 다시 확인해 주세요.");
                        str_pwdconfirm_check.setTextColor(Color.RED);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            // 비밀번호 확인 및 유효성 검사 이벤트 처리
            str_pwd_confirm.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String password = str_pwd.getText().toString();
                    String confirm = str_pwd_confirm.getText().toString();

                    if(password.equals(confirm) && password.length() > 0 ){
                        str_pwdconfirm_check.setText("비밀번호가 일치합니다.");
                        str_pwdconfirm_check.setTextColor(Color.BLUE);
                    }else if (!password.equals(confirm) || password.length() == 0){
                        str_pwdconfirm_check.setText("비밀번호가 일치하지 않습니다 다시 확인해 주세요.");
                        str_pwdconfirm_check.setTextColor(Color.RED);
                    }



                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            // ID 중복 검사 및 유효성 검사 처리
            str_nickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

                                    String nickname_data = str_nickname.getText().toString();
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
                                            str_nickname_check.setText("이미 존재하는 닉네임 입니다.");
                                            str_nickname_check.setTextColor(Color.RED);
                                        }else if(nickname_count.getCount() == 0 ){
                                            str_nickname_check.setText("사용 가능한 닉네임 입니다.");
                                            str_nickname_check.setTextColor(Color.BLUE);
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


            str_tel.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String telformat = "^[0-9]*$";
                    String tel_data = str_tel.getText().toString();
                    Pattern pattern = Pattern.compile(telformat);
                    Matcher matcher = pattern.matcher(tel_data);

                    if(!matcher.matches()){
                        str_tel_check.setText("숫자만 입력해 주세요.");
                        str_tel_check.setTextColor(Color.RED);
                    }else{
                        str_tel_check.setText("");
                        str_tel_check.setTextColor(Color.BLACK);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            str_email.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String email_data = str_email.getText().toString();
/*

                String emailformat = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+$";
                String email_data = str_email.getText().toString();
                Pattern pattern = Pattern.compile(emailformat);
                Matcher matcher = pattern.matcher(email_data);
*/

                    if(!Patterns.EMAIL_ADDRESS.matcher(email_data).matches())
                    {
                        str_email_check.setText("올바른 이메일 형식으로 입력해주세요.");
                        str_email_check.setTextColor(Color.RED);
                    }else{
                        str_email_check.setText("");
                        str_email_check.setTextColor(Color.BLACK);
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            btn_reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });



        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);

            initRefs();

            setEvents();

        }

        @Override
        protected void onActivityResult(
                int requestCode, int resultCode, @Nullable Intent data) {
            EditText str_add1 = (EditText) findViewById(R.id.str_add1);
            EditText str_add2 = (EditText) findViewById(R.id.str_add2);
            EditText str_add3 = (EditText) findViewById(R.id.str_add3);

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
