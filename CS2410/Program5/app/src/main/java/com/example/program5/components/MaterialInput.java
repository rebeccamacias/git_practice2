package com.example.program5.components;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MaterialInput extends TextInputLayout {
    private TextInputEditText input;

    public MaterialInput(Context context, String hint) {
        super(context);
        LayoutParams params  = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(48, 24, 48, 24);
        setLayoutParams(params);
        setHint(hint);
        input = new TextInputEditText(getContext());
        input.setGravity(Gravity.START);
        addView(input);
    }

    public String getText() {
        return input.getText().toString();
    }

    public void setInput(int type) {
        input.setInputType(type);
    }

    public void setText(String text) {
        input.setText(text);
    }
}
