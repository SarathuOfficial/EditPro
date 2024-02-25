package com.sarath.photoeditingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Firstpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        try {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Button button3 = findViewById(R.id.buttonSign);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(Firstpage.this, Secondpage.class);
                    startActivity(intent1);
                }
            });
            TextView textView3 = findViewById(R.id.textView3);
            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Firstpage.this, Thirdpage.class);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}