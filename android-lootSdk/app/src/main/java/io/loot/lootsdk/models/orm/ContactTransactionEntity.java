package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import io.loot.lootsdk.models.data.contacts.ContactTransaction;

@Entity(tableName = "ContactTransactions")
public class ContactTransactionEntity {
    @PrimaryKey
    @NonNull
    private String id = "0";
    private String contactId = "0";
    private String description;
    private String localAmount;
    private String localCurrency;
    private String postTransactionBalance;
    private String settlementAmount;
    private String settlementCurrency;
    private String settlementDate;
    private String direction;
    private String type;
    private String status;
    private String paymentDetailsId;

    @Ignore
    private RecipientsAndSendersEntity paymentDetails;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(String localAmount) {
        this.localAmount = localAmount;
    }

    public String getLocalCurrency() {
        return localCurrency;
    }

    public void setLocalCurrency(String localCurrency) {
        this.localCurrency = localCurrency;
    }

    public String getPostTransactionBalance() {
        return postTransactionBalance;
    }

    public void setPostTransactionBalance(String postTransactionBalance) {
        this.postTransactionBalance = postTransactionBalance;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getSettlementCurrency() {
        return settlementCurrency;
    }

    public void setSettlementCurrency(String settlementCurrency) {
        this.settlementCurrency = settlementCurrency;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RecipientsAndSendersEntity getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(RecipientsAndSendersEntity paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getPaymentDetailsId() {
        return paymentDetailsId;
    }

    public void setPaymentDetailsId(String paymentDetailsId) {
        this.paymentDetailsId = paymentDetailsId;
    }

    public ContactTransaction parseToDataObject() {
        ContactTransaction contactTransaction = new ContactTransaction();
        contactTransaction.setStatus(status);
        contactTransaction.setType(type);
        contactTransaction.setDirection(direction);
        contactTransaction.setSettlementDate(settlementDate);
        contactTransaction.setSettlementCurrency(settlementCurrency);
        contactTransaction.setSettlementAmount(settlementAmount);
        contactTransaction.setPostTransactionBalance(postTransactionBalance);
        contactTransaction.setLocalCurrency(localCurrency);
        contactTransaction.setLocalAmount(localAmount);
        contactTransaction.setDescription(description);
        contactTransaction.setId(id);
        return contactTransaction;
    }
}
