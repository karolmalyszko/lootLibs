package io.loot.lootsdk.models.data.contacts;


import android.support.annotation.NonNull;

import java.io.Serializable;

import io.loot.lootsdk.utils.RegexUtil;


public class Contact implements Comparable<Contact>, Serializable {
    public static String DEFAULT_CONTACT_NAME = "Unknown";
    public static String DEFAULT_CONTACT_INITIALS = "U";
    private String contactId;
    private long contactIdHash;
    private String name;
    private String accountNumber;
    private String sortCode;
    private String profilePhoto;
    private String nameToCompare;
    private String initials;
    private boolean hasPayments;
    private DetailsType type;

    public Contact() {
        contactId = "";
        contactIdHash = 0;
        name = DEFAULT_CONTACT_NAME;
        accountNumber = "";
        sortCode = "";
        profilePhoto = "";
        initials = DEFAULT_CONTACT_INITIALS;
        nameToCompare = DEFAULT_CONTACT_NAME;
        type = DetailsType.NO_CONTACT;
    }

    public Contact(Contact contact) {
        if (contact == null) {
            contact = new Contact();
        }
        setContactId(contact.getContactId());
        setContactIdHash(contact.getContactIdHash());
        setName(contact.getName());
        setAccountNumber(contact.getAccountNumber());
        setSortCode(contact.getSortCode());
        setProfilePhoto(contact.getProfilePhoto());
        setHasPayments(contact.hasPayments());
        setInitials(contact.getInitials());
        setNameToCompare(contact.getNameToCompare());
        setType(contact.getType());
    }

    public String getNameToCompare() {
        return nameToCompare;
    }

    public void setNameToCompare(String nameToCompare) {
        this.nameToCompare = nameToCompare;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        if(initials == null || initials.isEmpty()) {
            initials = DEFAULT_CONTACT_INITIALS;
        }
        this.initials = initials;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        if (contactId == null) {
            contactId = "";
        }
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isEmpty()) {
            name = DEFAULT_CONTACT_NAME;
        }
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        if (accountNumber == null) {
            accountNumber = "";
        }
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        if (sortCode == null) {
            sortCode = "";
        }
        this.sortCode = sortCode;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        if (profilePhoto == null) {
            profilePhoto = "";
        }
        this.profilePhoto = profilePhoto;
    }

    public boolean hasPayments() {
        return hasPayments;
    }

    public void setHasPayments(boolean hasPayments) {
        this.hasPayments = hasPayments;
    }

    public DetailsType getType() {
        return type;
    }

    public void setType(DetailsType type) {
        this.type = type;
    }

    public void generateInitials() {
        if(this.name == null) {
            setName("");
        }
        this.initials = RegexUtil.generateInitial(name);
    }

    public void generateNameToCompare() {
        if(this.name == null) {
            setName("");
        }
        this.nameToCompare = RegexUtil.generateNameToCompare(name);
    }

    @Override
    public int compareTo(@NonNull Contact another) {
        int result = getNameToCompare().compareTo(another.getNameToCompare());
        if (result == 0) {
            result = getName().compareTo(another.getName());
        }
        if (result == 0) {
            result = getSortCode().compareTo(another.getSortCode());
        }
        if (result == 0) {
            result = getAccountNumber().compareTo(another.getAccountNumber());
        }
        if (result == 0) {
            result = getContactId().compareTo(another.getContactId());
        }
        return result;
    }

    public long getContactIdHash() {
        return contactIdHash;
    }

    public void setContactIdHash(long contactIdHash) {
        this.contactIdHash = contactIdHash;
    }

    public enum DetailsType {
        LOOT_PUBLIC, LOOT_PHONEBOOK, STANDARD_CONTACT, RECIPIENT_SENDER, NO_CONTACT
    }
}
