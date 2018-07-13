package io.loot.lootsdk.models.networking.contacts;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import io.loot.lootsdk.models.data.contacts.AllContacts;
import io.loot.lootsdk.models.data.contacts.Contact;
import io.loot.lootsdk.models.orm.ContactEntity;
import lombok.Data;

@Data
public class ContactListResponse implements Serializable {
    @SerializedName("friends_on_loot")
    private ArrayList<ContactDetailsResponse> friendsOnLoot = null;
    @SerializedName("saved_details")
    private ArrayList<ContactDetailsResponse> savedDetails;

    public static ArrayList<ContactEntity> parseToEntitiesObject(ContactListResponse contactListResponse) {
        if (contactListResponse == null) {
            contactListResponse = new ContactListResponse();
        }
        ArrayList<ContactEntity> contactEntities = new ArrayList<ContactEntity>();
        ArrayList<ContactDetailsResponse> responses = new ArrayList<ContactDetailsResponse>();
        responses.addAll(contactListResponse.getFriendsOnLoot());
        responses.addAll(contactListResponse.getSavedDetails());
        for(ContactDetailsResponse contactDetailsResponse : responses) {
            contactEntities.add(ContactDetailsResponse.parseToEntityObject(contactDetailsResponse));
        }
        return contactEntities;
    }
}
