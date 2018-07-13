package io.loot.lootsdk.models.networking.contacts;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.contacts.Contact;
import io.loot.lootsdk.models.orm.ContactEntity;
import io.loot.lootsdk.utils.RegexUtil;
import lombok.Data;

@Data
public class ContactDetailsResponse implements Serializable {
    @SerializedName(value="contact_id", alternate={"public_id"})
    private String contactId;
    @SerializedName("name")
    private String name;
    @SerializedName("account_number")
    private String accountNumber;
    @SerializedName("sort_code")
    private String sortCode;
    @SerializedName("profile_photo")
    private String profilePhoto;
    @SerializedName("has_payments")
    private boolean hasPayments;

    public ContactDetailsResponse() {
        this.contactId = "";
    }

    public static Contact parseToDataObject(ContactDetailsResponse contactDetailsResponse, Contact.DetailsType type) {
        if (contactDetailsResponse == null) {
            contactDetailsResponse = new ContactDetailsResponse();
        }
        Contact contact = new Contact();
        contact.setContactId(contactDetailsResponse.getContactId());
        contact.setName(contactDetailsResponse.getName());
        contact.setAccountNumber(contactDetailsResponse.getAccountNumber());
        contact.setSortCode(contactDetailsResponse.getSortCode());
        contact.setProfilePhoto(contactDetailsResponse.getProfilePhoto());
        contact.setHasPayments(contactDetailsResponse.isHasPayments());
        contact.generateInitials();
        contact.generateNameToCompare();
        contact.setType(type);
        return contact;
    }

    public static ContactEntity parseToEntityObject(ContactDetailsResponse response) {
        if (response == null) {
            response = new ContactDetailsResponse();
        }
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setContactId(response.getContactId());
        contactEntity.setContactIdHash(response.getContactId().hashCode());
        contactEntity.setName(response.getName());
        contactEntity.setAccountNumber(response.getAccountNumber());
        contactEntity.setSortCode(response.getSortCode());
        contactEntity.setProfilePhoto(response.getProfilePhoto());
        contactEntity.setHasPayments(response.isHasPayments());
        contactEntity.setInitials(RegexUtil.generateInitial(response.getName()));
        contactEntity.setNameToCompare(RegexUtil.generateNameToCompare(response.getName()));
        return contactEntity;
    }
}
