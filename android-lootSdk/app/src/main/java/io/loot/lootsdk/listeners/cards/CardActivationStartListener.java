package io.loot.lootsdk.listeners.cards;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface CardActivationStartListener extends GenericFailListener {
    void onCardActivationStarted();
    void onCardActivationStartError(String error);
}
