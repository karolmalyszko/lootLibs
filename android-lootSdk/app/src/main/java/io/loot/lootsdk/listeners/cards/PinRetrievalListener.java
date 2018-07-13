package io.loot.lootsdk.listeners.cards;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface PinRetrievalListener extends GenericFailListener {
    void onPinRetrieveSuccess();
    void onPinRetrieveError(String error);
}
