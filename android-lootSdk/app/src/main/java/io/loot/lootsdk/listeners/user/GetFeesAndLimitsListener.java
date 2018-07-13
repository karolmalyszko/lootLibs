package io.loot.lootsdk.listeners.user;

import java.util.ArrayList;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.userinfo.FeeOrLimit;

public interface GetFeesAndLimitsListener extends GenericFailListener {

    void onGetCachedFeesAndLimits(ArrayList<FeeOrLimit> feesOrLimits);
    void onGetFeesAndLimitsSuccess(ArrayList<FeeOrLimit> feesOrLimits);
    void onGetFeesAndLimitsError(String error);

}
