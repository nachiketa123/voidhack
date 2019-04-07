package com.example.androidcontinuity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomePage extends AppCompatActivity {

    ImageButton buttonTextEditor,buttonMessage,buttonCall,buttonGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        buttonTextEditor = findViewById(R.id.textEditorButton);
        buttonTextEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,TextEditorActivity.class);
                startActivity(intent);
            }
        });
        buttonMessage = findViewById(R.id.messaginButton);
        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,MessageActivity.class);
                startActivity(intent);
            }
        });
        buttonCall = findViewById(R.id.phoneCallButton);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonGallery = findViewById(R.id.galleryButton);
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
