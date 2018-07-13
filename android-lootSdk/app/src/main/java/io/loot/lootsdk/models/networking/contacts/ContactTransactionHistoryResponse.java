package io.loot.lootsdk.models.networking.contacts;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.loot.lootsdk.models.orm.ContactTransactionEntity;
import lombok.Data;

@Data
public class ContactTransactionHistoryResponse {
    @SerializedName("contact")
    private ContactDetailsResponse contact;
    @SerializedName("transactions")
    private List<ContactTransactionResponse> transactions;

    public static ArrayList<ContactTransactionEntity> parseToEntityObjects(ContactTransactionHistoryResponse response) {
        if (response == null) {
            response = new ContactTransactionHistoryResponse();
        }

        if (response.getTransactions() == null) {
            response.setTransactions(new ArrayList<ContactTransactionResponse>());
        }
        if (response.getContact() == null) {
            response.setContact(new ContactDetailsResponse());
        }
        String contactId = response.getContact().getContactId();
        ArrayList<ContactTransactionEntity> entities = new ArrayList<ContactTransactionEntity>();
        for (ContactTransactionResponse contactTransactionResponse : response.getTransactions()) {
            entities.add(ContactTransactionResponse.parseToEntityObject(contactTransactionResponse, contactId));
        }
        return entities;
    }
}
