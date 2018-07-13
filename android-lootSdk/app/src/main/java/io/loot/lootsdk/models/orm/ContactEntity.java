package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import io.loot.lootsdk.models.data.contacts.Contact;

@android.arch.persistence.room.Entity(tableName = "Contact")
public class ContactEntity {
    @PrimaryKey
    @NonNull
    private String contactId = "0";
    private long contactIdHash = 0;
    private String name;
    private String accountNumber;
    private String sortCode;
    private String profilePhoto;
    private String initials;
    private String nameToCompare;
    private boolean hasPayments = false;

    public static Contact parseToDataObject(ContactEntity contactEntity, Contact.DetailsType type) {
        if (contactEntity == null) {
            contactEntity = new ContactEntity();
        }
        Contact contact = new Contact();
        contact.setContactId(contactEntity.getContactId());
        contact.setContactIdHash(contactEntity.getContactIdHash());
        contact.setName(contactEntity.getName());
        contact.setAccountNumber(contactEntity.getAccountNumber());
        contact.setSortCode(contactEntity.getSortCode());
        contact.setProfilePhoto(contactEntity.getProfilePhoto());
        contact.setHasPayments(contactEntity.hasPayments());
        contact.setInitials(contactEntity.getInitials());
        contact.setNameToCompare(contactEntity.getNameToCompare());
        contact.setType(type);
        return contact;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public long getContactIdHash() {
        return contactIdHash;
    }

    public void setContactIdHash(long contactIdHash) {
        this.contactIdHash = contactIdHash;
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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public boolean hasPayments() {
        return hasPayments;
    }

    public void setHasPayments(boolean hasPayments) {
        this.hasPayments = hasPayments;
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
