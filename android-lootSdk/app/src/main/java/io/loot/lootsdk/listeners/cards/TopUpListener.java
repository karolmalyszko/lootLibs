package io.loot.lootsdk.listeners.cards;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.topUp.TopUpResult;

public interface TopUpListener extends GenericFailListener {

    void onTopUpSuccess(TopUpResult result);
    void onTopUpError(String error);
    void onTopUpParsedError(String parsedErrorMessage);

}
