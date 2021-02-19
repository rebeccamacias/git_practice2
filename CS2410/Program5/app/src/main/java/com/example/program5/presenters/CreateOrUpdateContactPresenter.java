package com.example.program5.presenters;

import com.example.program5.database.AppDatabase;
import com.example.program5.models.Contact;

public class CreateOrUpdateContactPresenter { //new contact page
    public interface MVPView extends BaseMVPView {
        void goBackToContactsPage(Contact contact);
        void goToPhotos();
        void displayImage(String imageUri);
        void renderContact(Contact contact);
        void displayNameError();
        void displayPhoneError();
        void displayEmailError();
        void goToCamera();
    }

    MVPView view;
    AppDatabase database;
    Contact contact;

    public CreateOrUpdateContactPresenter(MVPView view, long id){
        this.view = view;
        database = view.getContextDatabase();
        if (id != -1){
            new Thread(() -> {
                contact = database.getContactDao().getContact(id);
                view.renderContact(contact);
            }).start();
        }
    }

    public void saveContact(String contactName, String phoneNumber, String emailAddress, String pictureUri) {
        if (contactName.length() == 0){
            view.displayNameError();
            return;
        }
        if (phoneNumber.length() == 0 || !phoneNumber.matches("[0-9]+")){
            view.displayPhoneError();
            return;
        }
        if (emailAddress.length() > 0){
            if (!emailAddress.contains("@")){
                view.displayEmailError();
                return;
            }
        }
        new Thread(() -> {
            if (contact == null){ //create new contact
                Contact contact = new Contact();
                contact.contactName = contactName;
                contact.phoneNumber = phoneNumber;
                contact.emailAddress = emailAddress;
                contact.pictureUri = pictureUri;
                contact.id = database.getContactDao().insert(contact);
                view.goBackToContactsPage(contact);
            } else { //update existing contact
                contact.contactName = contactName;
                contact.phoneNumber = phoneNumber;
                contact.emailAddress = emailAddress;
                contact.pictureUri = pictureUri;
                database.getContactDao().update(contact);
                view.goBackToContactsPage(contact);
            }
        }).start();
    }

    public void handleCancelPress() {
        view.goBackToContactsPage(null);
    }

    public void handleSelectImagePress() {
        view.goToPhotos();
    }

    public void handleImageSelected(String imageUri) {
        view.displayImage(imageUri);
    }

    public void handleTakePicturePress() {
        view.goToCamera();
    }
}
