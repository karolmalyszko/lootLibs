package io.loot.lootsdk.models.data.cards;

public class CardActivation {
    private String id;
    private String accountId;
    private String status;
    private String pinCode;

    public CardActivation() {
        id = "";
        accountId = "";
        status = "";
        pinCode = "";
    }

    public CardActivation(CardActivation cardActivation) {
        id = cardActivation.getId();
        accountId = cardActivation.getAccountId();
        status = cardActivation.getStatus();
        pinCode = cardActivation.getPinCode();
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        if (accountId == null) {
            accountId = "";
        }
        this.accountId = accountId;
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

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        if (pinCode == null) {
            pinCode = "";
        }
        this.pinCode = pinCode;
    }

}
