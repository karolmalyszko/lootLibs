package io.loot.lootsdk.models.networking.contacts;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.contacts.Contact;
import io.loot.lootsdk.models.data.contacts.ContactTransaction;
import io.loot.lootsdk.models.orm.ContactTransactionEntity;
import lombok.Data;

@Data
public class ContactTransactionResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("description")
    private String description;
    @SerializedName("local_amount")
    private String localAmount;
    @SerializedName("local_currency")
    private String localCurrency;
    @SerializedName("post_transaction_balance")
    private String postTransactionBalance;
    @SerializedName("settlement_amount")
    private String settlementAmount;
    @SerializedName("settlement_currency")
    private String settlementCurrency;
    @SerializedName("settlement_date")
    private String settlementDate;
    @SerializedName("direction")
    private String direction;
    @SerializedName("type")
    private String type;
    @SerializedName("status")
    private String status;

    public static ContactTransaction parseToDataObject(ContactTransactionResponse response) {
        if (response == null) {
            response = new ContactTransactionResponse();
        }
        ContactTransaction contactTransaction = new ContactTransaction();
        contactTransaction.setId(response.getId());
        contactTransaction.setDescription(response.getDescription());
        contactTransaction.setLocalAmount(response.getLocalAmount());
        contactTransaction.setLocalCurrency(response.getLocalCurrency());
        contactTransaction.setPostTransactionBalance(response.getPostTransactionBalance());
        contactTransaction.setSettlementAmount(response.getSettlementAmount());
        contactTransaction.setSettlementCurrency(response.getSettlementCurrency());
        contactTransaction.setSettlementDate(response.getSettlementDate());
        contactTransaction.setDirection(response.getDirection());
        contactTransaction.setType(response.getType());
        contactTransaction.setStatus(response.getStatus());
        return contactTransaction;
    }

    public static ContactTransactionEntity parseToEntityObject(ContactTransactionResponse response, String contactId) {
        if (response == null) {
            response = new ContactTransactionResponse();
        }
        ContactTransactionEntity contactTransactionEntity = new ContactTransactionEntity();
        contactTransactionEntity.setId(response.getId());
        contactTransactionEntity.setContactId(contactId);
        contactTransactionEntity.setDescription(response.getDescription());
        contactTransactionEntity.setLocalAmount(response.getLocalAmount());
        contactTransactionEntity.setLocalCurrency(response.getLocalCurrency());
        contactTransactionEntity.setPostTransactionBalance(response.getPostTransactionBalance());
        contactTransactionEntity.setSettlementAmount(response.getSettlementAmount());
        contactTransactionEntity.setSettlementCurrency(response.getSettlementCurrency());
        contactTransactionEntity.setSettlementDate(response.getSettlementDate());
        contactTransactionEntity.setDirection(response.getDirection());
        contactTransactionEntity.setType(response.getType());
        contactTransactionEntity.setStatus(response.getStatus());
        return contactTransactionEntity;
    }
}
