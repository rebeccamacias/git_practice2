package com.example.program5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.program5.models.Contact;
import com.example.program5.presenters.NewContactPresenter;

public class NewContactActivity extends BaseActivity implements NewContactPresenter.MVPView {
    NewContactPresenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NewContactPresenter(this);
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(48, 24, 48, 24);

        //name
        AppCompatTextView nameTextView = new AppCompatTextView(this);
        nameTextView.setText("Name");
        AppCompatEditText nameEditText = new AppCompatEditText(this);

        //phone
        AppCompatTextView phoneTextView = new AppCompatTextView(this);
        phoneTextView.setText("Number");
        AppCompatEditText phoneEditText = new AppCompatEditText(this);

        //email
        AppCompatTextView emailTextView = new AppCompatTextView(this);
        emailTextView.setText("Email");
        AppCompatEditText emailEditText = new AppCompatEditText(this);

        //save button
        AppCompatButton button = new AppCompatButton(this);
        button.setText("Save");
        button.setOnClickListener(view -> {
            presenter.createContact(nameEditText.getText().toString(), phoneEditText.getText().toString(), emailEditText.getText().toString());
        });

        mainLayout.setLayoutParams(params);
        mainLayout.addView(nameTextView);
        mainLayout.addView(nameEditText);
        mainLayout.addView(phoneTextView);
        mainLayout.addView(phoneEditText);
        mainLayout.addView(emailTextView);
        mainLayout.addView(emailEditText);
        mainLayout.addView(button);

        setContentView(mainLayout);
    }

    @Override
    public void goBackToContactsPage(Contact newContact) {
        Intent intent = new Intent();
        intent.putExtra("result", newContact);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
