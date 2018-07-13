package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.userinfo.AccountDetails;

public interface AccountDetailsListener extends GenericFailListener {

    void onGetCachedAccountDetails(AccountDetails accountDetails);
    void onGetAccountDetailsSuccess(AccountDetails accountDetails);
    void onGetAccountDetailsError(String error);

}
