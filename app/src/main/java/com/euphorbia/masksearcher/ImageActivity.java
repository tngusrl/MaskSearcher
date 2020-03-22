package com.euphorbia.masksearcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;

public class ImageActivity extends AppCompatActivity {

    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_image);

        photoView = findViewById(R.id.photo);
        photoView.setImageResource(R.drawable.guide);
    }
}
