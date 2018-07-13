package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import io.loot.lootsdk.models.data.userinfo.Account;

@android.arch.persistence.room.Entity(tableName = "Account")
public class AccountEntity {

    @PrimaryKey
    @NonNull
    private String id = "";
    private float accountBalance;
    private String accountNumber;
    private String sortCode;
    private String userId;
    private String status;

    @Embedded(prefix = "user_info_")
    private UserInfoEntity userInfo;
    private long userInfoId;

    @Embedded(prefix = "provider_")
    private ProviderEntity provider;
    private String providerId;

    public Account parseToDataObject() {
        Account account = new Account();
        account.setId(id);
        account.setUserId(userId);
        account.setAccountBalance(accountBalance);
        account.setSortCode(sortCode);
        account.setAccountNumber(accountNumber);

        if (getUserInfo() != null) {
            account.setUserInfo(getUserInfo().parseToDataObject());
        }

        if (getProvider() != null) {
            account.setProvider(getProvider().parseToDataObject());
        }


        return account;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return this.providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public float getAccountBalance() {
        return this.accountBalance;
    }

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return this.sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public long getUserInfoId() {
        return this.userInfoId;
    }

    public void setUserInfoId(long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }

    public ProviderEntity getProvider() {
        return provider;
    }

    public void setProvider(ProviderEntity provider) {
        this.provider = provider;
    }
}
