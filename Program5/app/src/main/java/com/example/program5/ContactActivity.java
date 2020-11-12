package com.example.program5;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.program5.models.Contact;
import com.example.program5.presenters.ContactPresenter;

public class ContactActivity extends BaseActivity implements ContactPresenter.MVPView {
    ContactPresenter presenter;
    LinearLayout contactPageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        presenter = new ContactPresenter(this);


        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 1);
        presenter.loadContact(id);

        contactPageLayout = new LinearLayout(this);
        contactPageLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(48, 24, 48, 24);
        //add margins for pt 2
        contactPageLayout.setLayoutParams(params);
        setContentView(contactPageLayout);
    }

    @Override
    public void renderContact(Contact contact) {
        runOnUiThread(()-> {
            AppCompatTextView nameTextView = new AppCompatTextView(this);
            nameTextView.setText("Name: " + contact.contactName);
            nameTextView.setTextSize(30);

            AppCompatTextView phoneTextView = new AppCompatTextView(this);
            phoneTextView.setText("Number: " + contact.phoneNumber);
            phoneTextView.setTextSize(30);

            AppCompatTextView emailTextView = new AppCompatTextView(this);
            emailTextView.setText("Email: " + contact.emailAddress);
            emailTextView.setTextSize(30);

            contactPageLayout.addView(nameTextView);
            contactPageLayout.addView(phoneTextView);
            contactPageLayout.addView(emailTextView);
        });
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //part 2?
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == id && resultCode == Activity.RESULT_OK){
//            Contact newContact = (Contact)data.getSerializableExtra("result");
//        }
//    }


//    @Override
//    public void goBackToContactsPage(Contact contact) { //part 2?
//
//    }
}
