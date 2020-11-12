package com.example.program5.components;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.program5.models.Contact;

public class ContactsView extends LinearLayout {
    private Contact contact;

    public interface OnContactSelectListener {
        public void call();
    }

    public ContactsView(Context context, Contact contact, OnContactSelectListener onContact) {
        super(context);
        AppCompatTextView contactsView = new AppCompatTextView(context);
        contactsView.setText(contact.contactName);
        contactsView.setTextSize(30);
        contactsView.setOnClickListener((view -> {
            onContact.call();
        }));
        addView(contactsView);
    }

    public Contact getContact() {
        return contact;
    }
}
