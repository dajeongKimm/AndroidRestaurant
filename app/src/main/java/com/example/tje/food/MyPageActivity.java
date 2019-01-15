package com.example.tje.food;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tje.food.Model.Member;
import com.example.tje.food.Model.Member_address;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MyPageActivity extends AppCompatActivity {

    private static final String SERVER_ADDRESS = Const.MYPAGEACTIVITY_IP;
    private static final String LOG_TAG = "signuptestapp";


    TextView str_mypage_nickname;
    TextView str_mypage_name;
    TextView str_mypage_tel;
    TextView str_mypage_email;
    TextView str_mypage_add;
    View dialogView;
    Member loginmember;

    LinearLayout goLog;



    Button btn_logout;
    Button btn_member_update;
    ImageButton btn_profile;

    public void setRefs() {
        str_mypage_nickname = findViewById(R.id.str_mypage_nickname);
        str_mypage_name = findViewById(R.id.str_mypage_name);
        str_mypage_tel = findViewById(R.id.str_mypage_tel);
        str_mypage_email = findViewById(R.id.str_mypage_email);
        str_mypage_add = findViewById(R.id.str_mypage_add);

        btn_logout = findViewById(R.id.btn_logout);
        btn_member_update = findViewById(R.id.btn_member_update);
        btn_profile = findViewById(R.id.btn_profile);

        goLog = (LinearLayout)findViewById(R.id.goLog);

    }

    public void setEvents() {
        Intent inetnet = getIntent();
        loginmember = (Member) inetnet.getSerializableExtra("loginmember");


        String fulladd = "[" + loginmember.getMember_address().getAddress_postcode() + "]" + loginmember.getMember_address().getAddress_general() + loginmember.getMember_address().getAddress_detail();


        str_mypage_nickname.setText(loginmember.getMember_nickname());
        str_mypage_name.setText(loginmember.getMember_name());
        str_mypage_tel.setText(loginmember.getMember_tel());
        str_mypage_email.setText(loginmember.getMember_email());
        str_mypage_add.setText(fulladd);



        btn_member_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // 다이얼로그의 화면을 구성할 View 객체 생성
                dialogView = (View) View.inflate(MyPageActivity.this, R.layout.mypage_pwdcheck_dialog, null);
                TextView str_mypage_check_id1 = (TextView) dialogView.findViewById(R.id.str_mypage_check_id1);
                str_mypage_check_id1.setText(loginmember.getMember_id());
                AlertDialog.Builder dlg = new AlertDialog.Builder(MyPageActivity.this);

                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        TextView str_mypage_check_id1 = (TextView) dialogView.findViewById(R.id.str_mypage_check_id1);
                        final EditText str_mypage_pwd1 = (EditText) dialogView.findViewById(R.id.str_mypage_pwd1);

                        str_mypage_check_id1.setText(loginmember.getMember_id());

                        String pwd = str_mypage_pwd1.getText().toString();

                        if (pwd.equals(loginmember.getMember_password())) {

                            Toast.makeText(getApplicationContext(), "확인 되셨습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MyPageUpdateActivity.class);

                            intent.putExtra("loginmember", loginmember);
                            startActivity(intent);

                            finish();
                        } else {


                            str_mypage_pwd1.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                                    str_mypage_pwd1.setFocusableInTouchMode(true);
                                    str_mypage_pwd1.requestFocus();

                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                                    imm.showSoftInput(str_mypage_pwd1, 0);
                                    Log.d(LOG_TAG, "str_pwd1 값이 null값 종료");

                                    return;

                                }
                            });



                        }


                    }
                });

                dlg.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "취소를 선택했습니다.", Toast.LENGTH_LONG).show();
                                return;
                            }
                        });

                dlg.show();


            }
        });



        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {


                        try {
                            URL endPoint = new URL(SERVER_ADDRESS + "/member/logout.m");
                            final HttpURLConnection myConnection =
                                    (HttpURLConnection) endPoint.openConnection();
                            // 메소드 방식 변경
                            myConnection.setRequestMethod("GET");


                            //myConnection.setRequestMethod("GET");

                            if (myConnection.getResponseCode() == 200) {
                                // Success

                                Log.d(LOG_TAG, "웹 연결 성공 로그아웃");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {



                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        loginmember = null;
                                        Toast.makeText(getApplicationContext(),"로그아웃 되셨습니다.",Toast.LENGTH_LONG).show();
                                        finish();

                                    }
                                });

                            } else {
                                // Error
                                Log.d(LOG_TAG,"웹 연결 실패 코드 :" + myConnection.getResponseCode());
                            }




                        }catch (Exception e) {

                            StringWriter sw = new StringWriter();
                            e.printStackTrace(new PrintWriter(sw));
                            String exceptionAsStrting = sw.toString();
                            Log.d(LOG_TAG,exceptionAsStrting);
                        }

                    }
                });




            }
        });

        //방문기록
        goLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VisitLog.class);
                intent.putExtra("loginmember", loginmember);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        setRefs();
        setEvents();

    }
}
