package com.example.program5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.example.program5.components.ImageSelector;
import com.example.program5.components.MaterialInput;
import com.example.program5.models.Contact;
import com.example.program5.presenters.CreateOrUpdateContactPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateOrUpdateContactActivity extends BaseActivity implements CreateOrUpdateContactPresenter.MVPView{
    CreateOrUpdateContactPresenter presenter;
    LinearLayout mainLayout;
    ImageSelector imageSelector;
    MaterialInput nameInput;
    MaterialInput phoneInput;
    MaterialInput emailInput;
    private final int SELECT_IMAGE = 1;
    private final int TAKE_PICTURE = 2;

    private String currentFilePath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CreateOrUpdateContactPresenter(this, getIntent().getLongExtra("id", -1));
        mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        imageSelector = new ImageSelector(this);
        imageSelector.setOnClickListener((view) -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Choose Image")
                    .setItems(new CharSequence[]{"From Camera", "From Photos"}, (menuItem, i) -> {
                        if (i == 0) {
                            presenter.handleTakePicturePress();
                        } else {
                            presenter.handleSelectImagePress();
                        }
                    }).show();
        });
        mainLayout.addView(imageSelector);

        nameInput = new MaterialInput(this, "Name");
        phoneInput = new MaterialInput(this, "Phone Number");
        phoneInput.setInput(InputType.TYPE_CLASS_PHONE);
        emailInput = new MaterialInput(this, "Email Address");

        MaterialButton saveButton = new MaterialButton(this, null, R.attr.materialButtonStyle);
        saveButton.setText("Save");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        saveButton.setLayoutParams(params);
        MaterialButton cancelButton = new MaterialButton(this, null, R.attr.borderlessButtonStyle);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener((view) -> {
            presenter.handleCancelPress();
        });

        LinearLayout buttons = new LinearLayout(this);
        buttons.setGravity(Gravity.RIGHT);
        buttons.setPadding(48, 0, 48, 0);
        buttons.addView(cancelButton);
        buttons.addView(saveButton);


        saveButton.setOnClickListener((view) -> {
            nameInput.setErrorEnabled(false);
            phoneInput.setErrorEnabled(false);
            emailInput.setErrorEnabled(false);
            presenter.saveContact(
                    nameInput.getText().toString(),
                    phoneInput.getText().toString(),
                    emailInput.getText().toString(),
                    imageSelector.getImageUri()
            );
        });

        mainLayout.addView(nameInput);
        mainLayout.addView(phoneInput);
        mainLayout.addView(emailInput);

        mainLayout.addView(buttons);
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(mainLayout);

        setContentView(scrollView);
    }

    @Override
    public void goBackToContactsPage(Contact contact) {
        if (contact == null) {
            setResult(Activity.RESULT_CANCELED, null);
        } else {
            Intent intent = new Intent();
            intent.putExtra("result", contact);
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void goToPhotos() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_IMAGE);
    }

    @Override
    public void displayImage(String imageUri) {
        imageSelector.setImageUri(imageUri);
    }

    @Override
    public void renderContact(Contact contact) {
        runOnUiThread(() -> {
            nameInput.setText(contact.contactName);
            phoneInput.setText(contact.phoneNumber);
            emailInput.setText(contact.emailAddress);
            imageSelector.setImageUri(contact.pictureUri);
        });
    }

    @Override
    public void displayNameError() {
        Snackbar.make(mainLayout, "Name cannot be blank.", Snackbar.LENGTH_SHORT).show();
        nameInput.setErrorEnabled(true);
        nameInput.setError("Name cannot be blank.");
    }

    @Override
    public void displayPhoneError() {
        Snackbar.make(mainLayout, "Enter a valid phone number", Snackbar.LENGTH_SHORT).show();
        phoneInput.setErrorEnabled(true);
        phoneInput.setError("Enter a valid phone number.");
    }

    @Override
    public void displayEmailError() {
        Snackbar.make(mainLayout, "Enter a valid email.", Snackbar.LENGTH_SHORT).show();
        emailInput.setErrorEnabled(true);
        emailInput.setError("Enter a valid email.");
    }

    @Override
    public void goToCamera() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "JPEG_" + timeStamp + ".jpg";

        File imageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
        currentFilePath = imageFile.getAbsolutePath();

        Uri imageUri = FileProvider.getUriForFile(this, "com.example.program5.provider", imageFile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            presenter.handleImageSelected(imageUri.toString());
        }
        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_CANCELED) {
            presenter.handleImageSelected("");
        }
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            presenter.handleImageSelected(currentFilePath);
        }
    }
}