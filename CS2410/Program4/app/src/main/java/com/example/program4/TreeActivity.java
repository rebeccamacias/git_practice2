package com.example.program4;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TreeActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String maxDepth = intent.getStringExtra("maxDepth");
        String minDepth = intent.getStringExtra("minDepth");

        int maxDepthInt = Integer.parseInt(maxDepth);
        int minDepthInt = Integer.parseInt(minDepth);

        int depth = (int)(Math.ceil(Math.random() * (maxDepthInt - minDepthInt + 1) + minDepthInt));
        DrawView drawView = new DrawView(this, depth);

        setContentView(drawView);
    }
}
