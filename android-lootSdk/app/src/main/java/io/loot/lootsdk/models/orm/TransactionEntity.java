package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.loot.lootsdk.database.TimestampConverter;
import io.loot.lootsdk.models.data.category.Category;
import io.loot.lootsdk.models.data.contacts.Contact;
import io.loot.lootsdk.models.data.transactions.Transaction;

// We need to call it 'Transactions' instead of 'Transaction'
// because Transaction is forbidden in SQL

@Entity(tableName = "Transactions")
public class TransactionEntity {

    // for testing
    @TypeConverters({TimestampConverter.class})
    public Date persistedLocalDate;
    @PrimaryKey
    @NonNull
    private String id = "0";
    private String description;
    private String localAmount;
    private String localCurrency;
    private String settlementAmount;
    private String direction;
    private String authorisationDate; // YYYYMMDDHHmmss (24 hours time format) 20120617154536
    private String localDate; // YYYYMMDDHHmmss (24 hours time format) 20120617154536
    private String settlementDate; // YYYYMMDDHHmmss (24 hours time format) 20120617154536
    private String postTransactionBalance;
    private String transactionType;
    private String budgetStatus;
    private String transactionStatus;
    private String mmc;
    private boolean payment;
    private String categoryId;
    private String foundCategoryId;
    private String merchantId;
    private String contactId;
    private String lootUserId;
    private String recipientId;
    private String senderId;
    @Ignore
    private CategoryEntity category;
    @Ignore
    private CategoryEntity foundCategory;
    @Ignore
    private MerchantEntity merchant;
    @Ignore
    private ContactEntity contact;
    @Ignore
    private ContactEntity lootUser;
    @Ignore
    private List<Category> availableCategories;
    @Ignore
    private RecipientsAndSendersEntity recipient;
    @Ignore
    private RecipientsAndSendersEntity sender;
    private String accountId;
    private Boolean categoryChangeable;

    public Transaction parseToDataObject() {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setDescription(description);
        transaction.setLocalAmount(localAmount);
        transaction.setLocalCurrency(localCurrency);
        transaction.setSettlementAmount(settlementAmount);
        transaction.setDirection(direction);
        transaction.setAuthorisationDate(authorisationDate);
        transaction.setLocalDate(localDate);
        transaction.setSettlementDate(settlementDate);
        transaction.setPostTransactionBalance(postTransactionBalance);
        transaction.setTransactionType(transactionType);
        transaction.setBudgetStatus(budgetStatus);
        transaction.setTransactionStatus(transactionStatus);
        transaction.setMmc(mmc);
        transaction.setCategoryChangeable(categoryChangeable);
        transaction.setPayment(payment);

        if (getFoundCategory() != null) {
            transaction.setFoundCategory(getFoundCategory().parseToDataObject());
        }
        if (getCategory() != null) {
            transaction.setCategory(getCategory().parseToDataObject());
        }
        transaction.setAccountId(accountId);
        if (getMerchant() != null) {
            transaction.setMerchant(getMerchant().parseToDataObject());
        }
        if (getContact() != null && getContact().getContactId() != null && !getContact().getContactId().isEmpty()) {
            transaction.setContact(ContactEntity.parseToDataObject(getContact(), Contact.DetailsType.STANDARD_CONTACT));
        }
        if (getLootUser() != null && getLootUser().getContactId() != null && !getLootUser().getContactId().isEmpty()) {
            transaction.setLootUser(ContactEntity.parseToDataObject(getLootUser(), Contact.DetailsType.LOOT_PUBLIC));
        }
        if(getSender() != null && getSender().getId() != null && !getSender().getId().isEmpty()) {
            transaction.setSender(RecipientsAndSendersEntity.parseToDataObject(getSender()));
        }
        if(getRecipient() != null && getRecipient().getId() != null && !getRecipient().getId().isEmpty()) {
            transaction.setRecipient(RecipientsAndSendersEntity.parseToDataObject(getRecipient()));
        }
        return transaction;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalAmount() {
        return this.localAmount;
    }

    public void setLocalAmount(String localAmount) {
        this.localAmount = localAmount;
    }

    public String getLocalCurrency() {
        return this.localCurrency;
    }

    public void setLocalCurrency(String localCurrency) {
        this.localCurrency = localCurrency;
    }

    public String getSettlementAmount() {
        return this.settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getAuthorisationDate() {
        return this.authorisationDate;
    }

    public void setAuthorisationDate(String authorisationDate) {
        this.authorisationDate = authorisationDate;
    }

    public String getLocalDate() {
        return this.localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public String getSettlementDate() {
        return this.settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getPostTransactionBalance() {
        return this.postTransactionBalance;
    }

    public void setPostTransactionBalance(String postTransactionBalance) {
        this.postTransactionBalance = postTransactionBalance;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getBudgetStatus() {
        return this.budgetStatus;
    }

    public void setBudgetStatus(String budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

    public String getTransactionStatus() {
        return this.transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getMmc() {
        return this.mmc;
    }

    public void setMmc(String mmc) {
        this.mmc = mmc;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFoundCategoryId() {
        return this.foundCategoryId;
    }

    public void setFoundCategoryId(String foundCategoryId) {
        this.foundCategoryId = foundCategoryId;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Boolean getCategoryChangeable() {
        return categoryChangeable;
    }

    public void setCategoryChangeable(Boolean categoryChangeable) {
        this.categoryChangeable = categoryChangeable;
    }

    public MerchantEntity getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantEntity merchant) {
        this.merchant = merchant;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public CategoryEntity getFoundCategory() {
        return foundCategory;
    }

    public void setFoundCategory(CategoryEntity foundCategory) {
        this.foundCategory = foundCategory;
    }

    public void setPersistedLocalDate(String persistedLocalDate) {
        SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        try {
            this.persistedLocalDate = sdfOut.parse(persistedLocalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ContactEntity getContact() {
        return contact;
    }

    public void setContact(ContactEntity contact) {
        this.contact = contact;
    }

    public ContactEntity getLootUser() {
        return lootUser;
    }

    public void setLootUser(ContactEntity lootUser) {
        this.lootUser = lootUser;
    }

    public String getLootUserId() {
        return lootUserId;
    }

    public void setLootUserId(String lootUserId) {
        this.lootUserId = lootUserId;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public RecipientsAndSendersEntity getRecipient() {
        return recipient;
    }

    public void setRecipient(RecipientsAndSendersEntity recipient) {
        this.recipient = recipient;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public RecipientsAndSendersEntity getSender() {
        return sender;
    }

    public void setSender(RecipientsAndSendersEntity sender) {
        this.sender = sender;
    }
}
