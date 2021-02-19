package com.example.program3;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

public class CalcDisplayView extends AppCompatTextView {
    public CalcDisplayView(Context context) {
        super(context);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(0, 1, 4);
        params.columnSpec = GridLayout.spec(0, 4, 1);
        setLayoutParams(params);
        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorDark, null));
        setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorHighlight, null));
        setTextSize(40);
        setGravity(Gravity.CENTER);
        setEnabled(false);
    }
}
