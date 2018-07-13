package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.topUp.TopUpLimits;

public interface TopUpLimitsListener extends GenericFailListener {

    void onTopUpLimitsReceived(TopUpLimits topUpLimits);
    void onCachedTopUpLimitsReceived(TopUpLimits topUpLimits);
    void onTopUpLimitsErrorReceived(String error);

}
