package com.euphorbia.masksearcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

public class GuideActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        imageView = findViewById(R.id.imageView);
        tv = findViewById(R.id.tv);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
                startActivity(intent);
            }
        });

        tv.setText(Html.fromHtml("이미지 클릭 시 확대하여 확인이 가능합니다! <br><br> <big><b>1. 지도에서 <font color='#00ff00'>녹색은 100개 이상</font>, <font color='#EADF15'>노랑색은 30개 ~ 100개</font>, <font color='#E95E20'>주황색은 2~30개</font>, <font color='#EE0000'>빨강색은 1개~매진</font>, <font color='#7F00FF'>보라색은 정보없음</font> 입니다!<br> </b></big> <br> <big><b>2. 1,6년생 월요일, 2,7년생 화요일, 3,8년생 수요일, 4,9년생 목요일, 5,0년생 금요일, 주중 구매하지 못한 사람에 한해 토요일 일요일 구매가능!</b></big> <br><br><big><b>" +
                "3. 1주일에 한번, 1인당 2매 , 한장당 1500원에 구매 가능! </b></big> <br><br><big><b>4. 본인이 검색하고자 하는 지역을 정확히 입력할 수록 정확도가 높아집니다. 예) 중동 x, 마포구 중동 ㅇ</b></big> <br><br><big><b>5. 공적 마스크 정보는 정부에서 제공하는 정보이며, 실제와는 다를 수 있습니다!</b></big>"));

    }
}
