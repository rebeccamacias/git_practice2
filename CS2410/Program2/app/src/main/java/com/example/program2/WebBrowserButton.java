package com.example.program2;

import androidx.appcompat.widget.AppCompatButton;
import android.content.Context;
import android.widget.LinearLayout;

public class WebBrowserButton extends AppCompatButton {

    public WebBrowserButton(Context context) {
        super(context);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(150,100);
        setLayoutParams(buttonParams);
    }
}
