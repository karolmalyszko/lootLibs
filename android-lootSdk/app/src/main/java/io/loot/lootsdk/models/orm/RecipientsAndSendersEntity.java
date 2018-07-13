package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import io.loot.lootsdk.models.data.contacts.Contact;

@Entity(tableName = "RecipientsAndSenders")
public class RecipientsAndSendersEntity {
    @PrimaryKey
    @NonNull
    private String id = "0";
    private String name;
    private String accountNumber;
    private String sortCode;
    private String initials;
    private String nameToCompare;

    public static Contact parseToDataObject(RecipientsAndSendersEntity recipientAndSenderEntity) {
        if (recipientAndSenderEntity == null) {
            recipientAndSenderEntity = new RecipientsAndSendersEntity();
        }
        Contact contact = new Contact();
        contact.setName(recipientAndSenderEntity.getName());
        contact.setInitials(recipientAndSenderEntity.getInitials());
        contact.setNameToCompare(recipientAndSenderEntity.getNameToCompare());
        contact.generateNameToCompare();
        contact.setSortCode(recipientAndSenderEntity.getSortCode());
        contact.setAccountNumber(recipientAndSenderEntity.getAccountNumber());
        contact.setType(Contact.DetailsType.RECIPIENT_SENDER);
        return contact;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getNameToCompare() {
        return nameToCompare;
    }

    public void setNameToCompare(String nameToCompare) {
        this.nameToCompare = nameToCompare;
    }
}
