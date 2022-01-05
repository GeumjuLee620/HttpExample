package com.example.http;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler(); //백그라운드에서 UI 스레드로 전달해주는 역할

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText linkEt = findViewById(R.id.link_et);
        Button searchBtn = findViewById(R.id.search_btn);
        TextView htmlTV = findViewById(R.id.html_tv); //레이아웃에 있는 3가지를 생성자 만들어줌

        searchBtn.setOnClickListener(new View.OnClickListener() { //버튼 클릭리스너 버튼을 누르면 불러옴
            @Override
            public void onClick(View view) {
                String link= linkEt.getText().toString(); //요청을 보내기 위한 주소
                OkHttpClient client = new OkHttpClient(); //클라인언트 생성
                Request request = new Request.Builder() //request okhttp3를 사용, 빌드를 해줘야 모든 것들이 적용된 것이 빌드
                        .url(link)
                        .build();
                client.newCall(request).enqueue(new Callback() { //새로운 콜을 만든 것에 결과를 받아올 enqueue
                    // (new CallBack를 만들어냄(자동 오버라이드))
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) { //실패한 경우

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException { //성공한 경우
                       String html = response.body().string(); //결과값이 들어옴
                       Log.d("html", html); //까지가 백그라운드에서
                       handler.post(new Runnable() { //HTML이라는 결과값을 UI 스레드로 넘김
                           @Override
                           public void run() {
                               htmlTV.setText(html);
                           }  //UI를 변경시키는 행위
                       });
                    }
                });
            }
        });
    }
}