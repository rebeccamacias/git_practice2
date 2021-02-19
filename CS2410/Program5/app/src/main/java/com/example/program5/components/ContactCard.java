package com.example.program5.components;

import android.content.Context;
import android.net.Uri;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.program5.CircleDisplay;
import com.example.program5.R;
import com.example.program5.models.Contact;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

public class ContactCard extends MaterialCardView {
    public MaterialButton callButton;
    public MaterialButton textButton;
    public MaterialButton emailButton;
    FloatingActionButton fab;
    Contact contact;
    AppCompatImageView imageView;
    MaterialTextView nameView;
    MaterialTextView phoneView;
    MaterialTextView emailView;
    LinearLayout header;
    boolean showFullContact = false;

    public ContactCard(Context context, Contact contact) {
        this(context, contact, false);
    }

    public ContactCard(Context context, Contact contact, boolean showFullContact) {
        super(context);
        setTag(contact.id);
        this.contact = contact;
        this.showFullContact = showFullContact;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(48, 24,48,24);
        setLayoutParams(params);

        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        header = new LinearLayout(context);
        header.setOrientation(LinearLayout.VERTICAL);

        LinearLayout body = new LinearLayout(context);
        body.setPadding(72, 0, 72, 0);
        body.setOrientation(LinearLayout.VERTICAL);

        LinearLayout footer = new LinearLayout(context);

        mainLayout.addView(header);
        mainLayout.addView(body);
        mainLayout.addView(footer);

        addView(mainLayout);

        //Header

        imageView = new AppCompatImageView(context);
        setupImage();
        header.addView(imageView);

        //Body
        nameView = new MaterialTextView(context, null, R.attr.textAppearanceHeadline6);
        nameView.setText(contact.contactName);
        body.addView(nameView);

        if (showFullContact) {
            phoneView = new MaterialTextView(context);
            phoneView.setText(contact.phoneNumber);
            phoneView.setTextSize(18);
            body.addView(phoneView);

            emailView = new MaterialTextView(context);
            emailView.setText(contact.emailAddress);
            emailView.setTextSize(18);

            body.addView(emailView);
        }


        // Footer
        if (showFullContact) {
            footer.setPadding(0, 50, 0, 50);
            callButton = new MaterialButton(context, null, R.attr.borderlessButtonStyle);
            callButton.setText( "Call");

            textButton = new MaterialButton(context, null, R.attr.borderlessButtonStyle);
            textButton.setText("Text");

            if (contact.emailAddress.length() > 0){
                emailButton = new MaterialButton(context, null, R.attr.borderlessButtonStyle);
                emailButton.setText("Email");
                footer.addView(emailButton);
            }

            footer.setGravity(Gravity.RIGHT);

            footer.addView(callButton);
            footer.addView(textButton);
        } else {
            footer.setPadding(8, 0, 8, 0);
        }

        if (showFullContact) {
            fab = new FloatingActionButton(context);
            fab.setImageResource(R.drawable.ic_baseline_edit_24);
            LayoutParams fabParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            fabParams.gravity = Gravity.RIGHT;
            fabParams.setMargins(0, 480, 48, 0);
            fab.setLayoutParams(fabParams);
            addView(fab);
        }
    }

    private void setupImage() {

        if (contact.pictureUri.equals("")) {
            if (showFullContact){
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 560);
                imageView.setLayoutParams(imageParams);
                imageView.setImageResource(R.drawable.ic_baseline_photo_size_select_actual_240);
                header.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground, null));
            }
        } else if (!contact.pictureUri.equals("")) {
            if (showFullContact){
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 560);
                imageView.setLayoutParams(imageParams);
                imageView.setImageURI(Uri.parse(contact.pictureUri));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        setupImage();
        nameView.setText(contact.contactName);
        if (showFullContact) {
            phoneView.setText(contact.phoneNumber);
            emailView.setText(contact.emailAddress);
        }
    }

    public void setFabOnClickListener(OnClickListener l) {
        fab.setOnClickListener(l);
    }
}
