package com.example.program4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createLayout();
    }

    private void createLayout(){
        LinearLayout specifyLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        //maxDepth/Length
        final AppCompatTextView maxDepDesc = new AppCompatTextView(this);
        maxDepDesc.setText("Enter a max depth:");
        final AppCompatEditText maxDepthInput = new AppCompatEditText(this);
        maxDepthInput.setLayoutParams(layoutParams);
        specifyLayout.addView(maxDepDesc);
        specifyLayout.addView(maxDepthInput);

        //minDepth/Length
        final AppCompatTextView minDepDesc = new AppCompatTextView(this);
        minDepDesc.setText("Enter a min depth:");
        final AppCompatEditText minDepthInput = new AppCompatEditText(this);
        minDepthInput.setLayoutParams(layoutParams);
        specifyLayout.addView(minDepDesc);
        specifyLayout.addView(minDepthInput);

        //goButton
        final AppCompatButton goActivity = new AppCompatButton(this);
        goActivity.setText("Create Tree");
        goActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TreeActivity.class);
                String maxDepth = maxDepthInput.getText().toString();
                String minDepth = minDepthInput.getText().toString();
                intent.putExtra("maxDepth", maxDepth);
                intent.putExtra("minDepth", minDepth);
                startActivity(intent);
            }
        });
        specifyLayout.addView(goActivity);
        goActivity.setLayoutParams(layoutParams);

        specifyLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(specifyLayout);
    }

}