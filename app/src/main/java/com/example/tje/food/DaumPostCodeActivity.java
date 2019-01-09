package com.example.tje.food;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DaumPostCodeActivity extends AppCompatActivity {

    private  static final String SERVER_ADDRESS = "http://192.168.10.8:8080/Final";

    private WebView daum_webView;
    private TextView add1;
    private TextView add2;


    private Handler handler;



    private Button btn_add_sumbit;
    private Button btn_add_cancle;

    public void setEvents(){

        btn_add_cancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        });

        btn_add_sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                intent.putExtra("add1",add1.getText().toString());
                intent.putExtra("add2",add2.getText().toString());
              //  intent.putExtra("add3",add3.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_daum_post_code);
        add1 = (TextView) findViewById(R.id.add1);
        add2 = (TextView) findViewById(R.id.add2);

        btn_add_sumbit = (Button)findViewById(R.id.btn_add_submit);
        btn_add_cancle = (Button)findViewById(R.id.btn_add_cancle);
        // WebView 초기화
        init_webView();
        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();
        setEvents();
    }



    public void init_webView() {
        // WebView 설정
        daum_webView = (WebView) findViewById(R.id.daum_webview);
        // JavaScript 허용
        daum_webView.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        daum_webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        daum_webView.addJavascriptInterface(new AndroidBridge(), "SignUpTestApp");

        // web client 를 chrome 으로 설정
        daum_webView.setWebChromeClient(new WebChromeClient());
        // webview url load. php 파일 주소
        daum_webView.loadUrl(SERVER_ADDRESS+"/daum_address.jsp");
    }


    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {


                    if(arg3 == null || arg3.length() ==0) {
                        String fulladd = arg2 ;
                        add2.setText(String.format("%s", fulladd));
                    }else{
                        String fulladd = arg2 + "(" + arg3 + ")";
                        add2.setText(String.format("%s", fulladd));

                    }

                    add1.setText(String.format("%s", arg1));

                  //  add3.setText(String.format("%s", arg3));
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });

        }
    }
}

