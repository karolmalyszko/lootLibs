package io.loot.lootsdk.listeners.cards;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface CardStatusChangeListener extends GenericFailListener {

    void onCardStatusChanged();
    void onCardStatusChangeError(String error);

}
