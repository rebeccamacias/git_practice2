package com.example.program5.presenters;

import com.example.program5.database.AppDatabase;
import com.example.program5.models.Contact;

import java.util.ArrayList;

public class ContactsPresenter { //Main job: load contacts from DB
    private MVPView view;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private AppDatabase database;
    public interface MVPView extends BaseMVPView{
        public void renderContact(Contact contact);
        public void goToNewContactPage();
        public void goToContactPage(Contact contact);
    }

    public ContactsPresenter(MVPView view){
        this.view = view;
        database = view.getContextDatabase();
        refreshContacts();
    }

    public void handleNewContactClick() {
        new Thread(() -> {
            view.goToNewContactPage();
        }).start();
    }

    public void refreshContacts() {
        new Thread(() -> {
            contacts = (ArrayList<Contact>)database.getContactDao().getAllContacts();
            renderContacts();
        }).start();
    }

    public void onNewContactCreated(Contact contact){
        contacts.add(contact);
        view.renderContact(contact);
    }

    private void renderContacts(){
        contacts.forEach(contact -> {
            view.renderContact(contact);
        });
    }

    public void handleContactSelected(Contact contact){
        view.goToContactPage(contact);
    }
}
