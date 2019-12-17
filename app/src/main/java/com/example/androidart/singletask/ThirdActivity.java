package com.example.androidart.singletask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidart.BaseAppCompatActivity;
import com.example.androidart.R;

public class ThirdActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), STMainActivity.class));
            }
        });
    }
}
