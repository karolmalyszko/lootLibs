package io.loot.lootsdk.models.data.contacts;


import java.util.ArrayList;
import java.util.List;

public class ContactTransactionHistory { //TODO REFACTOR NAME to PaymentHistory
    private Contact contact;
    private List<ContactTransaction> transactions;

    public ContactTransactionHistory() {
        contact = new Contact();
        transactions = new ArrayList<ContactTransaction>();
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        if (contact == null) {
            contact = new Contact();
        }
        this.contact = contact;
    }


    public List<ContactTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<ContactTransaction> transactions) {
        if (transactions == null) {
            transactions = new ArrayList<ContactTransaction>();
        }
        this.transactions = transactions;
    }
}
