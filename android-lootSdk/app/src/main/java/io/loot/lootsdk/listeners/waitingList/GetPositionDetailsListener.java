package io.loot.lootsdk.listeners.waitingList;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.waitingList.PositionDetails;

public interface GetPositionDetailsListener extends GenericFailListener {

    void onDetailsReceived(PositionDetails details);
    void onGettingDetailsError(String error);

}
