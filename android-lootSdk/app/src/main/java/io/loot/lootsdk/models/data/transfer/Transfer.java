package io.loot.lootsdk.models.data.transfer;

import io.loot.lootsdk.models.data.contacts.Contact;
import io.loot.lootsdk.models.data.transactions.Transaction;

public class Transfer {

    private String recipentName;
    private String recipentAccountNumber;
    private String recipentSortCode;
    private int amount;
    private String reference;
    private String uiOrigin;

    public Transfer(String recipentName, String recipentAccountNumber, String recipentSortCode, int amount, String reference, String uiOrigin) {
        this.setRecipentName(recipentName);
        this.setRecipentAccountNumber(recipentAccountNumber);
        this.setRecipentSortCode(recipentSortCode);
        this.setAmount(amount);
        this.setReference(reference);
        this.setUiOrigin(uiOrigin);
    }

    public Transfer() {
        this.recipentName = "";
        this.recipentAccountNumber = "";
        this.recipentSortCode = "";
        this.amount = 0;
        this.reference = "";
        this.uiOrigin = "";
    }

    public Transfer(Transfer transfer) {
        this.recipentName = transfer.getRecipentName();
        this.recipentAccountNumber = transfer.getRecipentAccountNumber();
        this.recipentSortCode = transfer.getRecipentSortCode();
        this.amount = transfer.getAmount();
        this.reference = transfer.getReference();
        this.uiOrigin = transfer.getUiOrigin();
    }

    public String getRecipentName() {
        return recipentName;
    }

    public void setRecipentName(String recipentName) {
        if (recipentName == null) {
            recipentName = "";
        }
        this.recipentName = recipentName;
    }

    public String getRecipentAccountNumber() {
        return recipentAccountNumber;
    }

    public void setRecipentAccountNumber(String recipentAccountNumber) {
        if (recipentAccountNumber == null) {
            recipentAccountNumber = "";
        }
        this.recipentAccountNumber = recipentAccountNumber;
    }

    public String getRecipentSortCode() {
        return recipentSortCode;
    }

    public void setRecipentSortCode(String recipentSortCode) {
        if (recipentSortCode == null) {
            recipentSortCode = "";
        }
        this.recipentSortCode = recipentSortCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        if (reference == null) {
            reference = "";
        }
        this.reference = reference;
    }

    public void setRecipent(Contact contact) {
        if (contact == null) {
            contact = new Contact();
        }
        this.setRecipentName(contact.getName());
        this.setRecipentAccountNumber(contact.getAccountNumber());
        this.setRecipentSortCode(contact.getSortCode());
    }

    public String getUiOrigin() {
        return uiOrigin;
    }

    public void setUiOrigin(String uiOrigin) {
        if(uiOrigin == null) {
            uiOrigin = "";
        }
        this.uiOrigin = uiOrigin;
    }
}
