package com.example.program5.presenters;

import com.example.program5.database.AppDatabase;
import com.example.program5.models.Contact;

public class ContactPresenter {
    public interface MVPView extends BaseMVPView {
        void goBackToContactsPage(Contact contact, boolean isDeleted, boolean isUpdated);
        void renderContact(Contact contact);
        void displayDeleteConfirmation();
        void goToEditPage(Contact contact);
        void updateContactUI(Contact contact);
        void makePhoneCall(String number);
        void sendText(String number);
        void sendEmail(String address);
    }

    MVPView view;
    AppDatabase database;
    Contact contact;
    boolean didUpdate = false;


    public ContactPresenter(MVPView view, long id) {
        this.view = view;
        database = view.getContextDatabase();
        new Thread(() -> {
            contact =  database.getContactDao().getContact(id);
            view.renderContact(contact);
        }).start();
    }

    public void handleDeletePressed() {
        view.displayDeleteConfirmation();
    }

    public void handleEditPressed() {
        view.goToEditPage(contact);
    }

    public void deleteContact() {
        new Thread(() -> {
            database.getContactDao().delete(contact);
            view.goBackToContactsPage(contact, true, false);
        }).start();
    }

    public void handleContactUpdated(Contact contact){
        this.contact = contact;
        didUpdate = true;
        view.updateContactUI(contact);
    }

    public void handleBackPressed(){
        view.goBackToContactsPage(contact, false, didUpdate);
    }

    public void handleCallPressed(String phoneNumber) {
        view.makePhoneCall(phoneNumber);
    }

    public void handleTextPressed(String phoneNumber) {
        view.sendText(phoneNumber);
    }

    public void handleEmailPressed(String email) {
        view.sendEmail(email);
    }

    public Contact getContact(){
        return contact;
    }
}
