package io.loot.lootsdk.listeners.cards;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface Get3DSResultListener extends GenericFailListener {

    void on3DSSuccess();
    void on3DSPending();
    void on3DSError(String error);

}
