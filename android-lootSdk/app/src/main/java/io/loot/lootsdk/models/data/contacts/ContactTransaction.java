package io.loot.lootsdk.models.data.contacts;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.io.Serializable;

public class ContactTransaction implements Comparable<ContactTransaction>, Serializable {
    private String id;
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
    private DateTime formatedDate;
    private String dateText;

    public ContactTransaction() {
        this.formatedDate = new DateTime();
        dateText = "";
        id = "";
        description = "";
        localAmount = "";
        localCurrency = "";
        postTransactionBalance = "";
        settlementAmount = "";
        settlementCurrency = "";
        settlementDate = "";
        direction = "";
        type = "";
        status = "";
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

    public String getPostTransactionBalance() {
        return postTransactionBalance;
    }

    public void setPostTransactionBalance(String postTransactionBalance) {
        if (postTransactionBalance == null) {
            postTransactionBalance = "";
        }
        this.postTransactionBalance = postTransactionBalance;
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

    public String getSettlementCurrency() {
        return settlementCurrency;
    }

    public void setSettlementCurrency(String settlementCurrency) {
        if (settlementCurrency == null) {
            settlementCurrency = "";
        }
        this.settlementCurrency = settlementCurrency;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        if (direction == null) {
            direction = "";
        }
        this.direction = direction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null) {
            type = "";
        }
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null) {
            status = "";
        }
        this.status = status;
    }

    public void setFormatedDate(DateTime formatedDate) {
        if (formatedDate == null) {
            formatedDate = new DateTime();
        }
        this.formatedDate = formatedDate;
    }

    public DateTime getFormatedDate() {
        return formatedDate;
    }
    
    public void setDateText(String dateText) {
        if (dateText == null) {
            dateText = "";
        }
        this.dateText = dateText;
    }

    @Override
    public int compareTo(@NonNull ContactTransaction another) {
        int dateCompare = another.getFormatedDate().compareTo(formatedDate);
        if (dateCompare != 0) {
            return dateCompare;
        }
        return id.compareTo(another.getId());
    }

    public String getDateText() {
        return dateText;
    }
}
