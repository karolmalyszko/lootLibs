package io.loot.lootsdk.listeners.cards;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.networking.cards.CardActivationResponse;

public interface PinCodeVerificationListener extends GenericFailListener {
    void onPinCodeVerified(CardActivationResponse response);
    void onPinCodeVerificationError(String error);
}
