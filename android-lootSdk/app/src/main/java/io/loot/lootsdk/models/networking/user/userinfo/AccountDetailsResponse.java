package io.loot.lootsdk.models.networking.user.userinfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import io.loot.lootsdk.models.data.userinfo.Account;
import io.loot.lootsdk.models.data.userinfo.AccountDetails;
import io.loot.lootsdk.models.networking.topUp.TopUpLimitsResponse;
import lombok.Data;

public @Data class AccountDetailsResponse implements Serializable {

    @SerializedName("accounts")
    ArrayList<AccountResponse> accounts;

    @SerializedName("top_up")
    TopUpLimitsResponse topUpLimits;

    public static AccountDetails parseToDataObject(AccountDetailsResponse accountDetailsResponse) {
        if (accountDetailsResponse == null) {
            accountDetailsResponse = new AccountDetailsResponse();
        }

        if (accountDetailsResponse.getAccounts() == null) {
            accountDetailsResponse.setAccounts(new ArrayList<AccountResponse>());
        }

        ArrayList<Account> accountsDataList = new ArrayList<>();

        for (AccountResponse account : accountDetailsResponse.getAccounts()) {
            accountsDataList.add(AccountResponse.parseToDataObject(account));
        }

        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccounts(accountsDataList);

        return accountDetails;
    }

}

