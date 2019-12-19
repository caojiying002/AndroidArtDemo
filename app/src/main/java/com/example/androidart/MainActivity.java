package com.example.androidart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidart.binder.BookManagerActivity;
import com.example.androidart.manualbinder.MBookManagerActivity;
import com.example.androidart.messenger.MessengerActivity;
import com.example.androidart.singletask.STMainActivity;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), STMainActivity.class));
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), BookManagerActivity.class));
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MBookManagerActivity.class));
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MessengerActivity.class));
            }
        });
    }
}
