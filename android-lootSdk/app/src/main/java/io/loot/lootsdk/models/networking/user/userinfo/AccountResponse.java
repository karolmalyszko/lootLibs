package io.loot.lootsdk.models.networking.user.userinfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.userinfo.Account;
import io.loot.lootsdk.models.orm.AccountEntity;
import lombok.Data;


public
@Data
class AccountResponse implements Serializable {
    @SerializedName("id")
    String id;

    @SerializedName("user_id")
    String userId;

    @SerializedName("provider_id")
    String providerId;

    @SerializedName("balance")
    float accountBalance;

    @SerializedName("number")
    String accountNumber;

    @SerializedName("sort_code")
    String sortCode;

    @SerializedName("user")
    UserInfoResponse userInfo;

    @SerializedName("provider")
    ProviderResponse provider;

    @SerializedName("status")
    String status;

    public static AccountEntity parseToEntityObject(AccountResponse accountResponse) {
        if (accountResponse == null) {
            accountResponse = new AccountResponse();
        }

        AccountEntity entity = new AccountEntity();
        entity.setId(accountResponse.getId());
        entity.setUserId(accountResponse.getUserId());
        entity.setProviderId(accountResponse.getProviderId());
        entity.setAccountBalance(accountResponse.getAccountBalance());
        entity.setSortCode(accountResponse.getSortCode());
        entity.setStatus(accountResponse.getStatus());
        entity.setUserInfo(UserInfoResponse.parseToEntityObject(accountResponse.getUserInfo()));
        entity.setProvider(ProviderResponse.parseToEntityObject(accountResponse.getProvider()));
        entity.setAccountNumber(accountResponse.getAccountNumber());

        return entity;
    }

    public static Account parseToDataObject(AccountResponse accountResponse) {
        if (accountResponse == null) {
            accountResponse = new AccountResponse();
        }
        Account account = new Account();
        account.setId(accountResponse.getId());
        account.setUserId(accountResponse.getUserId());
        account.setAccountBalance(accountResponse.getAccountBalance());
        account.setSortCode(accountResponse.getSortCode());
        account.setStatus(accountResponse.getStatus());
        account.setUserInfo(UserInfoResponse.parseToDataObject(accountResponse.getUserInfo()));
        account.setProvider(ProviderResponse.parseToDataObject(accountResponse.getProvider()));
        account.setAccountNumber(accountResponse.getAccountNumber());

        return account;
    }

}
