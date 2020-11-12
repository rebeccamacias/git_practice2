package com.example.program5.presenters;

import com.example.program5.database.AppDatabase;
import com.example.program5.models.Contact;

public class NewContactPresenter { //new contact page
    MVPView view;
    AppDatabase database;
    public interface MVPView extends BaseMVPView {
        public void goBackToContactsPage(Contact contact);
    }

    public NewContactPresenter(MVPView view){
        this.view = view;
        database = view.getContextDatabase();
    }

    public void createContact(String contactName, String phoneNumber, String emailAddress) {
        //check to make sure contents not empty
        new Thread(() -> {
            Contact contact = new Contact();
            contact.contactName = contactName;
            contact.phoneNumber = phoneNumber;
            contact.emailAddress = emailAddress;
            contact.id = database.getContactDao().insert(contact);
            view.goBackToContactsPage(contact); //add id?
        }).start();
    }

}
