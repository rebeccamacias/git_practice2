package com.example.program5.presenters;

import com.example.program5.database.AppDatabase;
import com.example.program5.models.Contact;

public class ContactPresenter {
    public interface MVPView extends BaseMVPView {
//        public void goBackToContactsPage(Contact contact); //part 2
        public void renderContact(Contact contact);
    }

    MVPView view;
    AppDatabase database;

    public ContactPresenter(MVPView view) {
        this.view = view;
        database = view.getContextDatabase();
    }

    public void loadContact(long id){
        new Thread(() -> {
           Contact contact = database.getContactDao().getContact(id);
           view.renderContact(contact);
        }).start();
    }
}
