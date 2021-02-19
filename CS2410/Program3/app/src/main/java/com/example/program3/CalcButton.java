package com.example.program3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;

public class CalcButton extends AppCompatButton {
    public CalcButton(Context context, CalcButtonData data, final View.OnClickListener onClick) {
        super(context);
        setText(data.getButtonText());
        setOnClickListener((view) -> {
            onClick.onClick(view);
        });

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(data.getRow(), 1, 1);
        params.columnSpec = GridLayout.spec(data.getColumn(), data.getSize(), 1);
        params.setMargins(1,1,1,1);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        drawable.setStroke(10, ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));

        setTextSize(60);
        setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorHighlight, null));
        setBackground(drawable);
        setLayoutParams(params);
    }
}
