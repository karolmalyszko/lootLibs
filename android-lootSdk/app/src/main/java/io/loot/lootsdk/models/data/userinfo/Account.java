package io.loot.lootsdk.models.data.userinfo;

import java.io.Serializable;

public class Account implements Serializable {

    private String id;
    private String userId;
    private float accountBalance;
    private String accountNumber;
    private String sortCode;
    private UserInfo userInfo;
    private Provider provider;
    private String status;

    public Account() {
        this.id = "";
        this.userId = "";
        this.accountBalance = 0;
        this.accountNumber = "";
        this.sortCode = "";
        this.userInfo = new UserInfo();
        this.provider = new Provider();
        this.status = "";
    }

    public Account(Account account) {
        this.id = account.getId();
        this.userId = account.getUserId();
        this.accountBalance = account.getAccountBalance();
        this.accountNumber = account.getAccountNumber();
        this.sortCode = account.getSortCode();
        this.userInfo = new UserInfo(account.getUserInfo());
        this.provider = new Provider(account.getProvider());
        this.status = account.getStatus();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            id = "";
        }
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId == null) {
            userId = "";
        }
        this.userId = userId;
    }

    public float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        if (accountNumber == null) {
            accountNumber = "";
        }
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        if (sortCode == null) {
            sortCode = "";
        }
        this.sortCode = sortCode;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        this.userInfo = userInfo;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        if (provider == null) {
            provider = new Provider();
        }
        this.provider = provider;
    }
}
