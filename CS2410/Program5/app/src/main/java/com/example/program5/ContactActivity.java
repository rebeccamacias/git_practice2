package com.example.program5;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.program5.components.ContactCard;
import com.example.program5.models.Contact;
import com.example.program5.presenters.ContactPresenter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ContactActivity extends BaseActivity implements ContactPresenter.MVPView {
    ContactPresenter presenter;
    LinearLayout contactPageLayout;

    private final int UPDATE_CONTACT = 2;

    private final int CALL_PERMISSION_REQUESTED = 3;
    private final int TEXT_PERMISSION_REQUESTED = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        presenter = new ContactPresenter(this, getIntent().getLongExtra("id", -1));
        ScrollView scrollView = new ScrollView(this);
        contactPageLayout = new LinearLayout(this);
        contactPageLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(contactPageLayout);
        setContentView(scrollView);
    }

    @Override
    public void renderContact(Contact contact) {
        runOnUiThread(()-> {
            ContactCard contactCard = new ContactCard(this, contact, true);

            contactCard.callButton.setOnClickListener(view -> {
                presenter.handleCallPressed(contact.phoneNumber);
            });
            if (contact.emailAddress.length() > 0){
                contactCard.emailButton.setOnClickListener(view -> {
                    presenter.handleEmailPressed(contact.emailAddress);
                });
            }

            contactCard.textButton.setOnClickListener(view -> {
                presenter.handleTextPressed(contact.phoneNumber);
            });

            contactCard.setFabOnClickListener((fab) -> {
                PopupMenu popupMenu = new PopupMenu(this, fab);
                popupMenu.getMenu().add("Edit");
                popupMenu.getMenu().add("Delete");
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getTitle().toString().equals("Edit")) { //edit
                        presenter.handleEditPressed();
                    } else { //delete
                        presenter.handleDeletePressed();
                    }
                    return true;
                });
                popupMenu.show();
            });
            contactPageLayout.addView(contactCard);
        });
    }

    @Override
    public void displayDeleteConfirmation() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Are you sure you want to delete this contact?")
                .setPositiveButton("Delete", (view, i) -> {
                    presenter.deleteContact();
                })
                .setNeutralButton("Cancel", (view, i) -> {
                    view.dismiss();
                })
                .show();
    }

    @Override
    public void goBackToContactsPage(Contact contact, boolean isDeleted, boolean isUpdated){
        Intent intent = new Intent();
        intent.putExtra("contact", contact);
        if (isDeleted) {
            setResult(ContactsActivity.CONTACT_DELETED, intent);
        } else if (isUpdated) {
            setResult(ContactsActivity.CONTACT_UPDATED, intent);
        }
        finish();
    }

    @Override
    public void goToEditPage(Contact contact){
        Intent intent = new Intent(this, CreateOrUpdateContactActivity.class);
        intent.putExtra("id", contact.id);
        startActivityForResult(intent, UPDATE_CONTACT);
    }

    @Override
    public void updateContactUI(Contact contact){
        ContactCard contactCard = contactPageLayout.findViewWithTag(contact.id);
        contactCard.setContact(contact);
//        goBackToContactsPage(contact, false, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_CONTACT && resultCode == Activity.RESULT_OK) {
            Contact contact = (Contact) data.getSerializableExtra("result");
            presenter.handleContactUpdated(contact);
        }
//        if (requestCode == )
    }

    @Override
    public void onBackPressed(){
        presenter.handleBackPressed();
    }

    @Override
    public void makePhoneCall(String number) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent();
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        } else {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUESTED);
        }
    }

    @Override
    public void sendText(String number) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Intent textIntent = new Intent(Intent.ACTION_VIEW);
            textIntent.setData(Uri.parse("sms:" + number));
            startActivity(textIntent);
        } else {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, TEXT_PERMISSION_REQUESTED);
        }
    }

    @Override
    public void sendEmail(String address) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+address));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUESTED) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // try to make the phone call again
                presenter.handleCallPressed(presenter.getContact().phoneNumber);
            } else {
                // display message saying that you will need to allow permissions to continue using this function.
            };
        }

        if (requestCode == TEXT_PERMISSION_REQUESTED) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // try to make the text again
                presenter.handleTextPressed(presenter.getContact().phoneNumber);
            } else {
                // display message saying that you will need to allow permissions to continue using this function.
            };
        }
    }
}
