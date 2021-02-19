package com.example.program2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize Main Layout
        final LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        //Initialize Toolbar
        final LinearLayout toolbarLayout = new LinearLayout(this);
        toolbarLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Initialize Webpage
        final WebView webView = new WebView(MainActivity.this);

        //Initialize history
        final MyLinkedList browserHistory = new MyLinkedList();

        //URL (before buttons so they have access to chosenURL)
        LinearLayout.LayoutParams urlParams = new LinearLayout.LayoutParams(625, 100);
        final AppCompatEditText chosenURL = new AppCompatEditText(this);
        chosenURL.setLayoutParams(urlParams);

        //Back button
        WebBrowserButton backButton = new WebBrowserButton(this);
        backButton.setText("<");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUrl;
                if (chosenURL.getText().toString().startsWith("https://")){
                    newUrl = browserHistory.backward(browserHistory.getNode(chosenURL.getText().toString()));
                    if (newUrl == null) { //Make sure clicking on backward at end of list doesn't break app
                        return;
                    }
                    webView.loadUrl(newUrl);
                }
                else{
                    newUrl = browserHistory.backward(browserHistory.getNode("https://" + chosenURL.getText().toString()));
                    if (newUrl == null) {
                        return;
                    }
                    webView.loadUrl(newUrl);
                }
                chosenURL.setText(newUrl); //updates URL text
            }
        });

        //Forward button
        WebBrowserButton forwardButton = new WebBrowserButton(this);
        forwardButton.setText(">");
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUrl;
                if (chosenURL.getText().toString().startsWith("https://")){
                    newUrl = browserHistory.forward(browserHistory.getNode(chosenURL.getText().toString()));
                    if (newUrl == null){ //Make sure clicking on forward at end of list doesn't break app
                        return;
                    }
                    webView.loadUrl(newUrl);
                }
                else{
                    newUrl = browserHistory.forward(browserHistory.getNode("https://" + chosenURL.getText().toString()));
                    if (newUrl == null){
                        return;
                    }
                    webView.loadUrl(newUrl);
                }
                chosenURL.setText(newUrl); //updates URL text
            }
        });

        //Go Button
        WebBrowserButton goButton = new WebBrowserButton(this);
        goButton.setText("Go");
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chosenURL.getText().toString().startsWith("https://")){
                    webView.loadUrl(chosenURL.getText().toString());
                    browserHistory.insert(chosenURL.getText().toString());
                } else {
                    webView.loadUrl("https://" + chosenURL.getText().toString());
                    browserHistory.insert("https://" + chosenURL.getText().toString());
                }
            }
        });

        //Toolbar Setup
        toolbarLayout.addView(backButton);
        toolbarLayout.addView(forwardButton);
        toolbarLayout.addView(chosenURL);
        toolbarLayout.addView(goButton);

        //Webpage Display
        webView.setWebViewClient(new WebViewClient());

        //Main Layout Setup
        mainLayout.addView(toolbarLayout);
        mainLayout.addView(webView);
        setContentView(mainLayout);

    }
}