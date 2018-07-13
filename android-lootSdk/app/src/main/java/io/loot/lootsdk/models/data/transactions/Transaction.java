package io.loot.lootsdk.models.data.transactions;

import org.joda.time.DateTime;

import java.io.Serializable;

import io.loot.lootsdk.models.data.category.Category;
import io.loot.lootsdk.models.data.contacts.Contact;

public class Transaction implements  Comparable<Transaction>, Serializable {
    DateTime formatedDate;
    String dateText;
    private String id;
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
    private Category category;
    private Category foundCategory;
    private String accountId;
    private Merchant merchant;
    private Boolean categoryChangeable;
    private Contact contact;
    private Contact lootUser;
    private Boolean payment;
    private Contact sender;
    private Contact recipient;

    public Transaction(String amount, String description, String settlementDate) {
        this();
        this.setLocalAmount(amount);
        this.setDescription(description);
        this.setSettlementDate(settlementDate);
    }

    public Transaction() {
        this.formatedDate = new DateTime();
        this.dateText = "";
        this.id = "";
        this.description = "";
        this.localAmount = "";
        this.localCurrency = "";
        this.settlementAmount = "";
        this.direction = "";
        this.authorisationDate = "";
        this.localDate = "";
        this.settlementDate = "";
        this.postTransactionBalance = "";
        this.transactionType = "";
        this.budgetStatus = "";
        this.transactionStatus = "";
        this.mmc = "";
        this.category = new Category();
        this.foundCategory = new Category();
        this.accountId = "";
        this.merchant = new Merchant();
        this.categoryChangeable = false;
        this.contact = new Contact();
        this.lootUser = new Contact();
        this.payment = false;
        this.sender = new Contact();
        this.recipient = new Contact();
    }

    public Transaction(Transaction transaction) {
        this.formatedDate = transaction.getFormatedDate();
        this.dateText = transaction.getDateText();
        this.id = transaction.getId();
        this.description = transaction.getDescription();
        this.localAmount = transaction.getLocalAmount();
        this.localCurrency = transaction.getLocalCurrency();
        this.settlementAmount = transaction.getSettlementAmount();
        this.direction = transaction.getDirection();
        this.authorisationDate = transaction.getAuthorisationDate();
        this.localDate = transaction.getLocalDate();
        this.settlementDate = transaction.getSettlementDate();
        this.postTransactionBalance = transaction.getPostTransactionBalance();
        this.transactionType = transaction.getTransactionType();
        this.budgetStatus = transaction.getBudgetStatus();
        this.transactionStatus = transaction.getTransactionStatus();
        this.mmc = transaction.getMmc();
        this.category = new Category(transaction.getCategory());
        this.foundCategory = new Category(transaction.getCategory());
        this.accountId = transaction.getAccountId();
        this.merchant = new Merchant(transaction.getMerchant());
        this.categoryChangeable = transaction.getCategoryChangeable();
        this.contact = transaction.getContact();
        this.lootUser = transaction.getLootUser();
        this.payment = transaction.isPayment();
        this.recipient = transaction.getRecipient();
        this.sender = transaction.getSender();
    }

    @Override
    public int compareTo(Transaction another) {
        int dateCompare = another.getFormatedDate().compareTo(formatedDate);
        if (dateCompare != 0) {
            return dateCompare;
        }
        return id.compareTo(another.getId());
    }

    public DateTime getFormatedDate() {
        return formatedDate;
    }

    public void setFormatedDate(DateTime formatedDate) {
        if (formatedDate == null) {
            formatedDate = new DateTime();

        }
        this.formatedDate = formatedDate;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        if (dateText == null) {
            dateText = "";

        }
        this.dateText = dateText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            id = "";

        }
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            description = "";

        }
        this.description = description;
    }

    public String getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(String localAmount) {
        if (localAmount == null) {
            localAmount = "";

        }
        this.localAmount = localAmount;
    }

    public String getLocalCurrency() {
        return localCurrency;
    }

    public void setLocalCurrency(String localCurrency) {
        if (localCurrency == null) {
            localCurrency = "";

        }
        this.localCurrency = localCurrency;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        if (settlementAmount == null) {
            settlementAmount = "";

        }
        this.settlementAmount = settlementAmount;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        if (direction == null) {
            direction = "";

        }
        this.direction = direction;
    }

    public String getAuthorisationDate() {
        return authorisationDate;
    }

    public void setAuthorisationDate(String authorisationDate) {
        if (authorisationDate == null) {
            authorisationDate = "";

        }
        this.authorisationDate = authorisationDate;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        if (localDate == null) {
            localDate = "";

        }
        this.localDate = localDate;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        if (settlementDate == null) {
            settlementDate = "";

        }
        this.settlementDate = settlementDate;
    }

    public String getPostTransactionBalance() {
        return postTransactionBalance;
    }

    public void setPostTransactionBalance(String postTransactionBalance) {
        if (postTransactionBalance == null) {
            postTransactionBalance = "";

        }
        this.postTransactionBalance = postTransactionBalance;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        if (transactionType == null) {
            transactionType = "";

        }
        this.transactionType = transactionType;
    }

    public String getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(String budgetStatus) {
        if (budgetStatus == null) {
            budgetStatus = "";

        }
        this.budgetStatus = budgetStatus;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        if (transactionStatus == null) {
            transactionStatus = "";

        }
        this.transactionStatus = transactionStatus;
    }

    public String getMmc() {
        return mmc;
    }

    public void setMmc(String mmc) {
        if (mmc == null) {
            mmc = "";

        }
        this.mmc = mmc;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if (category == null) {
            category = new Category();

        }
        this.category = category;
    }

    public Category getFoundCategory() {
        return foundCategory;
    }

    public void setFoundCategory(Category foundCategory) {
        if (foundCategory == null) {
            foundCategory = new Category();

        }
        this.foundCategory = foundCategory;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        if (accountId == null) {
            accountId = "";

        }
        this.accountId = accountId;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        if (merchant == null) {
            merchant = new Merchant();

        }
        this.merchant = merchant;
    }

    public Boolean getCategoryChangeable() {
        return this.categoryChangeable;
    }

    public void setCategoryChangeable(Boolean categoryChangeable) {
        if (categoryChangeable == null) {
            categoryChangeable = false;
        }
        this.categoryChangeable = categoryChangeable;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        if (contact == null) {
            contact = new Contact();
        }
        this.contact = contact;
    }

    public Contact getLootUser() {
        return lootUser;
    }

    public void setLootUser(Contact lootUser) {
        if (lootUser == null) {
            lootUser = new Contact();
        }
        this.lootUser = lootUser;
    }

    public void setPayment(Boolean payment) {
        this.payment = payment;
    }

    public Boolean isPayment() {
        return payment;
    }

    public Contact getSender() {
        return sender;
    }

    public void setSender(Contact sender) {
        if (sender == null) {
            sender = new Contact();
        }
        this.sender = sender;
    }

    public Contact getRecipient() {
        return recipient;
    }

    public void setRecipient(Contact recipient) {
        if(recipient == null) {
            recipient = new Contact();
        }
        this.recipient = recipient;
    }
}
