package io.loot.lootsdk.models.networking.transactions;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.io.Serializable;

import io.loot.lootsdk.models.data.contacts.Contact;
import io.loot.lootsdk.models.data.transactions.Transaction;
import io.loot.lootsdk.models.networking.RecipientsAndSendersResponse;
import io.loot.lootsdk.models.networking.category.CategoryResponse;
import io.loot.lootsdk.models.networking.contacts.ContactDetailsResponse;
import io.loot.lootsdk.models.orm.TransactionEntity;
import lombok.Data;

public
@Data
class TransactionResponse implements Serializable {

    DateTime formatedDate;
    String dateText;
    @SerializedName("id")
    private String id;
    @SerializedName("description")
    private String description;
    @SerializedName("local_amount")
    private String localAmount;
    @SerializedName("local_currency")
    private String localCurrency;
    @SerializedName("settlement_amount")
    private String settlementAmount;
    @SerializedName("direction")
    private String direction;
    @SerializedName("authorisation_date")
    private String authorisationDate; // YYYYMMDDHHmmss (24 hours time format) 20120617154536
    @SerializedName("local_date")
    private String localDate; // YYYYMMDDHHmmss (24 hours time format) 20120617154536
    @SerializedName("settlement_date")
    private String settlementDate; // YYYYMMDDHHmmss (24 hours time format) 20120617154536
    @SerializedName("post_transaction_balance")
    private String postTransactionBalance;
    @SerializedName("type")
    private String transactionType;
    @SerializedName("budget_status")
    private String budgetStatus;
    @SerializedName("status")
    private String transactionStatus;
    @SerializedName("mmc")
    private String mmc;
    @SerializedName("category")
    private CategoryResponse category;
    @SerializedName("found_category")
    private CategoryResponse foundCategory;
    @SerializedName("account_id")
    private String accountId;
    @SerializedName("merchant")
    private MerchantResponse merchant;
    @SerializedName("category_changeable")
    private Boolean categoryChangeable;
    @SerializedName("contact")
    private ContactDetailsResponse contact;
    @SerializedName("loot_user")
    private ContactDetailsResponse lootUser;
    @SerializedName("recipient")
    private RecipientsAndSendersResponse recipient;
    @SerializedName("sender")
    private RecipientsAndSendersResponse sender;
    @SerializedName("payment")
    private Boolean payment;

    // For mocks only
    public TransactionResponse(String amount, String description, String settlementDate) {
        this.localAmount = amount;
        this.description = description;
        this.settlementDate = settlementDate;
    }

    public TransactionResponse() {
        category = new CategoryResponse();
        foundCategory = new CategoryResponse();
        merchant = new MerchantResponse();
        contact = new ContactDetailsResponse();
    }

    public static Transaction parseToDataObject(TransactionResponse transactionResponse) {
        if (transactionResponse == null) {
            transactionResponse = new TransactionResponse();
        }
        Transaction transaction = new Transaction();
        transaction.setId(transactionResponse.getId());
        transaction.setDescription(transactionResponse.getDescription());
        transaction.setLocalAmount(transactionResponse.getLocalAmount());
        transaction.setLocalCurrency(transactionResponse.getLocalCurrency());
        transaction.setSettlementAmount(transactionResponse.getSettlementAmount());
        transaction.setDirection(transactionResponse.getDirection());
        transaction.setAuthorisationDate(transactionResponse.getAuthorisationDate());
        transaction.setLocalDate(transactionResponse.getLocalDate());
        transaction.setSettlementDate(transactionResponse.getSettlementDate());
        transaction.setPostTransactionBalance(transactionResponse.getPostTransactionBalance());
        transaction.setTransactionType(transactionResponse.getTransactionType());
        transaction.setBudgetStatus(transactionResponse.getBudgetStatus());
        transaction.setTransactionStatus(transactionResponse.getTransactionStatus());
        transaction.setMmc(transactionResponse.getMmc());
        transaction.setDateText(transactionResponse.getDateText());
        transaction.setFoundCategory(CategoryResponse.parseToDataObject(transactionResponse.getFoundCategory()));
        transaction.setCategory(CategoryResponse.parseToDataObject(transactionResponse.getCategory()));
        transaction.setAccountId(transactionResponse.getAccountId());
        transaction.setMerchant(MerchantResponse.parseToDataObject(transactionResponse.getMerchant()));
        transaction.setCategoryChangeable(transactionResponse.getCategoryChangeable());
        transaction.setContact(ContactDetailsResponse.parseToDataObject(transactionResponse.getContact(), Contact.DetailsType.STANDARD_CONTACT));
        transaction.setLootUser(ContactDetailsResponse.parseToDataObject(transactionResponse.getLootUser(), Contact.DetailsType.LOOT_PUBLIC));
        transaction.setPayment(transactionResponse.getPayment());
        transaction.setRecipient(RecipientsAndSendersResponse.parseToDataObject(transactionResponse.getRecipient()));
        transaction.setSender(RecipientsAndSendersResponse.parseToDataObject(transactionResponse.getSender()));
        return transaction;
    }

    public static TransactionEntity parseToEntityObject(TransactionResponse transactionResponse) {
        if (transactionResponse == null) {
            transactionResponse = new TransactionResponse();
        }
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(transactionResponse.getId());
        transactionEntity.setDescription(transactionResponse.getDescription());
        transactionEntity.setLocalAmount(transactionResponse.getLocalAmount());
        transactionEntity.setLocalCurrency(transactionResponse.getLocalCurrency());
        transactionEntity.setSettlementAmount(transactionResponse.getSettlementAmount());
        transactionEntity.setDirection(transactionResponse.getDirection());
        transactionEntity.setAuthorisationDate(transactionResponse.getAuthorisationDate());
        transactionEntity.setLocalDate(transactionResponse.getLocalDate());
        transactionEntity.setPersistedLocalDate(transactionResponse.getLocalDate());
        transactionEntity.setSettlementDate(transactionResponse.getSettlementDate());
        transactionEntity.setPostTransactionBalance(transactionResponse.getPostTransactionBalance());
        transactionEntity.setTransactionType(transactionResponse.getTransactionType());
        transactionEntity.setBudgetStatus(transactionResponse.getBudgetStatus());
        transactionEntity.setTransactionStatus(transactionResponse.getTransactionStatus());
        transactionEntity.setMmc(transactionResponse.getMmc());
        transactionEntity.setFoundCategory(CategoryResponse.parseToEntityObject(transactionResponse.getFoundCategory()));
        transactionEntity.setCategory(CategoryResponse.parseToEntityObject(transactionResponse.getCategory()));
        transactionEntity.setAccountId(transactionResponse.getAccountId());
        transactionEntity.setMerchant(MerchantResponse.parseToEntityObject(transactionResponse.getMerchant()));
        transactionEntity.setCategoryChangeable(transactionResponse.getCategoryChangeable());
        transactionEntity.setContact(ContactDetailsResponse.parseToEntityObject(transactionResponse.getContact()));
        transactionEntity.setLootUser(ContactDetailsResponse.parseToEntityObject(transactionResponse.getLootUser()));
        transactionEntity.setPayment(transactionResponse.getPayment());
        transactionEntity.setRecipient(RecipientsAndSendersResponse.parseToEntityObject(transactionResponse.getRecipient()));
        transactionEntity.setSender(RecipientsAndSendersResponse.parseToEntityObject(transactionResponse.getSender()));
        return transactionEntity;
    }
}

