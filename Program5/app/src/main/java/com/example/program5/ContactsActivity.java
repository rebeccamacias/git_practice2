package com.example.program5;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.program5.components.ContactsView;
import com.example.program5.models.Contact;
import com.example.program5.presenters.ContactsPresenter;

import java.util.ArrayList;

public class ContactsActivity extends BaseActivity implements ContactsPresenter.MVPView { //MAIN ACTIVITY SCREEN
    private final int CREATE_NEW_CONTACT = 1;
    private final int CONTACT_PAGE = 2;
    LinearLayout mainLayout;
    LinearLayout contactsLayout;
    ContactsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContactsPresenter(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(48, 24, 48, 24);


        mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        contactsLayout = new LinearLayout(this);
        contactsLayout.setOrientation(LinearLayout.VERTICAL);

        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(contactsLayout);

        AppCompatButton createNewContactButton = new AppCompatButton(this);
        createNewContactButton.setText("Create New Contact");
        createNewContactButton.setOnClickListener(view -> {
            presenter.handleNewContactClick();
        });
        mainLayout.setLayoutParams(params);
        mainLayout.addView(createNewContactButton);
        mainLayout.addView(scrollView);

        setContentView(mainLayout);
    }

    @Override
    public void renderContact(Contact contact) {
        runOnUiThread(() -> {
            ContactsView contactsView = new ContactsView(
                    this,
                    contact,
                    () -> {
                        presenter.handleContactSelected(contact);
                    }
            );
            contactsLayout.addView(contactsView);
        });
    }

    @Override
    public void goToNewContactPage() {
        Intent intent = new Intent(this, NewContactActivity.class);
        startActivityForResult(intent, CREATE_NEW_CONTACT);
    }

    @Override
    public void goToContactPage(Contact contact) {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("id", contact.id);
        startActivityForResult(intent, CONTACT_PAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_NEW_CONTACT && resultCode == Activity.RESULT_OK){
            Contact newContact = (Contact)data.getSerializableExtra("result");
            presenter.onNewContactCreated(newContact);
        }
    }
}